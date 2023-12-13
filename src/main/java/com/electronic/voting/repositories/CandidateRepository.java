package com.electronic.voting.repositories;

import com.electronic.voting.entities.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    Page<Candidate> findAll(Pageable page);
    @Query("SELECT c FROM Candidate c " +
            "WHERE (:nationalId IS NULL OR c.voter.nationalId = :nationalId) " +
            "AND (:name IS NULL OR c.voter.name LIKE :name ) ")
    Page<Candidate> findAll(
            @Param("nationalId") String nationalId,
            @Param("name") String name,
            Pageable pageable
    );
    @Transactional
    @Modifying
    void deleteCandidateByCandidateId(Integer candidateId);
}
