package com.garten.vo.bigcontrol;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.garten.model.baby.BabyInfo;
import com.garten.model.parent.ParentInfo;
import com.garten.vo.teacher.ClassManage;

public class BabyMessage extends BabyInfo {
	
	
	private String  leadClass;
	private String leadGrade;
	private String cardNo;
	@Override
	public String toString() {
		return "BabyMessage [leadClass=" + leadClass + ", leadGrade=" + leadGrade + ", cardNo=" + cardNo + "]";
	}
	public BabyMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BabyMessage(String babyName, Integer babyId, Long birthday, BigDecimal height, String health, String hobby,
			String specialty, Integer gartenId, String teacherId, Integer attendanceceId, String babyHead,
			String allergy, Integer parentId, String parentRelation, BigDecimal weight, Integer sex, Long registTime,String cardId) {
		super(babyName, babyId, birthday, height, health, hobby, specialty, gartenId, teacherId, attendanceceId, babyHead,
				allergy, parentId, parentRelation, weight, sex, registTime,cardId);
		// TODO Auto-generated constructor stub
	}
	public BabyMessage(String leadClass, String leadGrade, String cardNo) {
		super();
		this.leadClass = leadClass;
		this.leadGrade = leadGrade;
		this.cardNo = cardNo;
	}
	public String getLeadClass() {
		return leadClass;
	}
	public void setLeadClass(String leadClass) {
		this.leadClass = leadClass;
	}
	public String getLeadGrade() {
		return leadGrade;
	}
	public void setLeadGrade(String leadGrade) {
		this.leadGrade = leadGrade;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	
}
