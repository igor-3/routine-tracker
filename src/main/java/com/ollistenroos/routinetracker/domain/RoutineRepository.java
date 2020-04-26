package com.ollistenroos.routinetracker.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RoutineRepository extends CrudRepository<Routine, Long> {
	
	List<Routine> findByName(String name);
	List<Routine> findByUserName(String userName);

}
