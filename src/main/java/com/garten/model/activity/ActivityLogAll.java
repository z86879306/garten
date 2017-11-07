package com.garten.model.activity;

import java.sql.Timestamp;

import com.garten.model.baby.BabyInfo;

public class ActivityLogAll extends ActivityLog{
	
	private String babyName;

	@Override
	public String toString() {
		return "ActivityLogAll [babyName=" + babyName + "]";
	}

	public ActivityLogAll() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActivityLogAll(Integer babyId, String parentName, String phoneNumber, Integer activityId, Integer id,
			Long registTime) {
		super(babyId, parentName, phoneNumber, activityId, id, registTime);
		// TODO Auto-generated constructor stub
	}

	public ActivityLogAll(String babyName) {
		super();
		this.babyName = babyName;
	}

	public String getBabyName() {
		return babyName;
	}

	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}
	
	
	
}
