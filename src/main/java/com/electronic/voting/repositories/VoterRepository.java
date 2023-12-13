package com.electronic.voting.repositories;

import com.electronic.voting.entities.Voter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Long> {
    Page<Voter> findAll(Pageable page);
    // find all Voter using criteria
    @Query("SELECT c FROM Voter c " +
            "WHERE (:nationalId IS NULL OR c.nationalId = :nationalId) " +
            "AND (:name IS NULL OR c.name LIKE :name ) " +
            "AND (:VoterDateFrom IS NULL OR c.dateOfBirth >= :VoterDateFrom) " +
            "AND (:VoterDateTo IS NULL OR c.dateOfBirth <= :VoterDateTo)")
    Page<Voter> findAll(
            @Param("nationalId") String nationalId,
            @Param("name") String name,
            @Param("VoterDateFrom") Date VoterDateFrom,
            @Param("VoterDateTo") Date VoterDateTo,
            Pageable pageable
    );

    List<Voter> findAllByNationalIdContainingIgnoreCase(String nationalId);

    @Transactional
    @Modifying
    void deleteVoterByVoterId(Long voterId);

}
