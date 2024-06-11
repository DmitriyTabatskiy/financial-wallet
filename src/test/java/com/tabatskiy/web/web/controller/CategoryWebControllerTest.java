package com.tabatskiy.web.web.controller;

import com.tabatskiy.web.MockSecurityConfiguration;
import com.tabatskiy.web.config.SecurityConfiguration;
import com.tabatskiy.web.service.CategoryDTO;
import com.tabatskiy.web.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(CategoryWebController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class CategoryWebControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void categories_whenGetCategories_thenStatus200() throws Exception {
        CategoryDTO categoryDTO1 = new CategoryDTO(1, "category1");
        CategoryDTO categoryDTO2 = new CategoryDTO(2, "category2");
        CategoryDTO categoryDTO3 = new CategoryDTO(2, "category3");

        when(categoryService.findAllByClientId(1)).thenReturn(Arrays.asList(categoryDTO1, categoryDTO2, categoryDTO3));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("categoryList"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", hasSize(3)));

    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void insertCategoryGet_whenReturnView_thenStatus200() throws Exception {
        mockMvc.perform(get("/insert-category"))
                .andExpect(status().isOk())
                .andExpect(view().name("insertCategory"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void insertCategoryPost_whenInsert_thenStatus302() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(1, "category");

        when(categoryService.insert("category", 1)).thenReturn(categoryDTO);

        mockMvc.perform(post("/insert-category")
                        .param("name", "category")
                )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/categories"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void deleteCategory_whenDeleteCategory_thenStatus302() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(1, "category");

        when(categoryService.delete(1, 1)).thenReturn(categoryDTO);

        mockMvc.perform(get("/delete-category/{id}", categoryDTO.getId())
                )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/categories"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void updateCategoryGet_whenReturnView_thenStatus200() throws Exception {
        mockMvc.perform(get("/update-category"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateCategory"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void updateCategoryPost_whenInsert_thenStatus302() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(1, "category");

        when(categoryService.update("name", 1, 1)).thenReturn(categoryDTO);

        mockMvc.perform(post("/insert-category")
                        .param("name", "name")
                )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/categories"));
    }
}