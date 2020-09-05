package com.hexin.common.html2pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;

/**
 * @ClassName html2pdf
 * @Description 读取本地文件生成pdf
 * @Author shenxiaojie
 * @Date 2019-04-18 10:49
 * @Version 1.0
 */
public class LocalHtml2Pdf {

    public static void main(String[] args) throws Exception {
        System.out.println("::::::::::::::::::::html2pdf开始");
        pdfTest("/Users/shenxiaojie/Desktop/aa.jsp", "/Users/shenxiaojie/Desktop/", "", "", "/Users/shenxiaojie/Desktop/", "", 2, 2, "合同编号");
    }

    public static void pdfTest(String htmlUrl, String url, String contractName, String content, String pdfPath, String fileName, int headerType, int footType, String headerContent) throws Exception {
        Document document = null;
        PdfWriter writer = null;
        InputStream openStream = null;
        try {
            document = new Document(PageSize.A4, 38, 38, 60, 65);
            writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath + "/" + "aaa.pdf"));
            // 页头页尾
            HeaderFooter header = new HeaderFooter(headerType, footType, headerContent, url);
            writer.setPageEvent(header);
            document.open();

            File file = new File(htmlUrl);
            openStream = new FileInputStream(file);
            System.out.println("::::::::::::::::::::文件流：openStream");

//            PostMethod method = new PostMethod(htmlUrl);
//            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//            method.setParameter("contractName", contractName);
//            method.setParameter("content", content);
//            HttpClient httpClient = new HttpClient();
//            int statusCode = httpClient.executeMethod(method);
//
//            if (statusCode == HttpStatus.SC_OK) {
//                openStream = new ByteArrayInputStream(method.getResponseBody());
//            }
            FontsProvider fontsProvider = new myFontProvider();
            fontsProvider.addFontSubstitute("lowagie", "garamond");
            fontsProvider.setUseUnicode(true);

            CssAppliers cssAppliers = new CssAppliersImpl(fontsProvider);
            HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
            CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
            Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, new HtmlPipeline(htmlContext, new PdfWriterPipeline(document, writer)));
            XMLWorker xmlWorker = new XMLWorker(pipeline, true);
            XMLParser p = new XMLParser(xmlWorker);
            p.parse(new InputStreamReader(openStream, "UTF-8"));
            document.close();
            writer.flush();
            writer.close();
            openStream.close();
        } finally {
            if (document != null) {
                document.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (openStream != null) {
                openStream.close();
            }
        }
    }


    public static class FontsProvider extends XMLWorkerFontProvider {
        public FontsProvider() {
            super(null, null);
        }

        @Override
        public Font getFont(final String fontname, String encoding, float size, final int style) {
            BaseFont bf = null;
            try {
                bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                        BaseFont.NOT_EMBEDDED);
            } catch (DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Font font = new Font(bf, size, style);
            return font;
        }
    }

    static class myFontProvider extends FontsProvider {
        public myFontProvider() {
            super();
        }

        @Override
        public Font getFont(final String fontname, final String encoding,
                            final boolean embedded, final float size, final int style,
                            final BaseColor color) {
            BaseFont bf = null;
            try {
                bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                        BaseFont.NOT_EMBEDDED);
            } catch (DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Font font = new Font(bf, size, style, color);
            font.setColor(color);
            return font;
        }
    }

}
