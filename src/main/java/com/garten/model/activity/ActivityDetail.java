package com.garten.model.activity;

import java.sql.Timestamp;

public class ActivityDetail {
	
	private Integer activityId;
	private String contentHtml;
	private Long timeStart;
	private Long timeEnd;
	private Long joinTime;
	private Integer gartenId;
	private String img;
	private String content;
	private String activityAddress;
	private String title;
	private String htmlImgs;
	
	public String getHtmlImgs() {
		return htmlImgs;
	}
	public void setHtmlImgs(String htmlImgs) {
		this.htmlImgs = htmlImgs;
	}
	public ActivityDetail(Integer activityId, String contentHtml, Long timeStart, Long timeEnd, Long joinTime,
			Integer gartenId, String img, String content, String activityAddress, String title, String htmlImgs) {
		super();
		this.activityId = activityId;
		this.contentHtml = contentHtml;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.joinTime = joinTime;
		this.gartenId = gartenId;
		this.img = img;
		this.content = content;
		this.activityAddress = activityAddress;
		this.title = title;
		this.htmlImgs = htmlImgs;
	}
	@Override
	public String toString() {
		return "ActivityDetail [activityId=" + activityId + ", contentHtml=" + contentHtml + ", timeStart=" + timeStart
				+ ", timeEnd=" + timeEnd + ", joinTime=" + joinTime + ", gartenId=" + gartenId + ", img=" + img
				+ ", content=" + content + ", activityAddress=" + activityAddress + ", title=" + title + "]";
	}
	public ActivityDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ActivityDetail(Integer activityId, String contentHtml, Long timeStart, Long timeEnd, Long joinTime,
			Integer gartenId, String img, String content, String activityAddress, String title) {
		super();
		this.activityId = activityId;
		this.contentHtml = contentHtml;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.joinTime = joinTime;
		this.gartenId = gartenId;
		this.img = img;
		this.content = content;
		this.activityAddress = activityAddress;
		this.title = title;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getContentHtml() {
		return contentHtml;
	}
	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}
	public Long getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Timestamp timeStart) {
		this.timeStart = timeStart.getTime()/1000;
	}
	public Long getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Timestamp timeEnd) {
		this.timeEnd = timeEnd.getTime()/1000;
	}
	public Long getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Timestamp joinTime) {
		this.joinTime = joinTime.getTime()/1000;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getActivityAddress() {
		return activityAddress;
	}
	public void setActivityAddress(String activityAddress) {
		this.activityAddress = activityAddress;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	

}
