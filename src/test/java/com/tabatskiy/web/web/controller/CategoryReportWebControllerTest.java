package com.tabatskiy.web.web.controller;

import com.tabatskiy.web.MockSecurityConfiguration;
import com.tabatskiy.web.config.SecurityConfiguration;
import com.tabatskiy.web.service.CategoryReportDTO;
import com.tabatskiy.web.service.CategoryReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(CategoryReportWebController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class CategoryReportWebControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryReportService reportService;

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getCategoryIncomeReport_whenReturnView_thenStatus200() throws Exception {
        mockMvc.perform(get("/find-all-income"))
                .andExpect(status().isOk())
                .andExpect(view().name("findAllCategoryIncomeReport"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postCategoryIncomeReport_whenGetIncomeCategoryReports_thenStatus200() throws Exception {
        CategoryReportDTO categoryReportDTO1 = new CategoryReportDTO("name1", 2000);
        CategoryReportDTO categoryReportDTO2 = new CategoryReportDTO("name2", 3000);

        when(reportService.findAllIncomeCategoryForReport(
                LocalDate.of(2023, 10, 10),
                LocalDate.of(2023, 10, 20),
                1))
                .thenReturn(Arrays.asList(categoryReportDTO1, categoryReportDTO2));

        mockMvc.perform(post("/find-all-income")
                        .param("start", "2023-10-10")
                        .param("end", "2023-10-20")
                        .param("clientId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("findAllIncomeCategoryList"))
                .andExpect(model().attributeExists("categoryIncome"))
                .andExpect(model().attribute("categoryIncome", hasSize(2)));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getCategoryExpensesReport_whenReturnView_thenStatus200() throws Exception {
        mockMvc.perform(get("/find-all-expenses"))
                .andExpect(status().isOk())
                .andExpect(view().name("findAllCategoryExpensesReport"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postCategoryExpensesReport_whenGetExpensesCategoryReports_thenStatus200() throws Exception {
        CategoryReportDTO categoryReportDTO1 = new CategoryReportDTO("name1", 2000);
        CategoryReportDTO categoryReportDTO2 = new CategoryReportDTO("name2", 3000);

        when(reportService.findAllExpensesCategoryForReport(
                LocalDate.of(2023, 10, 10),
                LocalDate.of(2023, 10, 20),
                1))
                .thenReturn(Arrays.asList(categoryReportDTO1, categoryReportDTO2));

        mockMvc.perform(post("/find-all-expenses")
                        .param("start", "2023-10-10")
                        .param("end", "2023-10-20")
                        .param("clientId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("findAllExpensesCategoryList"))
                .andExpect(model().attributeExists("categoryExpenses"))
                .andExpect(model().attribute("categoryExpenses", hasSize(2)));
    }
}