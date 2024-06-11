package com.tabatskiy.web.api.controller;

import com.tabatskiy.web.api.converter.CategoryReportModelToResponseConverter;
import com.tabatskiy.web.api.json.category_report.CategoryReportRequest;
import com.tabatskiy.web.api.json.category_report.CategoryReportResponse;
import com.tabatskiy.web.service.CategoryReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tabatskiy.web.service.ClientService.currentClientId;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryReportController {

    private final CategoryReportService reportService;
    private final CategoryReportModelToResponseConverter converter;

    @PostMapping("/category-find-all-expenses")
    public ResponseEntity<List<CategoryReportResponse>> categoryFindAllExpenses(
            @RequestBody @Validated CategoryReportRequest categoryReportRequest) {
        Integer clientId = currentClientId();

        List<CategoryReportResponse> categoryReportDTOS = reportService.findAllExpensesCategoryForReport(
                        categoryReportRequest.getStart(),
                        categoryReportRequest.getEnd(),
                        clientId
                ).stream()
                .map(converter::convert)
                .collect(toList());

        if (categoryReportDTOS.isEmpty()) {
            return status(HttpStatus.NO_CONTENT).build();
        }

        return ok(categoryReportDTOS);
    }

    @PostMapping("/category-find-all-income")
    public ResponseEntity<List<CategoryReportResponse>> categoryFindAllIncome(
            @RequestBody @Validated CategoryReportRequest categoryReportRequest) {
        Integer clientId = currentClientId();

        List<CategoryReportResponse> categoryReportDTOS = reportService.findAllIncomeCategoryForReport(
                        categoryReportRequest.getStart(),
                        categoryReportRequest.getEnd(),
                        clientId
                ).stream()
                .map(converter::convert)
                .collect(toList());

        if (categoryReportDTOS.isEmpty()) {
            return status(HttpStatus.NO_CONTENT).build();
        }

        return ok(categoryReportDTOS);
    }
}