package com.hexin.common.html2pdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 【类或接口功能描述】
 *
 * @author shenxiaojie
 * @version 1.0
 * @date 2020/6/14
 */
public class CreateImag {
    private static Logger LOG = LoggerFactory.getLogger(CreateImag.class);

    // old
    public static final int width = 215;
    public static final int height = 100;

    //new

    public static final int width_new = 194;
    public static final int height_new = 90;

    public static final String SEAL_PICTURE_PATH_SUFFIX = ".png";
    /**
     * 边框线宽
     */
    public static float borderWidth = 5F;
    /**
     * 边框颜色
     */
    public static Color borderColor = Color.RED;


    public static void main(String[] args) {
        String[] name = { "付青", "刘冬冬"};

        for (int i = 0; i < name.length; i++) {
            LOG.info("start------------------------签章" + (i + 1) + "号");
            String path = "/Users/shenxiaojie/Desktop\n" + i + ".png";
            createSeal(name[i], path);
        }
      /*  String comPicPath = "E:\\project\\hxnew\\hexin-contract\\src\\main\\resources\\companyimages\\";
        String name[] = {"axb.png","hxcfglzx.png","hxdzsw.png","hxdzswtj.png","hxfz.png","hxjr.png","hxxx.png","wh.png"};
        try {
            for (int i = 0; i < name.length; i++) {
                condenceCompanyImage(comPicPath+name[i], "/Users/zm/Downloads/xx/"+name[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    public static void createSeal(String name, String pngPath) {
        //LOG.info("name为：'" + name + "'生成印章图片开始-----------------------");


        String path = pngPath.replace(".png",".jpg");
        File file = new File(path);
        if(!file.exists()) {
            // 创建一个画布
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 获取画布的画笔
            Graphics2D g2 = (Graphics2D) bi.getGraphics();
            Stroke stroke = g2.getStroke();// 旧的线性
            // 设置背景色
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, width, height);
            // 设置外边框颜色
            g2.setColor(borderColor);
            // 绘制印章边框
            float[] dash = {25, 1, 32, 2, 56, 3, 23, 1, 17, 2, 11, 1};
            g2.setStroke(new BasicStroke(borderWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, dash, 1f));
            g2.drawRect(0, 0, width, height);
            g2.setStroke(stroke);
            // 设置字体
            g2.setColor(Color.RED); // 设置画笔颜色
            // 字符串的长度
            int length = name.length();
            // 获取字符的size，以及是否分行
            Map<String, Object> fontMap = getFontInformation(length);
            // 设置字体的大小
            int fontSize = (int) fontMap.get("fontSize");
            // new一个font对象
            Font font = new Font("微软雅黑", Font.LAYOUT_LEFT_TO_RIGHT, fontSize);
            g2.setFont(font);// 写入签名
            // 计算Y轴坐标
            Map<String, Object> yMap = getAxisY(fontSize, length, g2, font);

            if (!(boolean) fontMap.get("isBranch")) {// 如果不需要分行
                createContent(0, length, name, fontSize, yMap, g2);
            } else {// 如果需要分行
                int flag = 0;
                // 计算每一行的字数
                if ((length % 2) == 0) {
                    flag = length / 2;
                } else {
                    flag = (length / 2) + 1;
                }
                createContent(0, flag, name, fontSize, yMap, g2);
                createContent(flag, length, name, fontSize, yMap, g2);
            }
            try {
                File parentPath = file.getParentFile();
                if (!parentPath.exists()) {
                    parentPath.mkdirs();
                }


                // 将生成的图片保存为JPG格式的文件。IMAGEIO支持JPG、PNG、GIF等格式
                ImageIO.write(bi, "jpg", file);
                //将图片进行0.9 压缩
                condenceImage(path, pngPath);
                // LOG.info("生成印章结束-------------------------------");
                //删除生成jpg图片
                file.delete();
            } catch (IOException e) {
                LOG.error("生成图片出错........", e);
            } catch (Exception e) {
                LOG.error("压缩图片出错........", e);
            }
        }else{
            try {
                condenceImage(path, pngPath);
            } catch (Exception e) {
                LOG.error("压缩图片出错........", e);
            }
        }

    }



    /**
     * 根据字符串的长度 获取字体的size
     *
     * @author jiuzhou
     *
     * @param length
     *            字符串的长度
     * @return
     */
    public static Map<String, Object> getFontInformation(int length) {
        int fontSize = 0;
        boolean isBranch = false;
        switch (length) {
            case 2:
                fontSize = 70;
                break;
            case 3:
                fontSize = 60;
                break;
            case 4:
                fontSize = 44;
                break;
            case 5:
                fontSize = 37;
                break;
            case 6:
                fontSize = 30;
                break;
            case 12:
                fontSize = 30;
                isBranch = true;
                break;
            default:
                fontSize = 33;
                isBranch = true;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isBranch", isBranch);// 是否分行
        map.put("fontSize", fontSize);// 字体size
        return map;
    }


    /**
     * 获取字体在签章上面的y轴位置
     *
     * @param fontSize
     *            字体的大小
     * @param length
     *            字体的长度
     * @param g2
     *            容器对象
     * @param font
     *            文字对象
     * @return
     */
    public static Map<String, Object> getAxisY(int fontSize, int length, Graphics2D g2, Font font) {
        Map<String, Object> map = new HashMap<String, Object>();
        // font容器
        FontMetrics fm = g2.getFontMetrics(font);
        // 获取font的基准线
        int ascent = fm.getAscent();// 得到font的上基准线
        int descent = fm.getDescent();// 得到font的下基准线
        if (length <= 6) {// 当为单行签章时
            float firstAxisY = ((height - (ascent + descent)) / 2) + ascent;
            map.put("firstAxisY", firstAxisY);
        } else {// 当为双行签章时
            float center = ((height - (ascent + descent)) / 2) + ascent;
            float firstAxisY = 0;
            float secondAxisY = 0;
            if (length == 12) {
                firstAxisY = center - 16;
                secondAxisY = center + 16;
            } else {
                firstAxisY = center - 18;
                secondAxisY = center + 18;
            }
            map.put("firstAxisY", firstAxisY);
            map.put("secondAxisY", secondAxisY);
        }
        return map;
    }


    /**
     * 生成签章内容
     *
     * @author jiuzhou
     *
     * @param start
     *            起始点
     * @param end
     *            终结点
     * @param name
     *            字符串
     * @param fontSize
     *            字体大小
     * @param yMap
     *            Y轴坐标map
     * @param g2
     *            容器对象
     */
    public static void createContent(int start, int end, String name, int fontSize, Map<String, Object> yMap,
                                     Graphics2D g2) {
        char[] charArray = name.toCharArray();
        float firstSpacing = ((width - (15 * 2) - ((end - start) * fontSize)) / ((end - start) - 1)) + fontSize;
        if (start == 0) {
            for (int i = start; i < end; i++) {
                g2.drawString(new String(new char[] { charArray[i] }), 15 + (i * firstSpacing),
                        (float) yMap.get("firstAxisY"));
            }
        } else {
            for (int i = start; i < end; i++) {
                g2.drawString(new String(new char[] { charArray[i] }), 15 + ((i - start) * firstSpacing),
                        (float) yMap.get("secondAxisY"));
            }
        }
    }

    public static void condenceImage(String src,String dest) throws Exception {
        //LOG.info("开始压缩图片-------------------------------");
        double wr=0,hr=0;
        File srcFile = new File(src);
        File destFile = new File(dest);
        BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
        Image Itemp = bufImg.getScaledInstance(width_new, height_new, bufImg.SCALE_SMOOTH);//设置缩放目标图片模板
        wr=width_new*1.0/bufImg.getWidth();     //获取缩放比例
        hr=height_new*1.0/bufImg.getHeight();
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile); //写入缩减后的图片
        } catch (Exception ex) {
            LOG.error("压缩图片异常",ex);
        }
        LOG.info("压缩图片结束");
    }


}
