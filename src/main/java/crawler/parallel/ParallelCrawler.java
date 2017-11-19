package crawler.parallel;

import crawler.cnnvd.CnnvdCrawler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 并行爬虫
 *
 * @author 李斌
 */
public class ParallelCrawler {
    private BlockingQueue<String> files = new LinkedBlockingDeque<>();

    public void run() throws Exception {
        String dir = "F:\\cnnvd\\";
        String excelDir = "F:\\out\\";
        Pager pager = new Pager(1, 2);
        DataHandler dataHandler = new ExcelDataHandler();
        dataHandler.init(excelDir);
        Crawler[] crawlers = new Crawler[2];
        for (int i = 0; i < 2; i++) {
            Crawler crawler = new CnnvdCrawler();
            if (i < 1) {
                crawler.setMainGet(true);
            } else {
                crawler.setMainGet(false);
            }
            crawler.setDataHandler(dataHandler);
            crawler.setDir(dir);
            crawler.setFiles(files);
            crawler.setPager(pager);
            crawler.setMaxDatas(1000);
            crawlers[i] = crawler;
        }

        for (Crawler crawler : crawlers) {
            crawler.start();
        }

        for (Crawler crawler : crawlers) {
            crawler.join();
        }

    }


}
