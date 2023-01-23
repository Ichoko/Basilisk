package com.basilisk.service;

import com.basilisk.dao.CategoryRepository;
import com.basilisk.dao.ProductRepository;
import com.basilisk.dto.CategoryGridDTO;
import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    //DI Dependency Ijection
    //IOC Inversion Of Control =
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Page<CategoryGridDTO> getCategoryGrid(Integer pageNumber, String searchName){
       var pageable = PageRequest.of(pageNumber -1,10, Sort.by("id"));
        var  hasilGrid = categoryRepository.findByName(searchName,pageable);
        return hasilGrid;
    }

    public CategoryGridDTO getOneCategory(Long id){
        Category entity = categoryRepository.findById(id).get();
        var dto = new CategoryGridDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
        return dto;
    }
    public Category saveCategory(UpsertCategoryDTO dto){
        Category entity = new Category(dto.getId(), dto.getName(), dto.getDescription());
        Category entityBaru = categoryRepository.save(entity);
        return  entityBaru;
//        Disini Sudah Mengandung Insert dan Atau update
//        method save di JPA repository Auto mengenali PK pada Entity, karena di deklarasi di model @Id
//        save akan mencari apakah ada data yg ditemukan sesuai PKnya
    }

    public UpsertCategoryDTO getUpdate(Long id){
        Category entity = categoryRepository.findById(id).get();
        UpsertCategoryDTO upsertCategoryDTO = new UpsertCategoryDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
        return upsertCategoryDTO;
    }
    public Boolean isValidName(String name, Long id){
        var totalCategory = categoryRepository.CountByNameId(name, id);
        if (totalCategory >= 1){
            return false;
        }
        return true;
    }

    public Long delete (Long id){
        Long totalProducts = productRepository.CountByCatergoryId(id);
        if (totalProducts == 0) {
            categoryRepository.deleteById(id);
        }
        return totalProducts;
    }
}
