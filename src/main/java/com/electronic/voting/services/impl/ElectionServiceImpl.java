package com.electronic.voting.services.impl;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Election;
import com.electronic.voting.repositories.ElectionRepository;
import com.electronic.voting.services.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectionServiceImpl implements ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Override
    public List<Election> findAll() {
        return electionRepository.findAll(Sort.by("updatedDate").descending());
    }

    @Override
    public Election findById(Integer id) {
        return electionRepository.findById(id).orElse(null);
    }

    @Override
    public Election save(Election election) {
        return electionRepository.save(election);
    }

    @Override
    public void delete(Integer id) {
        electionRepository.deleteById(id);
    }

    @Override
    public Page<Election> searchVElection(SearchDTO searchDTO, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate").descending());
        return electionRepository.findAll(searchDTO.getTitle(), pageable);
    }

    @Override
    public int getAllElectionCount() {
        return (int) electionRepository.count();
    }

}

