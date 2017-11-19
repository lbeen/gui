package crawler.cnnvd;

import crawler.cnnvd.bean.Ldxx;
import crawler.parallel.Crawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 国家信息漏洞安全平台爬虫
 *
 * @author 李斌
 */
public class CnnvdCrawler extends Crawler {
    @Override
    protected String getPageUrl(int currentPage) {
        return "http://www.cnnvd.org.cn/web/vulnerability/querylist.tag?pageno=" + currentPage;
    }

    @Override
    protected List<String> paserItemUrl(Document document) {
        Element list_list = document.getElementsByClass("list_list").get(0);
        Elements as = list_list.getElementsByClass("a_title2");
        List<String> urls = new ArrayList<>();
        for (Element a : as) {
            urls.add("http://www.cnnvd.org.cn/" + a.attr("href"));
        }
        return urls;
    }

    @Override
    public String[] paserPage(Document ldxxPage) {
        Elements xqs = ldxxPage.select("div[class=detail_xq w770]");
        if (xqs.isEmpty()) {
            return new String[0];
        }
        Ldxx ldxx = new Ldxx();
        ldxx.setID("");
        String[] data = new String[15];
        paserLdxqxx(xqs.get(0), data);
        paserDetail(ldxxPage, data);
        return data;
    }


    private void paserLdxqxx(Element xq, String[] data) {
        data[0] = xq.getElementsByTag("h2").html();
        Elements lis = xq.select("ul li");
        data[1] = lis.get(0).getElementsByTag("span").html().replace("CNNVD编号：", "");
        data[2] = lis.get(1).getElementsByTag("a").text();
        data[3] = lis.get(2).getElementsByTag("a").html();
        data[4] = lis.get(3).getElementsByTag("a").html();
        data[5] = lis.get(4).getElementsByTag("a").html();
        data[6] = lis.get(5).getElementsByTag("a").html();
        data[7] = lis.get(6).getElementsByTag("a").html();
        data[8] = lis.get(7).getElementsByTag("span").html().replace("厂&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商：", "");
        data[9] = getLdly(lis.get(8));
    }

    private String getLdly(Element li) {
        Elements as = li.getElementsByClass("a");
        if (as.isEmpty()) {
            return li.html().replace("<span>漏洞来源：</span>", "");
        }
        return as.html();
    }

    private void paserDetail(Document ldxxPage, String[] data) {
        Elements details = ldxxPage.select("div.d_ldjj");
        data[10] = details.get(0).select("p").html();
        data[11] = details.get(1).select("p").html();
        data[12] = details.get(2).select("p").html();
        data[13] = details.get(3).select("p").html();
        data[14] = details.get(4).select("p").html();
    }
}
