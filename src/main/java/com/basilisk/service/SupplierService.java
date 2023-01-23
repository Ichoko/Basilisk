package com.basilisk.service;

import com.basilisk.dao.SupplierRepository;
import com.basilisk.dto.CategoryGridDTO;
import com.basilisk.dto.SupplierGridDTO;
import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.dto.UpsertSupplierDTO;
import com.basilisk.entity.Category;
import com.basilisk.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SupplierService {
    //DI Dependency Ijection
    //IOC Inversion Of Control =
    @Autowired
    private SupplierRepository supplierRepository;
    public Page<SupplierGridDTO> getSupplierGrid(Integer pageNumber, String company, String contact ){
        var pageable = PageRequest.of(pageNumber -1,10, Sort.by("id"));
        var  hasilGrid = supplierRepository.findByName(company, contact,pageable);
        return hasilGrid;
    }
    public Supplier saveSupplier(UpsertSupplierDTO dto){
        Supplier entity = new Supplier(dto.getId(),dto.getCompanyName(),dto.getContactPerson(),
                dto.getJobTitle(),dto.getAddress(),dto.getCity(),dto.getPhone(),
                dto.getEmail());
       return supplierRepository.save(entity);
    }
    public UpsertSupplierDTO getUpdate(Long id){
        var supplier = supplierRepository.findById(id).get();
        UpsertSupplierDTO upsertSupplierDTO = new UpsertSupplierDTO(
                supplier.getId(),
                supplier.getCompanyName(),
                supplier.getContactPerson(),
                supplier.getJobTitle(),
                supplier.getAddress(),
                supplier.getCity(),
                supplier.getPhone(),
                supplier.getEmail()
        );
        return upsertSupplierDTO;
    }

    public void delete(Long id) {
        var supplier = supplierRepository.findById(id).get();
        supplier.setDeleteDate(LocalDateTime.now());
        supplierRepository.save(supplier);
    }

}
