package com.guangzhou.weiwong.accountbook.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Tower on 2016/4/28.
 */
public class HttpUtil {
    private final static String TAG = "HttpUtil";
    public static String httpPostWithJSON(String path, String json) {
        String httpResponseResult = "";
        try {
            String params = "data=" + URLEncoder.encode(json, "UTF-8");
//            String params = "data=" + json;
            byte[] postData = params.getBytes();
            URL url = new URL(path);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(5 * 1000);
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestMethod("POST");
//            urlConn.setInstanceFollowRedirects(true);
            urlConn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConn.connect();
            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
            dos.write(postData);
            dos.flush();
            dos.close();
            if (urlConn.getResponseCode() == 200){
                InputStream is = urlConn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len = 0;
                byte buffer[] = new byte[1024];
                while ((len = is.read(buffer)) != -1){
                    baos.write(buffer, 0, len);
                }
                is.close();
                baos.close();
                httpResponseResult = new String(baos.toByteArray());
//                Result result = new Gson().fromJson(httpResponseResult, Result.class);
                Log.d(TAG, "httpResponseResult = " + (httpResponseResult));
//                Log.d(TAG, "httpResponseResult = " + result);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponseResult;
    }

    static String unicode2String(String unicodeStr) {
        StringBuilder sb = new StringBuilder();
        String str[] = unicodeStr.split("//u");
        for (int i = 0; i < str.length; i++) {
            if (str[i].equals("")) continue;
            char c = (char) Integer.parseInt(str[i].trim(), 16);
            sb.append(c);
        }
        return sb.toString();
    }

//    void temp(){
//        if(HttpURLConnection.HTTP_OK==resultCode){
//            StringBuffer sb=new StringBuffer();
//            String readLine=new String();
//            BufferedReader responseReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
//            while((readLine=responseReader.readLine())!=null){
//                sb.append(readLine).append("\n");
//            }
//            responseReader.close();
//            System.out.println(sb.toString());
//        }
//    }
}
