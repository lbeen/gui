package crawler.parallel;

import lb.util.Lang;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 爬虫线程
 *
 * @author 李斌
 */
public abstract class Crawler extends Thread {
    /**
     * 是否主访问
     */
    private boolean mainGet;
    /**
     * 临时文件队列
     */
    private BlockingQueue<String> files;
    /**
     * 临时文件目录
     */
    private String dir;
    /**
     * 详细URL列表
     */
    private List<String> urls;
    /**
     * 当前线程处理的列表页
     */
    private int currentPage = -1;
    /**
     * 当前线程处理的列表页处理到的条数
     */
    private int item = 1;
    /**
     * 获取列表页数对象
     */
    private Pager pager;
    /**
     * 获取是否结束
     */
    private boolean getFinal = false;
    /**
     * 数据处理器
     */
    private DataHandler dataHandler;

    private List<String[]> datas = new ArrayList<>();

    private int maxDatas;

    @Override
    public void run() {
        try {
            if (mainGet) {
                mainGet();
            } else {
                mainPaser();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 是否主访问
     */
    void setMainGet(boolean mainGet) {
        this.mainGet = mainGet;
    }

    /**
     * 临时文件目录
     */
    void setDir(String dir) {
        this.dir = dir;
    }

    /**
     * 临时文件队列
     */
    void setFiles(BlockingQueue<String> files) {
        this.files = files;
    }

    /**
     * 获取列表页数对象
     */
    void setPager(Pager pager) {
        this.pager = pager;
    }

    /**
     * 数据处理器
     */
    void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public void setMaxDatas(int maxDatas) {
        this.maxDatas = maxDatas;
        this.datas = new ArrayList<>(maxDatas);
    }

    /**
     * 主解析方法，解析失败读取
     */
    private void mainPaser() throws IOException {
        while (true) {
            TaskState taskState = paser();
            if (taskState == TaskState.PASERFINAL) {
                taskState = get();
                if (taskState == TaskState.GETFINAL) {
                    _destroy();
                    break;
                }
            }
        }
    }

    /**
     * 主获取方法，读取失败解析
     */
    private void mainGet() throws IOException {
        while (true) {
            if (getFinal) {
                TaskState taskState = paser();
                if (taskState == TaskState.PASERFINAL) {
                    _destroy();
                    break;
                }
            } else {
                TaskState taskState = get();
                if (taskState == TaskState.CHANGE) {
                    paser();
                } else if (taskState == TaskState.GETFINAL) {
                    getFinal = true;
                    taskState = paser();
                    if (taskState == TaskState.PASERFINAL) {
                        _destroy();
                        break;
                    }
                }
            }
        }
    }

    /**
     * 访问方法存零时文件
     */
    private TaskState get() throws IOException {
        try {
            if (Lang.isEmpty(urls)) {
                if (currentPage == -1) {
                    currentPage = pager.getPage();
                    if (currentPage == -1) {
                        return TaskState.GETFINAL;
                    }
                }
                Document document = Jsoup.connect(getPageUrl(currentPage)).get();
                urls = paserItemUrl(document);
                item = 1;
            }
            for (int i = 0; i < urls.size(); item++) {
                Document document = Jsoup.connect(urls.get(i)).get();
                String filePath = dir + currentPage + "-" + item + ".html";
                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(document.toString());
                }
                urls.remove(i);
                files.offer(filePath);
            }
            currentPage = -1;
            return TaskState.NORMAL;
        } catch (SocketTimeoutException e) {
            e.printStackTrace(System.out);
            return TaskState.CHANGE;
        }
    }


    /**
     * 读取临时文件解析
     */
    private TaskState paser() throws IOException {
        String filePath = files.poll();
        if (filePath == null) {
            return TaskState.PASERFINAL;
        }
        File file = new File(filePath);
        Document document = Jsoup.parse(file, "utf-8");
//        file.delete();
        String[] data = paserPage(document);
        if (data.length > 0) {
            datas.add(data);
            if (datas.size() == maxDatas) {
                dataHandler.handle(datas);
                datas.clear();
            }
        }
        return TaskState.NORMAL;
    }

    private void _destroy(){
        if (datas.size() > 0){
            dataHandler.handle(datas);
        }
    }

    /**
     * 通过当前页码获取当前页url
     */
    protected abstract String getPageUrl(int currentPage);

    /**
     * 通话单页列表页解析详细url列表
     */
    protected abstract List<String> paserItemUrl(Document document);

    /**
     * 解析详细页面
     */
    protected abstract String[] paserPage(Document document);
}
