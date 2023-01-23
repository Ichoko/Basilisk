package com.basilisk.service;

import com.basilisk.dao.RegionRepository;
import com.basilisk.dao.SalesmanRepository;
import com.basilisk.dao.SupplierRepository;
import com.basilisk.dto.*;
import com.basilisk.entity.Customer;
import com.basilisk.entity.Region;
import com.basilisk.entity.Salesman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesmanService {
    @Autowired
    private SalesmanRepository salesmanRepository;
    @Autowired
    private RegionRepository regionRepository;

    public Page<SalesmanGridDTO> getSalesmanGrid(Integer pageNumber, String employeeNumber, String name, String employeeLevel,String superiorName){
        var pageable = PageRequest.of(pageNumber -1,10, Sort.by("id"));
        var  hasilGrid = salesmanRepository.findAll(employeeNumber, name,employeeLevel, superiorName,pageable);
        return hasilGrid;
    }
    public void saveSalesman(UpsertSalesmanDTO dto){
        String superiorEmployeeNumber = null;
        if(!dto.getSuperiorEmployeeNumber().equals("")) {
            superiorEmployeeNumber = dto.getSuperiorEmployeeNumber();
        }
        Salesman entity = new Salesman(
                dto.getEmployeeNumber(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getLevel(),
                dto.getBirthDate(),
                dto.getHiredDate(),
                dto.getAddress(),
                dto.getCity(),
                dto.getPhone(),
                superiorEmployeeNumber);
        salesmanRepository.save(entity);
    }

    public UpsertSalesmanDTO getUpdate(String id){
        var salesman = salesmanRepository.findById(id).get();
        UpsertSalesmanDTO dto = new UpsertSalesmanDTO(
                salesman.getEmployeeNumber(),
                salesman.getFirstName(),
                salesman.getLastName(),
                salesman.getLevel(),
                salesman.getBirthdate(),
                salesman.getHireDate(),
                salesman.getAddress(),
                salesman.getCity(),
                salesman.getPhone(),
                salesman.getSuperiorEmployeeNumber()
        );
        return dto;
    }

    public Page<RegionGridDTO> getDetailGrid(Integer pageNumber, String employeeNumber){
        var pagination = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));
        var hasilGrid = regionRepository.findByEmployee(employeeNumber, pagination);
        return hasilGrid;
    }

    public SalesmanHeaderDTO getHeaderSalesman(String employee){
        Optional<Salesman> nullableEntity = salesmanRepository.findById(employee);
        Salesman entity = nullableEntity.get();
        var dto = new SalesmanHeaderDTO(
                entity.getEmployeeNumber(),
                String.format(entity.getFirstName(),' ', entity.getLastName()),
                entity.getLevel(),
                entity.getSuperiorEmployeeNumber()

        );
        return dto;
    }
    public List<DropDownDTO> getLevelSalesman() {
        List<DropDownDTO> levelSalesmanDropdown = salesmanRepository.findEmployeeLevel();
        return levelSalesmanDropdown;
    }
    public List<DropDownDTO> getSalesmanDropdown() {
        List<DropDownDTO> salesmanDropdown = salesmanRepository.findAllOrderByFirstName();
        return salesmanDropdown;
    }

    public List<DropDownDTO> getRegionDropdown() {
        List<DropDownDTO> regionDropdown = regionRepository.findRegion();
        return regionDropdown;
    }

    public void deleteSalesmanRegion(Long regionId, String employeeNumber) {
        Optional<Salesman> nullableSalesman = salesmanRepository.findById(employeeNumber);
        Salesman salesman = nullableSalesman.get();

        Optional<Region> nullableRegion = regionRepository.findById(regionId);
        Region region = nullableRegion.get();

        salesman.getRegions().remove(region);
        salesmanRepository.save(salesman);
    }


    public void assignRegion(AssignRegionDTO dto) {
        Optional<Salesman> nullableSalesman = salesmanRepository.findById(dto.getSalesmanEmployeeNumber());
        Salesman salesman = nullableSalesman.get();

        Optional<Region> nullableRegion = regionRepository.findById(dto.getRegionId());
        Region region = nullableRegion.get();

        salesman.getRegions().add(region);
        salesmanRepository.save(salesman);
    }
}
