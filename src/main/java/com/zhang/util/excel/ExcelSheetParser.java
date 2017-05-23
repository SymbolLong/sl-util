package com.zhang.util.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author senzhu
 * @version 1.0
 * @ClassName: ExcelSheetParser
 * @Description: excel解析。
 * @date 2014-11-14 下午03:55:33
 */
public class ExcelSheetParser {
    private Workbook workbook;

    public ExcelSheetParser() {

    }

    /**
     * 实例化解析对象。
     *
     * @param excelFile 待解析的文件。
     * @throws IOException
     */
    public ExcelSheetParser(File excelFile) throws IOException {
        try {
            workbook = new XSSFWorkbook(new FileInputStream(excelFile));
        } catch (Exception e) {
            workbook = new HSSFWorkbook(new FileInputStream(excelFile));
        }
    }

    /**
     * 实例化解析对象。
     *
     * @param in 待解析的文件。
     * @throws IOException
     */
    public ExcelSheetParser(InputStream in) throws IOException {
        try {
            workbook = new XSSFWorkbook(in);
        } catch (Exception e) {
            workbook = new HSSFWorkbook(in);
        }
    }

    /**
     * 实例化解析对象。
     *
     * @param in 待解析的文件流。
     * @throws IOException
     */
    public void excel2003SheetParser(InputStream in) throws IOException {
        workbook = new HSSFWorkbook(in);
    }

    /**
     * 实例化解析对象。
     *
     * @param in 待解析的文件流。
     * @throws IOException
     */
    public void excel2007SheetParser(InputStream in) throws IOException {
        workbook = new XSSFWorkbook(in);
    }

    /**
     * 根据所提供的文件解析成java的数据集合。
     *
     * @param sheetNumber excel里的sheet，从0开始
     * @param skipRows    假如有头部信息，需要指定跳过头几行。
     * @return 数据的集合。
     */
    public List<List<Object>> getDatasInSheet(int sheetNumber, int skipRows) {

        List<List<Object>> result = new ArrayList<List<Object>>();

        // 获取指定的sheet
        Sheet sheet = workbook.getSheetAt(sheetNumber);

        int rowCount = sheet.getLastRowNum();
        int rowIndex = skipRows == 0 ? sheet.getFirstRowNum() : skipRows;

        if (rowCount < 1) {
            return result;
        }

        for (; rowIndex <= rowCount; rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            if (row != null) {
                List<Object> rowData = new ArrayList<Object>();
                int columnCount = row.getLastCellNum();
                int columnIndex = row.getFirstCellNum();

                for (; columnIndex < columnCount; columnIndex++) {
                    Cell cell = row.getCell(columnIndex);
                    Object cellStr = this.getCellString(cell);
                    rowData.add(cellStr);
                }
                result.add(rowData);
            }
        }
        return result;
    }

    /**
     * 根据所提供的文件解析成java的数据集合。
     *
     * @param sheetNumber excel里的sheet，从0开始
     * @param skipRows    假如有头部信息，需要指定跳过头几行。
     * @return 数据的集合。
     */
    public List<List<CellValue>> getDatasInSheet2CellValue(int sheetNumber, int skipRows) {

        List<List<CellValue>> result = new ArrayList<List<CellValue>>();

        // 获取指定的sheet
        Sheet sheet = workbook.getSheetAt(sheetNumber);

        int rowCount = sheet.getLastRowNum();
        int rowIndex = skipRows == 0 ? sheet.getFirstRowNum() : skipRows;

        if (rowCount < 1) {
            return result;
        }

        for (; rowIndex <= rowCount; rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            if (row != null) {
                List<CellValue> rowData = new ArrayList<CellValue>();
                int columnCount = row.getLastCellNum();
                int columnIndex = row.getFirstCellNum();

                for (; columnIndex < columnCount; columnIndex++) {
                    Cell cell = row.getCell(columnIndex);
                    Object cellStr = this.getCellString(cell);
                    CellValue cellvalue = new CellValue(columnIndex, cellStr);
                    rowData.add(cellvalue);
                }
                result.add(rowData);
            }
        }
        return result;
    }

    private Object getCellString(Cell cell) {
        Object result = null;
        if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getRichStringCellValue().getString();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    result = getNumbericValue2String(cell);
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    result = getNumbericValue2String(cell);
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = "";
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    result = "";
                    break;
                default:
                    result = "";
            }
        }
        return result;
    }

    //处理科学计数法
    private String getNumbericValue2String(Cell cell) {
        DecimalFormat df = new DecimalFormat("0");
        String whatYourWant = df.format(cell.getNumericCellValue());
        return whatYourWant;
    }

    public class CellValue {
        private int index;

        private Object value;

        private CellValue() {
            super();
        }

        private CellValue(int index, Object value) {
            super();
            this.index = index;
            this.value = value;
        }

        /**
         * @return the index
         */
        public int getIndex() {
            return index;
        }

        /**
         * @param index the index to set
         */
        public void setIndex(int index) {
            this.index = index;
        }

        /**
         * @return the value
         */
        public Object getValue() {
            return value;
        }

        /**
         * @param value the value to set
         */
        public void setValue(Object value) {
            this.value = value;
        }
    }


}
