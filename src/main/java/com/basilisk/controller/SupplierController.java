package com.basilisk.controller;

import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.dto.UpsertSupplierDTO;
import com.basilisk.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String company,
                        @RequestParam(defaultValue = "") String contact,
                        Model model){
        try {

            var grid = service.getSupplierGrid(page, company, contact);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid",grid);
            model.addAttribute("totalPage", totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("company", company);
            model.addAttribute("contact", contact);
            return "supplier/supplier-index";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }
    @GetMapping("/upsert-form")
        public String upsertForm(@RequestParam(required = false) Long id,Model model){
        try {
            UpsertSupplierDTO upsertSupplierDTO = new UpsertSupplierDTO();
            if (id != null){
                upsertSupplierDTO = service.getUpdate(id);
            }
            model.addAttribute("dto",upsertSupplierDTO);
            return "supplier/upsert-supplier";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertSupplierDTO upsertSupplierDTO, BindingResult bindingResult,
                       Model model) {
        try {

            if(bindingResult.hasErrors()){
                return "supplier/upsert-supplier";// akan mengembalikan upsert kembali jika salah(sesuai validasi)
            }
            service.saveSupplier(upsertSupplierDTO);
            return "redirect:/supplier/index";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes){
        try {
                service.delete(id);
                return "redirect:/supplier/index";

        }catch (Exception exception){
            String errorText = String.format("Jenis Exception: %s", exception.getCause().getCause());
            redirectAttributes.addAttribute("message" ,errorText);
            return "redirect:/error/serverError";
        }
    }
}
