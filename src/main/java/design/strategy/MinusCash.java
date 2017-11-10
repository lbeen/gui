package design.strategy;

/**
 * 满减结算
 *
 * @author 李斌
 */
public class MinusCash implements Cash {
    private int condition;
    private int minus;

    MinusCash(int condition, int minus) {
        this.condition = condition;
        this.minus = minus;
    }

    @Override
    public double getTotal(int count, int price) {
        int total = count * price;
        return total >= condition ? total - minus : total;
    }
}
