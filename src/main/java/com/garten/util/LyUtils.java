											/*　　      ＿＿＿
											　　 ／　　　▲
											／￣　 ヽ　■■
											●　　　　　■■
											ヽ＿＿＿　　■■			史努比祭天
											　　　　）＝｜
											　　　／　｜｜
											　∩∩＿＿とﾉ
											　しし———┘*/
package com.garten.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;
import com.garten.util.md5.md5Util;
import com.garten.vo.smallcontrol.CameraDetile;
import com.mysql.fabric.xmlrpc.base.Array;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class LyUtils {

	public static void main(String[] args) throws UnsupportedEncodingException, ParseException {
		System.out.println(md5Util.getMD5("5fdaf123-843f-4bdd-a84f-5f18355afe95"+"1504956639"));
	}

	public static boolean saveFile(MultipartFile file, HttpServletRequest request) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 文件保存路径
				String filePath = request.getSession().getServletContext().getRealPath("/") + "/fileUpload/"
						+ file.getOriginalFilename();
				// 转存文件
				file.transferTo(new File(filePath));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static long strChangeToLong(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Long time = null;
		try {
			time = sdf.parse(str).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;

	}

	// 给单个人发送通知
	public static void pushToOne(String appKey, String masterSecret, String message, String phoneNumber) {
		JPushClient jPushClient = new JPushClient(masterSecret, appKey);
		PushPayload payload = sendByPhoneNumber(phoneNumber, message);
		PushResult result = null;
		try {
			result = jPushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}

	// 全部人发送通知
	public static void pushToAll(String appKey, String masterSecret, String message) {
		JPushClient jPushClient = new JPushClient(masterSecret, appKey);
		PushPayload payload = alertAll(message);
		PushResult result = null;
		try {
			result = jPushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}

	public static PushPayload sendByPhoneNumber(String phoneNumber, String message) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(phoneNumber))
				.setNotification(Notification.alert(message)).build();
	}

	public static PushPayload alertAll(String alert) {
		return new Builder().setPlatform(Platform.all()).setAudience(Audience.all())
				.setNotification(Notification.alert(alert)).build();
	}

	// post请求
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	// 获取萤石云accessToken
	public static String getAccessToken() {

		String accessToken = sendPost(LyParam.YINGSHIYUN_TOKEN,
				"appKey="+LyParam.YINGSHIYUN_APPKEY+"&appSecret="+LyParam.YINGSHIYUN_APPSECRET);
		return accessToken.substring(24, 88);
	}
	
	//在萤石云平台添加设备
	public static String addYinshiyunCamera(String deviceSerial,String validateCode) throws UnsupportedEncodingException{
		String accessToken = getAccessToken();
		String info = sendPost(LyParam.YINGSHIYUN_ADD, "accessToken="+accessToken+"&deviceSerial="+deviceSerial+"&validateCode="+validateCode);
		JSONObject js = JSONObject.parseObject(info);
		String result = js.getString("code");
		return result;
	}
	//在萤石云平台删除设备
	public static void deleteYinshiyunCamera(String deviceSerial){
		String accessToken = getAccessToken();
		sendPost(LyParam.YINGSHIYUN_DELETE, "accessToken="+accessToken+"&deviceSerial="+deviceSerial);
	}
	
	// 开通直播 获取url地址
	public static String dredgeLive(String deviceSerial) {
		String accessToken = getAccessToken();
		//开通直播
		sendPost(LyParam.YINGSHIYUN_OPEN, "accessToken="+accessToken+"&source="+deviceSerial+":1");
		//获取直播url地址
		String info = sendPost(LyParam.YINGSHIYUN_GET,"accessToken="+accessToken+"&source="+deviceSerial+":1");
		 JSONObject object = JSONObject.parseObject(info);
		 JSONArray array = object.getJSONArray("data");
		 String js = JSONObject.toJSONString(array);
		 List<CameraDetile> cameraDetile = JSONObject.parseArray(js,
		 CameraDetile.class);
		 //String url = cameraDetile.get(0).getRtmpHd();
		 String url = cameraDetile.get(0).getHlsHd();
		 System.out.println(url);
		 return url;
	}
	
	//int数组转为string
	public static String intChangeToStr(Integer[] inte){
		String sb=null ;
		if(null!=inte){
			for(int i=0;i<inte.length;i++)
			{
			if(i<inte.length-1)
			{
			sb+=(inte[i].toString()+",");
			}
			else if(i==inte.length-1)
			{
			sb+=(inte[i].toString());
			}   
			}
			System.out.println(sb);
		}else{
			return null;
		}
			
		return sb;
	}
	//字符串数组生成字符串
	public static String StrChangeToStr(String [] inte){
		String sb="" ;
		for(int i=0;i<inte.length;i++)
		{
		if(i<inte.length-1)
		{
		sb+=(inte[i]+",");
		}
		else if(i==inte.length-1)
		{
		sb+=(inte[i]);
		}   
		}
		System.out.println(sb);
		return sb;
	}
	//删除数组中指定的元素
	public static String[] ArrayRemove(String[] array ,String id){
		String[] newArray = new String[array.length-1];
		int idx = 0;
        boolean hasRemove = false;
        for (int i = 0; i < array.length; i++) {
 
            if (!hasRemove && array[i] == id) {
                hasRemove = true;
                continue;
            }
            newArray[idx++] = array[i];
        }
        return newArray;
		
	}
	//Integer数组一次清除一个指定元素
	public static Integer [] IntArrayRemove(Integer [] array ,Integer id){
		Integer[] newArray = new Integer[array.length-1];
		int idx = 0;
        boolean hasRemove = false;
        for (int i = 0; i < array.length; i++) {
 
            if (!hasRemove && array[i] == id) {
                hasRemove = true;
                continue;
            }
            newArray[idx++] = array[i];
        }
        return newArray;
		
	}
	
	//删除数组中指定的位置元素
		public static String[] ArrayRemoveIndex(String[] array ,Integer index){
			String[] newArray = new String[array.length-1];
			if(newArray.length==0){
				return newArray;
			}
			int idx = 0;
	        boolean hasRemove = false;
	        for (int i = 0; i < array.length; i++) {
	 
	            if (!hasRemove && i == index) {
	                hasRemove = true;
	                continue;
	            }
	            newArray[idx++] = array[i];
	        }
	        return newArray;
			
		}
		
		//通过身份证获取生日
		 public static Long getBirthByIdCard(String idCard) throws ParseException {
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 Long birthday = sdf.parse(idCard.substring(6, 10)+"-"+idCard.substring(10, 12)+"-"+idCard.substring(12, 14)).getTime()/1000;
		     return birthday;   
		    }
	//查找数组相同元素的下标
		/*public static Integer findIndex(String[] array,String temp){
			Integer index=-1;
			
		}*/
		 
		 public static File base64ToFile(String base64,String fileName) {
		        File file = null;
		        //String fileName = "/Petssions/record/testFile.amr";
		        FileOutputStream out = null;
		        try {
		            // 解码，然后将字节转换为文件
		            file = new File("C:/excel", fileName);
		            if (!file.exists())
		                file.createNewFile();
		            BASE64Decoder decoder = new BASE64Decoder();  
		            byte[] b = decoder.decodeBuffer(base64);
		            for(int i=0;i<b.length;++i)  
		            {  
		                if(b[i]<0)  
		                {//调整异常数据  
		                    b[i]+=256;  
		                }  
		            }  
//		            Decoder decoder2 = Base64.getDecoder();
//		            byte[] decode = decoder2.decode(base64);
//		            ByteArrayInputStream in = new ByteArrayInputStream(decode);
//		            byte[] buffer = new byte[1024];
		            out = new FileOutputStream(file);
//		            int bytesum = 0;
//		            int byteread = 0;
//		            while ((byteread = in.read(buffer)) != -1) {
//		                bytesum += byteread;
//		                out.write(buffer, 0, byteread); // 文件写操作
//		            }
		            out.write(b);  
		            out.flush();  
		            out.close();  
		        } catch (IOException ioe) {
		            ioe.printStackTrace();
		        } finally {
		            try {
		                if (out!= null) {
		                    out.close();
		                }
		            } catch (IOException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
		        }
		        return file;
		    }
}
