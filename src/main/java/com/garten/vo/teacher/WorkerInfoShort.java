package com.garten.vo.teacher;

import java.sql.Timestamp;

public class WorkerInfoShort {
	//去除密码
	private Integer gartenId;
	private Integer workerId;
	private String workerName;
	private String phoneNumber;
	private Integer sex;
	private Integer age;
	private Integer classId ;
	private String leadClass ;
	private String leadGrade ;
	private String education;
	private String certificate;
	private String chinese;
	private String job;
	private Integer flowers;
	private String token;
	private Long tokenTime;
	private String headImg;
	private String jobcall;
	@Override
	public String toString() {
		return "WorkerInfoShort [gartenId=" + gartenId + ", workerId=" + workerId + ", workerName=" + workerName
				+ ", phoneNumber=" + phoneNumber + ", sex=" + sex + ", age=" + age + ", classId=" + classId
				+ ", leadClass=" + leadClass + ", leadGrade=" + leadGrade + ", education=" + education
				+ ", certificate=" + certificate + ", chinese=" + chinese + ", job=" + job + ", flowers=" + flowers
				+ ", token=" + token + ", tokenTime=" + tokenTime + ", headImg=" + headImg + ", jobcall=" + jobcall
				+ "]";
	}
	public WorkerInfoShort() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WorkerInfoShort(Integer gartenId, Integer workerId, String workerName, String phoneNumber, Integer sex,
			Integer age, Integer classId, String leadClass, String leadGrade, String education, String certificate,
			String chinese, String job, Integer flowers, String token, Long tokenTime, String headImg, String jobcall) {
		super();
		this.gartenId = gartenId;
		this.workerId = workerId;
		this.workerName = workerName;
		this.phoneNumber = phoneNumber;
		this.sex = sex;
		this.age = age;
		this.classId = classId;
		this.leadClass = leadClass;
		this.leadGrade = leadGrade;
		this.education = education;
		this.certificate = certificate;
		this.chinese = chinese;
		this.job = job;
		this.flowers = flowers;
		this.token = token;
		this.tokenTime = tokenTime;
		this.headImg = headImg;
		this.jobcall = jobcall;
	}
	public Integer getGartenId() {
		return gartenId;
	}
	public void setGartenId(Integer gartenId) {
		this.gartenId = gartenId;
	}
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
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
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getChinese() {
		return chinese;
	}
	public void setChinese(String chinese) {
		this.chinese = chinese;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Integer getFlowers() {
		return flowers;
	}
	public void setFlowers(Integer flowers) {
		this.flowers = flowers;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getTokenTime() {
		return tokenTime;
	}
	public void setTokenTime(Timestamp tokenTime) {
		this.tokenTime = tokenTime.getTime()/1000;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getJobcall() {
		return jobcall;
	}
	public void setJobcall(String jobcall) {
		this.jobcall = jobcall;
	}
	
}
