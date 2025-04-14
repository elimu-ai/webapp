package ai.elimu.web.content.multimedia.image;

import ai.elimu.dao.ImageDao;
import ai.elimu.entity.content.multimedia.Image;
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
@RequestMapping("/content/image/list/images.csv")
@RequiredArgsConstructor
@Slf4j
public class ImageCsvExportController {

  private final ImageDao imageDao;

  @GetMapping
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream) throws IOException {
    log.info("handleRequest");

    List<Image> images = imageDao.readAllOrderedById();
    log.info("images.size(): " + images.size());

    CSVFormat csvFormat = CSVFormat.DEFAULT
        .withHeader(
            "id",
            "content_type",
            "content_license",
            "attribution_url",
            "title",
            "checksum_md5",
            "cid",
            "download_url",
            "image_format"
        );
    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    for (Image image : images) {
      csvPrinter.printRecord(
          image.getId(),
          image.getContentType(),
          image.getContentLicense(),
          image.getAttributionUrl(),
          image.getTitle(),
          image.getChecksumMd5(),
          image.getCid(),
          image.getUrl(),
          image.getImageFormat()
      );

      csvPrinter.flush();
    }
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
