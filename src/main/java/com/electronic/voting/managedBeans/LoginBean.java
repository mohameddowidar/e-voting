package com.electronic.voting.managedBeans;

import com.electronic.voting.entities.Voter;
import com.electronic.voting.services.VoterService;
import lombok.Data;
import lombok.extern.java.Log;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Component
@Scope("view")
@Data
@Log
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private VoterService voterService;
    private String nationalId;

    @PostConstruct
    public void init() {

    }

    public void login(){
        List<Voter> allByNationalIdContainingIgnoreCase = voterService.findAllByNationalIdContainingIgnoreCase(this.nationalId);
        if(allByNationalIdContainingIgnoreCase == null || allByNationalIdContainingIgnoreCase.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "برجاء إضافة رقم هوية مصري صالح", null));
            return;
        }
        // Assuming the first match is the authenticated user
        Voter authenticatedVoter = allByNationalIdContainingIgnoreCase.get(0);
        try {
            // Set session attributes
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("USER_NAME", authenticatedVoter.getName());
            session.setAttribute("DEPARTMENT_NAME", authenticatedVoter.getAddress()); // Assuming you have this info in Voter
            session.setAttribute("ORGANIZATION_NAME", authenticatedVoter.getEmail()); // Assuming you have this info in Voter
            session.setAttribute("EMAIL_ADDRESS", authenticatedVoter.getEmail());
            session.setAttribute("PHONE_NUMBER", authenticatedVoter.getPhoneNumber());
            session.setAttribute("USER_ID", authenticatedVoter.getVoterId());
            session.setAttribute("NATIONAL_ID", authenticatedVoter.getNationalId());
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            log.severe("IOException during redirecting: " + e.getMessage());
        }
    }

    public void logout(){
        this.nationalId= null;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("USER_ID", null);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("indexLanding.xhtml");
        } catch (IOException e) {
            log.severe("IOException during redirecting: " + e.getMessage());
        }
    }

    public String getUserImage() {
        String imageStr="";
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            HttpSession session = request.getSession(false);
            String nationalIdString = (String) (session != null ? session.getAttribute("NATIONAL_ID") : null);
            List<Voter> allByNationalIdContainingIgnoreCase = voterService.findAllByNationalIdContainingIgnoreCase(nationalIdString);
            Voter authenticatedVoter = allByNationalIdContainingIgnoreCase.get(0);
            String imgStr= "";
            if (authenticatedVoter.getFaceBiometricData()!= null) {
                try {
                    imgStr = new String(Base64.encodeBase64(authenticatedVoter.getFaceBiometricData()), "UTF-8");
                } catch (IOException e) {
                    log.severe(e.getMessage());
                }
            }
            return imgStr;
        } catch (Exception e) {
            log.severe(e.getMessage());
            return imageStr;
        }
    }


}
