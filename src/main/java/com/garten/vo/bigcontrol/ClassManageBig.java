package com.garten.vo.bigcontrol;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.garten.vo.teacher.ClassManage;

public class ClassManageBig extends ClassManage{
	
	
	private String monitorTime;
	private String attendanceTime;
	@Override
	public String toString() {
		return "ClassManageBig [monitorTime=" + monitorTime + ", attendanceTime=" + attendanceTime + "]";
	}
	public ClassManageBig() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ClassManageBig(String babyName, Integer babyId, Long birthday, BigDecimal height, String health,
			String hobby, String specialty, Integer gartenId, String teacherId, String babyHead, String allergy,
			String weight, Integer sex, Long registTime, Integer classId, String leadClass, String leadGrade) {
		super(babyName, babyId, birthday, height, health, hobby, specialty, gartenId, teacherId, babyHead, allergy, weight, sex,
				registTime, classId, leadClass, leadGrade);
		// TODO Auto-generated constructor stub
	}
	public ClassManageBig(String babyName, Integer babyId, Long birthday, BigDecimal height, String health,
			String hobby, String specialty, Integer gartenId, String teacherId, String babyHead, String allergy,
			String weight, Integer sex, Long registTime, String leadClass, String leadGrade) {
		super(babyName, babyId, birthday, height, health, hobby, specialty, gartenId, teacherId, babyHead, allergy, weight, sex,
				registTime, leadClass, leadGrade);
		// TODO Auto-generated constructor stub
	}
	public ClassManageBig(String babyName, Integer babyId, Long birthday, BigDecimal height, String health,
			String hobby, String specialty, Integer gartenId, String teacherId, String babyHead, String allergy,
			String weight, Integer sex, Long registTime, Integer classId, String leadClass, String leadGrade,
			String monitorTime, String attendanceTime) {
		super(babyName, babyId, birthday, height, health, hobby, specialty, gartenId, teacherId, babyHead, allergy,
				weight, sex, registTime, classId, leadClass, leadGrade);
		this.monitorTime = monitorTime;
		this.attendanceTime = attendanceTime;
	}
	public String getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(String monitorTime) {
		this.monitorTime = monitorTime;
	}
	public String getAttendanceTime() {
		return attendanceTime;
	}
	public void setAttendanceTime(String attendanceTime) {
		this.attendanceTime = attendanceTime;
	}
	
	
	
}
