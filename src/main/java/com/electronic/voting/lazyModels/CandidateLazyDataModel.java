package com.electronic.voting.lazyModels;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Candidate;
import com.electronic.voting.services.CandidateService;
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
public class CandidateLazyDataModel extends LazyDataModel<Candidate> {

    private static final long serialVersionUID = 1L;
    @Autowired
    private CandidateService CandidateService;

    private SearchDTO searchDTO;

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return CandidateService.getAllCandidateCount();
    }

    @Override
    public List<Candidate> load(int first, int pageSize, Map<String, SortMeta> sortBy,
                            Map<String, FilterMeta> filterBy) {

        Page<Candidate> allCandidatePage;
        if (first == 0) {
            allCandidatePage = CandidateService.searchVCandidate(this.searchDTO, first, pageSize);
        } else {
            allCandidatePage = CandidateService.searchVCandidate(this.searchDTO, first / pageSize, pageSize);
        }
        this.setRowCount((int) allCandidatePage.getTotalElements());
        return allCandidatePage.getContent();
    }

}
