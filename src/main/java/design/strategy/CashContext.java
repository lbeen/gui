package design.strategy;

/**
 * 结算配置
 *
 * @author 李斌
 */
public class CashContext {
    private String name;
    private Cash cash;

    private CashContext(String name, Cash cash) {
        this.name = name;
        this.cash = cash;
    }

    static CashContext[] getContexts() {
        return new CashContext[]{
                new CashContext("原价", new RebateCash(1)),
                new CashContext("七折", new RebateCash(0.7)),
                new CashContext("满100减20", new MinusCash(100, 20)),
                new CashContext("满500减100", new MinusCash(500, 100))
        };
    }

    @Override
    public String toString() {
        return name;
    }

    double getResult(int count, int price) {
        return cash.getTotal(count, price);
    }


}
