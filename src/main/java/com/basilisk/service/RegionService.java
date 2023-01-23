package com.basilisk.service;

import com.basilisk.dao.RegionRepository;
import com.basilisk.dao.SalesmanRepository;
import com.basilisk.dto.*;
import com.basilisk.entity.Category;
import com.basilisk.entity.Region;
import com.basilisk.entity.Salesman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private SalesmanRepository salesmanRepository;

    public Page<RegionGridDTO> getRegionGrid(Integer pageNumber, String searchName){
        var pageable = PageRequest.of(pageNumber -1,10, Sort.by("id"));
        var  hasilGrid = regionRepository.findByName(searchName,pageable);
        return hasilGrid;
    }
    public void saveRegion(UpsertRegionDTO upsertRegionDTO){
        Region entity = new Region(upsertRegionDTO.getId(), upsertRegionDTO.getCity(), upsertRegionDTO.getRemark());
        regionRepository.save(entity);
//        Disini Sudah Mengandung Insert dan Atau update
//        method save di JPA repository Auto mengenali PK pada Entity, karena di deklarasi di model @Id
//        save akan mencari apakah ada data yg ditemukan sesuai PKnya
    }

    public UpsertRegionDTO getUpdate(Long id){
        Region entity = regionRepository.findById(id).get();
        UpsertRegionDTO upsertRegionDTO = new UpsertRegionDTO(
                entity.getId(),
                entity.getCity(),
                entity.getRemark()
        );
        return upsertRegionDTO;

    }
//salesmanGrid Detail Dari Region Menu
    public void delete (Long id){
        regionRepository.deleteById(id);
    }

    public Page<SalesmanGridDTO> getDetailGrid(Integer pageNumber, Long id){
            var pageable = PageRequest.of(pageNumber -1,10, Sort.by("employeeNumber"));
            var  hasilGrid = salesmanRepository.findByRegionId(id,pageable);
            return hasilGrid;
    }


    public RegionHeaderDTO getHeader(Long id){
        var entity = regionRepository.findById(id).get();
        var dto = new RegionHeaderDTO(
                entity.getId(),
                entity.getCity(),
                entity.getRemark()
        );
        return dto;
    }

    public void deleteRegionSalesman(Long regionId, String employeeNumber) {
        Optional<Salesman> nullableSalesman = salesmanRepository.findById(employeeNumber);
        Salesman salesman = nullableSalesman.get();

        Optional<Region> nullableRegion = regionRepository.findById(regionId);
        Region region = nullableRegion.get();

        region.getSalesmen().remove(salesman);
        regionRepository.save(region);
    }

    public List<DropDownDTO> getSalesmanDropdown() {
        List<DropDownDTO> salesmanDropdown = salesmanRepository.findAllOrderByFirstName();
        return salesmanDropdown;
    }

    public void assignSalesman(AssignSalesmanDTO dto) {
        Optional<Salesman> nullableSalesman = salesmanRepository.findById(dto.getSalesmanEmployeeNumber());
        Salesman salesman = nullableSalesman.get();

        Optional<Region> nullableRegion = regionRepository.findById(dto.getRegionId());
        Region region = nullableRegion.get();

        region.getSalesmen().add(salesman);
        regionRepository.save(region);
    }


    public Boolean isValidName(Long regionId, String employeeNumber) {
        Long totalExistingRegionSalesman = regionRepository.count(employeeNumber, regionId);
        return (totalExistingRegionSalesman > 0) ? false : true;
    }
}
