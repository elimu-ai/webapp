package ai.elimu.util.epub;

import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ai.elimu.entity.enums.StoryBookProvider;

@Slf4j
public class EPubImageExtractionHelper {
    
    /**
     * Examples of the expected file format can be found at <code>src/test/resources/ai/elimu/util/epub/</code>
     * 
     * @param xhtmlFile The XHTML file containing the image, e.g. {@code image_4.jpg}.
     * @return An image file reference.
     */
    public static String extractImageReferenceFromChapterFile(File xhtmlFile) {
        log.info("extractImageReferenceFromChapterFile");

        try {
            String fileContent = new String(Files.readAllBytes(xhtmlFile.toPath()), StandardCharsets.UTF_8);
            fileContent = fileContent.replaceAll("&aacute;", "á");
            fileContent = fileContent.replaceAll("&acirc;", "â");
            fileContent = fileContent.replaceAll("&agrave;", "à");
            fileContent = fileContent.replaceAll("&igrave;", "ì");
            fileContent = fileContent.replaceAll("&nbsp;", " ");
            fileContent = fileContent.replaceAll("<br>", "<br/>");
            Files.write(xhtmlFile.toPath(), fileContent.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error(null, e);
        }
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xhtmlFile);
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            log.info("nodeList.getLength(): " + nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                log.info("node: " + node);
                
                // Look for "<body>"
                if ("body".equals(node.getNodeName())) {
                    NodeList bodyChildNodeList = node.getChildNodes();
                    log.info("bodyChildNodeList.getLength(): " + bodyChildNodeList.getLength());
                    for (int j = 0; j < bodyChildNodeList.getLength(); j++) {
                        Node bodyChildNode = bodyChildNodeList.item(j);
                        log.info("bodyChildNode: " + bodyChildNode);
                        log.info("bodyChildNode.getNodeName(): " + bodyChildNode.getNodeName());
                        log.info("bodyChildNode.getTextContent(): \"" + bodyChildNode.getTextContent() + "\"");
                        
                        // StoryBookProvider: GLOBAL_DIGITAL_LIBRARY
                        // Look for an image within `<body>`
                        // Expected format:
                        /*
                            <body><img src="1e8e58cc7d627a7896737cfb3eba8270.jpg" />
                            <p>
                             আজকে ছুটির দিন আনন্দে হারাই!
                             চলো সবে পোশাকের উৎসবে যাই!
                            </p>
                            </body>
                        */
                        if ("img".equals(bodyChildNode.getNodeName())) {
                            return getFileReferenceFromImageNode(StoryBookProvider.GLOBAL_DIGITAL_LIBRARY, bodyChildNode);
                        }
                        
                        // StoryBookProvider: LETS_READ_ASIA
                        // Look for an image within `<div class="container">`
                        // Expected format:
                        /*
                            <body>
                                <div class="container">
                                    <img src = 'p-16.jpg' />
                                </div>
                                <p dir="auto">"Nagmumuni-muni lang," sabi niya.</p>
                            </body>
                        */
                        if ("div".equals(bodyChildNode.getNodeName()) && (bodyChildNode.getAttributes().getNamedItem("class") != null)) {
                            NodeList containerDivChildNodeList = bodyChildNode.getChildNodes();
                            log.info("containerDivChildNodeList: " + containerDivChildNodeList);
                            for (int l = 0; l < containerDivChildNodeList.getLength(); l++) {
                                Node containerDivChildNode = containerDivChildNodeList.item(l);
                                if ("img".equals(containerDivChildNode.getNodeName())) {
                                    return getFileReferenceFromImageNode(StoryBookProvider.LETS_READ_ASIA, containerDivChildNode);
                                }
                            }
                        }
                        
                        // StoryBookProvider: LETS_READ_ASIA
                        // Look for an image within `<div lang="en">`
                        // Expected format:
                        /*
                            <body dir="auto">
                                <div lang="en">
                                    <div class="container">
                                        <img src="p-1.jpg" alt=""/>
                                    </div>
                                    <p dir="auto">The moon rises.</p>
                                </div>
                            </body>
                        */
                        if ("div".equals(bodyChildNode.getNodeName()) && (bodyChildNode.getAttributes().getNamedItem("lang") != null)) {
                            NodeList langDivChildNodeList = bodyChildNode.getChildNodes();
                            log.info("langDivChildNodeList: " + langDivChildNodeList);
                            for (int k = 0; k < langDivChildNodeList.getLength(); k++) {
                                Node langDivChildNode = langDivChildNodeList.item(k);
                                log.info("langDivChildNode: " + langDivChildNode);
                                
                                // Look for `<div class="container">`
                                if ("div".equals(langDivChildNode.getNodeName()) && (langDivChildNode.getAttributes().getNamedItem("class") != null)) {
                                    NodeList containerDivChildNodeList = langDivChildNode.getChildNodes();
                                    log.info("containerDivChildNodeList: " + containerDivChildNodeList);
                                    for (int l = 0; l < containerDivChildNodeList.getLength(); l++) {
                                        Node containerDivChildNode = containerDivChildNodeList.item(l);
                                        if ("img".equals(containerDivChildNode.getNodeName())) {
                                            return getFileReferenceFromImageNode(StoryBookProvider.LETS_READ_ASIA, containerDivChildNode);
                                        }
                                    }
                                }
                            }
                        }
                        
                        // StoryBookProvider: STORYWEAVER
                        // Look for an image within `<div class='sp_h_iT66_cB33 has_illustration illustration'>`
                        // Expected format:
                        /*
                            <body id="story_epub_body">
                                <div id="story_epub">
                                  <div id="storyReader" class="bengali">
                                    <div id="selected_page" class=" page-container-landscape story-page">
                                        <div class='sp_h_iT66_cB33 has_illustration illustration'>
                                            <img class='responsive_illustration' src="image_2.jpg" />
                                        </div>
                                        <div class='text-font-normal sp_h_iT66_cB33 content ' dir="auto">
                                            <p>
                                                ভীমের
                                                শুধু ঘুম আর ঘুম। সকালে উঠতেই পারে না।
                                            </p>
                                            <p>
                                                <br/></p><p>ধোপা
                                                রামু সুযোগ পেলেই ভীমকে বকা দেয়। 
                                            </p>
                                        </div>
                                        <div class="page_number">
                                            2
                                        </div>
                                    </div>
                                  </div>
                                </div>
                            </body>
                        */
                        if ("div".equals(bodyChildNode.getNodeName())) {
                            if ((bodyChildNode.getAttributes().getNamedItem("id") != null) 
                                    && "story_epub".equals(bodyChildNode.getAttributes().getNamedItem("id").getNodeValue())) {
                                NodeList storyEpubDivChildNodeList = bodyChildNode.getChildNodes();
                                log.info("storyEpubDivChildNodeList.getLength() (STORYWEAVER): " + storyEpubDivChildNodeList.getLength());
                                for (int k = 0; k < storyEpubDivChildNodeList.getLength(); k++) {
                                    Node storyEpubDivChildNode = storyEpubDivChildNodeList.item(k);
                                    log.info("storyEpubDivChildNode (STORYWEAVER): " + storyEpubDivChildNode);
                                    
                                    // Look for "<div id="storyReader" class="bengali">"
                                    if ("div".equals(storyEpubDivChildNode.getNodeName())) {
                                        if ((storyEpubDivChildNode.getAttributes().getNamedItem("id") != null) 
                                                && "storyReader".equals(storyEpubDivChildNode.getAttributes().getNamedItem("id").getNodeValue())) {
                                            NodeList storyReaderDivChildNodeList = storyEpubDivChildNode.getChildNodes();
                                            log.info("storyReaderDivChildNodeList.getLength() (STORYWEAVER): " + storyReaderDivChildNodeList.getLength());
                                            for (int l = 0; l < storyReaderDivChildNodeList.getLength(); l++) {
                                                Node storyReaderDivChildNode = storyReaderDivChildNodeList.item(l);
                                                log.info("storyReaderDivChildNode (STORYWEAVER): " + storyReaderDivChildNode);
                                                
                                                // Look for "<div id="selected_page" class=" page-container-landscape story-page">"
                                                if ("div".equals(storyReaderDivChildNode.getNodeName())) {
                                                    if ((storyReaderDivChildNode.getAttributes().getNamedItem("id") != null) 
                                                            && "selected_page".equals(storyReaderDivChildNode.getAttributes().getNamedItem("id").getNodeValue())) {
                                                        NodeList selectedPageChildNodeList = storyReaderDivChildNode.getChildNodes();
                                                        log.info("selectedPageChildNodeList.getLength() (STORYWEAVER): " + selectedPageChildNodeList.getLength());
                                                        for (int m = 0; m < selectedPageChildNodeList.getLength(); m++) {
                                                            Node selectedPageChildNode = selectedPageChildNodeList.item(m);
                                                            log.info("selectedPageChildNode (STORYWEAVER): " + selectedPageChildNode);
                                                            
                                                            // Look for "<div class='sp_h_iT66_cB33 has_illustration illustration'>"
                                                            if ("div".equals(selectedPageChildNode.getNodeName())) {
                                                                if ((selectedPageChildNode.getAttributes().getNamedItem("class") != null) 
                                                                        && selectedPageChildNode.getAttributes().getNamedItem("class").getNodeValue().contains("illustration")) {
                                                                    NodeList illustrationChildNodeList = selectedPageChildNode.getChildNodes();
                                                                    log.info("illustrationChildNodeList.getLength() (STORYWEAVER): " + illustrationChildNodeList.getLength());
                                                                    for (int n = 0; n < illustrationChildNodeList.getLength(); n++) {
                                                                        Node illustrationChildNode = illustrationChildNodeList.item(n);
                                                                        log.info("illustrationChildNode (STORYWEAVER): " + illustrationChildNode);
                                                                        
                                                                        // Look for "<img class='responsive_illustration' />
                                                                        if ("img".equals(illustrationChildNode.getNodeName())) {
                                                                            return getFileReferenceFromImageNode(StoryBookProvider.STORYWEAVER, illustrationChildNode);
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
            log.error(ex.getMessage());
        }
        
        return null;
    }
    
    private static String getFileReferenceFromImageNode(StoryBookProvider storyBookProvider, Node imageNode) {
        log.info("getFileReferenceFromImageNode");
        
        log.info("storyBookProvider: " + storyBookProvider);
        
        NamedNodeMap itemAttributes = imageNode.getAttributes();
        log.info("itemAttributes: " + itemAttributes);

        Node srcAttributeNode = itemAttributes.getNamedItem("src");
        log.info("srcAttributeNode: " + srcAttributeNode);
        
        String imageSource = srcAttributeNode.getNodeValue();
        log.info("imageSource: \"" + imageSource + "\"");
        return imageSource;
    }
}
