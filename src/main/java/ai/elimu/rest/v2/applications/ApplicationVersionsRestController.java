package ai.elimu.rest.v2.applications;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.entity.application.Application;
import ai.elimu.entity.application.ApplicationVersion;
import ai.elimu.model.v2.enums.admin.ApplicationStatus;
import ai.elimu.util.ChecksumHelper;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.application.application_version.ApplicationVersionCreateController;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dongliu.apk.parser.ByteArrayApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;

import java.io.File;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
  value = "/rest/v2/applications/{packageName}/application-versions/{versionName}",
  consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
public class ApplicationVersionsRestController {

  private final ApplicationDao applicationDao;

  private final ApplicationVersionDao applicationVersionDao;

  @PutMapping
  public String handlePutRequest(
    @PathVariable String packageName,
    @PathVariable String versionName,
    @RequestBody String jsonData,
    HttpServletResponse response
  ) {
    log.info("handlePutRequest");

    log.info("packageName: " + packageName);
    log.info("versionName: " + versionName);
    log.info("jsonData: " + jsonData);

    JSONObject jsonResponseObject = new JSONObject();
    try {
      JSONObject jsonObject = new JSONObject(jsonData);
      log.info("jsonObject: " + jsonObject);

      if (!jsonObject.has("fileUrl")) {
        throw new IllegalArgumentException("fileUrl.missing");
      }
      
      String fileUrl = jsonObject.getString("fileUrl");
      log.info("fileUrl: " + fileUrl);
      if (StringUtils.isBlank(fileUrl)) {
        throw new IllegalArgumentException("fileUrl.empty");
      }

      // Perform the same steps as in the ApplicationVersionCreateController:

      Application application = applicationDao.readByPackageName(packageName);
      log.info("application: " + application);

      // Download the APK file to a temporary folder on the file system
      String tmpDir = System.getProperty("java.io.tmpdir");
      log.info("tmpDir: " + tmpDir);
      File tmpDirElimuAi = new File(tmpDir, "elimu-ai");
      log.info("tmpDirElimuAi: " + tmpDirElimuAi);
      log.info("tmpDirElimuAi.mkdir(): " + tmpDirElimuAi.mkdir());
      File apkFile = new File(tmpDirElimuAi, application.getPackageName() + "-" + versionName + ".apk");
      log.info("apkFile.getPath(): " + apkFile.getPath());
      FileUtils.copyURLToFile(new URL(fileUrl), apkFile);
      log.info("apkFile.exists(): " + apkFile.exists());

      byte[] bytes = IOUtils.toByteArray(apkFile.toURI());

      ApplicationVersion applicationVersion = new ApplicationVersion();
      applicationVersion.setApplication(application);
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
      
      log.info("apkMeta.getPackageName(): " + apkMeta.getPackageName());
      if (!apkMeta.getPackageName().equals(application.getPackageName())) {
        throw new IllegalArgumentException("packageName.mismatch");
      }

      Integer versionCode = apkMeta.getVersionCode().intValue();
      log.info("versionCode: " + versionCode);

      // Verify that the versionCode is higher than previous ones
      List<ApplicationVersion> existingApplicationVersions = applicationVersionDao.readAll(application);
      for (ApplicationVersion existingApplicationVersion : existingApplicationVersions) {
        if (existingApplicationVersion.getVersionCode() >= versionCode) {
          throw new IllegalArgumentException("versionCode.TooLow");
        }
      }
      applicationVersion.setVersionCode(versionCode);

      log.info("apkMeta.getVersionName(): " + apkMeta.getVersionName());
      if (!apkMeta.getVersionName().equals(versionName)) {
        throw new IllegalArgumentException("versionName.mismatch");
      }
      applicationVersion.setVersionName(apkMeta.getVersionName());

      String label = apkMeta.getLabel();
      log.info("label: " + label);
      applicationVersion.setLabel(label);

      Integer minSdkVersion = Integer.valueOf(apkMeta.getMinSdkVersion());
      log.info("minSdkVersion: " + minSdkVersion);
      if (minSdkVersion < ApplicationVersionCreateController.MIN_SDK_VERSION) {
        throw new IllegalArgumentException("sdkVersion.TooLow");
      }
      applicationVersion.setMinSdkVersion(minSdkVersion);

      log.info("icon.length: " + icon.length);
      applicationVersion.setIcon(icon);

      applicationVersion.setContributor(application.getContributor());
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

      jsonResponseObject.put("result", "success");
      jsonResponseObject.put("successMessage", "The application version was published with versionName " + applicationVersion.getVersionName());
      response.setStatus(HttpStatus.OK.value());
    } catch (Exception ex) {
        log.error(ex.getClass() + ": " + ex.getMessage(), ex);

        jsonResponseObject.put("result", "error");
        jsonResponseObject.put("errorMessage", ex.getClass() + ": " + ex.getMessage());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    String jsonResponse = jsonResponseObject.toString();
    log.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
