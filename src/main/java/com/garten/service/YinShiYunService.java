package com.garten.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garten.dao.YinShiYunDao;
import com.garten.model.garten.GartenVideo;
import com.garten.model.other.Equipment;
import com.garten.util.LyParam;
import com.garten.util.LyUtils;

@Service("yinshiyunService")
public class YinShiYunService {

	@Autowired
	private YinShiYunDao yinshiyunDao;
	
	public Map<String,Object> addDakaCamera(Integer gartenId, String cameraIp,Integer cameraPort,String cameraUser,String cameraPwd
			 ,Integer type,Integer macId,String deviceSerial,String validateCode){
		HashMap<String,Object> result = new HashMap<String,Object>();
		try {
			yinshiyunDao.addDakaCamera(gartenId, cameraIp, cameraPort, cameraUser, cameraPwd, type, macId, deviceSerial, validateCode);
			//获取萤石云accessToken
			String accessToken = LyUtils.getAccessToken();
			//添加直播设备
			LyUtils.sendPost("https://open.ys7.com/api/lapp/device/add", "accessToken="+accessToken+
			"&deviceSerial="+deviceSerial+"&validateCode="+validateCode);
			result.put("resCode", 0);
			result.put("resDesc", "请求成功");
		} catch (Exception e) {
			result.put("resCode", 1);
			result.put("resDesc", "请求失败");
		}
		return result;
	}
	
	public Map<String,Object> addLiveCamera(Integer gartenId, String cameraIp,Integer cameraPort,String cameraUser,
			String cameraPwd,String deviceSerial,String validateCode, Integer type, Integer pointId){
		HashMap<String,Object> result = new HashMap<String,Object>();
		try {
			yinshiyunDao.addLiveCamera(gartenId, cameraIp, cameraPort, cameraUser, cameraPwd, deviceSerial, validateCode);
			//获取萤石云accessToken
			String accessToken = LyUtils.getAccessToken();
			//添加直播设备
			LyUtils.sendPost("https://open.ys7.com/api/lapp/device/add", "accessToken="+accessToken+
					"&deviceSerial="+deviceSerial+"&validateCode="+validateCode);
			//开通直播 获取直播url
			String url = LyUtils.dredgeLive(deviceSerial);
			if(type==0){		//教室
				yinshiyunDao.addGartenVideo(deviceSerial,gartenId,url,type,pointId,LyParam.Garten_Class_Video);
			}else if(type==1){	//操场
				yinshiyunDao.addGartenVideo(deviceSerial,gartenId,url,type,pointId,LyParam.Garten_PlayGround_Video);
			}else if(type==2){	//食堂
				yinshiyunDao.addGartenVideo(deviceSerial,gartenId,url,type,pointId,LyParam.Garten_Dinner_Video);
			}else if(type==3){	//公共教室
				yinshiyunDao.addGartenVideo(deviceSerial,gartenId,url,type,pointId,LyParam.Garten_CommonClass_Video);
			}
			result.put("resCode", 0);
			result.put("resDesc", "请求成功");
		} catch (Exception e) {
			result.put("resCode", 1);
			result.put("resDesc", "请求失败");
		}
		return result;
	}
	
	//删除摄像头设备
	public Map<String,Object> deleteCamera(String deviceSerial){
		HashMap<String, Object> result = new HashMap<String,Object>();
		try {
			String accessToken = LyUtils.getAccessToken();
			LyUtils.sendPost(" https://open.ys7.com/api/lapp/device/delete", "accessToken="+accessToken+"&deviceSerial="+deviceSerial);
			yinshiyunDao.deleteCamera(deviceSerial);
			yinshiyunDao.deleteGartenVideo(deviceSerial);
			result.put("resCode", 0);
			result.put("resDesc", "请求成功");
		} catch (Exception e) {
			result.put("resCode", 1);
			result.put("resDesc", "请求失败");
		}
		return result;
	}
	
	
	public Map<String,Object> getCameraList(Integer gartenId,Integer type){
		HashMap<String, Object> result = new HashMap<String,Object>();
		
			if(type==0){			//0为打卡考勤摄像机
				List<Equipment> list = yinshiyunDao.findEquipBygartenId(gartenId);
				result.put("list", list);
			}else if(type==1){		//1为直播摄像机
				List<GartenVideo> list = yinshiyunDao.findVideoByGartenId(gartenId);
				result.put("list", list);
			}
			result.put("resCode", 0);
			result.put("resDesc", "请求成功");
	
		return  result;
	}
}
