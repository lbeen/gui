package design.strategy;

import util.GblGui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

/**
 * 页面
 *
 * @author 李斌
 */
public class MoneyGui extends GblGui {
    private JComboBox<Item> itemSelect;
    private JComboBox<CashContext> activitySelect;
    private JLabel djText;
    private JButton resetBtn;
    private JTextField slText;
    private JButton confirmBtn;
    private JLabel totalText;
    private DefaultTableModel detailModel;

    private int total = 0;

    @Override
    protected void initGui() {
        setTitle("商场收银系统");
        setSize(300, 400);
        GridBagConstraints gbc = getGridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;

        add(new JLabel("商品："));
        gbc.gridwidth = 2;
        activitySelect = new JComboBox<>(CashContext.getContexts());
        add(activitySelect);

        gridyAdd(1);
        gbc.gridwidth = 1;
        add(new JLabel("活动："));
        gbc.gridwidth = 2;
        itemSelect = new JComboBox<>(Item.getItems());
        add(itemSelect);

        gridyAdd(1);
        gbc.gridwidth = 1;
        add(new JLabel("单价："));
        djText = new JLabel(Integer.toString(Item.getItems()[0].getPrice()));
        add(djText);
        resetBtn = new JButton("重置");
        add(resetBtn);

        slText = new JTextField(10);
        confirmBtn = new JButton("确定");
        gridyAdd(1);
        add(new JLabel("数量："));
        add(slText);
        add(confirmBtn);

        gridyAdd(1);
        detailModel = new DefaultTableModel(new String[][]{}, new String[]{"商品", "单价", "数量", "活动", "总价"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(detailModel);
        JScrollPane scrollPane = new JScrollPane(table);
        gbc.fill = GridBagConstraints.BOTH;
        scrollPane.setPreferredSize(new Dimension(200, 40));
        table.setFillsViewportHeight(true);
        gbc.gridheight = 4;
        gbc.gridwidth = 3;
        add(scrollPane);


        gbc.fill = GridBagConstraints.REMAINDER;
        totalText = new JLabel("0");
        gridyAdd(5);
        gbc.gridwidth = 2;
        add(new JLabel("总价："));
        add(totalText);
    }

    @Override
    protected void addAction() {
        itemSelect.addItemListener(e -> {
            Item item = (Item) e.getItem();
            djText.setText(Integer.toString(item.getPrice()));
        });

        confirmBtn.addActionListener(e -> {
            int dj = Integer.parseInt(djText.getText());
            int sl;
            try {
                sl = Integer.parseInt(slText.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(getFrame(), "数量请输入整数", "提示消息", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Item item = (Item) itemSelect.getSelectedItem();
            CashContext cashContext = (CashContext) activitySelect.getSelectedItem();
            double total = cashContext.getResult(sl, dj);
            detailModel.addRow(new String[]{item.getName(), Integer.toString(dj), Integer.toString(sl),
                    cashContext.toString(), Double.toString(total)});
            this.total += total;
            totalText.setText(Integer.toString(this.total));
        });

        resetBtn.addActionListener(e -> {
            activitySelect.setSelectedIndex(0);
            itemSelect.setSelectedIndex(0);
            slText.setText("");
            totalText.setText("0");
            detailModel.setRowCount(0);
            this.total = 0;
        });
    }

}
