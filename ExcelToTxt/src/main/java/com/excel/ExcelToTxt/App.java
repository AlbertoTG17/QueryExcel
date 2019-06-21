package com.excel.ExcelToTxt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class App {
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
                Category category = null;
                Category category1 = null;
                Category category2 = null;
                Category category3 = null;
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();
                        String cellData = cell.getStringCellValue();

                        if (cellData.toString().contains("-")) {

                            String[] data = cellData.trim().split(" - ");
                            String idCategory = data[0].trim();
                            String description = data[1].trim();

                            switch (cell.getColumnIndex()) {
                            case 0:
                                category = new Category(idCategory, null, description);
                                System.out.println(buildQuery(category));
                                break;
                            case 1:
                                category1 = new Category(idCategory, category.getIdCategory(), description);
                                System.out.println(buildQuery(category1));
                                break;

                            case 2:
                                category2 = new Category(idCategory, category1.getIdCategory(), description);
                                System.out.println(buildQuery(category2));
                                break;

                            case 3:
                                category3 = new Category(idCategory, category2.getIdCategory(), description);
                                System.out.println(buildQuery(category3));
                                break;

                            default:
                                break;
                            }
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

    public static String buildQuery(Category category) {
        String parent = null;
        if(category.getParent() != null) {
            parent = "'" + category.getParent() + "'";
        }
           
        return "INSERT INTO INNOAGR_TF_CATEGORY (CATEGORYID, CATEGORYID_PARENT, DESCRIPTION) VALUES ('" + category.getIdCategory() + "', "
                + parent + ", '" + category.getDescription() + "');";
    }
}
