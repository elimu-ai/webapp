package ai.elimu.util.epub;

import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ai.elimu.entity.enums.StoryBookProvider;

@Slf4j
public class EPubParagraphExtractionHelper {

    /**
     * Examples of the expected file format can be found at <code>src/test/resources/ai/elimu/util/epub/</code>
     * 
     * @param xhtmlFile The XHTML file containing the paragraphs, e.g. {@code chapter-2.xhtml}.
     * @return A list of paragraphs.
     */
    public static List<String> extractParagraphsFromChapterFile(File xhtmlFile) {
        log.info("extractParagraphsFromChapter");
        
        List<String> paragraphs = new ArrayList<>();
        
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
                        // Look for paragraphs within `<body>`
                        // Expected format:
                        /*
                            <body><img src="1e8e58cc7d627a7896737cfb3eba8270.jpg" />
                            <p>
                             আজকে ছুটির দিন আনন্দে হারাই!
                             চলো সবে পোশাকের উৎসবে যাই!
                            </p>
                            </body>
                        */
                        if ("p".equals(bodyChildNode.getNodeName()) && (bodyChildNode.getAttributes().getNamedItem("dir") == null)) {
                            processParagraphNode(StoryBookProvider.GLOBAL_DIGITAL_LIBRARY, bodyChildNode, paragraphs);
                        }
                        
                        // StoryBookProvider: LETS_READ_ASIA
                        // Look for paragraphs within `<body>`
                        // Expected format:
                        /*
                            <body>
                                <div class="container">
                                    <img src = 'p-16.jpg' />
                                </div>
                                <p dir="auto">"Nagmumuni-muni lang," sabi niya.</p>
                            </body>
                        */
                        if ("p".equals(bodyChildNode.getNodeName()) && (bodyChildNode.getAttributes().getNamedItem("dir") != null)) {
                            processParagraphNode(StoryBookProvider.LETS_READ_ASIA, bodyChildNode, paragraphs);
                        }
                        
                        // StoryBookProvider: LETS_READ_ASIA
                        // Look for paragraphs within `<div lang="en">`
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
                                
                                // Look for "<p>"
                                if ("p".equals(langDivChildNode.getNodeName())) {
                                    processParagraphNode(StoryBookProvider.LETS_READ_ASIA, langDivChildNode, paragraphs);
                                }
                            }
                        }
                        
                        // StoryBookProvider: STORYWEAVER
                        // Look for paragraphs within `<div class='text-font-normal sp_h_iT66_cB33 content ' dir="auto">`
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
                            Node bodyChildNodeIdAttribute = bodyChildNode.getAttributes().getNamedItem("id");
                            log.info("bodyChildNodeIdAttribute: " + bodyChildNodeIdAttribute);
                            
                            // Expected format: <div id="story_epub">
                            if ((bodyChildNodeIdAttribute != null) && "story_epub".equals(bodyChildNodeIdAttribute.getNodeValue())) {
                                NodeList storyEpubDivChildNodeList = bodyChildNode.getChildNodes();
                                log.info("storyEpubDivChildNodeList: " + storyEpubDivChildNodeList);
                                for (int k = 0; k < storyEpubDivChildNodeList.getLength(); k++) {
                                    Node storyEpubDivChildNode = storyEpubDivChildNodeList.item(k);
                                    log.info("storyEpubDivChildNode: " + storyEpubDivChildNode);
                                    
                                    // Expected format: <div id="storyReader" class="bengali">
                                    if ("div".equals(storyEpubDivChildNode.getNodeName())) {
                                        Node storyEpubDivChildNodeIdAttribute = storyEpubDivChildNode.getAttributes().getNamedItem("id");
                                        log.info("storyEpubDivChildNodeIdAttribute: " + storyEpubDivChildNodeIdAttribute);
                                        if ((storyEpubDivChildNodeIdAttribute != null) && "storyReader".equals(storyEpubDivChildNodeIdAttribute.getNodeValue())) {
                                            NodeList storyReaderDivChildNodeList = storyEpubDivChildNode.getChildNodes();
                                            log.info("storyReaderDivChildNodeList: " + storyReaderDivChildNodeList);
                                            for (int l = 0; l < storyReaderDivChildNodeList.getLength(); l++) {
                                                Node storyReaderDivChildNode = storyReaderDivChildNodeList.item(l);
                                                log.info("storyReaderDivChildNode: " + storyReaderDivChildNode);
                                                        
                                                // Expected format: <div id="selected_page" class=" page-container-landscape story-page">
                                                if ("div".equals(storyReaderDivChildNode.getNodeName())) {
                                                    Node storyReaderDivChildNodeIdAttribute = storyReaderDivChildNode.getAttributes().getNamedItem("id");
                                                    log.info("storyReaderDivChildNodeIdAttribute: " + storyReaderDivChildNodeIdAttribute);
                                                    if ((storyReaderDivChildNodeIdAttribute != null) && "selected_page".equals(storyReaderDivChildNodeIdAttribute.getNodeValue())) {
                                                        NodeList selectedPageDivChildNodeList = storyReaderDivChildNode.getChildNodes();
                                                        log.info("selectedPageDivChildNodeList: " + selectedPageDivChildNodeList);
                                                        for (int m = 0; m < selectedPageDivChildNodeList.getLength(); m++) {
                                                            Node selectedPageDivChildNode = selectedPageDivChildNodeList.item(m);
                                                            log.info("selectedPageDivChildNode: " + selectedPageDivChildNode);
                                                            
                                                            // Expected format: <div class='text-font-normal sp_h_iT66_cB33 content ' dir="auto">
                                                            if ("div".equals(selectedPageDivChildNode.getNodeName())) {
                                                                Node selectedPageDivChildNodeClassAttribute = selectedPageDivChildNode.getAttributes().getNamedItem("class");
                                                                log.info("selectedPageDivChildNodeClassAttribute: " + selectedPageDivChildNodeClassAttribute);
                                                                if ((selectedPageDivChildNodeClassAttribute != null) 
                                                                        && selectedPageDivChildNodeClassAttribute.getNodeValue().contains("content")) {
                                                                    NodeList contentDivChildNodeList = selectedPageDivChildNode.getChildNodes();
                                                                    log.info("contentDivChildNodeList: " + contentDivChildNodeList);
                                                                    for (int n = 0; n < contentDivChildNodeList.getLength(); n++) {
                                                                        Node contentDivChildNode = contentDivChildNodeList.item(n);
                                                                        log.info("contentDivChildNode : " + contentDivChildNode);
                                                                        
                                                                        // Expected format: <p>ভীমের শুধু ঘুম আর ঘুম। সকালে উঠতেই পারে না।</p>
                                                                        if ("p".equals(contentDivChildNode.getNodeName())) {
                                                                            processParagraphNode(StoryBookProvider.STORYWEAVER, contentDivChildNode, paragraphs);
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
                        
                        // Look for text content
                        // In some cases, <p> tags are missing, and "#text" is the node name
                        if ("#text".equals(bodyChildNode.getNodeName())) {
                            String paragraph = bodyChildNode.getTextContent();
                            log.info("paragraph: \"" + paragraph + "\"");
                            paragraph = getCleanedUpParagraph(paragraph);
                            if (StringUtils.isNotBlank(paragraph)) {
                                paragraphs.add(paragraph);
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            log.error(ex.getMessage());
        }
        
        return paragraphs;
    }
    
    private static void processParagraphNode(StoryBookProvider storyBookProvider, Node paragraphNode, List<String> paragraphs) {
        log.info("processParagraphNode");
        
        log.info("storyBookProvider: " + storyBookProvider);
        if ((storyBookProvider == StoryBookProvider.GLOBAL_DIGITAL_LIBRARY) || (storyBookProvider == StoryBookProvider.LETS_READ_ASIA)) {
            // If single line-break ("<br/>"), replace it with whitespace.
            // If double line-breaks ("<br/><br/>"), treat the subsequent text as a new paragraph.
            if (paragraphNode.hasChildNodes()) {
                NodeList paragraphChildNodeList = paragraphNode.getChildNodes();
                int consecutiveLineBreaksCount = 0;
                for (int k = 0; k < paragraphChildNodeList.getLength(); k++) {
                    Node paragraphChildNode = paragraphChildNodeList.item(k);
                    log.info("paragraphChildNode: " + paragraphChildNode);
                    log.info("paragraphChildNode.getNodeName(): " + paragraphChildNode.getNodeName());
                    log.info("paragraphChildNode.getTextContent(): \"" + paragraphChildNode.getTextContent() + "\"");
                    if ("br".equals(paragraphChildNode.getNodeName())) {
                        consecutiveLineBreaksCount++;
                    } else {
                        consecutiveLineBreaksCount = 0;
                    }
                    log.info("consecutiveLineBreaksCount: " + consecutiveLineBreaksCount);
                    if (consecutiveLineBreaksCount == 1) {
                        // Replace "<br/>" with " "
                        paragraphChildNode.setTextContent(" ");
                    } else if (consecutiveLineBreaksCount == 2) {
                        // Replace "<br/><br/>" with "</p><p>"
                        paragraphChildNode.setTextContent("</p><p>");
                        consecutiveLineBreaksCount = 0;
                    }
                    
                    if (storyBookProvider == StoryBookProvider.LETS_READ_ASIA) {
                        // Look for line-breaks within `<em>`
                        // Expected format: 
                        /*
                            <p dir="auto"><em>Will they play with me? I am so different from them.<br/>I should try</em>.</p>
                        */
                        if ("em".equals(paragraphChildNode.getNodeName())) {
                            NodeList emChildNodeList = paragraphChildNode.getChildNodes();
                            for (int l = 0; l < emChildNodeList.getLength(); l++) {
                                Node emChildNode = emChildNodeList.item(l);
                                if ("br".equals(emChildNode.getNodeName())) {
                                    // Replace "<br/>" with " "
                                    emChildNode.setTextContent(" ");
                                }
                            }
                        }
                    }
                }
            }

            // Add each paragraph separately
            String[] paragraphArray = paragraphNode.getTextContent().split("</p><p>");
            for (String paragraph : paragraphArray) {
                log.info("paragraph: \"" + paragraph + "\"");
                paragraph = getCleanedUpParagraph(paragraph);
                if (StringUtils.isNotBlank(paragraph)) {
                    paragraphs.add(paragraph);
                }
            }
        } else if (storyBookProvider == StoryBookProvider.STORYWEAVER) {
            String paragraph = paragraphNode.getTextContent();
            log.info("paragraph: \"" + paragraph + "\"");
            paragraph = getCleanedUpParagraph(paragraph);

            // Skip paragraphs containing CSS code
            // See example at src/test/resources/ai/elimu/util/epub/hin-sw-10145-ek-sau-saintisvan-paer.epub_4.xhtml
            if (paragraph.contains("@page")) {
                return;
            }

            if (StringUtils.isNotBlank(paragraph)) {
                paragraphs.add(paragraph);
            }
        }
    }
    
    /**
     * E.g. "लेना ।" --> "लेना।"
     */
    private static String getCleanedUpParagraph(String paragraph) {
        log.info("getCleanedUpParagraph, paragraph: \"" + paragraph + "\"");
        
        // Replace line-breaks with a whitespace
        paragraph = paragraph.replace("\n", " ");
        
        // Replace non-breaking spaces (&nbsp;) with a whitespace
        paragraph = paragraph.replace("\u00a0", " ");
        
        // Replace vertical bar with Danda (https://en.wikipedia.org/wiki/Danda)
        paragraph = paragraph.replace("|", "।");
        
        // Remove leading/trailing whitespaces
        paragraph = paragraph.trim();
        
        // Remove duplicate whitespaces
        paragraph = paragraph.replaceAll(" +", " ");
        
        // Remove spaces in front of punctuation marks
        paragraph = paragraph.replace(" ,", ",");
        paragraph = paragraph.replace(" .", ".");
        paragraph = paragraph.replace(" ।", "।");
        paragraph = paragraph.replace(" ?", "?");
        paragraph = paragraph.replace(" !", "!");
        
        // Remove spaces within parenthesis
        paragraph = paragraph.replace("( ", "(");
        paragraph = paragraph.replace(" )", ")");
        
        // Remove spaces within quotes
        paragraph = paragraph.replace("“ ", "“");
        if (paragraph.startsWith("\" ")) {
            paragraph = paragraph.replace("\" ", "\"");
        }
        paragraph = paragraph.replace(" ”", "”");
        if (paragraph.endsWith(" \"")) {
            paragraph = paragraph.replace(" \"", "\"");
        }
        
        return paragraph;
    }
}
