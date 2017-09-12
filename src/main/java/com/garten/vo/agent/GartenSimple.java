package com.garten.vo.agent;

public class GartenSimple {

	private String gartenName;
	private Integer gartenId;
	private Integer monitorCount=0;
	private Integer attendanceCount=0;
	public String getGartenName() {
		return gartenName;
	}
	public void setGartenName(String gartenName) {
		this.gartenName = gartenName;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public GartenSimple(String gartenName, Integer gartenId) {
		super();
		this.gartenName = gartenName;
		this.gartenId = gartenId;
	}
	public GartenSimple() {
		super();
	}
	public Integer getMonitorCount() {
		return monitorCount;
	}
	public void setMonitorCount(Integer monitorCount) {
		this.monitorCount = monitorCount;
	}
	public Integer getAttendanceCount() {
		return attendanceCount;
	}
	public void setAttendanceCount(Integer attendanceCount) {
		this.attendanceCount = attendanceCount;
	}
	public GartenSimple(String gartenName, Integer gartenId, Integer monitorCount, Integer attendanceCount) {
		super();
		this.gartenName = gartenName;
		this.gartenId = gartenId;
		this.monitorCount = monitorCount;
		this.attendanceCount = attendanceCount;
	}
	
	
}
