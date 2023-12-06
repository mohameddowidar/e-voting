package com.electronic.voting.repositories;

import com.electronic.voting.entities.Election;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ElectionRepository extends JpaRepository<Election, Integer> {
    Page<Election> findAll(Pageable page);
    // find all Voter using criteria
    @Query("SELECT c FROM Election c " +
            "WHERE (:name IS NULL OR c.name = :name) ")
    Page<Election> findAll(@Param("name") String name,Pageable pageable);
}
