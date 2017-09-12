package com.garten.model.other;

import java.sql.Timestamp;

public class InfoLog {
	
	private Integer gartenId;
	private String info;
	private Long time;
	private String job;
	private Integer id;
	private Integer state;
	private Integer infoId;
	private String title;
	private Integer type;
	@Override
	public String toString() {
		return "InfoLog [gartenId=" + gartenId + ", info=" + info + ", time=" + time + ", job=" + job + ", id=" + id
				+ ", state=" + state + ", infoId=" + infoId + ", title=" + title + ", type=" + type + "]";
	}
	public InfoLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InfoLog(Integer gartenId, String info, Long time, String job, Integer id, Integer state, Integer infoId,
			String title, Integer type) {
		super();
		this.gartenId = gartenId;
		this.info = info;
		this.time = time;
		this.job = job;
		this.id = id;
		this.state = state;
		this.infoId = infoId;
		this.title = title;
		this.type = type;
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
		this.time = time.getTime()/1000;
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getInfoId() {
		return infoId;
	}
	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
