package com.xter.reptile;

import io.webfolder.cdp.internal.gson.JsonArray;
import io.webfolder.cdp.internal.gson.JsonObject;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class Momo {

    private static final String URL_MOMO = "https://momotk.uno";

    public static void main(String[] args) throws URISyntaxException, IOException {
//        new Momo().momoPosts(1);
        new Momo().momoTest();
    }


    private JsonArray momoPosts(int page) throws URISyntaxException {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();


        //1.创建URIBuilder 内容为未设置参数的地址
        URIBuilder uriBuilder = new URIBuilder(URL_MOMO);

        //2.设置参数
        uriBuilder.setParameter("page", String.valueOf(page));

        //如果为多个参数，则使用多次setParameter
        //eg：uriBuilder.setParameter("","").setParameter("","")...;

        //创建HttpGet对象，设置url访问地址
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        Header header = new BasicHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36 QIHU 360EE");
        httpGet.addHeader(header)
        ;
        //使用httpClient对象发起请求，获取response
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);

            //解析响应
            if (response.getStatusLine().getStatusCode() == 200) {
                String html = EntityUtils.toString(response.getEntity(), "UTF-8");
//                System.out.println(html);

                Document document = Jsoup.parse(html);

                Elements tagArticles = document.select("article");
//                System.out.println(tagArticles.html());
                for (Element element:tagArticles){
                    System.out.println(element.select("img"));
                    System.out.println("--------");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {//释放资源
            //判断response是否为null，为null则代表未获取response，不为null则要释放资源

            if (response != null) {
                try {
                    response.close();

                    //重点：释放掉资源后，将它重新赋值为null，因为GC回收资源优先回收null值的
                    response = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private void momoTest() throws IOException {

        Document document = Jsoup.parse(new File("I:\\360Downloads\\momo.html"));

        Elements tagArticles = document.select("article");
//                System.out.println(tagArticles.html());
        for (Element element:tagArticles){
            System.out.println(element.select("a").first().attr("href"));
            System.out.println(element.select("img").first().attr("data-src"));
            System.out.println("--------");
        }
    }
}
