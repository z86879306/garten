package com.garten.util;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.garten.model.garten.GartenInfo;
import com.garten.service.BigcontrolService;
import com.garten.service.ParentService;
import com.garten.util.myutil.MyUtil;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;

@Component
public class TimeTask {

	@Autowired
	private ParentService parentService;
	@Autowired
	private BigcontrolService bigcontrolService;
	
	public void updateHonghua(){
		parentService.updateFlower();
	}
	
	
	public void babyBirthday() throws APIConnectionException, APIRequestException{
		parentService.babyBirthday();
	}
	
	public  void addIgnoreEveryYear() throws ParseException {
		bigcontrolService.addIgnoreEveryYear();
	}
}
