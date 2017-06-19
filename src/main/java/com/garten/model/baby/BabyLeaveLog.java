package com.garten.model.baby;

import java.sql.Timestamp;

public class BabyLeaveLog {
	
	private Integer babyId;
	private Long leaveTime;
	private Long time;
	private String reason;
	private Integer leaveState;
	@Override
	public String toString() {
		return "BabyLeaveLog [babyId=" + babyId + ", leaveTime=" + leaveTime + ", time=" + time + ", reason=" + reason
				+ ", leaveState=" + leaveState + "]";
	}
	public BabyLeaveLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BabyLeaveLog(Integer babyId, Long leaveTime, Long time, String reason, Integer leaveState) {
		super();
		this.babyId = babyId;
		this.leaveTime = leaveTime;
		this.time = time;
		this.reason = reason;
		this.leaveState = leaveState;
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
	public void setLeaveTime(Long leaveTime) {
		this.leaveTime = leaveTime;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime();
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
	
	
	
	
	

}
