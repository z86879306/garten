package com.garten.model.other;

import java.math.BigDecimal;
import java.util.List;

public class EquipmentName {
	
	private String equipmentName;
	private BigDecimal price;
	private Integer equipmentId;
	private String remark;
	private String imgUrl;
	@Override
	public String toString() {
		return "EquipmentName [equipmentName=" + equipmentName + ", price=" + price + ", equipmentId=" + equipmentId
				+ ", remark=" + remark + ", imgUrl=" + imgUrl + "]";
	}
	public EquipmentName() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EquipmentName(String equipmentName, BigDecimal price, Integer equipmentId, String remark, String imgUrl) {
		super();
		this.equipmentName = equipmentName;
		this.price = price;
		this.equipmentId = equipmentId;
		this.remark = remark;
		this.imgUrl = imgUrl;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}
