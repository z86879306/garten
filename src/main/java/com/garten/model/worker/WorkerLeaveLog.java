package com.garten.model.worker;

import java.sql.Timestamp;

public class WorkerLeaveLog {
	
	private Integer workerId;
	private Long leaveTime;
	private Long applicationTime;
	private String leaveReason;
	private Integer leaveState;
	@Override
	public String toString() {
		return "WorkerLeaveLog [workerId=" + workerId + ", leaveTime=" + leaveTime + ", applicationTime="
				+ applicationTime + ", leaveReason=" + leaveReason + ", leaveState=" + leaveState + "]";
	}
	public WorkerLeaveLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WorkerLeaveLog(Integer workerId, Long leaveTime, Long applicationTime, String leaveReason,
			Integer leaveState) {
		super();
		this.workerId = workerId;
		this.leaveTime = leaveTime;
		this.applicationTime = applicationTime;
		this.leaveReason = leaveReason;
		this.leaveState = leaveState;
	}
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public Long getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Timestamp leaveTime) {
		this.leaveTime = leaveTime.getTime();
	}
	public Long getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(Timestamp applicationTime) {
		this.applicationTime = applicationTime.getTime();
	}
	public String getLeaveReason() {
		return leaveReason;
	}
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}
	public Integer getLeaveState() {
		return leaveState;
	}
	public void setLeaveState(Integer leaveState) {
		this.leaveState = leaveState;
	}
	
	
}
