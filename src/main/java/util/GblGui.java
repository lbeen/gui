package util;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * 贵页面简单封装
 *
 * @author 李斌
 */
public abstract class GblGui {
    private JFrame frame;
    private GridBagLayout layout;
    private GridBagConstraints gridBagConstraints;
    private int gridy = 0;

    public GblGui() {
        frame = new JFrame();
        layout = new GridBagLayout();
        frame.setLayout(layout);
        gridBagConstraints = new GridBagConstraints();
        initGui();
        addAction();
        frame.setLocationRelativeTo(null);
    }

    public void run() {
        SwingUtilities.invokeLater(() -> {
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    protected void setTitle(String title) {
        frame.setTitle(title);
    }

    protected void setSize(int width, int height) {
        frame.setSize(width, height);
    }

    protected void gridyAdd(int n) {
        gridy += n;
        gridBagConstraints.gridy = gridy;
    }

    protected void add(Component comp) {
        layout.setConstraints(comp, gridBagConstraints);
        frame.add(comp);
    }

    protected GridBagConstraints getGridBagConstraints() {
        return gridBagConstraints;
    }

    protected JFrame getFrame() {
        return frame;
    }

    protected abstract void initGui();

    protected abstract void addAction();

}
