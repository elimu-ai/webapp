package ai.elimu.util.content;

import ai.elimu.model.content.StoryBook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EpubToStoryBookConverterLetsReadAsia {
    
    private static final Logger logger = Logger.getLogger(EpubToStoryBookConverterLetsReadAsia.class);
    
    public static StoryBook getStoryBookFromEpub(File ePubFile) {
        logger.info("getStoryBookFromEpub");
        
        logger.info("Converting \"" + ePubFile + "\" from ePUB to StoryBook");
        
        StoryBook storyBook = new StoryBook();
        
        // Unzip ePUB
        List<File> unzippedFiles = unzipFiles(ePubFile);
        logger.info("unzippedFiles.size(): " + unzippedFiles.size());
        
        // Extract storybook metadata and cover image from the Open Package Format (OPF) file
        for (File unzippedFile : unzippedFiles) {
            // OEBPS/content.opf
            if ("content.opf".equals(unzippedFile.getName())) {
                extractMetadataFromOPF(unzippedFile, storyBook);
            }
        }
        
        // Iterate chapters in the Table of Contents (TOC) file and extract images and paragraphs
        for (File unzippedFile : unzippedFiles) {
            // OEBPS/toc.xhtml
            if ("toc.xhtml".equals(unzippedFile.getName())) {
                extractChaptersFromTOC(unzippedFile, storyBook);
            }
        }
        
        // Delete the temporary folder
        // TODO
        
        return storyBook;
    }
    
    /**
     * Unzip the contents of the ePUB file to a temporary folder.
     */
    private static List<File> unzipFiles(File ePubFile) {
        logger.info("unzipFiles");
        
        List<File> unzippedFiles = new ArrayList<>();
        
        String tmpDir = System.getProperty("java.io.tmpdir");
        logger.info("tmpDir: " + tmpDir);
        File tmpDirElimuAi = new File(tmpDir, "elimu-ai");
        logger.info("tmpDirElimuAi: " + tmpDirElimuAi);
        logger.info("tmpDirElimuAi.mkdir(): " + tmpDirElimuAi.mkdir());
        File unzipDestinationDirectory = new File(tmpDirElimuAi, ePubFile.getName().replace(" ", "_") + "_unzipped");
        logger.info("unzipDestinationDirectory: " + unzipDestinationDirectory);
        logger.info("unzipDestinationDirectory.mkdir(): " + unzipDestinationDirectory.mkdir());
        byte[] buffer = new byte[1024];
        try {
            FileInputStream fileInputStream = new FileInputStream(ePubFile);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                logger.info("zipEntry: " + zipEntry);
                
                // Create intermediate folders.
                File metaInfDirectory = new File(unzipDestinationDirectory, "META-INF");
                logger.info("metaInfDirectory.mkdir(): " + metaInfDirectory.mkdir());
                File contentDirectory = new File(unzipDestinationDirectory, "OEBPS");
                logger.info("contentDirectory.mkdir(): " + contentDirectory.mkdir());
                
                // E.g. unzipDestinationDirectory + "/" + "META-INF/container.xml"
                File unzipDestinationFile = new File(unzipDestinationDirectory + File.separator + zipEntry.toString());
                logger.info("unzipDestinationFile: " + unzipDestinationFile);
                
                // Write file to disk
                FileOutputStream fileOutputStream = new FileOutputStream(unzipDestinationFile);
                int length;
                while ((length = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                fileOutputStream.close();
                
                logger.info("unzipDestinationFile.exists(): " + unzipDestinationFile.exists());
                unzippedFiles.add(unzipDestinationFile);
                
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException ex) {
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        }
        
        return unzippedFiles;
    }
    
    /**
     * Extracts ePUB metadata from the <code>/content/book.opf</code> file, and adds it to a {@link StoryBook} instance.
     * 
     * @param opfFile The XML file containing the metadata.
     * @param storyBook The {@link StoryBook} that will be populated with the metadata extracted from the OPF file.
     * @return the updated {@link StoryBook}.
     */
    private static void extractMetadataFromOPF(File opfFile, StoryBook storyBook) {
        logger.info("extractMetadataFromOPF");
        
        logger.info("Extracting metadata from \"" + opfFile + "\"");
        
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
                    logger.info("nodeList.getLength(): " + nodeList.getLength());
                    for (int j = 0; j < metadataNodeList.getLength(); j++) {
                        Node metadataNode = metadataNodeList.item(j);
                        logger.info("metadataNode: " + metadataNode);
                        
                        if ("dc:title".equals(metadataNode.getNodeName())) {
                            String title = metadataNode.getTextContent();
                            logger.info("title: " + title);
                            storyBook.setTitle(title);
                        }
                        
                        if ("dc:description".equals(metadataNode.getNodeName())) {
                            String description = metadataNode.getTextContent();
                            logger.info("description: " + description);
                            storyBook.setDescription(description);
                        }
                    }
                }
                
                // Extract cover image
                if ("manifest".equals(node.getNodeName())) {
                    // TODO
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            logger.error(null, ex);
        }
    }
    
    /**
     * Extracts a list of hyperlink references (e.g. "Page_2.xhtml") from {@code OEBPS/toc.xhtml}.
     * <p />
     * Expected file structure:
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
    public static List<String> extractChapterReferencesFromTOC(File tocFile) {
        logger.info("extractChapterReferencesFromTOC");
        
        List<String> chapterReferences = new ArrayList<>();
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(tocFile);
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
                        
                        // Look for "<nav epub:type="toc">"
                        if ("toc".equals(bodyChildNode.getAttributes().getNamedItem("epub:type").getNodeValue())) {
                            NodeList navChildNodeList = bodyChildNode.getChildNodes();
                            logger.info("navChildNodeList.getLength(): " + navChildNodeList.getLength());
                            for (int k = 0; k < navChildNodeList.getLength(); k++) {
                                Node navChildNode = navChildNodeList.item(k);
                                logger.info("navChildNode: " + navChildNode);
                                
                                // Look for "<ol>"
                                if ("ol".equals(navChildNode.getNodeName())) {
                                    NodeList olChildNodeList = navChildNode.getChildNodes();
                                    logger.info("olChildNodeList.getLength(): " + olChildNodeList.getLength());
                                    
                                    // Look for "<li>"
                                    for (int l = 0; l < olChildNodeList.getLength(); l++) {
                                        Node liChildNode = olChildNodeList.item(l);
                                        logger.info("liChildNode: " + liChildNode);
                                        
                                        // Look for "<a>", e.g. <a href="Page_2.xhtml">Page 2</a>
                                        Node aChildNode = liChildNode.getFirstChild();
                                        logger.info("aChildNode: " + aChildNode);
                                        
                                        // Get the value of the "href" attribute
                                        String chapterReference = aChildNode.getAttributes().getNamedItem("href").getNodeValue();
                                        logger.info("chapterReference: \"" + chapterReference + "\"");
                                        chapterReferences.add(chapterReference);
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
        
        return chapterReferences;
    }
    
    /**
     * Extracts content from the chapters listed in the Table of Contents.
     */
    private static void extractChaptersFromTOC(File tocFile, StoryBook storyBook) {
        logger.info("extractChaptersFromTOC");
        
        logger.info("Extracting paragraphs from \"" + tocFile + "\"");
        
        List<String> chapterReferences = extractChapterReferencesFromTOC(tocFile);
        logger.info("chapterReferences.size(): " + chapterReferences.size());
        for (String chapterReference : chapterReferences) {
            File xhtmlChapterFile = new File(tocFile.getParent(), chapterReference);
            logger.info("xhtmlChapterFile: " + xhtmlChapterFile);

            List<String> paragraphs = extractImagesAndParagraphsFromChapter(xhtmlChapterFile);
            logger.info("paragraphs.size(): " + paragraphs.size());
            if (storyBook.getParagraphs() == null) {
                storyBook.setParagraphs(paragraphs);
            } else {
                storyBook.getParagraphs().addAll(paragraphs);
            }
        }                        
    }
    
    private static List<String> extractImagesAndParagraphsFromChapter(File xhtmlChapterFile) {
        logger.info("extractImagesAndParagraphsFromChapter");
        logger.info("xhtmlChapterFile: \"" + xhtmlChapterFile + "\"");
        
        List<String> paragraphs = new ArrayList<>();
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xhtmlChapterFile);
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            logger.info("nodeList.getLength(): " + nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                logger.info("node: " + node);
                if ("body".equals(node.getNodeName())) {
                    NodeList bodyNodeList = node.getChildNodes();
                    logger.info("bodyNodeList.getLength(): " + bodyNodeList.getLength());
                    for (int j = 0; j < bodyNodeList.getLength(); j++) {
                        Node bodyNode = bodyNodeList.item(j);
                        logger.info("bodyNode: " + bodyNode);
                        
                        // Extract paragraphs (<p>...</p>)
                        // In some cases, <p> tags are missing (e.g. swa-gdl-30.epub), and "#text" is the bodyChildNode name
                        if ("p".equals(bodyNode.getNodeName()) || "#text".equals(bodyNode.getNodeName())) {
                            String paragraph = bodyNode.getTextContent().trim();
                            logger.info("paragraph: \"" + paragraph + "\"");
                            if (StringUtils.isNotBlank(paragraph)) {
                                paragraphs.add(paragraph);
                            }
                        }

                        // Extract images (<img src="..." />)
                        if ("img".equals(bodyNode.getNodeName())) {
                            // TODO
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
