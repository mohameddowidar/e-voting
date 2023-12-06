package com.electronic.voting.managedBeans;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Vote;
import com.electronic.voting.lazyModels.VoteLazyDataModel;
import com.electronic.voting.services.VoteService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Component
@Scope("view")
@Data
@Log
public class VoteMB {

    @Autowired
    private VoteService voteService;
    @Autowired
    private VoteLazyDataModel voteLazyDataModel;
    private Vote selectedVote;
    private Long VoteId;

    @PostConstruct
    public void init() {
        this.voteLazyDataModel.setSearchDTO(new SearchDTO());
    }

    public void clearSearchFields() {
        voteLazyDataModel.setSearchDTO(new SearchDTO());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم اعادة تعيين البحث", null));
    }

    public void deleteVote(Vote selectedVote) {
        if (selectedVote != null && selectedVote.getId() != null) {
            voteService.deleteVote(selectedVote.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم حذف الناخب ", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لم يتم حذف الناخب", "complain can't be null!"));
        }
    }

}
