package com.electronic.voting.managedBeans;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Candidate;
import com.electronic.voting.entities.Election;
import com.electronic.voting.entities.Voter;
import com.electronic.voting.lazyModels.CandidateLazyDataModel;
import com.electronic.voting.services.CandidateService;
import com.electronic.voting.services.ElectionService;
import com.electronic.voting.services.VoterService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@Scope("view")
@Data
@Log
public class CandidateMB {
    @Autowired
    private CandidateService candidateService;

    @Autowired
    private VoterService voterService;
    @Autowired
    private CandidateLazyDataModel candidateLazyDataModel;
    @Autowired
    private ElectionService electionService;
    private List<Candidate> candidates;
    private Candidate selectedCandidate;
    private Integer candidateId;
    private Voter selectedVoter;
    private String searchNationalId;
    private List<Election> elections;
    private Integer selectedElectionId;


    @PostConstruct
    public void init() {
        this.candidateLazyDataModel.setSearchDTO(new SearchDTO());
        this.candidates = candidateService.findAll();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String CandidateIdParam = request.getParameter("candidateId");
        if (CandidateIdParam != null && !CandidateIdParam.isEmpty()) {
            try {
                candidateId = Integer.parseInt(CandidateIdParam);
                this.selectedCandidate = candidateService.findById(candidateId);
                this.selectedVoter= this.selectedCandidate.getVoter();
                this.selectedElectionId= this.selectedCandidate.getElection().getElectionId();
            } catch (NumberFormatException e) {
                candidateId = null;
            }
        } else {
            this.selectedCandidate = new Candidate();
        }
        elections = electionService.findAll(); // Load all elections
    }

    public void clearSearchFields() {
        candidateLazyDataModel.setSearchDTO(new SearchDTO());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم اعادة تعيين البحث", null));
    }

    public void deleteCandidate(Candidate selectedCandidate) {
        if (selectedCandidate != null && selectedCandidate.getCandidateId() != null) {
            candidateService.delete(selectedCandidate.getCandidateId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم حذف الناخب ", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لم يتم حذف الناخب", "complain can't be null!"));
        }
    }

    public void save() {
        if(!validateOnOtherValues())
            return;
        this.assignElectionToCandidate();
        this.candidateService.save(this.selectedCandidate);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم إضافة المرشح بنجاح  ", null));
    }

    private boolean validateOnOtherValues() {
        if("".equalsIgnoreCase(this.selectedCandidate.getSymbolName())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "الرجاء إضافة اسم الرمز ", null));
            return false;
        }
        if(this.getSelectedElectionId() == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "الرجاء اختر الانتخابات ", null));
            return false;
        }
        if("".equalsIgnoreCase(this.selectedCandidate.getAffiliatedParty())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "الرجاء إضافة الحزب المنتمي ", null));
            return false;
        }
        if("".equalsIgnoreCase(this.selectedCandidate.getBiography())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "الرجاء إضافة السيرة الذاتية", null));
            return false;
        }
        return true;
    }

    public void searchVoterByNationalId(){
        List<Voter> allByNationalIdContainingIgnoreCase = voterService.findAllByNationalIdContainingIgnoreCase(searchNationalId);
        this.selectedVoter= allByNationalIdContainingIgnoreCase != null ? allByNationalIdContainingIgnoreCase.get(0) : null;
    }

    public void assignElectionToCandidate() {
        if (selectedElectionId != null) {
            Election election = electionService.findById(selectedElectionId);
            selectedCandidate.setElection(election);
        }
        this.selectedCandidate.setVoter(this.selectedVoter);
    }
}
