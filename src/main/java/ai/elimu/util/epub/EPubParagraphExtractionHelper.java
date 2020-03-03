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
                        
                        // Look for "<p>" (StoryBookProvider#GLOBAL_DIGITAL_LIBRARY)
                        if ("p".equals(bodyChildNode.getNodeName())) {
                            // Look for text content within the paragraph
                            NodeList paragraphChildNodeList = bodyChildNode.getChildNodes();
                            logger.info("paragraphChildNodeList.getLength(): " + paragraphChildNodeList.getLength());
                            
                            String paragraph = "";
                            String previousNodeName = null;
                            for (int k = 0; k < paragraphChildNodeList.getLength(); k++) {
                                Node paragraphChildNode = paragraphChildNodeList.item(k);
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
                        
                        // Look for "<div>" (StoryBookProvider#STORYWEAVER)
                        if ("div".equals(bodyChildNode.getNodeName())) {
                            Node bodyChildNodeIdAttribute = bodyChildNode.getAttributes().getNamedItem("id");
                            logger.info("bodyChildNodeIdAttribute: " + bodyChildNodeIdAttribute);
                            
                            // Expected format: <div id="story_epub">
                            if ((bodyChildNodeIdAttribute != null) && "story_epub".equals(bodyChildNodeIdAttribute.getNodeValue())) {
                                NodeList storyEpubDivChildNodeList = bodyChildNode.getChildNodes();
                                logger.info("storyEpubDivChildNodeList: " + storyEpubDivChildNodeList);
                                for (int k = 0; k < storyEpubDivChildNodeList.getLength(); k++) {
                                    Node storyEpubDivChildNode = storyEpubDivChildNodeList.item(k);
                                    logger.info("storyEpubDivChildNode: " + storyEpubDivChildNode);
                                    
                                    // Expected format: <div id="storyReader" class="bengali">
                                    if ("div".equals(storyEpubDivChildNode.getNodeName())) {
                                        Node storyEpubDivChildNodeIdAttribute = storyEpubDivChildNode.getAttributes().getNamedItem("id");
                                        logger.info("storyEpubDivChildNodeIdAttribute: " + storyEpubDivChildNodeIdAttribute);
                                        if ((storyEpubDivChildNodeIdAttribute != null) && "storyReader".equals(storyEpubDivChildNodeIdAttribute.getNodeValue())) {
                                            NodeList storyReaderDivChildNodeList = storyEpubDivChildNode.getChildNodes();
                                            logger.info("storyReaderDivChildNodeList: " + storyReaderDivChildNodeList);
                                            for (int l = 0; l < storyReaderDivChildNodeList.getLength(); l++) {
                                                Node storyReaderDivChildNode = storyReaderDivChildNodeList.item(l);
                                                logger.info("storyReaderDivChildNode: " + storyReaderDivChildNode);
                                                        
                                                // Expected format: <div id="selected_page" class=" page-container-landscape story-page">
                                                if ("div".equals(storyReaderDivChildNode.getNodeName())) {
                                                    Node storyReaderDivChildNodeIdAttribute = storyReaderDivChildNode.getAttributes().getNamedItem("id");
                                                    logger.info("storyReaderDivChildNodeIdAttribute: " + storyReaderDivChildNodeIdAttribute);
                                                    if ((storyReaderDivChildNodeIdAttribute != null) && "selected_page".equals(storyReaderDivChildNodeIdAttribute.getNodeValue())) {
                                                        NodeList selectedPageDivChildNodeList = storyReaderDivChildNode.getChildNodes();
                                                        logger.info("selectedPageDivChildNodeList: " + selectedPageDivChildNodeList);
                                                        for (int m = 0; m < selectedPageDivChildNodeList.getLength(); m++) {
                                                            Node selectedPageDivChildNode = selectedPageDivChildNodeList.item(m);
                                                            logger.info("selectedPageDivChildNode: " + selectedPageDivChildNode);
                                                            
                                                            // Expected format: <div class='text-font-normal sp_h_iT66_cB33 content ' dir="auto">
                                                            if ("div".equals(selectedPageDivChildNode.getNodeName())) {
                                                                Node selectedPageDivChildNodeClassAttribute = selectedPageDivChildNode.getAttributes().getNamedItem("class");
                                                                logger.info("selectedPageDivChildNodeClassAttribute: " + selectedPageDivChildNodeClassAttribute);
                                                                if ((selectedPageDivChildNodeClassAttribute != null) 
                                                                        && selectedPageDivChildNodeClassAttribute.getNodeValue().contains("content")) {
                                                                    NodeList contentDivChildNodeList = selectedPageDivChildNode.getChildNodes();
                                                                    logger.info("contentDivChildNodeList: " + contentDivChildNodeList);
                                                                    for (int n = 0; n < contentDivChildNodeList.getLength(); n++) {
                                                                        Node contentDivChildNode = contentDivChildNodeList.item(n);
                                                                        logger.info("contentDivChildNode : " + contentDivChildNode);
                                                                        
                                                                        // Expected format: <p>ভীমের শুধু ঘুম আর ঘুম। সকালে উঠতেই পারে না।</p>
                                                                        if ("p".equals(contentDivChildNode.getNodeName())) {
                                                                            String paragraph = contentDivChildNode.getTextContent();
                                                                            logger.info("paragraph: \"" + paragraph + "\"");
                                                                            if (StringUtils.isNotBlank(paragraph)) {
                                                                                paragraphs.add(paragraph);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
        
        return paragraphs;
    }
}
