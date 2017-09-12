package com.garten.vo.teacher;


public class GartenStartEnd {
	
	private String arriveStartTime;
	private String arriveEndTime;
	private String leaveStartTime;
	private String leaveEndTime;
	@Override
	public String toString() {
		return "GartenStartEnd [arriveStartTime=" + arriveStartTime + ", arriveEndTime=" + arriveEndTime
				+ ", leaveStartTime=" + leaveStartTime + ", leaveEndTime=" + leaveEndTime + "]";
	}
	public GartenStartEnd() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenStartEnd(String arriveStartTime, String arriveEndTime, String leaveStartTime, String leaveEndTime) {
		super();
		this.arriveStartTime = arriveStartTime;
		this.arriveEndTime = arriveEndTime;
		this.leaveStartTime = leaveStartTime;
		this.leaveEndTime = leaveEndTime;
	}
	public String getArriveStartTime() {
		return arriveStartTime;
	}
	public void setArriveStartTime(String arriveStartTime) {
		this.arriveStartTime = arriveStartTime;
	}
	public String getArriveEndTime() {
		return arriveEndTime;
	}
	public void setArriveEndTime(String arriveEndTime) {
		this.arriveEndTime = arriveEndTime;
	}
	public String getLeaveStartTime() {
		return leaveStartTime;
	}
	public void setLeaveStartTime(String leaveStartTime) {
		this.leaveStartTime = leaveStartTime;
	}
	public String getLeaveEndTime() {
		return leaveEndTime;
	}
	public void setLeaveEndTime(String leaveEndTime) {
		this.leaveEndTime = leaveEndTime;
	}

	
}
