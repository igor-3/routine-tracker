package com.ollistenroos.routinetracker.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Routine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long routineid;
	private String name;
	private int balanceExpectation;		// Weekly / Monthly / periodical expectation for this routine.
	private String description;			// Short explanation of the balance values.
	private String displayName;			// Display name for the typeName.
	private String userName;				// User id of the user to whom this routine belongs to.
	
	public Routine() {}
	
	public Routine(String name, int balanceExpectation, String description, String displayName, String userName) {
		this.name = name;
		this.balanceExpectation = balanceExpectation;
		this.description = description;
		this.displayName = displayName;
		this.userName = userName;
	}

	public Long getRoutineid() {
		return routineid;
	}

	public void setRoutineid(Long routineid) {
		this.routineid = routineid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBalanceExpectation() {
		return balanceExpectation;
	}

	public void setBalanceExpectation(int balanceExpectation) {
		this.balanceExpectation = balanceExpectation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	

}
