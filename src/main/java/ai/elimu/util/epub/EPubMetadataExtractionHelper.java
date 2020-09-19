package ai.elimu.util.epub;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EPubMetadataExtractionHelper {
    
    private static final Logger logger = LogManager.getLogger(EPubMetadataExtractionHelper.class);
    
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
    
    public static String extractCoverImageReferenceFromOpfFile(File opfFile) {
        logger.info("extractCoverImageReferenceFromOpfFile");
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(opfFile);
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            logger.info("nodeList.getLength(): " + nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                logger.info("node: " + node);
                
                // Extract manifest
                if ("manifest".equals(node.getNodeName())) {
                    NodeList manifestNodeList = node.getChildNodes();
                    logger.info("manifestNodeList.getLength(): " + manifestNodeList.getLength());
                    for (int j = 0; j < manifestNodeList.getLength(); j++) {
                        Node manifestChildNode = manifestNodeList.item(j);
                        logger.info("manifestChildNode: " + manifestChildNode);
                        
                        // Look for "<item>"
                        if ("item".equals(manifestChildNode.getNodeName())) {
                            NamedNodeMap itemAttributes = manifestChildNode.getAttributes();
                            logger.info("itemAttributes: " + itemAttributes);
                            
                            Node idAttributeNode = itemAttributes.getNamedItem("id");
                            logger.info("idAttributeNode: " + idAttributeNode);
                            
                            Node hrefAttributeNode = itemAttributes.getNamedItem("href");
                            logger.info("hrefAttributeNode: " + hrefAttributeNode);
                            
                            // Expected format (Global Digital Library): 
                            // <item href="99e3d3af620881991813482fb602a1f6.jpg" id="cover" media-type="image/jpeg" properties="cover-image" />
                            Node propertiesAttributeNode = itemAttributes.getNamedItem("properties");
                            logger.info("propertiesAttributeNode: " + propertiesAttributeNode);
                            if ((propertiesAttributeNode != null) && "cover-image".equals(propertiesAttributeNode.getNodeValue())) {
                                logger.info("hrefAttributeNode.getNodeValue(): \"" + hrefAttributeNode.getNodeValue() + "\"");
                                return hrefAttributeNode.getNodeValue();
                            }
                            
                            // Expected format (Let's Read Asia): 
                            // <item id="image_1" href="coverImage.jpeg" media-type="image/jpeg" />
                            if (hrefAttributeNode.getNodeValue().toLowerCase().contains("cover")) {
                                logger.info("hrefAttributeNode.getNodeValue(): \"" + hrefAttributeNode.getNodeValue() + "\"");
                                return hrefAttributeNode.getNodeValue();
                            }
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
