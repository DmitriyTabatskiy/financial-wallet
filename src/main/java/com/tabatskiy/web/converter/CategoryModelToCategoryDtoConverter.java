package com.tabatskiy.web.converter;

import com.tabatskiy.web.entity.Category;
import com.tabatskiy.web.service.CategoryDTO;
import org.springframework.stereotype.Service;

@Service
public class CategoryModelToCategoryDtoConverter implements Converter<Category, CategoryDTO> {

    @Override
    public CategoryDTO convert(Category source) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(source.getId());
        categoryDTO.setName(source.getName());
        return categoryDTO;
    }
}