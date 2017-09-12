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
	
	
	//每天更新红花
	public void updateHonghua(){
		parentService.updateFlower();
	}
	
	//宝宝生日提醒
	public void babyBirthday() throws APIConnectionException, APIRequestException{
		parentService.babyBirthday();
	}
	
	//每年添加忽略时间
	public  void addIgnoreEveryYear() throws ParseException {
		bigcontrolService.addIgnoreEveryYear();
	}
	
	//每天删除未支付订单
	public void deleteOrderNoPay(){
		bigcontrolService.deleteOrderNoPay();
	}
}
