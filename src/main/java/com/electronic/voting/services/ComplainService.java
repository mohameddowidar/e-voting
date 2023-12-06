package com.electronic.voting.services;

import com.electronic.voting.entities.City;
import com.electronic.voting.entities.Complain;
import com.electronic.voting.entities.Status;
import com.electronic.voting.repositories.ComplainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ComplainService {

	@Autowired
    ComplainRepository complainRepository;
	
    public Complain saveComplain(Complain complain) {
        return complainRepository.save(complain);
    }

    public void deleteComplain(long complainId) {
        complainRepository.deleteById(complainId);
    }

    public Complain updateComplain(long complainId, Complain newComplain) {
        Optional<Complain> optionalComplain = complainRepository.findById(complainId);

        if (optionalComplain.isPresent()) {
            Complain complain = optionalComplain.get();
            complain.setComplainTitle(newComplain.getComplainTitle());
            complain.setComplainSubject(newComplain.getComplainSubject());
            complain.setComplainDate(newComplain.getComplainDate());
            complain.setReportDate(newComplain.getReportDate());
            complain.setComplainCity(newComplain.getComplainCity());
            complain.setComplainAddress(newComplain.getComplainAddress());
            complain.setComplainReporterName(newComplain.getComplainReporterName());
            complain.setStatus(newComplain.getStatus());
            return complainRepository.save(complain);
        } else {
            return null;
        }
    }

    public List<Complain> getAllComplains() {
        return (List<Complain>) complainRepository.findAll();
    }
    
    
    public int getAllComplainsCount() {
    	return (int) complainRepository.count();
    }
    public List<Complain> getAllComplains(int page,int size) {
    	
    	Pageable pageable = PageRequest.of(page, size,Sort.by("complainId").descending());
        Page<Complain> complainPage = complainRepository.findAll(pageable);
        return complainPage.getContent();
    }
    
    public Page<Complain> searchComplains(Long complainId, String complainTitle, City complainCity,Status status, Date complainDateFrom, Date complainDateTo, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize,Sort.by("complainId").descending());
        return complainRepository.findAll(complainId, complainTitle, complainCity,status ,complainDateFrom, complainDateTo, pageable);
    }

    public Optional<Complain> getComplainById(long complainId) {
        return complainRepository.findById(complainId);
    }
	
	
	
	
}
