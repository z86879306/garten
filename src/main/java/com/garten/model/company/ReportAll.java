package com.garten.model.company;

public class ReportAll extends Report{

	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ReportAll(String name) {
		super();
		this.name = name;
	}

	public ReportAll() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReportAll(Long startTime, Long endTime, String workContent, String workSummary, String harmonizeContent,
			String plan, Integer employeeNo, Integer reportId, Long registTime, Integer type) {
		super(startTime, endTime, workContent, workSummary, harmonizeContent, plan, employeeNo, reportId, registTime, type);
		// TODO Auto-generated constructor stub
	}
	
	
}
