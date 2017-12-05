package com.garten.vo.attendance;

public class BabyAttendanceInfo {
	
	private Integer babyId;
	private Long attendanceDate;
	private Long examDate;
	private Double examValue;
	private String cardNo;
	private String fileId;
	private Integer mode;
	public Integer getBabyId() {
		return babyId;
	}
	public void setBabyId(Integer babyId) {
		this.babyId = babyId;
	}
	public Long getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(Long attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public Long getExamDate() {
		return examDate;
	}
	public void setExamDate(Long examDate) {
		this.examDate = examDate;
	}
	public Double getExamValue() {
		return examValue;
	}
	public void setExamValue(Double examValue) {
		this.examValue = examValue;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	public BabyAttendanceInfo() {
		super();
	}
	public BabyAttendanceInfo(Integer babyId, Long attendanceDate, Long examDate, Double examValue, String cardNo,
			String fileId, Integer mode) {
		super();
		this.babyId = babyId;
		this.attendanceDate = attendanceDate;
		this.examDate = examDate;
		this.examValue = examValue;
		this.cardNo = cardNo;
		this.fileId = fileId;
		this.mode = mode;
	}
	
	
	
}
