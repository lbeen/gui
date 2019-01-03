package money.gui;

import crawler.parallel.CrawlerRunTimeModel;
import crawler.parallel.ParallelCrawler;
import money.Dic;
import util.GblGui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;

/**
 * 页面
 *
 * @author 李斌
 */
public class MoneyGui extends GblGui {
    private JComboBox<Dic> typeSelect;
    private JComboBox<Dic> itemSelect;
    private JTextField money;
    private JTextField remark;

    @Override
    protected void initGui() {
        setTitle("消费记录");
        setSize(700, 200);
        GridBagConstraints gbc = getGridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;

        add(new JLabel("类型："));
        gbc.gridwidth = 2;
        typeSelect = new JComboBox<>(Dic.types());
        add(typeSelect);
        add(new JLabel("项目："));
        gbc.gridwidth = 2;
        itemSelect = new JComboBox<>(Dic.items());
        add(itemSelect);

        gridyAdd(1);

        add(new JLabel("钱数："));
        gbc.gridwidth = 2;
        money = new JTextField(8);
        add(money);
        add(new JLabel("备注："));
        gbc.gridwidth = 2;
        remark = new JTextField(30);
        add(remark);
    }

    @Override
    protected void addAction() {

    }
}
