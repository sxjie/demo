package com.hexin.common.html2pdf;


import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Hetong
 * @Description create contract pdf
 * @Author shenxiaojie
 * @Date 2019-03-22 14:59
 * @Version 1.0
 */
public class HttpPost {

    private static Logger logger = LoggerFactory.getLogger(HttpPost.class);

    public static void main(String[] args) {

        createContractPdf();

    }

    public static String createContractPdf(){
        String result = null;
        String url = "http://contract.hexindai.com/contract/create";

        // 封装数据，
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("contract", "cfcaDigitalCertificateAgreement"));
        formParams.add(new BasicNameValuePair("professionid", "11"));
        formParams.add(new BasicNameValuePair("bizsystem", "hexindai"));
        formParams.add(new BasicNameValuePair("content", "{\"headerContent\":\"合同编号：如果需要的话\",\"footType\":\"2\",\"headType\":\"2\"}"));

        // 获取httpclient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            //创建post请求
            org.apache.http.client.methods.HttpPost httpPost = new org.apache.http.client.methods.HttpPost(url);
            // 设置请求和传输超时时间
//            RequestConfig requestConfig = RequestConfig.custom()
//                    .setSocketTimeout(2000).setConnectTimeout(2000).build();
//            httpPost.setConfig(requestConfig);

            // 提交参数发送请求 10 11 12 1  2.5 4 2.5
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formParams, "utf-8");
            httpPost.setEntity(urlEncodedFormEntity);

            response = httpclient.execute(httpPost);
            // 得到响应信息
            int statusCode = response.getStatusLine().getStatusCode();
            // 判断响应信息是否正确
            if (statusCode != HttpStatus.SC_OK) {
                // 终止并抛出异常
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //result = EntityUtils.toString(entity);//不进行编码设置
                result = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭所有资源连接
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

//
//    public static void main(String[] args) {
//        Map<String, String> sealInfoArray = new HashMap<>();
//        sealInfoArray.put("position", "签字并按手印:1500:0");
//        sealInfoArray.put("certNo", "130434199303104858");
//        sealInfoArray.put("sealType", "1");
//
//        List<Object> list = new ArrayList<>();
//        list.add(sealInfoArray);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("contactPath", HTMLPATH);
//        map.put("isOffline", "0");
//        map.put("isNeedPreserve", "0");
//        map.put("sealInfoArray", list);
//    }


}
