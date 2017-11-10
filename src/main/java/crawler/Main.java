package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * 爬虫
 *
 * @author 李斌
 */
public class Main {
    public static void main(String[] args) {
        String url = "http://www.cnnvd.org.cn/web/xxk/bdxqById.tag?id=76094";
        Document document = Get_Url(url);
        Elements es = document.getElementsByClass("gl-item");
        for (Element e : es) {
            System.out.println(e.getElementsByClass("p-price").text());
        }
        System.out.println(document);
    }

    private static Document Get_Url(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
