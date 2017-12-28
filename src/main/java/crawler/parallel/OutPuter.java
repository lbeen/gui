package crawler.parallel;

import java.util.List;

/**
 * 数据输出处理器
 *
 * @author 李斌
 */
public interface OutPuter {
    /**
     * 初始化操作
     */
    void init(String... args);

    /**
     * 输出数据
     */
    void out(List<String[]> datas);

    /**
     * 结束操作
     */
    void destroy();
}
