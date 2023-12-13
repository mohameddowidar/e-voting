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
import java.security.SecureRandom;
import java.util.List;

@Component
@Scope("session")
@Data
@Log
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private VoterService voterService;
    private String nationalId;
    private String otp;
    private String otpStored;
    private  Voter authenticatedVoter;

    @PostConstruct
    public void init() {

    }

    public void login(){
        List<Voter> allByNationalIdContainingIgnoreCase = voterService.findAllByNationalIdContainingIgnoreCase(this.nationalId);
        if(allByNationalIdContainingIgnoreCase == null || allByNationalIdContainingIgnoreCase.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "برجاء إضافة رقم هوية مصري صالح", null));
            return;
        }
        this.authenticatedVoter = allByNationalIdContainingIgnoreCase.get(0);
        try {
            this.otpStored= generateOtp(authenticatedVoter.getPhoneNumber());
            FacesContext.getCurrentInstance().getExternalContext().redirect("otp-login.xhtml");
        } catch (IOException e) {
            log.severe("IOException during redirecting: " + e.getMessage());
        }
    }

    public String generateOtp(String mobileNumber) {
        String otp = String.valueOf(new SecureRandom().nextInt(899999) + 100000);
        this.otpStored= otp;
        log.severe("Generated OTP for Mobile Number ::: "+mobileNumber + "  <<<<<  ::: " + otp+ "  >>>  ::: ");
        return otp;
    }
    public void otpLogin(){
        try {
            if(!this.otpStored.equalsIgnoreCase(this.otp)){
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "الرجاء إدخال كلمة المرور الصحيحة التي تم إرسالها إلى رقم هاتفك المحمول",
                                null));
                return;
            }
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("USER_NAME", authenticatedVoter.getName());
            session.setAttribute("DEPARTMENT_NAME", authenticatedVoter.getAddress());
            session.setAttribute("ORGANIZATION_NAME", authenticatedVoter.getEmail());
            session.setAttribute("EMAIL_ADDRESS", authenticatedVoter.getEmail());
            session.setAttribute("PHONE_NUMBER", authenticatedVoter.getPhoneNumber());
            session.setAttribute("USER_ID", authenticatedVoter.getVoterId());
            session.setAttribute("NATIONAL_ID", authenticatedVoter.getNationalId());
            this.otpStored= null;
            this.otp= null;
            FacesContext.getCurrentInstance().getExternalContext().redirect("face-login.xhtml");
        } catch (IOException e) {
            log.severe("IOException during redirecting: " + e.getMessage());
        }
    }

    public void logout(){
        this.nationalId= null;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("USER_ID", null);
        session.setAttribute("NATIONAL_ID", null);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/e-voting/login.xhtml");
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
