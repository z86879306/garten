package com.garten.model.garten;

public class PhotoDianZan {
	
	private Integer infoId;
	private String dianzanName;
	private String job;
	private Integer jobId;
	@Override
	public String toString() {
		return "PhotoDianZan [infoId=" + infoId + ", dianzanName=" + dianzanName + ", job=" + job + ", jobId=" + jobId
				+ "]";
	}
	public PhotoDianZan() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PhotoDianZan(Integer infoId, String dianzanName, String job, Integer jobId) {
		super();
		this.infoId = infoId;
		this.dianzanName = dianzanName;
		this.job = job;
		this.jobId = jobId;
	}
	public Integer getInfoId() {
		return infoId;
	}
	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}
	public String getDianzanName() {
		return dianzanName;
	}
	public void setDianzanName(String dianzanName) {
		this.dianzanName = dianzanName;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	

}
