package com.tabatskiy.web.web.controller;

import com.tabatskiy.web.service.CategoryReportService;
import com.tabatskiy.web.web.form.CategoryReportForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.tabatskiy.web.service.ClientService.currentClientId;

@Controller
@RequiredArgsConstructor
public class CategoryReportWebController {

    private final CategoryReportService categoryReportService;

    @GetMapping("/find-all-income")
    public String getCategoryIncomeReport() {
        return "findAllCategoryIncomeReport";
    }

    @PostMapping("/find-all-income")
    public String postCategoryIncomeReport(@ModelAttribute("form") CategoryReportForm form, Model model) {
        Integer clientId = currentClientId();

        model.addAttribute("categoryIncome", categoryReportService.findAllIncomeCategoryForReport((form.getStart()), (form.getEnd()), clientId));

        return "findAllIncomeCategoryList";
    }

    @GetMapping("/find-all-expenses")
    public String getCategoryExpensesReport() {
        return "findAllCategoryExpensesReport";
    }

    @PostMapping("/find-all-expenses")
    public String postCategoryExpensesReport(@ModelAttribute("form") CategoryReportForm form, Model model) {
        Integer clientId = currentClientId();

        model.addAttribute("categoryExpenses", categoryReportService.findAllExpensesCategoryForReport((form.getStart()), (form.getEnd()), clientId));

        return "findAllExpensesCategoryList";
    }
}