package com.tabatskiy.web.web.controller;

import com.tabatskiy.web.service.CategoryService;
import com.tabatskiy.web.web.form.CategoryForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.tabatskiy.web.service.ClientService.currentClientId;

@Controller
@RequiredArgsConstructor
public class CategoryWebController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model) {
        Integer clientId = currentClientId();

        model.addAttribute("categories", categoryService.findAllByClientId(clientId));
        return "categoryList";
    }

    @GetMapping("/insert-category")
    public String insertCategoryGet() {
        return "insertCategory";
    }

    @PostMapping("/insert-category")
    public String insertCategoryPost(@ModelAttribute("form") @Valid CategoryForm form) {
        Integer clientId = currentClientId();

        categoryService.insert(form.getName(), clientId);
        return "redirect:/categories";
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        Integer clientId = currentClientId();

        categoryService.delete(id, clientId);
        return "redirect:/categories";
    }

    @GetMapping("/update-category")
    public String updateCategoryGet() {
        return "updateCategory";
    }

    @PostMapping("/update-category")
    public String updateCategoryPost(@ModelAttribute("form") CategoryForm form) {
        Integer clientId = currentClientId();

        categoryService.update(form.getName(), form.getId(), clientId);

        return "redirect:/categories";
    }
}