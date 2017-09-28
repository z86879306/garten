package com.garten.vo.bigcontrol;

import java.sql.Timestamp;

import com.garten.model.worker.WorkerInfo;
import com.garten.vo.teacher.WorkerInfoShort;

public class WorkerMessage   extends WorkerInfo{
	
	
	private String leadClass ;
	private String leadGrade ;
	private String education;
	private String province;
	private String city;
	private String countries;
	private String gartenName;
	
	@Override
	public String toString() {
		return "WorkerMessage [leadClass=" + leadClass + ", leadGrade=" + leadGrade + ", education=" + education
				+ ", province=" + province + ", city=" + city + ", countries=" + countries + ", gartenName="
				+ gartenName + "]";
	}
	public WorkerMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*public WorkerMessage(Integer gartenId, Integer workerId, String pwd, String workerName, String phoneNumber,
			Integer sex, Integer age, Integer classId, String education, String certificate, String chinese, String job,
			Integer flowers, String token, Long tokenTime, String headImg, Long registTime, String jobcall) {
		super(gartenId, workerId, pwd, workerName, phoneNumber, sex, age, classId, education, certificate, chinese, job,
				flowers, token, tokenTime, headImg, registTime, jobcall);
		// TODO Auto-generated constructor stub
	}*/
	
	
	public WorkerMessage(Integer gartenId, Integer workerId, String pwd, String workerName, String phoneNumber,
			Integer sex, Integer age, Integer classId, String education, String certificate, String chinese, String job,
			Integer flowers, String token, Long tokenTime, String headImg, Long registTime, String jobcall,
			String permission) {
		super(gartenId, workerId, pwd, workerName, phoneNumber, sex, age, classId, education, certificate, chinese, job,
				flowers, token, tokenTime, headImg, registTime, jobcall, permission);
		// TODO Auto-generated constructor stub
	}
	/*public WorkerMessage(Integer gartenId, Integer workerId, String pwd, String workerName, String phoneNumber,
			Integer sex, Integer age, Integer classId, String education, String certificate, String chinese, String job,
			Integer flowers, String token, Long tokenTime, String headImg, Long registTime, String jobcall,
			String leadClass, String leadGrade, String education2, String province, String city, String countries,
			String gartenName) {
		super(gartenId, workerId, pwd, workerName, phoneNumber, sex, age, classId, education, certificate, chinese, job,
				flowers, token, tokenTime, headImg, registTime, jobcall);
		this.leadClass = leadClass;
		this.leadGrade = leadGrade;
		education = education2;
		this.province = province;
		this.city = city;
		this.countries = countries;
		this.gartenName = gartenName;
	}*/
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountries() {
		return countries;
	}
	public void setCountries(String countries) {
		this.countries = countries;
	}
	public String getGartenName() {
		return gartenName;
	}
	public void setGartenName(String gartenName) {
		this.gartenName = gartenName;
	}
	
	
}
