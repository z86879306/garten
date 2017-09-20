package com.garten.model.other;

import java.math.BigDecimal;
import java.util.List;

public class EquipmentName {
	
	private String equipmentName;
	private BigDecimal price;
	private Integer equipmentId;
	@Override
	public String toString() {
		return "EquipmentName [equipmentName=" + equipmentName + ", price=" + price + ", equipmentId=" + equipmentId
				+ "]";
	}
	public EquipmentName() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EquipmentName(String equipmentName, BigDecimal price, Integer equipmentId) {
		super();
		this.equipmentName = equipmentName;
		this.price = price;
		this.equipmentId = equipmentId;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	
}
