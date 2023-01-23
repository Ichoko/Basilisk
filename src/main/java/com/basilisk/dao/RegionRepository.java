package com.basilisk.dao;

import com.basilisk.dto.DropDownDTO;
import com.basilisk.dto.RegionGridDTO;
import com.basilisk.dto.SalesmanGridDTO;
import com.basilisk.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository  extends JpaRepository<Region, Long> {
    @Query("""
			SELECT new com.basilisk.dto.RegionGridDTO(reg.id, reg.city, reg.remark)
			FROM Region AS reg
			WHERE reg.city LIKE %:city% """)
    public Page<RegionGridDTO> findByName(@Param("city") String city, Pageable pageable);

	@Query("""
			SELECT COUNT(reg.id)
			FROM Region AS reg
			WHERE reg.city LIKE %:city% """)
	public Long count(@Param("city") String city);

	@Query("""
            SELECT new com.basilisk.dto.RegionGridDTO(reg.id, reg.city, reg.remark)
            FROM Region AS reg
                INNER JOIN reg.salesmen AS sal
            WHERE sal.employeeNumber = :employeeNumber
            """)
	public Page<RegionGridDTO> findByEmployee(@Param("employeeNumber") String regionId, Pageable pageable);

	@Query("""
			SELECT COUNT(reg.id)
			FROM Salesman AS sal
				INNER JOIN sal.regions AS reg
			WHERE sal.employeeNumber = :employeeNumber AND reg.id = :regionId """)
	public Long count(@Param("employeeNumber") String employeeNumber, @Param("regionId") Long regionId);
	@Query("""
			SELECT  new com.basilisk.dto.DropDownDTO (reg.id, reg.city)
			FROM Region AS reg
			""")
	public List<DropDownDTO> findRegion();
}
