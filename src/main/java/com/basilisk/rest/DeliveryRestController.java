package com.basilisk.rest;

import com.basilisk.dto.UpsertDeliveryDTO;
import com.basilisk.dto.errorDTO;
import com.basilisk.service.DeliveryService;
import com.basilisk.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/api/delivery")
public class DeliveryRestController {

    @Autowired
    DeliveryService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name){
        try {
            var grid = service.getCategoryGrid(page, name);
            return ResponseEntity.status(HttpStatus.OK).body(grid);
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

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) Long id){
        try{
            UpsertDeliveryDTO dto = service.getUpdate(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertDeliveryDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                var hasilEntity = service.saveDelivery(dto);
                return ResponseEntity.status(201).body(hasilEntity);
            } else {
                var validationErrors = bindingResult.getAllErrors();
                var formatedErrors = MapperHelper.getErrors(validationErrors);
                return ResponseEntity.status(422).body(formatedErrors);
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertDeliveryDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                var hasilEntity = service.saveDelivery(dto);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                var validationErrors = bindingResult.getAllErrors();
                var formatedErrors = MapperHelper.getErrors(validationErrors);
                return ResponseEntity.status(422).body(formatedErrors);
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete (@PathVariable(required = true) Long id) {
        service.delete(id);
        return ResponseEntity.status(204).body(null);
    }
}
