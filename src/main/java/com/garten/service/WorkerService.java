package com.garten.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.garten.Thread.XxxThread;
import com.garten.controller.FreeMarkerTest;
import com.garten.dao.AttendanceDao;
import com.garten.dao.ParentDao;
import com.garten.dao.SmallcontrolDao;
import com.garten.dao.WorkerDao;
import com.garten.model.baby.BabyInfo;
import com.garten.model.baby.BabyLeaveLog;
import com.garten.model.garten.GartenInfo;
import com.garten.model.garten.GartenLesson;
import com.garten.model.garten.GartenPhotos;
import com.garten.model.garten.GartenRecipe;
import com.garten.model.garten.PhotoDianZan;
import com.garten.model.garten.Video;
import com.garten.model.other.CheckNumber;
import com.garten.model.other.Comment;
import com.garten.model.other.GartenMsg;
import com.garten.model.other.HXLog;
import com.garten.model.other.InfoLog;
import com.garten.model.other.Unusual;
import com.garten.model.other.Version;
import com.garten.model.other.VisitCount;
import com.garten.model.parent.DaijieInfo;
import com.garten.model.parent.ParentInfo;
import com.garten.model.worker.WorkerCheckLog;
import com.garten.model.worker.WorkerInfo;
import com.garten.model.worker.WorkerLeaveLog;
import com.garten.util.duanxin.YunTongXun;
import com.garten.util.html.CreateHtml;
import com.garten.util.lxcutil.MyParamAll;
import com.garten.util.lxcutil.MyUtilAll;
import com.garten.util.md5.CryptographyUtil;
import com.garten.util.myutil.MyUtil;
import com.garten.util.page.MyPage;
import com.garten.vo.baby.UnusualAll;
import com.garten.vo.parent.ParentInfoShort;
import com.garten.vo.smallcontrol.BabyCheckSimple;
import com.garten.vo.teacher.ActivityDetailAll;
import com.garten.vo.teacher.BabyCheckLogAll;
import com.garten.vo.teacher.BabyPerformanceLogAll;
import com.garten.vo.teacher.ClassManage;
import com.garten.vo.teacher.Daijie;
import com.garten.vo.teacher.FlowerAll;
import com.garten.vo.teacher.Shouye;
import com.garten.vo.teacher.WorkerInfoShort;
import com.garten.vo.teacher.WorkerLeaveLogPrin;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
@Service
public class WorkerService{

	@Autowired
	private WorkerDao workerDao;
	@Autowired
	private SmallcontrolDao smallcontrolDao;
	@Autowired
	private AttendanceDao attendanceDao;
	@Autowired
	private ParentDao parentDao;
	@Autowired
	private BigcontrolService bigcontrolService;
	public Map<String, Object> login(String phoneNumber, String pwd) {
		Map<String,Object> param=MyUtil.putMapParams("phoneNumber", phoneNumber,"pwd",CryptographyUtil.md5(pwd, "lxc"));
		WorkerInfo worker=workerDao.findWorkerByPwd(param);
		String uuid="error";
		Map<String,Object> result=MyUtil.putMapParams("state", 0, "info", uuid);
		//如果worker为空则返回error
		//如果worker不为空则返回uuid,并修改token为uuid
		if(null!=worker){
			uuid=UUID.randomUUID().toString();
			param.put("token", uuid);
			workerDao.updateToken(param);
			MyUtil.putMapParams(result,"state", 1, "info", uuid);
		}
		return result;
	}
	
	/**
	 * 大改
	 */
	public Map<String, Object> shouye(String token) {
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken(token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//判断这个用户是否存在
			Shouye shouye=workerDao.findWorkerByToken(token);//根据用户token到职工表里查找老师
			if(null!=shouye){//有用户则找到通知数并添加进model里返回对象
				shouye.setCount(workerDao.findInfoCount(token));//设置通知数
				WorkerCheckLog workerCheckLog=workerDao.findWorkerCheckLogOne(token);//查找并设置今天是否已签到
				shouye.setSign(null==workerCheckLog||null==workerCheckLog.getAmArriveTime()?0:1);//为null或签到时间没有都是没有签到(基本不为空,基本记录事先已经建好)
				MyUtil.setShouyeClass(shouye);
			}
			MyUtil.putMapParams(result,"state", 1,"info",shouye,"worker",workerInfo);//改变返回的数据
		}
		return result;
	}
	
	
	public Map<String, Object> getHuanxin(String token) {
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken(token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//判断这个用户是否存在
			Map<String,Object> info=MyUtil.putMapParams("worker","worker"+workerInfo.getWorkerId(), "pwd", "1");
			MyUtil.putMapParams(result,"state", 1,"info",info);
		}
		return result;
	}
	
	public WorkerInfo findWorker(Integer workerId) {
		WorkerInfo workerInfo= workerDao.findWorkerInfoById(workerId);
		return workerInfo;
	}


	public Map<String, Object> notifiedCenter(String token,String type,Integer pageNo) {
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken(token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo);
		if(null!=workerInfo){
			List<InfoLog> notifieds= workerDao.findNotifiedByToken(MyUtil.putMapParams("token", token, "type", type));//有用户则找到所有通知并添加进model里返回对象
			Integer importantCount=workerDao.findImportantCountByToken(token);// 找到该用户的重要通知未读数
			MyUtil.putMapParams(result, "state", 1, "info",MyPage.listPage(notifieds, pageNo),"importantCount",importantCount); 
		}
		return result;
	}
	

	public Map<String, Object> readNotified(String infoId) {
		workerDao.readNotified(infoId);//读取通知,已读
		Map<String,Object> result=MyUtil.putMapParams("state", 1);
		
		return result;
	}
	
	public Map<String, Object> deleteComment(String commentId) {
		workerDao.deleteComment(commentId);//删除单挑评论
		Map<String,Object> result=MyUtil.putMapParams("state", 1);
		return result;
	}
	
	public Map<String, Object> deletePhoto(String infoId) {
		workerDao.deletePhotoComment(infoId);//删除单挑发表的所有评论
		workerDao.deletePhotoDianzan(infoId);//删除单挑发表的所有点赞
		GartenPhotos photo=workerDao.findPhotoOne(infoId);
		MyUtil.delePhotoImgs(photo);
		workerDao.deletePhoto(infoId);//删除单挑发表
		
		Map<String,Object> result=MyUtil.putMapParams("state", 1);
		return result;
	}
	
	
	
	

	/**
	 * 大改
	 */
		public Map<String, Object> babyCheck(String token,Long time,Integer classId) throws ParseException {
			time=MyUtil.getYMDLong(time);
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo);
			if(null!=workerInfo){//验证用户
				Integer frost =0;
				GartenInfo gartenInfo = workerDao.findGartenInfoById(workerInfo.getGartenId());
				if(gartenInfo.getAccountState()==1){
					frost=1;
				}
				
				List<BabyCheckLogAll> babyCheckLogs= workerDao.findBabyCheckByClassId(MyUtil.putMapParams("time",time,"classId",classId));//获取所有宝宝的晨检 考勤信息
				MyUtil.putMapParams(result, "state",1,"info",/*0==babyCheckLogs.size()?babyCheckLogs:*/ MyUtil.paixuBabyCheckLog(babyCheckLogs),"frost",frost);//排序 体温0的在前面 总的按id排序
				
				//请求一次接口 访问次数加一
				long current = System.currentTimeMillis();
	            long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
				Map<String, Object> params = MyUtil.putMapParams("gartenId",workerInfo.getGartenId(),"type",1,"time",zero/1000);
	            VisitCount visitCount = parentDao.findVisitCount(params);
				if(null==visitCount){
					parentDao.addVisitCount(params);
				}else{
					parentDao.updateVisitCount(params);
				}
			}
			return result;
		}
	//如果是私有的相册 则判断是不是自己 不是自己就不给看
	public Map<String, Object> photo(String token,Integer pageNo) {
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo,"workerId",null);
		if(null!=workerInfo){//验证用户
			List<GartenPhotos> parentPhoto= workerDao.findParentPhotoByToken(MyUtil.putMapParams("token", token));
			List<GartenPhotos> workerPhoto= workerDao.findWorkerPhotoByToken(MyUtil.putMapParams("token", token));
			parentPhoto.addAll(workerPhoto);
			
			long current = System.currentTimeMillis();
            long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
			Map<String, Object> params = MyUtil.putMapParams("gartenId",workerInfo.getGartenId(),"type",3,"time",zero/1000);
            VisitCount visitCount = parentDao.findVisitCount(params);
			if(null==visitCount){
				parentDao.addVisitCount(params);
			}else{
				parentDao.updateVisitCount(params);
			}
			MyUtil.putMapParams(result, "state",1,"info",MyPage.listPage(MyUtil.getPhotoFinal(parentPhoto,token,0), pageNo),"workerId",workerInfo.getWorkerId());
		}
		return result;
	}
	

	/**
	 * 大改
	 */
	public Map<String, Object> classManage(String token,Integer pageNo,Integer classId) {
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo);
		if(null!=workerInfo){//验证用户
			List<ClassManage> babyInfos= workerDao.findBabysByClassId(classId);
			MyUtil.putMapParams(result, "state", 1, "info",MyPage.listPage(babyInfos, pageNo));
		}
		return result;
	}
	
	


	
	
	/**
	 * 大改
	 */
	public Map<String, Object> circle(String token,Integer type,Integer classId) throws ParseException {
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo);
		Set<Long> time=null;
		if(null!=workerInfo){//验证用户
			if(0==type){//食谱红圈    这些天起码有一顿餐记录
				time= workerDao.findRecipeCircleCircleByToken(token);
			}else if(1==type){//代接红圈    这些天起码有一条代接记录
				time= workerDao.findDaijieCircleCircleByClassId(classId);
				time=MyUtil.getYMDLongs(time);
			}else if(2==type){//课程红圈   这些天起码有一节课记录
				 time= workerDao.findLessonCircleByClassId(classId);
			}else if(3==type){//考勤,晨检红圈  这些天[没有]考勤,晨检
				 time= workerDao.findAttendanceCircleByToken(token);
				 time=MyUtil.reverse(time);//反转sss
			}else if(4==type){//宝宝异常红圈  这些天至少一个异常
				time= workerDao.findYichangLongByClassId( classId );
			}else if(5==type){//宝宝请假红圈  
				 List<BabyLeaveLog> babyLeave= workerDao.findLeaveLongByClassId(classId);
				 Set<Long> leave=MyUtil.findBTimeLongByStartEnd(babyLeave)	;
				 time=MyUtil.getYMDLongs(leave);
			}else if(6==type){//老师请假红圈
				 List<WorkerLeaveLog> workers= workerDao.findWLeaveLongByToken(MyUtil.putMapParams("token", token ));
				 Set<Long> leave=MyUtil.findWTimeLongByStartEnd(workers)	;
				 time=MyUtil.getYMDLongs(leave);
			}else if(7==type){//老师签到红圈,这些天有签到
				time= workerDao.findWCheckLongByToken( token );
			}
			
			MyUtil.putMapParams(result, "state", 1, "info", time);
		}
		return result;
	}
	
	/**
	 * 大改
	 */
	public Map<String, Object> lesson(String token,Long time,Integer classId) throws ParseException {
		time=MyUtil.getYMDLong(time);
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo);
		if(null!=workerInfo){//验证用户
			List<GartenLesson> lesson= workerDao.findLessonByClassId(MyUtil.putMapParams("classId",classId,"time",time));
			//得到早上  下午的课程
			MyUtil.putMapParams(result,"state", 1,"info",MyUtil.lessonClassify(lesson));
		}
		return result;
	}

	/**
	 * 大改
	 */
	public Map<String, Object> updateBaby(String token,String  babyName,String allergy,Long birthday,BigDecimal height,String hobby
			 ,String specialty ,Integer classId ,String health,Integer babyId,Float  weight,Integer sex) {
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		Map<String,Object> param=MyUtil.putMapParams("token", token,"babyName",  babyName,"allergy", allergy,"birthday", birthday,"height", height,"hobby", hobby,"sex", sex);
		MyUtil.putMapParams(param,"specialty", specialty , "classId", classId,"health", health ,"babyId",babyId,"weight" , weight );
		if(null!=workerInfo){//验证用户
				workerDao.findUpdateBabyByToken(param );//修改宝宝
				ClassManage baby=workerDao.findBabyById(babyId);//大改
				MyUtil.putMapParams(result,"state", 1,"info",baby);
		}
		return result;
	}


	/**
	 * 大改
	 */
		public Map<String, Object> performance(String token,Integer type,Integer pageNo,Integer classId) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo);
			if(null!=workerInfo){//验证用户
				List<BabyPerformanceLogAll> performance= workerDao.findPerformanceByClassId(classId);//获取当日已评价 未评价的表现信息
				MyUtil.putMapParams(result,"state",1,"info",MyPage.listPage((List<?>) MyUtil.paixuBabyPerformanceLog(performance,type).get("info"), pageNo));
			}
			return result;
		}
	
	public Map<String, Object> toPerformance(Integer performanceId,Float learn,Float play,Float eat,Float sleep,String remark) {
		remark= null==remark?"":remark;
		workerDao.updatePerformance(MyUtil.putMapParams("performanceId",performanceId,"learn",learn,"play",play,"eat",eat ,"sleep",sleep,"remark",remark));
		Map<String,Object> result=MyUtil.putMapParams("state", 1);
		return result;
	}

	public Map<String, Object> sign(String token,Long time) throws ParseException, InterruptedException {
		time=MyUtil.getYMDLong(time);
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//验证用户
			WorkerCheckLog workerCheckLog= workerDao.findWorkerCheckLogByToken(MyUtil.putMapParams("token", token, "time", time));
			MyUtil.putMapParams(result,"state",1,"info",workerCheckLog);
		}
		return result;
	}
	
	
	public Map<String, Object> recipe(String token,Long time) throws ParseException {
		time=MyUtil.getYMDLong(time);
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0);
		if(null!=workerInfo){//验证用户
				Map<String,Object> param=MyUtil.putMapParams("token", token, "time", time,"gartenId",workerInfo.getGartenId());
				List<GartenRecipe> recipe= workerDao.findRecipeByToken(param);
				MyUtil.putMapParams(result, "state", 1,"info",recipe);
		}
		return result;
	}
	
	/**
	 * 大改
	 */
	public Map<String, Object> leaveLog(String token, Long time,Integer classId) throws ParseException {
		time=MyUtil.getYMDLong(time);
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo);
		if(null!=workerInfo){//验证用户
			MyUtil.putMapParams(result, "state", 1,"info",MyUtil.paixuBabyLeaveLog(workerDao.findLeaveLogByClassId(MyUtil.putMapParams("classId", classId, "time", time))));
		}
		return result;
	}
	
	

	public Map<String, Object> feedback(String token, String content) {
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0);
		if(null!=workerInfo){//验证用户
			Map<String,Object> param=MyUtil.putMapParams("token", token, "content", content,"gartenId",workerInfo.getGartenId(),"job",workerInfo.getJob(),"name",workerInfo.getWorkerName());
			MyUtil.putMapParams(param,"jobId", workerInfo.getWorkerId(),"phoneNumber", workerInfo.getPhoneNumber());
			 workerDao.createFeadbackByToken(param);
			 result.put("state", 1);
		}
		return result;
	}

	public Map<String, Object> version(String token) {
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//验证用户
			Version vs=workerDao.findVersion( );
			MyUtil.putMapParams(result,"state",1,"info",vs);
		}
		return result;
	}

		public Map<String, Object> introduceActivity(String token) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=workerInfo){//验证用户
				GartenInfo garten=workerDao.findGartenInfoById(workerInfo.getGartenId());
				MyUtil.putMapParams(result,"state",1,"info", garten.getIntroduceHtml());
			}
			return result;
		}

		public Map<String, Object> activity(String token) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			if(null!=workerInfo){//验证用户
				List<ActivityDetailAll> activituLog= workerDao.findIntroduceActivityByToken(workerInfo.getGartenId());//这个幼儿园所有的活动
				 //这个幼儿园的活动
				MyUtil.putMapParams(result,"state",1,"info", activituLog);
			}
			return result;
		}

		
		public void insertBybyCheck(Integer babyId, Integer gartenId) throws ParseException {
			Map<String,Object> param=MyUtil.putMapParams("babyId", babyId, "gartenId", gartenId);
			String startdate="2017年01月01日";
			Calendar cal = Calendar.getInstance();
			for(int i=0;i<1500;i++){
				Date date =(new SimpleDateFormat("yyyy年MM月dd日")).parse(startdate);
				cal.setTime(date);
				cal.add(Calendar.DATE,1);
				date =cal.getTime();
				startdate = (new SimpleDateFormat("yyyy年MM月dd日")).format(date);//加一天后结果
				if(startdate.equals("2020年01月01日")){
					break;
				}
				param.put("time", MyUtil.stringLon(startdate));
				workerDao.insetBabyCheck(param);
				workerDao.insetBabyPerformance(param);
				System.err.println(startdate);
			}
			
		}
		
		public void insertWorkerCheck(Integer workerId, Integer gartenId) throws ParseException {
			Map<String,Object> param=MyUtil.putMapParams("workerId", workerId, "gartenId", gartenId);
			String startdate="2017年01月01日";
			Calendar cal = Calendar.getInstance();
			for(int i=0;i<1500;i++){
				Date date =(new SimpleDateFormat("yyyy年MM月dd日")).parse(startdate);
				cal.setTime(date);
				cal.add(Calendar.DATE,1);
				date =cal.getTime();
				startdate = (new SimpleDateFormat("yyyy年MM月dd日")).format(date);//加一天后结果
				if(startdate.equals("2020年01月01日")){
					break;
				}
				param.put("time", MyUtil.stringLon(startdate));
				workerDao.insetWorkerCheck(param);
				System.err.println(startdate);
			}
			
		}

		/**
		 * 大改
		 */
				public Map<String, Object> daijie(String token,Integer type,Long time,Integer classId) throws ParseException {
					time=MyUtil.getYMDLong(time);
					WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
					Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo);
					if(null!=workerInfo){//验证用户
						List<Daijie> daijieInfo=workerDao.findDaijieByClassId(classId);
						MyUtil.putMapParams(result, "state",1, "info", MyUtil.paixuDaijieLog(daijieInfo,type,time).get("info"));
					}
					return result;
				}
		
		public Map<String, Object> agreeDaijie(String token,Integer daijieId) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=workerInfo){//验证用户
				workerDao.findAgreeDaijieByDaijieId(MyUtil.putMapParams("daijieId", daijieId));
				MyUtil.putMapParams(result, "state",1);
				
				DaijieInfo dj = workerDao.findDaijieById(daijieId);
				ClassManage manage = parentDao.findBabyById(dj.getBabyId());
				try {
					bigcontrolService.pushOneWithTypeBabyInfo(MyParamAll.JIGUANG_PARENT_APP, MyParamAll.JIGUANG_PARENT_MASTER, MyParamAll.JIGUANG_PARENT_DAIJIE_MESSAGE, dj.getPhoneNumber(), 6, null,JSONObject.toJSONString(manage));
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}
		
		
		/**
		 * 大改
		 */
		public Map<String, Object> yichang(String token,Long time,Integer classId) throws ParseException {
			time=MyUtil.getYMDLong(time);
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=workerInfo){//验证用户
				//检验幼儿园冻结
				Integer frost =0;
				GartenInfo gartenInfo = workerDao.findGartenInfoById(workerInfo.getGartenId());
				if(gartenInfo.getAccountState()==1){
					frost=1;
				}
				Map<String,Object> param=MyUtil.putMapParams("time", time,"classId",classId);
				List<UnusualAll> yichangs= workerDao.findUnusualAllByClassId(param);
				MyUtil.putMapParams(result, "state",1, "info", yichangs,"frost",frost);
			}
			return result;
		}
		
		
		
		//同意异常
		/*
		 * state 1是宝宝异常  给主监护人 发送推送
		 */
		public Map<String, Object> yichangResolve(Integer unusualId,Integer state) {
			workerDao.resolveUnusual( unusualId,state);
			if(1==state){
				Unusual unusual = smallcontrolDao.findUnusualByUnusualId(unusualId);
				BabyInfo babyInfo = attendanceDao.findBabyById(unusual.getJobId());
				ParentInfo parentInfo = parentDao.findParentById(babyInfo.getParentId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String content = "您的孩子"+babyInfo.getBabyName()+"于"+sdf.format(unusual.getUnusualTime()*1000);
				Integer mode = unusual.getUnusualType();
				String type = mode==5?"上午迟到":(mode==6?"上午早退":(mode==7?"下午迟到":(mode==8?"下午早退":(mode==9?"下午提前入园":"下午推迟离园"))));
				String message = content +type;
				try {
					bigcontrolService.pushOneWithType(MyParamAll.JIGUANG_PARENT_APP, MyParamAll.JIGUANG_PARENT_MASTER, message, parentInfo.getPhoneNumber(),3,babyInfo.getBabyId(),null);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map<String,Object> result=MyUtil.putMapParams("state", 1);
			return result;
		}
		
		
		
		public Map<String, Object> agreeLeaveLog(Integer leaveId,Integer type) {
			if(0==type){
				workerDao.updateAgreeLeaveByCheckId(leaveId);
			}else if(1==type){
				workerDao.deleteBabyLeaveByleaveId(leaveId);
			}
			Map<String,Object> result=MyUtil.putMapParams("state", 1);
			return result;
		}

		/**
		 * 大改
		 */
		public Map<String, Object> linkManParent(String token ,Integer classId) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null,"infoAndroid",null);
			if(null!=workerInfo){//验证用户
				List<ParentInfoShort> parents=workerDao.findParentLinkMan(classId);
				List<WorkerInfoShort> teachers=workerDao.findTeacherLinkMan(token);//该幼儿园所有的职工和园长
				Map<String,Map> all=MyUtil.paixuParentByZiMu(parents,teachers,0,workerInfo.getGartenId());

				MyUtil.putMapParams(result, "state",1, "info",all.get("result"),"infoAndroid",all.get("resultAndroid").get("result"));
			}
			return  result;
		}
		/**
		 * 大改
		 */
		public Map<String, Object> linkManWorker(String token ) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null,"infoAndroid",null);
			if(null!=workerInfo){//验证用户
				List<ParentInfoShort> parents=null;
				List<WorkerInfoShort> teachers=workerDao.findTeacherLinkMan(token);//该幼儿园所有的职工和园长
				Map<String,Map> all=MyUtil.paixuParentByZiMu(parents,teachers,1,workerInfo.getGartenId());
				MyUtil.putMapParams(result, "state",1, "info",all.get("result"),"infoAndroid",all.get("resultAndroid").get("result"));//一个给安卓用一个给IOS用
			}
			return  result;
		}
		
		
		
		
		/**
		 * 大改
		 */
		public Map<String, Object> teacher(String token) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=workerInfo){//验证用户
				WorkerInfoShort teacher=workerDao.findTeacherByToken(token);//该幼儿园所有的职工和园长
				MyUtil.putMapParams(result, "state",1,"info",teacher);
			}
			return  result;
		}
		
		public Map<String, Object> updateTeacher(String token,String workerName,Integer sex,Integer age) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=workerInfo){//验证用户
				workerDao.updateTeacherMessage(MyUtil.putMapParams("token",token,"workerName",workerName,"sex",sex,"age",age));
				MyUtil.putMapParams(result, "state",1);
			}
			return  result;
		}
		
		public Map<String, Object> updateTeacherHead(HttpServletRequest request,HttpSession session,String token) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=workerInfo){//验证用户
				String imgAddress=MyUtilAll.headImage(request, session, "worker=updateTeacherHead=workerId"+workerInfo.getPhoneNumber()+"="+new Date().getTime()/1000,workerInfo.getHeadImg());
				workerDao.updateTeacherHead(MyUtil.putMapParams("token",token,"imgAddress",imgAddress));
				MyUtil.putMapParams(result, "state",1);
			}
			return  result;
		}
		
		//Util类用到的老师信息
		public WorkerInfo getTeacher(String token) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			return  workerInfo;
		}
		
		
		public List<Comment> findEvaluationByInfoId(Integer infoId) {
			List<Comment> teacher=workerDao.findEvaluationByInfoId(infoId);//该幼儿园所有的职工和园长
			return  teacher;
		}


		public List<PhotoDianZan> findDianZanByInfoId(Integer infoId) {
			List<PhotoDianZan> count=workerDao.findDianZanByInfoId(infoId);//该幼儿园所有的职工和园长
			return  count;
		}
		
		public Map<String, Object> dianzan(String token,Integer infoId) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			if(null!=workerInfo){//验证用户
				Map<String,Object> param=MyUtil.putMapParams("token", token, "infoId",infoId,"name",workerInfo.getWorkerName(),"job",workerInfo.getJob(),"jobId",workerInfo.getWorkerId());
				PhotoDianZan dianzan=workerDao.findHasdianzan(param);
				if(null==dianzan){//改用户为点赞过 创建点赞
					workerDao.createDianzan(param);
				}else{
					workerDao.deleteDianzan(param);
				}
				List<PhotoDianZan> dianzans=workerDao.findDianZanByInfoId(infoId);
				MyUtil.putMapParams(result,"state", 1,"info",dianzans);
			}
			return  result;
		}
		
		public Map<String, Object> comment(String token,Integer infoId,Integer replyCommentId,String replyName,String commentContent) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			if(null!=workerInfo){//验证用户
				Map<String,Object> param=MyUtil.putMapParams( "infoId",infoId,"replyCommentId",replyCommentId,"replyName",replyName,"commentName",workerInfo.getWorkerName(),"commentContent",commentContent, "jobId", workerInfo.getWorkerId(),"job",workerInfo.getJob());
				workerDao.createCommentPhoto(param);
					List<Comment> teacher=workerDao.findEvaluationByInfoId(infoId);//该发表的所有评论
					MyUtil.putMapParams(result,"state", 1,"info",teacher);
			}
			return  result;
		}
		//这个用户对这个发表是否点赞
		public int findIsDianZanByInfoId(String token,Integer infoId) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByTokenAll( token);
			System.err.println(workerInfo);
			Map<String,Object> param=MyUtil.putMapParams("token", token, "infoId",infoId,"name",workerInfo.getWorkerName(),"job",workerInfo.getJob(),"jobId",workerInfo.getWorkerId());
			PhotoDianZan dianzan=workerDao.findHasdianzan(param);
			int a= null==dianzan?0:1;
			return  a;
		}
		public Map<String, Object> publish(String token, String content) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=workerInfo){//验证用户
				/*Integer state= null==state?0:1;*/
				Map<String,Object> param=MyUtil.putMapParams( "token", token, "content", content, "state", 1, "title", workerInfo.getWorkerName());
				MyUtil.putMapParams(param, "gartenId", workerInfo.getGartenId(), "job",workerInfo.getJob() , "id", workerInfo.getWorkerId());
				workerDao.createPhoto(param);
				result.put("state", 1);
			}
			return result;
		}
		
		public Map<String, Object> publishImg(HttpServletRequest request,HttpSession session,String token,String content) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=workerInfo){//验证用户
				/*GartenPhotos photo=workerDao.findPhotoByToken(token);*/
				String imgs=MyUtilAll.photoImage(request, session,"worker=publishPhotoImg=workerId"+workerInfo.getWorkerId());
				Map<String,Object> param=MyUtil.putMapParams( "token", token, "imgs", imgs.equals("")?null:imgs, "state", 1, "title", workerInfo.getWorkerName(),"content",content);
				MyUtil.putMapParams(param, "gartenId", workerInfo.getGartenId(), "job",workerInfo.getJob() , "id", workerInfo.getWorkerId());
				workerDao.createPhoto(param);
				result.put("state", 1);
			}
			return result;
		}
		//验证短信  再修改密码
		public Map<String, Object> updateLogin(String phoneNumber, String pwd, String number) throws ParseException {
			 WorkerInfo workerInfo=workerDao.findWorkerByAccount(phoneNumber);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info","没有这个用户");
				if(null!=workerInfo){
					Map<String,Object> duanxin=duanxin(workerInfo.getPhoneNumber(),0,number);//0代表 老师端短信 1家长2园长 3总控 4 代理商
					String newToken= UUID.randomUUID().toString();
					MyUtil.putMapParams(result,"state", 2, "info", "验证码错误");
					if(duanxin.get("0").equals("成功")){
						workerDao.updatePwdToken(MyUtil.putMapParams("token", newToken, "phoneNumber", phoneNumber,"pwd",CryptographyUtil.md5(pwd, "lxc")));
						MyUtil.putMapParams(result,"state", 1, "info", newToken);
					}
				}
				//验证码错误返回  验证错误信息
				return result;
		}

		//生成验证码 并发送  type 0:老师端 1:家长端  2：园长端
	public Map<String, Object> sendCheck(String phoneNumber ,Integer type) {
		String number=(int)(Math.random()*9000)+1000+"";
		Map<String,Object> param=MyUtil.putMapParams("phoneNumber", phoneNumber, "type", type, "number", number);
		//new YunTongXun().register(phoneNumber,"75338",new String[]{number,"10"});
		String[] data=new String[]{number,"10"};
		MyUtil.register(phoneNumber, MyParamAll.YTX_DUANXIN_YZM, data);
		workerDao.insertDuanxin(param);
		workerDao.deleteDuanxin();
		Map<String,Object> map=MyUtil.putMapParams("state", 1);
		return map;
	}
	
		// type 0:老师端  1:家长端  2:院长端  短信验证码 3总控制端 4 幼儿园控制端(防止园长端互顶)
	public Map<String, Object> duanxin(String phoneNumber, Integer type, String number) throws ParseException {
		Map<String,Object> param=MyUtil.putMapParams("type", type, "phoneNumber", phoneNumber);
		Map<String,Object> map=MyUtil.putMapParams("0","请输入验证码");
		//登陆查找是否有这个用户 没有创建
		CheckNumber ch=workerDao.findCheck(param);
		if(ch==null||!ch.getNumber().equals(number)){
			map.put("0", "错误");
			return map;
		}
		Long a=new Date().getTime()-new SimpleDateFormat("yyyyMMddHHmmss").parse(ch.getTime()).getTime();
		if(a/1000<=600){//小于10分钟的验证码才有效
			map.put("0", "成功");
			return map;
		}else{
			map.put("0", "过期");
			return map;
		}
	}

	public Map<String, Object> flower(String token,Integer pageNo) {
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo,"count",0);
		if(null!=workerInfo){//验证用户
			List<FlowerAll> flowers=workerDao.findFlowerByToken(token);
			Integer count=workerDao.findFlowerCountByToken(token);
			MyUtil.putMapParams(result,"state",1,"info",MyPage.listPage(flowers, pageNo),"count",count);
		}
		return result;
	}

	public Map<String, Object> workerLeaveLog(String token, Long time) throws ParseException {
		time=MyUtil.getYMDLong(time);
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo);
		if(null!=workerInfo){//验证用户
			List<WorkerLeaveLogPrin> leaves=workerDao.findTimeWorkerLeaveByToken(MyUtil.putMapParams("token", token, "time", time));
			MyUtil.putMapParams(result,"state",1,"info",MyUtil.paixuWorkerLeaveLog(leaves));
		}
		return result;
	}
	

	/**
	 * 大改
	 */
		public Map<String, Object> video(String token,Integer classId) {
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			if(null!=workerInfo){//验证用户
				Integer frost =0;
				GartenInfo gartenInfo = workerDao.findGartenInfoById(workerInfo.getGartenId());
				if(gartenInfo.getAccountState()==1){
					frost=1;
				}
				List<Video> videos=workerDao.findVideosByToken(MyUtil.putMapParams("token", token, "classId", classId));
				
				//请求一次接口 访问次数加一
				long current = System.currentTimeMillis();
	            long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
				Map<String, Object> params = MyUtil.putMapParams("gartenId",workerInfo.getGartenId(),"type",2,"time",zero/1000);
	            VisitCount visitCount = parentDao.findVisitCount(params);
				if(null==visitCount){
					parentDao.addVisitCount(params);
				}else{
					parentDao.updateVisitCount(params);
				}
				MyUtil.putMapParams(result,"state",1,"info",videos,"frost",frost);//frost 1被冻结状态	
			}
			return result;
		}
	

	public Map<String, Object> workerToLeave(String token, String reason, Long startTime, Long endTime) throws ParseException {
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0);
		if(null!=workerInfo){//验证用户
			List<WorkerLeaveLog> workers= workerDao.findWLeaveLongByToken(MyUtil.putMapParams("token", token ));
			  Integer able=MyUtil.ableToLeaveWorker(workers,startTime,endTime);//able 0:没有重复请假 1:开始时间冲突 2:结束时间冲突
			  MyUtil.putMapParams(result,"state", able+1);
			  if(0==able){
				  workerDao.insertWorkerLeaveLog(MyUtil.putMapParams("workerId", workerInfo.getWorkerId(),"reason",reason,"startTime",startTime,"endTime",endTime));
					MyUtil.putMapParams(result,"state", 1);
			  }
			
		}
		return result;
	}	
	//判断这个宝宝是否报名这个活动
	public Integer isApplyActivity(Integer babyId, Integer activityId) {
		Integer a=workerDao.isApplyActivity(MyUtil.putMapParams("babyId",babyId,"activityId",activityId));
		return a;
	}

	public Map<String, Object> replyPerson(String token, Integer infoId) {
		WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"name",workerInfo);
		if(null!=workerInfo){//验证用户
			Set<String>  name=parentDao.findPhoneNameByInfoId(infoId);
			MyUtil.putMapParams(result,"state", 1,"name",MyUtil.paixuReplyPerson(name,workerInfo.getWorkerName()));
		}
		return result;
	}

	

	

	public Map<String, Object> hxGetToken() {
		JSONObject Json = new JSONObject();  
		Json.put("grant_type", "client_credentials");
		Json.put("client_id", "YXA6V0QPwEdVEee_67GekiVw0w");
		Json.put("client_secret", "YXA6Sm0V3Uks4ipbuJ5vAD654KcJ5Xo");
    	Map<String,Object> result=MyUtil.HttpRequest("http://a1.easemob.com/"+MyParamAll.ORG_NAME+"/"+MyParamAll.APP_NAME+"/token",Json,"");
    	return result;
	}

	public Map<String, Object> findLeaveMessage(String authorization, Integer type, String token) throws ClientProtocolException, IOException {
		String username="";
		String password="";
		if(0==type){
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			username="worker"+workerInfo.getWorkerId();
			password=workerInfo.getPwd();
		}else if(1==type){
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			username="parent"+parentInfo.getParentName();
			password=parentInfo.getPwd();
		}
		
		Map<String, Object> result=MyUtil.getWeChatUserInfo("http://a1.easemob.com/1103170602178721/yunjiajie/users/"+username+"/offline_msg_count",authorization);
		return result;
	}	
	
	
	public Map<String, Object> findChat(String authorization, Integer type, String token) throws ClientProtocolException, IOException, ParseException {
		String username="";
		String password="";
		if(0==type){
			WorkerInfo workerInfo= workerDao.findWorkerInfoByToken( token);
			username="worker"+workerInfo.getWorkerId();
			password=workerInfo.getPwd();
		}else if(1==type){
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			username="parent"+parentInfo.getParentName();
			password=parentInfo.getPwd();
		}
		SimpleDateFormat sim=new SimpleDateFormat("yyyy/MM/dd");
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例  
		ca.setTime(sim.parse(sim.format(new Date()))); // 设置时间为当前时间  
		ca.add(Calendar.SECOND, -1);
		ca.add(Calendar.DATE, +1);// 日期减1  
		Date resultDate = ca.getTime(); // 结果  
		String  day=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(resultDate);
		day=day.replace(":", "%3A");
		day=day.replace("/", "%2F");
		day=day.replace(" ", "%20");
		Map<String, Object> jintian=MyUtil.getWeChatUserInfo("http://a1.easemob.com/1103170602178721/yunjiajie/chatmessages/"+day,authorization);
		ca.add(Calendar.DATE, -1);
		resultDate = ca.getTime();
		  day=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(resultDate);
		  day=day.replace(":", "%3A");
			day=day.replace("/", "%2F");
			day=day.replace(" ", "%20");
		Map<String, Object> zuotian=MyUtil.getWeChatUserInfo("http://a1.easemob.com/1103170602178721/yunjiajie/chatmessages/"+day,authorization);
		ca.add(Calendar.DATE, -1);
		resultDate = ca.getTime();
		  day=new SimpleDateFormat("yyyy/MM/dd HH:mm").format(resultDate);
		day=day.replace(":", "%3A");
		day=day.replace("/", "%2F");
		day=day.replace(" ", "%20");
		Map<String, Object> qiantian=MyUtil.getWeChatUserInfo("http://a1.easemob.com/1103170602178721/yunjiajie/chatmessages/"+day,authorization);
		Map<String, Object> result=MyUtil.putMapParams("jintian", jintian, "zuotian", zuotian,"qiantian",qiantian);
		return result;
	}

	public Map<String, Object> sendMessage(String authorization, Integer type, String token, String message,Integer toType,String toToken) {
		String username="";
		String tousername="";
		if(0==type){
			username="worker"+workerDao.findWorkerInfoByToken( token).getWorkerId();
		}else if(1==type){
			username="parent"+parentDao.findParentInfoByToken( token).getParentId();

		}
		if(0==toType){
			tousername="worker"+workerDao.findWorkerInfoByToken( toToken).getWorkerId();
		}else if(1==toType){
			tousername="parent"+parentDao.findParentInfoByToken( toToken).getParentId();

		}
		
		String[] target={tousername};
		String[] from={username};
		JSONObject Json = new JSONObject();  
		JSONObject msg = new JSONObject(); 
		msg.put("type", "txt");
		msg.put("msg", message);
		Json.put("target_type", "users");
		Json.put("target", target);
		Json.put("from", from);
		Json.put("msg", msg);
		Map<String, Object> result=MyUtil.HttpRequest("http://a1.easemob.com/1103170602178721/yunjiajie/messages",Json,authorization);
		System.err.println(msg.toJSONString());
		
		workerDao.insertHX(new HXLog(username,tousername,msg.toJSONString()));
		return result;
	}	
	
	//解压GZ文件
	public static Map<String,Object> readMessage() {
		String sourcedir="C:\\Users\\lxc2\\Downloads\\2017070710.gz";
        String ouputfile = "";
        try {  
            //建立gzip压缩文件输入流 
            FileInputStream fin = new FileInputStream(sourcedir);   
            //建立gzip解压工作流
            GZIPInputStream gzin = new GZIPInputStream(fin);   
            //建立解压文件输出流  
            ouputfile = sourcedir.substring(0,sourcedir.lastIndexOf('.'));
            /*ouputfile = ouputfile.substring(0,ouputfile.lastIndexOf('.'));*/
            FileOutputStream fout = new FileOutputStream(ouputfile);   
            
            int num;
            byte[] buf=new byte[1024];

            while ((num = gzin.read(buf,0,buf.length)) != -1)
            {   
            	fout.write(buf,0,num);
            }

            gzin.close();   
            fout.close();   
            fin.close();   
        } catch (Exception ex){  
            System.err.println(ex.toString());  
        }  
        return null;
    }

	
	 /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static Map<String,Object> readFileByLines() {
    	Map<String,Object> result=new HashMap<String,Object>();
    	result.put("info","");
        File file = new File("C:\\Users\\lxc2\\Downloads\\2017070715");
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            StringBuffer sb=new StringBuffer();
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
            	  Object succesResponse = JSON.parse(tempString);    //先转换成Object
                  Map map = (Map)succesResponse;         //Object强转换为Map
            	result.put("line("+line+")", map);
                System.err.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return result;
    }

	public Map<String, Object> getMyHtml() {
		//param1:幼儿园Id  param2:list<GartenMsg>
		List<GartenMsg> gartenMsg=new ArrayList<GartenMsg>();
		String content1="查看陈旺平 (认为天高地厚 - 业务员,上海 嘉定区) 的领英职业档案。加入领英,找找同事同学,结识业界同行,与其他职场人士一起驰骋职场。";
		String content2="天眼查为您提供陈旺平信息查询、诉讼财务信息查询、注册信息查询、电话地址查询等企业信息查询服务,了解陈旺平信用报告、股东法人、经营状况等详细工商信息,就到天眼查!查看陈旺平 (认为天高地厚 - 业务员,上海 嘉定区) 的领英职业档案。加入领英,找找同事同学,结识业界同行,与其他职场人士一起驰骋职场。";
		String img="http://yiyunwangl.oss-cn-hangzhou.aliyuncs.com/cleaning/images/1836780.3779.jpg";
		List<String> imgs=new ArrayList<String>();
		List<String> contents=new ArrayList<String>();
		imgs.add(img);
		contents.add(content1);
		contents.add(content2);
		GartenMsg gm=new GartenMsg(imgs,contents);
		gartenMsg.add(gm);
		gartenMsg.add(gm);
		FreeMarkerTest.getMyHtml(gartenMsg);
    	Map<String,Object> result=MyUtil.putMapParams("gartenMsg", gartenMsg);
		return result ;
	}

	


	//注册环信  type 0:老师 1:家长 2:园长
  	public void regist(String authorization,Integer type,Integer id) throws IOException {
  		String username="";
  		String password="1";
  		if(0==type){//老师
  			WorkerInfo workerInfo= workerDao.findWorkerInfoById(id);
  			username="worker"+workerInfo.getWorkerId();
  		}else if(1==type){//家长
  			ParentInfo parentInfo= parentDao.findParentById(id);
  			username="parent"+parentInfo.getParentId();
  		}else if(2==type){//园长
  			WorkerInfo workerInfo= workerDao.findWorkerInfoById(id);
  			username="worker"+workerInfo.getWorkerId();
  		}
  		
  		JSONObject Json = new JSONObject();  
  		Json.put("username", username);//JSONObject对象中添加键值对  
  		Json.put("password", password);
  		MyUtil.HttpRequest(MyParamAll.HUANXIN_BASE+MyParamAll.ORG_NAME+"/"+MyParamAll.APP_NAME+"/users",Json,authorization);
  	}
  	
  //给这个用户添加所有的环信好友
  	public void addFriend(String authorization,Integer type,Integer id) throws ClientProtocolException, IOException {
  		String username="";
  		String otherPerson="";
  		if(0==type){//老师
  			WorkerInfo workerInfo= workerDao.findWorkerInfoById(id);
  			username="worker"+workerInfo.getWorkerId();
  		}else if(1==type){//家长
  			ParentInfo parentInfo= parentDao.findParentById(id);
  			username="parent"+parentInfo.getParentId();
  		}else if(2==type){//园长
  			WorkerInfo workerInfo= workerDao.findWorkerInfoById(id);
  			username="worker"+workerInfo.getWorkerId();
  		}
  		JSONObject Json = new JSONObject();  
  		List<JSONObject> users=(List<JSONObject>) getUsers(MyParamAll.ACCESS_TOKEN).get("entities");
  		for(JSONObject user:users){
  			otherPerson=user.get("username")+"";
  			MyUtil.HttpRequest(MyParamAll.HUANXIN_BASE+MyParamAll.ORG_NAME+"/"+MyParamAll.APP_NAME+"/users/"+username+"/contacts/users/"+otherPerson,Json,authorization);
  		}
  	}
  	
  	//获取所有环信用户
  	public Map<String, Object> getUsers(String authorization) throws ClientProtocolException, IOException {
		JSONObject Json = new JSONObject();  
		Map<String, Object> result=MyUtil.getWeChatUserInfo(MyParamAll.HUANXIN_BASE+MyParamAll.ORG_NAME+"/"+MyParamAll.APP_NAME+"/users"+"?limit=10000",authorization);
		return result;
	}
	

  	/**
     * 
     * 1 删除活动activity_detail的OSShtml页面
     * 2 删除活动activity_detailhtml页面里面包括的所有OSS图片
     * 3 删除activity_detail记录
     * 4 删除activity_log用户活动记录
     */
 	public Map<String, Object> deleteActivity(Integer activityId) {
 		ActivityDetailAll ac=workerDao.findActivityDetailById(activityId);
 		String[] imgs= ac.getHtmlImgs().split(",");
 		if(null!=ac.getContentHtml()&&!"".equals(ac.getContentHtml())){//1
 			MyUtilAll.deleteOldOSS(ac.getContentHtml());
 		}
 		for(String img:imgs){//2
 			if(null!=img&&!"".equals(img)){
 				MyUtilAll.deleteOldOSS(ac.getContentHtml());
 			}
 		}
 		workerDao.deleteActivityDetail(activityId);
 		workerDao.deleteActivityLog(activityId);
 		return MyUtil.putMapParams( "state", 1);
 	}
 	
 	public  Map<String, Object>  htmlIntroduce(String webStr,String token){  
    	//1
    	WorkerInfo worker = smallcontrolDao.findWorkerByToken(token);
  	  Object succesResponse = JSON.parse(webStr);    //先转换成Object
  	List<Map<String,Object>> list= (List<Map<String, Object>>) succesResponse;
        //Map map = (Map)succesResponse;         //Object强转换为Map
        //List<Map<String,Object>> list=(List<Map<String, Object>>) map.get("list");				//获取List<Map>
        OSSClient ossClient=CreateHtml.getOSSClient();  //初始化OSSClient  
        List<GartenMsg> gartenMsg=new ArrayList<GartenMsg>();//构建页面所需的数据
        Map<String ,Object> param=MyUtil.putMapParams("info",gartenMsg,"gartenId",worker.getGartenId());//构建页面所需的数据
        String imaOne=null;//累计图片的OSS
        String imaAddress=null;//累计图片的OSS地址 保存到数据库
        List<String> img=null;
        for(Map<String,Object> mapone:list){//遍历List每一个<img><content>对象
        	 img=(List<String>) mapone.get("img");
        	 List<String> imgs=new ArrayList<String>();
	    	List<String> contents= (List<String>) mapone.get("content");
        	 if(null!=img){
        		 for(String imgOne:img){
        			 CreateHtml.GenerateImage( imgOne,"C:\\introduce.jpg")  ;//把每张图片字符创存入C://
        			 imaOne=MyUtilAll.uploadObject2OSS( ossClient,new File("C:\\introduce.jpg"), MyParamAll.MYOSS_BUCKET, "1IntroduceOne");
        			System.err.println(imaOne);
        			imaAddress= null==imaAddress?imaOne:imaAddress+","+imaOne ;
     	    		imgs.add(imaOne);
        		 }
        	 }
        	 GartenMsg gm=new GartenMsg(imgs,contents);
	    		gartenMsg.add(gm);//构建新对象
        }
        CreateHtml.upHtml(param,"C:\\introduce.html");//成功生成HTML("success.html")  C:需要预先放入aaa.html模板文件
    	String htmlAddress=MyUtilAll.uploadObject2OSS( ossClient,new File("C:\\introduce.html"), MyParamAll.MYOSS_BUCKET, "1Introduce") ;
    	System.err.println(htmlAddress);
    	CreateHtml.delete("C:\\introduce.html");
    	GartenInfo garten=workerDao.findGartenInfoById(worker.getGartenId());
    	if(null!=garten.getIntroduceHtml()&&!"".equals(garten.getIntroduceHtml())){//删除原来的页面
    		MyUtilAll.deleteOldOSS(garten.getIntroduceHtml());
    	}
    	if(null!=garten.getIntroduceImgs()&&!"".equals(garten.getIntroduceImgs())){//删除原来的页面包含的图片
    		String[] imgs=garten.getIntroduceImgs().split(",");
    		for(String i:imgs){
        		MyUtilAll.deleteOldOSS(i);
    		}
    	}
    	MyUtil.putMapParams(param,"htmlAddress",htmlAddress,"imgAddress",imaAddress);
    	workerDao.updateIntroduce(param);
    	return param;
    }

	
    /**
   	 * 1 把web传过来的String转Map
   	 * 2 获取Map里面的图片字符串,全部传入OSS,并返回OSS每张图片全路劲
   	 * 3 根据图片地址重新构建Map对象构建html页面保存在本地
   	 * 4 把html页面上传到OSS
   	 * 5 删除html
   	 * 6数据库记录 OSShtml地址
   	 * 7数据库记录每张图片OSS地址
   	 * 8全部try catch
   	 */
       public  Map<String, Object>  htmlActivity(String webStr,String token,Long timeStart,Long timeEnd ,String imgWaibu,String content,String activityAddress,String title,Long joinTime){  
       	//1
    	   WorkerInfo worker = smallcontrolDao.findWorkerByToken(token);
     	  Object succesResponse = JSON.parse(webStr);    //先转换成Object
     	List<Map<String,Object>> list= (List<Map<String, Object>>) succesResponse;
           //Map map = (Map)succesResponse;         //Object强转换为Map
           //List<Map<String,Object>> list=(List<Map<String, Object>>) map.get("list");				//获取List<Map>
           OSSClient ossClient=CreateHtml.getOSSClient();  //初始化OSSClient  
           List<GartenMsg> gartenMsg=new ArrayList<GartenMsg>();//构建页面所需的数据
           Map<String ,Object> param=MyUtil.putMapParams("info",gartenMsg,"gartenId",worker.getGartenId());//构建页面所需的数据
           String imaOne=null;//累计图片的OSS
           String imaAddress=null;//累计图片的OSS地址 保存到数据库
           List<String> img=null;
           for(Map<String,Object> mapone:list){//遍历List每一个<img><content>对象
           	 img=(List<String>) mapone.get("img");
           	 List<String> imgs=new ArrayList<String>();
   	    	 List<String> contents= (List<String>) mapone.get("content");
           	 if(null!=img){
           		 for(String imgOne:img){
           			 CreateHtml.GenerateImage( imgOne,"C:\\activity.jpg")  ;//把每张图片字符创存入C://
           			 imaOne=MyUtilAll.uploadObject2OSS( ossClient,new File("C:\\activity.jpg"), MyParamAll.MYOSS_BUCKET, "1ActivityOne");
           			System.err.println(imaOne);
           			imaAddress= null==imaAddress?imaOne:imaAddress+","+imaOne ;
        	    		imgs.add(imaOne);
           		 }
           	 }
           	 GartenMsg gm=new GartenMsg(imgs,contents);
   	    		gartenMsg.add(gm);//构建新对象
           }
           CreateHtml.upHtml(param,"C:\\activity.html");//成功生成HTML("success.html")  C:需要预先放入aaa.html模板文件
       	String htmlAddress=MyUtilAll.uploadObject2OSS( ossClient,new File("C:\\activity.html"), MyParamAll.MYOSS_BUCKET, "1Introduce") ;
       	System.err.println(htmlAddress);
       	CreateHtml.delete("C:\\activity.html");//删除临时本地页面
        CreateHtml.GenerateImage( imgWaibu,"C:\\imgWaibu.jpg")  ;//上传活动预览的那张图片
		imaOne=MyUtilAll.uploadObject2OSS( ossClient,new File("C:\\imgWaibu.jpg"), MyParamAll.MYOSS_BUCKET, "1ActivityOne");
       	MyUtil.putMapParams(param,"htmlAddress",htmlAddress,"imgAddress",imaAddress,"activityAddress",activityAddress,"title",title);
       	MyUtil.putMapParams(param,"img",imaOne,"content",content,"timeStart",timeStart,"timeEnd",timeEnd,"joinTime",joinTime);
       	workerDao.insertActivity(param);
       	return param;
       }

       public Map<String, Object> ceshi() throws InterruptedException {
    		Map<String, Object> map = new HashMap<String,Object>();
    		XxxThread myThread = new XxxThread(); 
    		Thread thread = new Thread(myThread); 
    		thread.start(); 
    		for(int i=0;i<1000;i++){
    			Thread.sleep(10L);
    			System.err.println("第"+i+"个");
    		}
    		map.put("0", "成功");
    		return map;
    	}

       public void insertBabyCheckNew(Integer id,Integer gartenId) throws ParseException{
    	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			//Map<String,Object> param=MyUtil.putMapParams("babyId", an.getJobId(), "gartenId", workerInfo.getGartenId());
			String startdate="2017年01月01日";
			Calendar cal = Calendar.getInstance();
			ArrayList<BabyCheckSimple> list = new ArrayList<BabyCheckSimple>();
			for(int i=0;i<1500;i++){
				Date date =sdf.parse(startdate);
				cal.setTime(date);
				cal.add(Calendar.DATE,1);
				date =cal.getTime();
				startdate = sdf.format(date);//加一天后结果
				if(startdate.equals("2020年01月01日")){
					break;
				}
				BabyCheckSimple babyCheckSimple = new BabyCheckSimple(id,gartenId , sdf.parse(startdate).getTime()/1000);
				list.add(babyCheckSimple);
			}
			smallcontrolDao.insertBabyCheck(list);
			smallcontrolDao.insertBabyPerformance(list);
       }
       
       public void insertTeacherCheck(Integer id,Integer gartenId) throws ParseException{
    	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			//Map<String,Object> param=MyUtil.putMapParams("babyId", an.getJobId(), "gartenId", workerInfo.getGartenId());
			String startdate="2017年01月01日";
			Calendar cal = Calendar.getInstance();
			ArrayList<BabyCheckSimple> list = new ArrayList<BabyCheckSimple>();
			for(int i=0;i<1500;i++){
				Date date =sdf.parse(startdate);
				cal.setTime(date);
				cal.add(Calendar.DATE,1);
				date =cal.getTime();
				startdate = sdf.format(date);//加一天后结果
				if(startdate.equals("2020年01月01日")){
					break;
				}
				BabyCheckSimple babyCheckSimple = new BabyCheckSimple(id, gartenId, sdf.parse(startdate).getTime()/1000);
				list.add(babyCheckSimple);
			}
			smallcontrolDao.insertTeacherCheck(list);
       }

	public GartenInfo findGartenInfoById(Integer gartenId) {
		GartenInfo garten=workerDao.findGartenInfoById( gartenId);
		return garten;
	}

	public synchronized  Map<String,Object> ceshi2() throws ClientProtocolException, IOException {
		for(int i=0;i<5000;i++){
			regist1( MyParamAll.ACCESS_TOKEN, 0,i);
			addFriend1( MyParamAll.ACCESS_TOKEN, 0, i);
			regist1( MyParamAll.ACCESS_TOKEN, 1,i);
			addFriend1( MyParamAll.ACCESS_TOKEN, 1, i);
		}
		
		return null;
		
	}
	
	
	//注册环信  type 0:老师 1:家长 2:园长
	
		public synchronized  void regist1(String authorization,Integer type,Integer id) throws IOException {
			String username="";
			String password="1";
			if(0==type){//老师
				username="worker"+id;
			}else if(1==type){//家长
				username="parent"+id;
			}else if(2==type){//园长
				username="worker"+id;
			}
			
			JSONObject Json = new JSONObject();  
			Json.put("username", username);//JSONObject对象中添加键值对  
			Json.put("password", password);
			MyUtil.HttpRequest(MyParamAll.HUANXIN_BASE+MyParamAll.ORG_NAME+"/"+MyParamAll.APP_NAME+"/users",Json,authorization);
		}
		
		
		//给这个用户添加所有的环信好友
				public synchronized  void addFriend1(String authorization,Integer type,Integer id) throws ClientProtocolException, IOException {
					String username="";
					String otherPerson="";
					if(0==type){//老师
						username="worker"+id;
					}else if(1==type){//家长
						username="parent"+id;
					}else if(2==type){//园长
						username="worker"+id;
					}
					JSONObject Json = new JSONObject();  
					List<JSONObject> users=(List<JSONObject>) getUsers(MyParamAll.ACCESS_TOKEN).get("entities");
					System.err.println("users的长度"+users.size());
					for(JSONObject user:users){
						otherPerson=user.get("username")+"";
						MyUtil.HttpRequest(MyParamAll.HUANXIN_BASE+MyParamAll.ORG_NAME+"/"+MyParamAll.APP_NAME+"/users/"+username+"/contacts/users/"+otherPerson,Json,authorization);
					}
				}
		
}
