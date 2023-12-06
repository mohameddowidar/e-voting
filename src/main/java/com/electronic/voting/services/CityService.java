package com.electronic.voting.services;

import com.electronic.voting.entities.City;
import com.electronic.voting.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
	
	@Autowired
    CityRepository cityRepo;
	

	
	public List<City> getAllCities(){
		return (List<City>) cityRepo.findAll();
		
	}
	
	public City getCityById(int cityId) {
		Optional<City> cityOptional = cityRepo.findById(cityId);
		if(cityOptional.isPresent())
			return cityOptional.get();
		else
			return null;
	}

}
