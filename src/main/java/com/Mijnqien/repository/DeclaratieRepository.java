package com.Mijnqien.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Mijnqien.domain.trainee.Declaratie;
import com.Mijnqien.domain.trainee.Trainee;

@Repository
public interface DeclaratieRepository extends CrudRepository <Declaratie, Long> {

}

