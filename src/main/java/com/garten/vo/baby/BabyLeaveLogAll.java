package com.garten.vo.baby;

import java.sql.Timestamp;

public class BabyLeaveLogAll {
	
	private Integer babyId;
	private Long leaveTime;
	private Long time;
	private String reason;
	private Integer leaveState;
	private Integer leaveId;
	private Long endTime;
	private String babyHead;
	private String babyName;
	
	public String getBabyName() {
		return babyName;
	}
	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}
	public BabyLeaveLogAll(Integer babyId, Long leaveTime, Long time, String reason, Integer leaveState, Integer leaveId,
			Long endTime, String babyHead, String babyName) {
		super();
		this.babyId = babyId;
		this.leaveTime = leaveTime;
		this.time = time;
		this.reason = reason;
		this.leaveState = leaveState;
		this.leaveId = leaveId;
		this.endTime = endTime;
		this.babyHead = babyHead;
		this.babyName = babyName;
	}
	public String getBabyHead() {
		return babyHead;
	}
	public void setBabyHead(String babyHead) {
		this.babyHead = babyHead;
	}
	public BabyLeaveLogAll(Integer babyId, Long leaveTime, Long time, String reason, Integer leaveState, Integer leaveId,
			Long endTime, String babyHead) {
		super();
		this.babyId = babyId;
		this.leaveTime = leaveTime;
		this.time = time;
		this.reason = reason;
		this.leaveState = leaveState;
		this.leaveId = leaveId;
		this.endTime = endTime;
		this.babyHead = babyHead;
	}
	@Override
	public String toString() {
		return "BabyLeaveLog [babyId=" + babyId + ", leaveTime=" + leaveTime + ", time=" + time + ", reason=" + reason
				+ ", leaveState=" + leaveState + ", leaveId=" + leaveId + ", endTime=" + endTime + "]";
	}
	public BabyLeaveLogAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BabyLeaveLogAll(Integer babyId, Long leaveTime, Long time, String reason, Integer leaveState, Integer leaveId,
			Long endTime) {
		super();
		this.babyId = babyId;
		this.leaveTime = leaveTime;
		this.time = time;
		this.reason = reason;
		this.leaveState = leaveState;
		this.leaveId = leaveId;
		this.endTime = endTime;
	}
	public Integer getBabyId() {
		return babyId;
	}
	public void setBabyId(Integer babyId) {
		this.babyId = babyId;
	}
	public Long getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Timestamp leaveTime) {
		this.leaveTime = leaveTime.getTime()/1000;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime()/1000;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getLeaveState() {
		return leaveState;
	}
	public void setLeaveState(Integer leaveState) {
		this.leaveState = leaveState;
	}
	public Integer getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(Integer leaveId) {
		this.leaveId = leaveId;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime.getTime()/1000;
	}

	
	

}
