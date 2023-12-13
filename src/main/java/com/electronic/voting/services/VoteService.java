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

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    public boolean hasVoted(Integer electionId){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpSession session = request.getSession(false);
        Long userId = (Long) (session != null ? session.getAttribute("USER_ID") : null);
        List<Vote> allByElectionIdAndVoterId = this.voteRepository.findAllByElectionIdAndVoterId(electionId, userId != null ? userId.intValue() : 0);
        if(!allByElectionIdAndVoterId.isEmpty()){
            return true;
        }
        return false;
    }
    public Page<Vote> searchVotes(SearchDTO searchDTO, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate").descending());
        String searchString = (searchDTO.getTitle() == null) ? null : "%" + searchDTO.getTitle() + "%";
        return voteRepository.findAll(
                searchDTO.getNationalId(), searchString,
                searchDTO.getCandidateId(), searchDTO.getElectionId(),
                searchDTO.getFromDate(), searchDTO.getToDate(), pageable);
    }

    public int getAllVotesCount() {
        return (int) voteRepository.count();
    }
}
