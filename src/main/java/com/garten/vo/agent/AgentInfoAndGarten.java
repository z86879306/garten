package com.garten.vo.agent;

import java.util.List;

import com.garten.model.agent.AgentInfo;
import com.garten.vo.garent.GarentDredgeCount;

public class AgentInfoAndGarten extends AgentInfo{

	private List<GarentDredgeCount> garentDredgeCount;
	private Integer gartenAttendanceCount;
	private Integer gartenMonitorCount;
	private Integer	gartenCount;
	public List<GarentDredgeCount> getGarentDredgeCount() {
		return garentDredgeCount;
	}
	public void setGarentDredgeCount(List<GarentDredgeCount> garentDredgeCount) {
		this.garentDredgeCount = garentDredgeCount;
	}
	public Integer getGartenAttendanceCount() {
		return gartenAttendanceCount;
	}
	public void setGartenAttendanceCount(Integer gartenAttendanceCount) {
		this.gartenAttendanceCount = gartenAttendanceCount;
	}
	public Integer getGartenMonitorCount() {
		return gartenMonitorCount;
	}
	public void setGartenMonitorCount(Integer gartenMonitorCount) {
		this.gartenMonitorCount = gartenMonitorCount;
	}
	public Integer getGartenCount() {
		return gartenCount;
	}
	public void setGartenCount(Integer gartenCount) {
		this.gartenCount = gartenCount;
	}
	public AgentInfoAndGarten(List<GarentDredgeCount> garentDredgeCount, Integer gartenAttendanceCount,
			Integer gartenMonitorCount, Integer gartenCount) {
		super();
		this.garentDredgeCount = garentDredgeCount;
		this.gartenAttendanceCount = gartenAttendanceCount;
		this.gartenMonitorCount = gartenMonitorCount;
		this.gartenCount = gartenCount;
	}
	public AgentInfoAndGarten() {
		super();
	}
	
}
