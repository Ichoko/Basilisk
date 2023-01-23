package com.basilisk.dao;

import com.basilisk.dto.DropDownDTO;
import com.basilisk.dto.SalesmanGridDTO;
import com.basilisk.entity.Salesman;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesmanRepository extends JpaRepository<Salesman, String> {

//    queri mengeluarkan =>> Employeenumber, FullName, Level, superiorFullName
    @Query("""
            SELECT new com.basilisk.dto.SalesmanGridDTO(
                sal.employeeNumber,
                CONCAT(sal.firstName,' ', sal.lastName),
                sal.level,
                CONCAT(sup.firstName,' ', sup.lastName)
            )
            FROM Salesman AS sal
                INNER JOIN sal.regions AS reg
                LEFT JOIN sal.superior as sup
            WHERE reg.id = :regionId
            """)

    public Page<SalesmanGridDTO> findByRegionId(@Param("regionId") Long regionId, Pageable pageable);

    @Query("""
			SELECT new com.basilisk.dto.SalesmanGridDTO(sal.employeeNumber, CONCAT(sal.firstName , ' ', sal.lastName), sal.level, 
				CONCAT(sup.firstName, ' ', sup.lastName))
			FROM Salesman AS sal
				LEFT JOIN sal.superior AS sup
			WHERE sal.employeeNumber LIKE %:employeeNumber%
				AND CONCAT(sal.firstName, ' ', sal.lastName) LIKE %:name%
				AND sal.level LIKE %:employeeLevel%
				AND (:superiorName = '' OR CONCAT(sup.firstName, ' ', sup.lastName) LIKE CONCAT('%',:superiorName,'%')) """)
    public Page<SalesmanGridDTO> findAll(@Param("employeeNumber") String employeeNumber,
                                         @Param("name") String name,
                                         @Param("employeeLevel") String employeeLevel,
										 @Param("superiorName") String superiorName,
										 Pageable pageable);

	@Query("""
			SELECT new com.basilisk.dto.DropDownDTO(sal.employeeNumber, CONCAT(sal.firstName , ' ', sal.lastName))
			FROM Salesman AS sal
			ORDER By sal.firstName """)
	public List<DropDownDTO> findAllOrderByFirstName();


	@Query("""
   			SELECT DISTINCT new com.basilisk.dto.DropDownDTO(sal.level, sal.level)
			FROM Salesman AS sal
			"""
	)
	public List<DropDownDTO> findEmployeeLevel();
}
