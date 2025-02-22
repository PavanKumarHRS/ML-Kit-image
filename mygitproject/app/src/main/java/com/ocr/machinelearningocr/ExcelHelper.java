package com.ocr.machinelearningocr;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelHelper {
    private final String FILE_NAME = "DetectedText.xlsx";

    private File getExcelFile(Context context) {
        return new File(context.getExternalFilesDir(null), FILE_NAME);
    }

    public void saveTextToExcel(Context context, String text) {
        try {
            File file = getExcelFile(context);
            Workbook workbook;
            Sheet sheet;

            if (file.exists()) {
                FileInputStream fileIn = new FileInputStream(file);
                workbook = new XSSFWorkbook(fileIn);
                sheet = workbook.getSheetAt(0);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("TextData");
            }

            int lastRowNum = sheet.getLastRowNum();
            Row row = sheet.createRow(lastRowNum + 1);
            row.createCell(0).setCellValue(text);

            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();

            Log.d("ExcelHelper", "Text saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e("ExcelHelper", "Error saving file: " + e.getMessage());
        }
    }

    public String readFromExcel(Context context) {
        try {
            File file = getExcelFile(context);
            if (!file.exists()) {
                return "No Data Found";
            }

            FileInputStream fileIn = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fileIn);
            Sheet sheet = workbook.getSheetAt(0);
            StringBuilder data = new StringBuilder();

            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                data.append(sheet.getRow(i).getCell(0).getStringCellValue()).append("\n");
            }

            return data.toString();
        } catch (IOException e) {
            Log.e("ExcelHelper", "Error reading file: " + e.getMessage());
            return "Error reading file";
        }
    }
}
