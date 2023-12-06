package com.electronic.voting.services.impl;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Voter;
import com.electronic.voting.repositories.VoterRepository;
import com.electronic.voting.services.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoterServiceImpl implements VoterService {

    @Autowired
    private VoterRepository voterRepository;

    @Override
    public Voter saveVoter(Voter voter) {
        return voterRepository.save(voter);
    }

    @Override
    public void deleteVoter(Long voterId) {
        voterRepository.deleteById(voterId);
    }

    @Override
    public Voter getVoterById(Long voterId) {
        return voterRepository.findById(voterId).orElse(null);
    }

    @Override
    public List<Voter> findAllByNationalIdContainingIgnoreCase(String nationalId) {
        return voterRepository.findAllByNationalIdContainingIgnoreCase(nationalId);
    }

    @Override
    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    @Override
    public Page<Voter> searchVoters(SearchDTO searchDTO, int pageNumber, int pageSize) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate").descending());
            return voterRepository.findAll(searchDTO.getNationalId(), searchDTO.getTitle(), searchDTO.getFromDate(),searchDTO.getToDate(), pageable);
    }

    @Override
    public int getAllVotersCount() {
        return (int) voterRepository.count();
    }
}
