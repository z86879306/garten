package com.garten.vo.attendance;

public class AttGartenClass {

	
	private Integer classId;
	private String gradeName;
	private String className;
	public AttGartenClass(Integer classId, String gradeName, String className) {
		super();
		this.classId = classId;
		this.gradeName = gradeName;
		this.className = className;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
}
