package design.strategy;

/**
 * 打折结算
 *
 * @author 李斌
 */
public class RebateCash implements Cash {
    private double discount;

    RebateCash(double discount) {
        this.discount = discount;
    }

    @Override
    public double getTotal(int count, int price) {
        return count * price * discount;
    }
}
