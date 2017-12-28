package crawler.parallel;

import javax.swing.table.DefaultTableModel;

/**
 * @author 李斌
 */
public class CrawlerRunTimeModel extends DefaultTableModel {
    private Crawler[] crawlers;
    private Object[][] data;

    public CrawlerRunTimeModel() {
        super(new String[][]{}, new String[]{"线程名", "状态", "访问文件数", "解析文件数", "输出文件数"});
    }

    public void setCrawlers(Crawler[] crawlers) {
        this.crawlers = crawlers;
        this.setRowCount(0);
        int len = crawlers.length;
        this.data = new Object[len][5];
        for (int i = 0; i < len; i++) {
            Crawler crawler = crawlers[i];
            Object[] row = new Object[]{
                    crawler.getName(),
                    TaskState.getName(crawler.getTaskState()),
                    crawler.getGets(),
                    crawler.getParses(),
                    crawler.getOuts()
            };
            data[i] = row;
            addRow(row);
        }

        System.out.println("刷新table2");
    }

    public void refresh() {
        int len = crawlers.length;
        for (int i = 0; i < len; i++) {
            Object[] row = this.data[i];
            Crawler crawler = crawlers[i];
            row[1] = TaskState.getName(crawler.getTaskState());
            row[2] = crawler.getGets();
            row[3] = crawler.getParses();
            row[4] = crawler.getOuts();
            row[2] = crawler.getGets();
        }
        this.dataVector = convertToVector(this.data);
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
