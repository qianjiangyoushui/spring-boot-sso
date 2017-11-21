package com.test.web1.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import java.net.URI;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/13.
 */
public class HttpUtil {
    public static String httpGet(String url, HttpMethod method,
                          Map<String, String> params, HttpEntity requestEntity) {
        HttpClient client = HttpClientBuilder.create().build();
        try {
            HttpGet httpGet = new HttpGet();
            URI uri = URI.create(url);
            httpGet.setURI(uri);
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String respBody = EntityUtils.toString(entity);
            return respBody;
        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

}
