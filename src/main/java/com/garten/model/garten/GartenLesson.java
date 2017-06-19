package com.garten.model.garten;

import java.sql.Timestamp;

public class GartenLesson {
	
	private Integer gartenId;
	private Long time;
	private Integer ampm;
	private String lessonName;
	private Integer workerId;
	private Long startTime;
	private Long endTime;
	@Override
	public String toString() {
		return "GartenLesson [gartenId=" + gartenId + ", time=" + time + ", ampm=" + ampm + ", lessonName=" + lessonName
				+ ", workerId=" + workerId + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	public GartenLesson() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenLesson(Integer gartenId, Long time, Integer ampm, String lessonName, Integer workerId, Long startTime,
			Long endTime) {
		super();
		this.gartenId = gartenId;
		this.time = time;
		this.ampm = ampm;
		this.lessonName = lessonName;
		this.workerId = workerId;
		this.startTime = startTime;
		this.endTime = endTime;
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
	public void setTime(Long time) {
		this.time = time;
	}
	public Integer getAmpm() {
		return ampm;
	}
	public void setAmpm(Integer ampm) {
		this.ampm = ampm;
	}
	public String getLessonName() {
		return lessonName;
	}
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime.getTime();
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime.getTime();
	}
	
}
