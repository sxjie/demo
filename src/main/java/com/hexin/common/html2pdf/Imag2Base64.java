package com.hexin.common.html2pdf;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Imag2Base64 {
    //测试
    public static void main(String[] str) throws Exception {

        String path = "/Users/shenxiaojie/Desktop/aa.png";
        String base64 = Imag2Base64.imageToBase64(path);


        Imag2Base64.base64ToImage(base64, "/Users/shenxiaojie/Desktop/bb.png");

        System.out.println(base64+"+++++++++++");
    }

    /**
     * @Descriptionmap 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @author
     * @Date
     * @param path 图片路径
     * @return
     */
    public static String imageToBase64(String path) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println("base64------:" + encoder.encode(data));
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * @Descriptionmap 对字节数组字符串进行Base64解码并生成图片
     * @author
     * @Date 2016-12-06
     * @param base64 图片Base64数据
     * @param path 图片路径
     * @return
     */
    public static boolean base64ToImage(String base64, String path) {// 对字节数组字符串进行Base64解码并生成图片
        if (base64 == null){ // 图像数据为空
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(base64);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(path);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }




}
