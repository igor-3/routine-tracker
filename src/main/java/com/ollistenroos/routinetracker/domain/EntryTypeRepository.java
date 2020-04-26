package com.ollistenroos.routinetracker.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EntryTypeRepository extends CrudRepository<EntryType, Long> {
	
	List<EntryType> findByEntryName(String entryName);
	List<EntryType> findByRoutId(Long routId);
	
}
