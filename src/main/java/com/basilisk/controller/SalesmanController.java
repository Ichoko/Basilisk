package com.basilisk.controller;

import com.basilisk.dto.*;
import com.basilisk.service.SalesmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/salesman")
public class SalesmanController {
    @Autowired
    SalesmanService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue="") String employeeNumber,
                        @RequestParam(defaultValue="") String name,
                        @RequestParam(defaultValue="") String employeeLevel,
                        @RequestParam(defaultValue="") String superiorName,
                        Model model){
        var grid = service.getSalesmanGrid(page, employeeNumber, name, employeeLevel, superiorName);
        var totalHalaman =grid.getTotalPages();
//        long totalPages = service.getTotalPages(employeeNumber, name, employeeLevel, superiorName);
        List<DropDownDTO> employeeLevelDropdown = service.getLevelSalesman();
        model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
        model.addAttribute("grid", grid);
        model.addAttribute("currentPage", page);
        model.addAttribute("employeeNumber", employeeNumber);
        model.addAttribute("name", name);
        model.addAttribute("employeeLevel", employeeLevel);
//        model.addAttribute("superiorName", superiorName);
        List<DropDownDTO> salesmanDropdown = service.getSalesmanDropdown();
        model.addAttribute("salesmanDropdown", salesmanDropdown);
        model.addAttribute("totalPage", totalHalaman);
        model.addAttribute("breadCrumbs", "Salesman Index");
        return "salesman/salesman-index";
    }

    @GetMapping("/upsert-form")
    public String upsertForm(@RequestParam(required = false) String employeeNumber,Model model){
        try {
            UpsertSalesmanDTO upsertSalesmanDTO = new UpsertSalesmanDTO();
            List<DropDownDTO> employeeLevelDropdown = service.getLevelSalesman();
            List<DropDownDTO> salesmanDropdown = service.getSalesmanDropdown();
            model.addAttribute("salesmanDropdown", salesmanDropdown);
            model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
            if (employeeNumber != null){
                upsertSalesmanDTO = service.getUpdate(employeeNumber);
                model.addAttribute("type", "update");

            }
            model.addAttribute("dto",upsertSalesmanDTO);
            model.addAttribute("type", "insert");
            return "salesman/upsert-salesman";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertSalesmanDTO dto, BindingResult bindingResult, Model model){
        try {
            if(bindingResult.hasErrors()){
                List<DropDownDTO> employeeLevelDropdown = service.getLevelSalesman();
                model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
                List<DropDownDTO> salesmanDropdown = service.getSalesmanDropdown();
                model.addAttribute("salesmanDropdown", salesmanDropdown);
                model.addAttribute("type", "insert");
                return "salesman/upsert-salesman";// akan mengembalikan upsert kembali jika salah(sesuai validasi)
            }
            service.saveSalesman(dto);
            return "redirect:/salesman/index";
// harus jalankan reques(get mapping) dulu sebelum ke index supaya data diolah dan baru diarahkan ke view agar data tidak kosng
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true) String employeeNumber ,
                         @RequestParam(defaultValue = "1") Integer pageNumber,
                         Model model){
        try {
            var header = service.getHeaderSalesman(employeeNumber);
            var grid = service.getDetailGrid(pageNumber, employeeNumber);
            var totalPage = grid.getTotalPages();
            model.addAttribute("header", header);
            model.addAttribute("grid", grid);
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("currentPage", pageNumber);
            return "salesman/salesman-detail";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/delete-detail")
    public String deleteDetail(@RequestParam(required = true) Long regionId,
                               @RequestParam(required = true) String employeeNumber,
                               Model model, RedirectAttributes redirectAttributes) {
        service.deleteSalesmanRegion(regionId, employeeNumber);
        redirectAttributes.addAttribute("employeeNumber", employeeNumber);
        return "redirect:/salesman/detail";
    }

    @GetMapping("/assignDetailForm")
    public String assignDetailForm(@RequestParam(required = true) String employeeNumber, Model model) {
        AssignRegionDTO dto = new AssignRegionDTO();
        dto.setSalesmanEmployeeNumber(employeeNumber);
        SalesmanHeaderDTO header = service.getHeaderSalesman(employeeNumber);
        String breadCrumbs = String.format("Salesman Index / Region of %s / Assign Region", header.getFullName());
        List<DropDownDTO> regionDropdown = service.getRegionDropdown();
        model.addAttribute("regionSalesman", dto);
        model.addAttribute("regionDropdown", regionDropdown);
        model.addAttribute("breadCrumbs", breadCrumbs);
        return "salesman/salesman-detail-form";
    }

    @PostMapping("/assign-detail")
    public String assignDetail(@Valid @ModelAttribute("regionSalesman") AssignRegionDTO dto,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            SalesmanHeaderDTO header = service.getHeaderSalesman(dto.getSalesmanEmployeeNumber());
            List<DropDownDTO> regionDropdown = service.getRegionDropdown();
            String breadCrumbs = String.format("Salesman Index / Region of %s / Assign Region", header.getFullName());
            model.addAttribute("regionSalesman", dto);
            model.addAttribute("regionDropdown", regionDropdown);
            model.addAttribute("breadCrumbs", breadCrumbs);
            return "salesman/salesman-detail-form";
        } else {
            service.assignRegion(dto);
            redirectAttributes.addAttribute("employeeNumber", dto.getSalesmanEmployeeNumber());
            return "redirect:/salesman/detail";
        }
    }
}
