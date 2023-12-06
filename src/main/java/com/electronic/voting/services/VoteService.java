package com.electronic.voting.services;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Vote;
import com.electronic.voting.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public Vote getVoteById(Integer id) {
        return voteRepository.findById(id).orElse(null);
    }

    public void deleteVote(int voteId) {
        voteRepository.deleteById(voteId);
    }

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }


    public Page<Vote> searchVotes(SearchDTO searchDTO, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate").descending());
        return voteRepository.findAll(
                searchDTO.getNationalId(), searchDTO.getTitle(),
                searchDTO.getCandidateId(), searchDTO.getElectionId(),
                searchDTO.getFromDate(), searchDTO.getToDate(), pageable);
    }

    public int getAllVotesCount() {
        return (int) voteRepository.count();
    }
}
