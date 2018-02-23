package crawler.parallel;

import javax.swing.table.DefaultTableModel;

/**
 * @author 李斌
 */
public class CrawlerRunTimeModel extends DefaultTableModel {
    public CrawlerRunTimeModel() {
        super(new String[][]{}, new String[]{"线程名", "状态", "访问文件数", "解析文件数", "输出文件数"});
    }

    public void setCrawlers(Crawler[] crawlers) {
        this.setRowCount(0);
        for (Crawler crawler : crawlers) {
            Object[] row = new Object[]{
                    crawler.getName(),
                    TaskState.getName(crawler.getTaskState()),
                    crawler.getGets(),
                    crawler.getParses(),
                    crawler.getOuts()
            };
            addRow(row);
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
