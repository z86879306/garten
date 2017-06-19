package com.garten.model.garten;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GartenInfo {
	
	private Integer gartenId;
	private String phoneNumber;
	private Long registTime;
	private Integer gartenGrade;
	private String accountNumber;
	private String pwd;
	private Integer accountState;
	private String gartenName;
	private Integer applicationState;
	private String address;
	private Integer attendanceState;
	private Long attendanceTime;
	private Integer monitorState;
	private Long monitorTime;
	private BigDecimal charge;
	private String agent;
	private String arriveTime;
	private String leaveTime;
	private String token;
	private Long tokenTime;
	@Override
	public String toString() {
		return "GartenInfo [gartenId=" + gartenId + ", phoneNumber=" + phoneNumber + ", registTime=" + registTime
				+ ", gartenGrade=" + gartenGrade + ", accountNumber=" + accountNumber + ", pwd=" + pwd
				+ ", accountState=" + accountState + ", gartenName=" + gartenName + ", applicationState="
				+ applicationState + ", address=" + address + ", attendanceState=" + attendanceState
				+ ", attendanceTime=" + attendanceTime + ", monitorState=" + monitorState + ", monitorTime="
				+ monitorTime + ", charge=" + charge + ", agent=" + agent + ", arriveTime=" + arriveTime
				+ ", leaveTime=" + leaveTime + ", token=" + token + ", tokenTime=" + tokenTime + "]";
	}
	public GartenInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenInfo(Integer gartenId, String phoneNumber, Long registTime, Integer gartenGrade, String accountNumber,
			String pwd, Integer accountState, String gartenName, Integer applicationState, String address,
			Integer attendanceState, Long attendanceTime, Integer monitorState, Long monitorTime, BigDecimal charge,
			String agent, String arriveTime, String leaveTime, String token, Long tokenTime) {
		super();
		this.gartenId = gartenId;
		this.phoneNumber = phoneNumber;
		this.registTime = registTime;
		this.gartenGrade = gartenGrade;
		this.accountNumber = accountNumber;
		this.pwd = pwd;
		this.accountState = accountState;
		this.gartenName = gartenName;
		this.applicationState = applicationState;
		this.address = address;
		this.attendanceState = attendanceState;
		this.attendanceTime = attendanceTime;
		this.monitorState = monitorState;
		this.monitorTime = monitorTime;
		this.charge = charge;
		this.agent = agent;
		this.arriveTime = arriveTime;
		this.leaveTime = leaveTime;
		this.token = token;
		this.tokenTime = tokenTime;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime();
	}
	public Integer getGartenGrade() {
		return gartenGrade;
	}
	public void setGartenGrade(Integer gartenGrade) {
		this.gartenGrade = gartenGrade;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Integer getAccountState() {
		return accountState;
	}
	public void setAccountState(Integer accountState) {
		this.accountState = accountState;
	}
	public String getGartenName() {
		return gartenName;
	}
	public void setGartenName(String gartenName) {
		this.gartenName = gartenName;
	}
	public Integer getApplicationState() {
		return applicationState;
	}
	public void setApplicationState(Integer applicationState) {
		this.applicationState = applicationState;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getAttendanceState() {
		return attendanceState;
	}
	public void setAttendanceState(Integer attendanceState) {
		this.attendanceState = attendanceState;
	}
	public Long getAttendanceTime() {
		return attendanceTime;
	}
	public void setAttendanceTime(Timestamp attendanceTime) {
		this.attendanceTime = attendanceTime.getTime();
	}
	public Integer getMonitorState() {
		return monitorState;
	}
	public void setMonitorState(Integer monitorState) {
		this.monitorState = monitorState;
	}
	public Long getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(Timestamp monitorTime) {
		this.monitorTime = monitorTime.getTime();
	}
	public BigDecimal getCharge() {
		return charge;
	}
	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getTokenTime() {
		return tokenTime;
	}
	public void setTokenTime(Timestamp tokenTime) {
		this.tokenTime = tokenTime.getTime();
	}

	
}
