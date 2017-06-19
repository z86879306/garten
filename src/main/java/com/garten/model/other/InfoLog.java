package com.garten.model.other;

import java.sql.Timestamp;

public class InfoLog {
	
	private Integer gartenId;
	private String info;
	private Long time;
	private String job;
	private Integer id;
	@Override
	public String toString() {
		return "InfoLog [gartenId=" + gartenId + ", info=" + info + ", time=" + time + ", job=" + job + ", id=" + id
				+ "]";
	}
	public InfoLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InfoLog(Integer gartenId, String info, Long time, String job, Integer id) {
		super();
		this.gartenId = gartenId;
		this.info = info;
		this.time = time;
		this.job = job;
		this.id = id;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime();
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
