package sort;

/**
 * 排序
 * @author 李斌
 */
public abstract class Sort {
    /**
     * 冒泡排序
     */
    public static int[] bubbleSort(int[] source){
        for (int i = 0, len = source.length - 1; i < len; i++) {
            boolean sorted = true;
            for (int j = 0; j < len-i; j++) {
                if (source[j] > source[j+1]){
                    sorted = false;
                    Util.swap(source, j, j+1);
                }
            }
            if (sorted){
                break;
            }
        }
        return source;
    }

    /**
     * 插入排序
     */
    public static int[] insertSort(int[] source){
        insertFor:
        for (int i = 1, len = source.length; i < len; i++) {
            int insert = source[i];
            for (int j = 0; j < i; j++) {
                if (source[j] > insert){
                    for (int a = i; a > j;) {
                        source[a] = source[--a];
                    }
                    source[j] = insert;
                    continue insertFor;
                }
            }
        }
        return source;
    }

    /**
     * 快速排序（数据量过大会堆栈溢出）
     */
    public static int[] quickSort(int[] source){
        if (source == null || source.length < 2){
            return source;
        }
        quickSortRe(source, 0, source.length - 1);
        return source;
    }
    /**
     * 快速排序递归方法
     */
    private static void quickSortRe(int[] source, int begin, int end){
        int fIndex = begin;
        int flag = source[fIndex];
        for (int i = begin + 1; i <= end; i++) {
            if (source[i] < flag){
                if (i - fIndex == 1){
                    Util.swap(source, i, fIndex);
                    fIndex++;
                } else {
                    Util.swap(source, i, fIndex);
                    fIndex++;
                    Util.swap(source, i, fIndex);
                }
            }
        }
        if (end - fIndex > 1){
            quickSortRe(source, fIndex + 1, end);
        }
        if (fIndex - begin > 1){
            quickSortRe(source, 0, fIndex - 1);
        }
    }
}
