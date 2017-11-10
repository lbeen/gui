package design.strategy;

/**
 * 结算借口
 *
 * @author 李斌
 */
public interface Cash {
    double getTotal(int count, int price);
}
