package crawler.gui;

import crawler.cnnvd.CnnvdCrawler;
import crawler.parallel.Crawler;

/**
 * url下拉数据对象
 *
 * @author 李斌
 */
class Web {
    static Web[] webs() {
        return new Web[]{new Web("国家信息安全漏洞库漏洞信息", CnnvdCrawler.class)};
    }

    private String name;
    private Class<? extends Crawler> crawlerClass;

    private Web(String name, Class<? extends Crawler> crawlerClass) {
        this.name = name;
        this.crawlerClass = crawlerClass;
    }

    @Override
    public String toString() {
        return name;
    }

    String getName() {
        return name;
    }

    public Class<? extends Crawler> getCrawlerClass() {
        return crawlerClass;
    }
}
