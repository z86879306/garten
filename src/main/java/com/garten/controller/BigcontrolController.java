package com.garten.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.garten.model.garten.GartenInfo;
import com.garten.service.BigcontrolService;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;

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
		
		//首页 -信息管理-宝宝信息管理  可选:除了token都可选
	@RequestMapping("babyMessage")//
	public  @ResponseBody Map<String,Object> babyMessage(String token,String name ,String province,String city,String countries,Integer pageNo,Integer gartenId,String leadGrade,String leadClass ) throws ParseException {
		Map<String,Object> map=bigcontrolService.babyMessage( token, name,province,city,countries,pageNo, gartenId,leadGrade,leadClass);
		return map;
	}
	//首页 -信息管理-老师信息管理  可选:除了token都可选
	@RequestMapping("workerMessage")//
	public  @ResponseBody Map<String,Object> workerMessage(String token,String name ,String province,String city,String countries,String phoneNumber,Integer  pageNo,Integer gartenId) throws ParseException {
		Map<String,Object> map=bigcontrolService.workerMessage( token, name,province,city,countries,phoneNumber,pageNo,gartenId);
		return map;
	}
	//首页 -信息管理-幼儿园信息管理
	@RequestMapping("gartenMessage")//
	public  @ResponseBody Map<String,Object> gartenMessage(String token,String name ,String province,String city,String countries,String phoneNumber,Integer  pageNo,Integer gartenId,Integer monitorState,Integer attendanceState ) throws ParseException {
		Map<String,Object> map=bigcontrolService.gartenMessage( token, name,province,city,countries,phoneNumber,pageNo,gartenId,monitorState,attendanceState);
		return map;
	}
	//修改幼儿园信息
	@RequestMapping("updateGarten")//
	public synchronized  @ResponseBody Map<String,Object> updateGarten(String token,Integer gartenId,String gartenName ,String name,String phoneNumber,String contractNumber,Long  contractStart,Long  contractEnd,String  organizationCode,String province,String city,String countries ,String address,Integer accountState,BigDecimal charge,Long attendanceTime,Long monitorTime) throws ParseException {
		Map<String,Object> map=bigcontrolService.updateGarten(  token, gartenId, gartenName , name, phoneNumber, contractNumber,  contractStart,  contractEnd,  organizationCode, province, city, countries , address, accountState, charge, attendanceTime, monitorTime);
		return map;
	}
	@RequestMapping("accountGarten")//冻结 解冻
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
	//通过开园审核
		@RequestMapping("agreeAgentAudit")//
		public synchronized  @ResponseBody Map<String,Object> agreeAgentAudit(String token ,Integer auditId,Integer gartenGrade,Long attendanceTime,Long monitorTime,Long contractStart,Long contractEnd,String organizationCode ) throws ParseException, IOException {
			Map<String,Object> map=bigcontrolService.agreeAgentAudit(  token , auditId, gartenGrade, attendanceTime, monitorTime, contractStart, contractEnd, organizationCode);
			return map;
		}
//增加忽略时间(手动)
		@RequestMapping("ignore")//
		public synchronized  @ResponseBody void ignore(Integer gartenId) throws ParseException, IOException {
			bigcontrolService.ignore( gartenId);
		}
		
		//首页-幼儿园管理——添加幼儿园
				@RequestMapping("addGarten")//
				public synchronized  @ResponseBody Map<String,Object> addGarten(String token  ,Integer gartenGrade,Long attendanceTime,Long monitorTime,Long contractStart,Long contractEnd,String organizationCode,String jobcall,String phoneNumber,
						String gartenName,String name,String contractNumber,String province,
						String city,String countries) throws ParseException, IOException {
					Map<String,Object> map=bigcontrolService.addGarten( token  , gartenGrade, attendanceTime, monitorTime, contractStart, contractEnd, organizationCode, jobcall, phoneNumber,
							 gartenName, name, contractNumber, province,
							 city, countries);
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
		public synchronized  @ResponseBody Map<String,Object> updateAgentMessge(String token,Integer agentId,String agentName,String phoneNumber,String province,String city,String countries,String cardFragment ) throws ParseException {
			Map<String,Object> map=bigcontrolService.updateAgentMessge(  token, agentId, agentName, phoneNumber, province, city, countries, cardFragment );
			return map;
		}
		//代理商财务管理修改
		@RequestMapping("updateAgentFinance")
		@ResponseBody
		public synchronized Map<String,Object> updateAgentFinance(String token, Integer agentId,String creditMoney,String agentMoney ,Integer rebate){
			Map<String, Object> map = bigcontrolService.updateAgentFinance(token, agentId, creditMoney, agentMoney, rebate);
			return map;
			
		}
		
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
		public synchronized  @ResponseBody Map<String,Object> changeGartenDredge(Integer count ,Integer type,Integer gartenId ) throws ParseException {
			Map<String,Object> map=bigcontrolService.changeGartenDredge(   count,type ,gartenId);
			return map;
		}
		
		@RequestMapping("sendNotified")//发送通知
		public synchronized  @ResponseBody Map<String,Object> sendNotified(String token,Integer[] gartenIds ,Integer type,String title,String info ) throws ParseException, APIConnectionException, APIRequestException {
			Map<String,Object> map=bigcontrolService.sendNotified(  token, gartenIds , type, title, info);
			return map;
		}
		
		@RequestMapping("order")//查看订单
		public  @ResponseBody Map<String,Object> order(String token,Integer pageNo,String province,String city,String countries
				,Integer gartenId,Integer state,String name,String phoneNumber,Integer type) throws ParseException, APIConnectionException, APIRequestException {
			Map<String,Object> map=bigcontrolService.order(  token,pageNo, province, city, countries, gartenId, state, name, phoneNumber, type);
			return map;
		}
		
		@RequestMapping("feedback")//查看反馈
		public  @ResponseBody Map<String,Object> feedback(String token,Integer pageNo) throws ParseException, APIConnectionException, APIRequestException {
			Map<String,Object> map=bigcontrolService.feedback(  token,pageNo);
			return map;
		}
		
		
		//支付宝支付
		@RequestMapping(value="alipay")
		public synchronized  @ResponseBody Map<String, Object> alipay(String token,Integer type,Integer monthCount,Integer gartenId ,Integer parentId,Integer babyId) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, AlipayApiException, APIConnectionException, APIRequestException, IOException {
	    	Map<String, Object> map = bigcontrolService.alipay(token,type,monthCount,gartenId ,parentId,babyId);
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
  		public synchronized Map<String,Object> addrelation(String relation){
  			Map<String, Object> map = bigcontrolService.addrelation(relation);
  			return map;
  		}
  		
  		
  	//家长关系 删除
  		@RequestMapping("deleterelation")
  		@ResponseBody
  		public synchronized Map<String,Object> deleterelation(Integer relationId){
  			Map<String, Object> map = bigcontrolService.deleterelation(relationId);
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
	   	public synchronized Map<String,Object> deleteMessage(Integer messageId){
	   		Map<String, Object> map = bigcontrolService.deleteMessage(messageId);
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
		public  @ResponseBody Map<String,Object> addEquipmentName(String equipmentName,BigDecimal  price ) throws ParseException {
			Map<String, Object> map = bigcontrolService.addEquipmentName(equipmentName,price);
			return map;
		}
		  
		 //删除设备及价格
		@RequestMapping("deleteEquipmentName")
		public  @ResponseBody Map<String,Object> deleteEquipmentName(Integer equipmentId) throws ParseException {
			Map<String, Object> map = bigcontrolService.deleteEquipmentName(equipmentId);
			return map;
		}

		//处理代理商的物料订单
		@RequestMapping("resolveWuliaoOrder")//1待发送 2已发送 3拒发送
		public  @ResponseBody Map<String,Object> resolveWuliaoOrder(Integer wuliaoId,Integer state,String toPhoneNunmber,String remark) throws ParseException {
			Map<String, Object> map = bigcontrolService.resolveWuliaoOrder(wuliaoId,state,toPhoneNunmber,remark);
			return map;
		}

}
