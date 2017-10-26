package com.garten.util.myutil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloopen.rest.sdk.utils.encoder.BASE64Encoder;
import com.garten.model.agent.AgentInfo;
import com.garten.model.agent.AgentOrderAll;
import com.garten.model.agent.SaleServiceAll;
import com.garten.model.agent.WithdrawAll;
import com.garten.model.baby.BabyLeaveLog;
import com.garten.model.baby.BabyPerformanceLog;
import com.garten.model.company.Employee;
import com.garten.model.garten.GartenCharge;
import com.garten.model.garten.GartenClass;
import com.garten.model.garten.GartenInfo;
import com.garten.model.garten.GartenLesson;
import com.garten.model.garten.GartenPhotos;
import com.garten.model.garten.PhotoDianZan;
import com.garten.model.other.Comment;
import com.garten.model.parent.ParentInfo;
import com.garten.model.worker.WorkerCheckLog;
import com.garten.model.worker.WorkerInfo;
import com.garten.model.worker.WorkerLeaveLog;
import com.garten.service.AgentService;
import com.garten.service.BigcontrolService;
import com.garten.service.ParentService;
import com.garten.service.PrincipalService;
import com.garten.service.WorkerService;
import com.garten.util.AA;
import com.garten.util.duanxin.CCPRestSDK;
import com.garten.util.lxcutil.MyParamAll;
import com.garten.util.lxcutil.MyUtilAll;
import com.garten.util.page.MyPage;
import com.garten.vo.agent.AgentAuditMessage;
import com.garten.vo.baby.BabyLeaveLogAll;
import com.garten.vo.baby.UnusualAll;
import com.garten.vo.parent.ParentInfoShort;
import com.garten.vo.parent.ParentInfoShortZimu;
import com.garten.vo.smallcontrol.GartenClassName;
import com.garten.vo.smallcontrol.OrderAll;
import com.garten.vo.teacher.ActivityDetailAll;
import com.garten.vo.teacher.BabyCheckLogAll;
import com.garten.vo.teacher.BabyPerformanceLogAll;
import com.garten.vo.teacher.ClassManage;
import com.garten.vo.teacher.Daijie;
import com.garten.vo.teacher.GartenStartEnd;
import com.garten.vo.teacher.PhotoAll;
import com.garten.vo.teacher.WorkerCheckLogAll;
import com.garten.vo.teacher.WorkerInfoShort;
import com.garten.vo.teacher.WorkerInfoShortZimu;
import com.garten.vo.teacher.WorkerLeaveLogPrin;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

@Component
public  class MyUtil implements ApplicationContextAware{
	 private static ApplicationContext appCtx;
	    public void setApplicationContext(ApplicationContext applicationContext)  {
	        appCtx = applicationContext;
	    }
	    public static ApplicationContext getApplicationContext() {
	        return appCtx;
	    }
	    public static Object getBean(String beanName) {
	        return appCtx.getBean(beanName);
	    }
	    
	    
	public static String getDecimal(Double number){
		DecimalFormat abc=new DecimalFormat("0.00");
				return abc.format(number);
	}
	//把班级大中小转int  大班:3 中班:2  小班:1
	public static int changeClassName(GartenClass classes) {
		int a="大班".equals(classes.getLeadGrade())?3:"中班".equals(classes.getLeadGrade())?2:1;
		return a;
	}
	
	public static List<GartenClassName> paixuClass(List<GartenClassName> classes) {
		for (int i = 0; i < classes.size(); i++) {   
	        int k = i;   
	        for (int j = classes.size() - 1; j >i; j--)  {   
	            if (changeClassName(classes.get(j)) < changeClassName(classes.get(k)))  k = j;   
	        }   
	        GartenClassName  temp = classes.get(i);   
	        classes.set(i,classes.get(k));   
	        classes.set(k,temp);   
	    }   

		
		return classes;
	}
	
	public static List<?> paixuBabyCheckLog(List<BabyCheckLogAll> listall){
		for (int i = 0; i < listall.size(); i++) {   
	        int k = i;   
	        for (int j = listall.size() - 1; j >i; j--)  {   
	            if (Long.valueOf(listall.get(j).getBabyId()) < Long.valueOf(listall.get(k).getBabyId()))  k = j;   
	        }   
	        BabyCheckLogAll  temp = listall.get(i);   
	        listall.set(i,listall.get(k));   
	        listall.set(k,temp);   
	    }   
		List<BabyCheckLogAll> listall1=new ArrayList<BabyCheckLogAll>();
		List<BabyCheckLogAll> listall2=new ArrayList<BabyCheckLogAll>();

		for(BabyCheckLogAll bc:listall){
			//有无晨检
			System.err.println(bc);
			if(bc.getTemperature().compareTo(new BigDecimal("0.0"))==0){
				listall1.add(bc);
			}else{
				listall2.add(bc);
			}
		}
		listall1.addAll(listall2);
		return listall1;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static Map<String, Map> paixuParentByZiMu(List<ParentInfoShort> list,List<WorkerInfoShort> list2,Integer type,Integer gartenId){
		ParentService parentService = (ParentService) MyUtil.getBean("parentService");
		String str="啊";
		String key="";
		 List<Object> a=new ArrayList<Object>();
		 List<Object> b=new ArrayList<Object>();
		 List<Object> c=new ArrayList<Object>();
		 List<Object> d=new ArrayList<Object>();
		 List<Object> e=new ArrayList<Object>();
		 List<Object> f=new ArrayList<Object>();
		 List<Object> g=new ArrayList<Object>();
		 List<Object> h=new ArrayList<Object>();
		 List<Object> i=new ArrayList<Object>();
		 List<Object> j=new ArrayList<Object>();
		 List<Object> k=new ArrayList<Object>();
		 List<Object> l=new ArrayList<Object>();
		 List<Object> m=new ArrayList<Object>();
		 List<Object> n=new ArrayList<Object>();
		 List<Object> o=new ArrayList<Object>();
		 List<Object> p=new ArrayList<Object>();
		 List<Object> q=new ArrayList<Object>();
		 List<Object> r=new ArrayList<Object>();
		 List<Object> s=new ArrayList<Object>();
		 List<Object> t=new ArrayList<Object>();
		 List<Object> u=new ArrayList<Object>();
		 List<Object> v=new ArrayList<Object>();
		 List<Object> w=new ArrayList<Object>();
		 List<Object> x=new ArrayList<Object>();
		 List<Object> y=new ArrayList<Object>();
		 List<Object> z=new ArrayList<Object>();
		 List<Object> all=new ArrayList<Object>();
		 
		 Map<String,List> result=new TreeMap<String,List>();
		 Map<String,List> resultAndroid=new TreeMap<String,List>();
		 Map<String,Map> resultContent=new TreeMap<String,Map>();
		 resultContent.put("result", result);
		 resultContent.put("resultAndroid", resultAndroid);
		 resultAndroid.put("result", all);
		 result.put("A", a);
		 result.put("B", b);
		 result.put("C", c);
		 result.put("D", d);
		 result.put("E", e);
		 result.put("F", f);
		 result.put("G", g);
		 result.put("H", h);
		 result.put("I", i);
		 result.put("J", j);
		 result.put("K", k);
		 result.put("L", l);
		 result.put("M", m);
		 result.put("N", n);
		 result.put("O", o);
		 result.put("P", p);
		 result.put("Q", q);
		 result.put("R", r);
		 result.put("S", s);
		 result.put("T", t);
		 result.put("U", u);
		 result.put("V", v);
		 result.put("W", w);
		 result.put("X", x);
		 result.put("Y", y);
		 result.put("Z", z);
		 String[] babyNames=null;
		 String[] babyIds=null;
		 String[] relations=null;
		 ParentInfoShortZimu realParent=null;
		 WorkerInfoShortZimu realWorker=null;

		 if(0==type){
			 for(ParentInfoShort index:list){//遍历家长有宝宝在这个幼儿园的家长
				 
					babyNames=index.getBabyName().split(",");
					babyIds=index.getBabyId().split(",");
					relations=index.getIdentity().split(",");
					for(int indexI=0;indexI<babyNames.length;indexI++){
						realParent=new ParentInfoShortZimu(babyNames[indexI],babyIds[indexI],relations[indexI],index.getParentName(),index.getPhoneNumber(),index.getParentHead(),index.getParentId(),index.getAddress());
						ClassManage classManage=parentService.findBaby(Integer.valueOf(babyIds[indexI]));
						if(classManage.getGartenId()==gartenId){
							key=String.valueOf((char)('A'+AA.cn2py(babyNames[indexI])-97));
							realParent.setZimu(key);
							all.add(realParent);
							result.get(key).add(realParent);
						}
						
					}
					
				}
		 }else{
			 for(WorkerInfoShort index:list2){
					str=index.getWorkerName();
					key=String.valueOf((char)('A'+AA.cn2py(str)-97));
					realWorker=new WorkerInfoShortZimu( index.getGartenId(), index.getWorkerId(), index.getWorkerName(), index.getPhoneNumber(), index.getSex(),
							 index.getAge(), index.getClassId(),  index.getEducation(),  index.getCertificate(),  index.getChinese(), index.getJob() ,
							index.getFlowers() , index.getToken() , index.getTokenTime() , index.getHeadImg() ,  key);
					result.get(key).add(realWorker);
					all.add(realWorker);
				}
		 }
		
		return resultContent;
		
	}
	
	//根据这天所有晨检记录 获取未同意的异常 同意的异常
	/*public static List<?> yichangBabyCheckLog(List<BabyCheckLogAll> listall, GartenStartEnd gartenStartEnd ) throws ParseException{
		//根据宝宝Id排序 
		WorkerService ckps = (WorkerService) MyUtil.getBean("workerService");
		int lateType=10;
		for (int i = 0; i < listall.size(); i++) {   
	        int k = i;   
	        for (int j = listall.size() - 1; j >i; j--)  {   
	            if (Long.valueOf(listall.get(j).getBabyId()) < Long.valueOf(listall.get(k).getBabyId()))  k = j;   
	        }   
	        BabyCheckLogAll  temp = listall.get(i);   
	        listall.set(i,listall.get(k));   
	        listall.set(k,temp);   
	    }   
		List<BabyLate> listall1=new ArrayList<BabyLate>();
		List<BabyLate> listall2=new ArrayList<BabyLate>();
		List<BabyLate> listall3=new ArrayList<BabyLate>();
		//获取迟到异常   早退异常
		for(BabyCheckLogAll bc:listall){
				//有无迟到 早退
				//迟到1.有签到
				//   2到达时间的秒数大于幼儿园规定时间   定义为签到异常  如果数据原来记录为正常改为异常  标记异常类型为lateType0
				//早退1.有签退
				//   2.离校时间小于 幼儿园规定时间  定义为签退异常  如果数据原来记录为正常改为异常  lateType 0:迟到 1:早退  2:全异常
				if(null!=bc.getArriveTime()){
					if(bc.getArriveTime()-getYMDLong(bc.getArriveTime())-getSecond(gartenStartEnd.getStart())>0){
						lateType=0;
						if(0==bc.getStateLate()){ckps.updateLate( bc.getCheckId(),0);}
					}
				}
				if(null!=bc.getLeaveTime()){
					if(bc.getLeaveTime()-getYMDLong(bc.getArriveTime())-getSecond(gartenStartEnd.getEnd())<0){
						lateType= lateType==0?2:1;
						if(0==bc.getStateEarly()){ckps.updateLate( bc.getCheckId(),1);}
						}
				}
				if(10!=lateType){
					listall1.add(new BabyLate(bc.getCheckId(),lateType,bc.getBabyId(),bc.getBabyName(),bc.getArriveImg(),bc.getArriveTime(),bc.getStateLate(),bc.getLeaveImg(),bc.getLeaveTime(),bc.getStateEarly(),bc.getBabyHead()));
					lateType=10;
				}
				
		}
		
		for(BabyLate bl:listall1){
			if(0==bl.getLate()||0==bl.getEarly()||1==bl.getLate()||1==bl.getEarly()){
				listall2.add(bl);
			}else{
				listall3.add(bl);
			}
		}
		listall2.addAll(listall3);	
		return listall2;
		
	}*/
		
		// 职工 根据这天所有晨检记录 获取未同意的异常 同意的异常
		/*public static List<?> yichangWorkerCheckLog(List<WorkerCheckLogAll> listall, GartenStartEnd gartenStartEnd ) throws ParseException{
			//根据宝宝Id排序 
			WorkerService ckps = (WorkerService) MyUtil.getBean("workerService");
			int lateType=10;
			for (int i = 0; i < listall.size(); i++) {   
		        int k = i;   
		        for (int j = listall.size() - 1; j >i; j--)  {   
		            if (Long.valueOf(listall.get(j).getWorkerId()) < Long.valueOf(listall.get(k).getWorkerId()))  k = j;   
		        }   
		        WorkerCheckLogAll  temp = listall.get(i);   
		        listall.set(i,listall.get(k));   
		        listall.set(k,temp);   
		    }   
			List<BabyLate> listall1=new ArrayList<BabyLate>();
			List<BabyLate> listall2=new ArrayList<BabyLate>();
			List<BabyLate> listall3=new ArrayList<BabyLate>();
			//获取迟到异常   早退异常
			for(WorkerCheckLogAll bc:listall){
					//有无迟到 早退
					//迟到1.有签到
					//   2到达时间的秒数大于幼儿园规定时间   定义为签到异常  如果数据原来记录为正常改为异常  标记异常类型为lateType0
					//早退1.有签退
					//   2.离校时间小于 幼儿园规定时间  定义为签退异常  如果数据原来记录为正常改为异常  lateType 0:迟到 1:早退  2:全异常
					if(null!=bc.getArriveTime()){
						if(bc.getArriveTime()-getYMDLong(bc.getArriveTime())-getSecond(gartenStartEnd.getStart())>0){
							lateType=0;
							if(0==bc.getStateLate()){ckps.updateLateWorker( bc.getCheckId(),0);}
						}
					}
					if(null!=bc.getLeaveTime()){
						if(bc.getLeaveTime()-getYMDLong(bc.getArriveTime())-getSecond(gartenStartEnd.getEnd())<0){
							lateType= lateType==0?2:1;
							if(0==bc.getStateEarly()){ckps.updateLateWorker( bc.getCheckId(),1);}
							}
					}
					if(10!=lateType){
						listall1.add(new BabyLate(bc.getCheckId(),lateType,bc.getWorkerId(),bc.getWorkerName(),bc.getArriveImg(),bc.getArriveTime(),bc.getStateLate(),bc.getLeaveImg(),bc.getLeaveTime(),bc.getStateEarly()));
						lateType=10;
					}
					
			}
			//遍历所有异常 排序 为同意的异常放前面
		for(BabyLate bl:listall1){
			if(0==bl.getLate()||0==bl.getEarly()||1==bl.getLate()||1==bl.getEarly()){
				listall2.add(bl);
			}else{
				listall3.add(bl);
			}
		}
		listall2.addAll(listall3);	
		return listall2;
		
	}*/
	
	
	public static Map<String, Object> paixuDaijieLog(List<Daijie> listall,Integer type,Long time) throws ParseException{
		for (int i = 0; i < listall.size(); i++) {   
	        int k = i;   
	        for (int j = listall.size() - 1; j >i; j--)  {   
	            if (Long.valueOf(listall.get(j).getDaijieId()) > Long.valueOf(listall.get(k).getDaijieId()))  k = j;   
	        }   
	        Daijie  temp = listall.get(i);   
	        listall.set(i,listall.get(k));   
	        listall.set(k,temp);   
	    }   
		List<Daijie> listall1=new ArrayList<Daijie>();
		List<Daijie> listall2=new ArrayList<Daijie>();
		List<Daijie> listall3=new ArrayList<Daijie>();
		for(Daijie bc:listall){
			if(getYMDLong(bc.getTime())==getYMDLong(time)){
				if(0==bc.getDaijieState()/*||1==bc.getDaijieState()*/){
					listall1.add(bc);
				}else{
					listall2.add(bc);
				}
			}
			
		}
		listall3.addAll(listall1);
		listall3.addAll(listall2);
		//type=0返回申请中的代接  1返回已接的代接 2返回所有代接
		Map<String ,Object> result=putMapParams("info", 0==type?listall1:(1==type?listall2:listall3));
		return result;
		
	}
	
	public static List<?> paixuWorkerCheckLog(List<WorkerCheckLog> listall){
		for (int i = 0; i < listall.size(); i++) {   
	        int k = i;   
	        for (int j = listall.size() - 1; j >i; j--)  {   
	            if (Long.valueOf(listall.get(j).getTime()) < Long.valueOf(listall.get(k).getTime()))  k = j;   
	        }   
	        WorkerCheckLog  temp = listall.get(i);   
	        listall.set(i,listall.get(k));   
	        listall.set(k,temp);   
	    }   
		return listall;
		
	}
	public static Map<String, Object> paixuBabyPerformanceLog(List<BabyPerformanceLogAll> listall,Integer type){
		for (int i = 0; i < listall.size(); i++) {   
	        int k = i;   
	        for (int j = listall.size() - 1; j >i; j--)  {   
	            if (Long.valueOf(listall.get(j).getBabyId()) < Long.valueOf(listall.get(k).getBabyId()))  k = j;   
	        }   
	        BabyPerformanceLogAll  temp = listall.get(i);   
	        listall.set(i,listall.get(k));   
	        listall.set(k,temp);   
	    }   
		List<BabyPerformanceLogAll> listall1=new ArrayList<BabyPerformanceLogAll>();
		List<BabyPerformanceLogAll> listall2=new ArrayList<BabyPerformanceLogAll>();

		for(BabyPerformanceLogAll bc:listall){
			if(bc.getEat().compareTo(new BigDecimal("0.0"))==0){
				listall1.add(bc);
			}else{
				listall2.add(bc);
			}
		}
		Map<String,Object> result=null;
		if(0==type){
			 result=putMapParams("info", listall1);
		}else if(1==type){
			 result=putMapParams("info", listall2);
		}
		return result;
		
	}
	//宝宝们哪一天请假 未同意的放到上面
	public static List<BabyLeaveLogAll> paixuBabyLeaveLog(List<BabyLeaveLogAll> listall){
		for (int i = 0; i < listall.size(); i++) {   
	        int k = i;   
	        for (int j = listall.size() - 1; j >i; j--)  {   
	            if (Long.valueOf(listall.get(j).getLeaveState()) < Long.valueOf(listall.get(k).getLeaveState()))  k = j;   
	        }   
	        BabyLeaveLogAll  temp = listall.get(i);   
	        listall.set(i,listall.get(k));   
	        listall.set(k,temp);   
	    }   
		return listall;
		
	}
	
	
	
	//老师那一天请假的排序
	public static List<WorkerLeaveLogPrin> paixuWorkerLeaveLog(List<WorkerLeaveLogPrin> listall){
		for (int i = 0; i < listall.size(); i++) {   
	        int k = i;   
	        for (int j = listall.size() - 1; j >i; j--)  {   
	            if (Long.valueOf(listall.get(j).getLeaveState()) < Long.valueOf(listall.get(k).getLeaveState()))  k = j;   
	        }   
	        WorkerLeaveLogPrin  temp = listall.get(i);   
	        listall.set(i,listall.get(k));   
	        listall.set(k,temp);   
	    }   
		return listall;
		
	}
	
	public static Map<String,Object> putMapParams(Map<String,Object> map,String a,Object a1){
		map.put(a, a1);
		return map;
	}
	public static Map<String,Object> putMapParams(Map<String,Object> map,String a,Object a1,String b,Object b1){
		map.put(a, a1);
		map.put(b, b1);
		return map;
	}
	public static Map<String,Object> putMapParams(Map<String,Object> map,String a,Object a1,String b,Object b1,String c,Object c1){
		map.put(a, a1);
		map.put(b, b1);
		map.put(c, c1);
		return map;
	}
	public static Map<String,Object> putMapParams(Map<String,Object> map,String a,Object a1,String b,Object b1,String c,Object c1,String d,Object d1){
		map.put(a, a1);
		map.put(b, b1);
		map.put(c, c1);
		map.put(d, d1);
		return map;
	}
	public static Map<String,Object> putMapParams(Map<String,Object> result,String a,Object a1,String b,Object b1,String c,Object c1,String d,Object d1,String e,Object e1){
		result.put(a, a1);
		result.put(b, b1);
		result.put(c, c1);
		result.put(d, d1);
		result.put(e, e1);
		return result;
	}
	
	
	public static Map<String,Object> putMapParams(String a,Object a1){
		Map<String,Object> result=new HashMap<String,Object>();
		result.put(a, a1);
		return result;
	}
	public static Map<String,Object> putMapParams(String a,Object a1,String b,Object b1){
		Map<String,Object> result=new HashMap<String,Object>();
		result.put(a+"", a1);
		result.put(b+"", b1);
		System.err.println(result);
		return result;
	}
	public static Map<String,Object> putMapParams(String a,Object a1,String b,Object b1,String c,Object c1){
		Map<String,Object> result=new HashMap<String,Object>();
		result.put(a+"", a1);
		result.put(b+"", b1);
		result.put(c+"", c1);
		return result;
	}
	public static Map<String,Object> putMapParams(String a,Object a1,String b,Object b1,String c,Object c1,String d,Object d1){
		Map<String,Object> result=new HashMap<String,Object>();
		result.put(a+"", a1);
		result.put(b+"", b1);
		result.put(c+"", c1);
		result.put(d+"", d1);
		return result;
	}
	public static Map<String,Object> putMapParams(String a,Object a1,String b,Object b1,String c,Object c1,String d,Object d1,String e,Object e1){
		Map<String,Object> result=new HashMap<String,Object>();
		result.put(a+"", a1);
		result.put(b+"", b1);
		result.put(c+"", c1);
		result.put(d+"", d1);
		result.put(e+"", e1);
		return result;
	}
	public static Map<String,Object> putMapParams(String a,Object a1,String b,Object b1,String c,Object c1,String d,Object d1,String e,Object e1,String f,Object f1){
		Map<String,Object> result=new HashMap<String,Object>();
		result.put(a+"", a1);
		result.put(b+"", b1);
		result.put(c+"", c1);
		result.put(d+"", d1);
		result.put(e+"", e1);
		result.put(f+"", f1);
		return result;
	}
	public static Map<String,Object> putMapParams(String a,Object a1,String b,Object b1,String c,Object c1,String d,Object d1,String e,Object e1,String f,Object f1,String g,Object g1){
		Map<String,Object> result=new HashMap<String,Object>();
		result.put(a+"", a1);
		result.put(b+"", b1);
		result.put(c+"", c1);
		result.put(d+"", d1);
		result.put(e+"", e1);
		result.put(f+"", f1);
		result.put(g+"", g1);
		return result;
	}
	//老师签到2017年6月转时间戳
	/*public static Map<String, Object> teacherFormatSign(String token,String time) throws ParseException{
		Map<String,Object> result=getMonthLong(time);
		 result.put("token", token);
		return result;
	}*/
	//根据月份获取 本月和下月的初始时间戳 2017年6月
	/*public static Map<String, Object> getMonthLong(String time) throws ParseException{
		Map<String,Object> result=new HashMap<String,Object>();
		SimpleDateFormat sp=new SimpleDateFormat("yyyy年MM月");
		Calendar calendar = Calendar.getInstance();// 获取当前日期  
		calendar.setTime(sp.parse(time));
	    calendar.add(Calendar.MONTH, 0);  
	    calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天  
	    calendar.set(Calendar.HOUR_OF_DAY, 0);  
	    calendar.set(Calendar.MINUTE, 0);  
	    calendar.set(Calendar.SECOND, 0);  
	    result.put("lonStart", calendar.getTimeInMillis()/1000);
	    calendar.add(Calendar.MONTH, 1);  
	    result.put("lonEnd", calendar.getTimeInMillis()/1000);
	    return result;
	}*/
	//字符串转时间戳 2017年6月1日
	public static long stringLon(String time) throws ParseException{
		SimpleDateFormat sp=new SimpleDateFormat("yyyy年MM月dd日");
		return sp.parse(time).getTime()/1000;

	}
	//字符串转时间戳 2017年6月
		public static long stringLonYM(String time) throws ParseException{
			SimpleDateFormat sp=new SimpleDateFormat("yyyy年MM月");
			return sp.parse(time).getTime()/1000;

		}
	//获取月份的天数
	 public static int getDaysOfMonth(Long date) throws ParseException { 
		 SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date dat=sp.parse(sp.format(date));
	     Calendar calendar = Calendar.getInstance();  
	     calendar.setTime(dat);  
	     return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
	 }  
	//获取月份的天数
		 public static int getDaysOfMonth(String date) throws ParseException { 
			 SimpleDateFormat sp=new SimpleDateFormat("yyyy年MM月");
			 Date dat=sp.parse(date);
		     Calendar calendar = Calendar.getInstance();  
		     calendar.setTime(dat);  
		     return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
		 }  
		 
		//课程早上下午分类
		 public static Map<String,Object> lessonClassify(List<GartenLesson> list)  {
			List<GartenLesson> listAm=new ArrayList<GartenLesson>();
			List<GartenLesson> listPm=new ArrayList<GartenLesson>();
			for(GartenLesson g:list){
				if(g.getAmpm()==0){
					listAm.add(g);
				}else{
					listPm.add(g);
				}
			}
			Map<String,Object> result=putMapParams("am", listAm, "pm", listPm);
			 return result;
		 }  
	
		//获取时间 8:30的秒数
		 public static long getSecond(String time)  {
			 System.err.println(time);
				Long a=Long.valueOf(time.substring(0,time.indexOf(":")))*3600;
				Long b=Long.valueOf(time.substring(time.indexOf(":")+1,time.length()))*60;
			 return a+b;
		 } 
		 
		 
		//根据年月日时分秒的时间戳 获取年月日的时间戳
		 public static long getYMDLong(Long time) throws ParseException  {
			 SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
			 //format是保证Long为毫秒
			 Date dat=sp.parse(sp.format(time*1000));
			 return dat.getTime()/1000;
		 }
		 public static Set<Long> getYMDLongs(List<Long> times) throws ParseException  {
			 Set<Long> timeAll=new HashSet<Long>();
			 for(Long time :times){
				 SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
				 //format是保证Long为毫秒
				 Date dat=sp.parse(sp.format(time*1000));
				 timeAll.add(dat.getTime()/1000);
			 }
			 return timeAll;
		 }
		 public static Set<Long> getYMDLongs(Set<Long> times) throws ParseException  {
			 Set<Long> timeAll=new HashSet<Long>();
			 for(Long time :times){
				 SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
				 //format是保证Long为毫秒
				 Date dat=sp.parse(sp.format(time*1000));
				 timeAll.add(dat.getTime()/1000);
			 }
			 return timeAll;
		 }
		
		
		 
		 //获取老师的异常
		 public static Set<Long> getYichangLongsWorker(List<WorkerCheckLogAll> listall,GartenStartEnd gartenStartEnd) throws ParseException{
			 Set<Long> lons=new HashSet<Long>();
				for(WorkerCheckLogAll bc:listall){
						//有异常的Long加入List
					if(bc.getArriveTime()-getYMDLong(bc.getArriveTime())-getSecond(gartenStartEnd.getArriveEndTime())>0||bc.getLeaveTime()-getYMDLong(bc.getArriveTime())-getSecond(gartenStartEnd.getLeaveStartTime())<0){
						lons.add(getYMDLong(bc.getArriveTime()));
					}
				}
				return lons;
			}
		 
		 
	 public static List<PhotoAll> getPhotoFinal(List<GartenPhotos>  list,String token,Integer type){
		 List<PhotoAll> photoAll=new ArrayList<PhotoAll>();
		WorkerService workerService = (WorkerService) MyUtil.getBean("workerService");
		ParentService parentService = (ParentService) MyUtil.getBean("parentService");
		 //遍历GartenPhotos对象 并查找 List<comment> -->构建PhotoAll对象
		 for(GartenPhotos p:list){//随时查到这个发表人的头像 老师:老师的头像,家长:宝宝的头像
			 System.err.println("测试"+p.getJob());
			 if(p.getJob().equals("家长")){
				 ClassManage classManage= parentService.findBaby(p.getBabyId());
				 p.setPhotoHeadImg(classManage.getBabyHead());
			 }else {//老师,园长
				 WorkerInfo workerInfo= workerService.findWorker(p.getId());
				 System.err.println("测试"+workerInfo);
				 p.setPhotoHeadImg(workerInfo.getHeadImg());
			 }
			 List<Comment> pComment=workerService.findEvaluationByInfoId(p.getInfoId());//所有的评论信息
			 List<PhotoDianZan> count=workerService.findDianZanByInfoId(p.getInfoId());//所有的点赞信息
			 Integer isDianzan=0;
			 if(0==type){
				 isDianzan=workerService.findIsDianZanByInfoId(token,p.getInfoId());
			 }else if(1==type){
				 isDianzan=parentService.findIsDianZanByInfoId(token,p.getInfoId());
			 }
			
			 photoAll.add(new PhotoAll(p,pComment,count,isDianzan));
		 }
		 for (int i = 0; i < photoAll.size(); i++) {   
		        int k = i;   
		        for (int j = photoAll.size() - 1; j >i; j--)  {   
		            if (Long.valueOf(photoAll.get(j).getPhoto().getUploadTime()) > Long.valueOf(photoAll.get(k).getPhoto().getUploadTime()))  k = j;   
		        }   
		        PhotoAll  temp = photoAll.get(i);   
		        photoAll.set(i,photoAll.get(k));   
		        photoAll.set(k,temp);   
		    }
		 //过滤私有相册
		/* List<PhotoAll> photoAll2=getPersonalPhotoFinal(photoAll,token);*/
		 return photoAll;
		 
	}
		 
	 public static List<PhotoAll> getPersonalPhotoFinal(List<PhotoAll>  list,String token){
			WorkerService ckps = (WorkerService) MyUtil.getBean("workerService");
		 List<PhotoAll> photoAllFinal=new ArrayList<PhotoAll>();//过滤私有相册
		 for(PhotoAll p:list){
			 if(1==p.getPhoto().getState()){//有过这条相册私有则验证 WorkerInfo teacher= ckps.getTeacher(token);
				 WorkerInfo teacher= ckps.getTeacher(token);
				 if(teacher.getWorkerId()==p.getPhoto().getId()&&teacher.getJob().equals( p.getPhoto().getJob())){
					 photoAllFinal.add(p);
				 }
			 }else{//state=0 公有直接加
				 photoAllFinal.add(p);
			 }
		 }
		 
		 return photoAllFinal;
	 }
	 //根据请假时间段 获取请假红圈   老师
	public static Set<Long> findWTimeLongByStartEnd(List<WorkerLeaveLog> workers) throws ParseException {
		Set<Long> set=new HashSet<Long>();
		Long start=0l;
		Long end=0l;
		for(WorkerLeaveLog w:workers){
			 start=getYMDLong(w.getLeaveTime());
			 end=getYMDLong(w.getEndTime());
			 System.err.println(start);
			 System.err.println(end);
			 for(int i=0;i<100;i++){
				 set.add(start);
				 if(end-start==0){
					 System.err.println("成功");
					 break;
				 }else{
					 start=start+86400l;
				 }
			 }
		}
		return set;
		
	}
	
	 //根据请假时间段 获取请假红圈   宝宝
		public static Set<Long> findBTimeLongByStartEnd(List<BabyLeaveLog> workers) throws ParseException {
			Set<Long> set=new HashSet<Long>();
			Long start=0l;
			Long end=0l;
			for(BabyLeaveLog b:workers){
				 start=getYMDLong(b.getLeaveTime());
				 end=getYMDLong(b.getEndTime());
				 end= end-b.getEndTime()==0?end-86400:end;
				 for(int i=0;i<100;i++){//开始时间的那一天加入红圈,如果结束时间是第二天的0点就不加入[起码1点起才算那天有请假]
					 set.add(start);
					 if(end-start==0){
						 break;
					 }else{
						 start=start+86400l;
					 }
				 }
			}
			return set;
			
		}
		
		
		//根据请假时间段 获取请假红圈   职工
				public static Set<Long> findBTimeLongByStartEndWorker(List<WorkerLeaveLog> workers) throws ParseException {
					Set<Long> set=new HashSet<Long>();
					Long start=0l;
					Long end=0l;
					for(WorkerLeaveLog b:workers){
						 start=getYMDLong(b.getLeaveTime());
						 end=getYMDLong(b.getEndTime());
						 end= end-b.getEndTime()==0?end-86400:end;
						 for(int i=0;i<100;i++){//开始时间的那一天加入红圈,如果结束时间是第二天的0点就不加入[起码1点起才算那天有请假]
							 set.add(start);
							 if(end-start==0){
								 break;
							 }else{
								 start=start+86400l;
							 }
						 }
					}
					return set;
					
				}
		//宝宝某一天的请假
		public static BabyLeaveLog findBTimeLongByStartEndOne(List<BabyLeaveLog> workers,Long time) throws ParseException {
			Set<Long> set=new HashSet<Long>();
			Long start=0l;
			Long end=0l;
			for(BabyLeaveLog b:workers){
				 start=getYMDLong(b.getLeaveTime());
				 end=getYMDLong(b.getEndTime());
				if(start<=time&&time<end+86400){
					return b;
				}
			}
			return null;
			
		}
		
		public static List<ActivityDetailAll> getActivityList(List<ActivityDetailAll> activityDetailAll, Integer babyId) {
			WorkerService ckps = (WorkerService) MyUtil.getBean("workerService");
			for(ActivityDetailAll a:activityDetailAll){//0能报名 1已报名   2报名结束
				if(a.getJoinTime()-new Date().getTime()/1000>=0){
					Integer boo=ckps.isApplyActivity(babyId, a.getActivityId());
					System.err.println(boo+"BOO");
					a.setApplyState(boo==0?0:1);
				}else {
					a.setApplyState(2);
				}
			}
			return activityDetailAll;
		}
		/**
		 * 判断这次的请假条时间是否冲突
		 * @param leaves  这个宝宝所有的请假条
		 * @param start   这次请假的开始时间
		 * @param end   	这次请假的结束时间
		 * @return
		 * @throws ParseException
		 */
		public static Integer ableToLeave(List<BabyLeaveLog> leaves, Long start, Long end) throws ParseException {
			for(BabyLeaveLog l:leaves){
				if(start<l.getLeaveTime()&&l.getLeaveTime()<end){//某条请假条的开始时间在申请的时间段内,则时间冲突
					return 2;//开始时间冲突   [结束时间需往前提]
				}
				if(start<l.getEndTime()&&l.getEndTime()<end){//某条请假条的结束时间在申请的时间段内,则时间冲突
					return 1;//结束时间冲突 [开始时间需往前提]
				}
				if( l.getLeaveTime()<=start&&end<=l.getEndTime() ){//某条请假条的结束时间在申请的时间段内,则时间冲突
					return 3;//内嵌在其他请假条了
				}
			}
			return 0;
		}
		public static Integer ableToLeaveWorker(List<WorkerLeaveLog> leaves, Long start, Long end) throws ParseException {
			for(WorkerLeaveLog l:leaves){
				if(start<l.getLeaveTime()&&l.getLeaveTime()<end){//某条请假条的开始时间在申请的时间段内,则时间冲突
					
					return 2;//结束时间冲突
				}
				if(start<l.getEndTime()&&l.getEndTime()<end){//某条请假条的结束时间在申请的时间段内,则时间冲突
					return 1;//开始时间冲突
				}
				if( l.getLeaveTime()<=start&&end<=l.getEndTime() ){//某条请假条的结束时间在申请的时间段内,则时间冲突
					return 3;//内嵌在其他请假条了
				}
			}
			return 0;
		}
		public static Object paixuReplyPerson(Set<String> name, String parentName) {
			for(String na:name){
				System.err.println(na+"==="+parentName);
				if(na.equals(parentName)){
					name.remove(na);
				}
			}
			return name;
		}
		
		
		
		
		//POST访问网络资源
		@SuppressWarnings("deprecation")
		public static Map<String,Object> HttpRequest(String url,JSONObject jsonObj,String Authorization){
		    boolean isSuccess = true;
		    HttpPost post = null;
		    Map<String,Object> succesResponse=null;
		    try {
		        HttpClient httpClient = new DefaultHttpClient();
		        // 设置超时时间
		        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
		        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
		        httpClient.getParams().setParameter("http.socket.timeout", new Integer(30000)); 
		        post = new HttpPost(url);
		        // 构造消息头
		        post.setHeader("Content-type", "application/json; charset=utf-8");
		        post.setHeader("Connection", "Close");
		        String sessionId = getSessionId();
		        post.setHeader("SessionId", sessionId);
		        post.setHeader("Authorization",Authorization);
		        // 构建消息实体
		        StringEntity entity = new StringEntity(jsonObj.toString(), Charset.forName("UTF-8"));
		        entity.setContentEncoding("UTF-8");
		        // 发送Json格式的数据请求
		        entity.setContentType("application/json");
		        post.setEntity(entity);
		        HttpResponse  response = httpClient.execute(post);
		        String  strResult = EntityUtils.toString(response.getEntity()); 
		         succesResponse = (Map<String, Object>) JSON.parse(strResult);    //先转换成Object

		          
	            System.err.println("请求成功: "+  jsonObj.toString());

		        // 检验返回码
		        int statusCode = response.getStatusLine().getStatusCode();
		        if(statusCode != HttpStatus.SC_OK){
		            System.err.println("请求出错: "+statusCode);
		            isSuccess = false;
		        }else{
		            int retCode = 0;
		            String sessendId = "";
		            // 返回码中包含retCode及会话Id
		            for(Header header : response.getAllHeaders()){
		                if(header.getName().equals("retcode")){
		                    retCode = Integer.parseInt(header.getValue());
		                }
		                if(header.getName().equals("SessionId")){
		                    sessendId = header.getValue();
		                }
		            }
		            
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        isSuccess = false;
		    }finally{
		        if(post != null){
		            try {
		                post.releaseConnection();
		                Thread.sleep(500);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		    return succesResponse;
		}
		
		
		    public static String getSessionId(){
		        UUID uuid = UUID.randomUUID();
		        String str = uuid.toString();
		        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
		    }
	   
		    
		    
    public static Map<String, Object> getWeChatUserInfo(String url,String authorization) throws ClientProtocolException, IOException{
        Map<String, Object>  succesResponse=new HashMap<String,Object>();
        // 根据地址获取请求
        HttpGet request = new HttpGet(url);//这里发送get请求
        request.setHeader("Authorization",authorization );
        // 获取当前客户端对象
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter("http.socket.timeout", new Integer(30000)); 
        // 通过请求对象获取响应对象
        HttpResponse response = httpClient.execute(request);
        System.err.println(response.getStatusLine().getStatusCode());
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            succesResponse = (Map<String, Object>) JSON.parse(EntityUtils.toString(response.getEntity(),"utf-8"));
        } 
		return succesResponse;
    }
	public static Map<String, Object> putMapParams(String string, String token, String string2, String babyName,
			String string3, String allergy, String string4, Long birthday, String string5, BigDecimal height,
			String string6, String hobby, String string7, String specialty, String string8, String leadClass,
			String string9, String leadGrade, String string10, String health, String string11,Integer babyId,String string12, Float weight,String string13,Integer sex) {
		Map<String,Object> result=new HashMap<String,Object>();
		result.put(string+"", token);
		result.put(string2+"", babyName);
		result.put(string3+"", allergy);
		result.put(string4+"", birthday);
		result.put(string5+"", height);
		result.put(string6+"", hobby);
		result.put(string7+"", specialty);
		result.put(string8+"", leadClass);
		result.put(string9+"", leadGrade);
		result.put(string10+"", health);
		result.put(string11+"", babyId);
		result.put(string12+"", weight);
		result.put(string13+"", sex);
		return result;
	}
	public static String getBabyRelation(Integer babyId, ParentInfo parentInfo) {
		ParentService parentService = (ParentService) MyUtil.getBean("parentService");
		ClassManage classmanege=parentService.findBaby(babyId);
		String name="";
		String[] babyIds=parentInfo.getBabyId();
		String[] relations=parentInfo.getIdentity();
		for(int i=0;i<babyIds.length;i++){
			System.err.println(babyIds[i]+"==="+classmanege.getBabyName());
			if(Integer.valueOf(babyIds[i])==babyId){
				name=classmanege.getBabyName()+relations[i];
			}
		}
		return name;
	}
	public static Boolean getParentAttendance(ParentInfo parentInfo, Integer babyId) throws ParseException {
		Boolean attendanceTime=false;
		String[] atten=parentInfo.getAttendanceTime();
		String[] babyIds=parentInfo.getBabyId();
		for(int i=0;i<babyIds.length;i++){
			if(babyIds[i].equals(babyId+"")){
				System.err.println(atten[i]+"测试"+i);
				attendanceTime=new SimpleDateFormat("yyyy-MM-dd").parse(atten[i]).getTime()>new Date().getTime();
			}
		}
		return attendanceTime;
	}
	//	反转 6年的晨检,考勤红圈
	public static Set<Long> reverse(Set<Long> time) throws ParseException {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Set<Long> timeReverse=new HashSet();
		Long index=getYMDLong(new Date().getTime()/1000)-100*86400;
		int a=0;//检测是否跟time里的long一样,全部一样则添加
		for(int i=0;i<=100;i++){//把前后6年的时间戳全部添加,后移除忽略的时间戳
			timeReverse.add(index);
			index+=86400;
		}
		for(Long lon:time){
			timeReverse.remove(lon);
		}
		return timeReverse;
	}
	public static void delePhotoImgs(GartenPhotos photo) {
		String[] imgs=photo.getImgs();
		System.err.println(imgs);
		if(null!=imgs&&!"".equals(imgs)){
			for(int i=0;i<imgs.length;i++){
				MyUtilAll.deleteOldOSS(imgs[i]);
			}
		}
		
	}
	//家长是否有看视频的权限
	public static Integer findMonitor(Integer babyId, ParentInfo parentInfo) throws ParseException {
		Integer monitor=0;
		String[] monitorTime=parentInfo.getMonitorTime();
		String[] babyIds=parentInfo.getBabyId();
		for(int i=0;i<babyIds.length;i++){
			if(babyIds[i].equals(babyId+"")){
				monitor=new SimpleDateFormat("yyyy-MM-dd").parse(monitorTime[i]).getTime()>new Date().getTime()?1:0;
			}
		}
		return monitor;
	}
	/**
	 * 1查询并遍历所有幼儿园
	 * 2查询每个幼儿园的忽略表(找到哪天需要晨检)
	 * 3这天需要晨检:找到这个幼儿园今天的所有宝贝的晨检记录
	 * 				体温 1:>37&!=0异常
	 * 					2:<36&!=0异常
	 * 					3:==0异常
	 * :应晨检人数,实际晨检人数,缺捡人数,过高人数,过小人数
	 * @return 
	 * @throws ParseException 
	 */
	public static Map<String, Object> getChecktongji(List<GartenInfo> gartens, Long time) throws ParseException {
		BigcontrolService bigcontrolService = (BigcontrolService) MyUtil.getBean("bigcontrolService");
		Set<Long> lons=new HashSet<Long>();
		List<BabyCheckLogAll> babyCheckLogs=new ArrayList<BabyCheckLogAll>();
		Integer flag=0;//今天要不要晨检 0要晨检
		Integer shouldCount=0;
		Integer realCount=0;
		Integer lackCount=0;
		Integer heightCount=0;
		Integer shortCount=0;
		for(GartenInfo g :gartens){
			lons=bigcontrolService.findCheckLong(g.getGartenId());
			for(Long l:lons){
				flag=time==l?1:0;
			}
			if(0==flag){//这天需要晨检
				babyCheckLogs= bigcontrolService.findBabyCheckByToken(putMapParams("gartenId", g.getGartenId(),"time",time));//获取所有宝宝的晨检 考勤信息
				for(BabyCheckLogAll b:babyCheckLogs){
					shouldCount++;
					if(0==b.getTemperature().compareTo(new BigDecimal(0))){
						lackCount++;
					}else if(1==b.getTemperature().compareTo(new BigDecimal(37))){//左边比右边大返回1
						heightCount++;
					}else if(-1==b.getTemperature().compareTo(new BigDecimal(36))){//左边比右边小返回-1
						shortCount++;
					}
				}
				flag=1;
			}
		}
		realCount=shouldCount-lackCount;
		Map<String,Object> result=putMapParams("shouldCount", shouldCount, "realCount", realCount, "lackCount", lackCount, "heightCount", heightCount, "shortCount", shortCount);
		return result;
	}
	public static Map<String, Object> getDakatongji(List<GartenInfo> gartens, Long time) {
		BigcontrolService bigcontrolService = (BigcontrolService) MyUtil.getBean("bigcontrolService");
		Set<Long> lons=new HashSet<Long>();
		List<BabyCheckLogAll> babyCheckLogs=new ArrayList<BabyCheckLogAll>();
		Integer flag=0;//今天要不要晨检 0要晨检
		Integer shouldCountAm=0;
		Integer realCountAm=0;
		Integer shouldCountPm=0;
		Integer realCountPm=0;
		for(GartenInfo g :gartens){
			lons=bigcontrolService.findCheckLong(g.getGartenId());
			for(Long l:lons){
				flag=time==l?1:0;
			}
			if(0==flag){//这天需要晨检
				babyCheckLogs= bigcontrolService.findBabyCheckByToken(putMapParams("gartenId", g.getGartenId(),"time",time));//获取所有宝宝的晨检 考勤信息
				for(BabyCheckLogAll b:babyCheckLogs){
					shouldCountAm++;
					shouldCountPm++;
					if(null!=b.getArriveTime()&&!"".equals(b.getArriveTime())){
						realCountAm++;
					}
					if(null!=b.getLeaveTime()&&!"".equals(b.getLeaveTime())){
						realCountPm++;
					}
				}
				flag=1;
			}
		}
		Map<String,Object> result=putMapParams("shouldCountAm", shouldCountAm, "realCountAm", realCountAm, "shouldCountPm", shouldCountPm, "realCountPm", realCountPm);
		return result;
	}
	public static BigDecimal getRealPrice(GartenCharge gartenCharge, Integer month) {
		BigDecimal big=null;
		if(month>=12){//大于12个月份的价格
			 big=gartenCharge.getMonth12().add(new BigDecimal((month-12)).multiply(gartenCharge.getMonth1()));
		}else if(month>=6){//大于6个月份的价格
			 big=gartenCharge.getMonth6().add(new BigDecimal((month-6)).multiply(gartenCharge.getMonth1()));
		}else if(month>=3){//大于3个月份的价格
			 big=gartenCharge.getMonth3().add(new BigDecimal((month-3)).multiply(gartenCharge.getMonth1()));
		}else {//小于3个月份的价格
			 big=new BigDecimal((month)).multiply(gartenCharge.getMonth1());
		}
		return big;
	}
	public static String  changeArrayToString(String[] attendanceTime) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < attendanceTime.length; i++){
		 sb. append(","+attendanceTime[i]);
		}
		String result=sb.toString().substring(1,sb.toString().length());
		return result;
	}
	//中文[一]转[1]
	public static int changeNumber(String chineseNumber){
        int result = 0;
        int temp = 1;//存放一个单位的数字如：十万
        int count = 0;//判断是否有chArr
        char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
        char[] chArr = new char[]{'十','百','千','万','亿'};
        for (int i = 0; i < chineseNumber.length(); i++) {
            boolean b = true;//判断是否是chArr
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
                if (c == cnArr[j]) {
                    if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
                        result += temp;
                        temp = 1;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            if(b){//单位{'十','百','千','万','亿'}
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        switch (j) {
                        case 0:
                            temp *= 10;
                            break;
                        case 1:
                            temp *= 100;
                            break;
                        case 2:
                            temp *= 1000;
                            break;
                        case 3:
                            temp *= 10000;
                            break;
                        case 4:
                            temp *= 100000000;
                            break;
                        default:
                            break;
                        }
                        count++;
                    }
                }
            }
            if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
                result += temp;
            }
        }
        return result;
    }

	//中文[1]转[一]
	public static String  changeNumber2(int d) {
		String[] str = { " ", "一", "二", "三", "四", "五", "六", "七", "八", "九" };  
		String ss[] = new String[] { "", "十", "百", "千", "万", "十", "百", "千", "亿" }; 
		String s = String.valueOf(d);  
		StringBuffer sb = new StringBuffer();    
		for (int i = 0; i < s.length(); i++) {       
			String index = String.valueOf(s.charAt(i));  
			sb = sb.append(str[Integer.parseInt(index)]);  
			}       
		String sss = String.valueOf(sb); 
		int i = 0;     
		for (int j = sss.length(); j > 0; j--) { 
			sb = sb.insert(j, ss[i++]); 
			}     
		return sb.toString();
		}
	//添加今天的忽略周末 参数"2017-1-1"
	public static void addIgnoreYear(Integer gartenId) throws ParseException {
		BigcontrolService big = (BigcontrolService) MyUtil.getBean("bigcontrolService");
			  DateFormat df = new SimpleDateFormat("yyyy-M-d");
			  DateFormat year = new SimpleDateFormat("yyyy");
			  String a=year.format(new Date())+"-1-";
		        Date date = df.parse(year.format(new Date())+"-1-1");//获取当前时间转为2017年1月1日的date
		        int day = date.getDay(); 
		        int startSatOffset = 6-day;
		      //  Set<Date> lon=new HashSet<Date>();
		        if(day==0){
		            System.out.println("此年的第一天是星期天");
		        }
		        for(int i=0;i<365/7;i++){
		            Date satday = df.parse(a+(1+startSatOffset+i*7));
		            Date sunday = df.parse(a+(1+startSatOffset+(i*7+1)));
		            big.addIgnoreYear(gartenId, satday);
		            big.addIgnoreYear(gartenId, sunday);
		            System.out.println("第"+(i+1)+"个星期末是:"+df.format(satday)+"和"+df.format(sunday));
		        }	         
		
	}

	
	//每年一月一号为所有幼儿安添加忽略时间
	public static void addIgnoreEveryYear(Integer gartenId) throws ParseException {
		BigcontrolService big = (BigcontrolService) MyUtil.getBean("bigcontrolService");
		List<GartenInfo> list = big.getAllGarten();
		for(GartenInfo g : list){
			addIgnoreYear(g.getGartenId());
		}
	}
	public static Map<String,Object> finalSmallYichang(List<UnusualAll> workerYichang, List<UnusualAll> babyYichang, Integer type,
			Integer state, Integer pageNo) {
		//过滤符合是否处理状态的
		if(null!=state){
			if(0==state||1==state){
				  Iterator<UnusualAll> iterator = workerYichang.iterator();
			        while(iterator.hasNext()){
			        	UnusualAll u = iterator.next();
			        	if(state!=u.getState()){
			        		iterator.remove();
						}
			        }
			         iterator = babyYichang.iterator();
			        while(iterator.hasNext()){
			        	UnusualAll u = iterator.next();
			        	if(state!=u.getState()){
			        		iterator.remove();
						}
			        }
				
			}
		}
		//0宝宝异常1老师异常 else全部异常
		if(null!=type){
			if(0==type){
				return MyPage.listPage16(babyYichang, pageNo);
			}else if(1==type){
				return MyPage.listPage16(workerYichang, pageNo);
			}
		}
		babyYichang.addAll(workerYichang);
		Collections.sort(babyYichang,new Comparator<Object>() {

			@Override
			public int compare(Object o1, Object o2) {
				UnusualAll u1 = (UnusualAll)o1;
				UnusualAll u2 = (UnusualAll)o2;
				return u1.getUnusualTime().compareTo(u2.getUnusualTime());
			}
		});
		return MyPage.listPage16(babyYichang, pageNo);
		
		
	}
	public static Object finalSmallLeave(List<WorkerLeaveLogPrin> workerLeave, List<BabyLeaveLogAll> babyLeave,
			Integer type, Integer state, Integer pageNo) {
		//过滤符合是否处理状态的
				if(null!=state){
					if(0==state||1==state){
						Iterator<WorkerLeaveLogPrin> iterator = workerLeave.iterator();
				        while(iterator.hasNext()){
				        	WorkerLeaveLogPrin u = iterator.next();
				        	if(state!=u.getLeaveState()){
				        		iterator.remove();
							}
				        }
				        Iterator<BabyLeaveLogAll>   iterator2 = babyLeave.iterator();
				        while(iterator2.hasNext()){
				        	BabyLeaveLogAll u = iterator2.next();
				        	if(state!=u.getLeaveState()){
				        		iterator2.remove();
							}
				        }
					}
				}
				//0宝宝异常1老师异常 else全部异常
				if(null!=type){
					if(0==type){
						return MyPage.listPage16(babyLeave, pageNo);
					}else if(1==type){
						return MyPage.listPage16(workerLeave, pageNo);
					}
				}
//				babyLeave.addAll(workerLeave);  bean不一样
				return MyPage.listPage16(babyLeave, pageNo);
				
	}
	public static List<OrderAll> appendOrderName(List<OrderAll> order,String name,String phoneNumber) {
		PrincipalService principal = (PrincipalService) MyUtil.getBean("principalService");
		ParentService parent = (ParentService) MyUtil.getBean("parentService");

		List<OrderAll> result=new ArrayList<OrderAll>();
		for(OrderAll o:order){
			if("园长".equals(o.getJob())){
				System.err.println("测试"+o.getGartenId());
				WorkerInfo workerInfo= principal.findPrincipalInfoById( o.getId());
				System.err.println("测试"+workerInfo);
				if(null!=workerInfo){
					o.setName(workerInfo.getWorkerName());
					o.setPhoneNumber(workerInfo.getPhoneNumber());
					o.setHead(workerInfo.getHeadImg());
					//如果有name和phoneNumber传过来  不相同就continue
					
					if(null!=name&&!"".equals(name)){
						if(!o.getName().equals(name)){
							continue;
						}
					}
					if(null!=phoneNumber&&!"".equals(phoneNumber)){
						if(!o.getPhoneNumber().equals(phoneNumber)){
							continue;
						}
					}
					//有选择name和phoneNumber过滤后就添加
						result.add(o);
				}
			}else if("家长".equals(o.getJob())){
				ParentInfo parentInfo= parent.findParentById( o.getId());
				if(null!=parentInfo){
					o.setName(parentInfo.getParentName());
					o.setPhoneNumber(parentInfo.getPhoneNumber());
					o.setHead(parentInfo.getParentHead());
					if(null!=name&&!"".equals(name)){
						if(!o.getName().equals(name)){
							continue;
						}
					}
					if(null!=phoneNumber&&!"".equals(phoneNumber)){
						if(!o.getPhoneNumber().equals(phoneNumber)){
							continue;
						}
					}
					//有选择name和phoneNumber过滤后就添加
						result.add(o);
				}
			}
		}
		return result;
	}   
	
	//云家洁86583用户注册成功
	 public static  void register(String phoneNumber,String modelId,String[] datas){
		 HashMap<String, Object> result = null; 
		 CCPRestSDK restAPI = new CCPRestSDK();
		 restAPI.init("app.cloopen.com", "8883");
		 // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883. 
		 restAPI.setAccount(MyParamAll.YTX_ACCOUNT_SID, MyParamAll.YTX_AUTH_TOKEN);
		 // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
		 restAPI.setAppId(MyParamAll.YTX_APPID);
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
	 
	 public static void pushOne(String appKey,String materSecret,String message,String phoneNumber) throws APIConnectionException, APIRequestException{
			System.err.println(phoneNumber+"JG准备发送");
			System.err.println(materSecret);
			System.err.println(appKey);
			try {
				JPushClient jPushCilent=new JPushClient(materSecret,appKey);
				
				PushPayload payload=PushPayload.newBuilder()
						 .setPlatform(Platform.all())
				            .setAudience(Audience.alias(phoneNumber))/*15356506227,13843838438*/
				            .setNotification(Notification.alert(message))
				            .build();
				PushResult result=jPushCilent.sendPush(payload);
				System.err.println(phoneNumber+"JG发送完成");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 public static List<SaleServiceAll> setSaleServiceAll(List<SaleServiceAll> ss) {
			AgentService agentService = (AgentService) MyUtil.getBean("agentService");
			WorkerService workerService = (WorkerService) MyUtil.getBean("workerService");
			if(null!=ss){
				for(SaleServiceAll s:ss){
					s.getAgentId();
					AgentInfo agent=agentService.findAgentByAgentId(s.getAgentId());
					GartenInfo garten=workerService.findGartenInfoById(s.getGartenId());
					s.setAgent(agent);
					s.setGarten(garten);
				}
			}
			
			return ss;
		}
		
		public static SaleServiceAll setSaleServiceAll(SaleServiceAll s) {
			AgentService agentService = (AgentService) MyUtil.getBean("agentService");
			WorkerService workerService = (WorkerService) MyUtil.getBean("workerService");
			if(null!=s){
					s.getAgentId();
					AgentInfo agent=agentService.findAgentByAgentId(s.getAgentId());
					GartenInfo garten=workerService.findGartenInfoById(s.getGartenId());
					s.setAgent(agent);
					s.setGarten(garten);
				
			}
			return s;
		}
		
		public static String  changeArrayToString(Integer[] attendanceTime) {
			String result=null;
			if(null!=attendanceTime){
				StringBuffer sb = new StringBuffer();
				for(int i = 0; i < attendanceTime.length; i++){
				 sb. append(","+attendanceTime[i]);
				}
				 result=sb.toString().substring(1,sb.toString().length());
			}
			
			return result;
		}

		public static List<WithdrawAll> setWithdrawAll(List<WithdrawAll> withdraw) {
			AgentService agentService = (AgentService) MyUtil.getBean("agentService");
			for(WithdrawAll w:withdraw){
				AgentInfo a=agentService.findAgentByAgentId(w.getAgentId());
				w.setAgentInfo(a);
			}
			return withdraw;
		}
		public static List<AgentAuditMessage> auditChange2Emloyee(List<AgentAuditMessage> agentAudit) {
			BigcontrolService big = (BigcontrolService) MyUtil.getBean("bigcontrolService");
			for(AgentAuditMessage a :agentAudit){
				Employee employee = big.findEmployeeById(a.getResourceId());
				a.setAgentName(employee.getName());
			}
			return agentAudit;
		}
		
		public static List<AgentOrderAll> setFindAgentOrder(List<AgentOrderAll> ao) {
			AgentService agentService = (AgentService) MyUtil.getBean("agentService");
			for(AgentOrderAll a:ao){
				AgentInfo aOne=agentService.findAgentByAgentId(a.getAgentId());
				a.setAgentInfo(aOne);
			}
			return ao;
		}

}
