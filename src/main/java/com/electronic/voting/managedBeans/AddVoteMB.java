package com.electronic.voting.managedBeans;

import com.electronic.voting.entities.Candidate;
import com.electronic.voting.entities.Election;
import com.electronic.voting.entities.Vote;
import com.electronic.voting.services.ElectionService;
import com.electronic.voting.services.VoteService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Scope("view")
@Data
@Log
public class AddVoteMB {

    @Autowired
    private ElectionService electionService;
    @Autowired
    private VoteService voteService;
    private List<Election> elections;
    private Election selectedElection;
    private Integer electionId;
    private int activeIndex = 0; // Default to first tab
    private List<Candidate> candidates;
    @PostConstruct
    public void init() {
        this.elections = electionService.findAll();
    }

    public void setSelectedElection(Election selectedElection) {
        this.selectedElection = selectedElection;
        this.candidates= new ArrayList<>(this.selectedElection.getCandidates());
    }

    public void addVote(Integer candidateId){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpSession session = request.getSession(false);
        Long userId = (Long) (session != null ? session.getAttribute("USER_ID") : null);
        Vote vote= new Vote();
        vote.setCandidateId(candidateId);
        vote.setElectionId(this.selectedElection.getElectionId());
        vote.setVoterId(userId != null ? userId.intValue() : 0 );
        vote.setTimeStamp(new Date());
        voteService.saveVote(vote);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم اعادة تعيين البحث", null));
    }
}
