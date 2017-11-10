package sort;

import java.util.Random;
import java.util.function.Function;

/**
 * 排序用到的工具
 * @author 李斌
 */
public abstract class Util {
    /**
     * 交换数组中两个位置的元素
     */
    static void swap(int[] source, int i, int j){
        int temp = source[i];
        source[i] = source[j];
        source[j] = temp;
    }
    /**
     * 创建一个数组
     */
    public static int[] creatArr(int len, int max){
        int[] arr = new int[len];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < len; i++) {
            arr[i] = random.nextInt(max);
        }
        return arr;
    }
    /**
     * 计时
     */
    public static int[] time(Function<Object, int[]> sort, String name){
        long start = System.currentTimeMillis();
        int[] sortedArr = sort.apply(new Object());
        long end = System.currentTimeMillis();
        System.out.println(name + "用时" + (end - start));
        return sortedArr;
    }
    /**
     * 验证数组是否从小到大排序
     */
    public static boolean checkArrAsc(int[] arr){
        for (int i = 0, len = arr.length - 1; i < len; i++) {
            if (arr[i] > arr[i+1]){
                return false;
            }
        }
        return true;
    }
    /**
     * 复制一个数组
     */
    public static int[] copy(int[] source){
        int len = source.length;
        int[] copy = new int[len];
        System.arraycopy(source, 0, copy, 0, len);
        return copy;
    }
    /**
     * 打印数组
     */
    public static void print(int[] arr){
        for (int i : arr) {
            System.out.print(i + ",");
        }
        System.out.println();
    }
}
