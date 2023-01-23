package com.basilisk.controller;

import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.dto.UpsertProductDTO;
import com.basilisk.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String productName,
                        Model model){
        try {

            var grid = service.getProductGrid(page, productName);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid",grid);
            model.addAttribute("totalPage", totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("productName", productName);
            return "product/product-index";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/upsert-form")
    public String upsertForm (@RequestParam(required = false) Long id, Model model){
        try {
            String actionType ="Insert";
            var dto = new UpsertProductDTO();
            dto.setPrice(0.0);
            dto.setStock(0);
            dto.setOnOrder(0);
            var categoryDropDown = service.getCategoryDropDown();
            var supplierDropDown = service.getSupplierDropDown();
            if (id != null) {
                actionType = "Update";
                dto = service.getUpdate(id);
            }
            model.addAttribute("actionType",actionType);
            model.addAttribute("dto",dto);
            model.addAttribute("categoryDropDown",categoryDropDown);
            model.addAttribute("supplierDropDown",supplierDropDown);
            return "product/upsert-product";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertProductDTO upsertProductDTO,
                       BindingResult bindingResult, Model model){
        try {
            if(bindingResult.hasErrors()){
                var categoryDropDown = service.getCategoryDropDown(); //Sebelum di kembalikan upsertDTo yg kosong
                var supplierDropDown = service.getSupplierDropDown();// Ambil dulu dropdown nya agar kembali ditampilkan
                model.addAttribute("categoryDropDown",categoryDropDown);
                model.addAttribute("supplierDropDown",supplierDropDown);
                model.addAttribute("actionType","Insert");
                if (upsertProductDTO.getId() != null)
                {
                    model.addAttribute("actionType","Update");
                }
                return "product/upsert-product";// akan mengembalikan upsert kembali jika salah(sesuai validasi)
            }
            service.save(upsertProductDTO);
            return "redirect:/product/index";
// harus jalankan reques(get mapping) dulu sebelum ke index supaya data diolah dan baru diarahkan ke view agar data tidak kosng
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }
}
