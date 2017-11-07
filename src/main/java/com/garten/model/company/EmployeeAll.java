package com.garten.model.company;

public class EmployeeAll extends Employee{

	private Department department;
	private Jobs jobs;
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Jobs getJobs() {
		return jobs;
	}
	public void setJobs(Jobs jobs) {
		this.jobs = jobs;
	}
	public EmployeeAll(Department department, Jobs jobs) {
		super();
		this.department = department;
		this.jobs = jobs;
	}
	public EmployeeAll() {
		super();
	}
	public EmployeeAll(Integer employeeNo, String name, Long departmentNo, Long jobsNo, String permission,
			String province, String city, String countries, String phoneNumber, String pwd, Long registTime,
			String token, Long entryTime, Integer sex, Integer lastEmployeeNo) {
		super(employeeNo, name, departmentNo, jobsNo, permission, province, city, countries, phoneNumber, pwd, registTime,
				token, entryTime, sex, lastEmployeeNo);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
}
