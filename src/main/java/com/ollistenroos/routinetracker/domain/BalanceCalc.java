package com.ollistenroos.routinetracker.domain;

import java.util.List;

public class BalanceCalc {
	
	private int balanceValues;
	private int balancePeriod;
	private int balanceExpectation;
	private int balanceResult;
	private int time;
	private String timeString;
	private Routine routine;
	
	public BalanceCalc() {}
	
	public int balance(List<Entry> entries) {
		routine = entries.get(0).getRoutine();
		balanceExpectation = routine.getBalanceExpectation();
		balanceValues = 0;
		balancePeriod = 0;
		for (Entry entry : entries) {
			balanceValues = balanceValues + entry.getBalanceValue();
			if (balancePeriod < entry.getBalancePeriod()) {
				balancePeriod = entry.getBalancePeriod();
			}
		}
		balanceResult = balanceValues - (balancePeriod * balanceExpectation);
		return balanceResult;
	}
	
	public int totalBalance(List<Entry> entries) {
		balanceValues = 0;
		for (Entry entry : entries) {
			balanceValues = balanceValues + entry.getBalanceValue();
		}
		return balanceValues;
	}
	
	public String totalTime(List<Entry> entries) {
		time = 0;
		for (Entry entry : entries) {
			time = time + entry.getTime();
		}
		if (time < 120) {
			timeString = Integer.toString(time) + " minutes";
		} else if (time < 1440){
			timeString = Integer.toString(time/60) + " hours and " + Integer.toString(time%60) + " minutes";
		} else {
			timeString = Integer.toString(time/1440) + " day(s), " + Integer.toString((time/60)%24) + " hour(s) and " + Integer.toString(time%60) + " minutes";
		}
		return timeString;
	}

}
