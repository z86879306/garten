package com.garten.vo.smallcontrol;

import com.garten.model.garten.GartenLesson;

public class GartenLessonDetail extends GartenLesson{

	private String leadClass;
	private String leadGrade;
	public String getLeadClass() {
		return leadClass;
	}
	public void setLeadClass(String leadClass) {
		this.leadClass = leadClass;
	}
	public String getLeadGrade() {
		return leadGrade;
	}
	public void setLeadGrade(String leadGrade) {
		this.leadGrade = leadGrade;
	}
	public GartenLessonDetail(String leadClass, String leadGrade) {
		super();
		this.leadClass = leadClass;
		this.leadGrade = leadGrade;
	}
	public GartenLessonDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenLessonDetail(Integer gartenId, Long time, Integer ampm, String lessonName, Integer classId,
			String startTime, String endTime, Integer lessonId) {
		super(gartenId, time, ampm, lessonName, classId, startTime, endTime, lessonId);
		// TODO Auto-generated constructor stub
	}
	
	
}
