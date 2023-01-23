package com.basilisk.controller;

import com.basilisk.dto.CustomerGridDTO;
import com.basilisk.dto.UpsertCustomerDTO;
import com.basilisk.dto.UpsertDeliveryDTO;
import com.basilisk.service.CustomerService;
import com.basilisk.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String companyName,
                        @RequestParam(defaultValue = "") String contactPerson,
                        @RequestParam(defaultValue = "") String city,
                        Model model){
        var grid = service.getCustomerGrid(page,companyName,contactPerson,city);
        var totalHalaman = grid.getTotalPages();
        model.addAttribute("grid", grid);
        model.addAttribute("currentPage", page);
        model.addAttribute("companyName", companyName);
        model.addAttribute("contactPerson", contactPerson);
        model.addAttribute("city", city);
        model.addAttribute("totalHalaman", totalHalaman);
        model.addAttribute("breadCrumbs", "Customer Index");
        return "customer/customer-index";
    }
    @GetMapping("/upsert-form")
    public String upsertForm(@RequestParam(required = false) Long id,Model model){
        try{
            UpsertCustomerDTO upsertCustomerDTO = new UpsertCustomerDTO();
            if (id != null){
                upsertCustomerDTO= service.getUpdate(id);
            }

            model.addAttribute("dto",upsertCustomerDTO);
            return "customer/upsert-customer";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertCustomerDTO upsertCustomerDTO, BindingResult bindingResult, Model model){
        try {
            if(bindingResult.hasErrors()){
                return "customer/upsert-customer";// akan mengembalikan upsert kembali jika salah(sesuai validasi)
            }
            service.saveCustomer(upsertCustomerDTO);
            return "redirect:/customer/index";
// harus jalankan reques(get mapping) dulu sebelum ke index supaya data diolah dan baru diarahkan ke view agar data tidak kosng
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            service.delete(id);
            return "redirect:/customer/index";

        }catch (Exception exception){
            String errorText = String.format("Jenis Exception: %s", exception.getCause().getCause());
            redirectAttributes.addAttribute("message" ,errorText);
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/info-customer")
    public String upsertForm(@RequestParam(required = true) Long id,Model model,RedirectAttributes redirectAttributes) {
    try {
        UpsertCustomerDTO upsertCustomerDTO = new UpsertCustomerDTO();
        upsertCustomerDTO = service.getInfo(id);
        model.addAttribute("dto",upsertCustomerDTO);
        return "customer/info-customer";
    }catch (Exception exception) {
        String errorText = String.format("Jenis Exception: %s", exception.getCause().getCause());
        redirectAttributes.addAttribute("message", errorText);
        return "redirect:/error/serverError";
     }
    }
}
