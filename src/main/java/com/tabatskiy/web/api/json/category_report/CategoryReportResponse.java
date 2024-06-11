package com.tabatskiy.web.api.json.category_report;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryReportResponse {
    private String name;
    private Integer amount;
}