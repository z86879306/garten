package com.garten.model.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AgentBusinessInfo {
	
	private Integer agentId;
	private Integer gartenId;
	private String contractNumber;
	private BigDecimal monitorPrice;
	private Long monitorTime;
	private BigDecimal attendancePrice;
	private Long attendanceTime;
	@Override
	public String toString() {
		return "AgentBusinessInfo [agentId=" + agentId + ", gartenId=" + gartenId + ", contractNumber=" + contractNumber
				+ ", monitorPrice=" + monitorPrice + ", monitorTime=" + monitorTime + ", attendancePrice="
				+ attendancePrice + ", attendanceTime=" + attendanceTime + "]";
	}
	public AgentBusinessInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AgentBusinessInfo(Integer agentId, Integer gartenId, String contractNumber, BigDecimal monitorPrice,
			Long monitorTime, BigDecimal attendancePrice, Long attendanceTime) {
		super();
		this.agentId = agentId;
		this.gartenId = gartenId;
		this.contractNumber = contractNumber;
		this.monitorPrice = monitorPrice;
		this.monitorTime = monitorTime;
		this.attendancePrice = attendancePrice;
		this.attendanceTime = attendanceTime;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public BigDecimal getMonitorPrice() {
		return monitorPrice;
	}
	public void setMonitorPrice(BigDecimal monitorPrice) {
		this.monitorPrice = monitorPrice;
	}
	public Long getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(Timestamp monitorTime) {
		this.monitorTime = monitorTime.getTime();
	}
	public BigDecimal getAttendancePrice() {
		return attendancePrice;
	}
	public void setAttendancePrice(BigDecimal attendancePrice) {
		this.attendancePrice = attendancePrice;
	}
	public Long getAttendanceTime() {
		return attendanceTime;
	}
	public void setAttendanceTime(Timestamp attendanceTime) {
		this.attendanceTime = attendanceTime.getTime();
	}
	
	
	

	
}
