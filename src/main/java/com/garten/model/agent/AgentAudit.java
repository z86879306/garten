package com.garten.model.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AgentAudit {
	
	private Integer resource;
	private String contractNumber;
	private String province;
	private String city;
	private String countries;
	private Integer count;
	private String equipment;
	private BigDecimal money1;
	private BigDecimal money2;
	private BigDecimal money3;
	private BigDecimal money4;
	private Integer auditId;
	private Integer resourceId;
	private Long registTime;
	private Integer state;
	private String gartenName;
	private String name;
	private String phoneNumber;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public AgentAudit(Integer resource, String contractNumber, String province, String city, String countries,
			Integer count, String equipment, BigDecimal money1, BigDecimal money2, BigDecimal money3, BigDecimal money4,
			Integer auditId, Integer resourceId, Long registTime, Integer state, String gartenName, String name,
			String phoneNumber) {
		super();
		this.resource = resource;
		this.contractNumber = contractNumber;
		this.province = province;
		this.city = city;
		this.countries = countries;
		this.count = count;
		this.equipment = equipment;
		this.money1 = money1;
		this.money2 = money2;
		this.money3 = money3;
		this.money4 = money4;
		this.auditId = auditId;
		this.resourceId = resourceId;
		this.registTime = registTime;
		this.state = state;
		this.gartenName = gartenName;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	public String getGartenName() {
		return gartenName;
	}
	public void setGartenName(String gartenName) {
		this.gartenName = gartenName;
	}
	public AgentAudit(Integer resource, String contractNumber, String province, String city, String countries,
			Integer count, String equipment, BigDecimal money1, BigDecimal money2, BigDecimal money3, BigDecimal money4,
			Integer auditId, Integer resourceId, Long registTime, Integer state, String gartenName) {
		super();
		this.resource = resource;
		this.contractNumber = contractNumber;
		this.province = province;
		this.city = city;
		this.countries = countries;
		this.count = count;
		this.equipment = equipment;
		this.money1 = money1;
		this.money2 = money2;
		this.money3 = money3;
		this.money4 = money4;
		this.auditId = auditId;
		this.resourceId = resourceId;
		this.registTime = registTime;
		this.state = state;
		this.gartenName = gartenName;
	}
	@Override
	public String toString() {
		return "AgentAudit [resource=" + resource + ", contractNumber=" + contractNumber + ", province=" + province
				+ ", city=" + city + ", countries=" + countries + ", count=" + count + ", equipment=" + equipment
				+ ", money1=" + money1 + ", money2=" + money2 + ", money3=" + money3 + ", money4=" + money4
				+ ", auditId=" + auditId + ", resourceId=" + resourceId + ", registTime=" + registTime + ", state="
				+ state + "]";
	}
	public AgentAudit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AgentAudit(Integer resource, String contractNumber, String province, String city, String countries,
			Integer count, String equipment, BigDecimal money1, BigDecimal money2, BigDecimal money3, BigDecimal money4,
			Integer auditId, Integer resourceId, Long registTime, Integer state) {
		super();
		this.resource = resource;
		this.contractNumber = contractNumber;
		this.province = province;
		this.city = city;
		this.countries = countries;
		this.count = count;
		this.equipment = equipment;
		this.money1 = money1;
		this.money2 = money2;
		this.money3 = money3;
		this.money4 = money4;
		this.auditId = auditId;
		this.resourceId = resourceId;
		this.registTime = registTime;
		this.state = state;
	}
	public Integer getResource() {
		return resource;
	}
	public void setResource(Integer resource) {
		this.resource = resource;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public BigDecimal getMoney1() {
		return money1;
	}
	public void setMoney1(BigDecimal money1) {
		this.money1 = money1;
	}
	public BigDecimal getMoney2() {
		return money2;
	}
	public void setMoney2(BigDecimal money2) {
		this.money2 = money2;
	}
	public BigDecimal getMoney3() {
		return money3;
	}
	public void setMoney3(BigDecimal money3) {
		this.money3 = money3;
	}
	public BigDecimal getMoney4() {
		return money4;
	}
	public void setMoney4(BigDecimal money4) {
		this.money4 = money4;
	}
	public Integer getAuditId() {
		return auditId;
	}
	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	

}
