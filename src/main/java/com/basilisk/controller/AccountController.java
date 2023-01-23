package com.basilisk.controller;

import com.basilisk.service.AccountService;
import com.basilisk.dto.DropDownDTO;
import com.basilisk.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService service;

    @GetMapping("/loginForm")
    public String loginForm(Model model){
        return "account/login-form";
    }

//  post mapping untuk login akan lewat spring

    @GetMapping ("registerForm")
    public String registerForm (Model model){
        RegisterDTO dto = new RegisterDTO();
        List<DropDownDTO> roleDropdown = service.getRoleDropdown();
        model.addAttribute("dto",dto);
        model.addAttribute("roleDropdown",roleDropdown);
        return "account/register-form";
    }

    @PostMapping("register")
    public String register (@Valid @ModelAttribute("dto") RegisterDTO dto, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            List<DropDownDTO> roleDropdown = service.getRoleDropdown();
            model.addAttribute("roleDropdown", roleDropdown);
            return "account/register-form";
        }
        service.registerAccount(dto);
        return "redirect:/account/loginForm";
    }

    @RequestMapping(value = "/accessDenied", method = {RequestMethod.GET, RequestMethod.POST})
    public String accessDenied(Model model){
        return "account/access-denied";
    }

}
