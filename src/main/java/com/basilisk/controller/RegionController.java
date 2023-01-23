package com.basilisk.controller;

import com.basilisk.dto.*;
import com.basilisk.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        Model model){
        try {
            var grid = service.getRegionGrid(page, name);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPage", totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("searchName", name);
            return "region/region-index";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/upsert-form")
    public String upsertForm(@RequestParam(required = false) Long id,Model model){
        try{
            UpsertRegionDTO upsertRegionDTO = new UpsertRegionDTO();
            if (id != null){
                upsertRegionDTO = service.getUpdate(id);
            }

            model.addAttribute("dto",upsertRegionDTO);
            return "region/upsert-region";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertRegionDTO upsertRegionDTO, BindingResult bindingResult, Model model){
        try {
            if(bindingResult.hasErrors()){
                return "region/upsert-region";// akan mengembalikan upsert kembali jika salah(sesuai validasi)
            }
            service.saveRegion(upsertRegionDTO);
            return "redirect:/region/index";
// harus jalankan reques(get mapping) dulu sebelum ke index supaya data diolah dan baru diarahkan ke view agar data tidak kosng
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            service.delete(id);
            return "redirect:/region/index";

        }catch (Exception exception){
            String errorText = String.format("Jenis Exception: %s", exception.getCause().getCause());
            redirectAttributes.addAttribute("message" ,errorText);
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true) Long id,
                         @RequestParam(defaultValue="") String employeeNumber,
                         @RequestParam(defaultValue = "1") Integer page,
                         Model model){
        try {
            var header = service.getHeader(id);
            var grid = service.getDetailGrid(page, id);
            var totalPage = grid.getTotalPages();
            model.addAttribute("header", header);
            model.addAttribute("dataGrid", grid);
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("currentPage",page);
            model.addAttribute("employeeNumber", employeeNumber);


            return "region/region-detail";
        }catch (Exception exception) {
            String errorText = String.format("Jenis Exception: %s", exception.getCause().getCause());
            return "redirect:/error/serverError";
        }
    }
    @GetMapping("/delete-detail")
    public String deleteDetail(@RequestParam(required = true) Long regionId,
                               @RequestParam(required = true) String employeeNumber,
                               Model model, RedirectAttributes redirectAttributes) {
        service.deleteRegionSalesman(regionId, employeeNumber);
        redirectAttributes.addAttribute("id", regionId);
        return "redirect:/region/detail";
    }

    @GetMapping("/assignDetailForm")
    public String assignDetailForm(@RequestParam(required = true) Long id, Model model) {
        AssignSalesmanDTO dto = new AssignSalesmanDTO();
        dto.setRegionId(id);
        RegionHeaderDTO header = service.getHeader(id);
        String breadCrumbs = String.format("Region Index / Salesman of %s / Assign Salesman", header.getCity());
        List<DropDownDTO> salesmanDropdown = service.getSalesmanDropdown();
        model.addAttribute("regionSalesman", dto);
        model.addAttribute("salesmanDropdown", salesmanDropdown);
        model.addAttribute("breadCrumbs", breadCrumbs);
        return "region/region-detail-form";
    }
    @PostMapping("/assign-detail")
    public String assignDetail(@Valid @ModelAttribute("regionSalesman") AssignSalesmanDTO dto,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            RegionHeaderDTO header = service.getHeader(dto.getRegionId());
            List<DropDownDTO> salesmanDropdown = service.getSalesmanDropdown();
            String breadCrumbs = String.format("Region Index / Salesman of %s / Assign Salesman", header.getCity());
            model.addAttribute("salesmanDropdown", salesmanDropdown);
            model.addAttribute("breadCrumbs", breadCrumbs);
            return "region/region-detail-form";
        } else {
            service.assignSalesman(dto);
            redirectAttributes.addAttribute("id", dto.getRegionId());
            return "redirect:/region/detail";
        }
    }
}
