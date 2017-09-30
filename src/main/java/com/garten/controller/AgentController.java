package com.garten.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garten.model.garten.GartenInfo;
import com.garten.service.AgentService;
import com.garten.service.BigcontrolService;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;

@Controller
@RequestMapping("agent")
public class AgentController {
	@Autowired
	private AgentService agentService;
	
	@RequestMapping(value="login")
	@ResponseBody
	public synchronized Map<String, Object> login(String phoneNumber,String pwd){
		Map<String,Object> result=agentService.login( phoneNumber, pwd);
		return result;
	}
	
	//发送验证码 在worker里面 sendControl
	@RequestMapping("updateLogin")//修改密码
	public  synchronized @ResponseBody Map<String,Object> updateLogin(String phoneNumber,String pwd,String number ) throws ParseException {
		Map<String,Object> map=agentService.updateLogin( phoneNumber, pwd, number);
		return map;
	}
	
	
	//子代理列表
	@RequestMapping("childAgent")
	public  @ResponseBody Map<String,Object> childAgent(String token,Integer pageNo) throws ParseException {
		Map<String,Object> result=agentService.childAgent( token, pageNo);
		return result;
	}
	
	
	//子代理列表（不分页）
	@RequestMapping("childAgentNoPage")
	public  @ResponseBody Map<String,Object> childAgentNoPage(String token) throws ParseException {
		Map<String,Object> result=agentService.childAgentNoPage( token);
		return result;
	}
	/*//测试
	@RequestMapping("test")
	@ResponseBody
	public Map<String, Object> test(String str, Integer inte, Long lon, Double dou) {
		System.out.println(str);
		System.out.println(inte);
		System.out.println(lon);
		System.out.println(dou);
		HashMap<String, Object> result = new HashMap<String,Object>();
		result.put("str", str);
		result.put("inte", inte);
		result.put("lon", lon);
		result.put("dou", dou);
		return result;
		
	}*/
  
	
	//幼儿园管理列表
	@RequestMapping("gartenManage")
	@ResponseBody
	public   Map<String,Object> gartenManage(String token,Integer pageNo,String name,String phoneNumber){
		Map<String, Object> result = agentService.gartenManage(token, pageNo, name, phoneNumber);
		return result;
		
	}
	
	//冻结幼儿园
	@RequestMapping("frostGarten")
	@ResponseBody
	public   synchronized Map<String,Object> frostGarten(String token,Integer gartenId){
		Map<String, Object> map = agentService.frostGarten(token, gartenId);
		return map;
	}
	
	//恢复幼儿园
	@RequestMapping("recoverGarten")
	@ResponseBody
	public   synchronized Map<String,Object> recoverGarten(String token,Integer gartenId){
		Map<String, Object> map = agentService.recoverGarten(token, gartenId);
		return map;
		
	}
	
	//修改幼儿园
	@RequestMapping("updateGarten")
	@ResponseBody
	public  synchronized  Map<String,Object> updateGarten(String token,Integer gartenId,String gartenName,String name,String phoneNumber,
			String contractNumber,Long contractStart ,Long contractEnd,	String organizationCode,String province ,String city,
			String countries,String address,Double charge){
		Map<String, Object> map = agentService.updateGarten(token, gartenId, gartenName, name, phoneNumber, contractNumber, contractStart, contractEnd, organizationCode, province, city, countries, address, charge);
		return map;
	}
	
	//开园申请列表
	@RequestMapping("applyList")
	@ResponseBody
	public   Map<String,Object> applyList(String token,Integer pageNo ){
		Map<String, Object> map = agentService.applyList(token, pageNo);
		
		return map;
	}
	
	//取消申请
	@RequestMapping("cancelApply")
	@ResponseBody
	public synchronized Map<String,Object> cancelApply(String token,String auditId){
		Map<String, Object> map = agentService.cancelApply(token, auditId);
		return map;
		
	}
	
	//开园申请
	@RequestMapping("applyGarten")
	@ResponseBody
	public synchronized Map<String,Object> applyGarten(String token,String gartenName,String name,String phoneNumber,String contractNumber,String province,
			String city, String countries,Integer count,Double money,String equipment){
		Map<String, Object> map = agentService.applyGarten(token, gartenName, name, phoneNumber, contractNumber, province, city, countries, count, money, equipment);
		return map;
	}
	
	//添加访园记录
	@RequestMapping("addVisit")
	@ResponseBody
	public synchronized Map<String,Object> addVisit(String token,Integer gartenId,String title , String content ){
		Map<String, Object> map = agentService.addVisit(token, gartenId, title, content);
		return map;
		
	}
	
	//访园跟进历史
	@RequestMapping("agentVisitList")
	@ResponseBody
	public Map<String,Object> agentVisitList(String token,Integer pageNo,Integer gartenId){
		Map<String, Object> map = agentService.agentVisitList(token, pageNo,gartenId);
		return map;
	}
	
	//子代理业绩统计
	@RequestMapping("childAgentBusiness")
	@ResponseBody
	public Map<String,Object> childAgentBusiness(String token,Integer agentId,Integer pageNo){
		Map<String, Object> map = agentService.childAgentBusiness(token, agentId, pageNo);
		return map;
	}
	
	//代理商代理幼儿园 （名字 id）
	@RequestMapping("/agentGarten")
	@ResponseBody
	public Map<String,Object> agentGarten (String token){
		Map<String, Object> map = agentService.agentGarten(token);
		return map;
	}
	
	//幼儿园详情
	@RequestMapping("getGartenDetail")
	@ResponseBody
	public Map<String,Object> getGartenDetail(String token,Integer agentId) throws ParseException{
		Map<String, Object> map = agentService.getGartenDetail(token, agentId);
		return map;
	}
	
	//删除访园记录
	@RequestMapping("deleteVisit")
	@ResponseBody
	public synchronized Map<String,Object> deleteVisit(String token ,Integer visitId){
		Map<String, Object> map = agentService.deleteVisit(token, visitId);
		return map;
	}
	
	/*[{equipmentName: '小明',price: 20,count:1},{equipmentName: '小明',price: 20,count:1}]*/
	//查询自己的物料订单
	@RequestMapping("findWuliaoOrder")
	@ResponseBody
	public synchronized Map<String,Object> findWuliaoOrder(String token,Integer pageNo ,Integer state){
		Map<String, Object> map = agentService.findWuliaoOrder(token,pageNo,state);
		return map;
	}
	//生成物料订单
	@RequestMapping("addWuliaoOrder")
	@ResponseBody
	public synchronized Map<String,Object> addWuliaoOrder(String token,String equipmentAll,String address,String postalcode,String fromPhoneNumber,BigDecimal totalPrice){
		Map<String, Object> map = agentService.addWuliaoOrder( token, equipmentAll, address, postalcode, fromPhoneNumber,totalPrice);
		return map;
	}
	
	//删除物料订单 
	@RequestMapping("deleteWuliaoOrder")
	@ResponseBody
	public synchronized Map<String,Object> deleteWuliaoOrder(String token,Integer wuliaoId ){
		Map<String, Object> map = agentService.deleteWuliaoOrder( token, wuliaoId);
		return map;
	}

	//查询售后订单
		@RequestMapping("findSaleService")//state 0未回复1已回复   null全部
		public  @ResponseBody Map<String,Object> findSaleService(Integer pageNo,String  token,Long start,Long end,Integer state,Integer[] gartenIds) throws ParseException {
			Map<String, Object> map = agentService.findSaleService( pageNo, token, start, end,state,gartenIds);
			return map;
		}  

	//添加自己的售后订单
		@RequestMapping("addSaleService")//  0   4
		@ResponseBody
		public synchronized Map<String,Object> addSaleService(String token,String title,
				Integer gartenId,String content,String mark){
			Map<String, Object> map = agentService.addSaleService( token, title, gartenId, content, mark);
			return map;
		}

	
	//获取设备及价格(不分页）
	/*@RequestMapping("findEquipmentNameNoPage")
	public  @ResponseBody Map<String,Object> findEquipmentNameNoPage() throws ParseException {
		Map<String, Object> map = agentService.findEquipmentNameNoPage();
		return map;
	}*/
}
