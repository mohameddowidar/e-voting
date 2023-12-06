package com.electronic.voting.services;

import com.electronic.voting.entities.Status;
import com.electronic.voting.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

	@Autowired
    StatusRepository statusRepo;
	
	public List<Status> getAllStatuses(){
		return (List<Status>) statusRepo.findAll();
		
	}
	
	public Status getStatusById(long statusId) {
		Optional<Status> statusOptional = statusRepo.findById(statusId);
		if(statusOptional.isPresent())
			return statusOptional.get();
		else
			return null;
	}
}
