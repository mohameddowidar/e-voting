package com.electronic.voting.services;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Election;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ElectionService {
    List<Election> findAll();
    Election findById(Integer id);
    Election save(Election election);
    void delete(Integer id);

    Page<Election> searchVElection(SearchDTO searchDTO, int pageNumber, int pageSize);

    int getAllElectionCount();
}

