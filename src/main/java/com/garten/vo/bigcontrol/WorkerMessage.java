package com.garten.vo.bigcontrol;

import java.sql.Timestamp;
import java.util.List;

import com.garten.model.gartenClass.GartenClass;
import com.garten.model.worker.WorkerInfo;
import com.garten.vo.teacher.WorkerInfoShort;

public class WorkerMessage   extends WorkerInfo{
	
	
	private String education;
	private String province;
	private String city;
	private String countries;
	private String gartenName;
	
	@Override
	public String toString() {
		return "WorkerMessage [education=" + education + ", province=" + province + ", city=" + city + ", countries="
				+ countries + ", gartenName=" + gartenName + "]";
	}
	public WorkerMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public WorkerMessage(Integer gartenId, Integer workerId, String pwd, String workerName, String phoneNumber,
			Integer sex, Integer age, String classId, String education, String certificate, String chinese, String job,
			Integer flowers, String token, Long tokenTime, String headImg, Long registTime, String jobcall,
			String permission, List<GartenClass> gartenClasses) {
		super(gartenId, workerId, pwd, workerName, phoneNumber, sex, age, classId, education, certificate, chinese, job,
				flowers, token, tokenTime, headImg, registTime, jobcall, permission, gartenClasses);
		// TODO Auto-generated constructor stub
	}
	
	public WorkerMessage(String education, String province, String city, String countries, String gartenName) {
		super();
		this.education = education;
		this.province = province;
		this.city = city;
		this.countries = countries;
		this.gartenName = gartenName;
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
