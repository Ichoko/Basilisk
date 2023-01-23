package com.basilisk.dao;

import com.basilisk.dto.CategoryGridDTO;
import com.basilisk.dto.DropDownDTO;
import com.basilisk.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("""
            SELECT new com.basilisk.dto.CategoryGridDTO(cat.id,cat.name, cat.description)
            FROM Category AS cat
            WHERE cat.name LIKE %:cariName%
            """)
    public Page<CategoryGridDTO> findByName(@Param("cariName") String searchName, Pageable pageable);

    @Query("""
            SELECT COUNT(cat.id)
            FROM Category AS cat
            WHERE cat.name = :name and cat.id !=:id
            """)
    public Long CountByNameId(@Param("name") String name, @Param("id") Long id);

    @Query("""
            SELECT new com.basilisk.dto.DropDownDTO(cat.id,cat.name)
            FROM Category AS cat
            """)
    public List<DropDownDTO> getDropDown();


}
