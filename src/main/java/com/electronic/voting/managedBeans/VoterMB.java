package com.electronic.voting.managedBeans;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Voter;
import com.electronic.voting.lazyModels.VoterLazyDataModel;
import com.electronic.voting.services.VoterService;
import lombok.Data;
import lombok.extern.java.Log;
import org.apache.tomcat.util.codec.binary.Base64;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Component
@Scope("view")
@Data
@Log
public class VoterMB {

    @Autowired
    private VoterService voterService;
    @Autowired
    private VoterLazyDataModel voterLazyDataModel;
    private List<Voter> voters;
    private Voter selectedVoter;
    private Long voterId;
    private String imgIcon;
    private UploadedFile imageFileContent;

    @PostConstruct
    public void init() {
        this.voterLazyDataModel.setSearchDTO(new SearchDTO());
        this.voters = voterService.getAllVoters();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String voterIdParam = request.getParameter("voterId");

        if (voterIdParam != null && !voterIdParam.isEmpty()) {
            try {
                voterId = Long.parseLong(voterIdParam);
                this.selectedVoter= voterService.getVoterById(voterId);
                if(this.selectedVoter.getFaceBiometricData() != null)
                try {
                    this.imgIcon = new String(Base64.encodeBase64(this.selectedVoter.getFaceBiometricData()), "UTF-8");
                } catch (IOException e) {
                    log.severe(e.getMessage());
                }
            } catch (NumberFormatException e) {
                voterId = null;
            }
        }else{
            this.selectedVoter= new Voter();
            this.imgIcon = null;
        }
    }

    public void clearSearchFields() {
        voterLazyDataModel.setSearchDTO(new SearchDTO());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم اعادة تعيين البحث", null));
    }

    public void deleteVoter(Voter selectedVoter) {
        if (selectedVoter != null && selectedVoter.getVoterId() != null) {
            voterService.deleteVoter(selectedVoter.getVoterId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم حذف الناخب ", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لم يتم حذف الناخب", "complain can't be null!"));
        }
    }
    public void save() {
        if("".equalsIgnoreCase(this.imgIcon)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "الرجاء إضافة صورة المواطن   ", null));
        }
        this.voterService.saveVoter(this.selectedVoter);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تمت إضافة المواطن بنجاح! ", null));
    }

    // Method to handle file upload
    public void uploadIcon(FileUploadEvent event) {
        System.out.println("in handle file upload...");
        try {
            imageFileContent = event.getFile();
            if (imageFileContent != null && imageFileContent.getContent() != null) {
                this.imgIcon = new String(Base64.encodeBase64(imageFileContent.getContent()), "UTF-8");
                this.selectedVoter.setFaceBiometricData(imageFileContent.getContent());
            }
        } catch (Exception e) {
           log.severe(e.getMessage()+ "" );
        }
    }



}
