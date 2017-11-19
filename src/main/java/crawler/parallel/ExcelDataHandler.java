package crawler.parallel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author 李斌
 */
public class ExcelDataHandler implements DataHandler {
    private String excelDir;

    private int fileIndex = 1;

    @Override
    public void init(String... args) {
        this.excelDir = args[0];
    }

    @Override
    public void handle(List<String[]> datas) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        for (int i = 0, size = datas.size(); i < size; i++) {
            HSSFRow row = sheet.createRow(i);
            String[] data = datas.get(i);
            int len = data.length;
            for (short j = 0; j < len; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(new HSSFRichTextString(data[j]));
            }
        }
        try (FileOutputStream out = new FileOutputStream(excelDir + getFileIndex() + ".xlsx")) {
            workbook.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {}

    private synchronized int getFileIndex() {
        return fileIndex++;
    }
}
