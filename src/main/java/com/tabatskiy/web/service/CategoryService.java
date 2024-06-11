package com.tabatskiy.web.service;

import com.tabatskiy.web.converter.CategoryModelToCategoryDtoConverter;
import com.tabatskiy.web.entity.Category;
import com.tabatskiy.web.entity.Client;
import com.tabatskiy.web.exception.CustomException;
import com.tabatskiy.web.repository.CategoryRepository;
import com.tabatskiy.web.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ClientRepository clientRepository;
    private final CategoryModelToCategoryDtoConverter categoryDtoConverter;

    public List<CategoryDTO> findAllByClientId(Integer clientId) {
        return categoryRepository.findAllByClientId(clientId).stream()
                .map(categoryDtoConverter::convert)
                .collect(toList());
    }

    @Transactional
    public CategoryDTO delete(Integer categoryId, Integer clientId) {
        Category category = categoryRepository.findByIdAndClientId(categoryId, clientId)
                .orElseThrow(() -> new CustomException("Account by id " + categoryId + " was not found"));

        categoryRepository.deleteByIdAndClientId(categoryId, clientId);
        return categoryDtoConverter.convert(category);
    }

    @Transactional
    public CategoryDTO insert(String nameCategory, Integer clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new CustomException("Client by id " + clientId + " was not found"));

        Category category = new Category();
        category.setName(nameCategory);
        category.setClient(client);
        categoryRepository.save(category);
        return categoryDtoConverter.convert(category);
    }

    @Transactional
    public CategoryDTO update(String name, Integer categoryId, Integer clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new CustomException("Client by id " + clientId + " was not found"));

        categoryRepository.findByIdAndClientId(categoryId, clientId)
                .orElseThrow(() -> new CustomException("Category was not found"));

        Category category = new Category();
        category.setId(categoryId);
        category.setName(name);
        category.setClient(client);
        categoryRepository.saveAndFlush(category);
        return categoryDtoConverter.convert(category);
    }
}