package com.garten.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.annotate.JsonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garten.service.WorkerService;
import com.garten.util.lxcutil.MyParamAll;
import com.garten.util.myutil.MyUtil;
import com.garten.vo.teacher.ClassManage;





@Controller
@RequestMapping("worker")
public class WorkerController {
	@Autowired
	private WorkerService workerService;
	
	
	@RequestMapping(value="login")
	@ResponseBody
	public synchronized Map<String, Object> login(String phoneNumber,String pwd){
		Map<String,Object> result=workerService.login( phoneNumber, pwd);
		return result;
	}
	
	//发送验证码
	@RequestMapping("sendCheckWorker")//老师端发送短信  0:老师 2:园长
	public  synchronized @ResponseBody Map<String,Object> sendCheckWorker(String phoneNumber) {
		Map<String,Object> map=workerService.sendCheck(phoneNumber,0);
		return map;
	}
	
	//发送验证码
	@RequestMapping("sendCheckParent")//家长端发送短信
	public  synchronized @ResponseBody Map<String,Object> sendCheckParent(String phoneNumber ) {
		Map<String,Object> map=workerService.sendCheck(phoneNumber,1);
		return map;
	}
	
	//发送验证码
		@RequestMapping("sendCheckPrincipal")//院长段发送短信
		public synchronized  @ResponseBody Map<String,Object> sendCheckPrincipal(String phoneNumber ) {
			Map<String,Object> map=workerService.sendCheck(phoneNumber,2);
			return map;
		}
		
	//发送验证码
	@RequestMapping("sendControl")//总管理员发送短信
	public  synchronized @ResponseBody Map<String,Object> sendControl(String phoneNumber ) {
		Map<String,Object> map=workerService.sendCheck(phoneNumber,3);
		return map;
	}
	
	//发送验证码
		@RequestMapping("sendControlSmall")//小控制端发送短信
		public synchronized  @ResponseBody Map<String,Object> sendControlSmall(String phoneNumber ) {
			Map<String,Object> map=workerService.sendCheck(phoneNumber,4);
			return map;
		}
	
	//发送验证码
	@RequestMapping("sendControlAgent")//代理商发送短信
	public synchronized  @ResponseBody Map<String,Object> sendControlAgent(String phoneNumber ) {
		Map<String,Object> map=workerService.sendCheck(phoneNumber,5);
		return map;
	}

	
	@RequestMapping(value="updateLogin")
	@ResponseBody
	public synchronized Map<String, Object> updateLogin(String phoneNumber,String pwd,String number) throws ParseException{
		Map<String,Object> result=workerService.updateLogin( phoneNumber, pwd,number);
		return result;
	}
	
	
	@RequestMapping("shouye")
	@ResponseBody
	public Map<String, Object> shouye(String token){
		Map<String,Object> result=workerService.shouye( token);
		return result;
	}
	
	@RequestMapping("getHuanxin")
	@ResponseBody
	public Map<String, Object> getHuanxin(String token){
		Map<String,Object> result=workerService.getHuanxin( token);
		return result;
	}
	
	@RequestMapping("notifiedCenter")
	@ResponseBody
	public Map<String, Object> notifiedCenter(String token,String type,Integer pageNo){
		Map<String,Object> result=workerService.notifiedCenter( token,type,pageNo);
		return result;
	}
	//读取通知
	@RequestMapping("readNotified")
	@ResponseBody
	public Map<String, Object> readNotified(String infoId){
		Map<String,Object> result=workerService.readNotified( infoId);
		return result;
	}
	//删除朋友圈评论
		@RequestMapping("deleteComment")
		@ResponseBody
		public synchronized Map<String, Object> deleteComment(String commentId){
			Map<String,Object> result=workerService.deleteComment(commentId);
			return result;
		}
		
//删除朋友圈评论
		@RequestMapping("deletePhoto")
		@ResponseBody
		public synchronized Map<String, Object> deletePhoto(String infoId){
			Map<String,Object> result=workerService.deletePhoto(infoId);
			return result;
		}
	
	
	
	
	
	@RequestMapping("classManage")
	@ResponseBody
	public Map<String, Object> classManage(String token,Integer pageNo){
		Map<String,Object> result=workerService.classManage( token,pageNo);
		return result;
	}
	
	
	
	//这个班级某天课程
	@RequestMapping("lesson")
	@ResponseBody
	public Map<String, Object> lesson(String token,Long time) throws ParseException{
		Map<String,Object> result=workerService.lesson( token,time);
		return result;
	}
	/*token"babyName""allergy""birthday""height""hobby"
	 * "specialty""leadClass""leadGrade""health""babyId"teacherId,weight,sex*/

	//修改某个宝宝
	@RequestMapping(value="updateBaby",method=RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String, Object> updateBaby(String token,String  babyName,String allergy,Long birthday,BigDecimal height,String hobby
			 ,String specialty ,String leadClass,String leadGrade,String health,Integer babyId,Float  weight,Integer sex ){
		Map<String,Object> result=workerService.updateBaby(  token,  babyName, allergy, birthday, height, hobby
				 , specialty , leadClass, leadGrade, health, babyId,weight,sex);
		return result;
	}
	//本班级所有的学生表现信息
	@RequestMapping("performance")
	@ResponseBody
	public Map<String, Object> performance(String token,Integer type,Integer pageNo){
		Map<String,Object> result=workerService.performance( token,type,pageNo);
		return result;
	}
	
	@RequestMapping(value="toPerformance",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> toPerformance(Integer performanceId,Float learn,Float play,Float eat,Float sleep,String remark){
		Map<String,Object> result=workerService.toPerformance(  performanceId, learn, play, eat, sleep, remark);
		return result;
	}
	//老师查看自己某天的签到详情
	@RequestMapping("sign")
	@ResponseBody
	public Map<String, Object> sign(String token,Long time) throws ParseException, InterruptedException{
		Map<String,Object> result=workerService.sign( token,time);
		return result;
	}
	
	@RequestMapping("circle")
	@ResponseBody
	public Map<String, Object> recipeCircle(String token,Integer type) throws ParseException, InterruptedException{
		Map<String,Object> result=workerService.circle( token,type);
		return result;
	}
	
	@RequestMapping("recipe")
	@ResponseBody
	public Map<String, Object> recipe(String token,Long time) throws ParseException, InterruptedException{
		Map<String,Object> result=workerService.recipe( token,time);
		return result;
	}
	
	
	
	@RequestMapping(value="feedback",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> feedback(String token,String content ) throws ParseException, InterruptedException{
		Map<String,Object> result=workerService.feedback( token,content);
		return result;
	}

	//版本更新
    @RequestMapping("version")
   	public  @ResponseBody Map<String,Object> version(String token)  {
       	Map<String ,Object>  map=workerService.version( token );
   		return map;
   	}
    
    @RequestMapping("introduceActivity")
   	public  @ResponseBody Map<String,Object> introduceActivity(String token)  {
       	Map<String ,Object>  map=workerService.introduceActivity( token );
   		return map;
   	}
    
    @RequestMapping("activity")
   	public  @ResponseBody Map<String,Object> activity(String token)  {
       	Map<String ,Object>  map=workerService.activity( token );
   		return map;
   	}

    
    	//本班级所有代接信息    type  0:申请中  1:已接
    @RequestMapping("daijie")
   	public  @ResponseBody Map<String,Object> daijie(String token,Integer type,Long time) throws ParseException  {
       	Map<String ,Object>  map=workerService.daijie( token,type,time );
   		return map;
   	}
    
  	//同意代接
    @RequestMapping(value="agreeDaijie",method=RequestMethod.POST)
   	public  synchronized @ResponseBody Map<String,Object> agreeDaijie(String token,Integer daijieId)  {
       	Map<String ,Object>  map=workerService.agreeDaijie( token,daijieId );
   		return map;
   	}
    //联系人 电话号码
    @RequestMapping("linkManParent")
   	public  @ResponseBody Map<String,Object> linkManParent(String token)  {
       	Map<String ,Object>  map=workerService.linkManParent( token );
   		return map;
   	}
    
    //联系人 电话号码
    @RequestMapping("linkManWorker")
   	public  @ResponseBody Map<String,Object> linkManWorker(String token )  {
       	Map<String ,Object>  map=workerService.linkManWorker( token );
   		return map;
   	}
    
  //这个老师的信息
    @RequestMapping("teacher")
   	public  @ResponseBody Map<String,Object> teacher(String token)  {
       	Map<String ,Object>  map=workerService.teacher( token );
   		return map;
   	}
    
    //这个老师的某一天的请假条
    @RequestMapping("workerLeaveLog")
   	public  @ResponseBody Map<String,Object> workerLeaveLog(String token,Long time) throws ParseException  {
       	Map<String ,Object>  map=workerService.workerLeaveLog( token,time );
   		return map;
   	}
    
    //这个老师去请假
    @RequestMapping(value="workerToLeave",method=RequestMethod.POST)
   	public  synchronized @ResponseBody Map<String,Object> workerToLeave(String token,String reason,Long startTime,Long endTime) throws ParseException  {
       	Map<String ,Object>  map=workerService.workerToLeave( token,reason,startTime,endTime );
   		return map;
   	}

    
    //这个老师的红花
    @RequestMapping("flower")
   	public  @ResponseBody Map<String,Object> flower(String token,Integer pageNo)  {
       	Map<String ,Object>  map=workerService.flower( token ,pageNo);
   		return map;
   	}
    
    //修改这个老师的信息[除了头像]
    @RequestMapping(value="updateTeacher",method=RequestMethod.POST)
   	public synchronized  @ResponseBody Map<String,Object> updateTeacher(String token,String workerName,Integer sex,Integer age)  {
    	Map<String ,Object>  map=workerService.updateTeacher(  token, workerName, sex, age );
   		return map;
   	}
    
    //修改老师[头像]
    @RequestMapping(value="updateTeacherHead",method=RequestMethod.POST)
   	public synchronized  @ResponseBody Map<String,Object> updateTeacher(HttpServletRequest request,HttpSession session,String token)  {
    	Map<String ,Object>  map=workerService.updateTeacherHead(  request,session,token );
   		return map;
   	}
    
    
    
    
    //--------------------------------------------------------------
    //考勤管理
    
  //宝宝晨检 考勤共用这一个接口
  	@RequestMapping("babyCheck")
  	@ResponseBody
  	public Map<String, Object> babyCheck(String token,Long time) throws ParseException{
  		Map<String,Object> result=workerService.babyCheck( token, time);
  		return result;
  	}
  	
  	
	@RequestMapping("photo")
  	@ResponseBody
  	public Map<String, Object> photo(String token,Integer pageNo){
  		Map<String,Object> result=workerService.photo( token,pageNo);
  		return result;
  	}
	//老师发表朋友圈   必定不带babyId [不带图片S]  (state可选 设置是否隐私)
/*	@RequestMapping(value="publishPhoto",method=RequestMethod.POST)
  	@ResponseBody
  	public Map<String, Object> publish(String token,String content){
  		Map<String,Object> result=workerService.publish(  token, content);
  		return result;
  	}*/
	
	//老师发表朋友圈   必定不带babyId [图片S]  (state可选 设置是否隐私
		@RequestMapping(value="publishPhotoImg",method=RequestMethod.POST)
	  	@ResponseBody
	  	public synchronized Map<String, Object> publishImg(HttpServletRequest request,HttpSession session,String token,String content ){
	  		Map<String,Object> result=workerService.publishImg(  request, session, token,content);
	  		return result;
	  	}
		
	
	//点击点赞 或取消点赞
	@RequestMapping(value="dianzan")
  	@ResponseBody
  	public synchronized Map<String, Object> dianzan(String token,Integer infoId){
  		Map<String,Object> result=workerService.dianzan( token,infoId);
  		return result;
  	}
	//评论 朋友圈   infoId必填 ( commentId可选 commentName可选   并列)
	@RequestMapping(value="comment")
  	@ResponseBody
  	public synchronized Map<String, Object> comment(String token,Integer infoId,Integer replyCommentId,String replyName,String commentContent){
  		Map<String,Object> result=workerService.comment( token,infoId,replyCommentId,replyName,commentContent);
  		return result;
  	}
   
	//获取可以@的人
   	@RequestMapping("replyPerson")
     	@ResponseBody
     	public Map<String, Object> replyPerson(String token,Integer infoId){
     		Map<String,Object> result=workerService.replyPerson( token,infoId);
     		return result;
     	}
    
    
    
    
    //--------------------------------------------------------------
    
  	 
    @RequestMapping("yichang")
   	public  @ResponseBody Map<String,Object> yichang(String token,Long time) throws ParseException  {
       	Map<String ,Object>  map=workerService.yichang( token,time );
   		return map;
   	}
    
   
    
  	@RequestMapping(value="yichangAgree")
   	public  @ResponseBody Map<String,Object> yichangAgree(Integer unusualId) throws ParseException  {
       	Map<String ,Object>  map=workerService.yichangAgree( unusualId );
   		return map;
   	}
  	//宝宝某天的请假
  	@RequestMapping("leaveLog")
   	public  @ResponseBody Map<String,Object> leaveLog(String token,Long time) throws ParseException  {
       	Map<String ,Object>  map=workerService.leaveLog( token,time );
   		return map;
   	}
  	//同意 或不同意孩子请假   type 0:同意 1：不同意
  	@RequestMapping(value="agreeLeaveLog",method=RequestMethod.POST)
   	public  synchronized @ResponseBody Map<String,Object> agreeLeaveLog(Integer leaveId,Integer type) throws ParseException  {
       	Map<String ,Object>  map=workerService.agreeLeaveLog( leaveId,type );
   		return map;
   	}
  	
  //视频
  	@RequestMapping(value="video")
   	public  @ResponseBody Map<String,Object> video(String token) throws ParseException  {
       	Map<String ,Object>  map=workerService.video( token);
   		return map;
   	}

    
    //插入宝宝的[签到,表现]5年空记录    宝宝表现表
    @RequestMapping("insertBybyCheck")
   	public  synchronized @ResponseBody Map<String,Object> insertBybyCheck(Integer babyId,Integer gartenId) throws ParseException  {
       	workerService.insertBybyCheck( babyId, gartenId);
   		return null;
   	}
    
    //插入老师的[签到]5年空记录    宝宝表现表
    @RequestMapping("insertWorkerCheck")
   	public synchronized  @ResponseBody Map<String,Object> insertWorkerCheck(Integer workerId,Integer gartenId) throws ParseException  {
       	workerService.insertWorkerCheck( workerId, gartenId);
   		return null;
   	}
    //-------------------------------------------------------------------------环信
    //获得token
/*    Bearer YWMt0uDcoHDZEeetCJHnv6YFggAAAAAAAAAAAAAAAAAAAAFXRA_AR1UR57_rsZ6SJXDTAgMAAAFdd2RWAQBPGgCzLMdH48bQ6ENYBBXCADB2da02PRu0qVdTkgoYM3TyLw
*/    @RequestMapping("hxGetToken")
   	public  @ResponseBody Map<String,Object> hxGetToken() throws ParseException, IOException  {
    	Map<String, Object> result=workerService.hxGetToken();
   		return result;
   	}
    
	//向IM 用户添加好友
	@RequestMapping("addFriend")
	public  @ResponseBody void addFriend(String authorization,Integer type,Integer id) throws ParseException, IOException  {
		workerService.addFriend(authorization,type,id);
	}


	//注册 IM 用户   老师  老师的ID
	@RequestMapping("regist")
	public  @ResponseBody void regist(String authorization,Integer type,Integer id) throws ParseException, IOException  {
		workerService.regist( authorization,type,id);
	}
    
    /*//注册 IM 用户   老师  老师的ID
    @RequestMapping("regist")
   	public  @ResponseBody Map<String, Object> regist(String authorization,Integer type,String token) throws ParseException, IOException  {
    	Map<String, Object> result=workerService.regist( authorization,type,token);
		return result;
   	}
    
  // 向IM 用户添加好友
    @RequestMapping("addFriend")
   	public  @ResponseBody Map<String, Object> addFriend(String authorization,Integer type,String token) throws ParseException, IOException  {
    	Map<String, Object> result=workerService.addFriend(authorization,type,token);
		return result;
   	}*/
    
 //   查询离线消息数
    
    @RequestMapping("findLeaveMessage")
   	public  @ResponseBody Map<String, Object> findLeaveMessage(String authorization,Integer type,String token) throws ParseException, IOException  {
    	Map<String, Object> result=workerService.findLeaveMessage(authorization,type,token);
		return result;
   	}
    
//  下载历史消息文件
   @RequestMapping("findChat")
  	public synchronized  @ResponseBody Map<String, Object> findChat(String authorization,Integer type,String token) throws ParseException, IOException  {
   	Map<String, Object> result=workerService.findChat(authorization,type,token);
		return result;
  	}
   
   
// 发送消息
  @RequestMapping("sendMessage")
 	public synchronized  @ResponseBody Map<String, Object> sendMessage(String authorization,Integer type,String token,String message,Integer toType,String toToken) throws ParseException, IOException  {
  	Map<String, Object> result=workerService.sendMessage(authorization,type,token,message,toType,toToken);
		return result;
 	}
  
  
//解压GZ 文件readMessage()  读取解压后的文件readFileByLines()
 @RequestMapping("test")
	public  @ResponseBody Map<String, Object> readMessage() throws Exception  {
 	Map<String, Object> result=workerService.readFileByLines();
		return result;
	}
 
//构建简介 活动页面
@RequestMapping("testHtml")
	public  @ResponseBody Map<String, Object> getMyHtml() throws Exception  {
	Map<String, Object> result=workerService.getMyHtml();
		return result;
	}
	//删除活动
	@RequestMapping("deleteActivity")
		public  synchronized @ResponseBody Map<String, Object> deleteActivity(Integer activityId) throws Exception  {
		Map<String, Object> result=workerService.deleteActivity(  activityId);
			return result;
		}
	//构建简介 
	@RequestMapping("htmlIntroduce")
		public   synchronized @ResponseBody Map<String, Object> htmlIntroduce(String webStr,String token) throws Exception  {
		Map<String, Object> result=workerService.htmlIntroduce( webStr,token);
			return result;
		}

	// 活动页面
	@RequestMapping("htmlActivity")
		public   synchronized @ResponseBody Map<String, Object> htmlActivity(String webStr,String token,Long timeStart,Long timeEnd ,String imgWaibu,String content,String activityAddress,String title,Long joinTime) throws Exception  {
		Map<String, Object> result=workerService.htmlActivity(  webStr, token, timeStart, timeEnd , imgWaibu, content, activityAddress, title, joinTime);
			return result;
		}
	//测试
	@RequestMapping("ceshi")
		public  @ResponseBody Map<String, Object> ceshi() throws Exception  {
		Map<String, Object> result=workerService.ceshi2(  );
			return result;
		}
	

}


