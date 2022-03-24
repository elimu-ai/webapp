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

public class EPubImageExtractionHelper {
    
    private static final Logger logger = LogManager.getLogger();

    /**
     * Expected file format (GLOBAL_DIGITAL_LIBRARY):
     * <pre>
     *     
     *     <body><img src="21f0ca572d1f21c4813bfb910ccb935d.jpg" />
     *     <p>
     *      Fifth grade student, Little Miss Grace,
     *     </p>
     *     <p>
     *      was totally fascinated by outer space .
     *     </p></body>
     *         
     * </pre>
     * <p />
     * 
     * Expected file format (LETS_READ_ASIA):
     * <pre>
     *     
     *     <body>
     *         <div class="container">
     *             <img src = 'image_3.jpg' />
     *         </div>
     *         <p dir="auto">WAAAAHHHH!<br/><br/>Ang ibong Brahminy ay umiiyak tulad ng isang gutom na sanggol.<br/><br/>WAAAAHHHH!</p>
     *     </body>
     *         
     * </pre>
     * 
     * Expected file format (STORYWEAVER):
     * <pre>
     * 
     *     <body id="story_epub_body">
     *         <div id="story_epub">
     *           <div id="storyReader" class="bengali">
     *             <div
     *       id="selected_page"
     *       class=" page-container-landscape story-page">
     *       <div class='sp_h_iT66_cB33 has_illustration illustration'>
     *           <img class='responsive_illustration'
     *             data-size1-src="https://storage.googleapis.com/story-weaver-e2e-production/illustration_crops/4094/size1/a1f14ac7a1ac0484043689848e2d42b8.jpg"
     *             data-size2-src="https://storage.googleapis.com/story-weaver-e2e-production/illustration_crops/4094/size2/a1f14ac7a1ac0484043689848e2d42b8.jpg"
     *             data-size3-src="https://storage.googleapis.com/story-weaver-e2e-production/illustration_crops/4094/size3/a1f14ac7a1ac0484043689848e2d42b8.jpg"
     *             data-size4-src="https://storage.googleapis.com/story-weaver-e2e-production/illustration_crops/4094/size4/a1f14ac7a1ac0484043689848e2d42b8.jpg"
     *             data-size5-src="https://storage.googleapis.com/story-weaver-e2e-production/illustration_crops/4094/size5/a1f14ac7a1ac0484043689848e2d42b8.jpg"
     *             data-size6-src="https://storage.googleapis.com/story-weaver-e2e-production/illustration_crops/4094/size6/a1f14ac7a1ac0484043689848e2d42b8.jpg"
     *             data-size7-src="https://storage.googleapis.com/story-weaver-e2e-production/illustration_crops/4094/size7/a1f14ac7a1ac0484043689848e2d42b8.jpg"
     *               src="image_2.jpg"
     *           />
     *       </div>
     *         <div class='text-font-normal sp_h_iT66_cB33 content ' dir="auto"><p>
     *     
     *     ভীমের
     *     শুধু ঘুম আর ঘুম। সকালে উঠতেই পারে না।</p>
     *     
     *     <p><br/></p><p>ধোপা
     *     রামু সুযোগ পেলেই ভীমকে বকা দেয়। </p>
     *     
     *     <p></p></div>
     *     <div class="page_number">2</div>
     *     
     *     </div>
     *     
     *           </div>
     *         </div>
     *     </body>
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
                        
                        // Look for "<div class="container">" (StoryBookProvider#LETS_READ_ASIA)
                        if ("div".equals(bodyChildNode.getNodeName())) {
                            NamedNodeMap itemAttributes = bodyChildNode.getAttributes();
                            logger.info("itemAttributes (LETS_READ_ASIA): " + itemAttributes);
                            
                            Node classAttributeNode = itemAttributes.getNamedItem("class");
                            logger.info("classAttributeNode (LETS_READ_ASIA): " + classAttributeNode);
                            if ((classAttributeNode != null) && "container".equals(classAttributeNode.getNodeValue())) {
                                NodeList containerDivChildNodeList = bodyChildNode.getChildNodes();
                                logger.info("containerDivChildNodeList.getLength() (LETS_READ_ASIA): " + containerDivChildNodeList.getLength());
                                for (int k = 0; k < containerDivChildNodeList.getLength(); k++) {
                                    Node containerDivChildNode = containerDivChildNodeList.item(k);
                                    logger.info("containerDivChildNode (LETS_READ_ASIA): " + containerDivChildNode);
                                    logger.info("containerDivChildNode.getTextContent() (LETS_READ_ASIA): \"" + containerDivChildNode.getTextContent() + "\"");

                                    // Look for "<img>"
                                    if ("img".equals(containerDivChildNode.getNodeName())) {
                                        // Expected format: <img src = 'image_3.jpg' />
                                        
                                        NamedNodeMap imageAttributes = containerDivChildNode.getAttributes();
                                        logger.info("imageAttributes (LETS_READ_ASIA): " + imageAttributes);

                                        Node srcAttributeNode = imageAttributes.getNamedItem("src");
                                        logger.info("srcAttributeNode (LETS_READ_ASIA): " + srcAttributeNode);
                                        return srcAttributeNode.getNodeValue();
                                    }
                                }
                            }
                        }
                        
                        // Look for images within `<div lang="en">` (StoryBookProvider#LETS_READ_ASIA)
                        // Expected format:
                        /*
                            <div lang="en">
                                <div class="container">
                                    <img src="p-1.jpg" alt=""/>
                                </div>
                                <p dir="auto">The moon rises.</p>
                            </div>
                        */
                        if ("div".equals(bodyChildNode.getNodeName()) && (bodyChildNode.getAttributes().getNamedItem("lang") != null)) {
                            NodeList langDivChildNodeList = bodyChildNode.getChildNodes();
                            logger.info("langDivChildNodeList: " + langDivChildNodeList);
                            for (int k = 0; k < langDivChildNodeList.getLength(); k++) {
                                Node langDivChildNode = langDivChildNodeList.item(k);
                                logger.info("langDivChildNode: " + langDivChildNode);
                                
                                // Look for "<div class="container">" (StoryBookProvider#LETS_READ_ASIA)
                                if ("div".equals(langDivChildNode.getNodeName())) {
                                    NamedNodeMap itemAttributes = langDivChildNode.getAttributes();
                                    logger.info("itemAttributes (LETS_READ_ASIA): " + itemAttributes);

                                    Node classAttributeNode = itemAttributes.getNamedItem("class");
                                    logger.info("classAttributeNode (LETS_READ_ASIA): " + classAttributeNode);
                                    if ((classAttributeNode != null) && "container".equals(classAttributeNode.getNodeValue())) {
                                        NodeList containerDivChildNodeList = langDivChildNode.getChildNodes();
                                        logger.info("containerDivChildNodeList.getLength() (LETS_READ_ASIA): " + containerDivChildNodeList.getLength());
                                        for (int l = 0; l < containerDivChildNodeList.getLength(); l++) {
                                            Node containerDivChildNode = containerDivChildNodeList.item(l);
                                            logger.info("containerDivChildNode (LETS_READ_ASIA): " + containerDivChildNode);
                                            logger.info("containerDivChildNode.getTextContent() (LETS_READ_ASIA): \"" + containerDivChildNode.getTextContent() + "\"");

                                            // Look for "<img>"
                                            if ("img".equals(containerDivChildNode.getNodeName())) {
                                                // Expected format: <img src = 'image_3.jpg' />

                                                NamedNodeMap imageAttributes = containerDivChildNode.getAttributes();
                                                logger.info("imageAttributes (LETS_READ_ASIA): " + imageAttributes);

                                                Node srcAttributeNode = imageAttributes.getNamedItem("src");
                                                logger.info("srcAttributeNode (LETS_READ_ASIA): " + srcAttributeNode);
                                                return srcAttributeNode.getNodeValue();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        // Look for "<div id="story_epub">" (StoryBookProvider#STORYWEAVER)
                        if ("div".equals(bodyChildNode.getNodeName())) {
                            if ((bodyChildNode.getAttributes().getNamedItem("id") != null) 
                                    && "story_epub".equals(bodyChildNode.getAttributes().getNamedItem("id").getNodeValue())) {
                                NodeList storyEpubDivChildNodeList = bodyChildNode.getChildNodes();
                                logger.info("storyEpubDivChildNodeList.getLength() (STORYWEAVER): " + storyEpubDivChildNodeList.getLength());
                                for (int k = 0; k < storyEpubDivChildNodeList.getLength(); k++) {
                                    Node storyEpubDivChildNode = storyEpubDivChildNodeList.item(k);
                                    logger.info("storyEpubDivChildNode (STORYWEAVER): " + storyEpubDivChildNode);
                                    
                                    // Look for "<div id="storyReader" class="bengali">"
                                    if ("div".equals(storyEpubDivChildNode.getNodeName())) {
                                        if ((storyEpubDivChildNode.getAttributes().getNamedItem("id") != null) 
                                                && "storyReader".equals(storyEpubDivChildNode.getAttributes().getNamedItem("id").getNodeValue())) {
                                            NodeList storyReaderDivChildNodeList = storyEpubDivChildNode.getChildNodes();
                                            logger.info("storyReaderDivChildNodeList.getLength() (STORYWEAVER): " + storyReaderDivChildNodeList.getLength());
                                            for (int l = 0; l < storyReaderDivChildNodeList.getLength(); l++) {
                                                Node storyReaderDivChildNode = storyReaderDivChildNodeList.item(l);
                                                logger.info("storyReaderDivChildNode (STORYWEAVER): " + storyReaderDivChildNode);
                                                
                                                // Look for "<div id="selected_page" class=" page-container-landscape story-page">"
                                                if ("div".equals(storyReaderDivChildNode.getNodeName())) {
                                                    if ((storyReaderDivChildNode.getAttributes().getNamedItem("id") != null) 
                                                            && "selected_page".equals(storyReaderDivChildNode.getAttributes().getNamedItem("id").getNodeValue())) {
                                                        NodeList selectedPageChildNodeList = storyReaderDivChildNode.getChildNodes();
                                                        logger.info("selectedPageChildNodeList.getLength() (STORYWEAVER): " + selectedPageChildNodeList.getLength());
                                                        for (int m = 0; m < selectedPageChildNodeList.getLength(); m++) {
                                                            Node selectedPageChildNode = selectedPageChildNodeList.item(m);
                                                            logger.info("selectedPageChildNode (STORYWEAVER): " + selectedPageChildNode);
                                                            
                                                            // Look for "<div class='sp_h_iT66_cB33 has_illustration illustration'>"
                                                            if ("div".equals(selectedPageChildNode.getNodeName())) {
                                                                if ((selectedPageChildNode.getAttributes().getNamedItem("class") != null) 
                                                                        && selectedPageChildNode.getAttributes().getNamedItem("class").getNodeValue().contains("illustration")) {
                                                                    NodeList illustrationChildNodeList = selectedPageChildNode.getChildNodes();
                                                                    logger.info("illustrationChildNodeList.getLength() (STORYWEAVER): " + illustrationChildNodeList.getLength());
                                                                    for (int n = 0; n < illustrationChildNodeList.getLength(); n++) {
                                                                        Node illustrationChildNode = illustrationChildNodeList.item(n);
                                                                        logger.info("illustrationChildNode (STORYWEAVER): " + illustrationChildNode);
                                                                        
                                                                        // Look for "<img class='responsive_illustration' />
                                                                        if ("img".equals(illustrationChildNode.getNodeName())) {
                                                                            NamedNodeMap imageAttributes = illustrationChildNode.getAttributes();
                                                                            logger.info("imageAttributes (STORYWEAVER): " + imageAttributes);

                                                                            Node srcAttributeNode = imageAttributes.getNamedItem("src");
                                                                            logger.info("srcAttributeNode (STORYWEAVER): " + srcAttributeNode);
                                                                            return srcAttributeNode.getNodeValue();
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
            logger.error(ex);
        }
        
        return null;
    }
}
