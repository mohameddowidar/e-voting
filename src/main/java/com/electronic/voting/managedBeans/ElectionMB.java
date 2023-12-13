package com.electronic.voting.managedBeans;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Election;
import com.electronic.voting.lazyModels.ElectionLazyDataModel;
import com.electronic.voting.services.ElectionService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Component
@Scope("view")
@Data
@Log
public class ElectionMB {

    @Autowired
    private ElectionService electionService;
    @Autowired
    private ElectionLazyDataModel electionLazyDataModel;
    private List<Election> elections;
    private Election selectedElection;
    private Integer electionId;

    private List<String> electionTypes;

    @PostConstruct
    public void init() {
        electionTypes = Arrays.asList("الانتخابات الرئاسية", "انتخابات مجلس الشيوخ", "الانتخابات البرلمانية");
        this.electionLazyDataModel.setSearchDTO(new SearchDTO());
        this.elections = electionService.findAll();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String electionIdParam = request.getParameter("electionId");
        if (electionIdParam != null && !electionIdParam.isEmpty()) {
            try {
                electionId = Integer.parseInt(electionIdParam);
                this.selectedElection = electionService.findById(electionId);
            } catch (NumberFormatException e) {
                electionId = null;
            }
        } else {
            this.selectedElection = new Election();
        }
    }

    public void clearSearchFields() {
        electionLazyDataModel.setSearchDTO(new SearchDTO());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم اعادة تعيين البحث", null));
    }

    public void deleteElection(Election selectedElection) {
        if(!selectedElection.getCandidates().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("لا يمكن حذف الانتخابات لأن هناك مرشحين", null));
            return;
        }
        if (selectedElection != null && selectedElection.getElectionId() != null) {
            electionService.delete(selectedElection.getElectionId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم حذف الناخب ", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لم يتم حذف الناخب", "complain can't be null!"));
        }
    }

    public void save() {
        if(this.electionService.isThereActiveInitiativePeroidsBetweenDates(this.selectedElection.getElectionId(),
                this.selectedElection.getStartDate(), this.selectedElection.getEndDate())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" يوجد فترة زمنية أخرى فى نفس التوقيت - من فضللك اختر مدة زمنية اخرى", null));
            return;
        }

        this.electionService.save(this.selectedElection);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تمت إضافة الانتخابات بنجاح ", null));
    }
}
