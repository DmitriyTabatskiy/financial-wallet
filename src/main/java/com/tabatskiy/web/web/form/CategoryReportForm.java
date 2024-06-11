package com.tabatskiy.web.web.form;

import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CategoryReportForm {

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
}