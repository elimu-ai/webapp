package ai.elimu.web.content.multimedia.video;

import ai.elimu.dao.VideoDao;
import ai.elimu.entity.content.multimedia.Video;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/content/video/list/videos.csv")
@RequiredArgsConstructor
@Slf4j
public class VideoCsvExportController {

  private final VideoDao videoDao;

  @GetMapping
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream) {
    log.info("handleRequest");

    // Generate CSV file
    String csvFileContent = "id,content_type,content_license,attribution_url,title,download_url,video_format" + "\n";
    List<Video> videos = videoDao.readAllOrderedById();
    log.info("videos.size(): " + videos.size());
    for (Video video : videos) {
      String downloadUrl = "/video/" + video.getId() + "." + video.getVideoFormat().toString().toLowerCase();
      csvFileContent += video.getId() + ","
          + video.getContentType() + ","
          + video.getContentLicense() + ","
          + "\"" + video.getAttributionUrl() + "\","
          + "\"" + video.getTitle() + "\","
          + "\"" + downloadUrl + "\","
          + video.getVideoFormat() + "\n";
    }

    response.setContentType("text/csv");
    byte[] bytes = csvFileContent.getBytes();
    response.setContentLength(bytes.length);
    try {
      outputStream.write(bytes);
      outputStream.flush();
      outputStream.close();
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }
  }
}
