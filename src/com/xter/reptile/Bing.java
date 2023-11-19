package com.xter.reptile;

import static java.util.Locale.ENGLISH;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Bing {

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("http://www.itcast.cn/");

        CloseableHttpResponse response = httpClient.execute(httpGet);

        if (response.getStatusLine().getStatusCode() == 200){
            String html = EntityUtils.toString(response.getEntity(),"UTF-8");

            System.out.println(html);
        }

        httpClient.close();
        httpGet.clone();
    }
}