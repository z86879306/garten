package com.garten.vo.garent;

import com.garten.vo.agent.GartenSimple;

public class GarentDredgeCount extends GartenSimple {

	private Integer monitorCount;
	private Integer attendanceCount;
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
	public GarentDredgeCount(String gartenName, Integer gartenId, Integer monitorCount, Integer attendanceCount) {
		super(gartenName, gartenId);
		this.monitorCount = monitorCount;
		this.attendanceCount = attendanceCount;
	}
	public GarentDredgeCount(String gartenName, Integer gartenId) {
		super(gartenName, gartenId);
	}
	
}
