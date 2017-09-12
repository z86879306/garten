package com.garten.model.garten;

import java.sql.Timestamp;

public class GartenLesson {
	
	private Integer gartenId;
	private Long time;
	private Integer ampm;
	private String lessonName;
	private Integer classId;
	private String startTime;
	private String endTime;
	private Integer lessonId;
	@Override
	public String toString() {
		return "GartenLesson [gartenId=" + gartenId + ", time=" + time + ", ampm=" + ampm + ", lessonName=" + lessonName
				+ ", classId=" + classId + ", startTime=" + startTime + ", endTime=" + endTime + ", lessonId="
				+ lessonId + "]";
	}
	public GartenLesson() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenLesson(Integer gartenId, Long time, Integer ampm, String lessonName, Integer classId,
			String startTime, String endTime, Integer lessonId) {
		super();
		this.gartenId = gartenId;
		this.time = time;
		this.ampm = ampm;
		this.lessonName = lessonName;
		this.classId = classId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.lessonId = lessonId;
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
	
	
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getLessonId() {
		return lessonId;
	}
	public void setLessonId(Integer lessonId) {
		this.lessonId = lessonId;
	}

}
