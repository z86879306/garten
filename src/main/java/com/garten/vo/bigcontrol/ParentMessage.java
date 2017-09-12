package com.garten.vo.bigcontrol;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.garten.model.parent.ParentInfo;
import com.garten.vo.teacher.ClassManage;

public class ParentMessage  {
	
	
	private ParentInfo parentInfo;
	private List<ClassManageBig> classManageBigs;
	@Override
	public String toString() {
		return "ParentMessage [parentInfo=" + parentInfo + ", classManageBigs=" + classManageBigs + "]";
	}
	public ParentMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ParentMessage(ParentInfo parentInfo, List<ClassManageBig> classManageBigs) {
		super();
		this.parentInfo = parentInfo;
		this.classManageBigs = classManageBigs;
	}
	public ParentInfo getParentInfo() {
		return parentInfo;
	}
	public void setParentInfo(ParentInfo parentInfo) {
		this.parentInfo = parentInfo;
	}
	public List<ClassManageBig> getClassManageBigs() {
		return classManageBigs;
	}
	public void setClassManageBigs(List<ClassManageBig> classManageBigs) {
		this.classManageBigs = classManageBigs;
	}
	
	
	
}
