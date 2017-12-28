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
    private String tempDir;
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
    private OutPuter outPuter;
    /**
     * 数据
     */
    private List<String[]> datas = new ArrayList<>();
    /**
     * 一次处理最大数据数
     */
    private int maxDatas;
    /**
     * 线程状态
     */
    private TaskState taskState = TaskState.READING;
    /**
     * 错误异常
     */
    private Exception error;
    /**
     * 访问数
     */
    private int gets = 0;
    /**
     * 解析数
     */
    private int parses = 0;
    /**
     * 输出数
     */
    private int outs = 0;

    /**
     * 是否主访问
     */
    void setMainGet(boolean mainGet) {
        this.mainGet = mainGet;
    }

    /**
     * 临时文件目录
     */
    void setTempDir(String tempDir) {
        this.tempDir = tempDir;
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
    void setOutPuter(OutPuter outPuter) {
        this.outPuter = outPuter;
    }

    /**
     * 一次处理最大数据数
     */
    void setMaxDatas(int maxDatas) {
        this.maxDatas = maxDatas;
        this.datas = new ArrayList<>(maxDatas);
    }

    /**
     * 线程状态
     */
    public TaskState getTaskState() {
        return taskState;
    }

    /**
     * 错误异常
     */
    public Exception getError() {
        return error;
    }

    /**
     * 访问数
     */
    public int getGets() {
        return gets;
    }

    /**
     * 解析数
     */
    public int getParses() {
        return parses;
    }

    /**
     * 输出数
     */
    public int getOuts() {
        return outs;
    }

    @Override
    public void run() {
        try {
            if (mainGet) {
                mainGet();
            } else {
                mainPaser();
            }
            if (!datas.isEmpty()) {
                out();
            }
            taskState = TaskState.END;
        } catch (IOException e) {
            taskState = TaskState.ERROR;
            error = e;
            throw new RuntimeException(e);
        }
    }

    /**
     * 主解析方法，解析失败读取
     */
    private void mainPaser() throws IOException {
        while (true) {
            HandleResult handleResult = paser();
            if (handleResult == HandleResult.PASERFINAL) {
                handleResult = get();
                if (handleResult == HandleResult.GETFINAL) {
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
                HandleResult handleResult = paser();
                if (handleResult == HandleResult.PASERFINAL) {
                    break;
                }
            } else {
                HandleResult handleResult = get();
                if (handleResult == HandleResult.CHANGE) {
                    paser();
                } else if (handleResult == HandleResult.GETFINAL) {
                    getFinal = true;
                    handleResult = paser();
                    if (handleResult == HandleResult.PASERFINAL) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * 访问方法存零时文件
     */
    private HandleResult get() throws IOException {
        taskState = TaskState.GETING;
        try {
            if (Lang.isEmpty(urls)) {
                if (currentPage == -1) {
                    currentPage = pager.getPage();
                    if (currentPage == -1) {
                        return HandleResult.GETFINAL;
                    }
                }
                Document document = Jsoup.connect(getPageUrl(currentPage)).get();
                urls = paserItemUrl(document);
                item = 1;
            }
            for (int i = 0; i < urls.size(); item++) {
                Document document = Jsoup.connect(urls.get(i)).get();
                String filePath = tempDir + currentPage + "-" + item + ".html";
                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(document.toString());
                }
                urls.remove(i);
                files.offer(filePath);
                gets++;
            }
            currentPage = -1;
            return HandleResult.NORMAL;
        } catch (SocketTimeoutException e) {
//            e.printStackTrace(System.out);
            return HandleResult.CHANGE;
        }
    }


    /**
     * 读取临时文件解析
     */
    private HandleResult paser() throws IOException {
        taskState = TaskState.PARSEING;
        String filePath = files.poll();
        if (filePath == null) {
            return HandleResult.PASERFINAL;
        }
        File file = new File(filePath);
        Document document = Jsoup.parse(file, "utf-8");
        file.delete();
        String[] data = paserPage(document);
        if (data.length > 0) {
            datas.add(data);
            parses++;
            if (datas.size() == maxDatas) {
                out();
                datas.clear();
            }
        }
        return HandleResult.NORMAL;
    }

    private void out() {
        taskState = TaskState.OUTING;
        outPuter.out(datas);
        outs += datas.size();
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
