package com.garten.vo.parent;

import java.sql.Timestamp;
import java.util.Arrays;

import com.garten.model.parent.ParentInfo;

public class ParentInfoCharge extends ParentInfo{
	
	private Integer  mainBabyId;

	@Override
	public String toString() {
		return "ParentInfoCharge [mainBabyId=" + mainBabyId + "]";
	}

	public ParentInfoCharge() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParentInfoCharge(String[] babyId, String[] identity, String parentName, String phoneNumber,
			String parentHead, String address, Integer parentId, String pwd, String[] monitorTime,
			String[] attendanceTime, Integer flower, String token, Long tokenTime, Long registTime, String gartenId) {
		super(babyId, identity, parentName, phoneNumber, parentHead, address, parentId, pwd, monitorTime, attendanceTime,
				flower, token, tokenTime, registTime, gartenId);
		// TODO Auto-generated constructor stub
	}

	public ParentInfoCharge(String[] babyId, String[] identity, String parentName, String phoneNumber,
			String parentHead, String address, Integer parentId, String pwd, String[] monitorTime,
			String[] attendanceTime, Integer flower, String token, Long tokenTime, Long registTime) {
		super(babyId, identity, parentName, phoneNumber, parentHead, address, parentId, pwd, monitorTime, attendanceTime,
				flower, token, tokenTime, registTime);
		// TODO Auto-generated constructor stub
	}

	public ParentInfoCharge(String[] babyId, String[] identity, String parentName, String phoneNumber,
			String parentHead, String address, Integer parentId, String pwd, String[] monitorTime,
			String[] attendanceTime, Integer flower, String token, Long tokenTime, Long registTime, String gartenId,
			Integer mainBabyId) {
		super(babyId, identity, parentName, phoneNumber, parentHead, address, parentId, pwd, monitorTime,
				attendanceTime, flower, token, tokenTime, registTime, gartenId);
		this.mainBabyId = mainBabyId;
	}

	public Integer getMainBabyId() {
		return mainBabyId;
	}

	public void setMainBabyId(Integer mainBabyId) {
		this.mainBabyId = mainBabyId;
	}
	
	
	
}
