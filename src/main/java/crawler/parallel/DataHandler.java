package crawler.parallel;

import java.util.List;

/**
 * @author 李斌
 */
public interface DataHandler {
    void init(String... args);

    void handle(List<String[]> datas);

    void destroy();
}
