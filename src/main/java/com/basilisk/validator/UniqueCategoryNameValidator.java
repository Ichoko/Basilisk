package com.basilisk.validator;

import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.service.CategoryService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//public class UniqueCategoryNameValidator implements ConstraintValidator<UniqueCategoryName, Object> {
    public class UniqueCategoryNameValidator implements ConstraintValidator<UniqueCategoryName, UpsertCategoryDTO>{


    @Autowired
    private CategoryService service;

//method yang akan dijalankan pertama kali sebelum validator mamvalidasi
    @Override
    public void initialize(UniqueCategoryName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
//is Valid method yg langsung menentukan data dalam DTO Valid Atau Tidak
    @Override
    public boolean isValid(UpsertCategoryDTO value, ConstraintValidatorContext constraintValidatorContext) { // menggunakan javareflection dan tidak (Oject)
//        Long id = (Long)(new BeanWrapperImpl(value).getPropertyValue("id"));
        Long id = value.getId();
        id = (id == null) ? 0l : id ;
//        String name = (new BeanWrapperImpl(value).getPropertyValue("name")).toString();
        String name = value.getName();
        return service.isValidName(name,id);
    }
}
