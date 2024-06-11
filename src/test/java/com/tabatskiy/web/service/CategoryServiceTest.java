package com.tabatskiy.web.service;

import com.tabatskiy.web.converter.CategoryModelToCategoryDtoConverter;
import com.tabatskiy.web.entity.Category;
import com.tabatskiy.web.entity.Client;
import com.tabatskiy.web.exception.CustomException;
import com.tabatskiy.web.repository.CategoryRepository;
import com.tabatskiy.web.repository.ClientRepository;
import com.tabatskiy.web.service.CategoryDTO;
import com.tabatskiy.web.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    CategoryModelToCategoryDtoConverter categoryDtoConverter;

    @InjectMocks
    CategoryService subj;

    @Test
    public void findAllByClientId_returnListCategoryNotEmpty() {
        Category category = new Category();
        category.setId(1);
        category.setName("goods");
        category.setClient(category.getClient());
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        when(categoryRepository.findAllByClientId(category.getId())).thenReturn(categories);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1);
        categoryDTO.setName("goods");
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        categoryDTOS.add(categoryDTO);
        when(categoryDtoConverter.convert(category)).thenReturn(categoryDTO);

        List<CategoryDTO> categoryDTOList = subj.findAllByClientId(category.getId());
        assertEquals(categoryDTOS, categoryDTOList);

        verify(categoryRepository, times(1)).findAllByClientId(category.getId());
        verify(categoryDtoConverter, times(1)).convert(category);
    }

    @Test
    public void findAllByClientId_returnListCategoryIsEmpty() {
        List<Category> categories = new ArrayList<>();
        when(categoryRepository.findAllByClientId(1)).thenReturn(categories);

        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        List<CategoryDTO> categoryDTOList = subj.findAllByClientId(1);

        assertEquals(categoryDTOS, categoryDTOList);

        verify(categoryRepository, times(1)).findAllByClientId(1);
        verifyZeroInteractions(categoryDtoConverter);
    }

    @Test
    public void delete_categoryDelete() {
        Client client = new Client();
        client.setId(1);
        client.setEmail("email");
        client.setPassword("password");

        Category category = new Category();
        category.setId(1);
        category.setName("goods");
        category.setClient(client);
        when(categoryRepository.findByIdAndClientId(1, client.getId())).thenReturn(Optional.of(category));

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1);
        categoryDTO.setName("goods");
        when(categoryDtoConverter.convert(category)).thenReturn(categoryDTO);

        subj.delete(1, client.getId());

        verify(categoryRepository, times(1)).findByIdAndClientId(1, client.getId());
        verify(categoryRepository, times(1)).deleteByIdAndClientId(1, client.getId());
        verify(categoryDtoConverter, times(1)).convert(category);
    }

    @Test
    public void delete_categoryNotDelete() {
        Client client = new Client();
        client.setId(1);
        client.setEmail("email");
        client.setPassword("password");

        Category category = new Category();
        category.setId(1);
        category.setName("goods");
        category.setClient(category.getClient());
        categoryRepository.delete(category);

        when(categoryRepository.findById(1)).thenReturn(null);

        verify(categoryRepository, times(1)).delete(category);
        verifyZeroInteractions(categoryDtoConverter);
    }

    @Test
    public void insert_categoryInsert() {
        Client client = new Client();
        client.setId(1);
        client.setEmail("email");
        client.setPassword("password");
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Category category = new Category();
        category.setName("goods");
        category.setClient(client);
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1);
        categoryDTO.setName("goods");
        when(categoryDtoConverter.convert(category)).thenReturn(categoryDTO);

        CategoryDTO categoryDTO1 = subj.insert("goods", 1);
        assertNotNull(categoryDTO1);
        assertEquals(categoryDTO, categoryDTO1);

        verify(clientRepository, times(1)).findById(1);
        verify(categoryRepository, times(1)).save(category);
        verify(categoryDtoConverter, times(1)).convert(category);
    }

    @Test(expected = CustomException.class)
    public void insert_categoryNotInsert() {
        Category category = new Category();
        category.setName("goods");
        when(categoryRepository.save(category)).thenReturn(null);

        assertNull(subj.insert("goods", 2));

        verifyZeroInteractions(categoryRepository);
        verifyZeroInteractions(categoryDtoConverter);
    }

    @Test
    public void update_categoryUpdateIsTrue() {
        Client client = new Client();
        client.setId(1);
        client.setEmail("email");
        client.setPassword("password");
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Category findCategory = new Category();
        findCategory.setId(1);
        findCategory.setName("goods");
        findCategory.setClient(client);
        when(categoryRepository.findByIdAndClientId(1, client.getId())).thenReturn(Optional.of(findCategory));

        Category category = new Category();
        category.setId(findCategory.getId());
        category.setName("goods1");
        category.setClient(client);
        when(categoryRepository.saveAndFlush(category)).thenReturn(category);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1);
        categoryDTO.setName("goods1");
        when(categoryDtoConverter.convert(category)).thenReturn(categoryDTO);

        CategoryDTO categoryDTO1 = subj.update("goods1", 1, 1);
        assertNotNull(categoryDTO1);
        assertEquals(categoryDTO, categoryDTO1);

        verify(clientRepository, times(1)).findById(1);
        verify(categoryRepository, times(1)).findByIdAndClientId(1, client.getId());
        verify(categoryRepository, times(1)).saveAndFlush(category);
        verify(categoryDtoConverter, times(1)).convert(category);
    }

    @Test(expected = CustomException.class)
    public void update_categoryUpdateIsFalse() {
        Category category = new Category();
        category.setId(1);
        category.setName("name");
        when(categoryRepository.saveAndFlush(category)).thenReturn(null);

        assertNull(subj.insert("goods", 2));

        verifyZeroInteractions(categoryRepository);
        verifyZeroInteractions(categoryDtoConverter);
    }
}