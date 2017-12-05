package com.garten.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.garten.dao.BigcontrolDao;
import com.garten.dao.ParentDao;
import com.garten.dao.SmallcontrolDao;
import com.garten.dao.WorkerDao;
import com.garten.model.activity.ActivityDetail;
import com.garten.model.activity.ActivityLog;
import com.garten.model.baby.BabyInfo;
import com.garten.model.baby.BabyLeaveLog;
import com.garten.model.baby.BabyPerformanceLog;
import com.garten.model.garten.GartenCharge;
import com.garten.model.garten.GartenInfo;
import com.garten.model.garten.GartenLesson;
import com.garten.model.garten.GartenPhotos;
import com.garten.model.garten.GartenRecipe;
import com.garten.model.garten.PhotoDianZan;
import com.garten.model.garten.Video;
import com.garten.model.other.Comment;
import com.garten.model.other.Feedback;
import com.garten.model.other.InfoLog;
import com.garten.model.other.Order;
import com.garten.model.other.Version;
import com.garten.model.other.VisitCount;
import com.garten.model.parent.ParentInfo;
import com.garten.model.worker.WorkerInfo;
import com.garten.model.worker.WorkerLeaveLog;
import com.garten.util.lxcutil.MyParamAll;
import com.garten.util.lxcutil.MyUtilAll;
import com.garten.util.md5.CryptographyUtil;
import com.garten.util.myutil.MyUtil;
import com.garten.util.mywxpay.ConfigUtil;
import com.garten.util.mywxpay.PayCommonUtil;
import com.garten.util.mywxpay.XMLUtil;
import com.garten.util.page.MyPage;
import com.garten.vo.baby.UnusualAll;
import com.garten.vo.bigcontrol.ClassManageBig;
import com.garten.vo.parent.ParentInfoShort;
import com.garten.vo.teacher.ActivityDetailAll;
import com.garten.vo.teacher.BabyCheckLogAll;
import com.garten.vo.teacher.BabyInfoShort;
import com.garten.vo.teacher.BabyPerformanceLogAll;
import com.garten.vo.teacher.ClassManage;
import com.garten.vo.teacher.Daijie;
import com.garten.vo.teacher.WorkerInfoShort;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;



@Service
public class ParentService {
	@Autowired
	private ParentDao parentDao;
	@Autowired
	private BigcontrolDao bigcontrolDao;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private WorkerDao workerDao;
	@Autowired
	private SmallcontrolDao smallcontrolDao;
	public Map<String, Object> login(String phoneNumber, String pwd) {
		Map<String,Object> result=MyUtil.putMapParams("state", 0, "info", "error");
		Map<String,Object> param=MyUtil.putMapParams("phoneNumber", phoneNumber,"pwd",CryptographyUtil.md5(pwd, "lxc"));
		ParentInfo parent=parentDao.findParentByPwd(param);
		String uuid="error";
		//如果worker为空则返回error
		//如果worker不为空则返回uuid,并修改token为uuid
		if(null!=parent&&!"".equals(parent)){
			uuid=UUID.randomUUID().toString();
			param.put("token", uuid);
			parentDao.updateToken(param);
			MyUtil.putMapParams(result,"state", 1, "info", uuid);
		}
		return result;
	}

	
	//验证短信  再修改密码  state 0登陆错误  1正确  2验证码错误
	public Map<String, Object> updateLogin(String phoneNumber, String pwd, String number) throws ParseException {
		 ParentInfo parent=parentDao.findWorkerByPhoNumber(phoneNumber);
		  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info","没有这个用户");
			if(null!=parent){
				Map<String,Object> duanxin=workerService.duanxin(phoneNumber,1,number);//1代表 验证家长端修改密码的短信
				String newToken= UUID.randomUUID().toString();
				if(duanxin.get("0").equals("成功")){
					parentDao.updatePwdToken(MyUtil.putMapParams("token", newToken, "phoneNumber", phoneNumber,"pwd",CryptographyUtil.md5(pwd, "lxc")));
					MyUtil.putMapParams(result,"state", 1, "info", newToken);
				}else{
					MyUtil.putMapParams(result,"state", 2, "info", "验证码错误");
				}
			}
			//验证码错误返回  验证错误信息
			return result;
	}


	/**首页
	 * [大改 获取孩子列表 mapper]
	 */
	public Map<String, Object> index(String token) {
		ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo,"babyList",parentInfo,"notified",parentInfo);
		if(null!=parentInfo){
			ParentInfoShort parentInfoShort= parentDao.findParentShortByToken(token);//获取首页家长信息
			List<BabyInfoShort> babyShorts=parentDao.findBabyShortByToken(token);//获取加长管理的所有孩子的信息
			MyUtil.putMapParams(result,"state", 1,"info",parentInfoShort,"babyList",babyShorts,"notified",parentDao.findInfoCount(token));
		}
		return result;
	}


	public Map<String, Object> circle(String token,Integer type,Integer babyId) throws ParseException {
		ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
		System.err.println("进入"+parentInfo);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo);
		Set<Long> time=null;
		if(null!=parentInfo){//验证用户
			
			if(0==type){//食谱红圈    这些天起码有一顿餐记录 ***
				time= parentDao.findRecipeCircleCircleByToken(babyId);
			}else if(1==type){//代接红圈    这些天起码有一条代接记录 ***
				time= parentDao.findDaijieCircleCircleByToken(babyId);
				time=MyUtil.getYMDLongs(time);
			}else if(2==type){//课程红圈   这些天起码有一节课记录 ***
				 time= parentDao.findLessonCircleByBabyId(babyId);
			}else if(3==type){//考勤,晨检红圈  这些天[没有]考勤,晨检  ***
				 time= parentDao.findAttendanceCircleByToken( babyId);
				 time=MyUtil.reverse(time);//反转
			}else if(4==type){//宝宝异常红圈  这些天至少一个异常 ***
				 time= parentDao.findYichangLongById( babyId );
			}else if(5==type){//宝宝请假红圈  ***
				 List<BabyLeaveLog> babyLeave= parentDao.findLeaveLongByToken(babyId);
				 Set<Long> leave=MyUtil.findBTimeLongByStartEnd(babyLeave)	;
				 time=MyUtil.getYMDLongs(leave);
			}
			
			MyUtil.putMapParams(result, "state", 1, "info", time);
		}
		return result;
	}

	
	public Map<String, Object> babyCheck(String token,Long time,Integer babyId) throws ParseException {
		time=MyUtil.getYMDLong(time);
		ParentInfo parentInfo= parentDao.findParentInfoByToken(token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null,"attendanceState",null);
		if(null!=parentInfo){//验证用户
			List<BabyCheckLogAll> babyCheckLogs= parentDao.findBabyCheckByToken(MyUtil.putMapParams("babyId", babyId,"time",time));//获取所有宝宝的晨检 考勤信息
			System.err.println(babyCheckLogs.size());
			
			//请求一次接口 访问次数加一
			long current = System.currentTimeMillis();
            long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
			Map<String, Object> params = MyUtil.putMapParams("gartenId",babyCheckLogs.get(0).getGartenId(),"type",1,"time",zero/1000);
            VisitCount visitCount = parentDao.findVisitCount(params);
			if(null==visitCount){
				parentDao.addVisitCount(params);
			}else{
				parentDao.updateVisitCount(params);
			}
			
			MyUtil.putMapParams(result, "state",1,"info",MyUtil.paixuBabyCheckLog(babyCheckLogs),"attendanceState",MyUtil.getParentAttendance(parentInfo,babyId)/*这个家长有没有这个宝宝的考勤权限*/);//排序 体温0的在前面 总的按id排序
		}
		return result;
	}
	
	public Map<String, Object> getHuanxin(String token) throws ParseException {
		ParentInfo parentInfo= parentDao.findParentInfoByToken(token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=parentInfo){//验证用户
			Map<String,Object> info=MyUtil.putMapParams("parent","parent"+parentInfo.getParentId(), "pwd", "1");
			MyUtil.putMapParams(result,"state", 1,"info",info);
		}
		return result;
	}


	public Map<String, Object> remarkCheck(String token, Integer checkId, String remark) {
		ParentInfo parentInfo= parentDao.findParentInfoByToken(token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0);
		if(null!=parentInfo){//验证用户
			parentDao.addRemarkCheck(MyUtil.putMapParams("checkId", checkId,"remark",remark));
			MyUtil.putMapParams(result,"state", 1);
		}
		return result;
	}


	public Map<String, Object> notifiedCenter(String token,String type,Integer pageNo) {
		ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo);
		if(null!=parentInfo){//验证用户
			List<InfoLog> notifieds= parentDao.findNotifiedByToken(MyUtil.putMapParams("token", token, "type", type));//有用户则找到所有通知并添加进model里返回对象 type返回系统通知,异常,重要通知
			Integer importantCount=parentDao.findImportantCountByToken(token);// 找到该用户的重要通知未读数
			MyUtil.putMapParams(result, "state", 1, "info",MyPage.listPage(notifieds, pageNo),"importantCount",importantCount); 
		}
		return result;
	}

	/*5：表示上午迟到6：表示上午早退7：表示下午迟到8：表示下午早退9：表示下午提前入园10：表示下午推迟离园
	 * 
	 */
	//stateLate 0正常 1迟到 2同意
		//stateEarly 0正常1早退 2同意
		public Map<String, Object> yichang(String token,Long time,Integer babyId) throws ParseException {
			time=MyUtil.getYMDLong(time);
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null,"attendanceState",null);
			if(null!=parentInfo){//验证用户
				Map<String,Object> param=MyUtil.putMapParams("babyId", babyId, "time", time);
				List<UnusualAll> yichangs= parentDao.findYichangByToken(param);
				MyUtil.putMapParams(result, "state",1,"info",yichangs,"attendanceState",MyUtil.getParentAttendance(parentInfo,babyId));
			}
			return result;
		}


		public Map<String, Object> daijie(String token,Integer babyId,Long time) throws ParseException {
			time=MyUtil.getYMDLong(time);
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo);
			if(null!=parentInfo){//验证用户
				List<Daijie> daijieInfo=parentDao.findDaijieById( babyId);//获取这个宝宝的代接
				System.err.println(daijieInfo.get(0));
				MyUtil.putMapParams(result, "state",1, "info", MyUtil.paixuDaijieLog(daijieInfo,2,time).get("info"));//获取其中是今天的代接
			}
			return result;
		}
		
		public Map<String, Object> deleteDaijie(String token,Integer daijieId) throws ParseException {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=parentInfo){//验证用户
				parentDao.deleteDaijieById( daijieId);//获取这个宝宝的代接
				MyUtil.putMapParams(result, "state",1);//获取其中是今天的代接
			}
			return result;
		}

		public Map<String, Object> createDaijie(HttpServletRequest request,HttpSession session,String token, String daijieName,String relation,String realPhoneNumber,Long arriveTime,Integer babyId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=parentInfo){//验证用户
				@SuppressWarnings("deprecation")
				String imgAddress=MyUtilAll.headImage(request, session, "parentcreateDaijie"+parentInfo.getParentId()+"="+new Date().getDate()/1000,"");
				BabyInfo baby=parentDao.findBabyShortByBabyId(babyId);//获取加长管理的所有孩子的信息
				Map<String,Object> param=MyUtil.putMapParams("token", token, "daijieName",daijieName, "relation",relation,"realPhoneNumber",realPhoneNumber,"daijieHead",imgAddress);
				MyUtil.putMapParams(param,"parentId",parentInfo.getParentId(),"phoneNumber",parentInfo.getPhoneNumber(),"arrivedTime",arriveTime,"babyId",babyId,"babyImg",baby.getBabyHead());
				parentDao.createDaijieById(param );//增加宝宝的代接
				
				MyUtil.putMapParams(result, "state",1);//获取其中是今天的代接
			}
			return result;
		}


		public Map<String, Object> lesson(String token, Integer babyId,Long time) throws ParseException {
			time=MyUtil.getYMDLong(time);
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo);
			if(null!=parentInfo){//验证用户
				List<GartenLesson> lesson=parentDao.findLessonById(MyUtil.putMapParams("babyId", babyId,"time",time) );//增加宝宝的代接
				
				MyUtil.putMapParams(result, "state",1,"info",lesson);//获取其中是今天的代接
			}
			return result;
		}
		
		public Map<String, Object> recipe(String token, Integer babyId,Long time) throws ParseException {
			time=MyUtil.getYMDLong(time);
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo);
			if(null!=parentInfo){//验证用户
				List<GartenRecipe> gartenRecipe=parentDao.findRecipeById(MyUtil.putMapParams("babyId", babyId,"time",time) );//增加宝宝的代接
				
				MyUtil.putMapParams(result, "state",1,"info",gartenRecipe);//获取其中是今天的代接
			}
			return result;
		}
		
		public Map<String, Object> performance(String token, Integer babyId,Long time) throws ParseException {
			time=MyUtil.getYMDLong(time);
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo);
			if(null!=parentInfo){//验证用户
				List<BabyPerformanceLogAll> performance=parentDao.findPerformanceById(MyUtil.putMapParams("babyId", babyId,"time",time) );
				MyUtil.putMapParams(result, "state",1,"info",performance);//获取其中是今天的代接
			}
			return result;
		}


		public Map<String, Object> introduceActivity(String token,Integer babyId) {
				ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
				Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo);
				if(null!=parentInfo){//验证用户
					GartenInfo garten=parentDao.findGartenInfoById(babyId);
					MyUtil.putMapParams(result,"state",1,"info", garten.getIntroduceHtml());
				}
			return result;
		}
		
		public Map<String, Object> activity(String token,Integer babyId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo);
			if(null!=parentInfo){//验证用户
				GartenInfo garten=parentDao.findGartenInfoById(babyId);
				System.err.println(babyId+"SSS"+garten);
				List<ActivityDetailAll> activituLog= workerDao.findIntroduceActivityByToken(garten.getGartenId());//这个幼儿园所有的活动
				 //这个幼儿园的活动
				activituLog=MyUtil.getActivityList(activituLog,babyId);//applyState;0能报名 1已报名   2报名结束
				MyUtil.putMapParams(result,"state",1,"info",activituLog);
			}
		return result;
	}


		public Map<String, Object> joinActivity(String token, Integer babyId, String parent, String phoneNumber,
				Integer activityId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=parentInfo){//验证用户
				 parentDao.insertActivity(MyUtil.putMapParams("babyId", babyId,"parent",parent,"phoneNumber",phoneNumber,"activityId",activityId));
				MyUtil.putMapParams(result,"state",1);
			}
		return result;
		}


		public Map<String, Object> linkManParent(String token,Integer babyId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo,"flower",parentInfo,"infoAndroid",null);
			if(null!=parentInfo){//验证用户
				ClassManage classmanege=findBaby(babyId);
				List<ParentInfoShort> parents=parentDao.findParentLinkMan(MyUtil.putMapParams("babyId", babyId, "parentId", parentInfo.getParentId()));//这个家长的所有宝宝
				List<WorkerInfoShort> teachers=parentDao.findTeacherLinkMan(classmanege.getClassId(),classmanege.getGartenId());//该幼儿园所有的职工和园长

				Map<String,Map> all=MyUtil.paixuParentByZiMu(parents,teachers,0,classmanege.getGartenId());
				MyUtil.putMapParams(result, "state",1, "info",all.get("result"),"infoAndroid",all.get("resultAndroid").get("result"),"flower",parentInfo.getFlower());
			
			
			}
			return  result;
		}
		
		public Map<String, Object> linkManWorker(String token,Integer babyId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo,"flower",parentInfo,"infoAndroid",null);
			if(null!=parentInfo){//验证用户
				
				ClassManage classmanege=findBaby(babyId);
				List<ParentInfoShort> parents=parentDao.findParentLinkMan(MyUtil.putMapParams("babyId", babyId, "parentId", parentInfo.getParentId()));//这个家长的所有宝宝
				List<WorkerInfoShort> teachers=parentDao.findTeacherLinkMan(classmanege.getClassId(),classmanege.getGartenId());//该幼儿园所有的职工和园长

				Map<String,Map> all=MyUtil.paixuParentByZiMu(parents,teachers,1,classmanege.getGartenId());
				MyUtil.putMapParams(result, "state",1, "info",all.get("result"),"infoAndroid",all.get("resultAndroid").get("result"),"flower",parentInfo.getFlower());//一个给安卓用一个给IOS用
		
			}
			return  result;
		}


		public Map<String, Object> flower(String token, Integer workerId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=parentInfo){//验证用户
				parentDao.toFlower(MyUtil.putMapParams("parentId", parentInfo.getParentId(),"workerId",workerId));
				parentDao.updateFlower(parentInfo.getParentId());
				parentDao.upFlower(workerId);
				MyUtil.putMapParams(result,"state", 1);
			}
			return  result;
		}


		public Map<String, Object> baby(String token, Integer babyId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo);
			if(null!=parentInfo){//验证用户
				ClassManage baby=parentDao.findBabyById( babyId);
				MyUtil.putMapParams(result,"state", 1,"info",baby);
			}
			return  result;
		}
		public  ClassManage findBaby(Integer babyId){
			ClassManage baby=parentDao.findBabyById( babyId);
			System.err.println(babyId);
			return baby;
		}
		


		public Map<String, Object> updateBaby(String token,BabyInfo baby) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=parentInfo){//验证用户
				parentDao.updateBaby( baby);
				MyUtil.putMapParams(result,"state", 1);
			}
			return  result;
		}


		public Map<String, Object> updateBabyHead(HttpServletRequest request, HttpSession session,String token,Integer babyId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo);
			if(null!=parentInfo){//验证用户
				String imgAddress=MyUtilAll.headImage(request, session, "parent=updateBabyHead=babyId"+babyId+"="+new Date().getTime()/1000/*拼接图片名*/,"");
				parentDao.updateBabyHead(MyUtil.putMapParams("babyId", babyId,"imgAddress",imgAddress) );//增加宝宝的代接
				MyUtil.putMapParams(result, "state",1,"info",imgAddress);//获取其中是今天的代接
			}
			return result;
		}

//宝宝 请假判断他是否请过假
		public Map<String, Object> babyLeave(String token, Integer babyId, Long time) throws ParseException {
			time=MyUtil.getYMDLong(time);
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=parentInfo){//验证用户
				 List<BabyLeaveLog> babyLeave= parentDao.findLeaveLongByToken(babyId);
				 BabyLeaveLog leave=MyUtil.findBTimeLongByStartEndOne(babyLeave,time)	;
				MyUtil.putMapParams(result, "state",1,"info",leave);
			}
			return result;
		}


		public Map<String, Object> tobabyLeave(String token, Integer babyId, Long start, Long end,String reason) throws ParseException {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=parentInfo){//验证用户
				 List<BabyLeaveLog> babyLeave= parentDao.findLeaveLongByToken(babyId);
				  Integer able=MyUtil.ableToLeave(babyLeave,start,end);//able 0:没有重复请假 1:开始时间冲突 2:结束时间冲突
				  MyUtil.putMapParams(result,"state", able+1);
				  if(0==able){
					  parentDao.insertBabyLeave( MyUtil.putMapParams("babyId", babyId,"leaveTime", start,"reason", reason,"endTime", end));
						 MyUtil.putMapParams(result,"state", 1);
				  }
			}
			return result;
		}


		public Map<String, Object> feedback(String token, String content,Integer babyId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=parentInfo){//验证用户
				ClassManage baby=parentDao.findBabyById(babyId);
				Map<String,Object> param=MyUtil.putMapParams( "content", content,"gartenId",baby.getBabyId(),"job","家长","name",parentInfo.getParentName());
				MyUtil.putMapParams(param,"jobId", parentInfo.getParentId(),"phoneNumber",parentInfo.getPhoneNumber());
				 parentDao.createFeadbackByToken(param);
				 result.put("state", 1);
			}
			return result;
		}


		public Map<String, Object> photo(String token, Integer babyId,Integer pageNo) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",parentInfo);
			if(null!=parentInfo){//验证用户
				ClassManage baby = parentDao.findBabyById(babyId);
				List<GartenPhotos> parentPhoto= parentDao.findParentPhotoByToken(babyId,baby.getGartenId(),baby.getClassId());//大改
				List<GartenPhotos> workerPhoto= parentDao.findWorkerPhotoByToken(babyId,baby.getGartenId(),baby.getClassId());
				parentPhoto.addAll(workerPhoto);
				
				long current = System.currentTimeMillis();
	            long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
				Map<String, Object> params = MyUtil.putMapParams("gartenId",baby.getGartenId(),"type",3,"time",zero/1000);
	            VisitCount visitCount = parentDao.findVisitCount(params);
				if(null==visitCount){
					parentDao.addVisitCount(params);
				}else{
					parentDao.updateVisitCount(params);
				}
				MyUtil.putMapParams(result, "state",1,"info",MyPage.listPage(MyUtil.getPhotoFinal(parentPhoto,token,1), pageNo));
			}
			return result;
		}

		//发表朋友圈传[除了图片S]
		public Map<String, Object> publish(String token, String content, Integer babyId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=parentInfo){//验证用户
				ClassManage baby=parentDao.findBabyById(babyId);
				Map<String,Object> param=MyUtil.putMapParams( "token", token, "content", content, "state", 1, "title", baby.getBabyName(),"parentName",parentInfo.getParentName(),"parentRelation",MyUtil.getBabyRelation( babyId,  parentInfo)/*获取宝宝A的爸爸 字符串*/);
				MyUtil.putMapParams(param, "gartenId", baby.getGartenId(), "job","家长" , "id", parentInfo.getParentId(),"babyId",babyId);
				parentDao.createPhoto(param);
				result.put("state", 1);
			}
			return result;
		}
		//发表朋友圈传[图片]
		public Map<String, Object> publishImg(HttpServletRequest request,HttpSession session,String token,Integer babyId,String content) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=parentInfo){//验证用户
				ClassManage baby=parentDao.findBabyById(babyId);
				String imgs=MyUtilAll.photoImage(request, session,"parent=publishPhoto=babyId"+babyId);
				Map<String,Object> param=MyUtil.putMapParams( "token", token, "content", content, "state", 1, "title", MyUtil.getBabyRelation( babyId,  parentInfo) /*baby.getBabyName()*/,"parentName",parentInfo.getParentName(),"parentRelation",MyUtil.getBabyRelation( babyId,  parentInfo)/*获取宝宝A的爸爸 字符串*/);
				MyUtil.putMapParams(param, "gartenId", baby.getGartenId(), "job","家长" , "id", parentInfo.getParentId(),"babyId",babyId,"imgs",imgs.equals("")?null:imgs);
				parentDao.createPhoto(param);
				result.put("state", 1);
			}
			return result;
		}


		public Map<String, Object> dianzan(String token, Integer infoId,Integer babyId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);

			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			if(null!=parentInfo){//验证用户
				String name=MyUtil.getBabyRelation(babyId,parentInfo);//获取宝宝+家长关系
				Map<String,Object> param=MyUtil.putMapParams("token", token, "infoId",infoId,"name",name,"job","家长","jobId",parentInfo.getParentId());
				PhotoDianZan dianzan=parentDao.findHasdianzan(param);
				if(null==dianzan){//改用户为点赞过 创建点赞
					System.err.println("点赞了");
					parentDao.createDianzan(param);
				}else{
					System.err.println("取消点赞了");
					parentDao.deleteDianzan(param);
				}
				List<PhotoDianZan> dianzans=workerDao.findDianZanByInfoId(infoId);
				MyUtil.putMapParams(result,"state", 1,"info",dianzans);
			}
			return result;
		}


		public Map<String, Object> comment(String token,Integer infoId,Integer replyCommentId,String replyName,String commentContent,Integer babyId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			System.err.println(token+"=="+parentInfo);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			if(null!=parentInfo){//验证用户
				ClassManage classmanege=findBaby(babyId);
				Map<String,Object> param=MyUtil.putMapParams( "infoId",infoId,"replyCommentId",replyCommentId,"replyName",replyName,"commentName",MyUtil.getBabyRelation( babyId,  parentInfo)  /*classmanege.getBabyName()*/,"commentContent",commentContent,"job","家长","jobId",parentInfo.getParentId());
				parentDao.createCommentPhoto(param);
				List<Comment> teacher=workerDao.findEvaluationByInfoId(infoId);//该发表的所有评论
				MyUtil.putMapParams(result,"state", 1,"info",teacher);
			}
			return result;
		}


		public int findIsDianZanByInfoId(String token, Integer infoId) {
			PhotoDianZan dianzan=parentDao.findHasdianzan(MyUtil.putMapParams("token", token, "infoId",infoId,"job","家长"));
			int a= null==dianzan?0:1;
			return  a;
		}


		public Map<String, Object> replyPerson(String token, Integer infoId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"name",parentInfo);
			if(null!=parentInfo){//验证用户
				Set<String>  name=parentDao.findPhoneNameByInfoId(infoId);
				MyUtil.putMapParams(result,"state", 1,"name",MyUtil.paixuReplyPerson(name,parentInfo.getParentName()));
			}
			return result;
		}


		public Map<String, Object> video(String token,Integer babyId) throws ParseException {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null,"monitor",null);
		if(null!=parentInfo){//验证用户
			ClassManage baby=findBaby(babyId);
			Integer monitor=MyUtil.findMonitor(babyId,parentInfo);
			List<Video> videos=parentDao.findVideos(MyUtil.putMapParams("babyId", babyId,"classId",baby.getClassId()));
			
			//请求一次接口 访问次数加一
			long current = System.currentTimeMillis();
            long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
			Map<String, Object> params = MyUtil.putMapParams("gartenId",baby.getGartenId(),"type",2,"time",zero/1000);
            VisitCount visitCount = parentDao.findVisitCount(params);
			if(null==visitCount){
				parentDao.addVisitCount(params);
			}else{
				parentDao.updateVisitCount(params);
			}
			
			MyUtil.putMapParams(result,"state",1,"info",videos,"monitor",monitor);
		}
		return result;
	}


		public Map<String, Object> version(String token) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);

		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=parentInfo){//验证用户
			Version vs=parentDao.findVersion( );
			MyUtil.putMapParams(result,"state",1,"info",vs);
		}
		return result;
	}

		/**
		 * 1遍历所有type	
		 *				
		 *				
		 *				
		 *				0家长视频
		 *				1家长考勤
		 *2有没有幼儿园主键的收费标准  没有跳3
		 *3有没有省市区的收费标准  没有跳4
		 *4有没有省市的收费标准  没有跳5
		 *5有没有省的收费标准   没有 联系管理员!
		 */
		public Map<String, Object> getPrice(String token,Integer babyId,Integer type,Integer month) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			
			if(null!=parentInfo){//验证用户
				BigDecimal big1=null;
				BigDecimal big2=null;
				BigDecimal big3=null;
				GartenInfo garten=parentDao.findGartenInfoById(babyId);
				List<GartenCharge> gartenCharges=new ArrayList<GartenCharge>();
				GartenCharge gartenCharge=new GartenCharge();
				//MyUtil.putMapParams(result,"state", 1,"info",gartenCharges);
				for(int i=0;i<2;i++){
					 gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams("province",garten.getProvince(),"city",garten.getCity(),"countries",garten.getCountries(),"gartenId", garten.getGartenId(), "type",i+ 4));//属于该幼儿园本身的收费
					if(null!=gartenCharge){
						gartenCharges.add(gartenCharge);
						if(0==i){
							big1=MyUtil.getRealPrice(gartenCharge,month);
						}else if(1==i){
							big2=MyUtil.getRealPrice(gartenCharge,month);
						}
					}else{
						gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams("province",garten.getProvince(),"city",garten.getCity(),"countries",garten.getCountries(),"gartenId", 0, "type",i+ 4));//属于该幼儿园所在区的收费
						 	if(null!=gartenCharge){
						 		gartenCharges.add(gartenCharge);
						 		if(0==i){
									big1=MyUtil.getRealPrice(gartenCharge,month);
								}else if(1==i){
									big2=MyUtil.getRealPrice(gartenCharge,month);
								}
						 	}else{
						 		gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams("province",garten.getProvince(),"city",garten.getCity(),"countries","","gartenId", 0, "type",i+ 4));//属于该幼儿园所在市的收费
								 if(null!=gartenCharge){
								 		gartenCharges.add(gartenCharge);
								 		if(0==i){
											big1=MyUtil.getRealPrice(gartenCharge,month);
										}else if(1==i){
											big2=MyUtil.getRealPrice(gartenCharge,month);
										}
								 	}else{
								 		gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams("province",garten.getProvince(),"city","","countries","","gartenId", 0, "type",i+ 4));//属于该幼儿园所在省的收费
										 if(null!=gartenCharge){
										 		gartenCharges.add(gartenCharge);
										 		if(0==i){
													big1=MyUtil.getRealPrice(gartenCharge,month);
												}else if(1==i){
													big2=MyUtil.getRealPrice(gartenCharge,month);
												}
										 	}else{//按照全国的标准
												//没找到发送反馈	parentDao.insertFeedback(new Feedback(garten.getGartenId(),"家长",new Date().getTime()/1000,parentInfo.getParentId(),garten.getProvince()+"没有"+(0==i?"视频":"考勤")+"收费标准",null,parentInfo.getParentName(),parentInfo.getPhoneNumber()));
										 		gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams( "province","","city","","countries","","gartenId", 0, "type",i+ 4));
										 		if(null==gartenCharge){
										 			return MyUtil.putMapParams("state", 3);			//没有该幼儿园的收费标准
										 		}
										 		gartenCharges.add(gartenCharge);
										 		System.err.println("测试"+gartenCharge);
										 		if(0==i){
													big1=MyUtil.getRealPrice(gartenCharge,month);
												}else if(1==i){
													big2=MyUtil.getRealPrice(gartenCharge,month);
												}
										 	}
								 	}
						 	}
					}
				}
				big3=big1.add(big2);
				MyUtil.putMapParams(result,"state", 1, "info", 0==type?big1:(1==type?big2:big3));
			}
			
			return result;
		}
		
		//支付宝支付 
		public Map<String, Object> alipay(String token,Integer type,Integer monthCount,Integer babyId) {
			
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			if(null!=parentInfo){//验证用户
				//创建未支付订单
				ClassManage baby=findBaby(babyId);
				Map<String, Object> getPrice= getPrice( token, babyId, type, monthCount) ;
				BigDecimal big=(BigDecimal) getPrice.get("info");
				Long orderNumber=System.currentTimeMillis();
				String orderDetail=0==type?"购买视频":(1==type?"购买考勤":"购买视频和考勤");
				Order o=new Order(orderNumber,new Date().getTime()/1000,null,"家长",big,orderDetail,parentInfo.getParentId(),type+4,0,0,monthCount,babyId,baby.getGartenId());
				parentDao.insertOrdr(o);//创建未支付订单
				System.err.println("成功创建==支付宝");
				System.err.println("成功创建==支付宝");
				System.err.println("成功创建==支付宝");
				Map<String,Object> payResult=MyUtilAll.myAlipay(orderNumber+"", orderDetail,big.toString() );
				MyUtil.putMapParams(result,"state", 1,"info",payResult.get("result"));
			}
			return result;
		}
		
		
		public String alipayyz() throws AlipayApiException, UnsupportedEncodingException, ParseException, APIConnectionException, APIRequestException {
		//获取支付宝POST过来反馈信息
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		System.err.println("进入回调");
		System.err.println("进入回调");
		System.err.println("进入回调");
		String orderNumber="";
		String type="";
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    String valueStr = "";
		    for (int i = 0; i < values.length; i++) {
		        valueStr = (i == values.length - 1) ? valueStr + values[i]
		                    : valueStr + values[i] + ",";
		       if(name.equals("out_trade_no")){
		    	   orderNumber=values[i];
		       }else if(name.equals("subject")){
		    	   type=values[i];
		       }
		    }
		  //乱码解决，这段代码在出现乱码时使用。    	valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
		    params.put(name, valueStr);	
		}
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean flag = AlipaySignature.rsaCheckV1(params, MyParamAll.MYALIPAY_ALIPAYPUBLICKEY, "utf-8", "RSA2")	;
		System.err.println("flagflag"+flag);
		System.err.println("flagflag"+flag);
		System.err.println("flagflag"+flag);
		Map<String,Object> param=new HashMap<String,Object>(); 
		param.put("orderNumber", orderNumber);
		if(flag==true){
					parentDao.zforder(param);
					System.err.println("回调成功");
					System.err.println("回调成功");
					System.err.println("回调成功");
					System.err.println("回调成功");
					System.err.println("回调成功");
					System.err.println("回调成功");
					System.err.println("回调成功");
					System.err.println("回调成功");
					System.err.println("回调成功");
					System.err.println("回调成功");
					System.err.println("回调成功");
					System.err.println("回调成功");
					System.err.println("回调成功");
					System.err.println("回调成功");
					
					//处理到期时间
					Order order=parentDao.findOrder(orderNumber);
					ParentInfo parentInfo=parentDao.findParentById(order.getId());
					String[] babyIds=parentInfo.getBabyId();
					SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
					Date day=new Date();
					Date date=new Date();
					for(int i=0;i<babyIds.length;i++){
						if(babyIds[i].equals(order.getRelateId()+"")){
							String[] attendanceTime=parentInfo.getAttendanceTime();
							String[] monitorTime=parentInfo.getMonitorTime();
							  Calendar calendar = Calendar.getInstance();  
							  
							
							Integer  orderType=order.getType();
							if(4==orderType||6==orderType){
								System.err.println(monitorTime[i]);
								if(sim.parse(monitorTime[i]).getTime()>day.getTime()){
									  calendar.setTime(sim.parse(monitorTime[i])); 
									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
									  date = calendar.getTime();  
									monitorTime[i]=sim.format(date);
									System.err.println(monitorTime[i]);
								}else{
									System.err.println(monitorTime[i]);
									calendar.setTime(day); 
									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
									  date = calendar.getTime();  
									monitorTime[i]=sim.format(date);	
									System.err.println(monitorTime[i]);
								}
							}
							if(5==orderType||6==orderType){
								if(sim.parse(attendanceTime[i]).getTime()>day.getTime()){
									System.err.println(attendanceTime[i]);
									  calendar.setTime(sim.parse(attendanceTime[i])); 
									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
									  date = calendar.getTime();  
									  attendanceTime[i]=sim.format(date);	
									  System.err.println(attendanceTime[i]);
								}else{
									System.err.println(attendanceTime[i]);
									calendar.setTime(day); 
									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
									  date = calendar.getTime();  
									  attendanceTime[i]=sim.format(date);
									  System.err.println(attendanceTime[i]);
								}
							}
							String attendanceTimeStr=MyUtil.changeArrayToString(attendanceTime);
							String monitorTimeStr=MyUtil.changeArrayToString(monitorTime);
							System.err.println("attendanceTimeStr"+attendanceTimeStr);
							System.err.println("monitorTimeStr"+monitorTimeStr);
							System.err.println("parentId"+parentInfo.getParentId());
							parentDao.updateChargeTime(MyUtil.putMapParams("monitorTime", monitorTimeStr, "attendanceTime", attendanceTimeStr,"parentId",parentInfo.getParentId()));
						}
						}
					
		}
		
		return "success";
	
	}


		


		public Map<String, Object> myWxinpay(String token,Integer type,Integer monthCount,Integer babyId) {
			ParentInfo parentInfo= parentDao.findParentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			if(null!=parentInfo){//验证用户
				//创建未支付订单
				ClassManage baby=findBaby(babyId);
				Map<String, Object> getPrice= getPrice( token, babyId, type, monthCount) ;
				BigDecimal big=(BigDecimal) getPrice.get("info");
				Long orderNumber=System.currentTimeMillis();
				String orderDetail=0==type?"购买视频":(1==type?"购买考勤":"购买视频和考勤");
				Order o=new Order(orderNumber,new Date().getTime()/1000,null,"家长",big,orderDetail,parentInfo.getParentId(),type+4,1,0,monthCount,babyId,baby.getGartenId());
				parentDao.insertOrdr(o);//创建未支付订单
				System.err.println("成功创建==微信");
				System.err.println("成功创建==微信");
				System.err.println("成功创建==微信");
				System.err.println("成功创建==微信");
				big = big.multiply(new BigDecimal(100));
				Map<String,Object> payResult=MyUtilAll.myWxinpay(orderNumber+"", orderDetail,big.toString());
				MyUtil.putMapParams(result,"state", 1,"info",payResult.get("msg"));
			}
			return result;
		}


		public void wxpayyz(HttpServletRequest request,HttpServletResponse response) throws IOException, JDOMException, ParseException, APIConnectionException, APIRequestException {
			  //读取参数  
	        InputStream inputStream ;  
	        StringBuffer sb = new StringBuffer();  
	        inputStream = request.getInputStream();  
	        String s ;  
	        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
	        while ((s = in.readLine()) != null){  
	            sb.append(s);  
	        }  
	        in.close();  
	        inputStream.close();  
	        //解析xml成map  
	        Map<String, String> m = new HashMap<String, String>();  
	        m = XMLUtil.doXMLParse(sb.toString());  
	        for(Object keyValue : m.keySet()){
	            System.out.println(keyValue+"="+m.get(keyValue));
	        }
	        //过滤空 设置 TreeMap  
	        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();        
			Iterator it = m.keySet().iterator();  
	        while (it.hasNext()) {  
	            String parameter = (String) it.next();  
	            String parameterValue = m.get(parameter);  

	            String v = "";  
	            if(null != parameterValue) {  
	                v = parameterValue.trim();  
	            }  
	            packageParams.put(parameter, v);  
	        }  

	        //判断签名是否正确  
	        String resXml = "";  
	        if(PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
	             if("SUCCESS".equals((String)packageParams.get("result_code"))){ 
	                 // 这里是支付成功  
	                 //////////执行自己的业务逻辑////////////////  
	                 String mch_id = (String)packageParams.get("mch_id"); //商户号 
	                 String openid = (String)packageParams.get("openid");  //用户标识
	                 String out_trade_no = (String)packageParams.get("out_trade_no"); //商户订单号
	                 String payType="微信"+out_trade_no;
	                 //out_trade_no=out_trade_no.substring(0, 14);
	                 String total_fee = (String)packageParams.get("total_fee");  
	                 String transaction_id = (String)packageParams.get("transaction_id"); //微信支付订单号
	                 String noncestr = (String)packageParams.get("nonce_str"); //微信支付订单号
	                 System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	                 //查询订单 
	                 Map<String,Object> param=new HashMap<String,Object>(); 
	                 param.put("orderNumber", out_trade_no);
		             Order order=parentDao.findOrder(out_trade_no);
		             BigDecimal   price=order.getOrderMoney().multiply(new BigDecimal(100));
		             
	                 if(!MyParamAll.MYWXIN_MCHID.equals(mch_id)||new BigDecimal(total_fee).compareTo(price) != 0){
	                 System.err.println("支付失败,错误信息：" + "参数错误"+new BigDecimal(total_fee)+"=="+price);
	                 resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
	                                 + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";  
	                 }else{
	                	 
	                	 /**
	                	  *  //开始处理
	                	  */
	                    	 resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
	                             + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
	                          System.err.println("订单已处理");
	                       

	      					parentDao.zforder(param);
	      					System.err.println("回调成功");
	      					System.err.println("回调成功");
	      					System.err.println("回调成功");
	      					//处理到期时间
	      					ParentInfo parentInfo=parentDao.findParentById(order.getId());
	      					String[] babyIds=parentInfo.getBabyId();
	      					SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
	      					Date day=new Date();
	      					Date date=new Date();
	      					for(int i=0;i<babyIds.length;i++){
	      						if(babyIds[i].equals(order.getRelateId()+"")){
	      							String[] attendanceTime=parentInfo.getAttendanceTime();
	      							String[] monitorTime=parentInfo.getMonitorTime();
	      							  Calendar calendar = Calendar.getInstance();  
	      							  
	      							
	      							Integer  orderType=order.getType();
	      							if(4==orderType||6==orderType){
	      								System.err.println(monitorTime[i]);
	      								if(sim.parse(monitorTime[i]).getTime()>day.getTime()){
	      									  calendar.setTime(sim.parse(monitorTime[i])); 
	      									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
	      									  date = calendar.getTime();  
	      									monitorTime[i]=sim.format(date);
	      									System.err.println(monitorTime[i]);
	      								}else{
	      									System.err.println(monitorTime[i]);
	      									calendar.setTime(day); 
	      									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
	      									  date = calendar.getTime();  
	      									monitorTime[i]=sim.format(date);	
	      									System.err.println(monitorTime[i]);
	      								}
	      							}
	      							if(5==orderType||6==orderType){
	      								if(sim.parse(attendanceTime[i]).getTime()>day.getTime()){
	      									System.err.println(attendanceTime[i]);
	      									  calendar.setTime(sim.parse(attendanceTime[i])); 
	      									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
	      									  date = calendar.getTime();  
	      									  attendanceTime[i]=sim.format(date);	
	      									  System.err.println(attendanceTime[i]);
	      								}else{
	      									System.err.println(attendanceTime[i]);
	      									calendar.setTime(day); 
	      									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
	      									  date = calendar.getTime();  
	      									  attendanceTime[i]=sim.format(date);
	      									  System.err.println(attendanceTime[i]);
	      								}
	      							}
	      							String attendanceTimeStr=MyUtil.changeArrayToString(attendanceTime);
	      							String monitorTimeStr=MyUtil.changeArrayToString(monitorTime);
	      							System.err.println("attendanceTimeStr"+attendanceTimeStr);
	      							System.err.println("monitorTimeStr"+monitorTimeStr);
	      							System.err.println("parentId"+parentInfo.getParentId());
	      							parentDao.updateChargeTime(MyUtil.putMapParams("monitorTime", monitorTimeStr, "attendanceTime", attendanceTimeStr,"parentId",parentInfo.getParentId()));
	      						}
	      						}
	      					
	      		
	      		
	                     
	                 }
	        }else {  
	                 System.err.println("支付失败,错误信息：" + packageParams.get("err_code"));
	                 resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
	                         + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";  
	             }  
	        } else{  
	            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
	                    + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> "; 
	            System.err.println("通知签名验证失败");
	        }   
	      //------------------------------  
	        //处理业务完毕  
	        //------------------------------  
	        BufferedOutputStream out = new BufferedOutputStream(  
	                response.getOutputStream());  
	        out.write(resXml.getBytes());  
	        out.flush();  
	        out.close();  
	    }


		public void test() throws ParseException {
			Order order=parentDao.findOrder("1502704105223");
			ParentInfo parentInfo=parentDao.findParentById(order.getId());
			String[] babyIds=parentInfo.getBabyId();
			SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
			Date day=new Date();
			Date date=new Date();
			for(int i=0;i<babyIds.length;i++){
				if(babyIds[i].equals(order.getRelateId()+"")){
					String[] attendanceTime=parentInfo.getAttendanceTime();
					String[] monitorTime=parentInfo.getMonitorTime();
					  Calendar calendar = Calendar.getInstance();  
					Integer  orderType=order.getType();
					if(4==orderType||6==orderType){
						System.err.println(monitorTime[i]);
						if(sim.parse(monitorTime[i]).getTime()>day.getTime()){
							  calendar.setTime(sim.parse(monitorTime[i])); 
							  System.err.println(sim.format(calendar.getTime()));

							  calendar.add(Calendar.MONTH, order.getMonthCount()); 
							  date = calendar.getTime();  
							monitorTime[i]=sim.format(date);
							System.err.println(monitorTime[i]);
						}else{
							System.err.println(monitorTime[i]);
							calendar.setTime(day); 
							  calendar.add(Calendar.MONTH, order.getMonthCount()); 
							  date = calendar.getTime();  
							monitorTime[i]=sim.format(date);	
							System.err.println(monitorTime[i]);
						}
					}
					if(5==orderType||6==orderType){
						if(sim.parse(attendanceTime[i]).getTime()>day.getTime()){
							System.err.println("1"+order.getMonthCount()+"进入"+attendanceTime[i]);
							  calendar.setTime(sim.parse(attendanceTime[i])); 
							  System.err.println("2"+sim.format(calendar.getTime()));
							  calendar.add(Calendar.MONTH, order.getMonthCount()); 
							  date = calendar.getTime();  
							  attendanceTime[i]=sim.format(date);	
							  System.err.println("3"+attendanceTime[i]);
						}else{
							System.err.println(attendanceTime[i]);
							calendar.setTime(day); 
							  calendar.add(Calendar.MONTH, order.getMonthCount()); 
							  date = calendar.getTime();  
							  attendanceTime[i]=sim.format(date);
							  System.err.println(attendanceTime[i]);
						}
					}
					String attendanceTimeStr=MyUtil.changeArrayToString(attendanceTime);
					String monitorTimeStr=MyUtil.changeArrayToString(monitorTime);
					System.err.println("attendanceTimeStr"+attendanceTimeStr);
					System.err.println("monitorTimeStr"+monitorTimeStr);
					System.err.println("parentId"+parentInfo.getParentId());
					parentDao.updateChargeTime(MyUtil.putMapParams("monitorTime", monitorTimeStr, "attendanceTime", attendanceTimeStr,"parentId",parentInfo.getParentId()));
				}
				}
			}
		public ParentInfo findParentById(Integer parentId) {
			ParentInfo parentInfo= parentDao.findParentById(parentId);
			return parentInfo;
		}
		
		
		//宝宝生日定时任务
		public Map<String, Object> babyBirthday() throws APIConnectionException, APIRequestException {
			List<BabyInfo> babys=parentDao.findBaby();//找到所有明天生日的宝宝
			if(null!=babys){
				for(BabyInfo b:babys){
					System.err.println(b.getTeacherId());
					List<WorkerInfo> workers=parentDao.getWorker(b.getClassId());//找到每个宝宝的teacherId的所有老师
					System.err.println(workers.get(1));
					if(null!=workers){
						for(WorkerInfo w:workers){
							MyUtil.pushOne(MyParamAll.JIGUANG_WORKER_APP, MyParamAll.JIGUANG_WORKER_MASTER,"宝宝"+b.getBabyName()+"将在明天生日,快去为他准备惊喜吧!", w.getPhoneNumber());
						}
					}
				}
			}
			
			Map<String,Object> result=MyUtil.putMapParams("state",1);
			return result;

		}
	

		public void updateFlower(){
			
			parentDao.updateHonghua();
		}
}
