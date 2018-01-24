package com.garten.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alipay.api.AlipayApiException;
import com.garten.model.garten.GartenInfo;
import com.garten.service.BigcontrolService;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import io.netty.handler.codec.http.HttpResponse;

@Controller
@RequestMapping("bigcontrol")
public class BigcontrolController {
	@Autowired
	private BigcontrolService bigcontrolService;
	
	@RequestMapping(value="login")
	@ResponseBody
	public synchronized Map<String, Object> login(String phoneNumber,String pwd){
		Map<String,Object> result=bigcontrolService.login( phoneNumber, pwd);
		return result;
	}
	 
	//发送验证码 在worker里面 sendControl
	@RequestMapping("updateLogin")//修改密码
	public  synchronized @ResponseBody Map<String,Object> updateLogin(String phoneNumber,String pwd,String number ) throws ParseException {
		Map<String,Object> map=bigcontrolService.updateLogin( phoneNumber, pwd, number);
		return map;
	}
	
	//首页 -统计-新增统计
		@RequestMapping("addtongji")//
		public  @ResponseBody Map<String,Object> addtongji(String token,Long start ,Long end ) throws ParseException {
			Map<String,Object> map=bigcontrolService.addtongji( token, start,end);
			return map;
		}
		
		
	//首页 -统计-新增详情   可选:province省
	@RequestMapping("adddetail")
	public  @ResponseBody Map<String,Object> adddetail(String token,Long start,Long end,String province,String city,String countries,String job,Integer pageNo,Integer gartenId ) throws ParseException {
		Map<String,Object> map=bigcontrolService.adddetail( token, start,end,province,city,countries,job,pageNo,gartenId);
		return map;
	}
		
     
	//首页 -统计-删除统计
	@RequestMapping("deletetongji")//
	public  @ResponseBody Map<String,Object> deletetongji(String token,Long start,Long end ) throws ParseException {
		Map<String,Object> map=bigcontrolService.deletetongji( token, start,end);
		return map;
	}
	
			
	//首页 -统计-删除详情   可选:province省
	@RequestMapping("deletedetail")
	public  @ResponseBody Map<String,Object> deletedetail(String token,Long start,Long end,String province,String city,String countries,String job,Integer pageNo,Integer gartenId ) throws ParseException {
		Map<String,Object> map=bigcontrolService.deletedetail( token, start,end,province,city,countries,job,pageNo, gartenId );
		return map;
	}
	
	//首页 -统计-晨检统计 
	@RequestMapping("checktongji")//
	public  @ResponseBody Map<String,Object> checktongji(String token,Long time ) throws ParseException {
		Map<String,Object> map=bigcontrolService.checktongji( token, time);
		return map;
	}
	
	//首页 -统计-打卡统计  (目前只统计宝宝点卡)
		@RequestMapping("dakatongji")//
		public  @ResponseBody Map<String,Object> dakatongji(String token,Long time ) throws ParseException {
			Map<String,Object> map=bigcontrolService.dakatongji( token, time);
			return map;
		}
		
		
	//------------------------------------------信息管理	
		
		//首页 -信息管理-家长信息管理  可选:除了token都可选
	@RequestMapping("parentMessage")//
	public  @ResponseBody Map<String,Object> parentMessage(String token,String name,String phoneNumber,String province,String city,String countries ,Integer pageNo,Integer gartenId,Integer monitorState,Integer attendanceState) throws ParseException {
		Map<String,Object> map=bigcontrolService.parentMessage( token, name,phoneNumber,province,city,countries,pageNo,gartenId,monitorState,attendanceState);
		return map;
	}
	
	//导出家长信息
	@RequestMapping("exportParent")
	@ResponseBody
	public Map<String,Object> exportParent(String name,String phoneNumber,String province,String city,String countries ,Integer gartenId,Integer monitorState,Integer attendanceState,HttpServletResponse response) throws Exception{
		bigcontrolService.exportParent(name, phoneNumber, province, city, countries, gartenId, monitorState, attendanceState, response);
		return null;
	}
		
		//首页 -信息管理-宝宝信息管理  可选:除了token都可选
	@RequestMapping("babyMessage")//
	public  @ResponseBody Map<String,Object> babyMessage(String token,String name ,String province,String city,String countries,Integer pageNo,Integer gartenId,Integer gradeId,Integer classId ) throws ParseException {
		Map<String,Object> map=bigcontrolService.babyMessage( token, name,province,city,countries,pageNo, gartenId,gradeId,classId);
		return map;
	}
	
	@RequestMapping("exportBaby")
	@ResponseBody
	public  Map<String,Object> exportBaby(String name ,String province,String city,String countries,Integer gartenId,Integer gradeId,Integer classId,HttpServletResponse response) throws IOException{
		bigcontrolService.exportBaby(name, province, city, countries, gartenId, gradeId, classId, response);
		return null;
	}
	//首页 -信息管理-老师信息管理  可选:除了token都可选
	@RequestMapping("workerMessage")//
	public  @ResponseBody Map<String,Object> workerMessage(String token,String name ,String province,String city,String countries,String phoneNumber,Integer  pageNo,Integer gartenId) throws ParseException {
		Map<String,Object> map=bigcontrolService.workerMessage( token, name,province,city,countries,phoneNumber,pageNo,gartenId);
		return map;
	}
	
	@RequestMapping("exportWorker")
	@ResponseBody
	public  Map<String,Object> exportWorker(String name ,String province,String city,String countries,String phoneNumber,Integer gartenId,HttpServletResponse response) throws IOException{
		bigcontrolService.exportWorker(name, province, city, countries, phoneNumber, gartenId, response);
		return null;
	}
	//首页 -信息管理-幼儿园信息管理
	@RequestMapping("gartenMessage")//
	public  @ResponseBody Map<String,Object> gartenMessage(String token,String name ,String province,String city,String countries,String phoneNumber,Integer  pageNo,Integer gartenId,Integer monitorState,Integer attendanceState ) throws ParseException {
		Map<String,Object> map=bigcontrolService.gartenMessage( token, name,province,city,countries,phoneNumber,pageNo,gartenId,monitorState,attendanceState);
		return map;
	}
	
	//幼儿园信息导出
	@RequestMapping("exportGarten")
	@ResponseBody
	public void exportGarten(String token,String name ,String province,String city,String countries,String phoneNumber,Integer gartenId,HttpServletResponse response){
		bigcontrolService.exportGarten(token, name, province, city, countries, phoneNumber, gartenId, response);
	}
	//修改幼儿园信息
	@RequestMapping("updateGarten")//
	//修改幼儿园信息  操作记录
	public synchronized  @ResponseBody Map<String,Object> updateGarten(String token,Integer gartenId,String gartenName ,String name,String phoneNumber,String contractNumber,Long  contractStart,Long  contractEnd,String province,String city,String countries ,String address,Integer accountState,BigDecimal charge,Long attendanceTime,Long monitorTime) throws ParseException {
		Map<String,Object> map=bigcontrolService.updateGarten(  token, gartenId, gartenName , name, phoneNumber, contractNumber,  contractStart,  contractEnd, province, city, countries , address, accountState, charge, attendanceTime, monitorTime);
		return map;
	}
	@RequestMapping("accountGarten")//冻结 解冻  操作记录
	public synchronized  @ResponseBody Map<String,Object> accountGarten(String token,Integer gartenId,Integer accountState) throws ParseException {
		Map<String,Object> map=bigcontrolService.accountGarten(  token, gartenId,accountState);
		return map;
	}
	
	
	
	@RequestMapping("getGarten")//
	public  @ResponseBody Map<String,Object> getGarten(String token,String province,String city,String countries ) throws ParseException {
		Map<String,Object> map=bigcontrolService.getGarten( token, province, city, countries);
		return map;
	}
	
	@RequestMapping("getGartenGrade")//
	public  @ResponseBody Map<String,Object> getGartenGrade(Integer gartenId ) throws ParseException {
		Map<String,Object> map=bigcontrolService.getGartenGrade( gartenId);
		return map;
	}
	//开园审核
	@RequestMapping("agentAudit")//
	public  @ResponseBody Map<String,Object> agentAudit(String token ,Integer resource,Integer pageNo) throws ParseException {
		Map<String,Object> map=bigcontrolService.agentAudit( token,resource,pageNo);
		return map;
	}
	//通过开园审核	操作记录
		@RequestMapping("agreeAgentAudit")//
		public synchronized  @ResponseBody Map<String,Object> agreeAgentAudit(String token ,Integer auditId,Integer gartenGrade,Long attendanceTime,Long monitorTime,Long contractStart,Long contractEnd) throws ParseException, IOException {
			Map<String,Object> map=bigcontrolService.agreeAgentAudit(  token , auditId, gartenGrade, attendanceTime, monitorTime, contractStart, contractEnd);
			return map;
		}
	//不通过开园申请
		@RequestMapping("refuseAgentAudit")
		public synchronized  @ResponseBody Map<String,Object> refuseAgentAudit(String token,Integer auditId,String reason){
			Map<String, Object> map = bigcontrolService.refuseAgentAudit(token,auditId, reason);
			return map;
		}
//增加忽略时间(手动)	 准备删除
	/*	@RequestMapping("ignore")//
		public synchronized  @ResponseBody void ignore(String token,Integer gartenId) throws ParseException, IOException {
			bigcontrolService.ignore( token,gartenId);
		}*/
		
		//首页-幼儿园管理——添加幼儿园
				@RequestMapping("addGarten")//
				public synchronized  @ResponseBody Map<String,Object> addGarten(String token  ,Integer gartenGrade,Long attendanceTime,Long monitorTime,Long contractStart,Long contractEnd,String jobcall,String phoneNumber,
						String gartenName,String name,String contractNumber,String province,
						String city,String countries,Integer gartenType) throws ParseException, IOException {
					Map<String,Object> map=bigcontrolService.addGarten( token  , gartenGrade, attendanceTime, monitorTime, contractStart, contractEnd,  jobcall, phoneNumber,
							 gartenName, name, contractNumber, province,
							 city, countries,gartenType);
					return map;
				}
				
				@RequestMapping("test1")//
				public  @ResponseBody String test1() throws ParseException {
					bigcontrolService.test1( );
					return "success";
				}
		
		//删除幼儿园
		@RequestMapping("deleteGarten")
		@ResponseBody
		public synchronized Map<String,Object> deleteGarten(String token,Integer gartenId){
			Map<String, Object> map = bigcontrolService.deleteGarten(token, gartenId);
			return map;
		}
//代理商管理-代理商管理
		@RequestMapping("agentMessge")//
		public  @ResponseBody Map<String,Object> agentMessge(String token,String province,String city,String countries,String agentId ,Integer pageNo) throws ParseException {
			Map<String,Object> map=bigcontrolService.agentMessge( token, province, city, countries, agentId,pageNo);
			return map;
		}

		
		
//找到这个范围的代理商名称
		@RequestMapping("getAgentName")//
		public  @ResponseBody Map<String,Object> getAgentName(String token,String province,String city,String countries  ) throws ParseException {
			Map<String,Object> map=bigcontrolService.getAgentName( token, province, city, countries);
			return map;
		}
		
		//代理商管理-代理商管理 [添加]
		@RequestMapping("addAgentMessge")//
		public synchronized  @ResponseBody Map<String,Object> addAgentMessge(String token,String phoneNumber,
				BigDecimal  agentMoney,BigDecimal creditMoney,Long agentStartTime,Long agentEndTime
				,String name,String agentName,Integer rebate,String province,String city,String countries
												) throws ParseException {
			Map<String,Object> map=bigcontrolService.addAgentMessge(  token, phoneNumber, 
					  agentMoney, creditMoney, agentStartTime, agentEndTime
					, name, agentName, rebate, province, city, countries
													);
			return map;
		}
				
//代理商管理-代理商管理 [删除]
		@RequestMapping("deleteAgentMessge")//
		public synchronized  @ResponseBody Map<String,Object> deleteAgentMessge(String token,Integer agentId ) throws ParseException {
			Map<String,Object> map=bigcontrolService.deleteAgentMessge( token,agentId);
			return map;
		}
		
	//代理商管理-代理商管理 [冻结]
		@RequestMapping("frostAgentMessge")//
		public synchronized  @ResponseBody Map<String,Object> frostAgentMessge(String token,Integer agentId ) throws ParseException {
			Map<String,Object> map=bigcontrolService.frostAgentMessge( token,agentId);
			return map;
		}
		//代理商管理-代理商管理 [解冻]
		@RequestMapping("unfrostAgentMessge")//
		public synchronized  @ResponseBody Map<String,Object> unfrostAgentMessge(String token,Integer agentId ) throws ParseException {
			Map<String,Object> map=bigcontrolService.unfrostAgentMessge( token,agentId);
			return map;
		}
		//代理商管理-代理商管理 [修改]
		@RequestMapping("updateAgentMessge")//
		public synchronized  @ResponseBody Map<String,Object> updateAgentMessge(String token,Integer agentId,String agentName,String phoneNumber,String province,String city,String countries,String cardFragment,
				String creditMoney,String agentMoney ,Integer rebate) throws ParseException {
			Map<String,Object> map=bigcontrolService.updateAgentMessge(  token, agentId, agentName, phoneNumber, province, city, countries, cardFragment,creditMoney,agentMoney,rebate);
			return map;
		}
		/*//代理商财务管理修改	准备删除
		@RequestMapping("updateAgentFinance")
		@ResponseBody
		public synchronized Map<String,Object> updateAgentFinance(String token, Integer agentId,String creditMoney,String agentMoney ,Integer rebate){
			Map<String, Object> map = bigcontrolService.updateAgentFinance(token, agentId, creditMoney, agentMoney, rebate);
			return map;
			
		}*/
		
		//代理商管理-代理商业绩统计    (暂时记录代理商签单的幼儿园)
		@RequestMapping("agentPerformance")//
		public  @ResponseBody Map<String,Object> agentPerformance(String token,String agentId,String province,String city,String countries,Integer state,Integer pageNo ) throws ParseException {
			Map<String,Object> map=bigcontrolService.agentPerformance(  token, agentId , province, city, countries, state,pageNo );
			return map;
		}

		@RequestMapping("gartenCharge")//费用设置-费用列表
		public  @ResponseBody Map<String,Object> gartenCharge(String token,String gartenId,String province,String city,String countries,Integer pageNo,Integer type ) throws ParseException {
			Map<String,Object> map=bigcontrolService.gartenCharge(  token, gartenId , province, city, countries,pageNo,type );
			return map;
		}
		@RequestMapping("addGartenCharge")//费用设置-添加费用
		public synchronized  @ResponseBody Map<String,Object> addGartenCharge(String token,Integer type,Integer gartenId,String province,String city,String countries,BigDecimal month1,BigDecimal month3,BigDecimal month6,BigDecimal month12) throws ParseException {
			Map<String,Object> map=bigcontrolService.addGartenCharge(  token, type, gartenId, province, city, countries, month1, month3, month6, month12  );
			return map;
		}
		
		@RequestMapping("deleteGartenCharge")//费用设置-添加费用
		public synchronized  @ResponseBody Map<String,Object> deleteGartenCharge(String token,Integer chargeId  ) throws ParseException {
			Map<String,Object> map=bigcontrolService.deleteGartenCharge(  token,chargeId   );
			return map;
		}
		
		@RequestMapping("changeGartenDredge")//改变幼儿园视频考勤开通状态  0视频 1考勤   count0 置0  其他 +count月
		public synchronized  @ResponseBody Map<String,Object> changeGartenDredge(String token, Integer count ,Integer type,Integer gartenId ) throws ParseException {
			Map<String,Object> map=bigcontrolService.changeGartenDredge( token,count,type ,gartenId);
			return map;
		}
		
		@RequestMapping("sendNotified")//发送通知
		public synchronized  @ResponseBody Map<String,Object> sendNotified(String token,Integer[] gartenIds ,Integer type,String title,String info ) throws ParseException, APIConnectionException, APIRequestException {
			Map<String,Object> map=bigcontrolService.sendNotified(  token, gartenIds , type, title, info);
			return map;
		}
		
		@RequestMapping("order")//查看订单
		public  @ResponseBody Map<String,Object> order(String token,Integer pageNo,String province,String city,String countries
				,Integer gartenId,Integer state,String name,String phoneNumber,Integer type,String orderDetail,String babyName,Long startTime,Long endTime) throws ParseException, APIConnectionException, APIRequestException {
			Map<String,Object> map=bigcontrolService.order(  token,pageNo, province, city, countries, gartenId, state, name, phoneNumber, type,orderDetail,babyName,startTime,endTime);
			return map;
		}

		
		//导出订单
		@RequestMapping("exportOrder")
		@ResponseBody
		public synchronized Map<String,Object> exportOrder(String token,String province,String city,String countries
				,Integer gartenId,Integer state,String name,String phoneNumber,Integer type,HttpServletResponse response,String orderDetail,String babyName,Long startTime,Long endTime){
			bigcontrolService.exportOrder(token, province, city, countries, gartenId, state, name, phoneNumber, type, response,orderDetail,babyName,startTime,endTime);
			return null;
		}
		
		@RequestMapping("feedback")//查看反馈
		public  @ResponseBody Map<String,Object> feedback(String token,Integer pageNo) throws ParseException, APIConnectionException, APIRequestException {
			Map<String,Object> map=bigcontrolService.feedback(  token,pageNo);
			return map;
		}
		
		
		//支付宝支付
		@RequestMapping(value="alipay")
		public synchronized  @ResponseBody Map<String, Object> alipay(String token,Integer type,Integer monthCount,Integer gartenId ,Integer parentId,Integer babyId,BigDecimal orderPrice) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, AlipayApiException, APIConnectionException, APIRequestException, IOException {
	    	Map<String, Object> map = bigcontrolService.alipay(token,type,monthCount,gartenId ,parentId,babyId,orderPrice);
			return map;
		}
	   	
	  //支付宝验证
	   	@RequestMapping(value="test")
		public  @ResponseBody String test() throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
	    	String map = bigcontrolService.test();
			return map;
		}
	   	
	   	
	   	////////////////
	   	
	  //终端管理：  考勤机器
  		@RequestMapping("machineList")
  		@ResponseBody											//1为打卡机 2为闸机
  		public Map<String,Object> machineList(String token,Integer type,String province,String city ,String countries,Integer gartenId,Integer pageNo){
  			Map<String, Object> map = bigcontrolService.machineList(token, type, province, city, countries,gartenId,pageNo);
  			return map;
  		}
  		
  		//添加考勤机器
  		@RequestMapping("addMachine")
  		@ResponseBody
  		public synchronized Map<String,Object> addMachine(String token,Integer type,String macId,Integer gartenId){
  			Map<String, Object> map = bigcontrolService.addMachine(token, type, macId, gartenId);
  			return map;
  		}
  		
  		//修改考勤机器信息
  		@RequestMapping("updateMachine")
  		@ResponseBody
  		public synchronized Map<String,Object> updateMachine(String token,Integer machineId,String macId){
  			Map<String, Object> map = bigcontrolService.updateMachine(token, machineId, macId);
  			return map;
  		}
  		
  		//删除考勤机器
  		@RequestMapping("deleteMachine")
  		@ResponseBody
  		public synchronized Map<String,Object> deleteMachine(String token,Integer machineId){
  			Map<String, Object> map = bigcontrolService.deleteMachine(token, machineId);
  			return map;
  		}
  		
  	//打卡摄像头列表
  		@RequestMapping("/getDakaCameraList")
  		@ResponseBody
  		public Map<String,Object> getDakaCameraList(String token,Integer gartenId,String province,String city,String countries,Integer pageNo){
  			
  			Map<String, Object> result = bigcontrolService.getDakaCameraList(token,gartenId, province,city,countries,pageNo);
  			return result;
  		}
  		
  	//直播摄像头列表
  		@RequestMapping("/getLiveCameraList")
  		@ResponseBody
  		public Map<String,Object> getLiveCameraList(String token,Integer gartenId,String province,String city,String countries,Integer pageNo){
  			
  			Map<String, Object> result = bigcontrolService.getLiveCameraList(token,gartenId, province,city,countries,pageNo);
  			return result;
  		}
  		
  	//添加打卡摄像头
  		@RequestMapping("addDakaCamera")
  		@ResponseBody
  		public synchronized Map<String,Object> addDakaCamera(String token,Integer gartenId,String cameraIp,String cameraPort,String cameraUser,String cameraPwd,
  				Integer type,String macId,String deviceSerial,String validateCode) throws UnsupportedEncodingException{
  			Map<String, Object> map = bigcontrolService.addDakaCamera(token, gartenId, cameraIp, cameraPort, cameraUser, cameraPwd, type, macId, deviceSerial, validateCode);
  			return map;
  		}
  		
  	//删除打卡摄像头
  		@RequestMapping("deleteDakaCamera")
  		@ResponseBody
  		public synchronized Map<String,Object> deleteDakaCamera(String token,Integer cameraId){
  			Map<String, Object> map = bigcontrolService.deleteDakaCamera(token, cameraId);
  			return map;
  		}
  		
  	//修改打卡摄像头
  		@RequestMapping("updateDakaCamera")
  		@ResponseBody
  		public synchronized Map<String,Object> updateDakaCamera(String token,Integer cameraId,String cameraIp,String cameraPort,String cameraUser,String cameraPwd,
  				Integer type,String macId){
  			Map<String, Object> map = bigcontrolService.updateDakaCamera(token, cameraId, cameraIp, cameraPort, cameraUser, cameraPwd, type, macId);
  			return map;
  		}
  		
  	//添加直播摄像头
  		@RequestMapping("addLiveCamera")
  		@ResponseBody
  		public synchronized Map<String,Object> addLiveCamera(String token,Integer gartenId,String cameraIp,String cameraPort,String cameraUser,String cameraPwd,
  				String deviceSerial,String validateCode,Integer type,Integer pointId) throws UnsupportedEncodingException{
  			Map<String, Object> map = bigcontrolService.addLiveCamera(token, gartenId, cameraIp, cameraPort, cameraUser, cameraPwd, deviceSerial, validateCode, type, pointId);
  			return map;
  		}
  		
  	//删除直播摄像头
  		@RequestMapping("deleteLiveCamera")
  		@ResponseBody
  		public synchronized Map<String,Object> deleteLiveCamera(String token,Integer cameraId){
  			Map<String, Object> map = bigcontrolService.deleteLiveCamera(token, cameraId);
  			return map;
  		}
  		
  	//修改直播摄像头
  		@RequestMapping("updateLiveCamera")
  		@ResponseBody
  		public synchronized Map<String,Object> updateLiveCamera(String token,Integer cameraId,String cameraIp,String cameraPort,String cameraUser,String cameraPwd,
  				Integer type,Integer pointId,String url){
  			Map<String, Object> map = bigcontrolService.updateLiveCamera(token, cameraId, cameraIp, cameraPort, cameraUser, cameraPwd, type, pointId,url);
  			return map;
  		}
  		
  		//添加录像机  后台添加url 
  		@RequestMapping("addLiveCameraUrl")
  		@ResponseBody
  		public synchronized Map<String,Object> addLiveCameraUrl(String token,Integer gartenId,String cameraIp,String cameraPort,String cameraUser,String cameraPwd,
  				String deviceSerial,String validateCode,Integer type,Integer pointId,String url){
  			Map<String, Object> map = bigcontrolService.addLiveCameraUrl(token, gartenId, cameraIp, cameraPort, cameraUser, cameraPwd, deviceSerial, validateCode, type, pointId,url);
  			return map;
  		}
  		
  		
  	//家长关系 List
  		@RequestMapping("relation")
  		@ResponseBody
  		public synchronized Map<String,Object> relation(){
  			Map<String, Object> map = bigcontrolService.relation();
  			return map;
  		}
  		
  	//家长关系 添加
  		@RequestMapping("addrelation")
  		@ResponseBody
  		public synchronized Map<String,Object> addrelation(String token , String relation){
  			Map<String, Object> map = bigcontrolService.addrelation(token ,relation);
  			return map;
  		}
  		
  		
  	//家长关系 删除
  		@RequestMapping("deleterelation")
  		@ResponseBody
  		public synchronized Map<String,Object> deleterelation(String token,Integer relationId){
  			Map<String, Object> map = bigcontrolService.deleterelation(token , relationId);
  			return map;
  		}
  		
  	//删除所有isvalid =1
	   	@RequestMapping(value="deleteAll")
		public  @ResponseBody Map<String, Object> deleteAll(String token) {
	   		Map<String, Object> map = bigcontrolService.deleteAll(token);
			return map;
		}
	   	
	   	@RequestMapping("messagelog")//消息历史
		public  @ResponseBody Map<String,Object> messagelog(String token,Long start,Long end,Integer gartenId,Integer pageNo) throws ParseException, APIConnectionException, APIRequestException {
			Map<String,Object> map=bigcontrolService.messagelog(  token,start,end,gartenId,pageNo);
			return map;
		}
	   	
	   	//删除消息历史
	   	@RequestMapping("deleteMessage")
	   	@ResponseBody
	   	public synchronized Map<String,Object> deleteMessage(String token ,Integer messageId){
	   		Map<String, Object> map = bigcontrolService.deleteMessage(token,messageId);
	   		return map;
	   	}
	   	
	   	
	  //获取设备及价格
		@RequestMapping("findEquipmentName")
		public  @ResponseBody Map<String,Object> findEquipmentName(Integer pageNo) throws ParseException {
			Map<String, Object> map = bigcontrolService.findEquipmentName(pageNo);
			return map;
		}
		
		 //新增设备及价格
		@RequestMapping("addEquipmentName")
		public  @ResponseBody Map<String,Object> addEquipmentName(String token , String equipmentName,BigDecimal  price, String img ,String remark) throws ParseException {
			Map<String, Object> map = bigcontrolService.addEquipmentName(token , equipmentName,price,img ,remark);
			return map;
		}
		
		//修改设备及价格
		@RequestMapping("updateEquipmentName")
		@ResponseBody
		public Map<String,Object> updateEquipmentName(String token ,Integer equipmentId, String equipmentName,BigDecimal  price , String img ,String remark){
			Map<String, Object> map = bigcontrolService.updateEquipmentName(token, equipmentId, equipmentName, price, img, remark);
			return map;
		}
		  
		 //删除设备及价格
		@RequestMapping("deleteEquipmentName")
		public  @ResponseBody Map<String,Object> deleteEquipmentName(String token ,Integer equipmentId) throws ParseException {
			Map<String, Object> map = bigcontrolService.deleteEquipmentName(token ,equipmentId);
			return map;
		}

		//查询代理商的无聊订单
		@RequestMapping("wuliaoOrder")
		@ResponseBody
		public Map<String,Object> wuliaoOrder(String token , Integer pageNo,Integer state){
			Map<String, Object> map = bigcontrolService.wuliaoOrder(token, pageNo, state);
			return map;
		}
		//处理代理商的物料订单 
		@RequestMapping("resolveWuliaoOrder")//1待发送 2已发送 3拒发送
		public  @ResponseBody Map<String,Object> resolveWuliaoOrder(String token ,Integer wuliaoId,Integer state,String toPhoneNumber,String remark) throws ParseException {
			Map<String, Object> map = bigcontrolService.resolveWuliaoOrder(token ,wuliaoId,state,toPhoneNumber,remark);
			return map;
		}
		
	//查看幼儿园考勤卡情况
		@RequestMapping("cardNoList")		
		@ResponseBody
		public Map<String,Object> cardNoList(String token,String province,String city,String countries,Integer gartenId,Integer pageNo,String job,Integer classId){
			Map<String, Object> map = bigcontrolService.cardNoList(token, province, city, countries, gartenId, pageNo, job, classId);
			return map;
		}
	//导出考勤卡信息
		@RequestMapping("exportAttendance")
		@ResponseBody
		public Map<String,Object> exportAttendance(String token ,String province,String city, String countries,Integer gartenId, String job,Integer classId,HttpServletResponse response) throws Exception{
			bigcontrolService.exportAttendance(token, province, city, countries, gartenId,job, classId, response);
			
			return null;
		}
		
	//-------------------------------------售后订单--------------------------------------------
	//查询售后订单
		@RequestMapping("findSaleService")//state 0未回复1已回复   null全部
		public  @ResponseBody Map<String,Object> findSaleService(Integer pageNo,Integer[] agentIds,Long start,Long end,Integer state,Integer[] gartenIds) throws ParseException {
			Map<String, Object> map = bigcontrolService.findSaleService( pageNo, agentIds, start, end,state,gartenIds);
			return map;
		}  
		//回复售后订单
		@RequestMapping("replySaleService")
		public  @ResponseBody Map<String,Object> replySaleService(String token ,Long saleServiceId,String reply) throws ParseException {
			Map<String, Object> map = bigcontrolService.replySaleService(token,saleServiceId,reply);
			return map;
		}  
		//删除售后订单 
		@RequestMapping("deleteSaleService")
		public  @ResponseBody Map<String,Object> deleteSaleService(String token ,Long saleServiceId) throws ParseException {
			Map<String, Object> map = bigcontrolService.deleteSaleService(token,saleServiceId);
			return map;
		}  	
		
		
		//-------------------------- ----  公司管理    ----------------         ----------------	
		
		
		//-------------------------- ----  员工管理管理    ----------------         ----------------	

			
				
				
				
		//查询员工
		@RequestMapping("findEmployee")
		public  @ResponseBody Map<String,Object> findEmployee(Integer pageNo,String name,Integer employeeNo,Long departmentNo,Long jobsNo,String phoneNumber) throws ParseException {
			Map<String, Object> map = bigcontrolService.findEmployee(  pageNo, name, employeeNo, departmentNo, jobsNo,phoneNumber);
			return map;
		}  
		//删除员工
		@RequestMapping("deleteEmployee")
		public  @ResponseBody Map<String,Object> deleteEmployee(String token ,Integer  employeeNo) throws ParseException {
			Map<String, Object> map = bigcontrolService.deleteEmployee(token,employeeNo);
			return map;
		}  
		//修改员工
		@RequestMapping("updateEmployee")
		public  @ResponseBody Map<String,Object> updateEmployee(Integer employeeNo,String name,Long departmentNo,
				Long jobsNo,String permission,String province,String city,String countries,
				String phoneNumber,String pwd,Integer sex,Long entryTime,String token) throws ParseException {
			Map<String, Object> map = bigcontrolService.updateEmployee( employeeNo, name, departmentNo,
					 jobsNo, permission, province, city, countries,
					 phoneNumber, pwd,sex,entryTime,token);
					return map;
			}  
		
		//添加员工
		@RequestMapping("addEmployee")
		public  @ResponseBody Map<String,Object> addEmployee(String name,Long departmentNo,
				Long jobsNo,String permission,String province,String city,String countries,
				String phoneNumber,String pwd,Integer sex,Long entryTime,String token ) throws ParseException {
			Map<String, Object> map = bigcontrolService.addEmployee( name, departmentNo,
					 jobsNo, permission, province, city, countries,
					 phoneNumber, pwd,sex,entryTime,token);
			return map;
		}  
		
		//根据部门，岗位获取员工
		@RequestMapping("getEmployee")
		@ResponseBody
		public   Map<String,Object> getEmployee(Long departmentNo,Long jobsNo){
			Map<String, Object> map = bigcontrolService.getEmployee(departmentNo, jobsNo);
			return map;
		}
		
		//员工业绩统计(开园业绩统计）
		@RequestMapping("employeePerformance")
		@ResponseBody
		public  Map<String,Object> employeePerformance(Long departmentNo,Long jobsNo,Integer employeeNo,Integer pageNo){
			Map<String, Object> map = bigcontrolService.employeePerformance(departmentNo, jobsNo, employeeNo, pageNo);
			return map;
		}
		
		//-------------------------- ----  活动中心    ----------------         ----------------	
				
		//查询所有活动 company=cp employeeNo 谁申请的活动
		@RequestMapping("findCpActivity")
		public  @ResponseBody Map<String,Object> findCpActivity(Integer pageNo,String state,Integer employeeNo) throws ParseException {
			Map<String, Object> map = bigcontrolService.findCpActivity( pageNo,state,employeeNo);
			return map;
		}  
		
		
		//查询本部门活动 company=cp  
		@RequestMapping("findDepartmentCpActivity")
		public  @ResponseBody Map<String,Object> findDepartmentCpActivity(Integer pageNo,String state,String token) throws ParseException {
			Map<String, Object> map = bigcontrolService.findDepartmentCpActivity( pageNo,state,token);
			return map;
		}  
		
		//删除活动
		@RequestMapping("deleteCpActivity")
		public  @ResponseBody Map<String,Object> deleteCpActivity(String token ,Integer  cpActivityId) throws ParseException {
			Map<String, Object> map = bigcontrolService.deleteCpActivity(token,cpActivityId);
			return map;
		}  
		
			
		//添加活动
		@RequestMapping("addCpActivity")
		public  @ResponseBody Map<String,Object> addCpActivity(String token,String content,String reason,String title
				) throws ParseException {
			Map<String, Object> map = bigcontrolService.addCpActivity( token, content, reason, title);
			return map;
		} 
			
			
		//修改活动
		@RequestMapping("updateCpActivity")
		public  @ResponseBody Map<String,Object> updateCpActivity(String token ,Integer cpActivityId,String state
		) throws ParseException {
			Map<String, Object> map = bigcontrolService.updateCpActivity( token, cpActivityId, state);
			return map;
		} 

		//-------------------------- ----  报表统计    ----------------         ----------------	

		//查询 所属部门的 报表
		@RequestMapping("findDepartmentReport")
		public  @ResponseBody Map<String,Object> findDepartmentReport(Integer pageNo,String token ,Integer type,Long startTime,Long endTime) throws ParseException {
			Map<String, Object> map = bigcontrolService.findDepartmentReport(  pageNo, token , type, startTime, endTime);
			return map;
		}  
				
		//查询 不同部门的 报表
		@RequestMapping("findReport")
		public  @ResponseBody Map<String,Object> findReport(Integer pageNo,Integer type,Long startTime,Long endTime,Long departmentNo) throws ParseException {
			Map<String, Object> map = bigcontrolService.findReport( pageNo , type, startTime, endTime,departmentNo);
			return map;
		}  
		//删除报表
		@RequestMapping("deleteReport")
		public  @ResponseBody Map<String,Object> deleteReport(String token ,Integer reportId) throws ParseException {
			Map<String, Object> map = bigcontrolService.deleteReport(token ,reportId);
			return map;
		}  
				
					
		//添加报表
		@RequestMapping("addReport")
		public  @ResponseBody Map<String,Object> addReport(String token,Long startTime,Long endTime,String workContent,
				String workSummary,String harmonizeContent,String plan,Integer type) throws ParseException {
			Map<String, Object> map = bigcontrolService.addReport( token, startTime, endTime, workContent,
					 workSummary, harmonizeContent, plan, type);
			return map;
		} 
		
		//查询 自己的 报表
		@RequestMapping("findMyReport")
		public  @ResponseBody Map<String,Object> findMyReport(Integer pageNo,String token ,Integer type,Long startTime,Long endTime) throws ParseException {
			Map<String, Object> map = bigcontrolService.findMyReport(  pageNo, token , type, startTime, endTime);
			return map;
		}  

		//-------------------------- ---- 开园申请     ----------------         ----------------			
		//查询 所属部门的 报表
		/**
		 * 找到自己的申请幼儿园
		 * @param token 员工的token
		 * @return
		 */
		@RequestMapping("findMyApplyGarten")
		public  @ResponseBody Map<String,Object> findMyApplyGarten(String token,Integer pageNo) throws ParseException {
			Map<String, Object> map = bigcontrolService.findMyApplyGarten(token,pageNo );
			return map;
		}  
			
	
		/**
		 * 提交申请开通幼儿园
		 * @param token 员工的token
		 * @return
		 */
		@RequestMapping("applyGarten")
		@ResponseBody
		public synchronized Map<String,Object> applyGarten(String token,String gartenName,String name,String phoneNumber,String contractNumber,String province,
				String city, String countries,Integer workerCount,Integer babyCount,Integer gradeCount,Integer classCount,Double money1,String equipment,String remark,Integer gartenType){
			Map<String, Object> map = bigcontrolService.applyGarten(token, gartenName, name, phoneNumber, contractNumber, province, city, countries, workerCount,babyCount,gradeCount,classCount, money1, equipment,remark,gartenType);
			return map;
		}
		
		

		/**
		 * 删除申请申请开通幼儿园   agent/cancelApply
		 * @param auditId 员工主键
		 * @param resource 0
		 */

		//-------------------------- ---- 部门管理     ----------------         ----------------			
		/**
		 * 查找部门
		 */
		@RequestMapping("findDepartment")
		public  @ResponseBody Map<String,Object> findDepartment( ) throws ParseException {
			Map<String, Object> map = bigcontrolService.findDepartment( );
			return map;
		} 
		
		/**
		 * 删除部门
		 */
		@RequestMapping("deleteDepartment")
		public  @ResponseBody Map<String,Object> deleteDepartment( String token ,Long departmentNo) throws ParseException {
			Map<String, Object> map = bigcontrolService.deleteDepartment( token,departmentNo);
			return map;
		}  
		
		
		
		/**
		 * 添加部门
		 */
		@RequestMapping("addDepartment")
		public  @ResponseBody Map<String,Object> addDepartment( String token ,String departmentName,String mark) throws ParseException {
			Map<String, Object> map = bigcontrolService.addDepartment( token,departmentName,mark);
			return map;
		}  
//-------------------------- ---- 岗位管理     ----------------         ----------------	
			
		/**
		 * 查找岗位Jobs
		 */
		@RequestMapping("findJobs")
		public  @ResponseBody Map<String,Object> findJobs( ) throws ParseException {
			Map<String, Object> map = bigcontrolService.findJobs( );
			return map;
		}
		
		/**
		 * 删除岗位
		 */
		@RequestMapping("deleteJobs")
		public  @ResponseBody Map<String,Object> deleteJobs( String token ,Long jobsNo) throws ParseException {
			Map<String, Object> map = bigcontrolService.deleteJobs( token,jobsNo);
			return map;
		}  
		
		/**
		 * 添加岗位
		 */
		@RequestMapping("addJobs")
		public  @ResponseBody Map<String,Object> addJobs( String token ,String jobsName,String mark) throws ParseException {
			Map<String, Object> map = bigcontrolService.addJobs( token,jobsName,mark);
			return map;
		}  
		
		//生成物料订单
		@RequestMapping("addWuliaoOrder")
		@ResponseBody
		public synchronized Map<String,Object> addWuliaoOrder(String token,String equipmentAll,String address,String postalcode,String fromPhoneNumber,BigDecimal totalPrice,String remark){
			Map<String, Object> map = bigcontrolService.addWuliaoOrder( token, equipmentAll, address, postalcode, fromPhoneNumber,totalPrice,remark);
			return map;
		}
		
		//查询自己的物料订单
		@RequestMapping("findWuliaoOrder")
		@ResponseBody
		public synchronized Map<String,Object> findWuliaoOrder(String token,Integer pageNo ,Integer state){
			Map<String, Object> map = bigcontrolService.findWuliaoOrder(token,pageNo,state);
			return map;
		}
		
		//删除物料订单 
		@RequestMapping("deleteWuliaoOrder")
		@ResponseBody
		public synchronized Map<String,Object> deleteWuliaoOrder(String token,Integer wuliaoId ){
			Map<String, Object> map = bigcontrolService.deleteWuliaoOrder( token, wuliaoId);
			return map;
		}
		
		//查看本部门的物料订单
		@RequestMapping("departmentWuliaoOrder")
		@ResponseBody
		public synchronized Map<String,Object> departmentWuliaoOrder(String token,Integer pageNo ,Integer state){
			Map<String, Object> map = bigcontrolService.departmentWuliaoOrder(token, pageNo, state);
			return map;
		}
		//--------------------------------代理商提现记录-----------------------------------
		@RequestMapping("findWithdraw")//
		@ResponseBody
		public synchronized Map<String,Object> findWithdraw(Long startTime,
				Long  endTime,Integer state){
			Map<String, Object> map = bigcontrolService.findWithdraw( startTime,  endTime,state);
			return map;
		}
		
		@RequestMapping("updateWithdraw")//
		@ResponseBody
		public synchronized Map<String,Object> updateWithdraw(String token,Integer withdrawId,Integer state,String mark){
			Map<String, Object> map = bigcontrolService.updateWithdraw(token, withdrawId,state,mark);
			return map;
		}
		
		
		@RequestMapping("deleteWithdraw")//
		@ResponseBody
		public synchronized Map<String,Object> deleteWithdraw(String token ,Integer withdrawId){
			Map<String, Object> map = bigcontrolService.deleteWithdraw(token, withdrawId);
			return map;
		}
	
	//-------------------------------代理商信用额度-----------------------------	
	
	//查询代理商购买信用额度的记录
	   	@RequestMapping(value="findAgentOrder")
		public  @ResponseBody Map<String, Object> findAgentOrder(Integer pageNo,Long startTime,Long endTime,Integer agentId,Integer state) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
	    	Map<String, Object> map = bigcontrolService.findAgentOrder(pageNo,startTime,endTime,agentId,state);
			return map;
		}
	  //删除代理商购买信用额度的记录	agent/deleteAgentOrder

		
	//---------------------考勤卡-----------
		   	
	   /*	//员工考勤卡查看
	   	@RequestMapping("employeeCard")
	   	@ResponseBody
	   	public Map<String, Object> employeeCard(Long departmentNo,Long jobsNo,Integer employeeNo,Integer pageNo){
	   		Map<String, Object> map = bigcontrolService.employeeCard(departmentNo, jobsNo, employeeNo,pageNo);
	   		return map;
	   	}*/
	   	//考勤卡查看
	   	@RequestMapping("cardList")
	   	@ResponseBody
	   	public Map<String, Object> cardList(Integer id,Integer agentType,Integer pageNo){
	   	
	   		Map<String, Object> map = bigcontrolService.cardList(id, agentType, pageNo);
	   		return map;
	   	}
	   	
	   	/*//代理商考勤卡查看
	   	@RequestMapping("agentCard")
	   	@ResponseBody
		public Map<String, Object> agentCard(String province ,String city ,String countries,Integer agentId,Integer pageNo){
	   		Map<String, Object> map = bigcontrolService.agentCard(province, city, countries, agentId,pageNo);
	   		return map;
	   	}*/
	   	
	   	//导入考勤卡信息
	   	@RequestMapping("importCard")
		@ResponseBody
		public Map<String,Object> importCard(String token,String str,Integer agentId,Integer agentType,Integer returnMoney,String fileName){
			Map<String, Object> map = bigcontrolService.importCard(token,str,agentId,agentType,returnMoney,fileName);
			return map;
		}
	   	
	   	//下载考勤卡导入模板
	   	@RequestMapping("downloadCardTemplate")
	   	@ResponseBody
	   	public Map<String,Object> downloadCardTemplate(String token ,HttpServletResponse response) throws IOException{
	   		bigcontrolService.downloadCardTemplate(token,response);
	   		
	   		return  null;
	   	}
	   	//导出员工或代理商考勤卡信息
	   	@RequestMapping("exportCard")
	   	@ResponseBody
	   	public Map<String,Object> exportCard(String token ,Integer agentId,Integer agentType ,HttpServletResponse response) throws IOException{
	   		bigcontrolService.exportCard(token,agentId, agentType, response);
	   		return null;
	   	}
	   	
	   	//考勤卡押金退还
	   	@RequestMapping("cardReturnMoney")
	   	@ResponseBody
	   	public Map<String,Object> cardReturnMoney(String token, Integer cardId){
	   		Map<String, Object> map = bigcontrolService.cardReturnMoney(token, cardId);
	   		return map;
	   	}
	   	
	   	//押金退还记录
	   	@RequestMapping("cardReturnList")
	   	@ResponseBody
	   	public Map<String,Object> cardReturnList(String name ,Integer pageNo,Long startTime, Long endTime){
	   		Map<String, Object> map = bigcontrolService.cardReturnList(name, pageNo, startTime, endTime);
	   		return map;
	   	}
	   	
	   	//删除押金退还记录
	   	@RequestMapping("deleteCardReturn")
	   	@ResponseBody
	   	public Map<String,Object> deleteCardReturn(String token ,Integer returnId){
	   		Map<String, Object> map = bigcontrolService.deleteCardReturn(token,returnId);
	   		return map;
	   	}
	  //---------------------------------幼儿园类型-----------------------	
	   	@RequestMapping(value="addGartentype")
			public  @ResponseBody Map<String, Object> addGartentype(String token,String mark,String typeName) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
		    	Map<String, Object> map = bigcontrolService.addGartentype(token,mark,typeName);
			return map;
		}
		@RequestMapping(value="deleteGartentype")
		public  @ResponseBody Map<String, Object> deleteGartentype(String token ,Integer gartenType) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
			Map<String, Object> map = bigcontrolService.deleteGartentype(token,gartenType);
			return map;
		}
	   	
		
		@RequestMapping(value="findGartentype")
		public  @ResponseBody Map<String, Object> findGartentype(Integer pageNo) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
			Map<String, Object> map = bigcontrolService.findGartentype(pageNo);
			return map;
		}

		
		//---------批量绑定考勤卡----------------
		@RequestMapping("importAttendanceNo")
		@ResponseBody
		public  Map<String, Object> importAttendanceNo(String token ,String str , String fileName){
			Map<String, Object> map = bigcontrolService.importAttendanceNo(token,str, fileName);
			return map;
		}
		
		/*//下载批量绑定考勤卡模板
		@RequestMapping("downloadAttendanceNoTemplate")
		@ResponseBody
		public  Map<String, Object> downloadAttendanceNoTemplate(HttpServletResponse response) throws Exception{
			Map<String, Object> map = bigcontrolService.downloadAttendanceNoTemplate(response);
			return  map;
		}*/
		
		//----------修改幼儿园代理商
		@RequestMapping("changeAgent")
		@ResponseBody
		public  Map<String, Object> changeAgent(String token ,Integer agentId,Integer agentType,Integer gartenId){
			Map<String, Object> map = bigcontrolService.changeAgent(token,agentId, agentType,gartenId);
			return map;
		}
		//----------------------------------总控制端发送给代理商的信息--------------------------------
		//查找总控制端发送给代理商的信息
		@RequestMapping(value="findAgentMessage")
		public  @ResponseBody Map<String, Object> findAgentMessage(Integer pageNo,Long startTime,Long endTime,Integer agentId,Integer state) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
			Map<String, Object> map = bigcontrolService.findAgentMessage(pageNo,startTime,endTime,agentId,state);
			return map;
		}
		
		//添加总控制端发送给代理商的信息
		@RequestMapping(value="addAgentMessage")
		public  @ResponseBody Map<String, Object> addAgentMessage(String token ,String title,String agentMessage,Integer[] agentIds) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
			Map<String, Object> map = bigcontrolService.addAgentMessage(token,title,agentMessage,agentIds);
			return map;
		}
		
		//删除总控制端发送给代理商的信息
		@RequestMapping(value="deleteAgentMessage")
		public  @ResponseBody Map<String, Object> deleteAgentMessage(Integer agentMessageId) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
			Map<String, Object> map = bigcontrolService.deleteAgentMessage(agentMessageId);
			return map;
		}
		
		//=====================考勤异常处理=============
	@RequestMapping("yichangResolve")
	@ResponseBody
	public Map<String ,Object> yichangResolve(Long startTime , Long endTime,Integer[] gartenIds,String identity){
		Map<String, Object> map = bigcontrolService.yichangResolve(startTime, endTime, gartenIds, identity);
		return map;
	}
	
	//----------------------------------------------------
	//查找操作记录
	@RequestMapping(value="findOperateLog")
	public  @ResponseBody Map<String, Object> findOperateLog(Integer type,String fromName,String toName,String content,Long startTime,Long endTime,Integer pageNo) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException, AlipayApiException, APIConnectionException, APIRequestException {
		Map<String, Object> map = bigcontrolService.findOperateLog( type, fromName, toName, content, startTime, endTime,pageNo);
		return map;
	}	

}
