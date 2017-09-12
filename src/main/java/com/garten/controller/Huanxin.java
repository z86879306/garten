package com.garten.controller;

import java.net.URLConnection;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;



/**
 * 用户Controller层
 * @author Administrator
 *
 */

public class Huanxin {
	 public static void main(String []args) throws IOException{
	           long begintime = System.currentTimeMillis();
	           URL url = new URL("http://139.196.102.135/wode/wode?phoneNumber=18367803779");
	           HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
	           urlcon.connect();         //获取连接
	           InputStream is = urlcon.getInputStream();
	           BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
	           StringBuffer bs = new StringBuffer();
	           String l = null;
	           while((l=buffer.readLine())!=null){
	               bs.append(l).append("\n");
	           }
	           Object succesResponse = JSON.parse(bs.toString());    //先转换成Object
	           @SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String,Object>)succesResponse;         //Object强转换为Map
	           System.out.println(map.get("3"));
	           System.out.println(bs.toString());
	    }
	 
	 public static Map<String, Object> getJiekou( String address) throws IOException {
         URL url = new URL(address);
         HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
         urlcon.setRequestMethod("POST");
         urlcon.addRequestProperty("Authorization", "Bearer YWMt9W9S-mErEee-xyvhI5fptwAAAAAAAAAAAAAAAAAAAAFXRA_AR1UR57_rsZ6SJXDTAgMAAAFdEKMR-QBPGgDRT8xspg_e_A_qpWWFdV8zct0aisOlW4Ir6UKhe7gsvg");
         urlcon.connect();         //获取连接
         InputStream is = urlcon.getInputStream();
         BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
         StringBuffer bs = new StringBuffer();
         String l = null;
         while((l=buffer.readLine())!=null){
             bs.append(l).append("\n");
         }
         Object succesResponse = JSON.parse(bs.toString());    //先转换成Object
		@SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String,Object>)succesResponse;   
		return map;
	}
	 

}
