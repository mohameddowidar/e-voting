package com.electronic.voting.managedBeans;

import com.electronic.voting.dto.NavigationDTO;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Scope("view")
@Data
@Log
public class ServiceBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<NavigationDTO> navList;
    private Long nationalId;

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String userId = (String) (session != null ? session.getAttribute("NATIONAL_ID") : null);
        if("29109011710612".equalsIgnoreCase(userId)){
            this.navList = new ArrayList<>(Arrays.asList(
                    new NavigationDTO("إدارة الانتخابات", "fa-list-check", "elections.xhtml", "001"),
                    new NavigationDTO("إدارة الأصوات", "fa-folder-tree", "votes.xhtml", "002"),
                    new NavigationDTO("إدارة المرشحين ", "fa-clock-rotate-left", "candidates.xhtml", "003"),
                    new NavigationDTO(" إدارة الناخبين", "fa-laptop-code", "voters.xhtml", "004"),
                    new NavigationDTO("  صوت الان", "fa-laptop-code", "add_vote.xhtml", "004"),
                    new NavigationDTO("نتائج الإنتخابات", "fa-database", "results.xhtml", "004")
            ));
        }else{
            this.navList = new ArrayList<>(Arrays.asList(
                    new NavigationDTO("  صوت الان", "fa-laptop-code", "add_vote.xhtml", "004")
            ));
        }
    }

}
