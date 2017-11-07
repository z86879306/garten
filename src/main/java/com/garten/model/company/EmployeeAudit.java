package com.garten.model.company;

import java.math.BigDecimal;

import com.garten.model.agent.AgentAudit;

public class EmployeeAudit extends AgentAudit{

	private Employee employee;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	

	public EmployeeAudit(Integer resource, String contractNumber, String province, String city, String countries,
			Integer workerCount, Integer babyCount, String equipment, BigDecimal money1, BigDecimal money2,
			BigDecimal money3, BigDecimal money4, Integer auditId, Integer resourceId, Long registTime, Integer state,
			String gartenName, String name, String phoneNumber, String remark, Integer gradeCount, Integer classCount,
			String reason, Integer gartenType, Employee employee) {
		super(resource, contractNumber, province, city, countries, workerCount, babyCount, equipment, money1, money2,
				money3, money4, auditId, resourceId, registTime, state, gartenName, name, phoneNumber, remark,
				gradeCount, classCount, reason, gartenType);
		this.employee = employee;
	}

	public EmployeeAudit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeeAudit(Integer resource, String contractNumber, String province, String city, String countries,
			Integer workerCount, Integer babyCount, String equipment, BigDecimal money1, BigDecimal money2,
			BigDecimal money3, BigDecimal money4, Integer auditId, Integer resourceId, Long registTime, Integer state,
			String gartenName, String name, String phoneNumber, String remark, Integer gradeCount, Integer classCount,
			String reason, Integer gartenType) {
		super(resource, contractNumber, province, city, countries, workerCount, babyCount, equipment, money1, money2, money3,
				money4, auditId, resourceId, registTime, state, gartenName, name, phoneNumber, remark, gradeCount, classCount,
				reason, gartenType);
		// TODO Auto-generated constructor stub
	}

	
	
	
}
