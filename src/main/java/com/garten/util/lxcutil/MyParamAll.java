package com.garten.util.lxcutil;

import java.io.*;
import java.net.URL;
import java.util.Date;
 

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
 
/**
 * 林熙程工具类参数
 *
 */
public class MyParamAll {
	/**
	 * OSS
	 */
	//OSS bucket名字
	public static final String MYOSS_BUCKET = "ahczjy";
	//OSS bucket下的具体路劲
	public static final String MYOSS_BUCKETLUJIN = "cleaning/images/";
	//OSS enpoint值(杭州)
	public static final String MYOSS_ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
	//OSS accessKeyId
	public static final String MYOSS_ACCESSKEYID = "LTAIdMTmLqkLxtpm";
	//OSS accessKeySecret
	public static final String MYOSS_ACCESSKEYSECRET = "b8OdQximyU1rY6FmbgYz2PEqf3LKFv";
	//OSS imageCasual 小图片临时存放地址方便上传小图片
	public static final String MYOSS_IMAGECASUAL = "C:\\success.jpg";
	//OSS OSS小图片地址
	public static final String MYOSS_ADDRESS = "http://ahczjy.oss-cn-hangzhou.aliyuncs.com/cleaning/images/";
	//OSS style 设置图片缩放 按高度100像素等比缩放  format,gif gif保存为gif  其他按原图保存
	public static final String MYOSS_STYLE = "image/resize,h_1000/format,gif";
	//OSS 删除原图片需要的切割字符串
	public static final String MYOSS_DELETEOLD = "oss-cn-hangzhou.aliyuncs.com/";
	/**
	 * 支付(微信,支付宝,余额)
	 */
	
		
	//Alipay 支付宝new DefaultAlipayClient的固定网址
	public static final String MYALIPAY_CLIENTADDRESS = "https://openapi.alipay.com/gateway.do";
	//Alipay APP_ID
	public static final String MYALIPAY_APPID = "2017080408035081";
	public static final String MYALIPAY_APPID_CONTROL = "2017080708077599";

	//Alipay APP_PRIVATE_KEY 应用私钥2048位 利用工具生成
	public static final String MYALIPAY_APPPRIVATEKEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCtYUDYm2ANfxyNtUqZpbaU1eq3Q5GbmRVm1sJoipO/6wxBZY4hbd/4lkxS/VuzL1rJmO6DhMcBLIXuShtGysNAJBMOFRSrLkCo5ZDXDwpkeJAd1Y3ZKyTp5w2XgQAEZSZay/kBTfZhQHl33hvyDAw9aIi/i4AB4elQLFiXAThubkJHPgA6l9VlDJ0EF1BiKeoEk67go4zCHXagOjXqvMcdzhcwrFNBBvRochM2g9jOWfPOxhaLWf/N0XKYmYZG18NBDHC5lc6jIxr4FzEgRANeqL2HON95GAljYYET+ogfv4pyu8agxn767C+8dEi0m3hwz/cEpTWd6hPu6yCa2DCvAgMBAAECggEBAKfO9Ftx7wLBH5rMeBrEODZP4XBlNp4aCYvSmrFiRuS/E8qMB4e1d8x7c/3y+HkygTWUDm4+6fnp5Osg0Muw3k/LWqILp0iTaMPQBWMRTKa3xg0KL9OcnYrCNfaIRcL7P6cruqMXmTrBk49/6x5XqNUawxhZZZjs1cviUBA/fyXpef/r6G7g2Ct8GaqezwQCMo0K3Ya0mQDmApbSPJaV98em8DdVUR/DV35r5qkrf0ZDXzdS1CqCUboMRq54vfBah8cPfTIWgwkZr+kSan5wCjJHhvpWZqN3hNDUpimNvPLNgetRwlep4YlRIo5MAqNAOOrbbb/qhsquBRQ5K0KBoNkCgYEA6Cf9rabVkQZLPgvXS6vrRw9fEqRZ+NSJWWGyUB9nA7s6oyIoKRSaZk5dhUBbiK/XcA35wq69DwyTFaCxLMX+bJzXeD/KcQyUQY6WpnX0GjnULoLfHZ76j3MmssHzUt3nmUAfLQJdS67zqkfQz4snwEkxpoq7VXp5p+V/diAGZnMCgYEAvy/gHBkBo2bWkBJGirdME+vTDoRqi0oMczzKxkp4mn/DvkJjJj4Ew2aTJc+x4+0duHqmeyyGjGWrYKA8BRWbC0j8ofjuDKh8i+drmOFX4QfMG0KouCeBrp0CCYnAgWRVE2gSgnzJf+7tzGmpqBHmMUJWrdY9x1ZM0o4m0eZHgdUCgYAHYA3dEGnEe7C0PegPDV0iGsjobLQ3BXl/j2z8wqKhKbeJKzKfa3okOeUasJq30u5DLw56MtbqvXmGvF4kj/2vMaReu5gQ01d6Za8/hovEsYWeHKY96KkJGZ1nHGZAgbAz3CehZGvWVa6EAFgP39nZb/nV0p507rkirlXISOgrJQKBgQC27Oa/24k+v5mRtXq7JI/tC5kf7cBHT21EISleukhUguRVuQ1GvNYv44ftCKELFId4SBncBokh8fEcfZOPxdFX5diaXSPfBqJL9ft0Bj/f9mbqukfWEy64VE9rstp2svnXnJ8BLMZa2s+54mtKfP3DqdZnc3IetMrCNvKbqHpIlQKBgF0dyzrQg/hUdxJPgZ2jvUVowHUOGNl1DdW2EegC7htISmTzdfofT5MYykeQIV8z9qr7+uo2DJ6IoGUjxxsfD5u3R8W8rSpaLxGk0mHtSKAaHWjGqkjev7c2jIhuV/EEsboj5APxE/xOp1DQwlHkv3Zq81eqSPRMwJEbBYx/+PrB";
	public static final String MYALIPAY_APPPRIVATEKEY_CONTROL = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCtYUDYm2ANfxyNtUqZpbaU1eq3Q5GbmRVm1sJoipO/6wxBZY4hbd/4lkxS/VuzL1rJmO6DhMcBLIXuShtGysNAJBMOFRSrLkCo5ZDXDwpkeJAd1Y3ZKyTp5w2XgQAEZSZay/kBTfZhQHl33hvyDAw9aIi/i4AB4elQLFiXAThubkJHPgA6l9VlDJ0EF1BiKeoEk67go4zCHXagOjXqvMcdzhcwrFNBBvRochM2g9jOWfPOxhaLWf/N0XKYmYZG18NBDHC5lc6jIxr4FzEgRANeqL2HON95GAljYYET+ogfv4pyu8agxn767C+8dEi0m3hwz/cEpTWd6hPu6yCa2DCvAgMBAAECggEBAKfO9Ftx7wLBH5rMeBrEODZP4XBlNp4aCYvSmrFiRuS/E8qMB4e1d8x7c/3y+HkygTWUDm4+6fnp5Osg0Muw3k/LWqILp0iTaMPQBWMRTKa3xg0KL9OcnYrCNfaIRcL7P6cruqMXmTrBk49/6x5XqNUawxhZZZjs1cviUBA/fyXpef/r6G7g2Ct8GaqezwQCMo0K3Ya0mQDmApbSPJaV98em8DdVUR/DV35r5qkrf0ZDXzdS1CqCUboMRq54vfBah8cPfTIWgwkZr+kSan5wCjJHhvpWZqN3hNDUpimNvPLNgetRwlep4YlRIo5MAqNAOOrbbb/qhsquBRQ5K0KBoNkCgYEA6Cf9rabVkQZLPgvXS6vrRw9fEqRZ+NSJWWGyUB9nA7s6oyIoKRSaZk5dhUBbiK/XcA35wq69DwyTFaCxLMX+bJzXeD/KcQyUQY6WpnX0GjnULoLfHZ76j3MmssHzUt3nmUAfLQJdS67zqkfQz4snwEkxpoq7VXp5p+V/diAGZnMCgYEAvy/gHBkBo2bWkBJGirdME+vTDoRqi0oMczzKxkp4mn/DvkJjJj4Ew2aTJc+x4+0duHqmeyyGjGWrYKA8BRWbC0j8ofjuDKh8i+drmOFX4QfMG0KouCeBrp0CCYnAgWRVE2gSgnzJf+7tzGmpqBHmMUJWrdY9x1ZM0o4m0eZHgdUCgYAHYA3dEGnEe7C0PegPDV0iGsjobLQ3BXl/j2z8wqKhKbeJKzKfa3okOeUasJq30u5DLw56MtbqvXmGvF4kj/2vMaReu5gQ01d6Za8/hovEsYWeHKY96KkJGZ1nHGZAgbAz3CehZGvWVa6EAFgP39nZb/nV0p507rkirlXISOgrJQKBgQC27Oa/24k+v5mRtXq7JI/tC5kf7cBHT21EISleukhUguRVuQ1GvNYv44ftCKELFId4SBncBokh8fEcfZOPxdFX5diaXSPfBqJL9ft0Bj/f9mbqukfWEy64VE9rstp2svnXnJ8BLMZa2s+54mtKfP3DqdZnc3IetMrCNvKbqHpIlQKBgF0dyzrQg/hUdxJPgZ2jvUVowHUOGNl1DdW2EegC7htISmTzdfofT5MYykeQIV8z9qr7+uo2DJ6IoGUjxxsfD5u3R8W8rSpaLxGk0mHtSKAaHWjGqkjev7c2jIhuV/EEsboj5APxE/xOp1DQwlHkv3Zq81eqSPRMwJEbBYx/+PrB";

	//Alipay ALIPAY_PUBLIC_KEY支付宝公钥
	public static final String MYALIPAY_ALIPAYPUBLICKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlBjo88f7bFy5267QD4rCPYaH7ZOD881n0hAPVfOO4Mcxq1tyL7tGLEWp2Q3ZmaGLmZREMGCJ1h8ol3ruTOx1Y9sJc+9XPhyQG9j31rUSNMbe8KhIWiCQhe1fcHs5j3kNCU/t8goELb8QZBfmlBiMDPmpYuju/ow1Rpf22tnBWrEt+KJgmC5D/E5Vu7PdPzOT4zwOPCR6WJtXhDP+aqQ20QlfDHZ/KhytfRCWLnNbhXIQzdSd1ltX2NBykaZUVuZFar+WocT55nWgbDq4dtKssTuL7EUTgX2bXv50tBZ2d3dx6QVndAhR5tINiT+KzCFWmROuQLBOTM1yS8cDp81pAQIDAQAB";
	//Alipay NotifyUrl支付宝回调函数						  
	public static final String MYALIPAY_NOTIFYURL = "http://cms.ahczjy.cc:8081/parent/alipayyz.do";
	public static final String MYALIPAY_NOTIFYURL_CONTROL = "http://cms.ahczjy.cc:8081/smallcontrol/alipayyz.do";
	//幼儿园后台主页面
	public static final String MYALIPAY_NOTIFYURL_MANAGE = "http://cms.ahczjy.cc:8081/smallcontrol/manage.html#/payQuery";
	//代理商购买信用额度的回调
	public static final String MYALIPAY_NOTIFYURL_AGENT = "http://cms.ahczjy.cc:8081/agent/alipayyz.do";
	//代理商购买信用额度完成的会跳
	public static final String MYALIPAY_NOTIFYURL_MANAGE_AGENT = "http://cms.ahczjy.cc:8081/agentcontrol/manage.html";

	//Wxin 微信appid
	public static final String MYWXIN_APPID = "wx5016949865cc41f1";
	//Wxin mch_id微信商户ID					   
	public static final String MYWXIN_MCHID = "1487640302";
	//Wxin app_secret
	public static final String MYWXIN_PARENT_APP_SECRET = "1cde90fedc5550b6606c2daf11f5dd97";
	//Wxin api_key
	public static final String MYWXIN_PARENT_API_KEY = "07282253834529058003629853775552";
	//Wxin ip
	public static final String MYWXIN_IP ="127.0.0.1";
	//Wxin ip
	public static final String MYWXIN_AGENT_CREDIT ="http://www.baidu.com";
	//Wxin 统一下单地址
	public static final String MYWXIN_UNIFIEDORDERURL = "https://api.mch.weixin.qq.com/pay/unifiedorder ";/**
	 * 退款(微信,支付宝,余额)
	 */
	//WX 微信退款证书
	public static final String MYWX_CERT= "C:\\apiclient_cert.p12";
	
	/**
	 * 极光推送
	 */
	public static final String JIGUANG_PRINCIPAL_APP= "201730daa413a52420cf1da1";
	public static final String JIGUANG_PRINCIPAL_MASTER= "9ba4d827c9d05b7d8c4ced94";
	public static final String JIGUANG_WORKER_APP= "8165ed87a4759bae3c5f13b1";
	public static final String JIGUANG_WORKER_MASTER= "e1910ba737b3b040d0678362";
	public static final String JIGUANG_PARENT_APP= "1b8fed66695934fb27ea9837";
	public static final String JIGUANG_PARENT_MASTER= "70a03e25b17cc6223340569f";
	public static final String JIGUANG_RECIPE_MESSAGE= "您有新的食谱!请到食谱界面查看";
	public static final String JIGUANG_LESSON_MESSAGE= "课程已更新!请到课程界面查看";
	public static final String JIGUANG_WORKER_DAIJIE_MESSAGE= "您有新的代接申请!请查看";
	public static final String JIGUANG_PARENT_DAIJIE_MESSAGE= "您申请的代接已成功";
	public static final String JIGUANG_GARENT_ACTIVITY_MESSAGE= "有新的校园活动哦！~请查看";

	/**
	 * 云通讯  (短信发送)
	 */
	public static final String YTX_ACCOUNT_SID="8a216da85635b77e0156442fa9ec05a7";
	public static final String YTX_AUTH_TOKEN="457f5a1797604e0c8a961ee7c1d0dde9";
	public static final String YTX_APPID="8a216da85635b77e0156442faa8905ad";
	//云通讯  老板电话
	public static final String YTX_DUANXIN_CONTROL_PHONE="13911203746";
	//云通讯  服务完成
	public static final String YTX_DUANXIN_FWWC="200937";
	//云通讯 后台派单
	public static final String YTX_DUANXIN_HTPD="201805";
	//云通讯 取消订单
	public static final String YTX_DUANXIN_QXDD="200941";
	//云通讯 用户注册成功
	public static final String YTX_DUANXIN_ZC="205495";
	//云通讯 8种订单
	public static final String YTX_DUANXIN_DD="202270";
	
	//云通讯 家长添加宝宝
	public static final String YTX_DUANXIN_GLBB="105828";
	//云通讯 获取验证码
	public static final String YTX_DUANXIN_YZM="105825";
	//云通讯 差价订单
	public static final String YTX_DUANXIN_CHAJIA="201219";
	//云通讯 售后回复//datas 1订单号 2 订单标题
	public static final String YTX_DUANXIN_SHHF="208482";

	/**
	 * [0]	在MyParamAll中加入环信
	 */
	public static final String ORG_NAME= "1162170804115123";
	public static final String APP_NAME= "chengzhangjiyi";
	public static final String BASEBODY= "grant_type=client_credentials&client_id=YXA6V0QPwEdVEee_67GekiVw0w&client_secret=YXA6Sm0V3Uks4ipbuJ5vAD654KcJ5Xo";
	public static final String CLIENT_SECRET= "YXA6e9pX5ahiUYeDNpjfqShQd4yPcZc";
	public static final String CLIENT_ID= "YXA65uRHUHjqEeeHWsFUXttZOA";
	public static final String ACCESS_TOKEN="Bearer YWMtT4xOrJHbEeeeQufoPlUplAAAAAAAAAAAAAAAAAAAAAHm5EdQeOoR54dawVRe21k4AgMAAAFeT7Lk1wBPGgAobMobjBeZtwcu6H93Lr7urf3eSDDCkw7oeTzEs_ZlWQ";
	public static final String HUANXIN_BASE= "http://a1.easemob.com/";
	
}