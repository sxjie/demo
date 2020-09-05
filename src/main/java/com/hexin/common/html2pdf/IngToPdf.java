package com.hexin.common.html2pdf;


import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * ing图片签名到pdf
 *
 * @author shenxiaojie
 * @version 1.0
 * @date 2020/6/14
 */
public class IngToPdf {
    /**
     * 导入logger
     */
    private static Logger LOG = LoggerFactory.getLogger(IngToPdf.class);

    // 定义返回值
    private static float[] resu = null;
    // 定义返回页码
    private static int i = 0;

    /**
     * PNG类型
     */
    protected static final String PNG = "PNG";

    /**
     * JPG类型
     */
    protected static final String JPG = "JPG";


    public static void main(String[] args) {
        signImage("/Users/shenxiaojie/Desktop/aaa.pdf",
                "/Users/shenxiaojie/Desktop/111.pdf",
                "身份证号码",
                "/Users/shenxiaojie/Desktop/aa.png");

    }



    /**
     * 将图片签到PDF文件上
     * @param templatePath
     * @param outPutPath
     * @param keyWord
     * @param imagePath
     */
    public static void signImage(String templatePath, String outPutPath, String keyWord, String imagePath) {
        InputStream input = null;
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            // 读取模板文件
            input = new FileInputStream(new File(templatePath));
            reader = new PdfReader(input);
            stamper = new PdfStamper(reader, new FileOutputStream(outPutPath));
            // 提取pdf中的表单
            AcroFields form = stamper.getAcroFields();
            form.addSubstitutionFont(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
            // 通过域名获取所在页和坐标，左下角为起点
            float[] aaa = getKeyWords(templatePath, keyWord);
            float x = aaa[0] + 130;
            float y = aaa[1] - 15;
            // 读图片
            Image image = Image.getInstance(imagePath);
            // 获取操作的页面
            PdfContentByte under = stamper.getOverContent(1);
            // 根据域的大小缩放图片
            image.scaleToFit(100, 50);
            // 添加图片
            image.setAbsolutePosition(x, y);
            under.addImage(image);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("[图片签名][出现异常，异常信息：" + e.getMessage() + "]", e);
        } finally {
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (Exception e) {
                    LOG.info("[图片签名][PdfStamper未正常关闭]");
                }
            }
            if (reader != null) {
                reader.close();
            }
            if(input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOG.info("[图片签名][InputStream未正常关闭]");
                }
            }
        }
    }


    /*
     * 返回关键字所在的坐标和页数 float[0] >> X float[1] >> Y float[2] >> page
     */
    private static float[] getKeyWords(String filePath, final String keyWord) {
        try {
            PdfReader pdfReader = new PdfReader(filePath);
            int pageNum = pdfReader.getNumberOfPages();
            PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);
            // 下标从1开始
            for (i = 1; i < pageNum + 1; i++) {
                pdfReaderContentParser.processContent(i, new RenderListener() {
                    @Override
                    public void renderText(TextRenderInfo textRenderInfo) {
                        String text = textRenderInfo.getText();
                        if (null != text && text.contains(keyWord)) {
                            com.itextpdf.awt.geom.Rectangle2D.Float boundingRectange = textRenderInfo.getBaseline()
                                    .getBoundingRectange();
                            resu = new float[3];
                            resu[0] = boundingRectange.x;
                            resu[1] = boundingRectange.y;
                            resu[2] = i;
                        }
                    }

                    @Override
                    public void renderImage(ImageRenderInfo arg0) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void endTextBlock() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void beginTextBlock() {
                        // TODO Auto-generated method stub

                    }
                });
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resu;
    }
}
