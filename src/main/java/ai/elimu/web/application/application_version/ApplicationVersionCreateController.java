package ai.elimu.web.application.application_version;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.entity.application.Application;
import ai.elimu.entity.application.ApplicationVersion;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.model.v2.enums.admin.ApplicationStatus;
import ai.elimu.util.ChecksumHelper;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.DiscordHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dongliu.apk.parser.ByteArrayApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/application/application-version/create")
@RequiredArgsConstructor
@Slf4j
public class ApplicationVersionCreateController {

  public static final int MIN_SDK_VERSION = 26;

  private final ApplicationDao applicationDao;

  private final ApplicationVersionDao applicationVersionDao;

  @GetMapping
  public String handleRequest(
      @RequestParam Long applicationId,
      Model model
  ) {
    log.info("handleRequest");

    log.info("applicationId: " + applicationId);
    Application application = applicationDao.read(applicationId);

    ApplicationVersion applicationVersion = new ApplicationVersion();
    applicationVersion.setApplication(application);
    model.addAttribute("applicationVersion", applicationVersion);

    return "application/application-version/create";
  }

  @PostMapping
  public String handleSubmit(
      ApplicationVersion applicationVersion,
      BindingResult result,
      Model model,
      HttpServletRequest request,
      HttpSession session
  ) {
    log.info("handleSubmit");

    Application application = applicationVersion.getApplication();
    log.info("application.getId(): " + application.getId());

    String fileUrl = request.getParameter("fileUrl");
    log.info("fileUrl: " + fileUrl);
    if (StringUtils.isBlank(fileUrl)) {
      result.rejectValue("fileUrl", "NotNull");
    }

    try {
      // Download the APK file to a temporary folder on the file system
      String tmpDir = System.getProperty("java.io.tmpdir");
      log.info("tmpDir: " + tmpDir);
      File tmpDirElimuAi = new File(tmpDir, "elimu-ai");
      log.info("tmpDirElimuAi: " + tmpDirElimuAi);
      log.info("tmpDirElimuAi.mkdir(): " + tmpDirElimuAi.mkdir());
      File apkFile = new File(tmpDirElimuAi, application.getPackageName() + ".apk");
      log.info("apkFile.getPath(): " + apkFile.getPath());
      FileUtils.copyURLToFile(new URL(fileUrl), apkFile);
      log.info("apkFile.exists(): " + apkFile.exists());

      byte[] bytes = IOUtils.toByteArray(apkFile.toURI());

      applicationVersion.setContentType("application/vnd.android.package-archive");

      Integer fileSizeInKb = bytes.length / 1_024;
      log.info("fileSizeInKb: " + fileSizeInKb);
      applicationVersion.setFileSizeInKb(fileSizeInKb);

      String checksumMd5 = ChecksumHelper.calculateMD5(bytes);
      log.info("checksumMd5: " + checksumMd5);
      applicationVersion.setChecksumMd5(checksumMd5);

      // Extract metadata from the APK file
      ByteArrayApkFile byteArrayApkFile = new ByteArrayApkFile(bytes);
      ApkMeta apkMeta = byteArrayApkFile.getApkMeta();
      byte[] icon = byteArrayApkFile.getIconFile().getData();
      byteArrayApkFile.close();
      
      String packageName = apkMeta.getPackageName();
      log.info("packageName: " + packageName);
      if (!packageName.equals(application.getPackageName())) {
        result.reject("packageName.mismatch");
      }

      Integer versionCode = apkMeta.getVersionCode().intValue();
      log.info("versionCode: " + versionCode);

      // Verify that the versionCode is higher than previous ones
      List<ApplicationVersion> existingApplicationVersions = applicationVersionDao.readAll(application);
      for (ApplicationVersion existingApplicationVersion : existingApplicationVersions) {
        if (existingApplicationVersion.getVersionCode() >= versionCode) {
          result.rejectValue("versionCode", "TooLow");
          break;
        }
      }
      applicationVersion.setVersionCode(versionCode);

      String versionName = apkMeta.getVersionName();
      log.info("versionName: " + versionName);
      applicationVersion.setVersionName(versionName);

      String label = apkMeta.getLabel();
      log.info("label: " + label);
      applicationVersion.setLabel(label);

      Integer minSdkVersion = Integer.valueOf(apkMeta.getMinSdkVersion());
      log.info("minSdkVersion: " + minSdkVersion);
      if (minSdkVersion < MIN_SDK_VERSION) {
        result.reject("sdkVersion.TooLow");
      }
      applicationVersion.setMinSdkVersion(minSdkVersion);

      log.info("icon.length: " + icon.length);
      applicationVersion.setIcon(icon);
    } catch (IOException e) {
      log.error(null, e);
    }

    if (result.hasErrors()) {
      model.addAttribute("applicationVersion", applicationVersion);
      return "application/application-version/create";
    } else {
      Contributor contributor = (Contributor) session.getAttribute("contributor");
      applicationVersion.setContributor(contributor);
      applicationVersion.setTimeUploaded(Calendar.getInstance());
      applicationVersionDao.create(applicationVersion);

      // If first APK file, change status of application to "ACTIVE"
      if (application.getApplicationStatus() == ApplicationStatus.MISSING_APK) {
        application.setApplicationStatus(ApplicationStatus.ACTIVE);
        applicationDao.update(application);
      }

      String contentUrl = "http://" + ConfigHelper.getProperty("content.language").toLowerCase() + ".elimu.ai/application/edit/" + application.getId();
      DiscordHelper.sendChannelMessage(
          "A new Application version (`.apk`) was published: " + contentUrl,
          application.getPackageName(),
          "Version: `" + applicationVersion.getVersionName() + "`",
          null,
          null
      );

      return "redirect:/application/edit/" + application.getId() + "#versions";
    }
  }
}
