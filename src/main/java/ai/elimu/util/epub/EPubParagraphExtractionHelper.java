package ai.elimu.util.epub;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EPubParagraphExtractionHelper {
    
    private static final Logger logger = Logger.getLogger(EPubParagraphExtractionHelper.class);

    /**
     * Expected file format:
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
     * <pre>
     *     
     *     <html xmlns="http://www.w3.org/1999/xhtml">
     *     <head>
     *         <title>Chapter 2</title>
     *         <link href="epub.css" rel="stylesheet" type="text/css"/>
     *     </head>
     *     <body><img src="9822eebad600b3ae74537c05c786208a.jpg" />
     *     <br />
     *     Kuku na Jongoo walikuwa marafiki.
     *     </body>
     *     </html>
     *         
     *         
     * </pre>
     * 
     * @param xhtmlFile The XHTML file containing the paragraphs, e.g. {@code chapter-2.xhtml}.
     * @return A list of paragraphs.
     */
    public static List<String> extractParagraphsFromChapterFile(File xhtmlFile) {
        logger.info("extractParagraphsFromChapter");
        
        List<String> paragraphs = new ArrayList<>();
        
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
                        
                        // Look for "<img>"
                        // TODO
                        
                        // Look for "<p>"
                        if ("p".equals(bodyChildNode.getNodeName())) {
                            // Look for text content within the paragraph
                            NodeList paragraphNodeList = bodyChildNode.getChildNodes();
                            logger.info("paragraphNodeList.getLength(): " + paragraphNodeList.getLength());
                            
                            String paragraph = "";
                            String previousNodeName = null;
                            for (int k = 0; k < paragraphNodeList.getLength(); k++) {
                                Node paragraphChildNode = paragraphNodeList.item(k);
                                logger.info("paragraphChildNode: " + paragraphChildNode);
                                
                                
                                if ("#text".equals(paragraphChildNode.getNodeName())) {
                                    // Add whitespace between each sentence
                                    // E.g. "WAAAAHHHH!" --> "WAAAAHHHH! "
                                    if (StringUtils.isNotBlank(paragraph)) {
                                        paragraph += " ";
                                    }
                                    paragraph += paragraphChildNode.getTextContent();
                                } else if ("br".equals(paragraphChildNode.getNodeName())) {
                                    // Handle double linebreaks within paragraphs
                                    // E.g. "<p>WAAAAHHHH!<br/><br/>Ang ibong Brahminy...</p>" --> "<p>WAAAAHHHH!</p><p>Ang ibong Brahminy...</p>"
                                    if ("br".equals(previousNodeName)) {
                                        // Replace <br/><br/> with </p><p>
                                        logger.info("paragraph: \"" + paragraph + "\"");
                                        if (StringUtils.isNotBlank(paragraph)) {
                                            paragraphs.add(paragraph);
                                            
                                            // Reset to initial state
                                            paragraph = "";
                                        }
                                    }
                                }
                                
                                // Keep track if the previous node name
                                previousNodeName = paragraphChildNode.getNodeName();
                            }
                            logger.info("paragraph: \"" + paragraph + "\"");
                            if (StringUtils.isNotBlank(paragraph)) {
                                paragraphs.add(paragraph);
                            }
                        }
                        
                        // Look for text content
                        // In some cases, <p> tags are missing, and "#text" is the node name
                        if ("#text".equals(bodyChildNode.getNodeName())) {
                            String paragraph = bodyChildNode.getTextContent();
                            logger.info("paragraph: \"" + paragraph + "\"");
                            if (StringUtils.isNotBlank(paragraph)) {
                                paragraphs.add(paragraph);
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            logger.error(null, ex);
        }
        
        return paragraphs;
    }
}
