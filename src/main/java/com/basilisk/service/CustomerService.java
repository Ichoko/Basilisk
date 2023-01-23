package com.basilisk.service;

import com.basilisk.dao.CategoryRepository;
import com.basilisk.dao.CustomerRepository;
import com.basilisk.dto.*;
import com.basilisk.entity.Customer;
import com.basilisk.entity.Delivery;
import com.basilisk.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class    CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public Page<CustomerGridDTO> getCustomerGrid(Integer pageNumber, String companyName, String contactPerson, String city ){
        var pageable = PageRequest.of(pageNumber -1,10, Sort.by("id"));
        var  hasilGrid = customerRepository.findByName(companyName, contactPerson,city,pageable);
        return hasilGrid;
    }
    public Customer saveCustomer(UpsertCustomerDTO upsertCustomerDTO){
        Customer entity = new Customer(upsertCustomerDTO.getId(),
                upsertCustomerDTO.getCompanyName(),
                upsertCustomerDTO.getContactPerson(),
                upsertCustomerDTO.getAddress(),
                upsertCustomerDTO.getCity(),
                upsertCustomerDTO.getPhone(),
                upsertCustomerDTO.getEmail());
        return customerRepository.save(entity);
//        Disini Sudah Mengandung Insert dan Atau update
//        method save di JPA repository Auto mengenali PK pada Entity, karena di deklarasi di model @Id
//        save akan mencari apakah ada data yg ditemukan sesuai PKnya
    }

    public UpsertCustomerDTO getUpdate(Long id){
        Customer entity = customerRepository.findById(id).get();
        UpsertCustomerDTO upsertCustomerDTO = new UpsertCustomerDTO(
                entity.getId(),
                entity.getCompanyName(),
                entity.getContactPerson(),
                entity.getAddress(),
                entity.getCity(),
                entity.getPhone(),
                entity.getEmail()
        );
        return upsertCustomerDTO;
    }

//    public Boolean isValidName(String name, Long id){
//        var totalCustomer = customerRepository.CountByNameId(name, id);
//        if (totalCustomer >= 1){
//            return false;
//        }
//        return true;
//    }
    public void delete (Long id){
        customerRepository.deleteById(id);
//        Long totalProducts = productRepository.CountByCatergoryId(id);
////        if (totalProducts == 0) {
//        categoryRepository.deleteById(id);
////        }
//        return totalProducts;
    }

    public UpsertCustomerDTO getInfo(Long id){
        Customer entity = customerRepository.findById(id).get();
        UpsertCustomerDTO upsertCustomerDTO = new UpsertCustomerDTO(
                entity.getId(),
                entity.getCompanyName(),
                entity.getContactPerson(),
                entity.getAddress(),
                entity.getCity(),
                entity.getPhone(),
                entity.getEmail()
        );
        return upsertCustomerDTO;
    }
}

