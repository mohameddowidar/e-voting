package com.electronic.voting.lazyModels;

import com.electronic.voting.dto.SearchDTO;
import com.electronic.voting.entities.Vote;
import com.electronic.voting.services.VoteService;
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
public class VoteLazyDataModel extends LazyDataModel<Vote> {

    private static final long serialVersionUID = 1L;
    @Autowired
    private VoteService voteService;

    private SearchDTO searchDTO;

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return voteService.getAllVotesCount();
    }

    @Override
    public List<Vote> load(int first, int pageSize, Map<String, SortMeta> sortBy,
                            Map<String, FilterMeta> filterBy) {

        Page<Vote> allVotePage;
        if (first == 0) {

            allVotePage = voteService.searchVotes(this.searchDTO, first, pageSize);
        } else {

            allVotePage = voteService.searchVotes(this.searchDTO, first / pageSize, pageSize);
        }
        this.setRowCount((int) allVotePage.getTotalElements());
        return allVotePage.getContent();
    }

}
