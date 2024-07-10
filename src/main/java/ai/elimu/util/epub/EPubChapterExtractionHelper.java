package ai.elimu.util.epub;

import ai.elimu.entity.enums.StoryBookProvider;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

public class EPubChapterExtractionHelper {
    
    private static final Logger logger = LogManager.getLogger();

    /**
     * Extracts a list of filename references from the Table of Contents (TOC) file – {@code toc.xhtml}. 
     * E.g. "chapter-1.xhtml", "chapter-2.xhtml", or "Page_2.xhtml", "Page_3.xhtml", etc.
     * <p />
     * 
     * Expected file structure ({@link StoryBookProvider#GLOBAL_DIGITAL_LIBRARY}):
     * <pre>
     * 
     *     <?xml version="1.0" encoding="UTF-8"?>
     *     <html xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops">
     *     	
     *     <head>
     *     		<meta charset="utf-8" />
     *     		<title>{0}</title>
     *     	</head>
     *     	
     *     <body>
     *     		<nav epub:type="toc" id="toc">
     *     			<ol>
     *     				<li>
     *     					<a href="chapter-1.xhtml" title="chapter-1.xhtml">Chapter 1</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-2.xhtml" title="chapter-2.xhtml">Chapter 2</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-3.xhtml" title="chapter-3.xhtml">Chapter 3</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-4.xhtml" title="chapter-4.xhtml">Chapter 4</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-5.xhtml" title="chapter-5.xhtml">Chapter 5</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-6.xhtml" title="chapter-6.xhtml">Chapter 6</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-7.xhtml" title="chapter-7.xhtml">Chapter 7</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-8.xhtml" title="chapter-8.xhtml">Chapter 8</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-9.xhtml" title="chapter-9.xhtml">Chapter 9</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-10.xhtml" title="chapter-10.xhtml">Chapter 10</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-11.xhtml" title="chapter-11.xhtml">Chapter 11</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-12.xhtml" title="chapter-12.xhtml">Chapter 12</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-13.xhtml" title="chapter-13.xhtml">Chapter 13</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-14.xhtml" title="chapter-14.xhtml">Chapter 14</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-15.xhtml" title="chapter-15.xhtml">Chapter 15</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-16.xhtml" title="chapter-16.xhtml">Chapter 16</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-17.xhtml" title="chapter-17.xhtml">Chapter 17</a>
     *     				</li>
     *     				<li>
     *     					<a href="chapter-18.xhtml" title="chapter-18.xhtml">Chapter 18</a>
     *     				</li>
     *     			</ol>
     *     		</nav>
     *     		<nav epub:type="landmarks" hidden="">
     *     			<ol></ol>
     *     		</nav>
     *     	</body>
     *     </html>
     * 
     * </pre>
     * <p />
     * 
     * Expected file structure ({@link StoryBookProvider#LETS_READ_ASIA}):
     * <pre>
     * 
     *     <?xml version="1.0" encoding="UTF-8" standalone="no"?>
     *     <html xmlns:epub="http://www.idpf.org/2007/ops" xml:lang="fil" lang="fil" xmlns="http://www.w3.org/1999/xhtml">
     *         <head>
     *             <title>Hindi na Ako natatakot!</title>
     *         </head>
     *         <body>
     *             <nav epub:type="toc">
     *                 <h1>Table of Contents</h1>
     *                 <ol>
     *                     <li><a href="Page_2.xhtml">Page 2</a></li>
     *                     <li><a href="Page_3.xhtml">Page 3</a></li>
     *                     <li><a href="Page_4.xhtml">Page 4</a></li>
     *                     <li><a href="Page_5.xhtml">Page 5</a></li>
     *                     <li><a href="Page_6.xhtml">Page 6</a></li>
     *                     <li><a href="Page_7.xhtml">Page 7</a></li>
     *                     <li><a href="Page_8.xhtml">Page 8</a></li>
     *                     <li><a href="Page_9.xhtml">Page 9</a></li>
     *                     <li><a href="Page_10.xhtml">Page 10</a></li>
     *                     <li><a href="Page_11.xhtml">Page 11</a></li>
     *                     <li><a href="Page_12.xhtml">Page 12</a></li>
     *                     <li><a href="Copyright.xhtml">Copyright</a></li>
     *                 </ol>
     *             </nav>
     *             <nav epub:type="landmarks">
     *                 <h1>Guide</h1>
     *                 <ol>
     *                     <li><a epub:type="cover" href="cover.xhtml">Cover</a></li>
     *                     <li><a epub:type="bodymatter" href="Page_2.xhtml">Start of Story</a></li>
     *                 </ol>
     *             </nav>
     *         </body>
     *     </html>
     * 
     * </pre>
     */
    public static List<String> extractChapterReferencesFromTableOfContentsFile(File xhtmlFile) {
        logger.info("extractChapterReferencesFromTableOfContentsFile");
        
        List<String> chapterReferences = new ArrayList<>();
        
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
                        logger.info("bodyChildNode.getNodeName(): \"" + bodyChildNode.getNodeName() + "\"");
                        
                        // Look for "<nav>"
                        if ("nav".equals(bodyChildNode.getNodeName())) {
                            // Look for "<nav epub:type="toc">"
                            NamedNodeMap attributes = bodyChildNode.getAttributes();
                            logger.info("attributes: " + attributes);
                            Node namedItem = attributes.getNamedItem("epub:type");
                            logger.info("namedItem: " + namedItem);
                            logger.info("namedItem.getNodeValue(): " + namedItem.getNodeValue());
                            if ("toc".equals(namedItem.getNodeValue())) {
                                NodeList navChildNodeList = bodyChildNode.getChildNodes();
                                logger.info("navChildNodeList.getLength(): " + navChildNodeList.getLength());
                                for (int k = 0; k < navChildNodeList.getLength(); k++) {
                                    Node navChildNode = navChildNodeList.item(k);
                                    logger.info("navChildNode: " + navChildNode);

                                    // Look for "<ol>"
                                    if ("ol".equals(navChildNode.getNodeName())) {
                                        NodeList olChildNodeList = navChildNode.getChildNodes();
                                        logger.info("olChildNodeList.getLength(): " + olChildNodeList.getLength());
                                        for (int l = 0; l < olChildNodeList.getLength(); l++) {
                                            Node olChildNode = olChildNodeList.item(l);
                                            logger.info("olChildNode: " + olChildNode);
                                                
                                            // Look for "<li>"
                                            if ("li".equals(olChildNode.getNodeName())) {
                                                NodeList liChildNodeList = olChildNode.getChildNodes();
                                                logger.info("liChildNodeList.getLength(): " + liChildNodeList.getLength());
                                                
                                                for (int m = 0; m < liChildNodeList.getLength(); m++) {
                                                    Node liChildNode = liChildNodeList.item(m);
                                                    logger.info("liChildNode: " + liChildNode);
                                                    
                                                    // Look for "<a>", e.g. <a href="Page_2.xhtml">Page 2</a>
                                                    if ("a".equals(liChildNode.getNodeName())) {
                                                        // Get the value of the "href" attribute
                                                        String chapterReference = liChildNode.getAttributes().getNamedItem("href").getNodeValue();
                                                        logger.info("chapterReference: \"" + chapterReference + "\"");
                                                        chapterReferences.add(chapterReference);
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
        
        return chapterReferences;
    }
    
    /**
     * Extracts a list of filename references from the Table of Contents (TOC) file – {@code toc.ncz}. 
     * E.g. "1.xhtml", "2.xhtml", or "3.xhtml", etc.
     * <p />
     * 
     * Expected file structure ({@link StoryBookProvider#STORYWEAVER}):
     * <pre>
     * 
     *     <?xml version="1.0" encoding="utf-8"?>
     *     <ncx xmlns="http://www.daisy.org/z3986/2005/ncx/" version="2005-1">
     *       <head>
     *         <meta name="dtb:uid" content="/stories/11791-ghumkature-bhim"/>
     *         <meta name="dtb:depth" content="1"/>
     *         <meta name="dtb:totalPageCount" content="0"/>
     *         <meta name="dtb:maxPageNumber" content="0"/>
     *       </head>
     *       <docTitle>
     *         <text>ঘুমকাতুরে ভীম</text>
     *       </docTitle>
     *       <navMap>
     *         <navPoint id="item_1" playOrder="1">
     *           <navLabel>
     *             <text>Page 1</text>
     *           </navLabel>
     *           <content src="1.xhtml"/>
     *         </navPoint>
     *         <navPoint id="item_2" playOrder="2">
     *           <navLabel>
     *             <text>Page 2</text>
     *           </navLabel>
     *           <content src="2.xhtml"/>
     *         </navPoint>
     *         <navPoint id="item_3" playOrder="3">
     *           <navLabel>
     *             <text>Page 3</text>
     *           </navLabel>
     *           <content src="3.xhtml"/>
     *         </navPoint>
     *         <navPoint id="item_4" playOrder="4">
     *           <navLabel>
     *             <text>Page 4</text>
     *           </navLabel>
     *           <content src="4.xhtml"/>
     *         </navPoint>
     *       </navMap>
     *     </ncx>
     * 
     * </pre>
     */
    public static List<String> extractChapterReferencesFromTableOfContentsFileNcx(File ncxFile) {
        logger.info("extractChapterReferencesFromTableOfContentsFileNcx");
        
        List<String> chapterReferences = new ArrayList<>();
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(ncxFile);
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            logger.info("nodeList.getLength(): " + nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                logger.info("node: " + node);
                logger.info("node.getNodeName(): " + node.getNodeName());
                        
                // Look for "<navMap>"
                if ("navMap".equals(node.getNodeName())) {
                    NodeList navMapChildNodeList = node.getChildNodes();
                    logger.info("navMapChildNodeList.getLength(): " + navMapChildNodeList.getLength());
                    for (int j = 0; j < navMapChildNodeList.getLength(); j++) {
                        Node navMapChildNode = navMapChildNodeList.item(j);
                        logger.info("navMapChildNode: " + navMapChildNode);
                        logger.info("navMapChildNode.getNodeName(): \"" + navMapChildNode.getNodeName() + "\"");
                        
                        // Look for "<navPoint>"
                        if ("navPoint".equals(navMapChildNode.getNodeName())) {
                            NodeList navPointChildNodeList = navMapChildNode.getChildNodes();
                            logger.info("navPointChildNodeList.getLength(): " + navPointChildNodeList.getLength());
                            for (int k = 0; k < navPointChildNodeList.getLength(); k++) {
                                Node navPointChildNode = navPointChildNodeList.item(k);
                                logger.info("navPointChildNode: " + navPointChildNode);
                                logger.info("navPointChildNode.getNodeName(): \"" + navPointChildNode.getNodeName() + "\"");
                                
                                // Look for "<content>", e.g. <content src="1.xhtml"/>
                                if ("content".equals(navPointChildNode.getNodeName())) {
                                    // Get the value of the "src" attribute
                                    String chapterReference = navPointChildNode.getAttributes().getNamedItem("src").getNodeValue();
                                    logger.info("chapterReference: \"" + chapterReference + "\"");
                                    chapterReferences.add(chapterReference);
                                }
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            logger.error(ex);
        }
        
        return chapterReferences;
    }
}
