package crawler;

import crawler.parallel.ParallelCrawler;

/**
 * 爬虫main
 *
 * @author 李斌
 */
public class Main {

    public static void main(String[] args) throws Exception {
        ParallelCrawler parallelCrawler = new ParallelCrawler();
        parallelCrawler.run();
    }
}
