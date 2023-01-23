package com.basilisk.dao;

import com.basilisk.dto.DeliveryGridDTO;
import com.basilisk.entity.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    @Query("""
			SELECT new com.basilisk.dto.DeliveryGridDTO(del.id, del.companyName, del.phone, del.cost)
			FROM Delivery AS del
			WHERE del.companyName LIKE %:companyName% """)
    public Page<DeliveryGridDTO> findByName(@Param("companyName") String companyName, Pageable pageable);

	@Query("""
			SELECT COUNT(del.id)
			FROM Delivery AS del
			WHERE del.companyName = :name and del.id !=:id
			""")
	Long CountByNameId(@Param("name") String name, @Param("id") Long id);
}

