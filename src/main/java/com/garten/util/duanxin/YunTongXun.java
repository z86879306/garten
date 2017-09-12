package com.garten.util.duanxin;

import java.util.HashMap;
import java.util.Set;





public class YunTongXun  {
	
	public static void main(String[] args) {
		/*String[] dat={"20"};
		register("18367803779", "86615", null);*/
	}
	 public void sendMessage(String phoneNumber,String number){
		 HashMap<String, Object> result = null; 
		 CCPRestSDK restAPI = new CCPRestSDK();
		 restAPI.init("app.cloopen.com", "8883");
		 // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883. 
		 restAPI.setAccount("aaf98f8953403fcf01534064ad6c00b8", "882d66de3a344e04b2705fd534f7fb98");
		 // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
		 restAPI.setAppId("aaf98f89539b228f0153a27d86850e37");
		 // 请使用管理控制台中已创建应用的APPID。
		 result = restAPI.sendTemplateSMS(phoneNumber,"75338" ,new String[]{number,"10"});
		 System.out.println("SDKTestGetSubAccounts result=" + result); 
		 if("000000".equals(result.get("statusCode"))){
		 //正常返回输出data包体信息（map）
		 @SuppressWarnings("unchecked")
		HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
		 Set<String> keySet = data.keySet();
		 for(String key:keySet){ 
		 Object object = data.get(key); 
		 System.out.println(key +" = "+object); 
		 }
		 }else{
		 //异常返回输出错误码和错误信息
		 System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		 }
		 }
	 //云家洁86583用户注册成功
	 public  void register(String phoneNumber,String modelId,String[] datas){
		 HashMap<String, Object> result = null; 
		 CCPRestSDK restAPI = new CCPRestSDK();
		 restAPI.init("app.cloopen.com", "8883");
		 // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883. 
		 restAPI.setAccount("aaf98f8953403fcf01534064ad6c00b8", "882d66de3a344e04b2705fd534f7fb98");
		 // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
		 restAPI.setAppId("aaf98f89539b228f0153a27d86850e37");
		 // 请使用管理控制台中已创建应用的APPID。
		 result = restAPI.sendTemplateSMS(phoneNumber,modelId, datas);
		 System.out.println("SDKTestGetSubAccounts result=" + result); 
		 if("000000".equals(result.get("statusCode"))){
		 //正常返回输出data包体信息（map）
		 @SuppressWarnings("unchecked")
		HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
		 Set<String> keySet = data.keySet();
		 for(String key:keySet){ 
		 Object object = data.get(key); 
		 System.out.println(key +" = "+object); 
		 }
		 }else{
		 //异常返回输出错误码和错误信息
		 System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		 }
		 } 
	
  
	
}
