package com.garten.vo.teacher;

import java.sql.Timestamp;
import java.util.List;

import com.garten.model.gartenClass.GartenClass;

public class WorkerInfoShortZimu {
	//去除密码
	private Integer gartenId;
	private Integer workerId;
	private String workerName;
	private String phoneNumber;
	private Integer sex;
	private Integer age;
	private List<GartenClass> gartenClasses;
	private String education;
	private String certificate;
	private String chinese;
	private String job;
	private Integer flowers;
	private String token;
	private Long tokenTime;
	private String headImg;
	private String zimu;
	@Override
	public String toString() {
		return "WorkerInfoShortZimu [gartenId=" + gartenId + ", workerId=" + workerId + ", workerName=" + workerName
				+ ", phoneNumber=" + phoneNumber + ", sex=" + sex + ", age=" + age + ", gartenClasses=" + gartenClasses
				+ ", education=" + education + ", certificate=" + certificate + ", chinese=" + chinese + ", job=" + job
				+ ", flowers=" + flowers + ", token=" + token + ", tokenTime=" + tokenTime + ", headImg=" + headImg
				+ ", zimu=" + zimu + "]";
	}
	public WorkerInfoShortZimu() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WorkerInfoShortZimu(Integer gartenId, Integer workerId, String workerName, String phoneNumber, Integer sex,
			Integer age, List<GartenClass> gartenClasses, String education, String certificate, String chinese,
			String job, Integer flowers, String token, Long tokenTime, String headImg, String zimu) {
		super();
		this.gartenId = gartenId;
		this.workerId = workerId;
		this.workerName = workerName;
		this.phoneNumber = phoneNumber;
		this.sex = sex;
		this.age = age;
		this.gartenClasses = gartenClasses;
		this.education = education;
		this.certificate = certificate;
		this.chinese = chinese;
		this.job = job;
		this.flowers = flowers;
		this.token = token;
		this.tokenTime = tokenTime;
		this.headImg = headImg;
		this.zimu = zimu;
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
	public List<GartenClass> getGartenClasses() {
		return gartenClasses;
	}
	public void setGartenClasses(List<GartenClass> gartenClasses) {
		this.gartenClasses = gartenClasses;
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
	public String getZimu() {
		return zimu;
	}
	public void setZimu(String zimu) {
		this.zimu = zimu;
	}
	
	
	
}
