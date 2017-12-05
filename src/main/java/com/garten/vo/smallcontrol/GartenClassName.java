package com.garten.vo.smallcontrol;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.garten.model.gartenClass.GartenClass;

//GartenClass 加上班级的所有老师名字
public class GartenClassName extends  GartenClass{
	
	private List<String> teacherName;
	private Integer babyCount;


	public List<String> getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(List<String> teacherName) {
		this.teacherName = teacherName;
	}

	public Integer getBabyCount() {
		return babyCount;
	}

	public void setBabyCount(Integer babyCount) {
		this.babyCount = babyCount;
	}

	public GartenClassName(List<String> teacherName, Integer babyCount) {
		super();
		this.teacherName = teacherName;
		this.babyCount = babyCount;
	}

	public GartenClassName() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GartenClassName(String leadClass, String leadGrade, Integer gartenId, Integer classId, String mark,
			Long registTime, Integer gradeId) {
		super(leadClass, leadGrade, gartenId, classId, mark, registTime, gradeId);
		// TODO Auto-generated constructor stub
	}





	
	
}
