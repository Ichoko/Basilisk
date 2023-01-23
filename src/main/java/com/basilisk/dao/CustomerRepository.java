package com.basilisk.dao;

import com.basilisk.dto.CustomerGridDTO;
import com.basilisk.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("""
			SELECT new com.basilisk.dto.CustomerGridDTO(cus.id, cus.companyName, cus.contactPerson, cus.city) 
			FROM Customer AS cus
			WHERE cus.companyName LIKE %:companyName% 
			AND cus.contactPerson LIKE %:contactPerson% 
			AND cus.city LIKE %:city%
			AND cus.deleteDate IS NULL """)
    public Page<CustomerGridDTO> findByName(@Param("companyName") String companyName,
                                         @Param("contactPerson") String contactPerson,
                                         @Param("city") String city,
                                         Pageable pageable);


}
