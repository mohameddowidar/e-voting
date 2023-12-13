package com.electronic.voting.repositories;

import com.electronic.voting.entities.Election;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface ElectionRepository extends JpaRepository<Election, Integer> {
    Page<Election> findAll(Pageable page);

    @Query("SELECT c FROM Election c  WHERE (:name IS NULL OR c.name LIKE :name) ")
    Page<Election> findAll(@Param("name") String name,Pageable pageable);

    @Query("SELECT e FROM Election e WHERE :date BETWEEN e.startDate AND e.endDate")
    List<Election> findAllWithDateBetween(@Param("date") Date date);

    @Transactional
    @Modifying
    void deleteElectionByElectionId(Integer electionId);


    @Query("select count(initPeriod) from Election initPeriod where "
            + " (:electionId=null or initPeriod.electionId <> :electionId )"
            + " and ( ( :startDate >= initPeriod.startDate AND :startDate <= initPeriod.endDate ) "
            + " or ( :endDate >=  initPeriod.startDate AND :endDate <=  initPeriod.endDate ) )"
            + "  order by initPeriod.createdDate desc ")
    int findActiveInitiativePeroids(@Param("electionId") Integer electionId,
                                    @Param("startDate") Date startDate, @Param("endDate")Date endDate);

}
