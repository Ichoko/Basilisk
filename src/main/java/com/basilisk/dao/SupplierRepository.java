package com.basilisk.dao;

import com.basilisk.dto.DropDownDTO;
import com.basilisk.dto.SupplierGridDTO;
import com.basilisk.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    @Query("""
            SELECT new com.basilisk.dto.SupplierGridDTO(sup.id,sup.companyName, sup.contactPerson, sup.jobTitle)
            FROM Supplier AS sup
            WHERE sup.companyName LIKE %:cariName% AND 
            sup.contactPerson LIKE %:cariContact%  AND
            sup.deleteDate IS NULL
            """)
    public Page<SupplierGridDTO> findByName(@Param("cariName") String company,
                                            @Param("cariContact")  String contact,
                                            Pageable pageable);

    @Query("""
            SELECT new com.basilisk.dto.DropDownDTO(sup.id,sup.companyName)
            FROM Supplier AS sup
            WHERE sup.deleteDate IS NULL
            """)
    public List<DropDownDTO> getDropDown();

}
