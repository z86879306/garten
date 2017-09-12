package com.garten.vo.teacher;

import java.sql.Timestamp;

import com.garten.model.worker.WorkerLeaveLog;

public class WorkerLeaveLogPrin extends  WorkerLeaveLog{
	
	private String  headImg;
	private String  workerName;
	@Override
	public String toString() {
		return "WorkerLeaveLogPrin [headImg=" + headImg + ", workerName=" + workerName + "]";
	}
	public WorkerLeaveLogPrin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WorkerLeaveLogPrin(Integer workerId, Long leaveTime, Long applicationTime, String leaveReason,
			Integer leaveState, Integer wleaveId, Long endTime) {
		super(workerId, leaveTime, applicationTime, leaveReason, leaveState, wleaveId, endTime);
		// TODO Auto-generated constructor stub
	}
	public WorkerLeaveLogPrin(String headImg, String workerName) {
		super();
		this.headImg = headImg;
		this.workerName = workerName;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	
}
