package com.tabatskiy.web.api.converter;

import com.tabatskiy.web.api.json.category_report.CategoryReportResponse;
import com.tabatskiy.web.service.CategoryReportDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryReportModelToResponseConverter implements Converter<CategoryReportDTO, CategoryReportResponse> {

    @Override
    public CategoryReportResponse convert(CategoryReportDTO categoryReportDTO) {
        return new CategoryReportResponse(categoryReportDTO.getName(), categoryReportDTO.getAmount());
    }
}