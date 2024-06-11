package com.tabatskiy.web.api.converter;

import com.tabatskiy.web.api.json.category.CategoryResponse;
import com.tabatskiy.web.service.CategoryDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToResponseConverter implements Converter<CategoryDTO, CategoryResponse> {

    @Override
    public CategoryResponse convert(CategoryDTO category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}