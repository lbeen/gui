package crawler.parallel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 并行爬虫
 *
 * @author 李斌
 */
public class ParallelCrawler {
    /**
     * 临时文件队列
     */
    private BlockingQueue<String> files = new LinkedBlockingDeque<>();
    /**
     * 临时文件保存文件夹
     */
    private String tempDir;
    /**
     * 输出文件夹
     */
    private String outDir;
    /**
     * 主访问线程数
     */
    private int mainGetThreadCount;
    /**
     * 主解析线程数
     */
    private int mainParseThreadCount;
    /**
     * 开始页码
     */
    private int startPage;
    /**
     * 结束页码
     */
    private int endPage;
    /**
     * 爬虫class
     */
    private Class<? extends Crawler> crawlerClass;
    /**
     * 爬虫线程数组
     */
    private Crawler[] crawlers;

    /**
     * 临时文件保存文件夹
     */
    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    /**
     * 输出文件夹
     */
    public void setOutDir(String outDir) {
        this.outDir = outDir;
    }

    /**
     * 主访问线程数
     */
    public void setMainGetThreadCount(int mainGetThreadCount) {
        this.mainGetThreadCount = mainGetThreadCount;
    }

    /**
     * 主解析线程数
     */
    public void setMainParseThreadCount(int mainParseThreadCount) {
        this.mainParseThreadCount = mainParseThreadCount;
    }

    /**
     * 开始页码
     */
    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    /**
     * 结束页码
     */
    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    /**
     * 爬虫class
     */
    public void setCrawlerClass(Class<? extends Crawler> crawlerClass) {
        this.crawlerClass = crawlerClass;
    }

    /**
     * 爬虫线程数组
     */
    public Crawler[] getCrawlers() {
        return crawlers;
    }

    public void init() throws Exception {
        Pager pager = new Pager(startPage, endPage);
        OutPuter outPuter = new ExcelOutPuter();
        outPuter.init(outDir);
        int totalThreads = mainGetThreadCount + mainParseThreadCount;
        crawlers = new Crawler[totalThreads];
        for (int i = 0; i < totalThreads; i++) {
            Crawler crawler = crawlerClass.newInstance();
            if (i < mainGetThreadCount) {
                crawler.setName("mainGet-" + i);
                crawler.setMainGet(true);
            } else {
                crawler.setName("mainPaser-" + i);
                crawler.setMainGet(false);
            }
            crawler.setOutPuter(outPuter);
            crawler.setTempDir(tempDir);
            crawler.setFiles(files);
            crawler.setPager(pager);
            crawler.setMaxDatas(10000);
            crawlers[i] = crawler;
        }
    }

    public void start() throws Exception {
        for (Crawler crawler : crawlers) {
            crawler.start();
        }
    }
}
