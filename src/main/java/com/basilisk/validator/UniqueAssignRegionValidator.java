package com.basilisk.validator;

import com.basilisk.dto.AssignSalesmanDTO;
import com.basilisk.dto.UpsertCustomerDTO;
import com.basilisk.dto.UpsertDeliveryDTO;
import com.basilisk.service.CustomerService;
import com.basilisk.service.DeliveryService;
import com.basilisk.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueAssignRegionValidator implements ConstraintValidator<UniqueAssignRegion, AssignSalesmanDTO> {
    @Autowired
    private RegionService service;
    @Override
    public void initialize(UniqueAssignRegion constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(AssignSalesmanDTO assignSalesmanDTO, ConstraintValidatorContext constraintValidatorContext) {
        //        Long id = (Long)(new BeanWrapperImpl(value).getPropertyValue("id"));
        Long id =  assignSalesmanDTO.getRegionId();
        id = (id == null) ? 0l : id ;
//        String name = (new BeanWrapperImpl(value).getPropertyValue("name")).toString();
        String name = assignSalesmanDTO.getSalesmanEmployeeNumber();
        return service.isValidName(id,name);
    }
}
