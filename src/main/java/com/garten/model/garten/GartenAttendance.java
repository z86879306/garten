package com.garten.model.garten;

public class GartenAttendance {

	private Integer id;
	private Integer gartenId;
	private String time1;
	private String time2;
	private String time3;
	private String time4;
	private String time5;
	private String time6;
	private Integer teacherAttendanceFlag;
	private Integer studentAttendanceFlag;  
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public String getTime1() {
		return time1;
	}
	public void setTime1(String time1) {
		this.time1 = time1;
	}
	public String getTime2() {
		return time2;
	}
	public void setTime2(String time2) {
		this.time2 = time2;
	}
	public String getTime3() {
		return time3;
	}
	public void setTime3(String time3) {
		this.time3 = time3;
	}
	public String getTime4() {
		return time4;
	}
	public void setTime4(String time4) {
		this.time4 = time4;
	}
	public String getTime5() {
		return time5;
	}
	public void setTime5(String time5) {
		this.time5 = time5;
	}
	public String getTime6() {
		return time6;
	}
	public void setTime6(String time6) {
		this.time6 = time6;
	}
	
	public Integer getTeacherAttendanceFlag() {
		return teacherAttendanceFlag;
	}
	public void setTeacherAttendanceFlag(Integer teacherAttendanceFlag) {
		this.teacherAttendanceFlag = teacherAttendanceFlag;
	}
	public Integer getStudentAttendanceFlag() {
		return studentAttendanceFlag;
	}
	public void setStudentAttendanceFlag(Integer studentAttendanceFlag) {
		this.studentAttendanceFlag = studentAttendanceFlag;
	}
	
	public GartenAttendance(Integer id, Integer gartenId, String time1, String time2, String time3, String time4,
			String time5, String time6, Integer teacherAttendanceFlag, Integer studentAttendanceFlag) {
		super();
		this.id = id;
		this.gartenId = gartenId;
		this.time1 = time1;
		this.time2 = time2;
		this.time3 = time3;
		this.time4 = time4;
		this.time5 = time5;
		this.time6 = time6;
		this.teacherAttendanceFlag = teacherAttendanceFlag;
		this.studentAttendanceFlag = studentAttendanceFlag;
	}
	public GartenAttendance() {
		super();
	}
	
	
	
}
