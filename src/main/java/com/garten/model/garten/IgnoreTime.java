package com.garten.model.garten;

import java.sql.Timestamp;

public class IgnoreTime {
	
	private Integer gartenId;
	private Long time;
	@Override
	public String toString() {
		return "IgnoreTime [gartenId=" + gartenId + ", time=" + time + "]";
	}
	public IgnoreTime() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IgnoreTime(Integer gartenId, Long time) {
		super();
		this.gartenId = gartenId;
		this.time = time;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime()/1000;
	}
	
}
