package com.garten.model.garten;

import java.sql.Timestamp;

public class GartenType {
	
	private Integer gartenType;
	private String typeName;
	private String operator;
	private Long registTime;
	private String mark;
	@Override
	public String toString() {
		return "GartenType [gartenType=" + gartenType + ", typeName=" + typeName + ", operator=" + operator
				+ ", registTime=" + registTime + ", mark=" + mark + "]";
	}
	public GartenType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenType(Integer gartenType, String typeName, String operator, Long registTime, String mark) {
		super();
		this.gartenType = gartenType;
		this.typeName = typeName;
		this.operator = operator;
		this.registTime = registTime;
		this.mark = mark;
	}
	public Integer getGartenType() {
		return gartenType;
	}
	public void setGartenType(Integer gartenType) {
		this.gartenType = gartenType;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	

}
