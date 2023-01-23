package com.basilisk.rest;


import com.basilisk.dto.CategoryGridDTO;
import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.dto.errorDTO;
import com.basilisk.service.CategoryService;
import com.basilisk.utility.MapperHelper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class CategoryRestController {
    @Autowired
    private CategoryService service;

    @GetMapping
//    public ResponseEntity< Page<CategoryGridDTO> > get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "") String name,Model model){
    public ResponseEntity<Object>  get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "") String name,Model model){
            try{var grid = service.getCategoryGrid(page, name);
                return ResponseEntity.status(HttpStatus.OK).body(grid);
//            return ResponseEntity.status(200).body(grid);
            }catch (Exception exception){
                var cause = exception.getCause().getCause().toString();
                var errorObject = new errorDTO(
                        cause,
                        exception.getMessage(),
                        LocalDateTime.now()
                );
                return ResponseEntity.status(500).body(errorObject);
            }
    }

    @Operation(summary = "End Point Yang digunakan untuk mendapatkan satu kategori berdasarkan ID kategory",
    description = "Ini akan menjadi deskripsi dari ini dan akan menjadi deskripsi saat dibuka")
    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) Long id){
        var selected = service.getOneCategory(id);
        return ResponseEntity.status(200).body(selected);
    }

    @PostMapping
    public ResponseEntity<Object> post (@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult){
    if(!bindingResult.hasErrors()){
        var hasilEntity = service.saveCategory(dto);
        return ResponseEntity.status(201).body(hasilEntity);
    }

    var validationErrors = bindingResult.getAllErrors();
    var formatedErrors = MapperHelper.getErrors(validationErrors);
    return ResponseEntity.status(422).body(formatedErrors);
    }

    @PutMapping
    public ResponseEntity<Object> put (@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult){
    if(!bindingResult.hasErrors()){
        var hasilEntity = service.saveCategory(dto);
        return ResponseEntity.status(200).body(hasilEntity);
    }
        var validationErrors = bindingResult.getAllErrors();
        var formatedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formatedErrors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete (@PathVariable(required = true) Long id){
        var dependencies= service.delete(id);
        if(dependencies == 0) {
            return ResponseEntity.status(204).body(null);
        }
        return ResponseEntity.status(500).body(String.format("Tidak Bisa Dihapus, ada %s product yang ada dalam kategori ini",dependencies));
    }
}
