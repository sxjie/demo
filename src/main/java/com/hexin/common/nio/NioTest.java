package com.hexin.common.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description TODO
 * @ClassName NioTest
 * @Author shenxiaojie
 * @Date 2019-09-29 09:36
 * @Version 1.0
 */
public class NioTest {

    public static void main(String[] args) {
        FileInputStream fin = null;
        FileOutputStream fos = null;
        try {
            fin = new FileInputStream(new File("/Users/shenxiaojie/Desktop/模版.html"));
            FileChannel channel = fin.getChannel();

            int capacity = 1000;// 字节
            ByteBuffer bf = ByteBuffer.allocate(capacity);
            int length = -1;

            fos = new FileOutputStream(new File("/Users/shenxiaojie/Desktop/aa.html"));
            FileChannel outchannel = fos.getChannel();


            while ((length = channel.read(bf)) != -1) {

                //将当前位置置为limit，然后设置当前位置为0，也就是从0到limit这块，都写入到同道中
                bf.flip();

                int outlength = 0;
                while ((outlength = outchannel.write(bf)) != 0) {
                    System.out.println(new String(bf.array(), 0, length));
                }

                //将当前位置置为0，然后设置limit为容量，也就是从0到limit（容量）这块，
                //都可以利用，通道读取的数据存储到
                //0到limit这块
                bf.clear();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
