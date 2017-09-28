package com.garten.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.garten.model.garten.GartenInfo;
import com.garten.model.garten.GartenRecipe;
import com.garten.service.BigcontrolService;
import com.garten.service.SmallcontrolService;
import com.garten.util.myutil.MyUtil;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;

@Controller
@RequestMapping("smallcontrol")
public class SmallcontrolController {
	@Autowired
	private SmallcontrolService smallcontrolService;
	
	@RequestMapping(value="login")
	@ResponseBody
	public synchronized Map<String, Object> login(String phoneNumber,String pwd){
		Map<String,Object> result=smallcontrolService.login( phoneNumber, pwd);
		return result;
	}
	
	//发送验证码 在worker里面 sendControl
	@RequestMapping("updateLogin")//修改密码
	public  synchronized @ResponseBody Map<String,Object> updateLogin(String phoneNumber,String pwd,String number ) throws ParseException {
		Map<String,Object> map=smallcontrolService.updateLogin( phoneNumber, pwd, number);
		return map;
	}
	
	@RequestMapping("getGartenGrade")//
	public  @ResponseBody Map<String,Object> getGartenGrade(String  token ) throws ParseException {
		Map<String,Object> map=smallcontrolService.getGartenGrade( token);
		return map;
	}

	@RequestMapping("sendNotified")//发送通知
	public  synchronized @ResponseBody Map<String,Object> sendNotified(String token,String leadClass,String leadGrade ,Integer type ,String title,String content) throws ParseException, APIConnectionException, APIRequestException {
		Map<String,Object> map=smallcontrolService.sendNotified(  token,  leadClass, leadGrade , type, title, content);
		return map;
	}
	
	@RequestMapping("workerMessage")//用户管理-老师管理
	public  @ResponseBody Map<String,Object> WorkerMessage(String token,String name,String phoneNumber,Integer pageNo ) throws ParseException, APIConnectionException, APIRequestException {
		Map<String,Object> map=smallcontrolService.WorkerMessage(  token, name, phoneNumber,pageNo);
		return map;
	}
	
	@RequestMapping("workerMessageNo")//用户管理-老师管理
	public  @ResponseBody Map<String,Object> workerMessageNo(String token,String name,String phoneNumber) throws ParseException, APIConnectionException, APIRequestException {
		Map<String,Object> map=smallcontrolService.WorkerMessageNo(  token, name, phoneNumber);
		return map;
	}
	@RequestMapping("babyMessage")//用户管理-宝宝管理
	public  @ResponseBody Map<String,Object> babyMessage(String token,String name ,String leadGrade,String leadClass,Integer pageNo ) throws ParseException {
		Map<String,Object> map=smallcontrolService.babyMessage( token, name,leadGrade,leadClass,pageNo);
		return map;
	}
	
	@RequestMapping("parentMessage")//用户管理-家长管理
	public  @ResponseBody Map<String,Object> parentMessage(String token,String name ,String phoneNumber,Integer pageNo ,Integer attendanceState,Integer monitorState) throws ParseException {
		Map<String,Object> map=smallcontrolService.parentMessage( token, name,phoneNumber,pageNo,attendanceState,monitorState);
		return map;
	}
	
	//这个幼儿园所有班级信息
		@RequestMapping("getClassAll")
	 	@ResponseBody
	 	public Map<String, Object> getClassAll(String token,String leadGrade,Integer pageNo){
	 		Map<String,Object> result=smallcontrolService.getClassAll( token,leadGrade,pageNo);
	 		return result;
	 	}
		//当前班级到了几号了
		@RequestMapping("getClassNumber")
	 	@ResponseBody
	 	public Map<String, Object> getClassNumber(String token){
	 		Map<String,Object> result=smallcontrolService.getClassNumber( token);
	 		return result;
	 	}
	
	//添加班级 大小中都添加一个
	@RequestMapping("addClass")
 	@ResponseBody
 	public synchronized Map<String, Object> addClass(String token){
 		Map<String,Object> result=smallcontrolService.addClass( token);
 		return result;
 	}
	
	//删除班级 大小中都删除一个
	@RequestMapping("deleteClass")
 	@ResponseBody
 	public synchronized Map<String, Object> deleteClass(String token){
 		Map<String,Object> result=smallcontrolService.deleteClass( token);
 		return result;
 	}
		
		@RequestMapping("graduation")
	 	@ResponseBody
	 	public synchronized Map<String, Object> graduation(String token){
	 		Map<String,Object> result=smallcontrolService.graduation( token);
	 		return result;
	 	}
		
		
		@RequestMapping("getDakaTime")
	 	@ResponseBody
	 	public Map<String, Object> getDakaTime(String token){
	 		Map<String,Object> result=smallcontrolService.getDakaTime( token);
	 		return result;
	 	}
		
		@RequestMapping("updateDakaTime")
	 	@ResponseBody
	 	public synchronized Map<String, Object> updateDakaTime(String token,String arriveStartTime,String arriveEndTime,String leaveStartTime,String leaveEndTime,Integer teacherAttendanceFlag,Integer studentAttendanceFlag){
	 		Map<String,Object> result=smallcontrolService.updateDakaTime(  token, arriveStartTime, arriveEndTime, leaveStartTime, leaveEndTime,teacherAttendanceFlag,studentAttendanceFlag);
	 		return result;
	 	}
		
		
		@RequestMapping("getIgnoreTime")
	 	@ResponseBody
	 	public Map<String, Object> getIgnoreTime(String token,Integer pageNo){
	 		Map<String,Object> result=smallcontrolService.getIgnoreTime( token,pageNo);
	 		return result;
	 	}
		
		
		//添加忽略时间
		@RequestMapping("addIgnoreTime")
	 	@ResponseBody
	 	public synchronized Map<String, Object> addIgnoreTime(String token,Long date){
	 		Map<String,Object> result=smallcontrolService.addIgnoreTime( token,date);
	 		return result;
	 	}
		//删除忽略时间
		@RequestMapping("deleteIgnoreTime")
	 	@ResponseBody
	 	public synchronized Map<String, Object> deleteIgnoreTime(String token,Long date){
	 		Map<String,Object> result=smallcontrolService.deleteIgnoreTime( token,date);
	 		return result;
	 	}
		
		//考勤异常管理
		@RequestMapping("yichang")
	 	@ResponseBody
	 	public Map<String, Object> yichang(String token,Long startTime,Long endTime,Integer type,Integer state,Integer pageNo) throws ParseException{
	 		Map<String,Object> result=smallcontrolService.yichang( token,startTime,endTime, type, state,pageNo);
	 		return result;
	 	}
		
		
		//处理老师考勤异常   principal/yichangAgree   :unusualId
	  	
	  	
		//请假管理
		@RequestMapping("leave")
	 	@ResponseBody
	 	public Map<String, Object> leave(String token,Long startTime,Long endTime,Integer type,Integer state,Integer pageNo) throws ParseException{
	 		Map<String,Object> result=smallcontrolService.leave( token,startTime,endTime, type, state,pageNo);
	 		return result;
	 	}
		
		@RequestMapping("classCheck")
	  	@ResponseBody
	  	public Map<String, Object> classCheck(String token,Long time,Integer classId,Integer pageNo ) throws ParseException{
	  		Map<String,Object> result=smallcontrolService.classCheck( token, time,classId,pageNo);
	  		return result;
	  	}
		
		
		
		@RequestMapping("lesson")
	  	@ResponseBody
	  	public Map<String, Object> lesson(String token,Long time,Integer classId) throws ParseException{
	  		Map<String,Object> result=smallcontrolService.lesson( token, time, classId );
	  		return result;
	  	}
		
		
		@RequestMapping("addlesson")
	  	@ResponseBody
	  	public synchronized Map<String, Object> addlesson(String token,Long time,Integer ampm,String lessonName,Integer classId,String startTime,String endTime ) throws ParseException{
	  		Map<String,Object> result=smallcontrolService.addlesson( token, time, ampm, lessonName, classId, startTime, endTime );
	  		return result;
	  	}
		
		@RequestMapping("addWeekLesson")
		@ResponseBody
		public synchronized Map<String, Object> addWeekLesson(String token,Long time,Integer[] ampm,String[] lessonName,Integer classId,String[] startTime,String[] endTime){
			Map<String, Object> map = smallcontrolService.addWeekLesson(token, time, ampm, lessonName, classId, startTime, endTime);
			return map;
		}
		@RequestMapping("deletelesson")
	  	@ResponseBody
	  	public synchronized Map<String, Object> deletelesson(String token,Integer lessonId ) throws ParseException{
	  		Map<String,Object> result=smallcontrolService.deletelesson( token,lessonId );
	  		return result;
	  	}
		
		
		
		@RequestMapping("recipe")
	  	@ResponseBody
	  	public Map<String, Object> recipe(String token,Long time ) throws ParseException{
	  		Map<String,Object> result=smallcontrolService.recipe( token, time );
	  		return result;
	  	}
		
		
		@RequestMapping("addrecipe")
	  	@ResponseBody
	  	public synchronized Map<String, Object> addrecipe(String token,Long time,String foodName,String foodContent,HttpServletRequest request,HttpSession session) throws ParseException{
	  		Map<String,Object> result=smallcontrolService.addrecipe(  token, time, foodName, foodContent, request, session);
	  		return result;
	  	}
		
		
		@RequestMapping("order")
	  	@ResponseBody
	  	public Map<String, Object> order(String token,Long startTime,Long endTime,String name,String phoneNumber,Integer pageNo) throws ParseException{
	  		Map<String,Object> result=smallcontrolService.order(  token, startTime ,endTime, name, phoneNumber,pageNo);
	  		return result;
	  	}
		
		
		
		/*
		 *   8.19 交接
		 *   
		 */
		
		//通过classId获取所有学生
		@RequestMapping("getStudentList")
		@ResponseBody
		public Map<String,Object> getStudentList(String token,Integer classId){
			Map<String, Object> map = smallcontrolService.getStudentList(token, classId);
			return map;
		}
		
		//考勤卡绑定信息
		@RequestMapping("cardNoList")
		@ResponseBody
		public Map<String,Object> cardNoList(String token,String job,Integer classId,Integer pageNo){
			Map<String, Object> map = smallcontrolService.cardNoList(token, job, classId,pageNo);
			
			return map;
		}
		
		//绑定考勤卡
		@RequestMapping("bindingCard")
		@ResponseBody
		public synchronized Map<String,Object> bindingCard(String token,Integer jobId,String cardNo){
			Map<String, Object> map = smallcontrolService.bindingCard(token, jobId, cardNo);
			return map;
		}
		
		
		//解除绑定
		@RequestMapping("cancelBinding")
		@ResponseBody
		public synchronized Map<String,Object> cancelBinding(String token,Integer jobId, String cardNo){
			Map<String, Object> map = smallcontrolService.cancelBinding(token, jobId, cardNo);
			return map;
		}
		
		//获取老师权限列表
		@RequestMapping("teacherPermission")
		@ResponseBody
		public Map<String,Object> teacherPermission(){
			Map<String, Object> map = smallcontrolService.teacherPermission();
			return map;
		}
		//修改老师信息
		@RequestMapping("updateTeacher")
		@ResponseBody
		public synchronized Map<String, Object> updateTeacher(String token,Integer sex,Integer age,String education,String certificate,String chinese,
				Integer classId,String phoneNumber,String workerName,Integer workerId,String jobcall,String permission){
			Map<String, Object> map = smallcontrolService.updateTeacher(token, sex, age, education, certificate, chinese, classId, phoneNumber, workerName, workerId,jobcall,permission);
			return map;
			
		}
		
		//修改宝宝信息
		@RequestMapping("updateBaby")
		@ResponseBody
		public synchronized Map<String, Object> updateBaby(String token,Integer babyId,String babyName,Integer sex,Long birthday,Integer classId,Double height,String health,String hobby,String specialty,String allergy,Double weight,Integer parentId){
			Map<String, Object> map = smallcontrolService.updateBaby(token, babyId, babyName, sex, birthday,  classId,height,health,hobby,specialty,allergy,weight,parentId);
			return map;
			
		}
		//修改家长信息
		@RequestMapping("updateParent")
		@ResponseBody
		public synchronized Map<String,Object> updateParent(String token,Integer parentId,String parentName,String address,String[] identity,Integer[] babyId,String phoneNumber){
			Map<String, Object> map = smallcontrolService.updateParent(token, parentId, parentName, address,identity,babyId,phoneNumber);
			return map;
		}
		
		//添加老师
		@RequestMapping("addTeacher")
		@ResponseBody
		public synchronized Map<String,Object> addTeacher(String token,String teacherName,Integer sex,Integer age,String phoneNumber,Integer classId,String education,
				String certificate,String chinese,String jobCall,String permission) throws ParseException, IOException{
			Map<String, Object> map = smallcontrolService.addTeacher(token, teacherName, sex, age, phoneNumber, classId, education, certificate, chinese, jobCall,permission);
			return map;
			
		}
		
		//添加宝宝（同时添加家长）
		@RequestMapping("addBaby")
		@ResponseBody
		public synchronized Map<String,Object> addBaby(String token,String babyName,Double height,String health,String hobby,String specialty,Integer classId,
				String allergy,Double weight,Integer sex,String parentName,String phoneNumber,String address,String identity,String cardId) throws ParseException, IOException{
			Map<String, Object> map = smallcontrolService.addBaby(token, babyName, height, health, hobby, specialty, classId, allergy, weight, sex, parentName, phoneNumber, address,identity,cardId);
			return map;
		}
		
		//添加家长 选择宝宝(宝宝已存在)
		@RequestMapping("addParent")
		@ResponseBody
		public synchronized Map<String,Object> addParent(String token,String parentName,String phoneNumber,String address,String[] identity,Integer[] babyId) throws IOException{
			Map<String, Object> map = smallcontrolService.addParent(token, parentName, phoneNumber, address, identity, babyId);
			return map;
			
		}
		
		//删除老师
		@RequestMapping("deleteTeacher")
		@ResponseBody
		public synchronized Map<String,Object> deleteTeacher(String token,Integer workerId){
			Map<String, Object> map = smallcontrolService.deleteTeacher(token, workerId);
			return map;
		}
		
		//删除宝宝
		@RequestMapping("deleteBaby")
		@ResponseBody
		public synchronized Map<String,Object> deleteBaby(String token,Integer babyId){
			Map<String, Object> map = smallcontrolService.deleteBaby(token, babyId);
			return map;
		}
		
		//删除家长
		@RequestMapping("deleteParent")
		@ResponseBody
		public synchronized Map<String,Object> deleteParent(String token,Integer parentId){
			Map<String, Object> map = smallcontrolService.deleteParent(token, parentId);
			return map;
		}
		
		//年级、班级管理（班级列表）
		//@RequestMapping("")
		
		//该幼儿园资料
		@RequestMapping("getGartenMessage")
		@ResponseBody
		public Map<String ,Object > getGartenMessage(String token){
			Map<String, Object> map = smallcontrolService.getGartenMessage(token);
			return map;
		}
		
		/*//消息历史
		@RequestMapping("notifyHistory")
		@ResponseBody
		public Map<String,Object> notifyHistory(String token,String job,Long startTime,Long endTime,Integer pageNo){
			Map<String, Object> map = smallcontrolService.notifyHistory(token, job, startTime, endTime,pageNo);
			return map;
		}*/
		
		//删除食谱
		@RequestMapping("deleteRecipe")
		@ResponseBody
		public synchronized Map<String ,Object> deleteRecipe(String token, Integer recipeId){
			Map<String, Object> map = smallcontrolService.deleteRecipe(token, recipeId);
			return map;
		}
		
		//支付宝支付
		@RequestMapping(value="alipay")
		public  synchronized @ResponseBody Map<String, Object> alipay(String token,Integer type,Integer monthCount,HttpServletRequest httpRequest,
	            HttpServletResponse httpResponse  ) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, AlipayApiException, APIConnectionException, APIRequestException, IOException {
	    	Map<String, Object> map = smallcontrolService.alipay(token,type,monthCount, httpRequest,
	                 httpResponse);
			return null;
		}
		//支付宝验证
	   	@RequestMapping(value="alipayyz")
		public  @ResponseBody String alipayyz() throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
	    	String map = smallcontrolService.alipayyz();
			return map;
		}
	   	
	  //修改主监护人
	  	@RequestMapping("updateMainParent")
	  	@ResponseBody
	  	public Map<String, Object> updateMainParent(Integer babyId,Integer parentId) throws ParseException{
	  		Map<String,Object> result=smallcontrolService.updateMainParent( babyId,parentId );
	  		return result;
	  	}
	  	
		//所有监护人
	  	@RequestMapping("getminorParent")
	  	@ResponseBody
	  	public Map<String, Object> getminorParent(Integer babyId) throws ParseException{
	  		Map<String,Object> result=smallcontrolService.getminorParent( babyId);
	  		return result;
	  	}


	   	
	   	//支付test
	   	@RequestMapping("alipayTest")
	   	@ResponseBody
	   	public Map<String ,Object> alipayTest(String orderNumber) throws ParseException{
	   		String string = smallcontrolService.alipayTest(orderNumber);
	   		return null;
	   	}
	   	
	   	/*//test
	   	@RequestMapping("strTest")
	   	@ResponseBody
	   	public Map<String,Object> strTest(String str) throws ParseException{
	   		MyUtil.addIgnoreYear(100);
	   		return null;
	   	}*/
	   	
	   	@RequestMapping("messagelog")//消息历史
		public  @ResponseBody Map<String,Object> messagelog(String token,Long start,Long end,Integer pageNo) throws ParseException, APIConnectionException, APIRequestException {
			Map<String,Object> map=smallcontrolService.messagelog(  token,start,end,pageNo);
			return map;
		}

	   	//老师短信申请列表
	   	@RequestMapping("teacherMessage")
	   	@ResponseBody
	   	public synchronized Map<String,Object> teacherMessage(String token,Long startTime , Long endTime,Integer pageNo,Integer state){
	   		Map<String, Object> map = smallcontrolService.teacherMessage(token, startTime, endTime, pageNo,state);
	   		return map;
	   	}
	   	
	   	//同意老师短信群发申请
	   	@RequestMapping("agreeMessage")
	   	@ResponseBody
	   	public synchronized Map<String,Object> agreeMessage(String token,Integer messageId){
	   		Map<String, Object> map = smallcontrolService.agreeMessage(token, messageId);
	   		return map;
	   	}
	   	
	   	//访问视频，考勤次数统计
	   	@RequestMapping("VisitCount")
	   	@ResponseBody
	   	public synchronized Map<String,Object> VisitCount(String token){
	   		Map<String, Object> map = smallcontrolService.VisitCount(token);
	   		return map;
	   	}
}
