package com.electronic.voting.lazyModels;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Voter;
import com.electronic.voting.services.VoterService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Scope("view")
@Getter
@Setter
public class VoterLazyDataModel extends LazyDataModel<Voter> {

    private static final long serialVersionUID = 1L;
    @Autowired
    private VoterService voterService;

    private SearchDTO searchDTO;

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return voterService.getAllVotersCount();
    }

    @Override
    public List<Voter> load(int first, int pageSize, Map<String, SortMeta> sortBy,
                            Map<String, FilterMeta> filterBy) {

        Page<Voter> allVoterPage;
        if (first == 0) {

            allVoterPage = voterService.searchVoters(this.searchDTO, first, pageSize);
        } else {

            allVoterPage = voterService.searchVoters(this.searchDTO, first / pageSize, pageSize);
        }
        this.setRowCount((int) allVoterPage.getTotalElements());
        return allVoterPage.getContent();
    }

}
