package com.garten.vo.bigcontrol;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.garten.model.gartenClass.GartenClass;


public class GartenClassAll {//改幼儿园每个年级下的所有班级
	
	private String leadGrade;
	private List<GartenClass> classes;
	@Override
	public String toString() {
		return "GartenClassAll [leadGrade=" + leadGrade + ", classes=" + classes + "]";
	}
	public GartenClassAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GartenClassAll(String leadGrade, List<GartenClass> classes) {
		super();
		this.leadGrade = leadGrade;
		this.classes = classes;
	}
	public String getLeadGrade() {
		return leadGrade;
	}
	public void setLeadGrade(String leadGrade) {
		this.leadGrade = leadGrade;
	}
	public List<GartenClass> getClasses() {
		return classes;
	}
	public void setClasses(List<GartenClass> classes) {
		this.classes = classes;
	}
	
}
