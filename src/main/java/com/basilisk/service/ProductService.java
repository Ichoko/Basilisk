package com.basilisk.service;

import com.basilisk.dao.CategoryRepository;
import com.basilisk.dao.ProductRepository;
import com.basilisk.dao.SupplierRepository;
import com.basilisk.dto.DropDownDTO;
import com.basilisk.dto.ProductGridDTO;
import com.basilisk.dto.UpsertProductDTO;
import com.basilisk.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    public Page<ProductGridDTO> getProductGrid(Integer pageNumber, String productName){
        var pageable = PageRequest.of(pageNumber -1,10, Sort.by("id"));
        var  hasilGrid = productRepository.findByName(productName, pageable);
        return hasilGrid;
    }

    public List<DropDownDTO> getCategoryDropDown(){
        return categoryRepository.getDropDown();
    }
    public  List<DropDownDTO> getSupplierDropDown(){
        return supplierRepository.getDropDown();
    }

    public UpsertProductDTO getUpdate(Long id){
        var entity = productRepository.findById(id).get();
        var dto = new UpsertProductDTO(
                entity.getId(),
                entity.getName(),
                entity.getSupplierId(),
                entity.getCategoryId(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock(),
                entity.getOnOrder(),
                entity.getDiscontinue()
        );
        return dto;
    }

    public void delete (Long id){
        productRepository.deleteById(id);}

    @Transactional //
    public Product save(UpsertProductDTO dto){
        var entity = new Product(
                dto.getId(),
                dto.getName(),
                dto.getSupplierId(),
                dto.getCategoryId(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStock(),
                dto.getOnOrder(),
                dto.getDiscontinue()
        );
       return productRepository.save(entity);
    }
}
