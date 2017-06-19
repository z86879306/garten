package com.garten.model.garten;

import java.sql.Timestamp;

public class GartenPhotos {
	
	private Integer gartenId;
	private String job;
	private Integer id;
	private Long uploadTime;
	private String imgs;
	private String content;
	private Integer babyId;
	private Integer state;
	private Integer infoId;
	@Override
	public String toString() {
		return "GartenPhotos [gartenId=" + gartenId + ", job=" + job + ", id=" + id + ", uploadTime=" + uploadTime
				+ ", imgs=" + imgs + ", content=" + content + ", babyId=" + babyId + ", state=" + state + ", infoId="
				+ infoId + "]";
	}
	public GartenPhotos() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenPhotos(Integer gartenId, String job, Integer id, Long uploadTime, String imgs, String content,
			Integer babyId, Integer state, Integer infoId) {
		super();
		this.gartenId = gartenId;
		this.job = job;
		this.id = id;
		this.uploadTime = uploadTime;
		this.imgs = imgs;
		this.content = content;
		this.babyId = babyId;
		this.state = state;
		this.infoId = infoId;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
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
	public Long getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime.getTime();
	}
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getBabyId() {
		return babyId;
	}
	public void setBabyId(Integer babyId) {
		this.babyId = babyId;
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
	
}
