package com.garten.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.garten.service.AttendanceService;
import com.garten.vo.attendance.BabyAttendanceInfo;




@Controller
@RequestMapping("/attendance")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;
	
	//获取身份令牌token 
	@RequestMapping(value="/getPartnerToken",method=RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> getPartnerToken(@RequestBody JSONObject info /*String partnerId,Long tms, String md5tms*/){
		String partnerId= info.getString("partnerId");
		String tms= info.getString("tms");
		String md5tms= info.getString("md5tms");
		String macId= info.getString("macId");
		Map<String, Object> result = attendanceService.getPartnerTokenNew(partnerId, tms, md5tms,macId);
		return result;
	}
	
	//获取学校班级列表
	@RequestMapping(value="/getSchClassList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getSchClassList(@RequestBody JSONObject info /*String schoolToken,String partnerToken,String partnerId*/){
		String schoolToken= info.getString("schoolToken");
		String partnerToken= info.getString("partnerToken");
		String partnerId= info.getString("partnerId");
		Map<String, Object> result = attendanceService.getSchClassList(schoolToken,partnerToken,partnerId);
		return result;
	}
	
	//获取班级下面的学生列表
	@RequestMapping(value="/getStudentList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getStudentList(@RequestBody JSONObject info /*String schoolToken,String partnerToken,String partnerId,Integer classId*/){
		String schoolToken= info.getString("schoolToken");
		String partnerToken= info.getString("partnerToken");
		String partnerId= info.getString("partnerId");
		Integer classId = info.getInteger("classId");
		Map<String, Object> result = attendanceService.getStudentList(classId, schoolToken, partnerToken, partnerId);
		return result;
	}
	
	//获取打卡时间段
	@RequestMapping(value="/getPlayTime",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getPlayTime(@RequestBody JSONObject info /*String schoolToken,String partnerToken,String partnerId*/){
		String schoolToken= info.getString("schoolToken");
		String partnerToken= info.getString("partnerToken");
		String partnerId= info.getString("partnerId");
		Map<String, Object> result = attendanceService.getPlayTimeNew( schoolToken, partnerToken, partnerId);
		return result;
	}
	
	//宝宝考勤数据上传
/*	@RequestMapping(value="/sendAttendanceData",method=RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String,Object> sendAttendanceData(@RequestBody JSONObject info){
//		JSONObject jsonObject = JSONObject.parseObject(info);
		JSONArray array = info.getJSONArray("attendanceInfoList");
		String js = JSONObject.toJSONString(array);
		List<BabyAttendanceInfo> list = JSONObject.parseArray(js, BabyAttendanceInfo.class);
		String schoolToken=info.getString("schoolToken");
		String partnerToken=info.getString("partnerToken");
		String partnerId=info.getString("partnerId");
		Map<String, Object> result = attendanceService.
				sendAttendanceData(list, schoolToken, partnerToken, partnerId);
		return result;
	}*/
	
	//宝宝考勤数据上传{ 新  （4个时间）}
		@RequestMapping(value="/sendAttendanceData",method=RequestMethod.POST)
		@ResponseBody
		public synchronized Map<String,Object> sendAttendanceData(@RequestBody JSONObject info){
//			JSONObject jsonObject = JSONObject.parseObject(info);
			JSONArray array = info.getJSONArray("attendanceInfoList");
			String js = JSONObject.toJSONString(array);
			List<BabyAttendanceInfo> list = JSONObject.parseArray(js, BabyAttendanceInfo.class);
			String schoolToken=info.getString("schoolToken");
			String partnerToken=info.getString("partnerToken");
			String partnerId=info.getString("partnerId");
			String macId=info.getString("macId");
			Map<String, Object> result = attendanceService.
					sendAttendanceDataNew(list, schoolToken, partnerToken, partnerId,macId);
			return result;
		}
	
	//根据幼儿园下发老师和宝宝信息
	@RequestMapping(value="/getTeacherAndStudentList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getTeacherAndStudentList(@RequestBody JSONObject info /*String schoolToken,String partnerToken,String partnerId*/){
		String schoolToken= info.getString("schoolToken");
		String partnerToken= info.getString("partnerToken");
		String partnerId= info.getString("partnerId");
		Map<String, Object> result = attendanceService.getTeacherAndStudentList(schoolToken, partnerToken, partnerId);
		return result;
	}
	
	//心跳接口
	@RequestMapping(value="/sendHeartbeat",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> sendHeartbeat(@RequestBody JSONObject info /*String schoolToken,String partnerToken,String partnerId*/){
		String schoolToken=info.getString("schoolToken");
		String partnerToken=info.getString("partnerToken");
		String partnerId=info.getString("partnerId");
		Map<String, Object> result = attendanceService.sendHeartbeat(schoolToken, partnerToken, partnerId);
		return result;
	}
	
	//附件上传接口
	@RequestMapping(value="/attendanceFileUpload",method=RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String,Object> attendanceFileUpload(@RequestParam("files") MultipartFile[] files ,String partnerToken,String schoolToken,String partnerId,String macId,String[] fileIds){
		Map<String, Object> result = attendanceService.attendanceFileUpload(schoolToken, partnerToken, partnerId, macId, files, fileIds);
		
		return result;
		
	}

	//拉取幼儿园考勤配置接口
	@RequestMapping(value="/getAttendanceConf",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getAttendanceConf(@RequestBody JSONObject info /*String schoolToken,String partnerToken,String partnerId,String macId*/){
		String schoolToken= info.getString("schoolToken");
		String partnerToken= info.getString("partnerToken");
		String partnerId= info.getString("partnerId");
		String macId= info.getString("macId");
		Map<String, Object> result = attendanceService.getAttendanceConf(schoolToken, partnerToken, partnerId, macId);
		return result;
	}
	
	//客户端查询是否需要重启接口
	@RequestMapping(value="/reboot",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> reboot(@RequestBody JSONObject info /*String schoolToken,String partnerToken,String partnerId*/){
		String schoolToken= info.getString("schoolToken");
		String partnerToken= info.getString("partnerToken");
		String partnerId= info.getString("partnerId");
		String macId= info.getString("macId");
		Map<String, Object> result = attendanceService.reboot(schoolToken, partnerToken, partnerId,macId);
		return result;
		
	}
}
