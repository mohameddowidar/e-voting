package com.electronic.voting.lazyModels;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Election;
import com.electronic.voting.services.ElectionService;
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
public class ElectionLazyDataModel extends LazyDataModel<Election> {

    private static final long serialVersionUID = 1L;
    @Autowired
    private ElectionService ElectionService;

    private SearchDTO searchDTO;

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return ElectionService.getAllElectionCount();
    }

    @Override
    public List<Election> load(int first, int pageSize, Map<String, SortMeta> sortBy,
                            Map<String, FilterMeta> filterBy) {

        Page<Election> allElectionPage;
        if (first == 0) {
            allElectionPage = ElectionService.searchVElection(this.searchDTO, first, pageSize);
        } else {
            allElectionPage = ElectionService.searchVElection(this.searchDTO, first / pageSize, pageSize);
        }
        this.setRowCount((int) allElectionPage.getTotalElements());
        return allElectionPage.getContent();
    }

}
