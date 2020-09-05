package com.hexin.common.nio;

import java.io.*;

/**
 * @Description TODO
 * @ClassName Local2Local
 * @Author shenxiaojie
 * @Date 2019-09-29 09:35
 * @Version 1.0
 */
public class Local2Local {


    public static void main(String[] args) throws FileNotFoundException {
        InputStream in = null;
        FileOutputStream fos = null;
        try {

            // 读取文件流
            in = new FileInputStream(new File("/Users/shenxiaojie/Desktop/模版.html"));

            // 写入io流
            fos = new FileOutputStream("/Users/shenxiaojie/Desktop/test.txt");
            int len = 0;
            byte[] b = new byte[1024];
            while ((len = in.read(b)) != -1) {
                fos.write(b, 0, len);
                System.out.println(new String(b, 0, len));
            }

            System.out.println(new String() + "]--------");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
            }
            return;
        }
    }

}
