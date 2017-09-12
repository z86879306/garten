package com.garten.util.lxcutil;

import java.io.*;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.jdom.JDOMException;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.garten.util.mywxpay.GetWxOrderno;
import com.garten.util.mywxpay.HttpClientConnectionManager;
import com.garten.util.mywxpay.MyPayCommonUtil;
import com.garten.util.mywxpay.MyXMLUtil;
import com.garten.util.mywxpay.PayCommonUtil;
import com.garten.util.mywxpay.RequestHandler;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
 
/**
 * 林熙程工具类
 *
 */
@Component
public class MyUtilAll {
	 public static OSSClient ossClient = new OSSClient(MyParamAll.MYOSS_ENDPOINT, MyParamAll.MYOSS_ACCESSKEYID, MyParamAll.MYOSS_ACCESSKEYSECRET);
	/**
	 * 利用OSS上传缩放后的图片
	 * @param request
	 * @param session
	 * @param phoneNumber 唯一(手机号)
	 * @return
	 */
	 public static String headImage(HttpServletRequest request,HttpSession session,String phoneNumber,String oldImg) {
		 String address=oldImg;//上传成功返回的地址,没传过来图片返回老图片地址
		 CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				//获取所有文件
				Iterator<String> iter = multiRequest.getFileNames();
					//获取当前文件
				
				System.err.println("就是准备进入"+iter.hasNext());
				while (iter.hasNext()) {//只是代表有一个传图片的坑,不一定有图片传过来
					MultipartFile file = multiRequest.getFile((String) iter.next());
					System.err.println("准备进入"+file.getOriginalFilename());
					if (file.getOriginalFilename() != null&&!file.getOriginalFilename().equals("")){//判断是不是有图片传过来
						System.err.println("确认进入"+file.getOriginalFilename());
						// endpoint以杭州为例，其它region请按实际情况填写
						String endpoint = MyParamAll.MYOSS_ENDPOINT;
						// accessKey请登录https://ak-console.aliyun.com/#/查看
						String accessKeyId = MyParamAll.MYOSS_ACCESSKEYID;
						String accessKeySecret = MyParamAll.MYOSS_ACCESSKEYSECRET;
						String bucket = MyParamAll.MYOSS_BUCKET;
						String bucketLujin = MyParamAll.MYOSS_BUCKETLUJIN;
						// 创建OSSClient实例
						OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
						// 上传文件

						   CommonsMultipartFile cf= (CommonsMultipartFile)file; 
					        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
					        File f = fi.getStoreLocation();
					        //命名图片
					        String fileName=imagesMyName(file, phoneNumber);
					        //把JSP上的图片上传到OSS
						ossClient.putObject(bucket, bucketLujin+fileName, f);
						 address=changeSmall(fileName);//获取到刚上传的图片路劲
						 //删除原来保存在OSS上的图片
						if(null!=oldImg&&!"".equals(oldImg)){
							deleteOldOSS(oldImg);
						}
						// 关闭client
						ossClient.shutdown();
					}
					
				}
				
		return address;
			}
			return address;
	}	
	 
	 /**
	  * 上传朋友圈N张图片
	  * @param request
	  * @param session
	  * @param job 接口名+上传者身份  例:worker=publishPhotoImg=workerId
	  * @param id 上传者Id
	  * @return
	  */
	 public static String photoImage(HttpServletRequest request,HttpSession session,String job) {
		 String address="";//累计上传的头像地址
		 CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				//获取所有文件
				Iterator<String> iter = multiRequest.getFileNames();
					//获取当前文件
				
				System.err.println("就是准备进入"+iter.hasNext());
				while (iter.hasNext()) {//只是代表有一个传图片的坑,不一定有图片传过来
					MultipartFile file = multiRequest.getFile((String) iter.next());
					System.err.println("准备进入"+file.getOriginalFilename());
					if (file.getOriginalFilename() != null&&!file.getOriginalFilename().equals("")){//判断是不是有图片传过来
						System.err.println("确认进入"+file.getOriginalFilename());
						// endpoint以杭州为例，其它region请按实际情况填写
						String endpoint = MyParamAll.MYOSS_ENDPOINT;
						// accessKey请登录https://ak-console.aliyun.com/#/查看
						String accessKeyId = MyParamAll.MYOSS_ACCESSKEYID;
						String accessKeySecret = MyParamAll.MYOSS_ACCESSKEYSECRET;
						String bucket = MyParamAll.MYOSS_BUCKET;
						String bucketLujin = MyParamAll.MYOSS_BUCKETLUJIN;
						// 创建OSSClient实例
						OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
						// 上传文件

						   CommonsMultipartFile cf= (CommonsMultipartFile)file; 
					        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
					        File f = fi.getStoreLocation();
					        //命名图片名字  例:worker1= 1234567890 .jpg
					        String fileName=imagesMyName(file, job+"=");
					        //把JSP上的图片上传到OSS
						ossClient.putObject(bucket, bucketLujin+fileName, f);
						System.err.println("fileName"+fileName);
						 address=address+","+changeSmall(fileName);//获取到刚上传的图片路劲
						// 关闭client
						ossClient.shutdown();
					}
					
				}
				if(!"".equals(address)){
					address=address.substring(1,address.length());//截掉第一个','
				}
		
		return address;
			}
			return address;
	}	
	/**
	 * OSS设置图片名称
	 * @param file 文件名.格式
	 * @param phoneNumber 手机号
	 * @return 手机号+时间戳.格式
	 */
	public static String imagesMyName( MultipartFile file,String phoneNumber) {
		String fileName = file.getOriginalFilename();//储存地址
			//给图片命名手机好+时间戳
			fileName=phoneNumber+System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf("."));
			return fileName;
	}	
	
	/**
	 * 删除原来OSS上的图片
	 * @param oldImg
	 * @return
	 */
	public static void deleteOldOSS( String oldImg) {
		  String old = oldImg.split(MyParamAll.MYOSS_DELETEOLD)[1];
		    System.err.println(old+"==");
			ossClient.deleteObject(MyParamAll.MYOSS_BUCKET, old);
	}	
	/**
	 * OSS替换成小图片,小图片再次上传覆盖大图片
	 * @param fileName 11212121.jpg
	 * @return
	 */
	public static String changeSmall( String fileName) {
	    GetObjectRequest req = new GetObjectRequest(MyParamAll.MYOSS_BUCKET, MyParamAll.MYOSS_BUCKETLUJIN+fileName);
	    req.setProcess(MyParamAll.MYOSS_STYLE);//给OSS上的图片设置缩放比例样式
	  ossClient.getObject(req, new File(MyParamAll.MYOSS_IMAGECASUAL));//将缩放后的图片放到指定地点
	    ossClient.putObject(MyParamAll.MYOSS_BUCKET, MyParamAll.MYOSS_BUCKETLUJIN+fileName, new File(MyParamAll.MYOSS_IMAGECASUAL));
	 /*   删除OSS上的图片ossClient.deleteObject(MyParam.MYOSS_BUCKET,  MyParam.MYOSS_BUCKETLUJIN+fileName);*/
	    new File(MyParamAll.MYOSS_IMAGECASUAL).delete();//本图片用完后删除
		System.err.println(MyParamAll.MYOSS_ADDRESS+fileName);
	    return MyParamAll.MYOSS_ADDRESS+fileName;
	}
	
	
	

	
	/**
	 * 支付 
	 * @param orderNumber
	 * @param type
	 * @param payType (支付宝,微信,余额)
	 * @return
	 */
	public static Map<String, Object> myPay(String orderNumber ,String type ,String payType) {
		Map<String,Object> result=new HashMap<String,Object>();
		if(payType.equals("支付宝")){
			 result=myAlipay(orderNumber,type,"0.01");
		}else if(payType.equals("微信")){
			 result=myWxinpay(orderNumber,type,"1");
		}else if(payType.equals("余额")){
		}
		return result;
		
	}
	/**
	 * 退款
	 * @param orderNumber
	 * @param type
	 * @param payType
	 * @return
	 * @throws Exception 
	 */
	public Map<String, String> myRefund (HttpServletRequest request,HttpServletResponse response,String orderNumber ,String price ,String payType) throws Exception {
		Map<String, String> result=new HashMap<String,String>();
		if(payType.equals("支付宝")){
			 result=myAlipayRefundRequest(orderNumber,price);
		}else if(payType.equals("微信")){
			result=myWXRefundRequest( request, response,orderNumber,price);
		}else if(payType.equals("余额")){
		}
		return result;
		
	}
	/**
	 * 微信支付
	 * @param orderNumber
	 * @param type
	 * @return
	 */
	public static Map<String, Object> myWxinpay(String orderNumber, String type,String price) {
			  Map<String, Object> resultMap = new HashMap<String, Object>();
		         SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		 	/*	String price="1";*///表示1分钱
		         parameters.put("appid", MyParamAll.MYWXIN_APPID);  
		         parameters.put("mch_id", MyParamAll.MYALIPAY_MCHID); 
		         parameters.put("nonce_str",MyPayCommonUtil.CreateNoncestr().substring(0, 9)+price);  
		         parameters.put("body",type);  
		         parameters.put("out_trade_no", orderNumber); //订单id
		         parameters.put("fee_type", "CNY");  
		         parameters.put("total_fee", price);  
		         parameters.put("spbill_create_ip","127.0.0.1");
		         parameters.put("notify_url", "http://a.yiyunwangl.com:8082/parent/wxpayyz.do"); 
		         parameters.put("trade_type", "APP");  
		        //设置签名
		         String sign = MyPayCommonUtil.createSign("UTF-8",parameters);
		         parameters.put("sign", sign);
		       //封装请求参数结束
		         String requestXML = MyPayCommonUtil.getRequestXml(parameters);  
		        //调用统一下单接口
		         String result = MyPayCommonUtil.httpsRequest(MyParamAll.MYALIPAY_UNIFIEDORDERURL, "POST", requestXML);
		         System.out.println("\n"+result);
		         try {
		         /**统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。参与签名的字段名为appId，partnerId，prepayId，nonceStr，timeStamp，package。注意：package的值格式为Sign=WXPay**/
		            Map<String, String> map = MyXMLUtil.doXMLParse(result);
		            SortedMap<Object, Object> parameterMap2 = new TreeMap<Object, Object>();  
		            parameterMap2.put("appid", MyParamAll.MYWXIN_APPID);  
		            parameterMap2.put("partnerid", MyParamAll.MYALIPAY_MCHID);  
		            parameterMap2.put("prepayid", map.get("prepay_id"));  
		            parameterMap2.put("package", "Sign=WXPay");  
		            //本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
		            parameterMap2.put("timestamp", Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0,10)));  
		            parameterMap2.put("noncestr", MyPayCommonUtil.CreateNoncestr());  
		            String sign2 = MyPayCommonUtil.createSign("UTF-8",parameterMap2);
		            parameterMap2.put("sign", sign2);  
		            resultMap.put("code","200");
		            resultMap.put("msg",parameterMap2);
		        } catch (JDOMException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        return resultMap;
		    
	}
	/**
	 * 支付宝支付
	 * @param orderNumber 订单编号
	 * @param type 订单类型
	 * @return
	 */
	public static Map<String, Object> myAlipay(String orderNumber, String type,String price) {
		//实例化客户端
				Map<String,Object> map=new HashMap<String,Object>(); 
				AlipayClient alipayClient = new DefaultAlipayClient(MyParamAll.MYALIPAY_CLIENTADDRESS,MyParamAll.MYALIPAY_APPID, MyParamAll.MYALIPAY_APPPRIVATEKEY,"json","utf-8",MyParamAll.MYALIPAY_ALIPAYPUBLICKEY, "RSA2");
				//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
				AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
				//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
				AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
				/*String price="0.01";*/
				model.setBody("成长记忆");
				model.setSubject(type);
				model.setOutTradeNo(orderNumber);
				model.setTimeoutExpress("30m");
				model.setTotalAmount(price);
				model.setProductCode("QUICK_MSECURITY_PAY");
				request.setBizModel(model);
				request.setNotifyUrl(MyParamAll.MYALIPAY_NOTIFYURL);
				try {
				        //这里和普通的接口调用不同，使用的是sdkExecute
				        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
				        map.put("result", response.getBody());
				        return map;//就是orderString 可以直接给客户端请求，无需再做处理。
				    } catch (AlipayApiException e) {
				        e.printStackTrace();
				}		return null;
		
	}
	
	
	public static Map<String, Object> myAlipayControl(String orderNumber, String type,String price,HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>(); 

		AlipayClient alipayClient = new DefaultAlipayClient(MyParamAll.MYALIPAY_CLIENTADDRESS, MyParamAll.MYALIPAY_APPID_CONTROL, MyParamAll.MYALIPAY_APPPRIVATEKEY_CONTROL,"json","utf-8", MyParamAll.MYALIPAY_ALIPAYPUBLICKEY, "RSA2"); //获得初始化的AlipayClient
		    AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
		    alipayRequest.setReturnUrl(MyParamAll.MYALIPAY_NOTIFYURL_MANAGE);
		    alipayRequest.setNotifyUrl(MyParamAll.MYALIPAY_NOTIFYURL_CONTROL);//在公共参数中设置回跳和通知地址
		    alipayRequest.setBizContent("{" +
		        "    \"out_trade_no\":\""+orderNumber+"\"," +
		        "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
		        "    \"total_amount\":"+price+"," +
		        "    \"subject\":\""+type+"\"," +
		        "    \"body\":\""+type+"\"," +
		        "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
		        "    \"extend_params\":{" +
		        "    \"sys_service_provider_id\":\"2088511833207846\"" +
		        "    }"+
		        "  }");//填充业务参数
		    String form="";
		    try {
		        form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
		        map.put("result", form);
		    } catch (AlipayApiException e) {
		        e.printStackTrace();
		    }
		    httpResponse.setContentType("text/html;charset=" + "UTF-8");
		    httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
		    httpResponse.getWriter().flush();
		    httpResponse.getWriter().close();
		    return map;
	}

	/**
	 * 支付宝退款
	 * @param orderNumber
	 * @param price
	 * @return
	 * @throws AlipayApiException
	 */
	 public  synchronized Map<String, String>  myAlipayRefundRequest(String orderNumber,String price) throws AlipayApiException{
			AlipayClient alipayClient = new DefaultAlipayClient(MyParamAll.MYALIPAY_CLIENTADDRESS,MyParamAll.MYALIPAY_APPID, MyParamAll.MYALIPAY_APPPRIVATEKEY,"json","utf-8",MyParamAll.MYALIPAY_ALIPAYPUBLICKEY, "RSA2");
	    	AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
	    	request.setBizContent("{" +
	    	"    \"out_trade_no\":\""+orderNumber+"\"," +
	    	"    \"refund_amount\":"+price+"," +
	    	"    \"refund_reason\":\"正常退款\"," +
	    	"    \"out_request_no\":\"HZ01RF001\"," +
	    	"    \"operator_id\":\"OP001\"," +
	    	"    \"store_id\":\"NJ_S_001\"," +
	    	"    \"terminal_id\":\"NJ_T_001\"" +
	    	"  }");
	    	Map<String, String> map=new HashMap<String,String>();
	    	AlipayTradeRefundResponse response = alipayClient.execute(request);
	    	if(response.isSuccess()){
	    		 map.put("result", "success");
	    		 return map;
	    	} else {
	    		 map.put("result", "failed");
	    		 return map;
	    	}
	    	
	    }
	 /**
	  * 微信退款
	  * @param request
	  * @param response
	  * @param orderNumber
	  * @param price
	  * @return
	  * @throws Exception
	  */
	 public  synchronized Map<String, String> myWXRefundRequest(HttpServletRequest request,HttpServletResponse response,String orderNumber,String price) throws Exception{
	        String filePath = MyParamAll.MYWX_CERT; //退款需要提供证书数据，所以需要根据证书路径读取证书
	        //需要退款的商户订单号，对应提交订单中的out_trade_no
	        Map<String,String> result = (Map<String, String>) wxRefund(request,response,orderNumber,price,filePath);
	        return result;
	    }
	 /**
	  * 微信退款具体步骤
	  * @param request
	  * @param response
	  * @param orderId
	  * @param total_fee
	  * @param path
	  * @return
	  * @throws Exception
	  */
	 public Map<String, String> wxRefund(HttpServletRequest request,HttpServletResponse response,
             String orderId,String total_fee ,String path) throws Exception{
		Map<String,String> result=new HashMap<String,String>(); 
		/*PageData pd = new PageData();
		pd = this.getPageData();*/
		String refundid = UUID.randomUUID().toString();
		String nonce_str = PayCommonUtil.CreateNoncestr();
		String appId="wx50a88bb93c045066";
		String shh="1464213402";
		String refund_fee=total_fee;
		/*-----  1.生成预支付订单需要的的package数据-----*/
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appId);  
		packageParams.put("mch_id", shh);  
		packageParams.put("nonce_str", nonce_str);  
		packageParams.put("op_user_id", shh);  
		packageParams.put("out_trade_no", orderId);  
		packageParams.put("out_refund_no", refundid);  
		packageParams.put("total_fee",total_fee);  
		packageParams.put("refund_fee",refund_fee);  
		/*----2.根据package生成签名sign---- */
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init("wx50a88bb93c045066", "def3512409d71b787d13a86074913515", "PShYrhYPrScAu3ZSzK5KdfE6Eb6aLsYL");
		String sign = reqHandler.createSign(packageParams);
		
		/*----3.拼装需要提交到微信的数据xml---- */
		String xml="<xml>"
		+"<appid>"+appId+"</appid>"
		+ "<mch_id>"+shh+"</mch_id>"
		+ "<nonce_str>"+nonce_str+"</nonce_str>"
		+ "<op_user_id>"+shh+"</op_user_id>"
		+ "<out_trade_no>"+orderId+"</out_trade_no>"
		+ "<out_refund_no>"+refundid+"</out_refund_no>"
		+ "<refund_fee>"+refund_fee+"</refund_fee>"
		+ "<total_fee>"+total_fee+"</total_fee>"
		+ "<sign>"+sign+"</sign>"
		+"</xml>";
		
		 /*----4.读取证书文件,这一段是直接从微信支付平台提供的demo中copy的，所以一般不需要修改---- */
		 KeyStore keyStore  = KeyStore.getInstance("PKCS12");
		 FileInputStream instream = new FileInputStream(new File(path));
		 try {
		     keyStore.load(instream, shh.toCharArray());
		 } finally {
		     instream.close();
		 }
		 // Trust own CA and all self-signed certs
		 SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, shh.toCharArray()).build();
		 // Allow TLSv1 protocol only
		 SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,new String[] { "TLSv1" },null,
		         SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		 CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		
		 /*----5.发送数据到微信的退款接口---- */
		 String url="https://api.mch.weixin.qq.com/secapi/pay/refund";
		 HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
		 httpost.setEntity(new StringEntity(xml, "UTF-8"));
		 HttpResponse weixinResponse = httpClient.execute(httpost);
		 String jsonStr = EntityUtils.toString(weixinResponse.getEntity(), "UTF-8");
		 System.err.println(jsonStr);
		 Map map = GetWxOrderno.doXMLParse(jsonStr);
		 if("success".equalsIgnoreCase((String) map.get("return_code"))){
		     System.err.println("退款成功");
		     result.put("returncode", "ok");
		     result.put("returninfo", "退款成功");
		 }else{
		     System.err.println("退款失败111");
		     result.put("returncode", "error");
		     result.put("returninfo", "退款失败");
		 }
		
		return result;
		
		}
	
	 /** 
	     * 上传图片至OSS 
	     * @param ossClient  oss连接 
	     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg） 
	     * @param bucketName  存储空间 
	     * @param folder 模拟文件夹名 如"qj_nanjing/" 
	     * @return String 返回的唯一MD5数字签名 
	     * */  
	    public static  String uploadObject2OSS(OSSClient ossClient, File file, String bucketName, String folder) {  
	        String resultStr = null;  
	        String result = null;  
	        try {  
	            //以输入流的形式上传文件  
	            InputStream is = new FileInputStream(file);  
	            //文件名  
	            String fileName = file.getName();   
	            //文件大小  
	            Long fileSize = file.length();   
	            //创建上传Object的Metadata    
	            ObjectMetadata metadata = new ObjectMetadata();  
	            //上传的文件的长度  
	            metadata.setContentLength(is.available());    
	            //指定该Object被下载时的网页的缓存行为  
	            metadata.setCacheControl("no-cache");   
	            //指定该Object下设置Header  
	            metadata.setHeader("Pragma", "no-cache");    
	            //指定该Object被下载时的内容编码格式  
	            metadata.setContentEncoding("utf-8");    
	            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，  
	            //如果没有扩展名则填默认值application/octet-stream  
	            metadata.setContentType(getContentType(fileName));    
	            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）  
	            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");    
	            //上传文件   (上传文件流的形式) 
	            result = folder +"gartenId"+System.currentTimeMillis()+ fileName;
	            PutObjectResult putResult = ossClient.putObject(bucketName,MyParamAll.MYOSS_BUCKETLUJIN+result, is, metadata);    
	            //解析结果  
	            resultStr = putResult.getETag();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	             System.err.println( "上传阿里云OSS服务器异常." + e.getMessage());
	        }  
	        return MyParamAll.MYOSS_ADDRESS+result;  
	    }  
	  
	    /** 
	     * 通过文件名判断并获取OSS服务文件上传时文件的contentType 
	     * @param fileName 文件名 
	     * @return 文件的contentType 
	     */  
	    public static  String getContentType(String fileName){  
	        //文件的后缀名  
	        String fileExtension = fileName.substring(fileName.lastIndexOf("."));  
	        if(".bmp".equalsIgnoreCase(fileExtension)) {  
	            return "image/bmp";  
	        }  
	        if(".gif".equalsIgnoreCase(fileExtension)) {  
	            return "image/gif";  
	        }  
	        if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {  
	            return "image/jpeg";  
	        }  
	        if(".html".equalsIgnoreCase(fileExtension)) {  
	            return "text/html";  
	        }  
	        if(".txt".equalsIgnoreCase(fileExtension)) {  
	            return "text/plain";  
	        }  
	        if(".vsd".equalsIgnoreCase(fileExtension)) {  
	            return "application/vnd.visio";  
	        }  
	        if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {  
	            return "application/vnd.ms-powerpoint";  
	        }  
	        if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {  
	            return "application/msword";  
	        }  
	        if(".xml".equalsIgnoreCase(fileExtension)) {  
	            return "text/xml";  
	        }  
	        //默认返回类型  
	        return "image/jpeg";  
	    }  
	  

}