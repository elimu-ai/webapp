package ai.elimu.util.epub;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EPubImageExtractionHelper {
    
    private static final Logger logger = Logger.getLogger(EPubImageExtractionHelper.class);

    /**
     * Expected file format (GLOBAL_DIGITAL_LIBRARY):
     * <pre>
     *     
     *     <html xmlns="http://www.w3.org/1999/xhtml">
     *     <head>
     *         <title>Chapter 3</title>
     *         <link href="epub.css" rel="stylesheet" type="text/css"/>
     *     </head>
     *     <body><img src="21f0ca572d1f21c4813bfb910ccb935d.jpg" />
     *     <p>
     *      Fifth grade student, Little Miss Grace,
     *     </p>
     *     <p>
     *      was totally fascinated by outer space .
     *     </p></body>
     *     </html>
     *         
     * </pre>
     * <p />
     * 
     * Expected file format (LETS_READ_ASIA):
     * <pre>
     *     
     *     <?xml version="1.0" encoding="utf-8"?>
     *     <!DOCTYPE html>
     *     <html xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops" xml:lang="fil" lang="fil" dir="ltr">
     *     <head>
     *         <title>Narinig mo ba?</title>
     *         <meta charset="utf-8"/>
     *         <link rel="stylesheet" href="style.css" type="text/css" />
     *     
     *     </head>
     *     <body>
     *         <div class="container">
     *             <img src = 'image_3.jpg' />
     *         </div>
     *         <p dir="auto">WAAAAHHHH!<br/><br/>Ang ibong Brahminy ay umiiyak tulad ng isang gutom na sanggol.<br/><br/>WAAAAHHHH!</p>
     *     </body>
     *     </html>
     *         
     * </pre>
     * 
     * @param xhtmlFile The XHTML file containing the image, e.g. {@code chapter-2.xhtml}.
     * @return An image file reference.
     */
    public static String extractImageReferenceFromChapterFile(File xhtmlFile) {
        logger.info("extractImageReferenceFromChapterFile");
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xhtmlFile);
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            logger.info("nodeList.getLength(): " + nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                logger.info("node: " + node);
                
                // Look for "<body>"
                if ("body".equals(node.getNodeName())) {
                    NodeList bodyChildNodeList = node.getChildNodes();
                    logger.info("bodyChildNodeList.getLength(): " + bodyChildNodeList.getLength());
                    for (int j = 0; j < bodyChildNodeList.getLength(); j++) {
                        Node bodyChildNode = bodyChildNodeList.item(j);
                        logger.info("bodyChildNode: " + bodyChildNode);
                        logger.info("bodyChildNode.getTextContent(): \"" + bodyChildNode.getTextContent() + "\"");
                        
                        // Look for "<img>" (StoryBookProvider#GLOBAL_DIGITAL_LIBRARY)
                        if ("img".equals(bodyChildNode.getNodeName())) {
                            NamedNodeMap itemAttributes = bodyChildNode.getAttributes();
                            logger.info("itemAttributes: " + itemAttributes);
                            
                            Node srcAttributeNode = itemAttributes.getNamedItem("src");
                            logger.info("srcAttributeNode: " + srcAttributeNode);
                            return srcAttributeNode.getNodeValue();
                        }
                        
                        // Look for "<div>" (StoryBookProvider#LETS_READ_ASIA)
                        if ("div".equals(bodyChildNode.getNodeName())) {
                            // Expected format: <div class="container">
                            NamedNodeMap itemAttributes = bodyChildNode.getAttributes();
                            logger.info("itemAttributes: " + itemAttributes);
                            
                            Node classAttributeNode = itemAttributes.getNamedItem("class");
                            logger.info("classAttributeNode: " + classAttributeNode);
                            if ((classAttributeNode != null) && "container".equals(classAttributeNode.getNodeValue())) {
                                NodeList containerDivChildNodeList = bodyChildNode.getChildNodes();
                                logger.info("containerDivChildNodeList.getLength(): " + containerDivChildNodeList.getLength());
                                for (int k = 0; k < containerDivChildNodeList.getLength(); k++) {
                                    Node containerDivChildNode = containerDivChildNodeList.item(k);
                                    logger.info("containerDivChildNode: " + containerDivChildNode);
                                    logger.info("containerDivChildNode.getTextContent(): \"" + containerDivChildNode.getTextContent() + "\"");

                                    // Look for "<img>"
                                    if ("img".equals(containerDivChildNode.getNodeName())) {
                                        // Expected format: <img src = 'image_3.jpg' />
                                        
                                        NamedNodeMap imageAttributes = containerDivChildNode.getAttributes();
                                        logger.info("imageAttributes: " + imageAttributes);

                                        Node srcAttributeNode = imageAttributes.getNamedItem("src");
                                        logger.info("srcAttributeNode: " + srcAttributeNode);
                                        return srcAttributeNode.getNodeValue();
                                    }
                                }
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
