package com.garten.model.agent;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class WuliaoOrder {
	
	private String equipmentAll;
	private Integer wuliaoId;
	private String address;
	private String postalcode;
	private Long registTime;
	private Integer agentId;
	private Integer state;
	private String toPhonenumber;
	private String fromPhonenumber;
	private BigDecimal totalPrice;
	private String remark;
	private Integer resource;
	
	public Integer getResource() {
		return resource;
	}
	public void setResource(Integer resource) {
		this.resource = resource;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public WuliaoOrder(String equipmentAll, Integer wuliaoId, String address, String postalcode, Long registTime,
			Integer agentId, Integer state, String toPhonenumber, String fromPhonenumber, BigDecimal totalPrice,
			String remark, Integer resource) {
		super();
		this.equipmentAll = equipmentAll;
		this.wuliaoId = wuliaoId;
		this.address = address;
		this.postalcode = postalcode;
		this.registTime = registTime;
		this.agentId = agentId;
		this.state = state;
		this.toPhonenumber = toPhonenumber;
		this.fromPhonenumber = fromPhonenumber;
		this.totalPrice = totalPrice;
		this.remark = remark;
		this.resource = resource;
	}
	@Override
	public String toString() {
		return "WuliaoOrder [equipmentAll=" + equipmentAll + ", wuliaoId=" + wuliaoId + ", address=" + address
				+ ", postalcode=" + postalcode + ", registTime=" + registTime + ", agentId=" + agentId + ", state="
				+ state + ", toPhonenumber=" + toPhonenumber + ", fromPhonenumber=" + fromPhonenumber + "]";
	}
	public WuliaoOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getEquipmentAll() {
		return equipmentAll;
	}
	public void setEquipmentAll(String equipmentAll) {
		this.equipmentAll = equipmentAll;
	}
	public Integer getWuliaoId() {
		return wuliaoId;
	}
	public void setWuliaoId(Integer wuliaoId) {
		this.wuliaoId = wuliaoId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getToPhonenumber() {
		return toPhonenumber;
	}
	public void setToPhonenumber(String toPhonenumber) {
		this.toPhonenumber = toPhonenumber;
	}
	public String getFromPhonenumber() {
		return fromPhonenumber;
	}
	public void setFromPhonenumber(String fromPhonenumber) {
		this.fromPhonenumber = fromPhonenumber;
	}
	
	
	


}
