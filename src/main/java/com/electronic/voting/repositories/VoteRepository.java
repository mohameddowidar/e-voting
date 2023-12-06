package com.electronic.voting.repositories;

import com.electronic.voting.entities.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    Page<Vote> findAll(Pageable page);

    @Query("SELECT c FROM Vote c " +
            "WHERE (:nationalId IS NULL OR c.voter.nationalId = :nationalId) " +
            "AND (:name IS NULL OR c.voter.name LIKE %:name%) " +
            "AND (:candidateId IS NULL OR c.candidateId = :candidateId) " +
            "AND (:electionId IS NULL OR c.electionId = :electionId) " +
            "AND (:dateFrom IS NULL OR c.createdDate >= :dateFrom) " +
            "AND (:dateTo IS NULL OR c.createdDate <= :dateTo)")
    Page<Vote> findAll(
            @Param("nationalId") String nationalId,
            @Param("name") String name,
            @Param("candidateId") Integer candidateId,
            @Param("electionId") Integer electionId,
            @Param("dateFrom") Date dateFrom,
            @Param("dateTo") Date dateTo,
            Pageable pageable
    );
}
