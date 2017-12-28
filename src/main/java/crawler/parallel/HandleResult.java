package crawler.parallel;

/**
 * 爬虫操作结果状态
 *
 * @author 李斌
 */
public enum HandleResult {
    /**
     * 正常
     */
    NORMAL,
    /**
     * 切换
     */
    CHANGE,
    /**
     * 读取结束
     */
    GETFINAL,
    /**
     * 解析结束
     */
    PASERFINAL
}
