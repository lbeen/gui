package crawler.gui;

import crawler.parallel.CrawlerRunTimeModel;
import crawler.parallel.ParallelCrawler;
import util.GblGui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 页面
 *
 * @author 李斌
 */
public class CrawlerGui extends GblGui {
    private JComboBox<Web> webSelect;
    private JButton startButton;
    private JButton stopButton;
    private JTextField tempDir;
    private JButton tempDirButton;
    private JTextField outDir;
    private JButton outDirButton;
    private JTextField mainGetThreadCount;
    private JTextField mainParseThreadCount;
    private JTextField startPage;
    private JTextField endPage;
    private CrawlerRunTimeModel detailModel;
    private ParallelCrawler parallelCrawler;

    @Override
    protected void initGui() {
        setTitle("爬虫");
        setSize(700, 350);
        GridBagConstraints gbc = getGridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;

        add(new JLabel("网站："));
        gbc.gridwidth = 2;
        webSelect = new JComboBox<>(Web.webs());
        add(webSelect);
        startButton = new JButton("开始");
        add(startButton);
        stopButton = new JButton("停止");
        stopButton.setVisible(false);
        add(stopButton);

        gridyAdd(1);
        gbc.gridwidth = 1;
        add(new JLabel("临时文件目录："));
        gbc.gridwidth = 2;
        tempDir = new JTextField(40);
        tempDir.setEnabled(false);
        add(tempDir);
        gbc.gridwidth = 1;
        tempDirButton = new JButton("选择文件夹");
        add(tempDirButton);


        gridyAdd(1);
        gbc.gridwidth = 1;
        add(new JLabel("输出文件目录："));
        gbc.gridwidth = 2;
        outDir = new JTextField(40);
        outDir.setEnabled(false);
        add(outDir);
        gbc.gridwidth = 1;
        outDirButton = new JButton("选择文件夹");
        add(outDirButton);

        gridyAdd(1);
        gbc.gridwidth = 1;
        add(new JLabel("主访问线程数："));
        mainGetThreadCount = new JTextField(3);
        add(mainGetThreadCount);
        add(new JLabel("主解析线程数："));
        mainParseThreadCount = new JTextField(3);
        add(mainParseThreadCount);

        gridyAdd(1);
        gbc.gridwidth = 1;
        add(new JLabel("访问开始页码："));
        startPage = new JTextField(3);
        add(startPage);
        add(new JLabel("访问结束页码："));
        endPage = new JTextField(3);
        add(endPage);

        gridyAdd(1);
        detailModel = new CrawlerRunTimeModel();
        JTable table = new JTable(detailModel);
        JScrollPane scrollPane = new JScrollPane(table);
        gbc.fill = GridBagConstraints.BOTH;
        scrollPane.setPreferredSize(new Dimension(200, 150));
        table.setFillsViewportHeight(true);
        gbc.gridheight = 6;
        gbc.gridwidth = 4;
        add(scrollPane);
    }

    @Override
    protected void addAction() {
        tempDirButton.addActionListener(e -> selectFile(tempDir));
        outDirButton.addActionListener(e -> selectFile(outDir));

        startButton.addActionListener(e -> {
            try {
                startButton.setVisible(false);
                stopButton.setVisible(true);
                start();
            } catch (Exception exception){
                exception.printStackTrace(System.out);
                JOptionPane.showMessageDialog(getFrame(), exception.getMessage(), "提示消息", JOptionPane.WARNING_MESSAGE);
            }
        });

        stopButton.addActionListener(e -> {
            parallelCrawler.stop();
            while (true){
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                if (parallelCrawler.isStoped()){
                    startButton.setVisible(true);
                    stopButton.setVisible(false);
                    break;
                }
            }
    });
    }

    private void selectFile(JTextField taget) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(getFrame());
        File file = chooser.getSelectedFile();
        if (file != null) {
            taget.setText(file.getAbsolutePath());
        }
    }

    private void start() throws Exception{
        parallelCrawler = new ParallelCrawler();
        parallelCrawler.setTempDir(tempDir.getText() + "\\");
        parallelCrawler.setOutDir(outDir.getText() + "\\");
        parallelCrawler.setMainGetThreadCount(Integer.parseInt(mainGetThreadCount.getText()));
        parallelCrawler.setMainParseThreadCount(Integer.parseInt(mainParseThreadCount.getText()));
        parallelCrawler.setStartPage(Integer.parseInt(startPage.getText()));
        parallelCrawler.setEndPage(Integer.parseInt(endPage.getText()));
        parallelCrawler.setCrawlerClass(((Web)webSelect.getSelectedItem()).getCrawlerClass());
        parallelCrawler.init();
        detailModel.setCrawlers(parallelCrawler.getCrawlers());
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                detailModel.setCrawlers(parallelCrawler.getCrawlers());
                if (parallelCrawler.isStoped()){
                    timer.cancel();
                    startButton.setVisible(true);
                    stopButton.setVisible(false);
                }
            }
        }, new Date(), 1000);
        parallelCrawler.start();
    }

}
