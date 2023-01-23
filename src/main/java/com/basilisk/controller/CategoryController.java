package com.basilisk.controller;

import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.Null;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        Model model){
        try {
            var grid = service.getCategoryGrid(page, name);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPage", totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("searchName", name);
            return "category/category-index";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }
//method untuk mengeluarkan form untuk nantinya diisi di VIEW(Get Request)
    @GetMapping("/upsert-form")
    public String upsertForm(@RequestParam(required = false) Long id,Model model){
        try{
            UpsertCategoryDTO upsertCategoryDTO = new UpsertCategoryDTO();
            if (id != null){
                upsertCategoryDTO = service.getUpdate(id);
            }

            model.addAttribute("dto",upsertCategoryDTO);
            return "category/upsert-category";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

//method ini menjadi kebalikan dari Get. dimana data akan dikembalikan terbalik(client site - server site)
    @PostMapping("/save")
    public String save( @Valid @ModelAttribute("dto") UpsertCategoryDTO upsertCategoryDTO,BindingResult bindingResult,Model model){
        try {
            if(bindingResult.hasErrors()){
                return "category/upsert-category";// akan mengembalikan upsert kembali jika salah(sesuai validasi)
            }
            service.saveCategory(upsertCategoryDTO);
            return "redirect:/category/index";
// harus jalankan reques(get mapping) dulu sebelum ke index supaya data diolah dan baru diarahkan ke view agar data tidak kosng
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            Long totalDependecies = service.delete(id);
            if (totalDependecies == 0) {
                return "redirect:/category/index";
            }
            model.addAttribute("dependecies", totalDependecies);
            return "category/delete-category";
        }catch (Exception exception){
            String errorText = String.format("Jenis Exception: %s", exception.getCause().getCause());
            redirectAttributes.addAttribute("message" ,errorText);
            return "redirect:/error/serverError";
        }
    }
}
