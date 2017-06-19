package com.garten.model.worker;

import java.sql.Timestamp;

public class WorkerInfo {
	
	private Integer gartenId;
	private Integer workerId;
	private String account;
	private String pwd;
	private String workerName;
	private String phoneNumber;
	private Integer sex;
	private Integer age;
	private String leadClass;
	private String education;
	private String certificate;
	private String chinese;
	private Integer flowers;
	private String token;
	private Long tokenTime;
	@Override
	public String toString() {
		return "WorkerInfo [gartenId=" + gartenId + ", workerId=" + workerId + ", account=" + account + ", pwd=" + pwd
				+ ", workerName=" + workerName + ", phoneNumber=" + phoneNumber + ", sex=" + sex + ", age=" + age
				+ ", leadClass=" + leadClass + ", education=" + education + ", certificate=" + certificate
				+ ", chinese=" + chinese + ", flowers=" + flowers + ", token=" + token + ", tokenTime=" + tokenTime
				+ "]";
	}
	public WorkerInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WorkerInfo(Integer gartenId, Integer workerId, String account, String pwd, String workerName,
			String phoneNumber, Integer sex, Integer age, String leadClass, String education, String certificate,
			String chinese, Integer flowers, String token, Long tokenTime) {
		super();
		this.gartenId = gartenId;
		this.workerId = workerId;
		this.account = account;
		this.pwd = pwd;
		this.workerName = workerName;
		this.phoneNumber = phoneNumber;
		this.sex = sex;
		this.age = age;
		this.leadClass = leadClass;
		this.education = education;
		this.certificate = certificate;
		this.chinese = chinese;
		this.flowers = flowers;
		this.token = token;
		this.tokenTime = tokenTime;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
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
	public String getLeadClass() {
		return leadClass;
	}
	public void setLeadClass(String leadClass) {
		this.leadClass = leadClass;
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
		this.tokenTime = tokenTime.getTime();
	}
	
	
}
