package com.garten.model.daka;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DakalogAll extends Dakalog{
	
	private String name;
	private String head;
	@Override
	public String toString() {
		return "DakalogAll [name=" + name + ", head=" + head + "]";
	}
	public DakalogAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DakalogAll(Long attendanceTime, String imgUrl, Integer jobId, Integer gartenId, String fileId, String cardNo,
			String macId, String job, Integer dakaId, Long time) {
		super(attendanceTime, imgUrl, jobId, gartenId, fileId, cardNo, macId, job, dakaId, time);
		// TODO Auto-generated constructor stub
	}
	public DakalogAll(String name, String head) {
		super();
		this.name = name;
		this.head = head;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	
	
}
