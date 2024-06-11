package com.tabatskiy.web.converter;

import com.tabatskiy.web.entity.CategoryReportModel;
import com.tabatskiy.web.service.CategoryReportDTO;
import org.springframework.stereotype.Service;

@Service
public class CategoryReportModelToReportDtoConverter implements Converter<CategoryReportModel, CategoryReportDTO> {

    @Override
    public CategoryReportDTO convert(CategoryReportModel source) {
        CategoryReportDTO categoryReportDTO = new CategoryReportDTO();
        categoryReportDTO.setName(source.getName());
        categoryReportDTO.setAmount(source.getAmount());
        return categoryReportDTO;
    }
}