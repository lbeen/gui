package money;

/**
 * @author 李斌
 */
public class Dic {
    private String name;
    private String value;

    public Dic(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Dic[] types(){
        return new Dic[]{new Dic("支出","1"),new Dic("收入","2")};
    }

    public static Dic[] items(){
        return new Dic[]{new Dic("三餐","01"),new Dic("烟","02"),new Dic("玩乐","03"),new Dic("零食","04"),new Dic("琐碎","05")};
    }
}
