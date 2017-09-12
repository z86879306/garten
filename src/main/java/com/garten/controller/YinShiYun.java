package com.garten.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garten.service.YinShiYunService;

@Controller
@RequestMapping("/yinshiyun")
public class YinShiYun {

	@Autowired
	private YinShiYunService yinShiYunService;
	
	
	//添加考勤摄像头
	@RequestMapping("/addDakaCamera")
	@ResponseBody
	public Map<String,Object> addDakaCamera(Integer gartenId, String cameraIp,Integer cameraPort,String cameraUser,String cameraPwd
	 ,Integer type,Integer macId,String deviceSerial,String validateCode){
		
		Map<String, Object> result = yinShiYunService.addDakaCamera(gartenId, cameraIp, cameraPort, 
				cameraUser, cameraPwd, type, macId, deviceSerial, validateCode);
		
		return result;
		
	}
	
	//添加直播摄像头
	@RequestMapping("/addLiveCamera")
	@ResponseBody
	public Map<String,Object> addLiveCamera(Integer gartenId, String cameraIp,Integer cameraPort,String cameraUser,
			String cameraPwd,String deviceSerial,String validateCode,Integer type, Integer pointId ){
		Map<String, Object> result = yinShiYunService.addLiveCamera(gartenId, cameraIp, cameraPort, cameraUser, cameraPwd, deviceSerial, validateCode,type,pointId);
		
		return result;
		
	}
	
	//删除摄像头设备
	@RequestMapping("/deleteCamera")
	@ResponseBody
	public Map<String,Object> deleteCamera(String deviceSerial){
		
		Map<String, Object> result = yinShiYunService.deleteCamera(deviceSerial);
		
		return result;
		
	}
	
	
	//摄像头列表
	@RequestMapping("/getCameraList")
	@ResponseBody
	public Map<String,Object> getCameraList(Integer gartenId,Integer type){
		
		Map<String, Object> result = yinShiYunService.getCameraList(gartenId, type);
		return result;
	}
}
