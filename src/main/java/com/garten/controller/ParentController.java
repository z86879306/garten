package com.garten.controller;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.garten.model.baby.BabyInfo;
import com.garten.model.parent.ParentInfo;
import com.garten.service.ParentService;
import com.garten.util.myutil.MyUtil;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;





@Controller
@RequestMapping("parent")
public class ParentController {
	@Autowired
	private ParentService parentService;
	//登陆
	@RequestMapping(value="login",method=RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String, Object> login(String phoneNumber,String pwd){
		Map<String,Object> result=parentService.login( phoneNumber, pwd);
		return result;
	}
	
	
	@RequestMapping(value="updateLogin",method=RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String, Object> updateLogin(String phoneNumber,String pwd,String number) throws ParseException{
		Map<String,Object> result=parentService.updateLogin( phoneNumber, pwd,number);
		return result;
	}
	
	//首页
	@RequestMapping("index")
	@ResponseBody
	public Map<String, Object> index(String token){
		Map<String,Object> result=parentService.index( token);
		return result;
	}
	
	@RequestMapping("circle")
	@ResponseBody
	public Map<String, Object> recipeCircle(String token,Integer type,Integer babyId) throws ParseException, InterruptedException{
		Map<String,Object> result=parentService.circle( token,type,babyId);
		return result;
	}
	
	
	//视频
  	@RequestMapping(value="video")
   	public  @ResponseBody synchronized Map<String,Object> video(String token,Integer babyId) throws ParseException  {
       	Map<String ,Object>  map=parentService.video( token,babyId);
   		return map;
   	}
  	
  //版本更新
    @RequestMapping("version")
   	public  @ResponseBody Map<String,Object> version(String token)  {
       	Map<String ,Object>  map=parentService.version( token );
   		return map;
   	}
    
  	
	//宝宝晨检 考勤共用这一个接口
  	@RequestMapping("babyCheck")
  	@ResponseBody
  	public  synchronized Map<String, Object> babyCheck(String token,Long time,Integer babyId) throws ParseException{
  		Map<String,Object> result=parentService.babyCheck( token, time,babyId);
  		return result;
  	}
  	
  //宝宝晨检 考勤共用这一个接口
  	@RequestMapping("getHuanxin")
  	@ResponseBody
  	public Map<String, Object> getHuanxin(String token) throws ParseException{
  		Map<String,Object> result=parentService.getHuanxin( token);
  		return result;
  	}
  	
  //晨检 家长备注
  	@RequestMapping(value="remarkCheck",method=RequestMethod.POST)
  	@ResponseBody
  	public synchronized Map<String, Object> remarkCheck(String token,Integer checkId,String remark){
  		Map<String,Object> result=parentService.remarkCheck( token, checkId,remark);
  		return result;
  	}
  	//通知 type-0:系统通知 1:异常 2：重要信息
  	@RequestMapping("notifiedCenter")
	@ResponseBody
	public Map<String, Object> notifiedCenter(String token,String type,Integer pageNo){
		Map<String,Object> result=parentService.notifiedCenter( token,type,pageNo);
		return result;
	}
  	
  	//宝宝某天异常
  	 @RequestMapping("yichang")
    	public  @ResponseBody Map<String,Object> yichang(String token,Long time,Integer babyId) throws ParseException  {
        	Map<String ,Object>  map=parentService.yichang( token,time ,babyId);
    		return map;
    	}
  	 
  	 /**查询这个宝宝的哪一天代接   哪个宝宝id
  	  * [大改 内部 mapper]
  	  */
     @RequestMapping("daijie")
    	public  @ResponseBody Map<String,Object> daijie(String token,Integer babyId,Long time) throws ParseException  {
        	Map<String ,Object>  map=parentService.daijie( token,babyId,time);
    		return map;
    	}
     
   //宝宝哪一天的代接   删除这条代接
     @RequestMapping(value="deleteDaijie",method=RequestMethod.POST)
    	public synchronized  @ResponseBody Map<String,Object> deleteDaijie(String token,Integer daijieId) throws ParseException  {
        	Map<String ,Object>  map=parentService.deleteDaijie( token,daijieId);
    		return map;
    	}
     
     //给当前宝宝申请当天代接   
     @RequestMapping(value="createDaijie",method=RequestMethod.POST)
    	public synchronized  @ResponseBody Map<String,Object> createDaijie(HttpServletRequest request,HttpSession session,String token, String daijieName,String relation,String realPhoneNumber,Long arriveTime,Integer babyId) throws ParseException  {
        	Map<String ,Object>  map=parentService.createDaijie( request, session,  token,  daijieName, relation, realPhoneNumber,arriveTime,babyId );
    		return map;
    	}
     
     //当前宝宝某天的课程 
     @RequestMapping("lesson")
    	public  @ResponseBody Map<String,Object> lesson(String token, Integer babyId,Long time) throws ParseException  {
        	Map<String ,Object>  map=parentService.lesson(  token, babyId,time );
    		return map;
    	}
     
     //当前宝宝某天的食谱 
     @RequestMapping("recipe")
    	public  @ResponseBody Map<String,Object> recipe(String token, Integer babyId,Long time) throws ParseException  {
        	Map<String ,Object>  map=parentService.recipe(  token, babyId,time );
    		return map;
    	}
     
   //当前宝宝某天的表现
     @RequestMapping("performance")
    	public  @ResponseBody Map<String,Object> performance(String token, Integer babyId,Long time) throws ParseException  {
        	Map<String ,Object>  map=parentService.performance(  token, babyId,time );
    		return map;
    	}
     /**这个宝宝的幼儿园
      * 大改 内部 mapper
     * @throws APIRequestException 
     * @throws APIConnectionException 
      */
     @RequestMapping("introduceActivity")
    	public  @ResponseBody Map<String,Object> introduceActivity(String token,Integer babyId) throws APIConnectionException, APIRequestException  {
        	Map<String ,Object>  map=parentService.introduceActivity( token,babyId );
    		return map;
    	}
     
     //这个活动
     @RequestMapping("activity")
    	public  @ResponseBody Map<String,Object> activity(String token,Integer babyId)  {
        	Map<String ,Object>  map=parentService.activity( token,babyId );
    		return map;
    	}
     
     
     //这个宝宝活动报名
     @RequestMapping(value="joinActivity",method=RequestMethod.POST)
    	public synchronized  @ResponseBody Map<String,Object> joinActivity(String token,Integer babyId,String parent,String phoneNumber,Integer activityId)  {
        	Map<String ,Object>  map=parentService.joinActivity( token,babyId,parent,phoneNumber,activityId );
    		return map;
    	}
     
     /**联系人 电话号码
  	 * [大改 内部 classId  mapper]
  	 */
  @RequestMapping("linkManParent")
 	public  @ResponseBody Map<String,Object> linkManParent(String token,Integer babyId)  {
     	Map<String ,Object>  map=parentService.linkManParent( token,babyId );
 		return map;
 	}
     
  /**联系人 电话号码
	 * [大改 内部 classId  mapper]
	 */
   @RequestMapping("linkManWorker")
  	public  @ResponseBody Map<String,Object> linkManWorker(String token,Integer babyId)  {
      	Map<String ,Object>  map=parentService.linkManWorker( token,babyId );
  		return map;
  	}
     
   //送花
     @RequestMapping(value="flower",method=RequestMethod.POST)
    	public synchronized  @ResponseBody Map<String,Object> flower(String token,Integer workerId)  {
        	Map<String ,Object>  map=parentService.flower( token,workerId);
    		return map;
    	}
     
   //查看一个孩子的信息
     @RequestMapping("baby")
    	public  @ResponseBody Map<String,Object> baby(String token,Integer babyId)  {
        	Map<String ,Object>  map=parentService.baby( token,babyId);
    		return map;
    	}
     
     //修改一个孩子的信息  token, babyId, height, weight, allergy, health, hobby, specialty
     @RequestMapping(value="updateBaby",method=RequestMethod.POST)
    	public synchronized  @ResponseBody Map<String,Object> updateBaby(String token ,BabyInfo baby)  {
        	Map<String ,Object>  map=parentService.updateBaby( token,baby);
    		return map;
    	}
     
     //修改一个孩子的头像
     @RequestMapping(value="updateBabyHead",method=RequestMethod.POST)
    	public synchronized  @ResponseBody Map<String,Object> updateBabyHead(HttpServletRequest request,HttpSession session,String token,Integer babyId)  {
        	Map<String ,Object>  map=parentService.updateBabyHead( request,session,token,babyId);
    		return map;
    	}
     
   //一个宝宝某一天的请假
     @RequestMapping("babyLeave")
    	public  @ResponseBody Map<String,Object> babyLeave(String token,Integer babyId,Long time) throws ParseException  {
        	Map<String ,Object>  map=parentService.babyLeave( token,babyId,time);
    		return map;
    	}
     
   //宝宝申请请假
     @RequestMapping(value="tobabyLeave")
    	public synchronized  @ResponseBody Map<String,Object> tobabyLeave(String token,Integer babyId,Long start,Long end,String reason) throws ParseException  {
        	Map<String ,Object>  map=parentService.tobabyLeave( token,babyId,start,end,reason);
    		return map;
    	}
     
     //家长反馈
       @RequestMapping(value="feedback",method=RequestMethod.POST)
      	public synchronized  @ResponseBody Map<String,Object> feedback(String token,String content,Integer babyId) throws ParseException  {
          	Map<String ,Object>  map=parentService.feedback( token,content,babyId);
      		return map;
      	}
       //---------------------------------------------------------------
       @RequestMapping("photo")
     	@ResponseBody
     	public Map<String, Object> photo(String token,Integer babyId,Integer pageNo){
     		Map<String,Object> result=parentService.photo( token,babyId,pageNo);
     		return result;
     	}
   	//家长发表了朋友圈[除了图片S]     (state可选 设置是否隐私
   	@RequestMapping(value="publishPhoto")
     	@ResponseBody
     	public synchronized Map<String, Object> publish(String token,String content,Integer babyId ){
     		Map<String,Object> result=parentService.publish(  token, content,babyId);
     		return result;
     	}
   	
  //家长发表了朋友圈[图片S]     (state可选 设置是否隐私
   	@RequestMapping(value="publishPhotoImg")
     	@ResponseBody
     	public synchronized Map<String, Object> publishImg(HttpServletRequest request,HttpSession session,String token,Integer babyId,String content ){
     		Map<String,Object> result=parentService.publishImg( request,session, token,babyId,content);
     		return result;
     	}
   	//点击点赞 或取消点赞
   	@RequestMapping(value="dianzan")
     	@ResponseBody
     	public synchronized Map<String, Object> dianzan(String token,Integer infoId,Integer babyId){
     		Map<String,Object> result=parentService.dianzan( token,infoId,babyId);
     		return result;
     	}
   	//评论 朋友圈   infoId必填 ( commentId可选 commentName可选   并列)
   	@RequestMapping(value="comment")
     	@ResponseBody
     	public synchronized  Map<String, Object> comment(String token,Integer infoId,Integer replyCommentId,String replyName,String commentContent,Integer babyId){
     		Map<String,Object> result=parentService.comment( token,infoId,replyCommentId,replyName,commentContent,babyId);
     		return result;
     	}
   	
	//获取可以@的人
   	@RequestMapping("replyPerson")
     	@ResponseBody
     	public Map<String, Object> replyPerson(String token,Integer infoId){
     		Map<String,Object> result=parentService.replyPerson( token,infoId);
     		return result;
     	}
   	
   	
   	//获取价格
   	@RequestMapping("getPrice")
 	@ResponseBody//type 0视频 1考勤 2全部
 	public Map<String, Object> getPrice(String token,Integer babyId,Integer type,Integer month){
 		Map<String,Object> result=parentService.getPrice( token,babyId, type,month);
 		return result;
 	}
   	
   	//支付宝支付
	@RequestMapping(value="alipay")
	public  synchronized @ResponseBody Map<String, Object> alipay(String token,Integer type,Integer monthCount,Integer babyId ) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
    	Map<String, Object> map = parentService.alipay(token,type,monthCount,babyId);
		return map;
	}
	//支付宝验证
   	@RequestMapping(value="alipayyz")
	public  @ResponseBody String alipayyz() throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
    	String map = parentService.alipayyz();
		return map;
	}
   	
	@RequestMapping(value="test")
	public  @ResponseBody String test() throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
    	 parentService.test();
		return null;
	}
  
			
	//微信支付  
   	@RequestMapping(value="wxpay")//0视频 1考勤 2视频+考勤
	public  synchronized @ResponseBody Map<String, Object> myWxinpay(String token,Integer type,Integer monthCount,Integer babyId) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
    	Map<String, Object> map = parentService.myWxinpay( token, type, monthCount, babyId);
		return map;
	}
  //微信验证
	@RequestMapping(value="wxpayyz")
	public  @ResponseBody String  wxpayyz(HttpServletRequest request,HttpServletResponse response) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, AlipayApiException, APIConnectionException, APIRequestException, IOException, JDOMException {
    	 parentService.wxpayyz( request, response);
		return "success";
	}
     
     
}
