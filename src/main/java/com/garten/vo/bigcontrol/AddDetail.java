package com.garten.vo.bigcontrol;

import java.sql.Timestamp;

public class AddDetail {
	
	private String gartenName;
	private String address;
	private String province;
	private String city;
	private String countries;
	private String  type;
	private String  name;
	private Long registTime;
	@Override
	public String toString() {
		return "AddDetail [gartenName=" + gartenName + ", address=" + address + ", province=" + province + ", city="
				+ city + ", countries=" + countries + ", type=" + type + ", name=" + name + ", registTime=" + registTime
				+ "]";
	}
	public AddDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AddDetail(String gartenName, String address, String province, String city, String countries, String type,
			String name, Long registTime) {
		super();
		this.gartenName = gartenName;
		this.address = address;
		this.province = province;
		this.city = city;
		this.countries = countries;
		this.type = type;
		this.name = name;
		this.registTime = registTime;
	}
	public String getGartenName() {
		return gartenName;
	}
	public void setGartenName(String gartenName) {
		this.gartenName = gartenName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	
}
