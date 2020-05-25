package com.smyx.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class AuthUtils {

    private static final String CHARSET_FORMAT = "UTF-8";
    
    /**
     * 发起GET请求，并将响应数据封装为JSON
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static JSONObject doGetJson(String url) throws Exception{
        JSONObject jsonObject = null;
        HttpClientBuilder  builder = HttpClientBuilder.create();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = builder.build().execute(httpGet);
        HttpEntity entity = response.getEntity();
        if(null != entity){
            String result = EntityUtils.toString(entity,CHARSET_FORMAT);
            jsonObject = JSONObject.parseObject(result);
        }
        return jsonObject;
    }

}
