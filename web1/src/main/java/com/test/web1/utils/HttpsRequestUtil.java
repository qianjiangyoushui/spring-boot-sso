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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/13.
 */
public class HttpsRequestUtil {
    public static String httpGet(String url, HttpMethod method,
                          Map<String, String> params, HttpEntity requestEntity) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpRequestBase request = null;

        try {
            if (HttpMethod.GET.equals(method)) {
                request = new HttpGet();
            } else if (HttpMethod.POST.equals(method)) {
                request = new HttpPost();
                if (requestEntity != null) {
                    ((HttpPost) request).setEntity(requestEntity);
                }
            }
            URIBuilder builder = new URIBuilder(url);

            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            request.setURI(builder.build());

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            String respBody = EntityUtils.toString(entity);
            return respBody;
        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

}
