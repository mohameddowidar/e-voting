package com.electronic.voting.repositories;

import com.electronic.voting.entities.City;
import com.electronic.voting.entities.Complain;
import com.electronic.voting.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ComplainRepository extends CrudRepository<Complain, Long> {
	
	Page<Complain> findAll(Pageable page);
	// find all complain using criteria
	 @Query("SELECT c FROM Complain c " +
	           "WHERE (:complainId IS NULL OR c.complainId = :complainId) " +
	           "AND (:complainTitle IS NULL OR c.complainTitle LIKE %:complainTitle%) " +
	           "AND (:complainCity IS NULL OR c.complainCity = :complainCity) " +
	           "AND (:status IS NULL OR c.status = :status) "+
	           "AND (:complainDateFrom IS NULL OR c.complainDate >= :complainDateFrom) " +
	           "AND (:complainDateTo IS NULL OR c.complainDate <= :complainDateTo)")
	    Page<Complain> findAll(
	        @Param("complainId") Long complainId,
	        @Param("complainTitle") String complainTitle,
	        @Param("complainCity") City complainCity,
	        @Param("status") Status status,
	        @Param("complainDateFrom") Date complainDateFrom,
	        @Param("complainDateTo") Date complainDateTo,
	        Pageable pageable
	    );

}
