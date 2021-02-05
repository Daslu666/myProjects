import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.Paragraph;
import com.aspose.words.ParagraphFormat;
import com.aspose.words.CellCollection;
import com.aspose.words.SectionCollection;
import com.aspose.words.ParagraphCollection;
import com.aspose.words.TableCollection;
import com.aspose.words.Table;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.aspose.words.FontSettings;
import com.aspose.words.SystemFontSource;
import com.aspose.words.FolderFontSource;
import com.aspose.words.FontSourceBase;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
/**
 * <b><code>Word2PdfUtil</code></b>
 * <p>
 * Description: word to pdf util.
 * <p>
 * <b>Creation Time:</b> 2018/5/15 15:10
 *
 * @author 
 * @date 2018 /5/15
 * @since JDK 1.7
 */
public class Word2PdfUtil {

    /**
     * The constant LOG.
     *
     */
    // private static final Logger LOG = LoggerFactory.getLogger(Word2PdfUtil.class);

    /**
     * license
     *
     * @return
     */
    private static boolean getLicense() {
        boolean result = false;
        try {
            String licenseStr =
                    "<License>\n" +
                    "  <Data>\n" +
                    "    <Products>\n" +
                    "      <Product>Aspose.Total for Java</Product>\n" +
                    "      <Product>Aspose.Words for Java</Product>\n" +
                    "    </Products>\n" +
                    "    <EditionType>Enterprise</EditionType>\n" +
                    "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                    "    <LicenseExpiry>20991231</LicenseExpiry>\n" +
                    "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                    "  </Data>\n" +
                    "  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
                    "</License>";
            InputStream license = new ByteArrayInputStream(licenseStr.getBytes("UTF-8"));
            License asposeLic = new License();
            asposeLic.setLicense(license);
            result = true;
        } catch (Exception e) {
            System.out.println(e.toString());
            // LOG.error("error:", e);
        }
        return result;
    }

    /**
     * Word 2 pdf.
     *
     * @param multipartFile the multipart file
     * @param pdfFilePath   the pdf file path
     */
    public static void word2Pdf(String path, String pdfFilePath) {
        FileOutputStream fileOS = null;
        FileOutputStream fileDoc = null;
        if (!getLicense()) {
            System.out.println("18");
            return;
        }
        String os = System.getProperty("os.name");
        // System.out.println(os);
        // Ssystem.out.println(System.getProperty("user.name"));
        // if(!os.toLowerCase().startsWith("win")){
        //     Document doc = new Document();

        //     // Create a font settings object for our document
            
        //     // FontSettings.getDefaultInstance().setFontsFolder(File.separator+"mnt"+File.separator+"aj"+File.separator+"scjg"+File.separator+"api"+File.separator+"chinese", false);
        //     // ArrayList fontSources = new ArrayList(Arrays.asList(FontSettings.getDefaultInstance().getFontsSources()));

        //     // for (int i= 0;i< fontSources.size();i++ ) {
        //     //     String[] str = fontSources.get(i).getSystemFontFolders();
        //     //     System.out.println(str);
        //     // }
        // }
        try {
            InputStream is = new FileInputStream(new File(path));   //word文件流
            Document doc = new Document(is);    //文档对象
            ParagraphCollection pc = doc.getFirstSection().getBody().getParagraphs();   //获取word中所有的段落对象
            int val = pc.getCount();    //段落数
            //遍历段落，设置段后为 0.0
            for (int i=0;i<val ;i++ ) {
                doc.getFirstSection().getBody().getParagraphs().get(i).getParagraphFormat().setSpaceAfter(0.0);
            }
            //获取word中的表格对象
            TableCollection tc = doc.getFirstSection().getBody().getTables();
            Table table = tc.get(0);
            //判断word中是否含有表格
            if (table != null) {
                int val2 = table.getCount();//获取表格的行数
                CellCollection cc = null;   //新建表格单元格集合对象
                int val3 = 0;               //单元格数
                int val4 = 0;               //每个单元格内的段落数
                //遍历表格的每一行
                for (int j=0;j<val2 ;j++ ) {
                    cc = table.getRows().get(j).getCells(); //获取每一行的单元格集合
                    if (cc != null){
                        val3 = cc.getCount();   //每一行的单元格个数
                        //遍历单元格
                        for (int i=0;i<val3 ;i++ ) {
                            val4 = cc.get(i).getParagraphs().getCount();    //每个单元格内的段落数
                            //遍历单元格内的段落，设置段后为 0.0
                            for (int a = 0;a < val4 ;a++ ) {
                                cc.get(i).getParagraphs().get(a).getParagraphFormat().setSpaceAfter(0.0);
                            }
                        }
                    }
                }
            }

            ArrayList fontSources = new ArrayList(Arrays.asList(FontSettings.getDefaultInstance().getFontsSources()));

            FolderFontSource folderFontSource = new FolderFontSource("../chinese", true);

            fontSources.add(folderFontSource);

            FontSourceBase[] updatedFontSources = (FontSourceBase[]) fontSources.toArray(new FontSourceBase[fontSources.size()]);
            FontSettings.getDefaultInstance().setFontsSources(updatedFontSources);

            // doc.setFontSettings(new FontSettings());
            // SystemFontSource systemFontSource = (SystemFontSource) doc.getFontSettings().getFontsSources()[0];
            // FolderFontSource folderFontSource = new FolderFontSource("/mnt/aj/scjg/api/chinese", false);
            // doc.getFontSettings().setFontsSources(new FontSourceBase[]{systemFontSource, folderFontSource});
            // for (String systemFontFolder : SystemFontSource.getSystemFontFolders()) {
            //     System.out.println(systemFontFolder);
            // }

            // String docFilePath = path.replace(".docx",".doc");
            // fileDoc = new FileOutputStream(new File(docFilePath));
            // doc.save(fileDoc, SaveFormat.DOC);
            
            fileOS = new FileOutputStream(new File(pdfFilePath));
            doc.save(fileOS, SaveFormat.PDF);
        } catch (Exception e) {
            System.out.println("error:" + e.toString());
            // LOG.error("error:", e);
        } finally {
            try {
                if(fileOS != null){
                    fileOS.close();
                }
            } catch (IOException e) {
                System.out.println(e.toString());
                // LOG.error("error:", e);
            }
        }
    }
    public static void main(String[] args) {
        String[] wordPaths =  args[0].split(",");
        String[] pdfPaths = args[1].split(",");
        for (int i = 0;i < wordPaths.length;i++ ) {
            word2Pdf(wordPaths[i],pdfPaths[i]);
        } 
        // word2Pdf(args[0],args[1]);
    }
}