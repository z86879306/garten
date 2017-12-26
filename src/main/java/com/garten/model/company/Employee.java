package com.garten.model.company;

import java.sql.Timestamp;

public class Employee {
	
	private Integer employeeNo;
	private String name;
	private Long departmentNo;
	private Long jobsNo;
	private String permission;
	private String province;
	private String city;
	private String countries;
	private String phoneNumber;
	private String pwd;
	private Long registTime;
	private String token;
	private Long entryTime;
	private Integer sex;
	private Integer lastEmployeeNo;
	@Override
	public String toString() {
		return "Employee [employeeNo=" + employeeNo + ", name=" + name + ", departmentNo=" + departmentNo + ", jobsNo="
				+ jobsNo + ", permission=" + permission + ", province=" + province + ", city=" + city + ", countries="
				+ countries + ", phoneNumber=" + phoneNumber + ", pwd=" + pwd + ", registTime=" + registTime
				+ ", token=" + token + ", entryTime=" + entryTime + ", sex=" + sex + ", lastEmployeeNo="
				+ lastEmployeeNo + "]";
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Employee(Integer employeeNo, String name, Long departmentNo, Long jobsNo, String permission, String province,
			String city, String countries, String phoneNumber, String pwd, Long registTime, String token,
			Long entryTime, Integer sex, Integer lastEmployeeNo) {
		super();
		this.employeeNo = employeeNo;
		this.name = name;
		this.departmentNo = departmentNo;
		this.jobsNo = jobsNo;
		this.permission = permission;
		this.province = province;
		this.city = city;
		this.countries = countries;
		this.phoneNumber = phoneNumber;
		this.pwd = pwd;
		this.registTime = registTime;
		this.token = token;
		this.entryTime = entryTime;
		this.sex = sex;
		this.lastEmployeeNo = lastEmployeeNo;
	}
	public Integer getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(Integer employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getDepartmentNo() {
		return departmentNo;
	}
	public void setDepartmentNo(Long departmentNo) {
		this.departmentNo = departmentNo;
	}
	public Long getJobsNo() {
		return jobsNo;
	}
	public void setJobsNo(Long jobsNo) {
		this.jobsNo = jobsNo;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime.getTime()/1000;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Timestamp entryTime) {
		this.entryTime = entryTime.getTime()/1000;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getLastEmployeeNo() {
		return lastEmployeeNo;
	}
	public void setLastEmployeeNo(Integer lastEmployeeNo) {
		this.lastEmployeeNo = lastEmployeeNo;
	}
	
}
