package com.garten.model.garten;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GartenInfo {
	
	private Integer gartenId;
	private String phoneNumber;
	private Long registTime;
	private Integer gartenGrade;
	private String pwd;
	private Integer accountState;
	private String gartenName;
	private Integer applicationState;
	private String address;
	private Long attendanceTime;
	private Long monitorTime;
	private BigDecimal charge;
	private Integer agent;
	private String token;
	private String  introduceHtml;
	private String  introduceImgs;
	private String  province;
	private String  city;
	private String  countries;
	private String  contractNumber;//合同号
	private Long  contractStart;
	private Long  contractEnd;
	private String  name;//签约人
	private Integer agentType;	//0 员工  1代理商
	private Integer gartenType;	//幼儿园类型
	
	
	public Integer getGartenType() {
		return gartenType;
	}
	public void setGartenType(Integer gartenType) {
		this.gartenType = gartenType;
	}
	public Integer getAgentType() {
		return agentType;
	}
	public void setAgentType(Integer agentType) {
		this.agentType = agentType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "GartenInfo [gartenId=" + gartenId + ", phoneNumber=" + phoneNumber + ", registTime=" + registTime
				+ ", gartenGrade=" + gartenGrade + ", pwd=" + pwd + ", accountState=" + accountState + ", gartenName="
				+ gartenName + ", applicationState=" + applicationState + ", address=" + address + ", attendanceTime="
				+ attendanceTime + ", monitorTime=" + monitorTime + ", charge=" + charge + ", agent=" + agent
				+ ", token=" + token + ", introduceHtml=" + introduceHtml + ", introduceImgs=" + introduceImgs
				+ ", province=" + province + ", city=" + city + ", countries=" + countries + ", contractNumber="
				+ contractNumber + ", contractStart=" + contractStart + ", contractEnd=" + contractEnd + ", name="
				+ name + ", agentType=" + agentType + ", gartenType=" + gartenType + "]";
	}
	public GartenInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public GartenInfo(Integer gartenId, String phoneNumber, Long registTime, Integer gartenGrade, String pwd,
			Integer accountState, String gartenName, Integer applicationState, String address, Long attendanceTime,
			Long monitorTime, BigDecimal charge, Integer agent, String token, String introduceHtml,
			String introduceImgs, String province, String city, String countries, String contractNumber,
			Long contractStart, Long contractEnd, String name, Integer agentType, Integer gartenType) {
		super();
		this.gartenId = gartenId;
		this.phoneNumber = phoneNumber;
		this.registTime = registTime;
		this.gartenGrade = gartenGrade;
		this.pwd = pwd;
		this.accountState = accountState;
		this.gartenName = gartenName;
		this.applicationState = applicationState;
		this.address = address;
		this.attendanceTime = attendanceTime;
		this.monitorTime = monitorTime;
		this.charge = charge;
		this.agent = agent;
		this.token = token;
		this.introduceHtml = introduceHtml;
		this.introduceImgs = introduceImgs;
		this.province = province;
		this.city = city;
		this.countries = countries;
		this.contractNumber = contractNumber;
		this.contractStart = contractStart;
		this.contractEnd = contractEnd;
		this.name = name;
		this.agentType = agentType;
		this.gartenType = gartenType;
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
		this.registTime = registTime.getTime()/1000;
	}
	public Integer getGartenGrade() {
		return gartenGrade;
	}
	public void setGartenGrade(Integer gartenGrade) {
		this.gartenGrade = gartenGrade;
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
	public Long getAttendanceTime() {
		return attendanceTime;
	}
	public void setAttendanceTime(Timestamp attendanceTime) {
		this.attendanceTime = attendanceTime.getTime()/1000;
	}
	public Long getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(Timestamp monitorTime) {
		this.monitorTime = monitorTime.getTime()/1000;
	}
	public BigDecimal getCharge() {
		return charge;
	}
	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}
	public Integer getAgent() {
		return agent;
	}
	public void setAgent(Integer agent) {
		this.agent = agent;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getIntroduceHtml() {
		return introduceHtml;
	}
	public void setIntroduceHtml(String introduceHtml) {
		this.introduceHtml = introduceHtml;
	}
	public String getIntroduceImgs() {
		return introduceImgs;
	}
	public void setIntroduceImgs(String introduceImgs) {
		this.introduceImgs = introduceImgs;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountries() {
		return countries;
	}
	public void setCountries(String countries) {
		this.countries = countries;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public Long getContractStart() {
		return contractStart;
	}
	public void setContractStart(Timestamp contractStart) {
		this.contractStart = contractStart.getTime()/1000;
	}
	public Long getContractEnd() {
		return contractEnd;
	}
	public void setContractEnd(Timestamp contractEnd) {
		this.contractEnd = contractEnd.getTime()/1000;
	}

}
