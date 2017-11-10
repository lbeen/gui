package design.strategy;

/**
 * 商品
 *
 * @author 李斌
 */
class Item {
    static Item[] getItems() {
        return new Item[]{new Item("a", 10), new Item("b", 20), new Item("c", 30)};
    }

    private String name;
    private int price;

    private Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }

    String getName() {
        return name;
    }

    int getPrice() {
        return price;
    }
}
