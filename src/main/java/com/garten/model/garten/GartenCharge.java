package com.garten.model.garten;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GartenCharge {
	
	private Integer type;
	private String province;
	private String city;
	private String countries;
	private Integer gartenId;
	private BigDecimal month1;
	private BigDecimal month3;
	private BigDecimal month6;
	private BigDecimal month12;
	private Integer chargeId;
	private String gartenName;
	
	public String getGartenName() {
		return gartenName;
	}
	public void setGartenName(String gartenName) {
		this.gartenName = gartenName;
	}
	public GartenCharge(Integer type, String province, String city, String countries, Integer gartenId,
			BigDecimal month1, BigDecimal month3, BigDecimal month6, BigDecimal month12, Integer chargeId,
			String gartenName) {
		super();
		this.type = type;
		this.province = province;
		this.city = city;
		this.countries = countries;
		this.gartenId = gartenId;
		this.month1 = month1;
		this.month3 = month3;
		this.month6 = month6;
		this.month12 = month12;
		this.chargeId = chargeId;
		this.gartenName = gartenName;
	}
	@Override
	public String toString() {
		return "GartenCharge [type=" + type + ", province=" + province + ", city=" + city + ", countries=" + countries
				+ ", gartenId=" + gartenId + ", month1=" + month1 + ", month3=" + month3 + ", month6=" + month6
				+ ", month12=" + month12 + ", chargeId=" + chargeId + "]";
	}
	public GartenCharge() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenCharge(Integer type, String province, String city, String countries, Integer gartenId,
			BigDecimal month1, BigDecimal month3, BigDecimal month6, BigDecimal month12, Integer chargeId) {
		super();
		this.type = type;
		this.province = province;
		this.city = city;
		this.countries = countries;
		this.gartenId = gartenId;
		this.month1 = month1;
		this.month3 = month3;
		this.month6 = month6;
		this.month12 = month12;
		this.chargeId = chargeId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public BigDecimal getMonth1() {
		return month1;
	}
	public void setMonth1(BigDecimal month1) {
		this.month1 = month1;
	}
	public BigDecimal getMonth3() {
		return month3;
	}
	public void setMonth3(BigDecimal month3) {
		this.month3 = month3;
	}
	public BigDecimal getMonth6() {
		return month6;
	}
	public void setMonth6(BigDecimal month6) {
		this.month6 = month6;
	}
	public BigDecimal getMonth12() {
		return month12;
	}
	public void setMonth12(BigDecimal month12) {
		this.month12 = month12;
	}
	public Integer getChargeId() {
		return chargeId;
	}
	public void setChargeId(Integer chargeId) {
		this.chargeId = chargeId;
	}
	
	
}
