package com.garten.controller;



import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garten.model.baby.BabyInfo;
import com.garten.service.ParentService;
import com.garten.service.PrincipalService;





@Controller
@RequestMapping("principal")
public class PrincipalController {
	@Autowired
	private PrincipalService principalService;
	
	
	//园长登陆
	@RequestMapping(value="login")
	@ResponseBody
	public synchronized Map<String, Object> login(String phoneNumber,String pwd){
		Map<String,Object> result=principalService.login( phoneNumber, pwd);
		return result;
	}
	
	
	//园长首页
		@RequestMapping(value="index")
		@ResponseBody
		public Map<String, Object> index(String token){
			Map<String,Object> result=principalService.index( token);
			return result;
		}
		
	
	
	//园长修改密码
	@RequestMapping(value="updateLogin")
	@ResponseBody
	public synchronized Map<String, Object> updateLogin(String phoneNumber,String pwd,String number) throws ParseException{
		Map<String,Object> result=principalService.updateLogin( phoneNumber, pwd,number);
		return result;
	}
	
	@RequestMapping("getHuanxin")
	@ResponseBody
	public Map<String, Object> getHuanxin(String token){
		Map<String,Object> result=principalService.getHuanxin( token);
		return result;
	}
	
     //幼儿园接口在老师端    worker/introduceActivity
	@RequestMapping("introduceActivity")
   	public  @ResponseBody Map<String,Object> introduceActivity(String token)  {
       	Map<String ,Object>  map=principalService.introduceActivity( token );
   		return map;
   	}
	
	 //幼儿园接口在老师端    worker/activity
		@RequestMapping("activity")
	   	public  @ResponseBody Map<String,Object> activity(String token)  {
	       	Map<String ,Object>  map=principalService.activity( token );
	   		return map;
	   	}
	
		//视频
	  	@RequestMapping(value="video")
	   	public  @ResponseBody Map<String,Object> video(String token,Integer classId) throws ParseException  {
	       	Map<String ,Object>  map=principalService.video( token,classId);
	   		return map;
	   	}
	  	
	  //版本更新
	    @RequestMapping("version")
	   	public  @ResponseBody Map<String,Object> version(String token)  {
	       	Map<String ,Object>  map=principalService.version( token );
	   		return map;
	   	}
	  	
	
	 //朋友圈接口在老师端    worker/photo
	@RequestMapping("photo")
  	@ResponseBody
  	public Map<String, Object> photo(String token,Integer pageNo){
  		Map<String,Object> result=principalService.photo( token,pageNo);
  		return result;
  	}
	
	//园长发表朋友圈 [图片S]	worker/publishPhotoImg

	@RequestMapping(value="publishPhotoImg",method=RequestMethod.POST)
  	@ResponseBody
  	public synchronized Map<String, Object> publishImg(HttpServletRequest request,HttpSession session,String token,String content ){
  		System.err.println(token);
		Map<String,Object> result=principalService.publishImg(  request, session, token,content);
  		return result;
  	}
	//点击点赞 或取消点赞	  worker/dianzan
	
	@RequestMapping(value="dianzan")
  	@ResponseBody
  	public synchronized Map<String, Object> dianzan(String token,Integer infoId){
  		Map<String,Object> result=principalService.dianzan( token,infoId);
  		return result;
  	}
	//评论 朋友圈  	  worker/comment
	@RequestMapping(value="comment")
  	@ResponseBody
  	public synchronized Map<String, Object> comment(String token,Integer infoId,Integer replyCommentId,String replyName,String commentContent){
  		Map<String,Object> result=principalService.comment( token,infoId,replyCommentId,replyName,commentContent);
  		return result;
  	}
	
	//获取可以@的人 	 worker/replyPerson
	@RequestMapping("replyPerson")
 	@ResponseBody
 	public synchronized Map<String, Object> replyPerson(String token,Integer infoId){
 		Map<String,Object> result=principalService.replyPerson( token,infoId);
 		return result;
 	}
	
	//这个幼儿园所有班级名字
	@RequestMapping("getClassAllName")
 	@ResponseBody
 	public Map<String, Object> getClassAllName(String token){
 		Map<String,Object> result=principalService.getClassAllName( token);
 		return result;
 	}
	 //联系人接口在老师端    worker/linkMan
	@RequestMapping("linkManParent")
   	public  @ResponseBody Map<String,Object> linkManParent(String token,Integer classId)  {
       	Map<String ,Object>  map=principalService.linkManParent( token,classId );
   		return map;
   	}
	
	
	@RequestMapping("linkManWorker")
   	public  @ResponseBody Map<String,Object> linkManWorker(String token )  {
       	Map<String ,Object>  map=principalService.linkManWorker( token );
   		return map;
   	}
	
	 //个人信息接口在老师端    worker/teacher
	
	 @RequestMapping("teacher")
	   	public  @ResponseBody Map<String,Object> teacher(String token)  {
	       	Map<String ,Object>  map=principalService.teacher( token );
	   		return map;
	   	}
	 
	//修改个人信息接口在老师端    worker/updateTeacher
	
	
	 
	 //修改一个老师的头像
     @RequestMapping(value="updateTeacherHead",method=RequestMethod.POST)
    	public synchronized  @ResponseBody Map<String,Object> updateTeacherHead(HttpServletRequest request,HttpSession session,String token )  {
        	Map<String ,Object>  map=principalService.updateTeacherHead( request,session,token);
    		return map;
    	}
     
	//修改个人信息接口在老师端    worker/updateTeacher
		
		 @RequestMapping(value="updateTeacher"/*,method=RequestMethod.POST*/)
		   	public synchronized  @ResponseBody Map<String,Object> updateTeacher(String token,String workerName,Integer sex,Integer age)  {
		    	Map<String ,Object>  map=principalService.updateTeacher(  token, workerName, sex, age );
		   		return map;
		   	}
		 
	 
	//意见反馈接口在老师端    worker/feedback
	
	 @RequestMapping(value="feedback",method=RequestMethod.POST)
		@ResponseBody
		public synchronized Map<String, Object> feedback(String token,String content ) throws ParseException, InterruptedException{
			Map<String,Object> result=principalService.feedback( token,content);
			return result;
		}
	 
	//?通知中心接口在老师端      worker/notifiedCenter
	
	 @RequestMapping("notifiedCenter")
		@ResponseBody
		public Map<String, Object> notifiedCenter(String token,String type,Integer pageNo){
			Map<String,Object> result=principalService.notifiedCenter( token,type,pageNo);
			return result;
		}
	 
	 //所有职工某天晨检 考勤共用这一个接口
  	@RequestMapping("workerCheck")
  	@ResponseBody
  	public Map<String, Object> workerCheck(String token,Long time ) throws ParseException{
  		Map<String,Object> result=principalService.workerCheck( token, time);
  		return result;
  	}
  	
  	
  	//班级考勤 ,按班级分类所有学生的考勤
  	@RequestMapping("classCheck")
  	@ResponseBody
  	public Map<String, Object> classCheck(String token,Long time,Integer classId ) throws ParseException{
  		Map<String,Object> result=principalService.classCheck( token, time,classId);
  		return result;
  	}
	
  	//老师考勤异常
  	 @RequestMapping("yichang")
    	public  @ResponseBody Map<String,Object> yichang(String token,Long time) throws ParseException  {
        	Map<String ,Object>  map=principalService.yichang( token,time );
    		return map;
    	}
  	 
  	//处理老师考勤异常
  	@RequestMapping(value="yichangResolve")
   	public synchronized  @ResponseBody Map<String,Object> yichangResolve(Integer unusualId ,Integer state) throws ParseException  {
       	Map<String ,Object>  map=principalService.yichangResolve( unusualId ,state);
   		return map;
   	}
  	 
	
  	//某天所有老师的请假
  	 @RequestMapping("leaveLog")
    	public  @ResponseBody Map<String,Object> leaveLog(String token,Long time) throws ParseException  {
        	Map<String ,Object>  map=principalService.leaveLog( token,time );
    		return map;
    	}
  	 
  	 
  	//同意 或不同意职工请假   type 0:同意 1：不同意
   	@RequestMapping(value="agreeLeaveLog",method=RequestMethod.POST)
    	public  synchronized @ResponseBody Map<String,Object> agreeLeaveLog(Integer wleaveId,Integer type) throws ParseException  {
        	Map<String ,Object>  map=principalService.agreeLeaveLog( wleaveId,type );
    		return map;
    	}
	
  	 //老师考勤,请假,红圈
  	@RequestMapping("circle")
	@ResponseBody
	public Map<String, Object> recipeCircle(String token,Integer type) throws ParseException, InterruptedException{
		Map<String,Object> result=principalService.circle( token,type);
		return result;
	}
  	
  	
  	
	
	
}
