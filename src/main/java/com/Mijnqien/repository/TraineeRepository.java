package com.Mijnqien.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Mijnqien.Trainee.Trainee;

@Repository
public interface TraineeRepository extends CrudRepository <Trainee, Long> {

}
