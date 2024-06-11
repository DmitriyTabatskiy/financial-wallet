package com.tabatskiy.web.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tabatskiy.web.MockSecurityConfiguration;
import com.tabatskiy.web.api.converter.CategoryToResponseConverter;
import com.tabatskiy.web.api.json.category.CategoryRequest;
import com.tabatskiy.web.api.json.category.CategoryRequestDelete;

import com.tabatskiy.web.config.SecurityConfiguration;
import com.tabatskiy.web.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CategoryService categoryService;

    @MockBean
    ClientService clientService;

    @SpyBean
    CategoryToResponseConverter converter;

    @Before
    public void setUp() {
        when(clientService.currentClient()).thenReturn(
                new ClientDTO(1,
                        "yana@gmail.com"))
        ;
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void categories_whenGetCategories_thenStatus200() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(1, "category");

        when(categoryService.findAllByClientId(1)).thenReturn(singletonList(categoryDTO));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(objectMapper.writeValueAsString(singletonList(categoryDTO))));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void deleteCategory_whenDeleteCategory_thenStatus200() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(1, "category");

        when(categoryService.delete(1, 1)).thenReturn(categoryDTO);

        ObjectWriter objectWriter = objectMapper.writer();

        mockMvc.perform(delete("/api/delete-category")
                        .contentType(APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(new CategoryRequestDelete(1, 1))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryDTO)));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void insertCategory_whenInsert_thenStatus200() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(1, "category");

        when(categoryService.insert("category", 1)).thenReturn(categoryDTO);

        ObjectWriter objectWriter = objectMapper.writer();

        mockMvc.perform(post("/api/insert-category")
                        .contentType(APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(new CategoryRequest(1, "category", 1))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryDTO)));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void updateCategory_whenUpdate_thenStatus200() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(1, "newCategory");

        when(categoryService.update("newCategory", 1, 1)).thenReturn(categoryDTO);

        ObjectWriter objectWriter = objectMapper.writer();

        mockMvc.perform(put("/api/update-category")
                        .contentType(APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(new CategoryRequest(1, "newCategory", 1))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryDTO)));
    }
}