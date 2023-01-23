package com.basilisk.service;

import com.basilisk.dao.DeliveryRepository;
import com.basilisk.dto.CategoryGridDTO;
import com.basilisk.dto.DeliveryGridDTO;
import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.dto.UpsertDeliveryDTO;
import com.basilisk.entity.Category;
import com.basilisk.entity.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    DeliveryRepository deliveryRepository;
    public Page<DeliveryGridDTO> getCategoryGrid(Integer pageNumber, String companyName){
        var pageable = PageRequest.of(pageNumber -1,10, Sort.by("id"));
        var  hasilGrid = deliveryRepository.findByName(companyName,pageable);
        return hasilGrid;
    }

    public Delivery saveDelivery(UpsertDeliveryDTO upsertDeliveryDTO){
        Delivery entity = new Delivery(upsertDeliveryDTO.getId(),
                upsertDeliveryDTO.getCompanyName(),
                upsertDeliveryDTO.getPhone(),
                upsertDeliveryDTO.getCost());
        return deliveryRepository.save(entity);
//        Disini Sudah Mengandung Insert dan Atau update
//        method save di JPA repository Auto mengenali PK pada Entity, karena di deklarasi di model @Id
//        save akan mencari apakah ada data yg ditemukan sesuai PKnya
    }

    public UpsertDeliveryDTO getUpdate(Long id){
        Delivery entity = deliveryRepository.findById(id).get();
        UpsertDeliveryDTO upsertDeliveryDTO = new UpsertDeliveryDTO(
                entity.getId(),
                entity.getCompanyName(),
                entity.getPhone(),
                entity.getCost()
        );
        return upsertDeliveryDTO;
    }

    public Boolean isValidName(String name, Long id){
        var totalDelivery = deliveryRepository.CountByNameId(name, id);
        if (totalDelivery >= 1){
            return false;
        }
        return true;
    }
    public void delete (Long id){
        deliveryRepository.deleteById(id);
//        Long totalProducts = productRepository.CountByCatergoryId(id);
////        if (totalProducts == 0) {
//        categoryRepository.deleteById(id);
////        }
//        return totalProducts;
    }
}
