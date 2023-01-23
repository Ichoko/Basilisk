package com.basilisk.rest;

import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.dto.UpsertSupplierDTO;
import com.basilisk.service.CategoryService;
import com.basilisk.service.SupplierService;
import com.basilisk.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/supplier")
public class SupplierRestController {
    @Autowired
    SupplierService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String company,
                                      @RequestParam(defaultValue = "") String contact,
                                      Model model) {
        var grid = service.getSupplierGrid(page, company, contact);
        return ResponseEntity.status(HttpStatus.OK).body(grid);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) Long id) {
        var selected = service.getUpdate(id);
        return ResponseEntity.status(200).body(selected);
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertSupplierDTO dto, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            var hasilEntity = service.saveSupplier(dto);
            return ResponseEntity.status(201).body(hasilEntity);
        }
        var validationErrors = bindingResult.getAllErrors();
        var formatedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formatedErrors);
    }
    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertSupplierDTO dto, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            var hasilEntity = service.saveSupplier(dto);
            return ResponseEntity.status(201).body(hasilEntity);
        }
        var validationErrors = bindingResult.getAllErrors();
        var formatedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formatedErrors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete (@PathVariable(required = true) Long id) {
        service.delete(id);
        return ResponseEntity.status(204).body(null);
    }

}
