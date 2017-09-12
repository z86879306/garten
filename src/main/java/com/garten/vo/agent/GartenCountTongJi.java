package com.garten.vo.agent;

public class GartenCountTongJi {
	
	private String gartenId;
	private String monitorTime;
	private String attendanceTime;
	public String getGartenId() {
		return gartenId;
	}
	public void setGartenId(String gartenId) {
		this.gartenId = gartenId;
	}
	public String getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(String monitorTime) {
		this.monitorTime = monitorTime;
	}
	public String getAttendanceTime() {
		return attendanceTime;
	}
	public void setAttendanceTime(String attendanceTime) {
		this.attendanceTime = attendanceTime;
	}
	public GartenCountTongJi(String gartenId, String monitorTime, String attendanceTime) {
		super();
		this.gartenId = gartenId;
		this.monitorTime = monitorTime;
		this.attendanceTime = attendanceTime;
	}
	public GartenCountTongJi() {
		super();
	}
	
}
