package com.electronic.voting.services;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Voter;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VoterService {
    Voter saveVoter(Voter voter);
    void deleteVoter(Long voterId);
    Voter getVoterById(Long voterId);

    List<Voter> findAllByNationalIdContainingIgnoreCase(String nationalId);
    List<Voter> getAllVoters();

    Page<Voter> searchVoters(SearchDTO searchDTO, int pageNumber, int pageSize);

    int getAllVotersCount();
}
