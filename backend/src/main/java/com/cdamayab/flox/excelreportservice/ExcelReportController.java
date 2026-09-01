package com.cdamayab.flox.excelreportservice;

import com.cdamayab.flox.productcatalogservice.Product;
import com.cdamayab.flox.productcatalogservice.ProductRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ExcelReportController {

    @Autowired
    private ExcelReportService excelReportService;

    @Autowired
    private ProductRepository productRepository;

    @Operation(
        summary = "Download Product Report",
        description = "Generates and downloads an Excel report containing the list of all products in the catalog."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Excel file generated and downloaded successfully",
            content = @Content(mediaType = "application/octet-stream")
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error while generating the report",
            content = @Content
        )
    })
    @GetMapping("/products")
    public ResponseEntity<byte[]> downloadProductReport() {
        List<Product> products = productRepository.findAll();
        ByteArrayInputStream in = excelReportService.generateProductReport(products);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=products_report.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(in.readAllBytes());
    }
}
