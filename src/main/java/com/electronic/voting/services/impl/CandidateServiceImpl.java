package com.electronic.voting.services.impl;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Candidate;
import com.electronic.voting.repositories.CandidateRepository;
import com.electronic.voting.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate findById(Integer id) {
        return candidateRepository.findById(id).orElse(null);
    }

    @Override
    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public void delete(Integer id) {
        candidateRepository.deleteCandidateByCandidateId(id);
    }

    @Override
    public Page<Candidate> searchVCandidate(SearchDTO searchDTO, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate").descending());
        String searchString = (searchDTO.getTitle() == null) ? null : "%" + searchDTO.getTitle() + "%";
        return candidateRepository.findAll(searchDTO.getNationalId(), searchString, pageable);
    }

    @Override
    public int getAllCandidateCount() {
            return (int) candidateRepository.count();
    }

}
