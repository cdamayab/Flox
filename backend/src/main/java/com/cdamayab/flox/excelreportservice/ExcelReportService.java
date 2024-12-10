package com.cdamayab.flox.excelreportservice;

import com.cdamayab.flox.productcatalogservice.Product;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelReportService {

    /**
     * Generates an Excel report for a list of products.
     *
     * @param products the list of products to include in the report
     * @return a {@link ByteArrayInputStream} with the Excel file
     * @throws IllegalArgumentException if the product list is null or empty
     */
    public ByteArrayInputStream generateProductReport(List<Product> products) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Productos");

            // Create Header Row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Nombre", "Descripción", "Stock", "Precio", "Proveedor", "Categoría"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Populate Data Rows
            int rowNum = 1;
            for (Product product : products) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getDescription());
                row.createCell(3).setCellValue(product.getStock());
                row.createCell(4).setCellValue(product.getPrice());
                row.createCell(5).setCellValue(product.getSupplier());
                row.createCell(6).setCellValue(product.getCategory());
            }

            // Autosize Columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Error while generating Excel file", e);
        }
    }
}
