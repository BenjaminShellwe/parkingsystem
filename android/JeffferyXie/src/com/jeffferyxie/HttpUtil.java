package com.jeffferyxie;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ma on 2016/4/19.
 */
public class HttpUtil {
    //��װ�ķ���������
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        if (!HttpUtil.isNetworkAvailable()){
            //����д��Ӧ���������ô���
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(address);
                    //ʹ��HttpURLConnection
                    connection = (HttpURLConnection) url.openConnection();
                    //���÷����Ͳ���
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    //��ȡ���ؽ��
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    //�ɹ���ص�onFinish
                    if (listener != null){
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //�����쳣��ص�onError
                    if (listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    //��װ��������������URL
    public static String getURLWithParams(String address,HashMap<String,String> params) throws UnsupportedEncodingException {
        //���ñ���
        final String encode = "UTF-8";
        StringBuilder url = new StringBuilder(address);
        url.append("?");
        //��map�е�key��value�������URL��
        for(Map.Entry<String, String> entry:params.entrySet())
        {
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue(), encode));
            url.append("&");
        }
        //ɾ�����һ��&
        url.deleteCharAt(url.length() - 1);
        return url.toString();
    }
    //�жϵ�ǰ�����Ƿ����
    public static boolean isNetworkAvailable(){
        return true;
    }
}

