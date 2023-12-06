package com.electronic.voting.lazyModels;

import com.electronic.voting.entities.City;
import com.electronic.voting.entities.Complain;
import com.electronic.voting.entities.Status;
import com.electronic.voting.services.ComplainService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Scope("view")
@Getter
@Setter
public class ComplainLazyDataModel extends LazyDataModel<Complain> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	ComplainService complainService;
	
    private Long complainId;
    private String complainTitle;
    private City complainCity;
    private Date complainDateFrom;
    private Date complainDateTo;
    private Status complainStatus;
	
	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		
		return complainService.getAllComplainsCount();
	}

	@Override
	public List<Complain> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		
		Page<Complain> allComplainsPage;
		if( first == 0) {
			
			allComplainsPage = complainService.searchComplains(complainId, complainTitle, complainCity, complainStatus, complainDateFrom, complainDateTo, first, pageSize);
		}else {
			
			allComplainsPage = complainService.searchComplains(complainId, complainTitle, complainCity, complainStatus, complainDateFrom, complainDateTo, first/pageSize, pageSize);
		}
		this.setRowCount((int) allComplainsPage.getTotalElements());
		return allComplainsPage.getContent();
	}

}
