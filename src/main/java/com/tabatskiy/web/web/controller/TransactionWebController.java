package com.tabatskiy.web.web.controller;

import com.tabatskiy.web.service.TransactionService;
import com.tabatskiy.web.web.form.TransactionForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.tabatskiy.web.service.ClientService.currentClientId;

@Controller
@RequiredArgsConstructor
public class TransactionWebController {

    private final TransactionService transactionService;

    @GetMapping("/from-transaction")
    public String getFromAccount(Model model) {
        model.addAttribute("form", new TransactionForm());
        return "fromTransaction";
    }

    @PostMapping("/from-transaction")
    public String postFromAccount(@ModelAttribute("form") @Valid TransactionForm form,
                                  BindingResult result,
                                  Model model) {
        Integer clientId = currentClientId();

        if (!result.hasErrors()) {
            if (transactionService.insertFromAccount(
                    form.getFromAccountId(),
                    form.getAmount(),
                    clientId,
                    form.getIntegerList()) != null) {

                return "redirect:/accounts";
            }
            result.addError(new FieldError("form", "fromAccountId", "Не правельные параметры"));
        }
        model.addAttribute("form", form);
        return "fromTransaction";
    }

    @GetMapping("/to-transaction")
    public String getToAccount(Model model) {
        model.addAttribute("form", new TransactionForm());
        return "toTransaction";
    }

    @PostMapping("/to-transaction")
    public String postToAccount(@ModelAttribute("form") @Valid TransactionForm form,
                                BindingResult result,
                                Model model) {
        Integer clientId = currentClientId();

        if (!result.hasErrors()) {
            if (transactionService.insertToAccount(
                    form.getToAccountId(),
                    form.getAmount(),
                    clientId,
                    form.getIntegerList()) != null) {

                return "redirect:/accounts";
            }
            result.addError(new FieldError("form", "tooAccountId", "Не правельные параметры"));
        }
        model.addAttribute("form", form);
        return "toTransaction";
    }

    @GetMapping("/from-account-to-account-transaction")
    public String getFromAccountToAccount(Model model) {
        model.addAttribute("form", new TransactionForm());
        return "fromAccountToAccountTransaction";
    }

    @PostMapping("/from-account-to-account-transaction")
    public String postFromAccountToAccount(@ModelAttribute("form") @Valid TransactionForm form,
                                           BindingResult result,
                                           Model model) {
        Integer clientId = currentClientId();

        if (!result.hasErrors()) {
            if (transactionService.insertAccountToAccount(
                    form.getFromAccountId(),
                    form.getToAccountId(),
                    form.getAmount(),
                    clientId,
                    form.getIntegerList()) != null) {

                return "redirect:/accounts";
            }
            result.addError(new FieldError("form", "fromAccountId", "Не правельные параметры"));
        }
        model.addAttribute("form", form);

        return "fromAccountToAccountTransaction";
    }
}