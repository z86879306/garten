package com.garten.vo.baby;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UnusualAll {
	private Long unusualTime;
	private Integer unusualType;
	private String unusualImg;
	private Integer jobId;
	private Integer gartenId;
	private Integer state;
	private Integer fileId;
	private String cardNo;
	private Integer unusualId;
	private Long time;
	private String job;
	
	private String jobName;
	private String jobHead;
	@Override
	public String toString() {
		return "UnusualAll [unusualTime=" + unusualTime + ", unusualType=" + unusualType + ", unusualImg=" + unusualImg
				+ ", jobId=" + jobId + ", gartenId=" + gartenId + ", state=" + state + ", fileId=" + fileId
				+ ", cardNo=" + cardNo + ", unusualId=" + unusualId + ", time=" + time + ", job=" + job + ", jobName="
				+ jobName + ", jobHead=" + jobHead + "]";
	}
	public UnusualAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UnusualAll(Long unusualTime, Integer unusualType, String unusualImg, Integer jobId, Integer gartenId,
			Integer state, Integer fileId, String cardNo, Integer unusualId, Long time, String job, String jobName,
			String jobHead) {
		super();
		this.unusualTime = unusualTime;
		this.unusualType = unusualType;
		this.unusualImg = unusualImg;
		this.jobId = jobId;
		this.gartenId = gartenId;
		this.state = state;
		this.fileId = fileId;
		this.cardNo = cardNo;
		this.unusualId = unusualId;
		this.time = time;
		this.job = job;
		this.jobName = jobName;
		this.jobHead = jobHead;
	}
	public Long getUnusualTime() {
		return unusualTime;
	}
	public void setUnusualTime(Timestamp unusualTime) {
		this.unusualTime = unusualTime.getTime()/1000;
	}
	public Integer getUnusualType() {
		return unusualType;
	}
	public void setUnusualType(Integer unusualType) {
		this.unusualType = unusualType;
	}
	public String getUnusualImg() {
		return unusualImg;
	}
	public void setUnusualImg(String unusualImg) {
		this.unusualImg = unusualImg;
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Integer getUnusualId() {
		return unusualId;
	}
	public void setUnusualId(Integer unusualId) {
		this.unusualId = unusualId;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time.getTime()/1000;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobHead() {
		return jobHead;
	}
	public void setJobHead(String jobHead) {
		this.jobHead = jobHead;
	}
	
	
}
