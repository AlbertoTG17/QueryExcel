package com.excel.ExcelToTxt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AppD {

    public static void main(String[] args) {

        FileInputStream file = null;
        XSSFWorkbook workbook = null;

        // "/home/atostado/Descargas/Arbol categorias_Particulares.xlsx

        if (args.length < 1) {
            System.out.println("Se necesita una ruta al fichero que contiene la categorizacion");
        } else {

            try {
                file = new FileInputStream(new File(args[0]));

                // Create Workbook instance holding reference to .xlsx file
                workbook = new XSSFWorkbook(file);

                // Get first/desired sheet from the workbook
                XSSFSheet sheet = workbook.getSheetAt(0);

                Iterator<Row> rowIterator = sheet.iterator();
                Map<Integer, String> map = new HashMap<>();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();
                        String cellData = cell.getStringCellValue();

                        if (cellData.toString().contains("-")) {

                            cellData = cellData.trim();
                            String idCategory = cellData.substring(0, cellData.indexOf(" - "));
                            String description = cellData.substring(cellData.indexOf(" - ") + 3);
                            int level = cell.getColumnIndex();

                            map.put(level, idCategory);
                            String parent = map.containsKey(level - 1) ? "'" + map.get(level - 1) + "'" : "null";

                            System.out.println(
                                    "INSERT INTO INNOAGR_TF_CATEGORY (CATEGORYID, CATEGORYID_PARENT, DESCRIPTION) VALUES ('"
                                            + idCategory + "', " + parent + ", '" + description + "');");
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (workbook != null && file != null)
                        workbook.close();
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
