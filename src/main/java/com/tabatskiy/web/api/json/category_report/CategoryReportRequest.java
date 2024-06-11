package com.tabatskiy.web.api.json.category_report;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class CategoryReportRequest {

    @NonNull
    private LocalDate start;

    @NonNull
    private LocalDate end;
}