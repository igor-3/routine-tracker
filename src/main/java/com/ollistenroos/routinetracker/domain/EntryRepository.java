package com.ollistenroos.routinetracker.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EntryRepository extends CrudRepository<Entry, Long> {
	List<Entry> findByDate(@Param("date") String date);
	List<Entry> findByBalancePeriod(@Param("balancePeriod") int balancePeriod);
	List<Entry> findByRoutId(@Param("routId")Long routId);

}
