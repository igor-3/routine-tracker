package com.ollistenroos.routinetracker.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Entry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long entryid;
	private int time;					// Time in minutes
	private String date;				// Date of the entry in YYYY-MM-DD format
	private int balanceValue;
	private int balancePeriod;			// Ordinal number of the balance period this entry is a part of.
	private String addinfo;				// Any additional info related to this Entry. Optional.
	private Long routId;
	
	@ManyToOne
	@JoinColumn(name = "routineid")
	private Routine routine;
	
	@ManyToOne
	@JoinColumn(name = "entrytypeid")
	private EntryType entryType;
	
	public Entry() {}
	
	public Entry(Routine routine) {
		this.routine = routine;
		routId = routine.getRoutineid();
	}
	
	public Entry(int time, String date, int balanceValue, int balancePeriod, String addinfo, Routine routine, EntryType entryType) {
		this.time = time;
		this.date = date;
		this.balanceValue = balanceValue;
		this.balancePeriod = balancePeriod;
		this.addinfo = addinfo;
		this.routine = routine;
		this.entryType = entryType;
		routId = routine.getRoutineid();
	}

	public Long getEntryid() {
		return entryid;
	}

	public void setEntryid(Long entryid) {
		this.entryid = entryid;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getBalanceValue() {
		return balanceValue;
	}

	public void setBalanceValue(int balanceValue) {
		this.balanceValue = balanceValue;
	}

	public int getBalancePeriod() {
		return balancePeriod;
	}

	public void setBalancePeriod(int balancePeriod) {
		this.balancePeriod = balancePeriod;
	}

	public String getAddinfo() {
		return addinfo;
	}

	public void setAddinfo(String addinfo) {
		this.addinfo = addinfo;
	}

	public Routine getRoutine() {
		return routine;
	}

	public void setRoutine(Routine routine) {
		this.routine = routine;
	}

	public EntryType getEntryType() {
		return entryType;
	}

	public void setEntryType(EntryType entryType) {
		this.entryType = entryType;
	}

	public Long getRoutId() {
		return routId;
	}

	public void setRoutId(Long routId) {
		this.routId = routId;
	}
	
	

}
