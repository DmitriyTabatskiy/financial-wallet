package com.tabatskiy.web.web.controller;

import com.tabatskiy.web.service.ClientDTO;
import com.tabatskiy.web.service.ClientService;

import com.tabatskiy.web.web.form.ClientForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ClientWebController {

    private final ClientService clientService;

    @GetMapping("/personal-area")
    public String index(Model model) {
        ClientDTO clientDTO = clientService.currentClient();

        model.addAttribute("id", clientDTO.getId());
        model.addAttribute("name", clientDTO.getEmail());
        return "personal-area";
    }

    @GetMapping("/login-form")
    public String getLogin() {
        return "login-form";
    }

    @GetMapping("/regist")
    public String getRegistration(Model model) {
        model.addAttribute("form", new ClientForm());
        return "registration";
    }

    @PostMapping("/regist")
    public String postRegistration(@ModelAttribute("form") @Valid ClientForm form,
                                   BindingResult result,
                                   Model model) {
        if (!result.hasErrors()) {
            if ((clientService.registration(
                    form.getEmail(),
                    form.getPassword()) != null)) {
                return "redirect:/login-form";
            }
            result.addError(new FieldError("form", "email", "Не верный логин или пароль"));
        }
        model.addAttribute("form", form);
        return "registration";
    }
}