package com.electronic.voting.managedBeans;

import com.electronic.voting.dto.NavigationDTO;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
        this.navList = new ArrayList<>(Arrays.asList(
                new NavigationDTO("إدارة الانتخابات", "pi pi-home", "elections.xhtml", "001"),
                new NavigationDTO("إدارة الأصوات", "pi pi-users", "votes.xhtml", "002"),
                new NavigationDTO("إدارة المرشحين ", "pi pi-globe", "candidates.xhtml", "003"),
                new NavigationDTO(" إدارة الناخبين", "pi pi-calendar-plus", "voters.xhtml", "004"),
                new NavigationDTO("  صوت الان", "pi pi-user-plus", "add_vote.xhtml", "004")
        ));

    }

}
