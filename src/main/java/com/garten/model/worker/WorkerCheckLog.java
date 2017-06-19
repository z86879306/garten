package com.garten.model.worker;

import java.sql.Timestamp;

public class WorkerCheckLog {
	
	private Integer gartenId;
	private Integer workerId;
	private Long arriveTime;
	private Long leaveTime;
	private Long time;
	@Override
	public String toString() {
		return "WorkerCheckLog [gartenId=" + gartenId + ", workerId=" + workerId + ", arriveTime=" + arriveTime
				+ ", leaveTime=" + leaveTime + ", time=" + time + "]";
	}
	public WorkerCheckLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WorkerCheckLog(Integer gartenId, Integer workerId, Long arriveTime, Long leaveTime, Long time) {
		super();
		this.gartenId = gartenId;
		this.workerId = workerId;
		this.arriveTime = arriveTime;
		this.leaveTime = leaveTime;
		this.time = time;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public Long getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Timestamp arriveTime) {
		this.arriveTime = arriveTime.getTime();
	}
	public Long getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Timestamp leaveTime) {
		this.leaveTime = leaveTime.getTime();
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime();
	}
	
	
}
