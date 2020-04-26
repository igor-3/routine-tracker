package com.ollistenroos.routinetracker.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EntryType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long entrytypeid;
	private String entryName;
	private Long routId;
	
	@ManyToOne
	@JoinColumn(name = "routineid")
	private Routine routine;
	
	public EntryType() {}
	
	public EntryType(Routine routine) {
		this.routine = routine;
		routId = routine.getRoutineid();
	}

	public EntryType(String entryName, Routine routine) {
		this.entryName = entryName;
		this.routine = routine;
		routId = routine.getRoutineid();
	}

	public Long getEntrytypeid() {
		return entrytypeid;
	}

	public void setEntrytypeid(Long entrytypeid) {
		this.entrytypeid = entrytypeid;
	}

	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	public Routine getRoutine() {
		return routine;
	}

	public void setRoutine(Routine routine) {
		this.routine = routine;
	}

	public Long getRoutId() {
		return routId;
	}

	public void setRoutId(Long routId) {
		this.routId = routId;
	}
	
}
