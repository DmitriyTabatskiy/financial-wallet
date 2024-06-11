package com.tabatskiy.web.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tabatskiy.web.MockSecurityConfiguration;

import com.tabatskiy.web.api.converter.CategoryReportModelToResponseConverter;

import com.tabatskiy.web.api.json.category_report.CategoryReportRequest;
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

import java.time.LocalDate;
import java.util.Arrays;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryReportController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class CategoryReportControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CategoryReportService reportService;

    @MockBean
    ClientService clientService;

    @SpyBean
    CategoryReportModelToResponseConverter converter;

    @Before
    public void setUp() {
        when(clientService.currentClient()).thenReturn(
                new ClientDTO(1,
                        "yana@gmail.com"))
        ;
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void categoryFindAllExpenses_whenGetExpensesCategoryReports_thenStatus200() throws Exception {
        CategoryReportDTO categoryReportDTO1 = new CategoryReportDTO("name1", 2000);
        CategoryReportDTO categoryReportDTO2 = new CategoryReportDTO("name2", 3000);

        when(reportService.findAllExpensesCategoryForReport(
                LocalDate.of(2023, 10, 10),
                LocalDate.of(2023, 10, 20),
                1))
                .thenReturn(Arrays.asList(categoryReportDTO1, categoryReportDTO2));

        ObjectWriter objectWriter = objectMapper.writer();

        mockMvc.perform(post("/api/category-find-all-expenses")
                        .contentType(APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(new CategoryReportRequest(
                                LocalDate.of(2023, 10, 10),
                                LocalDate.of(2023, 10, 20)))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(categoryReportDTO1, categoryReportDTO2))));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void categoryFindAllExpenses_whenGetEmptyExpensesCategoryReports_thenStatusIsNoContent() throws Exception {
        when(reportService.findAllExpensesCategoryForReport(
                LocalDate.of(2023, 10, 10),
                LocalDate.of(2023, 10, 20),
                1))
                .thenReturn(emptyList());

        ObjectWriter objectWriter = objectMapper.writer();

        mockMvc.perform(post("/api/category-find-all-expenses")
                        .contentType(APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(new CategoryReportRequest(
                                LocalDate.of(2023, 10, 10),
                                LocalDate.of(2023, 10, 20)))))
                .andExpect(status().isNoContent());
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void categoryFindAllIncome_whenGetIncomeCategoryReports_thenStatus200() throws Exception {
        CategoryReportDTO categoryReportDTO1 = new CategoryReportDTO("name1", 2000);
        CategoryReportDTO categoryReportDTO2 = new CategoryReportDTO("name2", 3000);

        when(reportService.findAllIncomeCategoryForReport(
                LocalDate.of(2023, 10, 10),
                LocalDate.of(2023, 10, 20),
                1))
                .thenReturn(Arrays.asList(categoryReportDTO1, categoryReportDTO2));

        ObjectWriter objectWriter = objectMapper.writer();

        mockMvc.perform(post("/api/category-find-all-income")
                        .contentType(APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(new CategoryReportRequest(
                                LocalDate.of(2023, 10, 10),
                                LocalDate.of(2023, 10, 20)))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(categoryReportDTO1, categoryReportDTO2))));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void categoryFindAllIncome_whenGetEmptyIncomeCategoryReports_thenStatusIsNoContent() throws Exception {
        when(reportService.findAllIncomeCategoryForReport(
                LocalDate.of(2023, 10, 10),
                LocalDate.of(2023, 10, 20),
                1))
                .thenReturn(emptyList());

        ObjectWriter objectWriter = objectMapper.writer();

        mockMvc.perform(post("/api/category-find-all-income")
                        .contentType(APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(new CategoryReportRequest(
                                LocalDate.of(2023, 10, 10),
                                LocalDate.of(2023, 10, 20)))))
                .andExpect(status().isNoContent());
    }
}