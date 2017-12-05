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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.garten.Thread.AddRecipeNotify;
import com.garten.Thread.DeleteBabyThread;
import com.garten.Thread.DeleteParentThread;
import com.garten.Thread.DeleteTeacherThread;
import com.garten.Thread.InsertCheckThread;
import com.garten.Thread.SmallcontrolSendNotify;
import com.garten.dao.AgentDao;
import com.garten.dao.AttendanceDao;

import com.garten.dao.BigcontrolDao;
import com.garten.dao.ParentDao;
import com.garten.dao.PrincipalDao;
import com.garten.dao.SmallcontrolDao;
import com.garten.dao.WorkerDao;
import com.garten.model.agent.AgentInfo;
import com.garten.model.baby.BabyInfo;
import com.garten.model.company.Employee;
import com.garten.model.daka.DakalogAll;
import com.garten.model.garten.GartenAttendance;
import com.garten.model.garten.GartenCharge;
import com.garten.model.garten.GartenInfo;
import com.garten.model.garten.GartenRecipe;
import com.garten.model.garten.IgnoreTime;
import com.garten.model.gartenClass.GartenClass;
import com.garten.model.gartenClass.GartenGrade;
import com.garten.model.other.AttendanceNo;
import com.garten.model.other.InfoLog;
import com.garten.model.other.MessageLog;
import com.garten.model.other.Order;
import com.garten.model.other.TPermission;
import com.garten.model.other.Unusual;
import com.garten.model.other.VisitCount;
import com.garten.model.parent.ParentInfo;
import com.garten.model.worker.WorkerInfo;
import com.garten.model.worker.WorkerMessageLog;
import com.garten.util.LyUtils;
import com.garten.util.excel.ExcelUtil;
import com.garten.util.lxcutil.MyParamAll;
import com.garten.util.lxcutil.MyUtilAll;
import com.garten.util.md5.CryptographyUtil;
import com.garten.util.myutil.MyUtil;
import com.garten.util.mywxpay.MyXMLUtil;
import com.garten.util.mywxpay.PayCommonUtil;
import com.garten.util.page.DividePage;
import com.garten.util.page.MyPage;
import com.garten.vo.attendance.BabySimpleInfo;
import com.garten.vo.baby.BabyLeaveLogAll;
import com.garten.vo.baby.BabyMessageAndParent;
import com.garten.vo.baby.UnusualAll;
import com.garten.vo.bigcontrol.BabyMessage;
import com.garten.vo.bigcontrol.ClassManageBig;
import com.garten.vo.bigcontrol.GartenClassAll;
import com.garten.vo.bigcontrol.ParentMessage;
import com.garten.vo.bigcontrol.WorkerMessage;
import com.garten.vo.garent.GartenAndAgent;
import com.garten.vo.parent.ParentInfoCharge;
import com.garten.vo.smallcontrol.BabyCheckSimple;
import com.garten.vo.smallcontrol.CardNoDetail;
import com.garten.vo.smallcontrol.GartenClassName;
import com.garten.vo.smallcontrol.GartenLessonDetail;
import com.garten.vo.smallcontrol.MachineDetail;
import com.garten.vo.smallcontrol.OrderAll;
import com.garten.vo.teacher.ActivityDetailAll;
import com.garten.vo.teacher.BabyCheckLogAll;
import com.garten.vo.teacher.ClassManage;
import com.garten.vo.teacher.WorkerLeaveLogPrin;
import com.garten.vo.teacher.WorkerNameMessage;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;



@Service
public class SmallcontrolService {
	
	@Autowired
	private SmallcontrolDao smallcontrolDao;
	@Autowired
	private WorkerDao workerDao;
	@Autowired
	private BigcontrolDao bigcontrolDao;
	@Autowired
	private ParentDao parentDao;
	@Autowired
	private PrincipalDao principalDao;
	@Autowired
	private BigcontrolService bigcontrolService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private AttendanceDao attendanceDao;
	@Autowired
	private AgentDao agentDao;
	
	public Map<String, Object> login(String phoneNumber, String pwd) {
		Map<String,Object> param=MyUtil.putMapParams("phoneNumber", phoneNumber,"pwd",CryptographyUtil.md5(pwd, "lxc"));
		WorkerInfo worker=smallcontrolDao.findWorkerByPwd(param);
		String uuid="error";
		Map<String,Object> result=MyUtil.putMapParams("state", 0, "info", uuid);
		//如果worker为空则返回error
		//如果worker不为空则返回uuid,并修改token为uuid
		if(null!=worker&&!"".equals(worker)){
			uuid=smallcontrolDao.findToken(param);
			String gartenName = smallcontrolDao.getGartenNameById(worker.getGartenId());
			
			MyUtil.putMapParams(result,"state", 1, "info", worker,"gartenName",gartenName);
		}
		return result;
	}
	public Map<String, Object> updateLogin(String phoneNumber, String pwd, String number) throws ParseException {
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByAccount(phoneNumber);//根据账号查找到用户,手机号
		  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info","没有这个用户");
			if(null!=workerInfo){
				Map<String,Object> duanxin=workerService.duanxin(workerInfo.getPhoneNumber(),4,number);//0代表 老师端短信
				String newToken= UUID.randomUUID().toString();
				MyUtil.putMapParams(result,"state", 2, "info", "验证码错误");
				if(duanxin.get("0").equals("成功")){
					smallcontrolDao.updatePwdToken(MyUtil.putMapParams("token", newToken, "phoneNumber", phoneNumber,"pwd",CryptographyUtil.md5(pwd, "lxc")));
					MyUtil.putMapParams(result,"state", 1, "info", newToken);
				}
			}
			//验证码错误返回  验证错误信息
			return result;
	}
	
	//type 0给老师家长发 2给家长发 3给老师发
	public Map<String, Object> sendNotified(String token,Integer classId,Integer gradeId,Integer type ,String title,String content) throws APIConnectionException, APIRequestException {
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
		  Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=workerInfo){
				
				try {
					SmallcontrolSendNotify notify = new SmallcontrolSendNotify(classId, gradeId, type, title, content, workerInfo);
					Thread thread = new Thread(notify);
					thread.start();
				} catch (Exception e) {
					e.printStackTrace();
					return result;
				}
				MyUtil.putMapParams(result,"state", 1);
			}
			return result;
	}
	public Map<String, Object> getGartenGrade(String token) {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号

			if(null!=workerInfo){
				List<String> grades=new ArrayList<String>();
				List<GartenClassAll>  classesAlls=new ArrayList();
				grades=bigcontrolDao.findGartenGrade(workerInfo.getGartenId());
				for(String grade:grades){
					List<GartenClass>  classes=new ArrayList();
					GartenClassAll  classesAll=new GartenClassAll();
					classes=bigcontrolDao.findGartenClasses(MyUtil.putMapParams("gartenId", workerInfo.getGartenId(), "leadGrade", grade));
					classesAll.setLeadGrade(grade);
					classesAll.setClasses(classes);
					classesAlls.add(classesAll);
				}
			MyUtil.putMapParams(result,"state", 1,"info",classesAlls);
			}
			
	return result;
	}
	
	
	public Map<String, Object> WorkerMessage(String token, String name, String phoneNumber,Integer pageNo ) {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				List<WorkerMessage> workerMessage=bigcontrolDao.findWorkerMessage(MyUtil.putMapParams("name", name , "phoneNumber",phoneNumber,"gartenId", workerInfo.getGartenId()));
			MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(workerMessage, pageNo),"count",workerMessage.size());
			}
			
			return result;
	}
	
	public Map<String, Object> WorkerMessageNo(String token, String name, String phoneNumber ) {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				List<WorkerMessage> workerMessage=bigcontrolDao.findWorkerMessage(MyUtil.putMapParams("name", name , "phoneNumber",phoneNumber,"gartenId", workerInfo.getGartenId()));
			MyUtil.putMapParams(result,"state", 1,"info",workerMessage);
			}
		
		return result;
	}
	public Map<String, Object> babyMessage(String token,String name ,Integer gradeId, Integer classId,Integer pageNo ) {
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
		if(null!=workerInfo){
			List<BabyMessageAndParent> babyMessages=smallcontrolDao.findBabyMessage(MyUtil.putMapParams("name", name ,"gartenId",workerInfo.getGartenId(),"gradeId",gradeId,"classId",classId));
			MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(babyMessages, pageNo),"count",babyMessages.size());
		}
		return result;
	}
	
	public Map<String, Object> parentMessage(String token,String name ,String phoneNumber,Integer pageNo, Integer attendanceState, Integer monitorState ) throws ParseException {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){//先找到符合搜索的所有家长
				Map<String, Object> params = MyUtil.putMapParams("name", name, "phoneNumber", phoneNumber,"gartenId",workerInfo.getGartenId(),"pageNo",(pageNo-1)*16);
				List<ParentInfo> parents=bigcontrolDao.findParentMessage(params);
				Integer count = bigcontrolDao.findParentMessageCount(params);
				int pageCount = new DividePage(16, count, pageNo).getPageCount();
				List<ParentMessage>   parentMessages=new ArrayList<ParentMessage>();//为这些佳航附加 他们的宝宝信息
				ClassManageBig classManageBig=new ClassManageBig();//创建一个空的家长+宝宝对象
				Long nowTime = new Date().getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for(ParentInfo p:parents){
					System.err.println(p);
					List<ClassManageBig>  classManageBigList=new ArrayList<ClassManageBig>();
					Boolean attendanceFlag = false;
					Boolean monitorFlag = false;
					String[]  babyIds=p.getBabyId();
					String[]  monitor=p.getMonitorTime();
					String[] attendance=p.getAttendanceTime();
					for(int i=0;i<babyIds.length;i++){//家长babyId里面每一个id就添加一个宝宝信息
						classManageBig=bigcontrolService.findBaby(Integer.valueOf(babyIds[i]));
						if(null!=classManageBig){
							classManageBig.setMonitorTime(monitor[i]);
							if(sdf.parse(monitor[i]).getTime()>nowTime){
								monitorFlag=true;
							}
							classManageBig.setAttendanceTime(attendance[i]);
							if(sdf.parse(attendance[i]).getTime()>nowTime){
								attendanceFlag=true;
							}
							classManageBigList.add(classManageBig);
						}
					}
					//全部未开通
					if(1==attendanceState&&1==monitorState){			//全部未开通
						if(monitorFlag||attendanceFlag)
							continue;						//跳出循环  不添加该家长
					}else if(2==attendanceState&&1==monitorState){		//考勤开通，视频未开通
						if(!attendanceFlag||monitorFlag)
							continue;
					}else if(1==attendanceState&&2==monitorState){		//考勤未开通，视频开通
						if(attendanceFlag||!monitorFlag)
							continue;
					}else if(2==attendanceState&&2==monitorState){		//全部开通状态
						if(!monitorFlag||!attendanceFlag)
							continue;
					}else if(3==attendanceState&&1==monitorState){		//考勤未选择   视频未开通
						if(monitorFlag)
							continue;
					}else if(3==attendanceState&&2==monitorState){		//考勤未选择   视频已开通
						if(!monitorFlag)
							continue;
					}else if(1==attendanceState&&3==monitorState){		//考勤未开通，视频未选择
						if(attendanceFlag)
							continue;
					}else if(2==attendanceState&&3==monitorState){		//考勤已开通，视频未选择
						if(!attendanceFlag)
							continue;
					}
					//添加一个完整的家长宝宝信息
					parentMessages.add(new ParentMessage(p,classManageBigList));
				}
				MyUtil.putMapParams(result,"state", 1,"info",parentMessages, "pageCount",pageCount,"count",count);
		}
			
	return result;
	}
	
	public Map<String, Object> getClassAll(String token,String leadGrade ,Integer pageNo) {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				List<GartenClassName>  classes=parentDao.findClassesName(MyUtil.putMapParams("token", token, "leadGrade", leadGrade));
				for(GartenClassName c:classes){
					List<String> teacherName=parentDao.findClassName(c.getClassId());
					c.setTeacherName(teacherName);
					Integer babyCount = smallcontrolDao.getBabyCountByClassId(c.getClassId());
					c.setBabyCount((null==babyCount)?0:babyCount);
				}
				MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(MyUtil.paixuClass(classes), pageNo));
		}
			
	return result;
	}
	
	
	public Map<String, Object> getClassNumber(String token ) {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0,"before",null,"after",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
			String leadClass=smallcontrolDao.findMaxLeadClass(workerInfo.getGartenId());
			Integer classNumber=MyUtil.changeNumber(leadClass)+1;
			String leadClass2=MyUtil.changeNumber2(classNumber)+"班";
			MyUtil.putMapParams(result,"state", 1,"before",leadClass,"after",leadClass2);
		}
			
	return result;
	}
	
	public Map<String, Object> addClass(String token ) {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
			String leadClass=smallcontrolDao.findMaxLeadClass(workerInfo.getGartenId());
			Integer classNumber=MyUtil.changeNumber(leadClass)+1;
			leadClass=MyUtil.changeNumber2(classNumber)+"班";
			Map<String,Object> param=MyUtil.putMapParams("leadClass", leadClass, "gartenId", workerInfo.getGartenId());
			MyUtil.putMapParams(param, "leadGrade", "小班");
			bigcontrolDao.insertClass(param);
			MyUtil.putMapParams(param, "leadGrade", "中班");
			bigcontrolDao.insertClass(param);
			MyUtil.putMapParams(param, "leadGrade", "大班");
			bigcontrolDao.insertClass(param);
			MyUtil.putMapParams(result,"state", 1);
		}
			
	return result;
	}
	
	
	public Map<String, Object> deleteClass(String token ) {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
			String leadClass=smallcontrolDao.findMaxLeadClass(workerInfo.getGartenId());//找到最大的班级名"一班"
			Map<String,Object> param=MyUtil.putMapParams("leadClass", leadClass, "gartenId", workerInfo.getGartenId());
			MyUtil.putMapParams(param, "leadGrade", "小班");
			bigcontrolDao.deleteClass(param);
			MyUtil.putMapParams(param, "leadGrade", "中班");
			bigcontrolDao.deleteClass(param);
			MyUtil.putMapParams(param, "leadGrade", "大班");
			bigcontrolDao.deleteClass(param);
			MyUtil.putMapParams(result,"state", 1);
		}
			
	return result;
	}
	
	/**大改
	 * 1修改幼儿园所有班级的gradeId
	 */
	public Map<String, Object> graduation(String token) {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				List<GartenClass> gc=smallcontrolDao.findGartenClassByGartenId(workerInfo.getGartenId(),null);
				for(GartenClass g:gc){
					smallcontrolDao.graduation(g);
				}
				 MyUtil.putMapParams(result,"state", 1);
		}
	return result;
	}
	public Map<String, Object> getDakaTime(String token) {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				GartenAttendance attendance = attendanceDao.findGartenAttendanceByGartenId(workerInfo.getGartenId()); 
				MyUtil.putMapParams(result,"state", 1,"info",attendance);
		}
	return result;
	}
	
	public Map<String, Object> updateDakaTime(String token,String time1,String time2,String time3,String time4,String time5,String time6,Integer teacherAttendanceFlag,Integer studentAttendanceFlag) {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				Map<String,Object> param=MyUtil.putMapParams("gartenId", workerInfo.getGartenId(), "time1", time1, "time2", time2, "time3", time3, 
						"time4", time4,"teacherAttendanceFlag",teacherAttendanceFlag,"studentAttendanceFlag",studentAttendanceFlag);
				MyUtil.putMapParams(param, "time5", time5, "time6", time6);
				smallcontrolDao.updateDakaTime(param);
				smallcontrolDao.updateRebootFlag(workerInfo.getGartenId());
				 MyUtil.putMapParams(result,"state", 1);
		}
	return result;
	}
	
	
	public Map<String, Object> getIgnoreTime(String token,Integer pageNo) {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				List<Long> time= workerDao.findAttendanceCircleByTokenList(token);
				 MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(time, pageNo));
		}
	return result;
	}
//考勤卡号里面不用添加家长
	/*public Map<String, Object> registWorker(Integer gartenId,String parentName,String phoneNumber,String address) {
		Map<String,Object> param=MyUtil.putMapParams("gartenId", gartenId,"phoneNumber",phoneNumber,"address",address);
		smallControlDao.createWorker(param);
		Map<String,Object> result=MyUtil.putMapParams("state", 1);
		return result;
	}


	public Map<String, Object> registBaby(Integer gartenId, String babyName,String leadClass,String leadGrade, String parentId,
			String parentRelation) {
		Map<String,Object> param=MyUtil.putMapParams("gartenId", gartenId,"babyName",babyName,"leadClass",leadClass,"leadGrade",leadGrade,"parentId",parentId,"parentRelation",parentRelation,"job","宝宝");
			
			List<WorkerInfo> worker=workerDao.findWorkerInfoByClass(param);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(0!=worker.size()){
				String teacherId="";
				for(int i=0;i<worker.size();i++){
					teacherId+=","+worker.get(i).getWorkerId();
				}
				teacherId=teacherId.substring(1,teacherId.length());
				smallControlDao.createAttendanceNo(param);
				AttendanceNo attendanceNo=smallControlDao.findAttendanceNo(param);
				MyUtil.putMapParams(param,"jobId", attendanceNo.getJobId(),"teacherId",teacherId);//根据创建好的考勤卡号表查到最新的宝宝主键 用来创建宝宝信息
				smallControlDao.createBaby(param);
				MyUtil.putMapParams(result,"state", 1);
			}
		
		return result;
	}*/
	public Map<String, Object> addIgnoreTime(String token,Long date) {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				Map<String,Object> param=MyUtil.putMapParams("token", token, "date", date, "gartenId",workerInfo.getGartenId() );
				IgnoreTime  ignore= smallcontrolDao.findIgnoreOne(param);//是否已有这个忽略存在
				if(null==ignore){
					 bigcontrolDao.insertIgnore(param);
					 MyUtil.putMapParams(result,"state", 1);
				}else{
					 MyUtil.putMapParams(result,"state", 2);
				}
		}
	return result;
	}
	public Map<String, Object> deleteIgnoreTime(String token, Long date) {
		 Map<String,Object> result=MyUtil.putMapParams("state", 0);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				 bigcontrolDao.deleteIgnore(MyUtil.putMapParams("gartenId", workerInfo.getGartenId(), "date", date));
				 MyUtil.putMapParams(result,"state", 1);
		}
	return result;
	}
	//素哟偶异常 type: 0=宝宝 1=老师
	public Map<String, Object> yichang(String token,Long startTime,Long endTime, Integer type,Integer state,Integer pageNo) throws ParseException {
		startTime=MyUtil.getYMDLong(startTime);
		endTime=MyUtil.getYMDLong(endTime);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
					Map<String,Object> param=MyUtil.putMapParams("token", token, "startTime", startTime,"endTime",endTime,"gartenId",workerInfo.getGartenId());
					//获取老师某时间段的异常
					List<UnusualAll> workerYichang = smallcontrolDao.findUnusualAllByToken(param);
					//List<UnusualAll> workerYichang= principalDao.findUnusualAllByToken(param);
					//获取宝宝某时间段的异常
					//List<UnusualAll> babyYichang= workerDao.findUnusualAllByTokenSmall(param);
					List<UnusualAll> babyYichang = smallcontrolDao.findUnusualAllByTokenSmall(param);
				 MyUtil.putMapParams(result,"state", 1,"info",MyUtil.finalSmallYichang(workerYichang,babyYichang,type,state,pageNo));
		}
	return result;
	}
	
	public Map<String, Object> yichangResolve(Integer unusualId ,Integer state ){
		Unusual unusual = smallcontrolDao.findUnusualByUnusualId(unusualId);
		if("宝宝".equals(unusual.getJob())){	//处理宝宝异常
			if(1==state){
				BabyInfo babyInfo = attendanceDao.findBabyById(unusual.getJobId());
				ParentInfo parentInfo = parentDao.findParentById(babyInfo.getParentId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String content = "您的孩子"+babyInfo.getBabyName()+"于"+sdf.format(unusual.getUnusualTime());
				Integer mode = unusual.getUnusualType();
				String type = mode==5?"上午迟到":(mode==6?"上午早退":(mode==7?"下午迟到":(mode==8?"下午早退":(mode==9?"下午提前入园":"下午推迟离园"))));
				String message = content +type;
				try {
					bigcontrolService.pushOne(MyParamAll.JIGUANG_PARENT_APP, MyParamAll.JIGUANG_PARENT_MASTER, message, parentInfo.getPhoneNumber());
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		workerDao.resolveUnusual(unusualId, state);
		return MyUtil.putMapParams("state", 1);
	}
	//type 0宝宝请假1 老师请假
	public Map<String, Object> leave(String token, Long startTime, Long endTime, Integer type, Integer state, Integer pageNo) throws ParseException {
		startTime=MyUtil.getYMDLong(startTime);
		endTime=MyUtil.getYMDLong(endTime);
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
					Map<String,Object> param=MyUtil.putMapParams("token", token, "startTime", startTime,"endTime",endTime,"gartenId",workerInfo.getGartenId());
					//获取老师某时间段的异常
					List<WorkerLeaveLogPrin> workerLeave=MyUtil.paixuWorkerLeaveLog(/*principalDao.findLeaveLogByToken(param)*/smallcontrolDao.findLeaveLogByToken(param));
					//获取宝宝某时间段的请假
					List<BabyLeaveLogAll> babyLeave=MyUtil.paixuBabyLeaveLog(/*workerDao.findLeaveLogByTokenSmall(param)*/smallcontrolDao.findLeaveLogByTokenSmall(param));
					MyUtil.putMapParams(result,"state", 1,"info",MyUtil.finalSmallLeave(workerLeave,babyLeave,type,state,pageNo));
		}
	return result;
	}
	public Map<String, Object> classCheck(String token, Long time,Integer classId,Integer pageNo  ) {
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
		 System.err.println(workerInfo);
			if(null!=workerInfo){
				Map<String,Object> param=MyUtil.putMapParams("time", time,"classId",classId,"gartenId",workerInfo.getGartenId());
				List<BabyCheckLogAll> babyCheckLogs= principalDao.findBabyCheckByClassSmall(param);//获取所有宝宝的晨检 考勤信息
				MyUtil.putMapParams(result, "state",1,"info",MyPage.listPage16(MyUtil.paixuBabyCheckLog(babyCheckLogs), pageNo));//排序 体温0的在前面 总的按id排序
				}
	return result;
	}
	public Map<String, Object> addlesson(String token, Long time, Integer ampm, String lessonName, Integer classId,
			String startTime, String endTime) {
		Map<String,Object> result=MyUtil.putMapParams("state", 0);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				Map<String,Object> param=MyUtil.putMapParams("gartenId", workerInfo.getGartenId(),"time",time,"ampm",ampm,"lessonName",lessonName,"classId",classId,"startTime",startTime,"endTime",endTime);
				smallcontrolDao.insertLesson(param);//添加课程
				MyUtil.putMapParams(result, "state",1);
				}
	return result;
	}
	
	public synchronized Map<String, Object> addWeekLesson(String token,Long time,Integer[] ampm,String[] lessonName,Integer classId,String[] startTime,String[] endTime){
		Map<String,Object> result=MyUtil.putMapParams("state", 0);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				for(int i=0;i<5;i++){

					for(int j=0;j<lessonName.length;j++){
						Map<String, Object> params = MyUtil.putMapParams("gartenId", workerInfo.getGartenId(),"time",time,"ampm",ampm[j],"lessonName",lessonName[j],"classId",classId,"startTime",startTime[j],"endTime",endTime[j]);
						smallcontrolDao.insertLesson(params);
					}
					time+=86400;
				}
				MyUtil.putMapParams(result, "state",1);
			}
			
			return result;
	}
	
	
	public Map<String, Object> lesson(String token, Long time, Integer classId) {
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				Map<String,Object> param=MyUtil.putMapParams("classId", classId,"time",time,"gartenId",workerInfo.getGartenId());
				List<GartenLessonDetail> lesson= smallcontrolDao.findLesson(param);
				MyUtil.putMapParams(result, "state",1,"info",lesson);
				}
			return result;
	}
	public Map<String, Object> deletelesson(String token, Integer lessonId) {
		Map<String,Object> result=MyUtil.putMapParams("state", 0);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				smallcontrolDao.deletelesson(lessonId);
				MyUtil.putMapParams(result, "state",1);
			}
	return result;
	}
	public Map<String, Object> recipe(String token, Long time ) {
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				Map<String,Object> param=MyUtil.putMapParams("gartenId", workerInfo.getGartenId(), "time", time);
				List<GartenRecipe> recipe=smallcontrolDao.findRecipe(param);
				MyUtil.putMapParams(result, "state",1,"info",recipe);
			}
	return result;
	}
	public Map<String, Object> addrecipe(String token,Long time,String foodName,String foodContent,HttpServletRequest request,HttpSession session) {
		Map<String,Object> result=MyUtil.putMapParams("state",0 );
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				MyUtil.putMapParams(result,"state",2 );//图片过小或格式错误
				try {
					String imgs=MyUtilAll.photoImage(request, session,"smallcontrol=addrecipe=gartenId"+workerInfo.getGartenId());
					Map<String,Object> param=MyUtil.putMapParams("gartenId", workerInfo.getGartenId(), "time", time,"foodName",foodName,"foodContent",foodContent,"foodImg",imgs);
					smallcontrolDao.addrecipe(param);
					AddRecipeNotify addRecipeNotify = new AddRecipeNotify(workerInfo.getGartenId());
					Thread thread = new Thread(addRecipeNotify);
					thread.start();
					MyUtil.putMapParams(result, "state",1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	return result;
	}
	public Map<String, Object> order(String token,Integer pageNo,Integer state,String name,
			String phoneNumber,Integer type,String babyName,Long startTime,Long endTime) {
		WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号//根据账号查找到用户,手机号
		  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			if(null!=workerInfo){
				
				Map<String, Object> params = MyUtil.putMapParams("state", state, "type",type, "gartenId",workerInfo.getGartenId());
				MyUtil.putMapParams(params, "startTime", startTime, "endTime", endTime);
				List<OrderAll> order=bigcontrolDao.findOrder(params);
				int pageCount = new DividePage(16, order.size(), pageNo).getPageCount();
				if(order.size()>16){
					order.subList((pageNo-1)*16, (pageNo)*16);
				}
				order=MyUtil.appendOrderName(order,name,phoneNumber,babyName);
					MyUtil.putMapParams(result,"state", 1,"info",order,"pageCount",pageCount);
			}
			return result;
	}

	
	
	/*
	 * 
	 * 8.19 交接
	 */
	
	public Map<String,Object> getStudentList(String token,Integer classId){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
		 if(null!=workerInfo){
			 List<BabySimpleInfo> students = attendanceDao.findBabyByClassId(classId);
			 MyUtil.putMapParams(result, "state",1,"info",students);
		 }
		 return result;
	}
	
	public Map<String,Object> cardNoList(String token,String job,Integer classId,Integer pageNo){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
		ArrayList<CardNoDetail> list = new ArrayList<CardNoDetail>();	
		List<CardNoDetail> babyCardNoList=null;
		List<CardNoDetail> teacherCardNoList = null;
		if(null!=workerInfo){
			if(null!=job){		//根据
				if("宝宝".equals(job)){
					babyCardNoList = smallcontrolDao.getBabyCardNoList(workerInfo.getGartenId(),job,classId);
					list.addAll(babyCardNoList);
				}else if("老师".equals(job)){
					teacherCardNoList = smallcontrolDao.getTeacherCardNoList(workerInfo.getGartenId(),job,classId);
					list.addAll(teacherCardNoList);
				}
			}
			if(null==job||"".equals(job)){			
				babyCardNoList = smallcontrolDao.getBabyCardNoList(workerInfo.getGartenId(),job,classId);
				teacherCardNoList = smallcontrolDao.getTeacherCardNoList(workerInfo.getGartenId(),job,classId);
				list.addAll(teacherCardNoList);
				list.addAll(babyCardNoList);
				
			}
			MyUtil.putMapParams(result, "state",1,"info",MyPage.listPage16(list, pageNo));
		}
		return result;
	}
	
	public void exportAttendance(String token , String job,Integer classId,HttpServletResponse response){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
		ArrayList<CardNoDetail> list = new ArrayList<CardNoDetail>();	
		List<CardNoDetail> babyCardNoList=null;
		List<CardNoDetail> teacherCardNoList = null;
		if(null!=workerInfo){
			if(null!=job){		//根据
				if("宝宝".equals(job)){
					babyCardNoList = smallcontrolDao.getBabyCardNoList(workerInfo.getGartenId(),job,classId);
					list.addAll(babyCardNoList);
				}else if("老师".equals(job)){
					teacherCardNoList = smallcontrolDao.getTeacherCardNoList(workerInfo.getGartenId(),job,classId);
					list.addAll(teacherCardNoList);
				}
			}
			if(null==job||"".equals(job)){			
				babyCardNoList = smallcontrolDao.getBabyCardNoList(workerInfo.getGartenId(),job,classId);
				teacherCardNoList = smallcontrolDao.getTeacherCardNoList(workerInfo.getGartenId(),job,classId);
				list.addAll(teacherCardNoList);
				list.addAll(babyCardNoList);
				
			}
			try {
				response.setContentType("application/x-execl");
				response.setHeader("Content-Disposition", "attachment;filename=" + new String("考勤卡列表.xls".getBytes(), "ISO-8859-1"));
				ServletOutputStream outputStream = response.getOutputStream();
				ExcelUtil.exportAttendanceNoExcel(list, outputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	//绑定考勤卡
	public Map<String,Object> bindingCard(Integer jobId,String cardNo){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		
			 CardNoDetail cnd = smallcontrolDao.findJobByCardNo(cardNo);
			 if(null==cnd){
				 AttendanceNo attendanceNo = smallcontrolDao.findCardNo(jobId);
				 String cardNo1 = attendanceNo.getCardNo1();
				 String cardNo2 = attendanceNo.getCardNo2();
				 String cardNo3 = attendanceNo.getCardNo3();
				 if(null==cardNo1){
					 smallcontrolDao.bindingCardNo1(jobId,cardNo);
				 }else if(null==cardNo2){
					 smallcontrolDao.bindingCardNo2(jobId,cardNo);
				 }else if(null==cardNo3){
					 smallcontrolDao.bindingCardNo3(jobId,cardNo);
				 }
				 smallcontrolDao.updateRebootFlag(attendanceNo.getGartenId());
				 smallcontrolDao.updateCardGartenId(cardNo,attendanceNo.getGartenId());
				 MyUtil.putMapParams(result, "state",1);
			 }else{
				 MyUtil.putMapParams(result, "state",2);
			 }
		 
		 return result;
	}
	
	public Map<String,Object> cancelBinding(Integer jobId, String cardNo){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		
			 AttendanceNo attendanceNo = smallcontrolDao.findCardNo(jobId);
			 if(cardNo.equals(attendanceNo.getCardNo1())){
				 smallcontrolDao.cancelBindingNo1(jobId);
			 }else if(cardNo.equals(attendanceNo.getCardNo2())){
				 smallcontrolDao.cancelBindingNo2(jobId);
			 }else if(cardNo.equals(attendanceNo.getCardNo3())){
				 smallcontrolDao.cancelBindingNo3(jobId);
			 }
			 AttendanceNo no = smallcontrolDao.findAttendanceNoById(jobId);
			 smallcontrolDao.updateRebootFlag(no.getGartenId());
			 smallcontrolDao.updateCardGartenId(cardNo,null);
			 MyUtil.putMapParams(result, "state",1);
		 return result;
	}
	
	public Map<String,Object> teacherPermission(){
		List<TPermission> list = smallcontrolDao.findTeacherPermisson();
		return MyUtil.putMapParams("state", 1, "info",list);
	}
	
	public Map<String, Object> updateTeacher(String token,Integer sex,Integer age,String education,String certificate,String chinese,
			Integer[] classId,String phoneNumber,String workerName,Integer workerId,String jobcall, String permission){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				//WorkerInfo teacher = smallcontrolDao.findworkerById(workerId);
				WorkerInfo worker = smallcontrolDao.findWorkerByPhoneNumber(phoneNumber);
				if(null!=worker&&!worker.getWorkerId().equals(workerId)){
					return MyUtil.putMapParams(result, "state",3);//手机号已存在
				}
				smallcontrolDao.updateTeacher( sex, age, education, certificate, chinese,LyUtils.intChangeToStr(classId), phoneNumber,workerName,workerId,jobcall,permission);
				smallcontrolDao.updateRebootFlag(workerInfo.getGartenId());
			
				MyUtil.putMapParams(result, "state",1);
			}
			
			return result;
	}

	public Map<String,Object> updateParent(String token,Integer parentId,String parentName,String address, String[] identity, Integer[] babyId, String phoneNumber){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				
				ParentInfo parent = smallcontrolDao.findParentByPhone(phoneNumber);
				ParentInfo parentInfo = parentDao.findParentById(parentId);
				if(null!=parent&&!parent.getParentId().equals(parentId)){
					return MyUtil.putMapParams(result, "state",2);			//该手机号码已经被注册
				}
				smallcontrolDao.updateParentMessage(parentId,parentName,address,phoneNumber);
				
				if(babyId!=null){		//有新增的宝宝
					String newBabyId="";
					String newGarten="";
					String newIdentity="";
					String newMonitorTime="";
					String newAttendanceTime="";
					if("".equals(LyUtils.StrChangeToStr(parentInfo.getBabyId()))){	//第一个宝宝为空字符串  即实际没有宝宝
						//拼接新的宝宝id
						for(int i=0;i<babyId.length;i++){
							if(i==0){
								newBabyId=newBabyId+babyId[i];
							}else{
								newBabyId=newBabyId+","+babyId[i];
							}
						}
							//拼接新幼儿园id
						for(int i=0;i<babyId.length;i++){
							if(i==0){
								newGarten=newGarten+workerInfo.getGartenId();
							}else{
								newGarten=newGarten+","+workerInfo.getGartenId();
							}
						}
							//拼接新身份
						for(int i=0;i<babyId.length;i++){
							if(i==0){
							newIdentity=newIdentity+identity[i];
							}else{
								newIdentity=newIdentity+","+identity[i];
							}
						}
							//拼接新的监控直播时间
						for(int i=0;i<babyId.length;i++){
							if(i==0){
								newMonitorTime = newMonitorTime+"2000-01-01";
							}else{
								newMonitorTime = newMonitorTime+",2000-01-01";
							}
						}
							//拼接新的考勤时间
						for(int i=0;i<babyId.length;i++){
							if(i==0){
								newAttendanceTime = newAttendanceTime+"2000-01-01";
							}else{
								newAttendanceTime = newAttendanceTime+",2000-01-01";
							}
						}
					}else{										//第一个宝宝不等于字符串
						//拼接新的宝宝id
						String[] oldBabyId = parentInfo.getBabyId();
						newBabyId = LyUtils.StrChangeToStr(oldBabyId);
						for(int i=0;i<babyId.length;i++){
							newBabyId=newBabyId+","+babyId[i];
						}
							//拼接新幼儿园id
						newGarten = parentInfo.getGartenId();
						for(int i=0;i<babyId.length;i++){
							newGarten=newGarten+","+workerInfo.getGartenId();
						}
							//拼接新身份
						String[] oldIdentity = parentInfo.getIdentity();
						newIdentity = LyUtils.StrChangeToStr(oldIdentity);
						for(int i=0;i<babyId.length;i++){
							newIdentity=newIdentity+","+identity[i];
						}
							//拼接新的监控直播时间
						String[] oldMonitorTime = parentInfo.getMonitorTime();
						newMonitorTime = LyUtils.StrChangeToStr(oldMonitorTime);
						for(int i=0;i<babyId.length;i++){
							newMonitorTime = newMonitorTime+",2000-01-01";
						}
							//拼接新的考勤时间
						String[] oldAttendanceTime = parentInfo.getAttendanceTime();
						newAttendanceTime = LyUtils.StrChangeToStr(oldAttendanceTime);
						for(int i=0;i<babyId.length;i++){
							newAttendanceTime = newAttendanceTime+",2000-01-01";
						}
					}
					
					smallcontrolDao.updateParentIsExist(parentInfo.getParentId(),newBabyId,newGarten,newIdentity,newMonitorTime,newAttendanceTime);
				}
				MyUtil.putMapParams(result, "state",1);
			}
			return result;
	}
	//大改
	public Map<String, Object> updateBaby(String token,Integer babyId,String babyName,Integer sex,Long birthday,Integer classId, Double height, String health, String hobby, String specialty, String allergy,Double weight, Integer parentId){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				BabyInfo baby=parentDao.findBabyShortByBabyId(babyId);
				if(!baby.getParentId().equals(parentId)){			//修改了主监护人信息
					String newParentRelation = null;
					ParentInfo parentInfo = parentDao.findParentById(parentId);
					for(int i=0;i<parentInfo.getBabyId().length;i++){
						if(parentInfo.getBabyId()[i].equals(babyId)){
							newParentRelation=parentInfo.getIdentity()[i];
						}
					}
					Map<String,Object> param=MyUtil.putMapParams("babyId",babyId,"newparentId",parentId,"parentRelation",newParentRelation);
					smallcontrolDao.updateMainParent(param);//3
				}
				smallcontrolDao.updateBaby( babyId, babyName, sex, birthday, classId,height,health,hobby,specialty,allergy,weight);
				smallcontrolDao.updateRebootFlag(workerInfo.getGartenId());		//修改信息后 考勤机重启
				MyUtil.putMapParams(result, "state",1);
			}
			return result;
			
	}
	
	//添加老师
	public Map<String,Object> addTeacher(String token,String teacherName,Integer sex,Integer age,String phoneNumber,Integer[] classId,String education,
			String certificate,String chinese, String jobCall,String permission, String job) throws ParseException, IOException{
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				WorkerInfo worker = smallcontrolDao.findWorkerByPhoneNumber(phoneNumber);
				if(null!=worker){
					return MyUtil.putMapParams(result, "state",2);		//该老师已注册
				}
				AttendanceNo an = new AttendanceNo(job,workerInfo.getGartenId());
				smallcontrolDao.addAttendanceNo(an);
				smallcontrolDao.addWorkerTeacher(workerInfo.getGartenId(),an.getJobId(),CryptographyUtil.md5("123456", "lxc"),teacherName,phoneNumber,sex,age,education,certificate,chinese,"老师",jobCall,LyUtils.intChangeToStr(classId),permission,UUID.randomUUID().toString());
				//smallcontrolDao.updateRebootFlag(workerInfo.getGartenId());		//修改信息后 考勤机重启
			
				InsertCheckThread insertCheckThread = new InsertCheckThread(an.getJobId(), workerInfo.getGartenId(), 2);
				Thread thread2 = new Thread(insertCheckThread);
				thread2.start();
				MyUtil.putMapParams(result, "state",1);
			}
			return result;
	}
	/*
	 * 
	 * 大改 
	 */
	//添加宝宝（同时添加家长）
		public Map<String,Object> addBaby(String token,String babyName,Double height,String health,String hobby,String specialty,Integer classId,
				String allergy,Double weight,Integer sex,String parentName,String phoneNumber,String address,String identity,String cardId) throws ParseException, IOException{
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
				if(null!=workerInfo){
					/*BabyInfo babyInfo = smallcontrolDao.findBabyByIdCard(cardId);
					if(null!=babyInfo){
						return MyUtil.putMapParams(result, "state",3);		//该宝宝已经存在
					}*/
					ParentInfo parentInfo = smallcontrolDao.findParentByPhone(phoneNumber);
					
					//根据宝宝身份证获取生日
					Long birthday = null;
					if(cardId!=null&&!"".equals(cardId)){
						birthday = LyUtils.getBirthByIdCard(cardId);
					}
					//在attendance 生成一个宝宝  获取宝宝id
					AttendanceNo an = new AttendanceNo("宝宝",workerInfo.getGartenId());
					smallcontrolDao.addAttendanceNo(an);
					if(null==parentInfo){			//不存在这个家长时  生成新家长
						String monitorTime = "2000-01-01";
						String attendanceTime = "2000-01-01";
						smallcontrolDao.addParent(an.getJobId().toString(),identity,parentName,phoneNumber,address,workerInfo.getGartenId().toString(),CryptographyUtil.md5("123456", "lxc"),monitorTime,attendanceTime);
						ParentInfo parent = smallcontrolDao.findParentByPhone(phoneNumber);
						smallcontrolDao.addBaby(workerInfo.getGartenId(),babyName,birthday,height,health,hobby,specialty,classId,allergy,identity,weight,sex,an.getJobId(),parent.getParentId(),cardId);
					}else{							//已经存在这个家长
						//根据已有的家长id 添加宝宝
						smallcontrolDao.addBaby(workerInfo.getGartenId(),babyName,birthday,height,health,hobby,specialty,classId,allergy,identity,weight,sex,an.getJobId(),parentInfo.getParentId(),cardId);
						//更新已存在家长的信息
						String newBabyId;
						String newGarten;
						String newIdentity;
						String newMonitorTime;
						String newAttendanceTime;
						if("".equals(LyUtils.StrChangeToStr(parentInfo.getBabyId()))){
							//拼接新宝宝id
							newBabyId=""+an.getJobId();
								//拼接新幼儿园id
							newGarten=""+workerInfo.getGartenId();
								//拼接新身份
							newIdentity=""+identity;
								//拼接新的监控直播时间
							newMonitorTime ="2000-01-01";
								//拼接新的考勤时间
							newAttendanceTime ="2000-01-01";
						}else{
							//拼接新宝宝id
							String[] babyId = parentInfo.getBabyId();
							newBabyId = LyUtils.StrChangeToStr(babyId);
							newBabyId=newBabyId+","+an.getJobId();
								//拼接新幼儿园id
							newGarten=parentInfo.getGartenId()+","+workerInfo.getGartenId();
								//拼接新身份
							String[] oldIdentity = parentInfo.getIdentity();
							newIdentity = LyUtils.StrChangeToStr(oldIdentity);
							newIdentity=newIdentity+","+identity;
								//拼接新的监控直播时间
							String[] oldMonitorTime = parentInfo.getMonitorTime();
							newMonitorTime = LyUtils.StrChangeToStr(oldMonitorTime);
							newMonitorTime = newMonitorTime+",2000-01-01";
								//拼接新的考勤时间
							String[] oldAttendanceTime = parentInfo.getAttendanceTime();
							newAttendanceTime = LyUtils.StrChangeToStr(oldAttendanceTime);
							newAttendanceTime = newAttendanceTime+",2000-01-01";
						}
						
						smallcontrolDao.updateParentIsExist(parentInfo.getParentId(),newBabyId,newGarten,newIdentity,newMonitorTime,newAttendanceTime);
					}
						InsertCheckThread insertCheckThread = new InsertCheckThread(an.getJobId(), workerInfo.getGartenId(), 1);
						Thread thread = new Thread(insertCheckThread);
						thread.start();
						//smallcontrolDao.updateRebootFlag(workerInfo.getGartenId());		//修改信息后 考勤机重启
						String[] data=new String[]{babyName,"成功"};
						MyUtil.register(phoneNumber,MyParamAll.YTX_DUANXIN_GLBB,data);
						
					MyUtil.putMapParams(result, "state",1);
					
				}
				return result;
		}
	//添加家长 选择宝宝(宝宝已存在)
	public Map<String,Object> addParent(String token,String parentName,String phoneNumber,String address,String[] identity,Integer[] babyId) throws IOException{
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				ParentInfo parentInfo = smallcontrolDao.findParentByPhone(phoneNumber);
				if(null==parentInfo){		//家长不存在  宝宝已存在
					//String gartenIdArray = LyUtils.intChangeToStr(workerInfo.getGartenId());
					String gartenIdArray=workerInfo.getGartenId().toString();
					for(int i=0;i<babyId.length-1;i++){
						gartenIdArray=gartenIdArray+","+workerInfo.getGartenId().toString();
					}
					String babyIdArray = LyUtils.intChangeToStr(babyId);
					String identityArray = LyUtils.StrChangeToStr(identity);
					//改家长对宝宝的考勤 ，视频功能全部未开通
					String time ="2000-01-01";
					for(int i=0;i<babyId.length-1;i++){
						time+=",2000-01-01";
					}
					smallcontrolDao.addParent(babyIdArray, identityArray, parentName, phoneNumber, address, gartenIdArray, CryptographyUtil.md5("123456", "lxc"),time,time);
				}else{						//家长已存在  宝宝已存在
					
					return MyUtil.putMapParams(result, "state",2);
				}
				for(int i =0 ;i<babyId.length;i++){
					String babyName = parentDao.findBabyById(babyId[i]).getBabyName();
					String[] data=new String[]{babyName,"成功"};
					MyUtil.register(phoneNumber,MyParamAll.YTX_DUANXIN_GLBB,data);
				}
				MyUtil.putMapParams(result, "state",1);
			}
			return result;
	}
	
	//删除老师
	public Map<String,Object> deleteTeacher(Integer workerId){
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		
			WorkerInfo worker = smallcontrolDao.findworkerById(workerId);
			smallcontrolDao.deleteTeacher(workerId);
			
			//开启删除老师相关信息线程
			DeleteTeacherThread deleteTeacherThread = new DeleteTeacherThread(workerId);
			Thread thread = new Thread(deleteTeacherThread);
			thread.start();
			
			smallcontrolDao.updateRebootFlag(worker.getGartenId());	//重启标识
			MyUtil.putMapParams(result, "state",1);
		
			return result;
	}
	
	//删除宝宝
	public Map<String,Object> deleteBaby(Integer babyId){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		
		List<ParentInfo> parents = smallcontrolDao.findParentByBabyId(babyId);
		for(ParentInfo p : parents){			//该宝宝对应的家长清除该宝宝的记录
			Integer index = Arrays.binarySearch(p.getBabyId(),babyId.toString());
			String[] newBabyId = LyUtils.ArrayRemoveIndex(p.getBabyId(), index);
			String[] newIdentity = LyUtils.ArrayRemoveIndex(p.getIdentity(), index);
			String[] newAttendanceTime = LyUtils.ArrayRemoveIndex(p.getAttendanceTime(), index);
			String[] newMonitorTime = LyUtils.ArrayRemoveIndex(p.getMonitorTime(), index);
			String[] newGarten = LyUtils.ArrayRemoveIndex(p.getGartenId().split(","), index);
			smallcontrolDao.updateParentIsExist(p.getParentId(), LyUtils.StrChangeToStr(newBabyId), LyUtils.StrChangeToStr(newGarten), 
					LyUtils.StrChangeToStr(newIdentity), LyUtils.StrChangeToStr(newMonitorTime), LyUtils.StrChangeToStr(newAttendanceTime));
		}
		
		//开启删除宝宝相关信息线程
		DeleteBabyThread babyThread = new DeleteBabyThread(babyId);
		Thread thread = new Thread(babyThread);
		thread.start();
		ClassManage baby = parentDao.findBabyById(babyId);
		smallcontrolDao.updateRebootFlag(baby.getGartenId());	//重启标识
		MyUtil.putMapParams(result, "state",1);
			return result;
	}
	
	//删除家长
	public synchronized Map<String,Object> deleteParent(Integer parentId){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		
			BabyInfo babyInfo = smallcontrolDao.findBabyByParentId(parentId);
			if(null!=babyInfo){
				MyUtil.putMapParams(result,  "state",2);			//该家长为某位宝宝的主监护人  无法删除
			}else{
				//不是主监护人   开始删除这位家长的信息  isValid=1
				//开启删除家长线程
				DeleteParentThread deleteParentThread = new DeleteParentThread(parentId);
				Thread thread = new Thread(deleteParentThread);
				thread.start();
				MyUtil.putMapParams(result,  "state",1);	
			}
			
			return result;
	}
	
	
	//该幼儿园资料
	
	public Map<String ,Object > getGartenMessage(String token){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				GartenAndAgent gartenInfo = smallcontrolDao.getGartenById(workerInfo.getGartenId());
							
				if(gartenInfo.getAgentType()==1){		//0是员工  1是代理商
					AgentInfo agentInfo = agentDao.findAgentById(Integer.parseInt(gartenInfo.getAgent()));
					gartenInfo.setAgentInfo(agentInfo);
				}else if(gartenInfo.getAgentType()==0){
					Employee employee = bigcontrolDao.findEmployeeById(Integer.parseInt(gartenInfo.getAgent()));
					gartenInfo.setEmployee(employee);
				}
				MyUtil.putMapParams(result, "state",1,"info",gartenInfo);
			}
			return result;
	}
	
	//消息历史
	/*public Map<String,Object> notifyHistory(String token,String job,Long startTime,Long endTime, Integer pageNo){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				List<InfoLog> infoLog = smallcontrolDao.findNotifyHistory(workerInfo.getGartenId(),job,startTime,endTime);
				MyUtil.putMapParams(result, "state",1,"info",MyPage.listPage16(infoLog, pageNo));
			}
			return result;
	}*/
	
	//删除食谱
	public Map<String ,Object> deleteRecipe(String token, Integer recipeId){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			if(null!=workerInfo){
				smallcontrolDao.deleteRecipe(recipeId);
				MyUtil.putMapParams(result, "state",1);
			}
			return result;
	}
	
	//支付前获取价格
	public Map<String, Object> getPayPrice(String token, Integer type, Integer monthCount) {
		WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
		  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		  BigDecimal big = null;
		  if(null!=workerInfo){//验证用户
			//创建未支付订单
			/**
			 * 每种类型的支付分别是什么价格
			 * 0幼儿园本身视频1幼儿园本身考勤2幼儿园家长视频3幼儿园家长考勤4家长视频5家长考勤6:2+3
			 * 目前type只有2+3+6 帮家长买的3种支付
			 * 这里传过来的type本来是0-5的后来6需要分解为:2+3
			 */
			Map<String, Object> getPrice=null;
			Map<String, Object> getPrice1=null;
			if(6==type){
				getPrice=getPrice( token, 2, monthCount,workerInfo.getGartenId());//获取幼儿园要帮家长购买的月份
				getPrice1=getPrice( token, 3, monthCount,workerInfo.getGartenId());//获取幼儿园要帮家长购买的月份
			}else{
				getPrice=getPrice( token, type, monthCount,workerInfo.getGartenId());//获取幼儿园要帮家长购买的月份
			}
			big= (BigDecimal)getPrice.get("price");
			System.err.println(big);
			if(null!=getPrice1){
				BigDecimal big1= (BigDecimal)getPrice1.get("price");
				big=big.add(big1);
			}
			if(null==big){
				return MyUtil.putMapParams("state", 3);		//没有收费标准
			}
			List<ParentInfoCharge>  parentInfoCharge=bigcontrolDao.findParentInfoCharge(workerInfo.getGartenId());//9.9新增
			if(0==parentInfoCharge.size()){
				return MyUtil.putMapParams("state", 4);		//该幼儿园家长人数为0 没有家长
			}
			big=big.multiply(new BigDecimal(parentInfoCharge.size()));

			MyUtil.putMapParams(result,"state", 1, "price",big);
		}
		return result;
	}
	
	//支付宝支付 
	public Map<String, Object> alipay(String token,BigDecimal price,Integer type,Integer monthCount,HttpServletRequest httpRequest,
            HttpServletResponse httpResponse ) throws IOException {
		
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
		  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
	/*	if(null!=workerInfo){//验证用户
			//创建未支付订单
			*//**
			 * 每种类型的支付分别是什么价格
			 * 0幼儿园本身视频1幼儿园本身考勤2幼儿园家长视频3幼儿园家长考勤4家长视频5家长考勤6:2+3
			 * 目前type只有2+3+6 帮家长买的3种支付
			 * 这里传过来的type本来是0-5的后来6需要分解为:2+3
			 *//*
			Map<String, Object> getPrice=null;
			Map<String, Object> getPrice1=null;
			if(6==type){
				getPrice=getPrice( token, 2, monthCount,workerInfo.getGartenId());//获取幼儿园要帮家长购买的月份
				getPrice1=getPrice( token, 3, monthCount,workerInfo.getGartenId());//获取幼儿园要帮家长购买的月份
			}else{
				getPrice=getPrice( token, type, monthCount,workerInfo.getGartenId());//获取幼儿园要帮家长购买的月份
			}
			BigDecimal big= (BigDecimal)getPrice.get("price");
			System.err.println(big);
			if(null!=getPrice1){
				BigDecimal big1= (BigDecimal)getPrice1.get("price");
				big=big.add(big1);
			}
			if(null==big){
				return MyUtil.putMapParams("state", 3);		//没有收费标准
			}
			List<ParentInfoCharge>  parentInfoCharge=bigcontrolDao.findParentInfoCharge(workerInfo.getGartenId());//9.9新增
			big=big.multiply(new BigDecimal(parentInfoCharge.size()));
*/
			Long orderNumber=System.currentTimeMillis();
			String orderDetail=2==type?"购买幼儿园所有家长视频":(3==type?"购买幼儿园所有家长考勤":"购买幼儿园所有家长考勤和视频");
			Order o=new Order(orderNumber,new Date().getTime()/1000,null,"园长",price,orderDetail,workerInfo.getWorkerId(),type,0,0,monthCount,null,workerInfo.getGartenId());
			parentDao.insertOrdr(o);//创建未支付订单
			Map<String,Object> payResult=MyUtilAll.myAlipayControl(orderNumber+"", orderDetail,price.toString() , httpRequest,
		             httpResponse);
			MyUtil.putMapParams("state", 1,"info",payResult.get("result"));
		return null;
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
				//处理到期时间
				Order order=parentDao.findOrder(orderNumber);
				WorkerInfo workerInfo=workerDao.findWorkerInfoById(order.getId());
				GartenInfo gartenInfo=workerDao.findGartenInfoById(workerInfo.getGartenId());
				SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
				Date day=new Date();
				Date date=new Date();
				Long monitorTime=gartenInfo.getMonitorTime();
				Long attendanceTime=gartenInfo.getAttendanceTime();
			    Calendar calendar = Calendar.getInstance();  
				Integer  orderType=order.getType();
						if(0==orderType||1==orderType){
							if(0==orderType){//幼儿园购买本身视频
								if(gartenInfo.getMonitorTime()>day.getTime()/1000){
									System.err.println("monitorTime前"+monitorTime);
									  calendar.setTime(sim.parse(sim.format(gartenInfo.getMonitorTime()*1000))); 
									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
									monitorTime=calendar.getTime().getTime()/1000;  
									System.err.println("monitorTime后"+monitorTime);
								}else{
									calendar.setTime(day); 
									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
									  monitorTime=calendar.getTime().getTime()/1000;  
								}
							}
							if(1==orderType){//幼儿园购买本身考勤
								if(gartenInfo.getAttendanceTime()>day.getTime()/1000){
									  calendar.setTime(sim.parse(sim.format(gartenInfo.getAttendanceTime()*1000))); 
									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
									  attendanceTime=calendar.getTime().getTime()/1000;  
								}else{
									calendar.setTime(day); 
									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
									  attendanceTime=calendar.getTime().getTime()/1000;  
								}
							}
							System.err.println("monitorTime最终"+monitorTime);
							bigcontrolDao.updateGartenTime(MyUtil.putMapParams("monitorTime", monitorTime, "attendanceTime", attendanceTime,"gartenId",gartenInfo.getGartenId()));
						}else{
							 // 1找到这个幼儿园所有的宝宝的主要监护人+附加宝宝主键
							List<ParentInfoCharge>  parentInfoCharge=bigcontrolDao.findParentInfoCharge(gartenInfo.getGartenId());
							if(2==orderType||6==orderType){//修改幼儿园表里面的到期时间
								bigcontrolService.changeGartenDredge(  order.getMonthCount() ,  0,  order.getGartenId());
							}
							if(3==orderType||6==orderType){
								bigcontrolService.changeGartenDredge(  order.getMonthCount() ,  1,  order.getGartenId());
							}

							for(ParentInfoCharge p:parentInfoCharge){//为每个家长找到自己的主宝宝添加对应的时间
							String[] babyIds=p.getBabyId();
							for(int i=0;i<babyIds.length;i++){
								if(babyIds[i].equals(p.getMainBabyId()+"")){
									
									String[] attendanceTimeParent=p.getAttendanceTime();
									String[] monitorTimeParent=p.getMonitorTime();
									System.err.println(LyUtils.StrChangeToStr(attendanceTimeParent));
									System.err.println(LyUtils.StrChangeToStr(attendanceTimeParent));
									System.err.println(LyUtils.StrChangeToStr(attendanceTimeParent));
									if(2==orderType||6==orderType){
										if(sim.parse(monitorTimeParent[i]).getTime()>day.getTime()){
											System.err.println(monitorTimeParent[i]+"前");
											calendar.setTime(sim.parse(monitorTimeParent[i])); 
											  calendar.add(Calendar.MONTH, order.getMonthCount()); 
											  date = calendar.getTime();  
											  monitorTimeParent[i]=sim.format(date);
											System.err.println(monitorTimeParent[i]+"后");
										}else{
											System.err.println(monitorTimeParent[i]);
											calendar.setTime(day); 
											  calendar.add(Calendar.MONTH, order.getMonthCount()); 
											  date = calendar.getTime();  
											  monitorTimeParent[i]=sim.format(date);	
												System.err.println(monitorTimeParent[i]+"后");
										}
									}
									if(3==orderType||6==orderType){
										if(sim.parse(attendanceTimeParent[i]).getTime()>day.getTime()){
											System.err.println(attendanceTimeParent[i]+"前");
											  calendar.setTime(sim.parse(attendanceTimeParent[i])); 
											  calendar.add(Calendar.MONTH, order.getMonthCount()); 
											  date = calendar.getTime();  
											  attendanceTimeParent[i]=sim.format(date);	
											  System.err.println(attendanceTimeParent[i]+"后");
										}else{
											System.err.println(attendanceTimeParent[i]+"前");
											calendar.setTime(day); 
											  calendar.add(Calendar.MONTH, order.getMonthCount()); 
											  date = calendar.getTime();  
											  attendanceTimeParent[i]=sim.format(date);
											  System.err.println(attendanceTimeParent[i]+"后");
										}
									}
									String attendanceTimeStr=MyUtil.changeArrayToString(attendanceTimeParent);
									String monitorTimeStr=MyUtil.changeArrayToString(monitorTimeParent);
									System.err.println("attendanceTimeStr"+attendanceTimeStr);
									System.err.println("monitorTimeStr"+monitorTimeStr);
									System.err.println("parentId"+p.getParentId());
									parentDao.updateChargeTime(MyUtil.putMapParams("monitorTime", monitorTimeStr, "attendanceTime", attendanceTimeStr,"parentId",p.getParentId()));
									}
								}
							
							}
						}
				}
	return "success";
}

	
	//微信支付 
			public Map<String, Object> wxpay(String token,Integer type,Integer monthCount,BigDecimal price,HttpServletRequest httpRequest,
		            HttpServletResponse httpResponse ) throws IOException {
				
				 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
				  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				/*if(null!=workerInfo){//验证用户
					//创建未支付订单
					*//**
					 * 每种类型的支付分别是什么价格
					 * 0幼儿园本身视频1幼儿园本身考勤2幼儿园家长视频3幼儿园家长考勤4家长视频5家长考勤6:2+3
					 * 目前type只有2+3+6 帮家长买的3种支付
					 * 这里传过来的type本来是0-5的后来6需要分解为:2+3
					 *//*
					Map<String, Object> getPrice=null;
					Map<String, Object> getPrice1=null;
					if(6==type){
						getPrice=getPrice( token, 2, monthCount,workerInfo.getGartenId());//获取幼儿园要帮家长购买的月份
						getPrice1=getPrice( token, 3, monthCount,workerInfo.getGartenId());//获取幼儿园要帮家长购买的月份
					}else{
						getPrice=getPrice( token, type, monthCount,workerInfo.getGartenId());//获取幼儿园要帮家长购买的月份
					}
					BigDecimal big= (BigDecimal)getPrice.get("price");
					System.err.println(big);
					if(null!=getPrice1){
						BigDecimal big1= (BigDecimal)getPrice1.get("price");
						big=big.add(big1);
					}
					List<ParentInfoCharge>  parentInfoCharge=bigcontrolDao.findParentInfoCharge(workerInfo.getGartenId());//9.9新增
					big=big.multiply(new BigDecimal(parentInfoCharge.size()));
*/
					Long orderNumber=System.currentTimeMillis();
					String orderDetail=2==type?"购买幼儿园所有家长视频":(3==type?"购买幼儿园所有家长考勤":"购买幼儿园所有家长考勤和视频");
					Order o=new Order(orderNumber,new Date().getTime()/1000,null,"园长",price,orderDetail,workerInfo.getWorkerId(),type,1,0,monthCount,null,workerInfo.getGartenId());
					parentDao.insertOrdr(o);//创建未支付订单
					Map<String,Object> payResult=MyUtilAll.myWxpayControl(orderNumber+"", orderDetail,price.multiply(new BigDecimal(100)).toString() , httpRequest,
				             httpResponse);
					MyUtil.putMapParams(result,"state", 1,"info",payResult);
				return result;
			}
				
			
			public void wxpayyz(HttpServletRequest httpRequest,HttpServletResponse httpResponse) throws AlipayApiException, ParseException, APIConnectionException, APIRequestException, IOException, JDOMException {
			    
		        //读取参数  
		        InputStream inputStream ;  
		        StringBuffer sb = new StringBuffer();  
		        inputStream = httpRequest.getInputStream();  
		        String s ;  
		        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
		        while ((s = in.readLine()) != null){  
		            sb.append(s);  
		        }  
		        in.close();  
		        inputStream.close();  
		  
		        //解析xml成map  
		        Map<String, String> m = new HashMap<String, String>();  
		        m = MyXMLUtil.doXMLParse(sb.toString());  
		          
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
		          
		        // 账号信息  
		        String key = MyParamAll.MYWXIN_PARENT_API_KEY; // key  
		  
		        //判断签名是否正确  
		        if(PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
		            //------------------------------  
		            //处理业务开始  
		            //------------------------------  
		            String resXml = "";  
		            if("SUCCESS".equals((String)packageParams.get("result_code"))){  
		                // 这里是支付成功  
		                //////////执行自己的业务逻辑////////////////  
		                String mch_id = (String)packageParams.get("mch_id");  
		                String openid = (String)packageParams.get("openid");  
		                String is_subscribe = (String)packageParams.get("is_subscribe");  
		                String out_trade_no = (String)packageParams.get("out_trade_no");  
		                String total_fee = (String)packageParams.get("total_fee");  
		                //////////执行自己的业务逻辑////////////////  
		                  System.err.println("支付成功");
		                 Map<String,Object> param=MyUtil.putMapParams("orderNumber",out_trade_no);
		  				parentDao.zforder(param);
		  				System.err.println("回调成功");
		  				System.err.println("回调成功");
		  				System.err.println("回调成功");
		  				//处理到期时间
		  				Order order=parentDao.findOrder(out_trade_no);
		  				WorkerInfo workerInfo=workerDao.findWorkerInfoById(order.getId());
		  				GartenInfo gartenInfo=workerDao.findGartenInfoById(workerInfo.getGartenId());
		  				SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		  				Date day=new Date();
		  				Date date=new Date();
		  				Long monitorTime=gartenInfo.getMonitorTime();
		  				Long attendanceTime=gartenInfo.getAttendanceTime();
		  			    Calendar calendar = Calendar.getInstance();  
		  				Integer  orderType=order.getType();
		  						if(0==orderType||1==orderType){
		  							if(0==orderType){//幼儿园购买本身视频
		  								if(gartenInfo.getMonitorTime()>day.getTime()/1000){
		  									System.err.println("monitorTime前"+monitorTime);
		  									  calendar.setTime(sim.parse(sim.format(gartenInfo.getMonitorTime()*1000))); 
		  									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
		  									monitorTime=calendar.getTime().getTime()/1000;  
		  									System.err.println("monitorTime后"+monitorTime);
		  								}else{
		  									calendar.setTime(day); 
		  									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
		  									  monitorTime=calendar.getTime().getTime()/1000;  
		  								}
		  							}
		  							if(1==orderType){//幼儿园购买本身考勤
		  								if(gartenInfo.getAttendanceTime()>day.getTime()/1000){
		  									  calendar.setTime(sim.parse(sim.format(gartenInfo.getAttendanceTime()*1000))); 
		  									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
		  									  attendanceTime=calendar.getTime().getTime()/1000;  
		  								}else{
		  									calendar.setTime(day); 
		  									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
		  									  attendanceTime=calendar.getTime().getTime()/1000;  
		  								}
		  							}
		  							System.err.println("monitorTime最终"+monitorTime);
		  							bigcontrolDao.updateGartenTime(MyUtil.putMapParams("monitorTime", monitorTime, "attendanceTime", attendanceTime,"gartenId",gartenInfo.getGartenId()));
		  						}else{
		  							 // 1找到这个幼儿园所有的宝宝的主要监护人+附加宝宝主键
		  							List<ParentInfoCharge>  parentInfoCharge=bigcontrolDao.findParentInfoCharge(gartenInfo.getGartenId());
		  							if(2==orderType||6==orderType){//修改幼儿园表里面的到期时间
		  								bigcontrolService.changeGartenDredge(  order.getMonthCount() ,  0,  order.getGartenId());
		  							}
		  							if(3==orderType||6==orderType){
		  								bigcontrolService.changeGartenDredge(  order.getMonthCount() ,  1,  order.getGartenId());
		  							}

		  							for(ParentInfoCharge p:parentInfoCharge){//为每个家长找到自己的主宝宝添加对应的时间
		  							String[] babyIds=p.getBabyId();
		  							for(int i=0;i<babyIds.length;i++){
		  								if(babyIds[i].equals(p.getMainBabyId()+"")){
		  									
		  									String[] attendanceTimeParent=p.getAttendanceTime();
		  									String[] monitorTimeParent=p.getMonitorTime();
		  									System.err.println(LyUtils.StrChangeToStr(attendanceTimeParent));
		  									System.err.println(LyUtils.StrChangeToStr(attendanceTimeParent));
		  									System.err.println(LyUtils.StrChangeToStr(attendanceTimeParent));
		  									if(2==orderType||6==orderType){
		  										if(sim.parse(monitorTimeParent[i]).getTime()>day.getTime()){
		  											System.err.println(monitorTimeParent[i]+"前");
		  											calendar.setTime(sim.parse(monitorTimeParent[i])); 
		  											  calendar.add(Calendar.MONTH, order.getMonthCount()); 
		  											  date = calendar.getTime();  
		  											  monitorTimeParent[i]=sim.format(date);
		  											System.err.println(monitorTimeParent[i]+"后");
		  										}else{
		  											System.err.println(monitorTimeParent[i]);
		  											calendar.setTime(day); 
		  											  calendar.add(Calendar.MONTH, order.getMonthCount()); 
		  											  date = calendar.getTime();  
		  											  monitorTimeParent[i]=sim.format(date);	
		  												System.err.println(monitorTimeParent[i]+"后");
		  										}
		  									}
		  									if(3==orderType||6==orderType){
		  										if(sim.parse(attendanceTimeParent[i]).getTime()>day.getTime()){
		  											System.err.println(attendanceTimeParent[i]+"前");
		  											  calendar.setTime(sim.parse(attendanceTimeParent[i])); 
		  											  calendar.add(Calendar.MONTH, order.getMonthCount()); 
		  											  date = calendar.getTime();  
		  											  attendanceTimeParent[i]=sim.format(date);	
		  											  System.err.println(attendanceTimeParent[i]+"后");
		  										}else{
		  											System.err.println(attendanceTimeParent[i]+"前");
		  											calendar.setTime(day); 
		  											  calendar.add(Calendar.MONTH, order.getMonthCount()); 
		  											  date = calendar.getTime();  
		  											  attendanceTimeParent[i]=sim.format(date);
		  											  System.err.println(attendanceTimeParent[i]+"后");
		  										}
		  									}
		  									String attendanceTimeStr=MyUtil.changeArrayToString(attendanceTimeParent);
		  									String monitorTimeStr=MyUtil.changeArrayToString(monitorTimeParent);
		  									System.err.println("attendanceTimeStr"+attendanceTimeStr);
		  									System.err.println("monitorTimeStr"+monitorTimeStr);
		  									System.err.println("parentId"+p.getParentId());
		  									parentDao.updateChargeTime(MyUtil.putMapParams("monitorTime", monitorTimeStr, "attendanceTime", attendanceTimeStr,"parentId",p.getParentId()));
		  									}
		  								}
		  							
		  							}
		  						}
		  				
		                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
		                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
		                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
		            } else {  
		                System.err.println("支付失败,错误信息：" + packageParams.get("err_code"));
		                System.err.println();
		                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
		                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";  
		            }  
		            //------------------------------  
		            //处理业务完毕  
		            //------------------------------  
		            BufferedOutputStream out = new BufferedOutputStream(  
		            		httpResponse.getOutputStream());  
		            out.write(resXml.getBytes());  
		            out.flush();  
		            out.close();  
		        } else{  
		            System.err.println("通知签名验证失败");
		        }  
		    
		}
	
			
		//微信支付 单个订单 ,用来跳转页面
		public Order wxpayyzOrder(Long  orderNumber) throws IOException {
			Order o=smallcontrolDao.WxpayyzOrder(orderNumber);
			return o;
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
	public Map<String, Object> getPrice(String token ,Integer type,Integer month,Integer gartenId) {
		 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
		  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=workerInfo){//验证用户
			BigDecimal big1=null;//每种类型的支付分别是什么价格
			BigDecimal big2=null;//0幼儿园本身视频1幼儿园本身考勤2幼儿园家长视频3幼儿园家长考勤4家长视频5家长考勤6:2+3
			BigDecimal big3=null;//目前type只有2+3+6 帮家长买的3种支付
			BigDecimal big4=null;
			BigDecimal big5=null;
			BigDecimal big0=null;
			GartenInfo garten=workerDao.findGartenInfoById( gartenId);
			List<GartenCharge> gartenCharges=new ArrayList<GartenCharge>();
			GartenCharge gartenCharge=new GartenCharge();
			//MyUtil.putMapParams(result,"state", 1,"info",gartenCharges);
			System.err.println("测试type5");
			for(int i=0;i<6;i++){
				 gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams("province",garten.getProvince(),"city",garten.getCity(),"countries",garten.getCountries(),"gartenId", garten.getGartenId(), "type",i));//属于该幼儿园本身的收费
				 System.err.println("测试type4"+gartenCharge);
				 if(null!=gartenCharge){
					System.err.println("测试type4"+i);
					gartenCharges.add(gartenCharge);
					if(5==i){
						big5=MyUtil.getRealPrice(gartenCharge,month);
					}else if(4==i){
						big4=MyUtil.getRealPrice(gartenCharge,month);
					}else if(3==i){
						big3=MyUtil.getRealPrice(gartenCharge,month);
					}else if(2==i){
						big2=MyUtil.getRealPrice(gartenCharge,month);
					}else if(1==i){
						big1=MyUtil.getRealPrice(gartenCharge,month);
					}else if(0==i){
						big0=MyUtil.getRealPrice(gartenCharge,month);
					}
				}else{
					gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams("province",garten.getProvince(),"city",garten.getCity(),"countries",garten.getCountries(),"gartenId", 0, "type",i));//属于该幼儿园所在区的收费
					 	if(null!=gartenCharge){
					 		gartenCharges.add(gartenCharge);
					 		System.err.println("测试type3"+i);
							if(5==i){
								big5=MyUtil.getRealPrice(gartenCharge,month);
							}else if(4==i){
								big4=MyUtil.getRealPrice(gartenCharge,month);
							}else if(3==i){
								big3=MyUtil.getRealPrice(gartenCharge,month);
							}else if(2==i){
								big2=MyUtil.getRealPrice(gartenCharge,month);
							}else if(1==i){
								big1=MyUtil.getRealPrice(gartenCharge,month);
							}else if(0==i){
								big0=MyUtil.getRealPrice(gartenCharge,month);
							}
					 	}else{
					 		gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams("province",garten.getProvince(),"city",garten.getCity(),"countries","","gartenId", 0, "type",i));//属于该幼儿园所在市的收费
							 if(null!=gartenCharge){
								 System.err.println("测试type2"+i);
								 gartenCharges.add(gartenCharge);
									if(5==i){
										big5=MyUtil.getRealPrice(gartenCharge,month);
									}else if(4==i){
										big4=MyUtil.getRealPrice(gartenCharge,month);
									}else if(3==i){
										big3=MyUtil.getRealPrice(gartenCharge,month);
									}else if(2==i){
										big2=MyUtil.getRealPrice(gartenCharge,month);
									}else if(1==i){
										big1=MyUtil.getRealPrice(gartenCharge,month);
									}else if(0==i){
										big0=MyUtil.getRealPrice(gartenCharge,month);
									}
							 	}else{
							 		gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams("province",garten.getProvince(),"city","","countries","","gartenId", 0, "type",i));//属于该幼儿园所在省的收费
									 if(null!=gartenCharge){
										 System.err.println("测试type1"+i);
										 gartenCharges.add(gartenCharge);
											if(5==i){
												big4=MyUtil.getRealPrice(gartenCharge,month);
											}else if(5==i){
												big4=MyUtil.getRealPrice(gartenCharge,month);
											}else if(3==i){
												big3=MyUtil.getRealPrice(gartenCharge,month);
											}else if(2==i){
												big2=MyUtil.getRealPrice(gartenCharge,month);
											}else if(1==i){
												big1=MyUtil.getRealPrice(gartenCharge,month);
											}else if(0==i){
												big0=MyUtil.getRealPrice(gartenCharge,month);
											}
									 	}else{//按照全国的标准
											//没找到发送反馈	parentDao.insertFeedback(new Feedback(garten.getGartenId(),"家长",new Date().getTime()/1000,parentInfo.getParentId(),garten.getProvince()+"没有"+(0==i?"视频":"考勤")+"收费标准",null,parentInfo.getParentName(),parentInfo.getPhoneNumber()));
									 		gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams( "province","","city","","countries","","gartenId", 0, "type",i));
									 		if(null==gartenCharge){
									 			return MyUtil.putMapParams("state", 1, "info",null,"price",null);		//没有收费标准
									 		}
									 		gartenCharges.add(gartenCharge);
									 		System.err.println("测试gartenCharge"+gartenCharge);
									 		System.err.println("测试type"+i);
											if(5==i){
												big5=MyUtil.getRealPrice(gartenCharge,month);
											}else if(4==i){
												big4=MyUtil.getRealPrice(gartenCharge,month);
											}else if(3==i){
												big3=MyUtil.getRealPrice(gartenCharge,month);
											}else if(2==i){
												big2=MyUtil.getRealPrice(gartenCharge,month);
											}else if(1==i){
												big1=MyUtil.getRealPrice(gartenCharge,month);
											}else if(0==i){
												big0=MyUtil.getRealPrice(gartenCharge,month);
										 		System.err.println("big0"+big0);

											}
									 	}
							 	}
					 	}
				}
			}
			if(0==type){
				MyUtil.putMapParams(result,"state", 1, "info",gartenCharges.get(0),"price",big0);
			}else if(1==type){
				MyUtil.putMapParams(result,"state", 1, "info",gartenCharges.get(0),"price",big1);
			}else if(2==type){
				MyUtil.putMapParams(result,"state", 1, "info",gartenCharges.get(0),"price",big2);
			}else if(3==type){
				MyUtil.putMapParams(result,"state", 1, "info",gartenCharges.get(0),"price",big3);
			}else if(4==type){
				MyUtil.putMapParams(result,"state", 1, "info",gartenCharges.get(0),"price",big4);
			}else if(5==type){
				MyUtil.putMapParams(result,"state", 1, "info",gartenCharges.get(0),"price",big5);
			}
		}
		return result;
	}


	public String alipayTest(String orderNumber) throws ParseException{
		//处理到期时间
		Order order=parentDao.findOrder(orderNumber);
		WorkerInfo workerInfo=workerDao.findWorkerInfoById(order.getId());
		GartenInfo gartenInfo=workerDao.findGartenInfoById(workerInfo.getGartenId());
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		Date day=new Date();
		Date date=new Date();
		Long monitorTime=gartenInfo.getMonitorTime();
		Long attendanceTime=gartenInfo.getAttendanceTime();
	    Calendar calendar = Calendar.getInstance();  
		Integer  orderType=order.getType();
				if(0==orderType||1==orderType){
					if(0==orderType){//幼儿园购买本身视频
						if(gartenInfo.getMonitorTime()>day.getTime()/1000){
							System.err.println("monitorTime前"+monitorTime);
							  calendar.setTime(sim.parse(sim.format(gartenInfo.getMonitorTime()*1000))); 
							  calendar.add(Calendar.MONTH, order.getMonthCount()); 
							monitorTime=calendar.getTime().getTime()/1000;  
							System.err.println("monitorTime后"+monitorTime);
						}else{
							calendar.setTime(day); 
							  calendar.add(Calendar.MONTH, order.getMonthCount()); 
							  monitorTime=calendar.getTime().getTime()/1000;  
						}
					}
					if(1==orderType){//幼儿园购买本身考勤
						if(gartenInfo.getAttendanceTime()>day.getTime()/1000){
							  calendar.setTime(sim.parse(sim.format(gartenInfo.getAttendanceTime()*1000))); 
							  calendar.add(Calendar.MONTH, order.getMonthCount()); 
							  attendanceTime=calendar.getTime().getTime()/1000;  
						}else{
							calendar.setTime(day); 
							  calendar.add(Calendar.MONTH, order.getMonthCount()); 
							  attendanceTime=calendar.getTime().getTime()/1000;  
						}
					}
					System.err.println("monitorTime最终"+monitorTime);
					bigcontrolDao.updateGartenTime(MyUtil.putMapParams("monitorTime", monitorTime, "attendanceTime", attendanceTime,"gartenId",gartenInfo.getGartenId()));
				}else{
					 // 1找到这个幼儿园所有的宝宝的主要监护人+附加宝宝主键
					List<ParentInfoCharge>  parentInfoCharge=bigcontrolDao.findParentInfoCharge(gartenInfo.getGartenId());
				for(ParentInfoCharge p:parentInfoCharge){//为每个家长找到自己的主宝宝添加对应的时间
					String[] babyIds=p.getBabyId();
					for(int i=0;i<babyIds.length;i++){
						if(babyIds[i].equals(p.getMainBabyId()+"")){
							ParentInfo  parent=parentDao.findParentById(p.getParentId());
							String[] attendanceTimeParent=parent.getAttendanceTime();
							String[] monitorTimeParent=parent.getMonitorTime();
							System.err.println(LyUtils.StrChangeToStr(attendanceTimeParent));
							System.err.println(LyUtils.StrChangeToStr(attendanceTimeParent));
							System.err.println(LyUtils.StrChangeToStr(attendanceTimeParent));
							if(2==orderType||6==orderType){
								if(sim.parse(monitorTimeParent[i]).getTime()>day.getTime()){
									System.err.println(monitorTimeParent[i]+"前");
									calendar.setTime(sim.parse(monitorTimeParent[i])); 
									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
									  date = calendar.getTime();  
									  monitorTimeParent[i]=sim.format(date);
									System.err.println(monitorTimeParent[i]+"后");
								}else{
									System.err.println(monitorTimeParent[i]);
									calendar.setTime(day); 
									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
									  date = calendar.getTime();  
									  monitorTimeParent[i]=sim.format(date);	
										System.err.println(monitorTimeParent[i]+"后");
								}
							}
							if(3==orderType||6==orderType){
								if(sim.parse(attendanceTimeParent[i]).getTime()>day.getTime()){
									System.err.println(attendanceTimeParent[i]+"前");
									  calendar.setTime(sim.parse(attendanceTimeParent[i])); 
									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
									  date = calendar.getTime();  
									  attendanceTimeParent[i]=sim.format(date);	
									  System.err.println(attendanceTimeParent[i]+"后");
								}else{
									System.err.println(attendanceTimeParent[i]+"前");
									calendar.setTime(day); 
									  calendar.add(Calendar.MONTH, order.getMonthCount()); 
									  date = calendar.getTime();  
									  attendanceTimeParent[i]=sim.format(date);
									  System.err.println(attendanceTimeParent[i]+"后");
								}
							}
							String attendanceTimeStr=MyUtil.changeArrayToString(attendanceTimeParent);
							String monitorTimeStr=MyUtil.changeArrayToString(monitorTimeParent);
							System.err.println("attendanceTimeStr"+attendanceTimeStr);
							System.err.println("monitorTimeStr"+monitorTimeStr);
							System.err.println("parentId"+p.getParentId());
							parentDao.updateChargeTime(MyUtil.putMapParams("monitorTime", monitorTimeStr, "attendanceTime", attendanceTimeStr,"parentId",p.getParentId()));
							}
						}
					
					}
				}
		
		return "success";
   	}
	
	public void addrecipeNotify(Integer gartenId) throws APIConnectionException, APIRequestException{
		List<ParentInfoCharge>  parentInfoCharge=bigcontrolDao.findParentInfoCharge(gartenId);
		for(ParentInfoCharge p:parentInfoCharge){
			MyUtil.pushOne(MyParamAll.JIGUANG_PARENT_APP, MyParamAll.JIGUANG_PARENT_MASTER,MyParamAll.JIGUANG_RECIPE_MESSAGE,p.getPhoneNumber());
		}
	}
	
	public void deleteBabyById(Integer babyId){
		smallcontrolDao.deleteBaby(babyId);						//删除该宝宝
		smallcontrolDao.deleteActivity(babyId);					//删除该宝宝活动记录
		smallcontrolDao.deleteBabyLeaveLog(babyId);				//删除宝宝考勤记录
		smallcontrolDao.deleteBabyPerformanceLog(babyId);		//删除宝宝表现
		smallcontrolDao.deleteAttendanceNo(babyId);				//删除宝宝attendanceNo
		smallcontrolDao.deleteDaijieInfo(babyId);				//删除代接
		smallcontrolDao.deleteDakaLog(babyId);					//删除宝宝打卡记录
		smallcontrolDao.deleteGartenPhotos(babyId);				//删除朋友圈照片
		smallcontrolDao.deleteUnusual(babyId);					//删除宝宝考勤异常记录
	}
	
	public void deleteTeacherById(Integer workerId){
		smallcontrolDao.deleteAttendanceNo(workerId);			//删除attendancenNo
		smallcontrolDao.deleteComment("老师",workerId);			//删除comment
		smallcontrolDao.deleteDakaLog(workerId);				//删除打卡记录
		smallcontrolDao.deleteFeedback("老师",workerId);			//删除反馈
		smallcontrolDao.deleteInfoLog("老师",workerId);			//删除老师通知记录
		smallcontrolDao.deletePhotoDianzan("老师",workerId);		//删除朋友圈点赞记录
		smallcontrolDao.deleteWorkerCheckLog(workerId); 		//删除老师考勤记录
		smallcontrolDao.deleteWorkerFlower(workerId);			//删除老师红花数
		smallcontrolDao.deleteUnusual(workerId);				//删除老师异常考勤记录
		smallcontrolDao.deleteWorkerPhoto(workerId);			//删除老师朋友圈照片
	}
	
	public void deleteParentById(Integer parentId){
		smallcontrolDao.deleteParent(parentId);				//删除该家长
		smallcontrolDao.deleteComment("家长", parentId);		//删除家长comment
		smallcontrolDao.deleteDaijieInfo(parentId);			//删除家长代接记录
		smallcontrolDao.deleteFeedback("家长", parentId);		//删除家长反馈
		smallcontrolDao.deleteGartenPhotos(parentId);		//删除家长朋友圈图片
		smallcontrolDao.deleteInfoLog("家长", parentId);		//删除家长信息通知
		smallcontrolDao.deletePhotoDianzan("家长", parentId);	//删除家长点赞记录
		smallcontrolDao.deleteParentFlower(parentId);		//删除该家长红花记录
	}
	
	/**
	 * 1 获取宝宝信息 获取家长信息
	 * 2 获取宝宝的主监护人 获取到原监护人信息
	 * 3 替换主监护人 
	 */
	public Map<String, Object> updateMainParent(Integer babyId, Integer parentId) {
		Map<String,Object> result=MyUtil.putMapParams("state",0);
		if(null!=babyId&&null!=parentId){
			ParentInfo parentInfo = parentDao.findParentById(parentId);
			String[] babys = parentInfo.getBabyId();
			String parentRelation=null;
			for(int i=0 ;i<babys.length;i++){
				if(babyId.equals(Integer.parseInt(babys[i]))){
					parentRelation=parentInfo.getIdentity()[i];
				}
			}
			Map<String,Object> param=MyUtil.putMapParams("babyId",babyId,"newparentId",parentId,"parentRelation",parentRelation);
			smallcontrolDao.updateMainParent(param);//3
			MyUtil.putMapParams(result,"state",1);
		}
		
		return result;
	}
	public Map<String, Object> getminorParent(Integer babyId) {
		
		Map<String,Object> result=MyUtil.putMapParams("state",0,"info",null);
		if(null!=babyId&&0!=babyId){
			BabyInfo baby=parentDao.findBabyShortByBabyId(babyId);
			Map<String,Object>param =MyUtil.putMapParams("babyId",babyId,"parentId",baby.getParentId());
			List<ParentInfo> parent=parentDao.getminorParent(param);
			MyUtil.putMapParams(result,"state",1,"info",parent);
		}
		
		
		return result;
	}
	
	public void sendNotifySmall(Integer classId,Integer gradeId ,Integer type ,String title,String content,WorkerInfo workerInfo){
		//----------------------------------新增发送历史记录---------------
		String targetName="";//拼接目标人群
		String toclass="";
		String leadClass=null;
		String leadGrade=null;
		GartenGrade grade = smallcontrolDao.findGartenGradeByGradeId(gradeId);
		GartenClass clazz = smallcontrolDao.findGartenClassByClassId(classId);
		if(null!=grade){
			toclass+=grade.getLeadGrade();
			leadGrade=grade.getLeadGrade();
			if(null!=clazz){
				toclass+=clazz.getLeadClass();
				leadGrade=clazz.getLeadClass();
			}
		}
		targetName+=0==type?"老师和家长":(2==type?"家长":"老师");
		GartenInfo g=workerDao.findGartenInfoById(workerInfo.getGartenId());
		String gartenName="";
		if(null!=g){
			gartenName+=g.getGartenName();
		}
		MessageLog ml=new MessageLog(new Date().getTime()/1000,targetName,content,null,workerInfo.getWorkerName(),workerInfo.getGartenId().toString(),title,gartenName,gartenName,workerInfo.getWorkerId(),"园长",toclass);
		smallcontrolDao.insertMessageLog(ml);
			List<WorkerInfo> workers=new ArrayList<WorkerInfo>();//需要发的老师
			List<ParentInfoCharge> parents=new ArrayList<ParentInfoCharge>();//需要发的家长
			List<InfoLog> infoLogs=new ArrayList<InfoLog>();
			Map<String,Object> param=MyUtil.putMapParams("leadClass", leadClass, "leadGrade", leadGrade,"gartenId",workerInfo.getGartenId());
			if(2==type||0==type){//2给家长发  找个某个班级的宝宝的主要监护人
				parents=smallcontrolDao.findParentInfoCharge(param);
			}
			if(3==type||0==type){//3给老师发
				workers=workerDao.findWorkerInfoNotified(param);
			}//建立通知记录  并发送通知

		System.err.println("老师开始发");	
		for(WorkerInfo w:workers){
				infoLogs.add(new InfoLog(w.getGartenId(),content,null,"老师",w.getWorkerId(),null,null,title,0));
				try {
					bigcontrolService.pushOne(MyParamAll.JIGUANG_WORKER_APP,MyParamAll.JIGUANG_WORKER_MASTER,content,w.getPhoneNumber());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		System.err.println("家长开始发");	
			for(ParentInfo p:parents){
				infoLogs.add(new InfoLog(Integer.valueOf(p.getGartenId().split(",")[0]),content,null,"家长",p.getParentId(),null,null,title,0));
				try {
					bigcontrolService.pushOne(MyParamAll.JIGUANG_PARENT_APP,MyParamAll.JIGUANG_PARENT_MASTER,content,p.getPhoneNumber());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for(InfoLog infoLog:infoLogs){//建立通知记录
				bigcontrolDao.insertInfoLog(infoLog);
			}
	}
	
	
	//type 0给老师家长发 2给家长发 3给老师发
		public Map<String, Object> messagelog(String token,Long start,Long end, Integer pageNo)  {
			 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=workerInfo){
					Map<String,Object> param=MyUtil.putMapParams("gartenId",workerInfo.getGartenId(),"start",start,"end",end);
					List<MessageLog> messagelog=smallcontrolDao.findMessageLog(param);
					Collections.sort( messagelog,new Comparator<Object>() {

						@Override
						public int compare(Object o1, Object o2) {
							MessageLog m1= (MessageLog)o1;
							MessageLog m2= (MessageLog)o2;
							return m2.getRegistTime().compareTo(m1.getRegistTime());	
						}
					});
					MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(messagelog, pageNo));
				}
				return result;
		}

		public Map<String,Object> teacherMessage(String token,Long startTime , Long endTime,Integer pageNo, Integer state){
			WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=workerInfo){
					Map<String, Object> params = MyUtil.putMapParams("gartenId",workerInfo.getGartenId(), "startTime", startTime, "endTime", endTime,"state",state);
					List<WorkerNameMessage> list = smallcontrolDao.findWorkerApplyMessage(params);
					MyUtil.putMapParams(result, "state", 1, "info",MyPage.listPage16(list, pageNo));
					
				}
				return result;
	   	}
		
		public Map<String,Object> agreeMessage(String token,Integer messageId){
			WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=workerInfo){
					WorkerMessageLog messageLog = smallcontrolDao.findMessageById(messageId);
					SmallcontrolSendNotify notify = new SmallcontrolSendNotify(messageLog.getGartenClass().getClassId(), messageLog.getGartenClass().getGradeId(), 2, messageLog.getTitle(), messageLog.getInfo(), workerInfo);
					Thread thread = new Thread(notify);
					thread.start();
					
					smallcontrolDao.updateTeacherMessageState(messageId);
					MyUtil.putMapParams(result, "state", 1 );
				}
				return result;
	   	}
			
		public  Map<String,Object> VisitCount(String token){
			 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=workerInfo){
					long current = System.currentTimeMillis();
		            long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
					Map<String, Object> params = MyUtil.putMapParams("gartenId",workerInfo.getGartenId(),"time",zero/1000);
		            List<VisitCount> visitCount = smallcontrolDao.findVisitCount(params);
		            MyUtil.putMapParams(result,"state", 1,"info",visitCount);
				}
				return result;
	   	}
		
		/*public Map<String,Object> workerLogin(String phoneNumber, String pwd){
			
			Map<String, Object> params = MyUtil.putMapParams("phoneNumber", phoneNumber,"pwd",CryptographyUtil.md5(pwd, "lxc"));
			WorkerInfo workerInfo = workerDao.findWorkerByPwd(params);
			Map<String, Object> result = MyUtil.putMapParams("state", 0);
			if(null!=workerInfo){
				String gartenName = smallcontrolDao.getGartenNameById(workerInfo.getGartenId());
				MyUtil.putMapParams(result, "state", 1, "info", workerInfo,"gartenName",gartenName);
					
			}
			return result;
		}*/
		
		public Map<String,Object> applySendMessage(String token,String title, String info,Integer[] classIds){
			Map<String, Object> result = MyUtil.putMapParams("state", 0);
			WorkerInfo workerInfo = workerDao.findWorkerInfoByToken(token);		//根据token获取老师信息
			if(null!=workerInfo){	
				for(int i=0;i<classIds.length;i++){
					Map<String, Object> params = MyUtil.putMapParams("title", title,"info",info,"workerId",workerInfo.getWorkerId(),"gartenId",workerInfo.getGartenId(),"classId",classIds[i]);
					smallcontrolDao.addWorkerMessage(params);
					List<WorkerNameMessage> list = smallcontrolDao.findMessageMore(workerInfo.getWorkerId());
					if(list.size()>100){		//申请大于一百条  删除最前面一条数据
						Integer messageId = smallcontrolDao.findMostEarlyApply(workerInfo.getWorkerId());
						smallcontrolDao.deleteApplyMessage(messageId);
					}
				}
					
				MyUtil.putMapParams(result, "state", 1);
			}
			return result;
		}
		
		
		public Map<String,Object> cancelApplyMessage(String token,Integer messageId){
			Map<String, Object> result = MyUtil.putMapParams("state", 0);
			WorkerInfo workerInfo = workerDao.findWorkerInfoByToken(token);		//根据token获取老师信息
			if(null!=workerInfo){	
				smallcontrolDao.deleteApplyMessage(messageId);
				MyUtil.putMapParams(result, "state", 1);
			}
			return result;
		}
		
		public Map<String,Object> applyMessageList(String token,Integer pageNo){
			Map<String, Object> result = MyUtil.putMapParams("state", 0);
			WorkerInfo workerInfo = workerDao.findWorkerInfoByToken(token);		//根据token获取老师信息
			if(null!=workerInfo){	
				List<WorkerMessageLog> list = smallcontrolDao.findApplyMessage(workerInfo.getWorkerId());
				return MyUtil.putMapParams(result, "state", 1, "info", MyPage.listPage16(list, pageNo));
			}
			return result;
		}
		public Map<String, Object> feedback(String token, String content) {
			WorkerInfo workerInfo= smallcontrolDao.findUnknownInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=workerInfo){//验证用户
				Map<String,Object> param=MyUtil.putMapParams("token", token, "content", content,"gartenId",workerInfo.getGartenId(),"job",workerInfo.getJob(),"name",workerInfo.getWorkerName());
				MyUtil.putMapParams(param,"jobId", workerInfo.getWorkerId(),"phoneNumber", workerInfo.getPhoneNumber());
				 workerDao.createFeadbackByToken(param);
				 result.put("state", 1);
			}
			return result;
		}

		public Map<String, Object> ActivityLogAll(Integer activityId,Integer pageNo) {
			List<com.garten.model.activity.ActivityLogAll> ac=smallcontrolDao.ActivityLogAllByactivityId(activityId);
			Map<String,Object> result=MyUtil.putMapParams("state", 1,"info",MyPage.listPage16(ac, pageNo));
			return result;
		}
		public Map<String, Object> findActivity(String token,Integer pageNo) {
			 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=workerInfo){
					List<ActivityDetailAll> activituLog= workerDao.findIntroduceActivityByToken(workerInfo.getGartenId());//这个幼儿园所有的活动
					 //这个幼儿园的活动
					MyUtil.putMapParams(result,"state",1,"info", MyPage.listPage16(activituLog, pageNo));
				}
				return result;
		}
	
		//------------------------------------查看打卡记录-------------------------------------
			public Map<String, Object> findDakalog(String token, Long startTime, Long endTime, String jobName,
					Integer pageNo,String job) {
				 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
				  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null,"count",null,"pageCount",null);
					if(null!=workerInfo){
						String babyName="宝宝".equals(job)?jobName:null;
						String workerName="老师".equals(job)?jobName:null;
						Map<String,Object> param=MyUtil.putMapParams("startTime", startTime,"endTime",endTime,"babyName",babyName,"gartenId",workerInfo.getGartenId(),"workerName",workerName,"pageNoStart",(pageNo-1)*16);
						List<DakalogAll> d=smallcontrolDao.findDakalog(param);
						d=MyUtil.setFindDakalog(d);
						Integer count=smallcontrolDao.findDakalogCount(param);
						Integer pageCount=new DividePage( 16,  count, 1).getPageCount();
						MyUtil.putMapParams(result,"state", 1,"info",d,"count",count,"pageCount",pageCount);
					}
					
					
				return result;
			}
			public List<GartenClass> findGartenClassByClassIdStr(String classId) {
				List<GartenClass> gc=smallcontrolDao.findGartenClassByClassIdStr(classId);
				return gc;
			}
			public Map<String, Object> findGartenGrade(Integer gartenId) {
				Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				List<GartenGrade> d=smallcontrolDao.findGartenGradeByGartenId(gartenId);
				MyUtil.putMapParams(result,"state", 1,"info",d);
				return result;
			}
			public Map<String, Object> addGartenGrade(String token, String leadGrade, String mark,Integer no) {
				 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
				  Map<String,Object> result=MyUtil.putMapParams("state", 0);
					if(null!=workerInfo){
						Map<String,Object> param=MyUtil.putMapParams("gartenId",workerInfo.getGartenId(),"leadGrade",leadGrade,"mark",mark,"no",no);
						//年级名字不重复才能加
						MyUtil.putMapParams(result,"state",4);
						if(isAddGartenGrade(workerInfo.getGartenId(),leadGrade,no,null)){
							smallcontrolDao.addGartenGrade(param);
							MyUtil.putMapParams(result,"state", 1);
						}
					}
				return result;
			}
			
			private boolean isAddGartenGrade(Integer gartenId, String leadGrade,Integer no,Integer gradeId) {
				Map<String,Object> param=MyUtil.putMapParams("gartenId",gartenId,"leadGrade",leadGrade,"no",no,"gradeId",gradeId);
				List<GartenGrade> d=smallcontrolDao.findGartenGrade(param);
				return 0==d.size()?true:false;
			}
			public Map<String, Object> deleteGartenGrade(String token, Integer gradeId) {
				 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
				  Map<String,Object> result=MyUtil.putMapParams("state", 0);
					if(null!=workerInfo){
						MyUtil.putMapParams(result,"state", 3);
						if(isDeleteGartenGrade(gradeId)){
							smallcontrolDao.deleteGartenGrade(gradeId);
							MyUtil.putMapParams(result,"state", 1);
						}
						
					}
				return result;
			}
			public Map<String, Object> updateGartenGrade(String token, Integer gradeId,String leadGrade,String mark) {
				 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
				  Map<String,Object> result=MyUtil.putMapParams("state", 0);
					if(null!=workerInfo){
						GartenGrade gg=smallcontrolDao.findGartenGradeByGradeId(gradeId);
						MyUtil.putMapParams(result,"state", 3);
						if(isAddGartenGrade(gg.getGartenId(),leadGrade,null,gradeId)){
							Map<String,Object> param=MyUtil.putMapParams("gradeId", gradeId,"leadGrade",leadGrade,"mark",mark);
							smallcontrolDao.updateGartenGrade(param);//修改年级名[班级表,年级表]
							MyUtil.putMapParams(result,"state", 1);
						}
						
					}
				return result;
			}
			/**
			 * 是否可以删除年级  true可以删
			 * @param gradeId 年级主键
			 * @return
			 */
			
			private Boolean isDeleteGartenGrade(Integer gradeId) {
				  Map<String,Object> param=MyUtil.putMapParams("gradeId", gradeId);
				List<GartenClass> gg=smallcontrolDao.findGartenClass(param);
				return gg.size()==0?true:false;
			}
			
			public Map<String, Object> findGartenClass(Integer gartenId,Integer gradeId) {
				Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				List<GartenClass> d=smallcontrolDao.findGartenClassByGartenId(gartenId,gradeId);
				MyUtil.putMapParams(result,"state", 1,"info",d);
				return result;
			}
			public Map<String, Object> addGartenClass(String token, Integer gradeId, String mark,String leadClass) {
				 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
				  Map<String,Object> result=MyUtil.putMapParams("state", 0);
					if(null!=workerInfo){
						Map<String,Object> param=MyUtil.putMapParams("gartenId",workerInfo.getGartenId(),"gradeId",gradeId,"mark",mark,"leadClass",leadClass);
						//班级名字,序号不重复才能加
						MyUtil.putMapParams(result,"state", 3);
						if(isAddGartenClass(gradeId,leadClass,0)){
							//需要查到年级名字
							GartenGrade gg=smallcontrolDao.findGartenGradeByGradeId(gradeId);
							param.put("leadGrade", gg.getLeadGrade());
							smallcontrolDao.addGartenClass(param);
							MyUtil.putMapParams(result,"state", 1);
						}
						
					}
				return result;
			}
			
			public Map<String, Object> updateGartenClass(String token, Integer classId, Integer gradeId, String mark,String leadClass) {
				 WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
				  Map<String,Object> result=MyUtil.putMapParams("state", 0);
					if(null!=workerInfo){
						//班级名字不重复才能加
						MyUtil.putMapParams(result,"state", 3);
						if(isAddGartenClass(gradeId,leadClass,classId)){
							//需要查到年级名字
							GartenGrade gg=smallcontrolDao.findGartenGradeByGradeId(gradeId);
							Map<String,Object> param=MyUtil.putMapParams("gartenId",workerInfo.getGartenId(),"gradeId",gradeId,"mark",mark,"leadClass",leadClass,"leadGrade",gg.getLeadGrade(),"classId",classId);
							smallcontrolDao.updateGartenClass(param);
							MyUtil.putMapParams(result,"state", 1);
						}
						
					}
				return result;
			}
			/**
			 * true能添加
			 * @param gradeId 年级主键
			 * @param leadClass 班级名字
			 * @return
			 */
			private boolean isAddGartenClass(Integer gradeId,String leadClass,Integer classId) {
				Map<String,Object> param=MyUtil.putMapParams("gradeId",gradeId,"leadClass",leadClass,"classId",classId);
				List<GartenClass> gc=smallcontrolDao.findGartenClass(param);
				return gc.size()==0?true:false;
			}
			
			public Map<String, Object> deleteGartenClass(String token, Integer classId) {
				WorkerInfo workerInfo=smallcontrolDao.findWorkerByToken(token);//根据账号查找到用户,手机号
				  Map<String,Object> result=MyUtil.putMapParams("state", 0);
					if(null!=workerInfo){
						MyUtil.putMapParams(result,"state", 3);
						if(isDeleteGartenClass(classId)){//是否能删除
							smallcontrolDao.deleteGartenClass(classId);
							MyUtil.putMapParams(result,"state", 1);
						}
						
					}
				return result;
			}
			private boolean isDeleteGartenClass(Integer classId) {
				Integer babyCount=smallcontrolDao.findBabyByClassId(classId);
				Integer workerCount=smallcontrolDao.findWorkerByClassId(classId);
				return (babyCount+workerCount)==0?true:false;
			}
}
