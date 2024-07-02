package ai.elimu.web.content.multimedia.image;

import ai.elimu.dao.ImageDao;
import ai.elimu.model.content.multimedia.Image;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/image/list")
public class ImageCsvExportController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private ImageDao imageDao;
    
    @RequestMapping(value="/images.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream) throws IOException {
        logger.info("handleRequest");

        List<Image> images = imageDao.readAll();
        logger.info("images.size(): " + images.size());

        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id", 
                        "content_type", 
                        "content_license", 
                        "attribution_url", 
                        "title",
                        "download_url",
                        "image_format"
                );
        StringWriter stringWriter = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

        for (Image image : images) {
            String downloadUrl = "/image/" + image.getId() + "." + image.getImageFormat().toString().toLowerCase();
            
            csvPrinter.printRecord(
                image.getId(),
                image.getContentType(),
                image.getContentLicense(),
                image.getAttributionUrl(),
                image.getTitle(),
                downloadUrl,
                image.getImageFormat()
            );
            
            csvPrinter.flush();
        }

        String csvFileContent = stringWriter.toString();
        
        response.setContentType("text/csv");
        byte[] bytes = csvFileContent.getBytes();
        response.setContentLength(bytes.length);
        try {
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            logger.error(ex);
        }
    }
}
