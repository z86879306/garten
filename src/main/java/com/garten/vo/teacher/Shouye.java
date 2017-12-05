package com.garten.vo.teacher;

import java.util.List;

import com.garten.model.gartenClass.GartenClass;


public class Shouye {
	
	private String gartenName;
	private String workerName;
	private String leadClass;
	private Integer count;
	private Integer flowers;
	private String leadGrade;
	private Integer sign;
	private String headImg;
	private List<GartenClass> gartenClasses;
	private String classId;
	
	
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Shouye(String gartenName, String workerName, String leadClass, Integer count, Integer flowers,
			String leadGrade, Integer sign, String headImg, List<GartenClass> gartenClasses, String classId) {
		super();
		this.gartenName = gartenName;
		this.workerName = workerName;
		this.leadClass = leadClass;
		this.count = count;
		this.flowers = flowers;
		this.leadGrade = leadGrade;
		this.sign = sign;
		this.headImg = headImg;
		this.gartenClasses = gartenClasses;
		this.classId = classId;
	}
	@Override
	public String toString() {
		return "Shouye [gartenName=" + gartenName + ", workerName=" + workerName + ", leadClass=" + leadClass
				+ ", count=" + count + ", flowers=" + flowers + ", leadGrade=" + leadGrade + ", sign=" + sign
				+ ", headImg=" + headImg + ", gartenClasses=" + gartenClasses + "]";
	}
	public Shouye() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Shouye(String gartenName, String workerName, String leadClass, Integer count, Integer flowers,
			String leadGrade, Integer sign, String headImg, List<GartenClass> gartenClasses) {
		super();
		this.gartenName = gartenName;
		this.workerName = workerName;
		this.leadClass = leadClass;
		this.count = count;
		this.flowers = flowers;
		this.leadGrade = leadGrade;
		this.sign = sign;
		this.headImg = headImg;
		this.gartenClasses = gartenClasses;
	}
	public String getGartenName() {
		return gartenName;
	}
	public void setGartenName(String gartenName) {
		this.gartenName = gartenName;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getLeadClass() {
		return leadClass;
	}
	public void setLeadClass(String leadClass) {
		this.leadClass = leadClass;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getFlowers() {
		return flowers;
	}
	public void setFlowers(Integer flowers) {
		this.flowers = flowers;
	}
	public String getLeadGrade() {
		return leadGrade;
	}
	public void setLeadGrade(String leadGrade) {
		this.leadGrade = leadGrade;
	}
	public Integer getSign() {
		return sign;
	}
	public void setSign(Integer sign) {
		this.sign = sign;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public List<GartenClass> getGartenClasses() {
		return gartenClasses;
	}
	public void setGartenClasses(List<GartenClass> gartenClasses) {
		this.gartenClasses = gartenClasses;
	}
	
	
	
}
