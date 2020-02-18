package ai.elimu.util.epub;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EPubMetadataExtractionHelper {
    
    private static final Logger logger = Logger.getLogger(EPubMetadataExtractionHelper.class);
    
    public static String extractTitleFromOpfFile(File opfFile) {
        logger.info("extractTitleFromOpfFile");
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(opfFile);
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            logger.info("nodeList.getLength(): " + nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                logger.info("node: " + node);
                
                // Extract metadata
                if ("metadata".equals(node.getNodeName())) {
                    NodeList metadataNodeList = node.getChildNodes();
                    logger.info("metadataNodeList.getLength(): " + metadataNodeList.getLength());
                    for (int j = 0; j < metadataNodeList.getLength(); j++) {
                        Node metadataNode = metadataNodeList.item(j);
                        logger.info("metadataNode: " + metadataNode);
                        
                        if ("dc:title".equals(metadataNode.getNodeName())) {
                            return metadataNode.getTextContent();
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            logger.error(null, ex);
        }
        
        return null;
    }
    
    public static String extractDescriptionFromOpfFile(File opfFile) {
        logger.info("extractDescriptionFromOpfFile");
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(opfFile);
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            logger.info("nodeList.getLength(): " + nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                logger.info("node: " + node);
                
                // Extract metadata
                if ("metadata".equals(node.getNodeName())) {
                    NodeList metadataNodeList = node.getChildNodes();
                    logger.info("metadataNodeList.getLength(): " + metadataNodeList.getLength());
                    for (int j = 0; j < metadataNodeList.getLength(); j++) {
                        Node metadataNode = metadataNodeList.item(j);
                        logger.info("metadataNode: " + metadataNode);
                        
                        if ("dc:description".equals(metadataNode.getNodeName())) {
                            return metadataNode.getTextContent();
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            logger.error(null, ex);
        }
        
        return null;
    }
}
