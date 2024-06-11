package com.tabatskiy.web.api.controller;

import com.tabatskiy.web.api.converter.CategoryToResponseConverter;
import com.tabatskiy.web.api.json.category.CategoryRequest;
import com.tabatskiy.web.api.json.category.CategoryRequestDelete;
import com.tabatskiy.web.api.json.category.CategoryResponse;
import com.tabatskiy.web.service.CategoryDTO;
import com.tabatskiy.web.service.CategoryService;
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
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryToResponseConverter converter;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> categories() {
        Integer clientId = currentClientId();

        List<CategoryDTO> categoryDTOS = categoryService.findAllByClientId(clientId);

        if (categoryDTOS.isEmpty()) {
            return status(HttpStatus.NO_CONTENT).build();
        }

        List<CategoryResponse> categories = categoryService.findAllByClientId(clientId).stream()
                .map(converter::convert)
                .collect(toList());
        return ok(categories);
    }

    @DeleteMapping("/delete-category")
    public ResponseEntity<CategoryResponse> deleteCategory(@RequestBody @Validated CategoryRequestDelete categoryRequest) {
        CategoryDTO categoryDTO = categoryService.delete(categoryRequest.getId(), categoryRequest.getClientId());

        if (categoryDTO == null) {
            return status(HttpStatus.NO_CONTENT).build();
        }
        return ok(converter.convert(categoryDTO));
    }

    @PostMapping("/insert-category")
    public ResponseEntity<CategoryResponse> insertCategory(@RequestBody @Validated CategoryRequest categoryRequest) {
        Integer clientId = currentClientId();

        CategoryDTO categoryDTO = categoryService.insert(
                categoryRequest.getName(), clientId);

        return ok(converter.convert(categoryDTO));
    }

    @PutMapping("/update-category")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody @Validated CategoryRequest categoryRequest) {
        Integer clientId = currentClientId();

        CategoryDTO categoryDTO = categoryService.update(
                categoryRequest.getName(), categoryRequest.getId(), clientId);

        return ok(converter.convert(categoryDTO));
    }
}