package crawler.parallel;

/**
 * 任务状态
 *
 * @author 李斌
 */
public enum TaskState {
    /**
     * 准备
     */
    READING,
    /**
     * 访问
     */
    GETING,
    /**
     * 解析
     */
    PARSEING,
    /**
     * 输出
     */
    OUTING,
    /**
     * 错误
     */
    ERROR,
    /**
     * 结束
     */
    END;

    public static String getName(TaskState taskState) {
        switch (taskState) {
            case READING:
                return "准备";
            case GETING:
                return "访问";
            case PARSEING:
                return "解析";
            case OUTING:
                return "输出";
            case ERROR:
                return "错误";
            case END:
                return "结束";
            default:
                return "";
        }
    }
}
