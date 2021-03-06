package com.Mijnqien.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Mijnqien.domain.trainee.Profiel;
import com.Mijnqien.repository.ProfielRepository;

@Component
@Transactional

public class ProfielService {
	@Autowired
	ProfielRepository profielRepository;
	
	public Iterable<Profiel> findAlleProfielen(){
		Iterable<Profiel> resultaat = profielRepository.findAll();
		return resultaat;
	}

	public Profiel saveProfiel(Profiel profiel) {
	return profielRepository.save(profiel);

}
	
}
