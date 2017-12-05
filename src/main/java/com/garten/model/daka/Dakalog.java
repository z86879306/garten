package com.garten.model.daka;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Dakalog {
	
	private Long attendanceTime;
	private String imgUrl;
	private Integer jobId;
	private Integer gartenId;
	private String fileId;
	private String cardNo;
	private String macId;
	private String job;
	private Integer dakaId;
	private Long time;
	@Override
	public String toString() {
		return "GartenCharge [attendanceTime=" + attendanceTime + ", imgUrl=" + imgUrl + ", jobId=" + jobId
				+ ", gartenId=" + gartenId + ", fileId=" + fileId + ", cardNo=" + cardNo + ", macId=" + macId + ", job="
				+ job + ", dakaId=" + dakaId + ", time=" + time + "]";
	}
	public Dakalog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Dakalog(Long attendanceTime, String imgUrl, Integer jobId, Integer gartenId, String fileId,
			String cardNo, String macId, String job, Integer dakaId, Long time) {
		super();
		this.attendanceTime = attendanceTime;
		this.imgUrl = imgUrl;
		this.jobId = jobId;
		this.gartenId = gartenId;
		this.fileId = fileId;
		this.cardNo = cardNo;
		this.macId = macId;
		this.job = job;
		this.dakaId = dakaId;
		this.time = time;
	}
	public Long getAttendanceTime() {
		return attendanceTime;
	}
	public void setAttendanceTime(Timestamp attendanceTime) {
		this.attendanceTime = attendanceTime.getTime()/1000;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Integer getDakaId() {
		return dakaId;
	}
	public void setDakaId(Integer dakaId) {
		this.dakaId = dakaId;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime()/1000;
	}
	
	
	
}
