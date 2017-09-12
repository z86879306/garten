package com.garten.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.garten.dao.ParentDao;
import com.garten.dao.PrincipalDao;
import com.garten.dao.WorkerDao;
import com.garten.model.activity.ActivityDetail;
import com.garten.model.activity.ActivityLog;
import com.garten.model.baby.BabyInfo;
import com.garten.model.baby.BabyLeaveLog;
import com.garten.model.baby.BabyPerformanceLog;
import com.garten.model.garten.GartenClass;
import com.garten.model.garten.GartenInfo;
import com.garten.model.garten.GartenLesson;
import com.garten.model.garten.GartenPhotos;
import com.garten.model.garten.GartenRecipe;
import com.garten.model.garten.PhotoDianZan;
import com.garten.model.garten.Video;
import com.garten.model.other.Comment;
import com.garten.model.other.InfoLog;
import com.garten.model.other.Version;
import com.garten.model.parent.ParentInfo;
import com.garten.model.worker.WorkerCheckLog;
import com.garten.model.worker.WorkerInfo;
import com.garten.model.worker.WorkerLeaveLog;
import com.garten.util.lxcutil.MyUtilAll;
import com.garten.util.md5.CryptographyUtil;
import com.garten.util.myutil.MyUtil;
import com.garten.util.page.MyPage;
import com.garten.vo.baby.UnusualAll;
import com.garten.vo.parent.ParentInfoShort;
import com.garten.vo.smallcontrol.GartenClassName;
import com.garten.vo.teacher.ActivityDetailAll;
import com.garten.vo.teacher.BabyCheckLogAll;
import com.garten.vo.teacher.BabyInfoShort;
import com.garten.vo.teacher.ClassManage;
import com.garten.vo.teacher.Daijie;
import com.garten.vo.teacher.Shouye;
import com.garten.vo.teacher.WorkerCheckLogAll;
import com.garten.vo.teacher.WorkerInfoShort;



@Service
public class PrincipalService {
	@Autowired
	private PrincipalDao principalDao;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private ParentDao parentDao;
	@Autowired
	private WorkerDao workerDao;
	public Map<String, Object> login(String phoneNumber, String pwd) {
		Map<String,Object> param=MyUtil.putMapParams("phoneNumber", phoneNumber,"pwd",CryptographyUtil.md5(pwd, "lxc"));
		WorkerInfo worker=principalDao.findPrincipalByPwd(param);
		//String uuid="error";
		Map<String,Object> result=MyUtil.putMapParams("state", 0, "info", "error");
		//如果worker为空则返回error
		//如果worker不为空则返回uuid,并修改token为uuid
		if(null!=worker&&!"".equals(worker)){
			//uuid=UUID.randomUUID().toString();
			//param.put("token", uuid);
			//principalDao.updateToken(param);//调用老师Dao的方法验证更新token
			MyUtil.putMapParams(result,"state", 1, "info", worker.getToken());//info: token或error  user:首页信息
		}
		return result;
	}
	
	

	
	
	
	public Map<String, Object> index(String token) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0, "info",workerInfo);
		if(null!=workerInfo&&!"".equals(workerInfo)){
			Shouye shouye=workerDao.findWorkerByToken(token);//根据新的token 查找用户构建Shouye对象
			shouye.setCount(workerDao.findInfoCount(token));//设置通知数
			MyUtil.putMapParams(result,"state", 1,"info",shouye);//info: token或error  user:首页信息
		}
		return result;
	}
	
	public Map<String, Object> updateLogin(String phoneNumber, String pwd, String number) throws ParseException {
		 WorkerInfo workerInfo=principalDao.findPrincipalByAccount(phoneNumber);//根据账号查找到用户,手机号
		  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info","没有这个用户");
			if(null!=workerInfo){
				Map<String,Object> duanxin=workerService.duanxin(phoneNumber,2,number);//0代表 老师端短信  1:家长端 2:院长端
				String newToken= UUID.randomUUID().toString();
				MyUtil.putMapParams(result,"state", 2, "info", "验证码错误");
				if(duanxin.get("0").equals("成功")){
					principalDao.updatePwdToken(MyUtil.putMapParams("token", newToken, "phoneNumber", phoneNumber,"pwd",CryptographyUtil.md5(pwd, "lxc")));
					MyUtil.putMapParams(result,"state", 1, "info", newToken);
				}
			}
			//验证码错误返回  验证错误信息
			return result;
	}
	//这个幼儿园所有老师的
	public Map<String, Object> workerCheck(String token, Long time ) throws ParseException {
		time=MyUtil.getYMDLong(time);
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo);
		if(null!=workerInfo){//验证用户
			List<WorkerCheckLogAll> workerCheckLogs= principalDao.findWorkerCheckByToken(MyUtil.putMapParams("token", token,"time",time));//获取所有宝宝的晨检 考勤信息
			System.out.println(workerCheckLogs.get(0));
			MyUtil.putMapParams(result, "state",1,"info",workerCheckLogs);//排序
		}
		return result;
	}
	public Map<String, Object> yichang(String token, Long time) throws ParseException {
		time=MyUtil.getYMDLong(time);
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//验证用户
			Map<String,Object> param=MyUtil.putMapParams("token", token, "time", time);
			List<UnusualAll> yichangs= principalDao.findUnusualAllByToken(param);
			MyUtil.putMapParams(result, "state",1, "info", yichangs);
		}
		return result;
	}
	public Map<String, Object> leaveLog(String token, Long time) throws ParseException {
		time=MyUtil.getYMDLong(time);
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//验证用户
			MyUtil.putMapParams(result, "state", 1,"info",MyUtil.paixuWorkerLeaveLog(principalDao.findLeaveLogByToken(MyUtil.putMapParams("token", token, "time", time))));
		}
		return result;
	}
	public Map<String, Object> circle(String token, Integer type) throws ParseException {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo);
		Set<Long> time=null;
		if(null!=workerInfo){//验证用户
			if(0==type){//老师考勤,晨检红圈  这些天考勤,晨检
				 time= workerDao.findAttendanceCircleByToken(token);
				 time=MyUtil.reverse(time);//反转
			}else if(1==type){//老师异常红圈  这些天至少一个异常
				 time= principalDao.findYichangCircleByToken(token);
/*				 List<WorkerCheckLogAll> workerCheckLogs= principalDao.findWorkerCheckAllByToken(token)查询有签到签退的晨检;
*/				 /*time=MyUtil.getYichangLongsWorker(workerCheckLogs,workerDao.findGartenArriveLeaveById(token)获取该幼儿园的规定签到签退时间);*/
			}else if(2==type){//老师请假红圈  
				 List<WorkerLeaveLog> workerLeave= principalDao.findLeaveLongByToken( token);
				 Set<Long> leave=MyUtil.findBTimeLongByStartEndWorker(workerLeave)	;
				 time=MyUtil.getYMDLongs(leave);
			}
			
			MyUtil.putMapParams(result, "state", 1, "info", time);
		}
		return result;
	}
	public Map<String, Object> agreeLeaveLog(Integer wleaveId, Integer type) {
		if(0==type){
			principalDao.updateAgreeLeaveByCheckId(wleaveId);
		}else if(1==type){
			principalDao.deleteWorkerLeaveByleaveId(wleaveId);
		}
		Map<String,Object> result=MyUtil.putMapParams("state", 1);
		return result;
	}
	
	
	public Map<String, Object> yichangAgree(Integer unusualId) {
		workerDao.resolveUnusual( unusualId);
		Map<String,Object> result=MyUtil.putMapParams("state", 1);
		return result;
	}
	
	public Map<String, Object> classCheck(String token, Long time,Integer classId ) throws ParseException {
		time=MyUtil.getYMDLong(time);
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//验证用户
			List<BabyCheckLogAll> babyCheckLogs= principalDao.findBabyCheckByClass(MyUtil.putMapParams("classId", classId,"time",time));//获取所有宝宝的晨检 考勤信息
			MyUtil.putMapParams(result, "state",1,"info",MyUtil.paixuBabyCheckLog(babyCheckLogs));//排序 体温0的在前面 总的按id排序
		}
		return result;
	}
	
	public Map<String, Object> introduceActivity(String token) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			if(null!=workerInfo){//验证用户
				GartenInfo garten=workerDao.findGartenInfoById(workerInfo.getGartenId());
				MyUtil.putMapParams(result,"state",1,"info", garten.getIntroduceHtml());
			}
			return result;
		}
	
	public Map<String, Object> activity(String token) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//验证用户
			List<ActivityDetailAll> activituLog= workerDao.findIntroduceActivityByToken(workerInfo.getGartenId());//这个幼儿园所有的活动
			 //这个幼儿园的活动
			MyUtil.putMapParams(result,"state",1,"info", activituLog);
		}
		return result;
	}
	
	public Map<String, Object> photo(String token,Integer pageNo) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo,"workerId",null);
		if(null!=workerInfo){//验证用户
			List<GartenPhotos> parentPhoto= principalDao.findParentPhotoByToken( token);//这个幼儿园所有班级的家长
			List<GartenPhotos> workerPhoto= principalDao.findWorkerPhotoByToken( token);//这个幼儿园所有老师和园长
			parentPhoto.addAll(workerPhoto);
			MyUtil.putMapParams(result, "state",1,"info",MyPage.listPage(MyUtil.getPhotoFinal(parentPhoto,token,0), pageNo),"workerId",workerInfo.getWorkerId());
		}
		return result;
	}
	
	
	public Map<String, Object> publishImg(HttpServletRequest request,HttpSession session,String token,String content) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		System.err.println(token+"==="+workerInfo);
		Map<String,Object> result=MyUtil.putMapParams("state", 0);
		if(null!=workerInfo){//验证用户
			String imgs=MyUtilAll.photoImage(request, session,"principal=publishPhotoImg=workerId"+workerInfo.getWorkerId());
			Map<String,Object> param=MyUtil.putMapParams( "token", token, "imgs", imgs.equals("")?null:imgs, "state", 1, "title", workerInfo.getWorkerName(),"content",content);
			MyUtil.putMapParams(param, "gartenId", workerInfo.getGartenId(), "job",workerInfo.getJob() , "id", workerInfo.getWorkerId());
			workerDao.createPhoto(param);
			result.put("state", 1);
		}
		return result;
	}
	
	public Map<String, Object> dianzan(String token,Integer infoId) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
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
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//验证用户
			Map<String,Object> param=MyUtil.putMapParams( "infoId",infoId,"replyCommentId",replyCommentId,"replyName",replyName,"commentName",workerInfo.getWorkerName(),"commentContent",commentContent, "jobId", workerInfo.getWorkerId(),"job",workerInfo.getJob());
			workerDao.createCommentPhoto(param);
				List<Comment> comment=workerDao.findEvaluationByInfoId(infoId);//该发表的所有评论
				MyUtil.putMapParams(result,"state", 1,"info",comment);
		}
		return  result;
	}
	
	public Map<String, Object> replyPerson(String token, Integer infoId) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"name",workerInfo);
		if(null!=workerInfo){//验证用户
			Set<String>  name=parentDao.findPhoneNameByInfoId(infoId);
			MyUtil.putMapParams(result,"state", 1,"name",MyUtil.paixuReplyPerson(name,workerInfo.getWorkerName()));
		}
		return result;
	}
	public Map<String, Object> getClassAllName(String token) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo);
		if(null!=workerInfo){//验证用户
			List<GartenClassName>  classes=parentDao.findClassesName(MyUtil.putMapParams("token", token));
			MyUtil.putMapParams(result,"state", 1,"info",MyUtil.paixuClass(classes));
		}
		return result;
	}
	
	public Map<String, Object> linkManParent(String token ,Integer classId) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null,"infoAndroid",null);
		if(null!=workerInfo){//验证用户
			List<ParentInfoShort> parents=principalDao.findParentLinkMan(classId);
			List<WorkerInfoShort> teachers=workerDao.findTeacherLinkMan(token);//该幼儿园所有的职工和园长
			Map<String,Map> all=MyUtil.paixuParentByZiMu(parents,teachers,0,workerInfo.getGartenId());
			MyUtil.putMapParams(result, "state",1, "info",all.get("result"),"infoAndroid",all.get("resultAndroid").get("result"));
		}
		return  result;
	}

	public Map<String, Object> linkManWorker(String token  ) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null,"infoAndroid",null);
		if(null!=workerInfo){//验证用户
			List<WorkerInfoShort> teachers=workerDao.findTeacherLinkMan(token);//该幼儿园所有的职工和园长
			Map<String,Map> all=MyUtil.paixuParentByZiMu(null,teachers,1,workerInfo.getGartenId());
			MyUtil.putMapParams(result, "state",1, "info",all.get("result"),"infoAndroid",all.get("resultAndroid").get("result"));
		}
		return  result;
	}
	public Map<String, Object> teacher(String token) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//验证用户
			WorkerInfoShort teacher=workerDao.findTeacherByToken(token);//该幼儿园所有的职工和园长
			MyUtil.putMapParams(result, "state",1,"info",teacher);
		}
		return  result;
	}
	
	
	public Map<String, Object> updateTeacher(String token,String workerName,Integer sex,Integer age) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=workerInfo){//验证用户
				workerDao.updateTeacherMessage(MyUtil.putMapParams("token",token,"workerName",workerName,"sex",sex,"age",age));
				MyUtil.putMapParams(result, "state",1);
			}
			return  result;
		}
	
	public Map<String, Object> updateTeacherHead(HttpServletRequest request,HttpSession session,String token ) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			if(null!=workerInfo){//验证用户
				String imgAddress=MyUtilAll.headImage(request, session, "principal=updateTeacherHead=workerId"+workerInfo.getWorkerId()+"="+new Date().getTime()/1000/*拼接图片名*/,"");
				principalDao.updateTeacherHead(MyUtil.putMapParams("workerId", workerInfo.getWorkerId(),"imgAddress",imgAddress) );//增加宝宝的代接
				MyUtil.putMapParams(result, "state",1,"info",imgAddress);//获取其中是今天的代接
			}
			return  result;
		}
	
	public Map<String, Object> feedback(String token, String content) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0);
		if(null!=workerInfo){//验证用户
			Map<String,Object> param=MyUtil.putMapParams("token", token, "content", content,"gartenId",workerInfo.getGartenId(),"job",workerInfo.getJob(),"name",workerInfo.getWorkerName());
			MyUtil.putMapParams(param,"jobId", workerInfo.getWorkerId(),"phoneNumber", workerInfo.getPhoneNumber());
			 workerDao.createFeadbackByToken(param);
			 result.put("state", 1);
		}
		return result;
	}
	
	public Map<String, Object> notifiedCenter(String token,String type,Integer pageNo) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",workerInfo);
		if(null!=workerInfo){
			List<InfoLog> notifieds= workerDao.findNotifiedByToken(MyUtil.putMapParams("token", token, "type", type));//有用户则找到所有通知并添加进model里返回对象
			Integer importantCount=workerDao.findImportantCountByToken(token);// 找到该用户的重要通知未读数
			MyUtil.putMapParams(result, "state", 1, "info",MyPage.listPage(notifieds, pageNo),"importantCount",importantCount); 
		}
		return result;
	}


	public Map<String, Object> video(String token,Integer classId) {
		System.err.println(classId);
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//验证用户
			List<Video> videos=principalDao.findVideosByToken(MyUtil.putMapParams("token", token,"classId",classId));
			MyUtil.putMapParams(result,"state",1,"info",videos);
		}
		return result;
	}


	public Map<String, Object> version(String token) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//验证用户
			Version vs=principalDao.findVersion( );
			MyUtil.putMapParams(result,"state",1,"info",vs);
		}
		return result;
	}


	public Map<String, Object> getHuanxin(String token) {
		WorkerInfo workerInfo= principalDao.findPrincipalInfoByToken( token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//判断这个用户是否存在
			Map<String,Object> info=MyUtil.putMapParams("worker","worker"+workerInfo.getWorkerId(), "pwd", "1");
			MyUtil.putMapParams(result,"state", 1,"info",info);
		}
		return result;
	}






	public WorkerInfo findPrincipalInfoById(Integer id) {
		WorkerInfo workerInfo=principalDao.findPrincipalInfoById(id);
		return workerInfo;
	}
}
