package ai.elimu.web.content.multimedia.video;

import ai.elimu.dao.VideoDao;
import ai.elimu.entity.content.multimedia.Video;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
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
      OutputStream outputStream
  ) throws IOException {
    log.info("handleRequest");

    List<Video> videos = videoDao.readAllOrderedById();
    log.info("videos.size(): " + videos.size());

    CSVFormat csvFormat = CSVFormat.DEFAULT
        .withHeader(
            "id",
            "content_type",
            "content_license",
            "attribution_url",
            "title",
            "checksum_md5",
            "file_url",
            "file_size",
            "video_format"
        );
    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    for (Video video : videos) {
      csvPrinter.printRecord(
          video.getId(),
          video.getContentType(),
          video.getContentLicense(),
          video.getAttributionUrl(),
          video.getTitle(),
          video.getChecksumMd5(),
          video.getUrl(),
          video.getFileSize(),
          video.getVideoFormat()
      );
    }
    csvPrinter.flush();
    csvPrinter.close();

    String csvFileContent = stringWriter.toString();

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
