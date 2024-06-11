package com.tabatskiy.web.web.controller;

import com.tabatskiy.web.service.AccountService;
import com.tabatskiy.web.web.form.AccountForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static com.tabatskiy.web.service.ClientService.currentClientId;

@Controller
@RequiredArgsConstructor
public class AccountWebController {

    private final AccountService accountService;

    @GetMapping("/accounts")
    public String accounts(Model model) {
        Integer clientId = currentClientId();

        model.addAttribute("accounts", accountService.findAllByClientId(clientId));
        return "accountList";
    }

    @GetMapping("/insert-account")
    public String insertAccountGet() {
        return "insertAccount";
    }

    @PostMapping("/insert-account")
    public String insertAccountPost(@ModelAttribute("form") AccountForm form) {
        Integer clientId = currentClientId();

        accountService.insert(form.getName(), form.getBalance(), clientId);

        return "redirect:/accounts";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Integer clientId = currentClientId();

        accountService.delete(id, clientId);
        return "redirect:/accounts";
    }
}