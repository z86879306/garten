package com.garten.model.company;

public class Department {

	private Long departmentNo;
	private String departmentName;
	private String mark;
	public Long getDepartmentNo() {
		return departmentNo;
	}
	public void setDepartmentNo(Long departmentNo) {
		this.departmentNo = departmentNo;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Department(Long departmentNo, String departmentName, String mark) {
		super();
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.mark = mark;
	}
	public Department() {
		super();
	}
	
	
}
