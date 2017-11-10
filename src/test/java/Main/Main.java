package Main;

import design.strategy.MoneyGui;
import util.GblGui;

/**
 * @author 李斌
 */
public class Main {
    public static void main(String[] args) {
        GblGui gblGui = new MoneyGui();
        gblGui.run();
    }
}
