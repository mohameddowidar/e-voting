package com.electronic.voting.services;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Candidate;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CandidateService {
    List<Candidate> findAll();
    Candidate findById(Integer id);
    Candidate save(Candidate candidate);
    void delete(Integer id);

    Page<Candidate> searchVCandidate(SearchDTO searchDTO, int pageNumber, int pageSize);

    int getAllCandidateCount();
}
