package com.garten.vo.teacher;

import java.sql.Timestamp;

public class ActivityDetailAll {
	
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

	
	private Integer count;
	private Integer applyState;//0能报名 1已报名   2报名结束
	
	public String getHtmlImgs() {
		return htmlImgs;
	}
	public void setHtmlImgs(String htmlImgs) {
		this.htmlImgs = htmlImgs;
	}
	public ActivityDetailAll(Integer activityId, String contentHtml, Long timeStart, Long timeEnd, Long joinTime,
			Integer gartenId, String img, String content, String activityAddress, String title, String htmlImgs,
			Integer count, Integer applyState) {
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
		this.count = count;
		this.applyState = applyState;
	}
	@Override
	public String toString() {
		return "ActivityDetailAll [activityId=" + activityId + ", contentHtml=" + contentHtml + ", timeStart="
				+ timeStart + ", timeEnd=" + timeEnd + ", joinTime=" + joinTime + ", gartenId=" + gartenId + ", img="
				+ img + ", content=" + content + ", activityAddress=" + activityAddress + ", title=" + title
				+ ", count=" + count + ", applyState=" + applyState + "]";
	}
	public ActivityDetailAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ActivityDetailAll(Integer activityId, String contentHtml, Long timeStart, Long timeEnd, Long joinTime,
			Integer gartenId, String img, String content, String activityAddress, String title, Integer count,
			Integer applyState) {
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
		this.count = count;
		this.applyState = applyState;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getApplyState() {
		return applyState;
	}
	public void setApplyState(Integer applyState) {
		this.applyState = applyState;
	}
	
	
}
