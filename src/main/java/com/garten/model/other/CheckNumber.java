package com.garten.model.other;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CheckNumber {
	private String phoneNumber;
	private String time;
	private String type;
	private String number;
	@Override
	public String toString() {
		return "CheckNumber [phoneNumber=" + phoneNumber + ", time=" + time + ", type=" + type + ", number=" + number
				+ "]";
	}
	public CheckNumber() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CheckNumber(String phoneNumber, String time, String type, String number) {
		super();
		this.phoneNumber = phoneNumber;
		this.time = time;
		this.type = type;
		this.number = number;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = new SimpleDateFormat("yyyyMMddHHmmss").format(time);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	

}
