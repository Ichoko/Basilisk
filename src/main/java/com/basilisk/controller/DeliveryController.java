package com.basilisk.controller;

import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.dto.UpsertDeliveryDTO;
import com.basilisk.entity.Delivery;
import com.basilisk.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        Model model){
        try {
            var grid = deliveryService.getCategoryGrid(page, name);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPage", totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("searchName", name);
            return "delivery/delivery-index";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    //method untuk mengeluarkan form untuk nantinya diisi di VIEW(Get Request)
    @GetMapping("/upsert-form")
    public String upsertForm(@RequestParam(required = false) Long id,Model model){
        try{
            UpsertDeliveryDTO upsertDeliveryDTO = new UpsertDeliveryDTO();
            if (id != null){
                upsertDeliveryDTO= deliveryService.getUpdate(id);
            }

            model.addAttribute("dto",upsertDeliveryDTO);
            return "delivery/upsert-delivery";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    //method ini menjadi kebalikan dari Get. dimana data akan dikembalikan terbalik(client site - server site)
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertDeliveryDTO upsertDeliveryDTO, BindingResult bindingResult, Model model){
        try {
            if(bindingResult.hasErrors()){
                return "delivery/upsert-delivery";// akan mengembalikan upsert kembali jika salah(sesuai validasi)
            }
            deliveryService.saveDelivery(upsertDeliveryDTO);
            return "redirect:/delivery/index";
// harus jalankan reques(get mapping) dulu sebelum ke index supaya data diolah dan baru diarahkan ke view agar data tidak kosng
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            deliveryService.delete(id);
            return "redirect:/delivery/index";

        }catch (Exception exception){
            String errorText = String.format("Jenis Exception: %s", exception.getCause().getCause());
            redirectAttributes.addAttribute("message" ,errorText);
            return "redirect:/error/serverError";
        }
    }

}
