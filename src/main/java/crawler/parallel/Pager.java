package crawler.parallel;

/**
 * 获取当前页
 *
 * @author 李斌
 */
class Pager {
    /**
     * 结束页码
     */
    private int endPage;
    /**
     * 当前页码
     */
    private int nowPage;

    Pager(int startPage, int endPage) {
        this.endPage = endPage;
        this.nowPage = startPage;
    }

    /**
     * 获取当前页
     */
    synchronized int getPage() {
        if (nowPage > endPage) {
            return -1;
        }
        return nowPage++;
    }
}
