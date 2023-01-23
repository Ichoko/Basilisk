package com.basilisk.validator;

import com.basilisk.dto.UpsertCustomerDTO;
import com.basilisk.dto.UpsertDeliveryDTO;
import com.basilisk.service.CustomerService;
import com.basilisk.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueDeliveryNameValidator implements ConstraintValidator<UniqueDeliveryName, UpsertDeliveryDTO> {
    @Autowired
    private DeliveryService service;
    @Override
    public void initialize(UniqueDeliveryName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UpsertDeliveryDTO upsertDeliveryDTO, ConstraintValidatorContext constraintValidatorContext) {
        //        Long id = (Long)(new BeanWrapperImpl(value).getPropertyValue("id"));
        Long id =  upsertDeliveryDTO.getId();
        id = (id == null) ? 0l : id ;
//        String name = (new BeanWrapperImpl(value).getPropertyValue("name")).toString();
        String name = upsertDeliveryDTO.getCompanyName();
        return service.isValidName(name,id);
    }
}
