package com.garten.service;



import java.io.File;
import java.io.IOException;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.garten.Thread.BigControlSendNotify;
import com.garten.Thread.DeleteGartenAll;
import com.garten.Thread.DeleteGartenBaby;
import com.garten.Thread.DeleteGartenTeacher;
import com.garten.Thread.GartenRegisteNotify;
import com.garten.Thread.XxxThread;
import com.garten.dao.AgentDao;
import com.garten.dao.AttendanceDao;
import com.garten.dao.BigcontrolDao;
import com.garten.dao.ParentDao;
import com.garten.dao.PrincipalDao;
import com.garten.dao.SmallcontrolDao;
import com.garten.dao.WorkerDao;
import com.garten.model.agent.AgentAudit;
import com.garten.model.agent.AgentAuditDetail;
import com.garten.model.agent.AgentInfo;
import com.garten.model.agent.AgentOrderAll;
import com.garten.model.agent.SaleServiceAll;
import com.garten.model.agent.Withdraw;
import com.garten.model.agent.WithdrawAll;
import com.garten.model.agent.WuliaoOrder;
import com.garten.model.baby.BabyInfo;
import com.garten.model.company.CpActivity;
import com.garten.model.company.Department;
import com.garten.model.company.Employee;
import com.garten.model.company.Jobs;
import com.garten.model.company.Report;
import com.garten.model.company.ReportAll;
import com.garten.model.garten.GartenCharge;
import com.garten.model.garten.GartenClass;
import com.garten.model.garten.GartenInfo;
import com.garten.model.garten.GartenType;
import com.garten.model.other.AttendanceNo;
import com.garten.model.other.Card;
import com.garten.model.other.CardReturn;
import com.garten.model.other.Equipment;
import com.garten.model.other.EquipmentName;
import com.garten.model.other.InfoLog;
import com.garten.model.other.MessageLog;
import com.garten.model.other.Order;
import com.garten.model.parent.ParentInfo;
import com.garten.model.parent.Relation;
import com.garten.model.worker.WorkerInfo;
import com.garten.util.LyParam;
import com.garten.util.LyUtils;
import com.garten.util.excel.ExcelUtil;
import com.garten.util.excel.ExcelUtils;
import com.garten.util.lxcutil.MyParamAll;
import com.garten.util.md5.CryptographyUtil;
import com.garten.util.myutil.MyUtil;
import com.garten.util.page.DividePage;
import com.garten.util.page.MyPage;
import com.garten.vo.agent.AgentAuditMessage;
import com.garten.vo.attendance.TeacherAndBabyInfo;
import com.garten.vo.baby.BabyMessageAndParent;
import com.garten.vo.bigcontrol.AddDetail;
import com.garten.vo.bigcontrol.CardSimple;
import com.garten.vo.bigcontrol.ClassManageBig;
import com.garten.vo.bigcontrol.DakaCamera;
import com.garten.vo.bigcontrol.FeedbackDetail;
import com.garten.vo.bigcontrol.GartenClassAll;
import com.garten.vo.bigcontrol.LiveCamera;
import com.garten.vo.bigcontrol.ParentMessage;
import com.garten.vo.bigcontrol.WorkerMessage;
import com.garten.vo.parent.ParentInfoCharge;
import com.garten.vo.smallcontrol.CardNoDetail;
import com.garten.vo.smallcontrol.MachineDetail;
import com.garten.vo.smallcontrol.OrderAll;
import com.garten.vo.teacher.BabyCheckLogAll;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;



@Service
public class BigcontrolService {
	
	@Autowired
	private BigcontrolDao bigcontrolDao;
	@Autowired
	private WorkerDao workerDao;
	@Autowired
	private ParentDao parentDao;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private ParentService parentService;
	@Autowired
	private PrincipalDao principalDao;
	@Autowired
	private SmallcontrolDao smallcontrolDao;
	@Autowired
	private AgentDao agentDao;
	@Autowired
	private AttendanceDao attendanceDao;
	@Autowired
	private SmallcontrolService smallcontrolService;
	public Map<String, Object> login(String phoneNumber, String pwd) {
		Map<String,Object> param=MyUtil.putMapParams("phoneNumber", phoneNumber,"pwd",pwd);
		Employee e=bigcontrolDao.findEmployeeByPwd(param);
		String uuid="error";
		Map<String,Object> result=MyUtil.putMapParams("state", 0, "info", uuid);
		//如果worker为空则返回error
		//如果worker不为空则返回uuid,并修改token为uuid
		if(null!=e&&!"".equals(e)){
			MyUtil.putMapParams(result,"state", 1, "info", e);
		}
		return result;
	}

	//验证短信  再修改密码
		public Map<String, Object> updateLogin(String phoneNumber, String pwd, String number) throws ParseException {
			 Employee e=bigcontrolDao.findEmployeeByPhoneNumber(phoneNumber);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info","没有这个用户");
				if(null!=e){
					Map<String,Object> duanxin=workerService.duanxin(e.getPhoneNumber(),3,number);//0代表 老师端短信
					String newToken= UUID.randomUUID().toString();
					MyUtil.putMapParams(result,"state", 2, "info", "验证码错误");
					if(duanxin.get("0").equals("成功")){
						bigcontrolDao.updatePwdToken(MyUtil.putMapParams("token", newToken, "phoneNumber", phoneNumber,"pwd",CryptographyUtil.md5(pwd, "lxc")));
						MyUtil.putMapParams(result,"state", 1, "info", newToken);
					}
				}
				//验证码错误返回  验证错误信息
				return result;
		}

		public Map<String, Object> addtongji(String token, Long start,Long end) {
			start=null==start?1:start;//时间选择是[全部]传过来是null
			end =null==end?new Date().getTime()/1000:end;
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"babyCount",null,"workerCount",null,"parentCount",null);
				if(null!=em){
					Integer babyCount=bigcontrolDao.addtongjiBaby(MyUtil.putMapParams("start", start, "end", end));
					Integer workerCount=bigcontrolDao.addtongjiWorker(MyUtil.putMapParams("start", start, "end", end));
					Integer parentCount=bigcontrolDao.addtongjiParent(MyUtil.putMapParams("start", start, "end", end));
					MyUtil.putMapParams(result,"state", 1,"babyCount",babyCount,"workerCount",workerCount,"parentCount",parentCount);
				}
				//验证码错误返回  验证错误信息
				return result;
		}
		
		
		public Map<String, Object> deletetongji(String token,Long start,Long end ) {
			start=null==start?1:start;//时间选择是[全部]传过来是null
			end =null==end?new Date().getTime()/1000:end;
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"babyCount",null,"workerCount",null,"parentCount",null);
				if(null!=em){
					Integer babyCount=bigcontrolDao.deletetongjiBaby(MyUtil.putMapParams("start", start, "end", end));
					System.err.println(babyCount);
					Integer workerCount=bigcontrolDao.deletetongjiWorker(MyUtil.putMapParams("start", start, "end", end));
					Integer parentCount=bigcontrolDao.deletetongjiParent(MyUtil.putMapParams("start", start, "end", end));
					MyUtil.putMapParams(result,"state", 1,"babyCount",babyCount,"workerCount",workerCount,"parentCount",parentCount);
					
				}
				//验证码错误返回  验证错误信息
				return result;
				
				
			
		}
		/**
		 * 1查询并遍历所有幼儿园
		 * 2查询每个幼儿园的忽略表(找到哪天需要晨检)
		 * 3这天需要晨检:找到这个幼儿园今天的所有宝贝的晨检记录
		 * 				体温 1:>37&!=0异常
		 * 					2:<36&!=0异常
		 * 					3:==0异常
		 * :应晨检人数,实际晨检人数,缺捡人数,过高人数,过小人数
		 * @throws ParseException 
		 */
		public Map<String, Object> checktongji(String token, Long time) throws ParseException {
			time=null==time?1:time;//时间选择是[全部]传过来是null
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<GartenInfo> gartens=bigcontrolDao.findGartens();
					Map<String,Object> tongji=MyUtil.getChecktongji(gartens,time);
					MyUtil.putMapParams(result,"state", 1,"info",tongji);
				}
				//验证码错误返回  验证错误信息
				return result;
		}
		
		public  Set<Long> findCheckLong(Integer gartenId){
			Set<Long> lons=bigcontrolDao.findCheckLong(gartenId);
			return lons;
		}


		public Map<String, Object> adddetail(String token,Long start,Long end,String province,String city,String countries,String job,Integer pageNo,Integer gartenId ) {
			start=null==start?1:start;//时间选择是[全部]传过来是null
			end =null==end?new Date().getTime()/1000:end;
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){//可选参数省市区
					List<AddDetail>  babyCount=bigcontrolDao.adddetailBaby(MyUtil.putMapParams("start", start,"end",end,"province",province,"city",city,"countries",countries,"gartenId",gartenId));
					List<AddDetail> workerCount=bigcontrolDao.adddetailWorker(MyUtil.putMapParams("start", start,"end",end,"province",province,"city",city,"countries",countries,"gartenId",gartenId));
					List<AddDetail> parentCount=bigcontrolDao.adddetailParent(MyUtil.putMapParams("start", start,"end",end,"province",province,"city",city,"countries",countries,"gartenId",gartenId));
					List<AddDetail> addDetails=new ArrayList();
					//有选择job就要搜索出对应的Job对象
					if(!"".equals(job)&&null!=job){//job不是选择[全部]的情况下
						if("宝宝".equals(job)){
							addDetails=babyCount;
						}else if("老师".equals(job)){
							addDetails=workerCount;
						}else if("园长".equals(job)){
							addDetails=parentCount;
						}
					}else{
						addDetails.addAll(babyCount);
						addDetails.addAll(workerCount);
						addDetails.addAll(parentCount);
					}
					Collections.sort( addDetails,new Comparator<Object>() {

						@Override
						public int compare(Object o1, Object o2) {
							AddDetail m1= (AddDetail)o1;
							AddDetail m2= (AddDetail)o2;
							return m2.getRegistTime().compareTo(m1.getRegistTime());	
						}
					});
					MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(addDetails, pageNo));
				}
				//验证码错误返回  验证错误信息
				return result;
		}

		public Map<String, Object> deletedetail(String token,Long start,Long end,String province,String city,String countries,String job,Integer pageNo,Integer gartenId ) {
			start=null==start?1:start;//时间选择是[全部]传过来是null
			end =null==end?new Date().getTime()/1000:end;
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){//可选参数省市区
					List<AddDetail>  babyCount=bigcontrolDao.deletedetailBaby(MyUtil.putMapParams("start", start,"end",end,"province",province,"city",city,"countries",countries,"gartenId",gartenId));
					List<AddDetail> workerCount=bigcontrolDao.deletedetailWorker(MyUtil.putMapParams("start", start,"end",end,"province",province,"city",city,"countries",countries,"gartenId",gartenId));
					List<AddDetail> parentCount=bigcontrolDao.deletedetailParent(MyUtil.putMapParams("start", start,"end",end,"province",province,"city",city,"countries",countries,"gartenId",gartenId));
					List<AddDetail> addDetails=new ArrayList();
					//有选择job就要搜索出对应的Job对象
					if(!"".equals(job)&&null!=job){//job不是选择[全部]的情况下
						if("宝宝".equals(job)){
							addDetails=babyCount;
						}else if("老师".equals(job)){
							addDetails=workerCount;
						}else if("园长".equals(job)){
							addDetails=parentCount;
						}
					}else{
						addDetails.addAll(babyCount);
						addDetails.addAll(workerCount);
						addDetails.addAll(parentCount);
					}
					Collections.sort( addDetails,new Comparator<Object>() {

						@Override
						public int compare(Object o1, Object o2) {
							AddDetail m1= (AddDetail)o1;
							AddDetail m2= (AddDetail)o2;
							return m2.getRegistTime().compareTo(m1.getRegistTime());	
						}
					});
					MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(addDetails, pageNo));
				}
				//验证码错误返回  验证错误信息
				return result;
		}

		public List<BabyCheckLogAll> findBabyCheckByToken(Map<String, Object> putMapParams) {
			List<BabyCheckLogAll> babyCheckLogs= bigcontrolDao.findBabyCheckByToken(putMapParams);//获取所有宝宝的晨检 考勤信息
			return babyCheckLogs;
		}

		public Map<String, Object> dakatongji(String token, Long time) {
			time=null==time?1:time;//时间选择是[全部]传过来是null
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<GartenInfo> gartens=bigcontrolDao.findGartens();
					Map<String,Object> tongji=MyUtil.getDakatongji(gartens,time);
					MyUtil.putMapParams(result,"state", 1,"info",tongji);
				}
				//验证码错误返回  验证错误信息
				return result;
		}
			
		public Map<String, Object> parentMessage(String token, String name, String phoneNumber,String province,String city,String countries,Integer pageNo,Integer gartenId, Integer monitorState, Integer attendanceState) throws ParseException {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					Long nowTime = new Date().getTime();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					
					//根据 可变参数 筛选出符合条件的家长
					Map<String, Object> params = MyUtil.putMapParams("name", name, "phoneNumber", phoneNumber, "province", province, "city", city, "countries", countries,"gartenId",gartenId,"pageNo",(pageNo-1)*16);
					List<ParentInfo> parents=bigcontrolDao.findParentMessage(params);
					Integer count = bigcontrolDao.findParentMessageCount(params);
					int pageCount = new DividePage(16, count, pageNo).getPageCount();
					List<ParentMessage>   parentMessages=new ArrayList<ParentMessage>();
					ClassManageBig classManageBig=new ClassManageBig();
					for(ParentInfo p:parents){
						System.err.println(p);
						Boolean attendanceFlag = false;
						Boolean monitorFlag = false;
						
						List<ClassManageBig>  classManageBigList=new ArrayList<ClassManageBig>();
						if("".equals(LyUtils.StrChangeToStr(p.getBabyId()))){
							continue;
						}
						String[]  babyIds=p.getBabyId();
						String[]  monitor=p.getMonitorTime();
						String[] attendance=p.getAttendanceTime();
						System.err.println(babyIds.length);
						System.err.println(babyIds[0]);
						for(int i=0;i<babyIds.length;i++){
							classManageBig=findBaby(Integer.valueOf(babyIds[i]));
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
						parentMessages.add(new ParentMessage(p,classManageBigList));
						
					}
					MyUtil.putMapParams(result,"state", 1,"info",parentMessages,"pageCount",pageCount,"count",count);
					
				}
				//验证码错误返回  验证错误信息
				return result;
		}
		
		
		public void exportParent(String name,String phoneNumber,String province,String city,String countries ,Integer gartenId,Integer monitorState,Integer attendanceState,HttpServletResponse response) throws IOException, Exception{
			Map<String, Object> params = MyUtil.putMapParams("name", name, "phoneNumber", phoneNumber, "province", province, "city", city, "countries", countries,"gartenId",gartenId);
			List<ParentInfo> parents=bigcontrolDao.findParentMessage(params);
			Long nowTime = new Date().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			for(ParentInfo p : parents){
				Boolean attendanceFlag = false;
				Boolean monitorFlag = false;
				for(int i=0; i<p.getBabyId().length ;i++){
					if(sdf.parse(p.getMonitorTime()[i]).getTime()>nowTime){
						monitorFlag=true;
					}
					if(sdf.parse(p.getAttendanceTime()[i]).getTime()>nowTime){
						attendanceFlag=true;
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
			}
			String [] titles= {"家长ID","家长名字","联系方式","地址"};
	   		String [] fields = {"parentid","parentname","phonenumber","address"};
	   		
	   		
	   		response.setContentType("application/x-execl");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("家长列表.xls".getBytes(), "ISO-8859-1"));
	   		
	   		
	   		ServletOutputStream outputStream = response.getOutputStream();
			ExcelUtils.Export_(titles, fields, parents, outputStream);
			
			
		}
		
		
		public Map<String, Object> babyMessage(String token, String name ,String province,String city,String countries,Integer pageNo,Integer gartenId,String leadGrade,String leadClass) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<BabyMessageAndParent> babyMessages=bigcontrolDao.findBabyMessage(MyUtil.putMapParams("name", name , "province", province, "city", city, "countries", countries,"gartenId",gartenId,"leadGrade",leadGrade,"leadClass",leadClass));
					MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(babyMessages, pageNo),"count",babyMessages.size());
				}
				//验证码错误返回  验证错误信息
				return result;
		}
		
		public  void exportBaby(String name ,String province,String city,String countries,Integer gartenId,String leadGrade,String leadClass,HttpServletResponse response) throws IOException{
			List<BabyMessageAndParent> babyMessages=bigcontrolDao.findBabyMessage(MyUtil.putMapParams("name", name , "province", province, "city", city, "countries", countries,"gartenId",gartenId,"leadGrade",leadGrade,"leadClass",leadClass));
			
			String [] titles= {"宝宝ID","宝宝名字","年级","班级","身高","体重","性别","健康状态","爱好","特长","过敏史","主监护人","监护人联系方式","身份证号码"};
	   		String [] fields = {"babyid","babyname","leadgrade","leadclass","height","weight","sex","health","hobby","specialty","allergy","parentname","phonenumber","cardid"};
	   		
	   		
	   		response.setContentType("application/x-execl");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("宝宝列表.xls".getBytes(), "ISO-8859-1"));
	   		
	   		
	   		ServletOutputStream outputStream = response.getOutputStream();
			ExcelUtils.Export_(titles, fields, babyMessages, outputStream);
			
		}
		
		
		
		public  ClassManageBig findBaby(Integer babyId){
			ClassManageBig baby=bigcontrolDao.findBabyByIdBig( babyId);
			return baby;
		}

		public Map<String, Object> workerMessage(String token, String name, String province, String city,
				String countries,String phoneNumber,Integer pageNo,Integer gartenId) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<WorkerMessage> workerMessage=bigcontrolDao.findWorkerMessage(MyUtil.putMapParams("name", name , "province", province, "city", city, "countries", countries,"phoneNumber",phoneNumber,"gartenId", gartenId));
					MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(workerMessage, pageNo),"count",workerMessage.size());
				}
				return result;
		}

		
		public  void exportWorker(String name ,String province,String city,String countries,String phoneNumber,Integer gartenId,HttpServletResponse response) throws IOException{
		
			List<WorkerMessage> workerMessage=bigcontrolDao.findWorkerMessage(MyUtil.putMapParams("name", name , "province", province, "city", city, "countries", countries,"phoneNumber",phoneNumber,"gartenId", gartenId));
			
			String [] titles= {"老师ID","老师名字","所属幼儿园","联系方式","年级","班级","性别","年龄","学历","普通话等级","证书","红花数量","职称"};
	   		String [] fields = {"workerid","workername","gartenname","phonenumber","leadgrade","leadclass","sex","age","education","chinese","certificate","flowers","jobcall"};
	   		
	   		
	   		response.setContentType("application/x-execl");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("老师列表.xls".getBytes(), "ISO-8859-1"));
	   		
	   		
	   		ServletOutputStream outputStream = response.getOutputStream();
			ExcelUtils.Export_(titles, fields, workerMessage, outputStream);
			
		}
		public Map<String, Object> gartenMessage(String token, String name, String province, String city,
				String countries, String phoneNumber,Integer pageNo,Integer gartenId,Integer monitorState,Integer attendanceState) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					Map<String,Object> param=MyUtil.putMapParams("name", name , "province", province, "city", city, "countries", countries,"phoneNumber",phoneNumber,"gartenId",gartenId);
					MyUtil.putMapParams(param,"monitorState",monitorState,"attendanceState",attendanceState);
					List<GartenInfo> gartenInfo=bigcontrolDao.findGartenMessage(param);
					MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(gartenInfo, pageNo));
				}
				return result;
		}

		public Map<String,Object> exportGarten(String token,String name ,String province,String city,String countries,String phoneNumber,Integer gartenId,HttpServletResponse response){
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					Map<String,Object> param=MyUtil.putMapParams("name", name , "province", province, "city", city, "countries", countries,"phoneNumber",phoneNumber,"gartenId",gartenId);
					
					List<GartenInfo> gartenInfo=bigcontrolDao.findGartenMessage(param);
					
					try {
						response.setContentType("application/x-execl");
						response.setHeader("Content-Disposition", "attachment;filename=" + new String("幼儿园信息列表.xls".getBytes(), "ISO-8859-1"));
						ServletOutputStream outputStream = response.getOutputStream();
						ExcelUtil.exportGarten(gartenInfo, outputStream);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return null;
		}
		public Map<String, Object> getGarten(String token,String province,String city,String countries) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<GartenInfo> gartenInfo=bigcontrolDao.findGartenMessage(MyUtil.putMapParams(  "province", province, "city", city, "countries", countries));
					MyUtil.putMapParams(result,"state", 1,"info",gartenInfo);
				}
				return result;
		}
		
		
		public Map<String, Object> getGartenGrade(Integer gartenId) {
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
						List<String> grades=new ArrayList<String>();
						List<GartenClassAll>  classesAlls=new ArrayList();
						grades=bigcontrolDao.findGartenGrade(gartenId);
						for(String grade:grades){
							List<GartenClass>  classes=new ArrayList();
							GartenClassAll  classesAll=new GartenClassAll();
							classes=bigcontrolDao.findGartenClasses(MyUtil.putMapParams("gartenId", gartenId, "leadGrade", grade));
							classesAll.setLeadGrade(grade);
							classesAll.setClasses(classes);
							classesAlls.add(classesAll);
						}
					
					MyUtil.putMapParams(result,"state", 1,"info",classesAlls);
				return result;
		}

		public Map<String, Object> updateGarten(String token, Integer gartenId, String gartenName, String name,
				String phoneNumber, String contractNumber, Long contractStart, Long contractEnd,
				 String province, String city, String countries, String address,
				Integer accountState, BigDecimal charge, Long attendanceTime, Long monitorTime) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
				if(null!=em){
					Map<String,Object> param=new HashMap<String,Object>();
					MyUtil.putMapParams( param, "gartenId", gartenId, "gartenName", gartenName, "name", name,"phoneNumber",phoneNumber,"contractNumber",contractNumber);
					MyUtil.putMapParams( param, "province", province, "city", city,"countries",countries,"address",address);
					MyUtil.putMapParams( param, "charge", charge, "attendanceTime", attendanceTime, "monitorTime", monitorTime,"contractEnd",contractEnd,"accountState",accountState);
					MyUtil.putMapParams( param,"contractStart",contractStart);
					WorkerInfo principal = smallcontrolDao.findPrincipalByGartenId(gartenId);
					if(!principal.getPhoneNumber().equals(phoneNumber)){
						WorkerInfo worker = principalDao.findPrincipalByAccount(phoneNumber);
						if(null!=worker){
							return MyUtil.putMapParams(result, "state", 2);			//手机号码已经存在
						}
						bigcontrolDao.updateAgentAuditPhone(principal.getPhoneNumber(),phoneNumber);	//合同填写号码修改
						Map<String, Object> params = MyUtil.putMapParams("workerId", principal.getWorkerId(),"phoneNumber", phoneNumber);
						workerDao.updateWorkerPhoneNumber(params);
						
					}
					bigcontrolDao.updateGarten(param);
					MyUtil.putMapParams(result,"state", 1);
				}
				return result;
		}

		public Map<String, Object> accountGarten(String token, Integer gartenId,Integer accountState) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
				if(null!=em){
					System.err.println(accountState);
					bigcontrolDao.updateGarten(MyUtil.putMapParams("accountState", accountState,"gartenId",gartenId));
					MyUtil.putMapParams(result,"state", 1);
				}
				return result;
		}

		public Map<String, Object> agentAudit(String token, Integer resource,Integer pageNo) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<AgentAuditMessage> agentAudit=bigcontrolDao.findAgentAudit(resource);
					if(0==resource){
						agentAudit = MyUtil.auditChange2Emloyee(agentAudit);
					}
					MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(agentAudit, pageNo));
				}
				return result;
		}		
//-----------------------------------------开园处理
		public Map<String, Object> agreeAgentAudit(String token ,Integer auditId,Integer gartenGrade,Long attendanceTime,Long monitorTime,Long contractStart,Long contractEnd) throws ParseException, IOException {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
				if(null!=em){
						bigcontrolDao.agreeAgentAudit(auditId);
						AgentAudit audit=bigcontrolDao.findAgentAuditOne(auditId);
						MyUtil.putMapParams(result,"state", 1);
						creation( audit.getPhoneNumber(), gartenGrade, audit.getGartenName(),
								 attendanceTime, monitorTime,audit.getResourceId(),
								 audit.getProvince(), audit.getCity(), audit.getCountries(), audit.getContractNumber(), contractStart, contractEnd,
								  audit.getName(),audit,1,audit.getGartenType());
//						String[] datas=new String[]{audit.getGartenName(),audit.getPhoneNumber(),"123456"};
//						MyUtil.register(audit.getPhoneNumber(),MyParamAll.YTX_DUANXIN_ZC, datas);
//						GartenRegisteNotify gartenRegisteNotify = new GartenRegisteNotify(audit.getPhoneNumber(), datas);
//						Thread thread = new Thread(gartenRegisteNotify);
//						thread.start();
//						GartenRegisterNotify(datas, audit.getGartenName());
				}
				return result;
		}
		
		public Map<String,Object> refuseAgentAudit(Integer auditId,String reason){
			Map<String, Object> params = MyUtil.putMapParams("auditId", auditId, "reason", reason);
			
			bigcontrolDao.refuseAgentAudit(params);
			return MyUtil.putMapParams("state", 1);
		}
		
		
		
		public Map<String, Object> addGarten(String token  ,Integer gartenGrade,Long attendanceTime,Long monitorTime,Long contractStart,Long contractEnd,String jobcall,String phoneNumber,
				String gartenName,String name,String contractNumber,String province,
				String city,String countries,Integer gartenType) throws ParseException, IOException {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
				if(null!=em){
					WorkerInfo worker =principalDao.findPrincipalByAccount(phoneNumber);
					if(null!=worker){
						return MyUtil.putMapParams(result,"state", 2);			//该幼儿园已经注册
					}
	
						creation(phoneNumber, gartenGrade, gartenName, 
								 attendanceTime, monitorTime, em.getEmployeeNo(),
								 province, city, countries,contractNumber, contractStart, contractEnd,
								  name, null,0,gartenType);
						
						String[] datas=new String[]{gartenName,phoneNumber,"123456"};
						//MyUtil.register(phoneNumber,MyParamAll.YTX_DUANXIN_ZC,datas);
						GartenRegisteNotify gartenRegisteNotify = new GartenRegisteNotify(phoneNumber, datas);
						Thread thread = new Thread(gartenRegisteNotify);
						thread.start();
						MyUtil.putMapParams(result,"state", 1);
				}
				return result;
		}
		
		
		/**
		 * 1创建幼儿园记录
		 * 2创建园长身份的职工
		 * 3创建大班1中班1小班1
		 * 4天加1今年的ignoreTime
		 * @throws ParseException 
		 * @throws IOException 
		 */
		public  void creation(
				String phoneNumber,Integer gartenGrade,String gartenName,
				Long attendanceTime,Long monitorTime,Integer agent,
				String province,String city,String countries,String contractNumber,Long contractStart,Long contractEnd,
				String name ,AgentAudit agentAudit,Integer agentType,Integer gartenType) throws ParseException, IOException{
			Map<String,Object> param=MyUtil.putMapParams("phoneNumber", phoneNumber, "gartenGrade", gartenGrade, "gartenName", gartenName);
			MyUtil.putMapParams(param,"attendanceTime", attendanceTime, "monitorTime", monitorTime, "agent", agent);
			MyUtil.putMapParams(param,"province", province, "city", city, "countries", countries,"agentType",agentType);
			MyUtil.putMapParams(param, "contractNumber", contractNumber, "contractStart", contractStart, "contractEnd", contractEnd);
			MyUtil.putMapParams(param, "name", name,"token",UUID.randomUUID().toString(),"gartenType",gartenType);
			MyUtil.putMapParams(param,"leadClass", "一班","pwd",CryptographyUtil.md5("123456", "lxc"),"agentAudit",agentAudit);
			bigcontrolDao.insertGartenInfo(param);
			Integer gartenId=bigcontrolDao.fingMaxGartenId();
			MyUtil.putMapParams(param,"gartenId",gartenId,"job","园长","permission",LyParam.PRINCIPAL_QX);
			bigcontrolDao.insertAttendanceNo(param);
			Integer jobId=bigcontrolDao.fingMaxJobId();
			MyUtil.putMapParams(param,"jobId",jobId);
			bigcontrolDao.insertWorkerInfo(param);
			
			//注册园长环信
//			HuanXinThread huanXinThread = new HuanXinThread(2, jobId);
//			Thread thread = new Thread(huanXinThread);
//			thread.start();
			//----------------3----------
			MyUtil.putMapParams(param, "leadGrade", "小班");
			bigcontrolDao.insertClass(param);
			MyUtil.putMapParams(param, "leadGrade", "中班");
			bigcontrolDao.insertClass(param);
			MyUtil.putMapParams(param, "leadGrade", "大班");
			bigcontrolDao.insertClass(param);
			//--------------4----------
			MyUtil.addIgnoreYear(gartenId);
			//--------------5----------
			if(null!=agentAudit&&null!=agent&&agentAudit.getResource()==1){
				creditMoney(param);
			}
			
		}
		
		
		public  Map<String,Object> deleteGarten(String token,Integer gartenId){
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<TeacherAndBabyInfo> babys = attendanceDao.findBabys(gartenId);
					//开启删除宝宝线程
					DeleteGartenBaby deleteGartenBaby = new DeleteGartenBaby(babys);
					Thread babyDeleteThread = new Thread(deleteGartenBaby);
					babyDeleteThread.start();
					
					List<TeacherAndBabyInfo> teachers = attendanceDao.findTeachers(gartenId);
					//开启删除老师线程
					DeleteGartenTeacher deleteGartenTeacher = new DeleteGartenTeacher(teachers);
					Thread teacherDeleteThread = new Thread(deleteGartenTeacher);
					teacherDeleteThread.start();
					WorkerInfo principal = smallcontrolDao.findPrincipalByGartenId(gartenId);
					
					//开启删除幼儿园相关信息线程
					DeleteGartenAll deleteGartenAll = new DeleteGartenAll(gartenId);
					Thread deleteGarten = new Thread(deleteGartenAll);
					deleteGarten.start();
					
					MyUtil.putMapParams(result, "state", 1);
				}
				return result;
			
		}
		
		public void ignore(Integer gartenId) throws ParseException {
			MyUtil.addIgnoreYear(gartenId);
			
		}
		
		
		public  void test1() throws ParseException{
			MyUtil.addIgnoreYear(1);		
			}
		
		
		public  void addIgnoreYear(Integer gartenId,Date date){
			bigcontrolDao.insertIgnore(MyUtil.putMapParams("gartenId", gartenId, "date", date));
		}
		
		
		public  List<GartenInfo> getAllGarten(){
			return bigcontrolDao.getAllGarten();
		}
		
		
		
		
		
		
		
		

		public Map<String, Object> agentMessge(String token,String province,String city,String countries,String agentId,Integer pageNo) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<AgentInfo> agentInfo=bigcontrolDao.findAgentMessge(MyUtil.putMapParams("province", province, "city", city, "countries", countries, "agentId", agentId));
					MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(agentInfo, pageNo));
				}
				return result;
		}
		
		
		
		public Map<String, Object> getAgentName(String token,String province,String city,String countries) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<AgentInfo> agentInfo=bigcontrolDao.findAgentName(MyUtil.putMapParams("province", province, "city", city, "countries", countries));
					MyUtil.putMapParams(result,"state", 1,"info",agentInfo);
				}
				return result;
		}

		public Map<String, Object> deleteAgentMessge(String token, Integer agentId) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
				if(null!=em){
					bigcontrolDao.deleteAgentMessge(agentId);
					MyUtil.putMapParams(result,"state", 1);
				}
				return result;
		}

		public Map<String, Object> frostAgentMessge(String token, Integer agentId) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
				if(null!=em){
					bigcontrolDao.frostAgentMessge(agentId);
					MyUtil.putMapParams(result,"state", 1);
				}
				return result;
		}
		public Map<String, Object> unfrostAgentMessge(String token, Integer agentId) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
				if(null!=em){
					bigcontrolDao.unfrostAgentMessge(agentId);
					MyUtil.putMapParams(result,"state", 1);
				}
				return result;
		}

		public Map<String, Object> updateAgentMessge(String token, Integer agentId, String agentName,
				String phoneNumber, String province, String city, String countries, String cardFragment) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
				if(null!=em){
					Integer agentGrade=null==countries?(null==city?1:2):3;
					Map<String,Object> param=MyUtil.putMapParams("agentId", agentId, "agentName", "".equals(agentName)?null:agentName, "phoneNumber",  "".equals(phoneNumber)?null:phoneNumber, "province",  "".equals(province)?null:province , "city","".equals(city)?null:city   ,"countries","".equals(countries)?null:countries ,"cardFragment","".equals(cardFragment)?null:cardFragment );
					MyUtil.putMapParams(param,"agentGrade",agentGrade);
					AgentInfo agentInfo=bigcontrolDao.findAgentMessgeOne(MyUtil.putMapParams("province", province, "city", city, "countries", countries));
					if(null==agentInfo||agentInfo.getAgentId()==agentId){
						bigcontrolDao.updateAgentMessge(param);
						MyUtil.putMapParams(result,"state", 1);
					}else{
						MyUtil.putMapParams(result,"state", 2);
					}
					
				}
				return result;
		}
		
		public Map<String,Object> updateAgentFinance(String token, Integer agentId,String creditMoney,String agentMoney ,Integer rebate){
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					Map<String, Object> params = MyUtil.putMapParams("agentId", agentId, "creditMoney",creditMoney,"agentMoney",agentMoney,"rebate",rebate);
					bigcontrolDao.updateAgentFinance(params);
					MyUtil.putMapParams(result,"state", 1);
				}
				return result;
		}
		
		
		public Map<String, Object> agentPerformance(String token, String agentId, String province, String city,
				String countries, Integer state,Integer pageNo) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<AgentAuditDetail> agentPerformance=bigcontrolDao.agentPerformance(MyUtil.putMapParams("agentId", agentId, "province", province, "city", city, "countries", countries, "state", state));
					for(AgentAuditDetail a :agentPerformance){
						
						AgentInfo agentInfo = agentDao.findAgentById(a.getResourceId());
						a.setAgentInfo(agentInfo);
					}
					MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(agentPerformance, pageNo));
				}
				return result;
		}

		public Map<String, Object> gartenCharge(String token, String gartenId, String province, String city,
				String countries, Integer pageNo,Integer type) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<GartenCharge> agentPerformance=bigcontrolDao.gartenCharge(MyUtil.putMapParams("gartenId", gartenId, "province", province, "city", city, "countries", countries,"type",type));
					MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(agentPerformance, pageNo));
				}
				return result;
		}

		public Map<String, Object> addGartenCharge(String token, Integer type, Integer gartenId, String province,
				String city, String countries, BigDecimal month1, BigDecimal month3, BigDecimal month6,
				BigDecimal month12 ) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
				if(null!=em){
					System.err.println("幼儿园主键"+gartenId+"===");
					System.err.println("幼儿园主键"+gartenId+"===");
					System.err.println("幼儿园主键"+gartenId+"===");
					System.err.println("幼儿园主键"+gartenId+"===");

					//province="".equals(province)?null:province;注释掉后就是 直接存空字符串
					//city="".equals(city)?null:city;
					//countries="".equals(countries)?null:countries;
					gartenId=null==gartenId?0:gartenId;//没有gartenId则转成0
					String gartenName = null;
					if(0!=gartenId){//有选择幼儿园 就赋值省市区
						GartenInfo garten=workerDao.findGartenInfoById(gartenId);
						province=garten.getProvince();
						city=garten.getCity();
						countries=garten.getCountries();
						gartenName=garten.getGartenName();
					}
					GartenCharge gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams("gartenId", gartenId, "province", province, "city", city, "countries", countries,"type",type));
					if(null==gartenCharge){
						
						Map<String,Object> param=MyUtil.putMapParams("gartenId", gartenId, "province", province, "city", city, "countries", countries,"gartenName",gartenName);
						MyUtil.putMapParams(param,"type",type,"month1",month1,"month3",month3,"month6",month6,"month12",month12);
						bigcontrolDao.addGartenCharge(param);
						MyUtil.putMapParams(result,"state", 1);
					}else{
						MyUtil.putMapParams(result,"state", 2);
					}
					
				}
				return result;
		}
		
		
		public Map<String, Object> deleteGartenCharge(String token,Integer chargeId) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
				if(null!=em){
						bigcontrolDao.deleteGartenCharge(chargeId);
						  MyUtil.putMapParams(result,"state", 1);
				}
				return result;
		}
		

		public Map<String, Object> changeGartenDredge( Integer count, Integer type, Integer gartenId) {
			  Map<String,Object> result=MyUtil.putMapParams("state", 1);
					bigcontrolDao.changeGartenDredge(MyUtil.putMapParams("type",type,"gartenId",gartenId,"count",count));
				return result;
		}
		
		public Map<String, Object> sendNotified(String token,Integer[] gartenIds ,Integer type,String title,String info) throws APIConnectionException, APIRequestException {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
				if(null!=em){
					try {
						BigControlSendNotify bs = new BigControlSendNotify(gartenIds, type, title, info,em);
						Thread thread = new Thread(bs);
						thread.start();
					} catch (Exception e) {
						e.printStackTrace();
						return result;
					}
					
					MyUtil.putMapParams(result,"state", 1);
				}
				return result;
		}

		public static void pushOne(String appKey,String materSecret,String message,String phoneNumber) throws APIConnectionException, APIRequestException{
			//阿姨
			/*String materSecret="1dd0e3924e7394137bc20d57";
			String appKey="8f6965a441a0aa10011c7063";*/
			//阿姨
			/*String materSecret="b2588d7dcaa473f3975c86fc";
			String appKey="587fc17e9c0cc048c2a3e10a";*/
			System.err.println(phoneNumber+"准备发送");
			JPushClient jPushCilent=new JPushClient(materSecret,appKey);
			PushPayload payload=PushPayload.newBuilder()
					 .setPlatform(Platform.all())
		                .setAudience(Audience.alias(phoneNumber))/*15356506227,13843838438*/
		                .setNotification(Notification.alert(message))
		                .build();
			PushResult result=jPushCilent.sendPush(payload);
			System.err.println(phoneNumber+"发送完成");
			/*PushPayload payload=PushPayload.alertAll("我输出了"); 
			PushResult result=jPushCilent.sendPush(payload);
			System.err.println("s:="+result);*/
		}

		public Map<String, Object> addAgentMessge(String token, String phoneNumber, BigDecimal agentMoney,
				BigDecimal creditMoney, Long agentStartTime, Long agentEndTime, String name, String agentName,
				Integer rebate, String province, String city, String countries) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
				if(null!=em){
					AgentInfo agent = agentDao.findAgentByAccount(phoneNumber);
					if(agent!=null){
						return MyUtil.putMapParams(result,"state", 3);		//手机号已被注册
					}
					city="".equals(city)?null:city;
					countries="".equals(countries)?null:countries;
					AgentInfo agentInfo=bigcontrolDao.findAgentMessgeOne(MyUtil.putMapParams("province", province, "city", city, "countries", countries));
					if(null==agentInfo){//验证该地区是否已经有人代理
						Integer agentGrade=null==countries?(null==city?1:2):3;
						Map<String,Object> param=MyUtil.putMapParams("phoneNumber", phoneNumber, "pwd", CryptographyUtil.md5("123456", "lxc"), "agentMoney", agentMoney, "creditMoney", creditMoney, "agentStartTime", agentStartTime);
						MyUtil.putMapParams(param,"agentEndTime", agentEndTime,"name",name,"agentName",agentName,"rebate",rebate,"province",province);
						MyUtil.putMapParams(param,"city", city,"countries",countries,"agentGrade",agentGrade,"token",UUID.randomUUID().toString(),"operator",em.getName());
						bigcontrolDao.insertAgent(param);
						MyUtil.putMapParams(result,"state", 1);
					}else{//state 2有人代理了  不能注册
						MyUtil.putMapParams(result,"state", 2);
					}
				}
				return result;
		}

		public Map<String, Object> order(String token,Integer pageNo,String province,String city,String countries
				,Integer gartenId,Integer state,String name,String phoneNumber,Integer type,String orderDetail,String babyName) {
			 			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<OrderAll> order=bigcontrolDao.findOrder(state,type, province, city, countries, gartenId,orderDetail);
					order=MyUtil.appendOrderName(order,name,phoneNumber,babyName);
						MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(order, pageNo));
				}
				return result;
		}


		public Map<String,Object> exportOrder(String token,String province,String city,String countries
				,Integer gartenId,Integer state,String name,String phoneNumber,Integer type,HttpServletResponse response,String orderDetail,String babyName){
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
				if(null!=em){
					List<OrderAll> order=bigcontrolDao.findOrder(state,type, province, city, countries, gartenId,orderDetail);
					order=MyUtil.appendOrderName(order,name,phoneNumber,babyName);
					
					try {
						response.setContentType("application/x-execl");
						response.setHeader("Content-Disposition", "attachment;filename=" + new String("订单列表.xls".getBytes(), "ISO-8859-1"));
						ServletOutputStream outputStream = response.getOutputStream();
						ExcelUtil.exportOrderExcel(order, outputStream);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			return null;
		}
		
		public Map<String, Object> feedback(String token, Integer pageNo) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<FeedbackDetail> order=bigcontrolDao.findFeedback();
						MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(order, pageNo));
				}
				return result;
		}
	
		
		
//支付宝支付 
	/*public Map<String, Object> alipay(String token,Integer type,Integer monthCount,Integer gartenId ) {
		
		 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
		  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		if(null!=em){//验证用户
			//创建未支付订单
			Map<String, Object> getPrice= getPrice( token, type, monthCount,gartenId) ;
			BigDecimal big=(BigDecimal) getPrice.get("price");
			Long orderNumber=System.currentTimeMillis();
			String orderDetail=0==type?"购买幼儿园本身考勤":(1==type?"购买幼儿园本身考勤":(2==type?"购买幼儿园所有家长视频":"购买幼儿园所有家长考勤"));
			Order o=new Order(orderNumber,new Date().getTime()/1000,null,"园长",big,orderDetail,workerInfo.getWorkerId(),type,0,0,monthCount,null);
			parentDao.insertOrdr(o);//创建未支付订单
			Map<String,Object> payResult=MyUtilAll.myAlipay(orderNumber+"", orderDetail,big.toString() );
			MyUtil.putMapParams(result,"state", 1,"info",payResult.get("result"));
		}
		return result;
	}
	*/
	
	
		//支付宝支付 
		public Map<String, Object> alipay(String token,Integer type,Integer monthCount,Integer gartenId, Integer parentId, Integer babyId) throws IOException, ParseException {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
			if(null!=em){//验证用户
				//总控制端不需要支付 直接处理回调函数里面的内容
				//处理到期时间
				GartenInfo gartenInfo = null;
				if(null!=gartenId && !"".equals(gartenId)){
					gartenInfo=workerDao.findGartenInfoById(gartenId);
				}
				
				SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
				Date day=new Date();
				Date date=new Date();
				Long monitorTime=gartenInfo.getMonitorTime();
				Long attendanceTime=gartenInfo.getAttendanceTime();
			    Calendar calendar = Calendar.getInstance();  
				Integer  orderType=type;
						if(0==orderType||1==orderType){
							if(0==orderType){//幼儿园购买本身视频
								if(gartenInfo.getMonitorTime()>day.getTime()/1000){
									System.err.println("monitorTime前"+monitorTime);
									  calendar.setTime(sim.parse(sim.format(gartenInfo.getMonitorTime()*1000))); 
									  calendar.add(Calendar.MONTH, 0==monthCount?-60:monthCount); 
									monitorTime=calendar.getTime().getTime()/1000;  
									System.err.println("monitorTime后"+monitorTime);
								}else{
									calendar.setTime(day); 
									  calendar.add(Calendar.MONTH, 0==monthCount?-60:monthCount); 
									  monitorTime=calendar.getTime().getTime()/1000;  
								}
							}
							if(1==orderType){//幼儿园购买本身考勤
								if(gartenInfo.getAttendanceTime()>day.getTime()/1000){
									  calendar.setTime(sim.parse(sim.format(gartenInfo.getAttendanceTime()*1000))); 
									  calendar.add(Calendar.MONTH, 0==monthCount?-60:monthCount); 
									  attendanceTime=calendar.getTime().getTime()/1000;  
								}else{
									calendar.setTime(day); 
									  calendar.add(Calendar.MONTH, 0==monthCount?-60:monthCount); 
									  attendanceTime=calendar.getTime().getTime()/1000;  
								}
							}
							System.err.println("monitorTime最终"+monitorTime);
							bigcontrolDao.updateGartenTime(MyUtil.putMapParams("monitorTime", monitorTime, "attendanceTime", attendanceTime,"gartenId",gartenInfo.getGartenId()));
						}else if(4==orderType||5==orderType||7==orderType){//总控制端帮某个家长开通视频或考勤   7是帮某个家长购买全部

							System.err.println("回调成功");
							System.err.println("回调成功");
							System.err.println("回调成功");
							ParentInfo parentInfo=parentDao.findParentById(parentId);//需要parentId
							Long orderNumber=System.currentTimeMillis();//总控制端也要生成订单
							
							String orderDetail=4==type?"帮家长购买视频":(5==type?"帮家长购买考勤":"帮家长购买视频和考勤");
							BabyInfo baby=parentDao.findBabyShortByBabyId(babyId);
							Order o=new Order(orderNumber,new Date().getTime()/1000,null,"家长",new BigDecimal(0),orderDetail,parentId,type+4,0,1,monthCount,babyId,baby.getGartenId());
							parentDao.insertOrdr(o);//创建未支付订单

							String[] babyIds=parentInfo.getBabyId();
							 day=new Date();
							 date=new Date();
							for(int i=0;i<babyIds.length;i++){
								if(babyIds[i].equals(babyId+"")){//需要宝宝Id
									String[] attendanceTimeStr=parentInfo.getAttendanceTime();
									String[] monitorTimeStr=parentInfo.getMonitorTime();
									   calendar = Calendar.getInstance();  
									  orderType=type;
									if(4==orderType||7==orderType){
										System.err.println(monitorTimeStr[i]);
										if(sim.parse(monitorTimeStr[i]).getTime()>day.getTime()){
											  calendar.setTime(sim.parse(monitorTimeStr[i])); 
											  calendar.add(Calendar.MONTH, monthCount); 
											  date = calendar.getTime();  
											  monitorTimeStr[i]=sim.format(date);
											System.err.println(monitorTimeStr[i]);
										}else{
											System.err.println(monitorTimeStr[i]);
											calendar.setTime(day); 
											  calendar.add(Calendar.MONTH, monthCount); 
											  date = calendar.getTime();  
											monitorTimeStr[i]=sim.format(date);	
											System.err.println(monitorTimeStr[i]);
										}
									}
									if(5==orderType||7==orderType){
										if(sim.parse(attendanceTimeStr[i]).getTime()>day.getTime()){
											System.err.println(attendanceTimeStr[i]);
											  calendar.setTime(sim.parse(attendanceTimeStr[i])); 
											  calendar.add(Calendar.MONTH,monthCount); 
											  date = calendar.getTime();  
											  attendanceTimeStr[i]=sim.format(date);	
											  System.err.println(attendanceTimeStr[i]);
										}else{
											System.err.println(attendanceTimeStr[i]);
											calendar.setTime(day); 
											  calendar.add(Calendar.MONTH, monthCount); 
											  date = calendar.getTime();  
											  attendanceTimeStr[i]=sim.format(date);
											  System.err.println(attendanceTimeStr[i]);
										}
									}
									String attendanceTimeStr2=MyUtil.changeArrayToString(attendanceTimeStr);
									String monitorTimeStr2=MyUtil.changeArrayToString(monitorTimeStr);
									System.err.println("attendanceTimeStr"+attendanceTimeStr2);
									System.err.println("monitorTimeStr"+monitorTimeStr2);
									System.err.println("parentId"+parentInfo.getParentId());
									parentDao.updateChargeTime(MyUtil.putMapParams("monitorTime", monitorTimeStr2, "attendanceTime", attendanceTimeStr2,"parentId",parentInfo.getParentId()));
								}
							}
						}else{
							if(2==orderType||6==orderType){//修改幼儿园表里面的到期时间
								changeGartenDredge(  monthCount ,  0, gartenId);
							}
							if(3==orderType||6==orderType){
								changeGartenDredge(  monthCount ,  1,  gartenId);
							}
							WorkerInfo worker=principalDao.findPrincipalByGartenId(gartenId);
							Long orderNumber=System.currentTimeMillis();//总控制端也要生成订单
							String orderDetail=2==type?"帮幼儿园购买视频":(3==type?"帮幼儿园购买考勤":"帮幼儿园购买视频和考勤");
							BabyInfo baby=parentDao.findBabyShortByBabyId(babyId);
							Order o=new Order(orderNumber,new Date().getTime()/1000,null,"园长",new BigDecimal(0),orderDetail,worker.getWorkerId(),type+10,0,1,monthCount,null,gartenId);
							parentDao.insertOrdr(o);//创建未支付订单

							 // 1找到这个幼儿园所有的宝宝的主要监护人+附加宝宝主键
							List<ParentInfoCharge>  parentInfoCharge=bigcontrolDao.findParentInfoCharge(gartenInfo.getGartenId());
						for(ParentInfoCharge p:parentInfoCharge){//为每个家长找到自己的主宝宝添加对应的时间
							String[] babyIds=p.getBabyId();
							for(int i=0;i<babyIds.length;i++){
								if(babyIds[i].equals(p.getMainBabyId()+"")){
									ParentInfo  parent=parentDao.findParentById(p.getParentId());
									String[] attendanceTimeParent=parent.getAttendanceTime();
									String[] monitorTimeParent=parent.getMonitorTime();
									if(2==orderType||6==orderType){//并且改变幼儿园的考勤到期时间视频到期时间
										if(sim.parse(monitorTimeParent[i]).getTime()>day.getTime()){
											System.err.println(monitorTimeParent[i]+"前");
											calendar.setTime(sim.parse(monitorTimeParent[i])); 
											  calendar.add(Calendar.MONTH, 0==monthCount?-60:monthCount); 
											  date = calendar.getTime();  
											  monitorTimeParent[i]=sim.format(date);
											System.err.println(monitorTimeParent[i]+"后");
										}else{
											System.err.println(monitorTimeParent[i]);
											calendar.setTime(day); 
											  calendar.add(Calendar.MONTH, 0==monthCount?-60:monthCount); 
											  date = calendar.getTime();  
											  monitorTimeParent[i]=sim.format(date);	
												System.err.println(monitorTimeParent[i]+"后");
										}
										
									}
									if(3==orderType||6==orderType){
										if(sim.parse(attendanceTimeParent[i]).getTime()>day.getTime()){
											System.err.println(attendanceTimeParent[i]+"前");
											  calendar.setTime(sim.parse(attendanceTimeParent[i])); 
											  calendar.add(Calendar.MONTH, 0==monthCount?-60:monthCount); 
											  date = calendar.getTime();  
											  attendanceTimeParent[i]=sim.format(date);	
											  System.err.println(attendanceTimeParent[i]+"后");
										}else{
											System.err.println(attendanceTimeParent[i]+"前");
											calendar.setTime(day); 
											  calendar.add(Calendar.MONTH, 0==monthCount?-60:monthCount); 
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
						MyUtil.putMapParams(result,"state", 1);
				
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
	public Map<String, Object> getPrice(String token ,Integer type,Integer month,Integer gartenId) {
		 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
		  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		
		if(null!=em){//验证用户
			BigDecimal big1=null;
			BigDecimal big2=null;
			BigDecimal big3=null;
			BigDecimal big4=null;
			BigDecimal big5=null;
			BigDecimal big0=null;
			GartenInfo garten=workerDao.findGartenInfoById( gartenId);
			List<GartenCharge> gartenCharges=new ArrayList<GartenCharge>();
			GartenCharge gartenCharge=new GartenCharge();
			//MyUtil.putMapParams(result,"state", 1,"info",gartenCharges);
			for(int i=0;i<6;i++){
				 gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams("province",garten.getProvince(),"city",garten.getCity(),"countries",garten.getCountries(),"gartenId", garten.getGartenId(), "type",i));//属于该幼儿园本身的收费
				if(null!=gartenCharge){
					gartenCharges.add(gartenCharge);
					if(4==i){
						big5=MyUtil.getRealPrice(gartenCharge,month);
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
				}else{
					gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams("province",garten.getProvince(),"city",garten.getCity(),"countries",garten.getCountries(),"gartenId", 0, "type",i));//属于该幼儿园所在区的收费
					 	if(null!=gartenCharge){
					 		gartenCharges.add(gartenCharge);
							if(4==i){
								big5=MyUtil.getRealPrice(gartenCharge,month);
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
					 	}else{
					 		gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams("province",garten.getProvince(),"city",garten.getCity(),"countries","","gartenId", 0, "type",i));//属于该幼儿园所在市的收费
							 if(null!=gartenCharge){
								 gartenCharges.add(gartenCharge);
									if(4==i){
										big5=MyUtil.getRealPrice(gartenCharge,month);
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
							 	}else{
							 		gartenCharge=bigcontrolDao.gartenChargeOne(MyUtil.putMapParams("province",garten.getProvince(),"city","","countries","","gartenId", 0, "type",i));//属于该幼儿园所在省的收费
									 if(null!=gartenCharge){
										 gartenCharges.add(gartenCharge);
											if(4==i){
												big5=MyUtil.getRealPrice(gartenCharge,month);
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
									 		gartenCharges.add(gartenCharge);
									 		System.err.println("gartenCharge"+gartenCharge);
									 		System.err.println("type"+i);
											if(4==i){
												big5=MyUtil.getRealPrice(gartenCharge,month);
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

	public String test() throws ParseException {
		Map<String,Object> param=MyUtil.putMapParams("orderNumber", "1502528108988");
		parentDao.zforder(param);
		//处理到期时间
		Order order=parentDao.findOrder( "1502528108988");
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
							String[] attendanceTimeParent=p.getAttendanceTime();
							String[] monitorTimeParent=p.getMonitorTime();
							if(4==orderType||6==orderType){
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
							if(5==orderType||6==orderType){
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
	//终端设备  ：考勤机器
	public Map<String,Object> machineList(String token,Integer type,String province,String city ,String countries,Integer gartenId, Integer pageNo){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			if(null!=em){
				List<MachineDetail> machine = bigcontrolDao.getMachine(type,province,city,countries,gartenId);
				MyUtil.putMapParams(result, "state", 1, "info", MyPage.listPage16(machine, pageNo));
				
			}
			return result;
	}	

	//添加考勤机器
	public Map<String,Object> addMachine(String token,Integer type,String macId,Integer gartenId){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			if(null!=em){
				MachineDetail machine = bigcontrolDao.findMachineByMacId(macId);
				if(machine!=null){
					return MyUtil.putMapParams(result, "state", 2); 	//该macId的闸机已经存在
				}
				bigcontrolDao.addMachine(type,macId,gartenId);
				Integer machineId = bigcontrolDao.findMaxMachineId(gartenId);
				bigcontrolDao.addPartner(UUID.randomUUID().toString(),UUID.randomUUID().toString(),gartenId,machineId);
				MyUtil.putMapParams(result, "state", 1);
			}
			return result;
	}
	
	//修改考勤机器
	public Map<String,Object> updateMachine(String token,Integer machineId,String macId){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			if(null!=em){
				MachineDetail machineDetail = bigcontrolDao.findMachineByMacId(macId);
				if(machineDetail!=null){
					return MyUtil.putMapParams(result, "state", 2); 	//该macId的闸机已经存在
				}
				MachineDetail machine = bigcontrolDao.findMachineById(machineId);
				bigcontrolDao.updateMachine(machineId,macId);
				bigcontrolDao.updateEquipMac(macId,machine.getMacId());
				MyUtil.putMapParams(result, "state", 1);
			}
			return result;
		}
	
	//删除考勤机器
	public Map<String,Object> deleteMachine(String token,Integer machineId){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			if(null!=em){
				MachineDetail machine = bigcontrolDao.findMachineById(machineId);
				bigcontrolDao.deleteMachine(machineId);
				bigcontrolDao.deletePartner(machineId);
				bigcontrolDao.updateEquipIsValid(machineId,machine.getMacId());
				MyUtil.putMapParams(result, "state", 1);
			}
			return result;
		}
	
	//打卡摄像头列表
	public Map<String,Object> getDakaCameraList(String token,Integer gartenId,String province,String city,String countries,Integer pageNo){
		Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			if(null!=em){
				List<DakaCamera> list = bigcontrolDao.findEquipBygartenId(gartenId,province,city,countries);
				
				MyUtil.putMapParams(result, "state", 1, "info", MyPage.listPage16(list, pageNo));
			}
		return  result;
	}
	
	//直播摄像头列表
		public Map<String,Object> getLiveCameraList(String token,Integer gartenId,String province,String city,String countries,Integer pageNo){
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
				if(null!=em){
					List<LiveCamera> list = bigcontrolDao.findVideoByGartenId(gartenId,province,city,countries);
					
					MyUtil.putMapParams(result, "state", 1, "info", MyPage.listPage16(list, pageNo));
				}
			return  result;
		}
		
	//添加打卡摄像头
		public Map<String,Object> addDakaCamera(String token,Integer gartenId,String cameraIp,String cameraPort,String cameraUser,String cameraPwd,
  				Integer type,String macId,String deviceSerial,String validateCode) throws UnsupportedEncodingException{
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
				if(null!=em){
					/*String code = LyUtils.addYinshiyunCamera(deviceSerial, validateCode);
					if(200==Integer.valueOf(code)){*/
						bigcontrolDao.addDakaCamera(gartenId,cameraIp,cameraPort,cameraPwd,cameraUser,type,macId,deviceSerial,validateCode);
						MyUtil.putMapParams(result, "state", 1);
//					}
				}
				return result;
  		}
		
		//删除打卡摄像头
		public Map<String,Object> deleteDakaCamera(String token,Integer cameraId){
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
				if(null!=em){
//					String deviceSerial = bigcontrolDao.getDeviceSerialById(cameraId);
//					LyUtils.deleteYinshiyunCamera(deviceSerial);
					bigcontrolDao.deleteDakaCamera(cameraId);
					MyUtil.putMapParams(result, "state", 1);
				}
				return result;
  		}
		
		//更新打卡摄像头
		public Map<String,Object> updateDakaCamera(String token,Integer cameraId,String cameraIp,String cameraPort,String cameraUser,String cameraPwd,
  				Integer type,String macId){
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
				if(null!=em){
					bigcontrolDao.updateDakaCamera(cameraId,cameraIp,cameraPort,cameraUser,cameraPwd,type,macId);
					MyUtil.putMapParams(result, "state", 1);
				}
				return result;
  		}
		
		//添加直播摄像头
		public Map<String,Object> addLiveCamera(String token,Integer gartenId,String cameraIp,String cameraPort,String cameraUser,String cameraPwd,
  				String deviceSerial,String validateCode,Integer type,Integer pointId) throws UnsupportedEncodingException{
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			String url=null;	
			if(null!=em){
						
				String code = LyUtils.addYinshiyunCamera(deviceSerial, validateCode);
				if(200==Integer.valueOf(code)){
					url = LyUtils.dredgeLive(deviceSerial);
					bigcontrolDao.addLiveCamera(gartenId,cameraIp,cameraPort,
							cameraUser,cameraPwd,deviceSerial,validateCode);
					if(1==type){
						bigcontrolDao.addLiveGartenVideo(type,pointId,gartenId,url,LyParam.Garten_PlayGround_Video,deviceSerial);
					}else if(2==type){
						bigcontrolDao.addLiveGartenVideo(type,pointId,gartenId,url,LyParam.Garten_Dinner_Video,deviceSerial);
					}else if(3==type){
						bigcontrolDao.addLiveGartenVideo(type,pointId,gartenId,url,LyParam.Garten_CommonClass_Video,deviceSerial);
					}else if(0==type){
						bigcontrolDao.addLiveGartenVideo(type,pointId,gartenId,url,LyParam.Garten_Class_Video,deviceSerial);
					}
					MyUtil.putMapParams(result, "state", 1);
				}
			}
			return result;
  		}
		
		//删除直播摄像头
		public Map<String,Object> deleteLiveCamera(String token,Integer cameraId){
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			if(null!=em){
				String deviceSerial = bigcontrolDao.getDeviceSerialById(cameraId);
				List<Equipment> list = bigcontrolDao.findEquipmentByDeviceSerial(deviceSerial);
				if(list.size()==1){
					LyUtils.deleteYinshiyunCamera(deviceSerial);
				}
				bigcontrolDao.deleteLiveCamera(cameraId);
				bigcontrolDao.deleteGartenVideo(cameraId);
				MyUtil.putMapParams(result, "state", 1);
			}
			return result;
					
  		}
		
		//修改直播摄像头
		public Map<String,Object> updateLiveCamera(String token,Integer cameraId,String cameraIp,String cameraPort,String cameraUser,String cameraPwd,
  				Integer type,Integer pointId, String url){
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			if(null!=em){
				bigcontrolDao.updateLiveCamera(cameraId,cameraIp,cameraPort,cameraPwd,cameraUser);
				if(1==type){
					bigcontrolDao.updateLiveVideo(cameraId,type,pointId,LyParam.Garten_PlayGround_Video,url);
				}else if(2==type){
					bigcontrolDao.updateLiveVideo(cameraId,type,pointId,LyParam.Garten_Dinner_Video,url);
				}else if(3==type){
					bigcontrolDao.updateLiveVideo(cameraId,type,pointId,LyParam.Garten_CommonClass_Video,url);
				}else if(0==type){
					bigcontrolDao.updateLiveVideo(cameraId,type,pointId,LyParam.Garten_Class_Video,url);
				}
				MyUtil.putMapParams(result, "state", 1);
			}
			return result;
  		}
		
		public  Map<String,Object> addLiveCameraUrl(String token,Integer gartenId,String cameraIp,String cameraPort,String cameraUser,String cameraPwd,
  				String deviceSerial,String validateCode,Integer type,Integer pointId, String url){
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			if(null!=em){
				bigcontrolDao.addLiveCamera(gartenId,cameraIp,cameraPort,
						cameraUser,cameraPwd,deviceSerial,validateCode);
				if(1==type){
					bigcontrolDao.addLiveGartenVideo(type,pointId,gartenId,url,LyParam.Garten_PlayGround_Video,deviceSerial);
				}else if(2==type){
					bigcontrolDao.addLiveGartenVideo(type,pointId,gartenId,url,LyParam.Garten_Dinner_Video,deviceSerial);
				}else if(3==type){
					bigcontrolDao.addLiveGartenVideo(type,pointId,gartenId,url,LyParam.Garten_CommonClass_Video,deviceSerial);
				}else if(0==type){
					bigcontrolDao.addLiveGartenVideo(type,pointId,gartenId,url,LyParam.Garten_Class_Video,deviceSerial);
				}
				MyUtil.putMapParams(result, "state", 1);
			}
			return result;
  		}
		
		public  void addIgnoreEveryYear() throws ParseException {
			List<GartenInfo> list = bigcontrolDao.getAllGarten();
			for(GartenInfo g : list){
				MyUtil.addIgnoreYear(g.getGartenId());
			}
		}
		
		//-------------------------------新增的信用额度方法

		//给每一级的代理商增加信用额度  param:agent(代理商Id),agentAudit(审核记录),
		public  void creditMoney(Map<String, Object> param) throws ParseException{
			
			Integer agent=Integer.valueOf(param.get("agent")+"");//代理商ID
			AgentAudit agentAudit=(AgentAudit) param.get("agentAudit");//审核记录
			
			System.err.println(agentAudit);
			System.err.println(agentAudit);
			System.err.println(agentAudit);
			BigDecimal big1=null;//一级代理商获取
			BigDecimal big2=null;//2级代理商获取
			BigDecimal big3=null;//三级代理商获得
			BigDecimal big4=null;//某个点还剩多少钱
			Map<String,Object> map1=new HashMap<String,Object>();
			Map<String,Object> map2=new HashMap<String,Object>();
			Map<String,Object> map3=new HashMap<String,Object>();
			if(null!=param.get("agent")&&null!=param.get("agentAudit")){
				AgentInfo agentInfo=agentDao.findAgentById(agent)	;//通过ID获取代理商信息
				Integer grade=agentInfo.getAgentGrade();//获取代理商等级  决定 信用额度的分配
				//---------------------签约人是3级代理商--------------------------
				if(3==grade){//等级是3自己拿 给2的一部分剩下给自己
					 big3=agentAudit.getMoney1().multiply(new BigDecimal(agentInfo.getRebate())).divide(new BigDecimal(100));//总信用额度等待分配
					System.err.println(big3);
					System.err.println(big3);
					System.err.println(big3);
					 MyUtil.putMapParams(map3,"price",big3,"agentId",agent);
					 Map<String,Object> map=MyUtil.putMapParams("city",agentInfo.getCity(),"province",param.get("province"),"parentAgentId",agent);
					List<AgentInfo> agents2=bigcontrolDao.findAgentMessge(map);
					System.err.println("~~~~~~~~~~~~"+agents2.get(0));
					System.err.println(agents2.get(0));
					System.err.println(agents2.get(0));
					if(1==agents2.size()){
						big4=agentAudit.getMoney1().subtract(big3);//到了2级代理商还剩的信用额度
						
						big2=big4.multiply(new BigDecimal(agents2.get(0).getRebate())).divide(new BigDecimal(100));//2级代理商拿钱啦!
						System.err.println(big2);
						System.err.println(big2);
						System.err.println(big2);
						MyUtil.putMapParams(map2,"price",big2,"agentId",agents2.get(0).getAgentId());
						//bigcontrolDao.updateAgentCredits();
						map.remove("city");
						MyUtil.putMapParams(map,"parentAgentId",map.get("parentAgentId")+","+agents2.get(0).getAgentId());
						List<AgentInfo> agents1=bigcontrolDao.findAgentMessge(map);//查找1级代理商
						if(1==agents1.size()){
							big4=big4.subtract(big2);//到了1级代理商还剩的信用额度
							big1=big4.multiply(new BigDecimal(agents1.get(0).getRebate())).divide(new BigDecimal(100));//1级代理商拿钱啦!
							System.err.println(big1);
							System.err.println(big1);
							System.err.println(big1);
							MyUtil.putMapParams(map1,"price",big1,"agentId",agents1.get(0).getAgentId());

						}
					}
				}
				//---------------------签约人是2级代理商--------------------------
				if(2==grade){//等级是2自己拿 给1的一部分剩下给自己
					 big2=agentAudit.getMoney1().multiply(new BigDecimal(agentInfo.getRebate())).divide(new BigDecimal(100));//总信用额度等待分配
					MyUtil.putMapParams(map2,"price",big2,"agentId",agent);
					 Map<String,Object> map=MyUtil.putMapParams("province",param.get("province"),"parentAgentId",agent);
					List<AgentInfo> agents1=bigcontrolDao.findAgentMessge(map);
					if(1==agents1.size()){
						big4=agentAudit.getMoney1().subtract(big2);//到了2级代理商还剩的信用额度
						big1=big4.multiply(new BigDecimal(agents1.get(0).getRebate())).divide(new BigDecimal(100));//2级代理商拿钱啦!
						MyUtil.putMapParams(map1,"price",big1,"agentId",agents1.get(0).getAgentId());

					}
				}
				//---------------------签约人是1级代理商--------------------------
				if(1==grade){//等级是2自己拿 给1的一部分剩下给自己
					 big1=agentAudit.getMoney1().multiply(new BigDecimal(agentInfo.getRebate())).divide(new BigDecimal(100));//总信用额度等待分配
						MyUtil.putMapParams(map1,"price",big1,"agentId",agent);
				}
				List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
				list.add(map1);//往List里面增加3个级别的代理商
				list.add(map2);
				list.add(map3);
				for(Map<String,Object> m:list){
					if(null!=m.get("price")){//这个级别的代理商需要增加余额
						bigcontrolDao.updateAgentCredits(m);
					}
				}
			}
			
			
		}
		
		public Map<String, Object> relation() {
			List<Relation>  list=bigcontrolDao.relation();
			Map<String,Object> reult=MyUtil.putMapParams("state",1,"info",list);
			return reult;
		}

		public Map<String, Object> addrelation(String relation) {
			bigcontrolDao.addrelation(relation);
			Map<String,Object> reult=MyUtil.putMapParams("state",1);
			return reult;
		}

		public Map<String, Object> deleterelation(Integer relationId) {
			bigcontrolDao.deleterelation(relationId);
			Map<String,Object> reult=MyUtil.putMapParams("state",1);
			return reult;
		}
		
		public void GartenRegisterNotify(String[] datas ,String phoneNumber){
			MyUtil.register(phoneNumber,MyParamAll.YTX_DUANXIN_ZC, datas);
		}

		public void deleteOrderNoPay(){
			bigcontrolDao.deleteOrderNoPay();
		}
		
		public void bigControlSendNotify(Integer[] gartenIds,Integer type,String title,String info,Employee employee){
			String toGartenName="";//发给哪些幼儿园

			for(int i=0;i<gartenIds.length;i++){
				GartenInfo g=workerDao.findGartenInfoById(gartenIds[i]);
				if(null!=g){
					toGartenName+=toGartenName+","+g.getGartenName();
				}

				WorkerInfo principal=new WorkerInfo();//准备好给谁发通知
				List<WorkerInfo> workers=new ArrayList<WorkerInfo>();
				List<ParentInfo> parents=new ArrayList<ParentInfo>();
				List<InfoLog> infoLogs=new ArrayList<InfoLog>();
				if(1==type||0==type){//1给园长发
					 principal=principalDao.findPrincipalByGartenId(gartenIds[i]);
					 infoLogs.add(new InfoLog(principal.getGartenId(),info,null,"园长",principal.getWorkerId(),null,null,title,2));
				}
				if(2==type||0==type){//2给家长发
					System.err.println("测试"+i);

					parents=parentDao.findParentByGartenId(gartenIds[i]);
				}
				if(3==type||0==type){//3给老师发
					workers=workerDao.findWorkerByGartenId(gartenIds[i]);
				}
				//建立通知记录  并发送通知
				
				System.err.println("园长开始发");
			try {
				pushOne(MyParamAll.JIGUANG_PRINCIPAL_APP,MyParamAll.JIGUANG_PRINCIPAL_MASTER,info,principal.getPhoneNumber());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.err.println("老师开始发");	
			for(WorkerInfo w:workers){
					infoLogs.add(new InfoLog(w.getGartenId(),info,null,"老师",w.getWorkerId(),null,null,title,2));
					try {
						pushOne(MyParamAll.JIGUANG_WORKER_APP,MyParamAll.JIGUANG_WORKER_MASTER,info,w.getPhoneNumber());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			System.err.println("家长开始发");	
				for(ParentInfo p:parents){
					infoLogs.add(new InfoLog(Integer.valueOf(p.getGartenId().split(",")[0]),info,null,"家长",p.getParentId(),null,null,title,2));
					try {
						pushOne(MyParamAll.JIGUANG_PARENT_APP,MyParamAll.JIGUANG_PARENT_MASTER,info,p.getPhoneNumber());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for(InfoLog infoLog:infoLogs){//建立通知记录
					bigcontrolDao.insertInfoLog(infoLog);
				}
				
			}
			//----------------------------------新增发送历史记录---------------
			String targetName="";//拼接目标人群
			targetName+=0==type?"老师和家长和园长":(1==type?"园长":(2==type?"家长":"老师"));
			MessageLog ml=new MessageLog(new Date().getTime()/1000,targetName,info,null,employee.getName(),null,title,toGartenName,"成长记忆总台",employee.getEmployeeNo(),"总管理员",null);
			smallcontrolDao.insertMessageLog(ml);
		}
		
		//删除幼儿园 循环删除老师
		public void deleteGartenTeacher(List<TeacherAndBabyInfo> teachers){
			for(TeacherAndBabyInfo teacher : teachers){	//删除这个幼儿园所有老师
				smallcontrolDao.deleteTeacher(teacher.getBabyId());
				smallcontrolService.deleteTeacherById(teacher.getBabyId());
			}
		}
		
		//删除幼儿园 循环删除宝宝  并删除宝宝对应的家长信息
		public void deleteGartenBaby(List<TeacherAndBabyInfo> babys){
			for(TeacherAndBabyInfo baby : babys){		
				List<ParentInfo> parents = smallcontrolDao.findParentByBabyId(baby.getBabyId());
				for(ParentInfo p : parents){			//该宝宝对应的家长清除该宝宝的记录
					Integer index = Arrays.binarySearch(p.getBabyId(),baby.getBabyId().toString());
					String[] newBabyId = LyUtils.ArrayRemoveIndex(p.getBabyId(), index);
					String[] newIdentity = LyUtils.ArrayRemoveIndex(p.getIdentity(), index);
					String[] newAttendanceTime = LyUtils.ArrayRemoveIndex(p.getAttendanceTime(), index);
					String[] newMonitorTime = LyUtils.ArrayRemoveIndex(p.getMonitorTime(), index);
					String[] newGarten = LyUtils.ArrayRemoveIndex(p.getGartenId().split(","), index);
					smallcontrolDao.updateParentIsExist(p.getParentId(), LyUtils.StrChangeToStr(newBabyId), LyUtils.StrChangeToStr(newGarten), 
							LyUtils.StrChangeToStr(newIdentity), LyUtils.StrChangeToStr(newMonitorTime), LyUtils.StrChangeToStr(newAttendanceTime));
				}
				smallcontrolService.deleteBabyById(baby.getBabyId());
			}
		}
		
		//删除关于幼儿园的所有信息
		public void deleteGartenAll(Integer gartenId){
			WorkerInfo principal = smallcontrolDao.findPrincipalByGartenId(gartenId);
			
			
			smallcontrolDao.deleteComment("园长", principal.getWorkerId());
			smallcontrolDao.deleteAttendanceNo(principal.getWorkerId());
			smallcontrolDao.deleteFeedback("园长", principal.getWorkerId());
			smallcontrolDao.deletePhotoDianzan("园长", principal.getWorkerId());
			
			bigcontrolDao.deleteGartenActivityDetail(gartenId);
			bigcontrolDao.deleteGartenAllCharge(gartenId);
			bigcontrolDao.deleteGartenAllClass(gartenId);
			bigcontrolDao.deleteGartenInfo(gartenId);
			bigcontrolDao.deleteGartenAllLesson(gartenId);
			bigcontrolDao.deleteGartenAllPhoto(gartenId);
			bigcontrolDao.deleteGartenAllRecipe(gartenId);
			bigcontrolDao.deleteGartenAllVideo(gartenId);
			bigcontrolDao.deleteGartenAllIgnoreTime(gartenId);
			bigcontrolDao.deleteGartenAllInfoLog(gartenId);
			bigcontrolDao.deleteGartenAllUnusual(gartenId);
			bigcontrolDao.deleteGartenAllMachine(gartenId);
			bigcontrolDao.deleteGartenAllEquipment(gartenId);
		}
		
		public Map<String, Object> deleteAll(String token) {
			 Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0);
				if(null!=em){
					XxxThread xx=new XxxThread();
					Thread thread=new Thread(xx);
					thread.start();
					MyUtil.putMapParams(result,"state", 1);
				}
			return result;
		}



	public void deleteIsvalid() {
			bigcontrolDao.deleteIsvalid();
			
		}
	
	//type 0给老师家长发 2给家长发 3给老师发
			public Map<String, Object> messagelog(String token,Long start,Long end,Integer gartenId, Integer pageNo)  {
				Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
				  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
					if(null!=em){
						Map<String,Object> param=MyUtil.putMapParams("gartenId",gartenId,"start",start,"end",end);
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

		public synchronized Map<String,Object> deleteMessage(Integer messageId){
	   		bigcontrolDao.deleteMessageLog(messageId);
	   		Map<String, Object> result = MyUtil.putMapParams("state", 1);
	   		return result;
	   	}
		
		public Map<String, Object> findEquipmentName(Integer pageNo) {
			Map<String,Object> result=MyUtil.putMapParams("state",1,"info",null);
			List<EquipmentName> en=bigcontrolDao.findEquipmentName();
			MyUtil.putMapParams(result,"info",MyPage.listPage16(en, pageNo));
			return result;
		}

		public Map<String, Object> addEquipmentName(String equipmentName, BigDecimal price) {
			Map<String,Object> result=MyUtil.putMapParams("state",1);
			EquipmentName name = bigcontrolDao.findEquipmentByName(equipmentName);
			if(null!=name){
				return MyUtil.putMapParams(result, "state", 2);    		//设备已经添加
			}
			bigcontrolDao.addEquipmentName(MyUtil.putMapParams("equipmentName",equipmentName,"price",price));
			return result;
		}

		public Map<String, Object> deleteEquipmentName(Integer equipmentId) {
			Map<String,Object> result=MyUtil.putMapParams("state",1);
			bigcontrolDao.deleteEquipmentName(equipmentId);
			return result;
		}

		public Map<String,Object> wuliaoOrder(String token , Integer pageNo,Integer state){
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
				if(null!=em){
					List<WuliaoOrder> list = bigcontrolDao.findWuliaoOrder(state);
					MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(list, pageNo));
				}
			return result;
		}
		
		public synchronized Map<String, Object> resolveWuliaoOrder(Integer wuliaoId, Integer state, String toPhoneNumber,String remark) {
			Map<String,Object> result=MyUtil.putMapParams("state",2);//订单编号错误
			List<WuliaoOrder> wo=agentDao.findWuliaoOrder(MyUtil.putMapParams("wuliaoId",wuliaoId));
			if(1==wo.size()){
				
				if(1!=wo.get(0).getState()){
					MyUtil.putMapParams(result,"state",4);//该订单已处理
					return result;
				}
				//这个订单是代理商的
				if(1==wo.get(0).getResource()){
					AgentInfo ai=agentDao.findAgentById(wo.get(0).getAgentId());
					Integer big=ai.getCreditMoney().compareTo(wo.get(0).getTotalPrice());
					MyUtil.putMapParams(result,"state",3);//余额不足
					if((1==big||0==big)&&2==state){//余额足够
						BigDecimal balance=ai.getCreditMoney().subtract(wo.get(0).getTotalPrice());
						Map<String,Object> param=MyUtil.putMapParams("wuliaoId",wuliaoId,"state",state,"toPhoneNumber",toPhoneNumber,"remark",remark,"balance",balance,"agentId",wo.get(0).getAgentId());
						bigcontrolDao.updateBalance(param);
						bigcontrolDao.resolveWuliaoOrder(param);
						MyUtil.putMapParams(result,"state",1);
					}
					if(3==state){//判断是否拒收
						Map<String,Object> param=MyUtil.putMapParams("wuliaoId",wuliaoId,"state",state,"toPhoneNumber",toPhoneNumber,"remark",remark,"agentId",wo.get(0).getAgentId());
						bigcontrolDao.resolveWuliaoOrder(param);
						MyUtil.putMapParams(result,"state",1);
					}
					
				}else {//这个订单是员工的  只是修改一下订单状态
					Map<String,Object> param=MyUtil.putMapParams("wuliaoId",wuliaoId,"state",state,"toPhoneNumber",toPhoneNumber,"remark",remark);
					bigcontrolDao.resolveWuliaoOrder(param);
					MyUtil.putMapParams(result,"state",1);

				}
				
			}
			
			return result;
		}

		public Map<String,Object> cardNoList(String token,String province,String city,String countries,Integer gartenId,Integer pageNo,String job,Integer classId){
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			ArrayList<CardNoDetail> list = new ArrayList<CardNoDetail>();	
			List<CardNoDetail> babyCardNoList=null;
			List<CardNoDetail> teacherCardNoList = null;
			if(null!=em){
				Map<String, Object> params = MyUtil.putMapParams("province", province, "city", city, "countries", countries, "gartenId", gartenId, "job", job,"classId",classId);
				if(null!=job){		
					if("宝宝".equals(job)){
						babyCardNoList = bigcontrolDao.getBabyCardNoList(params);
						list.addAll(babyCardNoList);
					}else if("老师".equals(job)){
						teacherCardNoList = bigcontrolDao.getTeacherCardNoList(params);
						list.addAll(teacherCardNoList);
					}
				}
				if(null==job||"".equals(job)){			
					babyCardNoList = bigcontrolDao.getBabyCardNoList(params);
					teacherCardNoList = bigcontrolDao.getTeacherCardNoList(params);
					list.addAll(teacherCardNoList);
					list.addAll(babyCardNoList);
					
				}
				MyUtil.putMapParams(result, "state", 1, "info", MyPage.listPage16(list, pageNo));
			}
			return result;
		}
		
		//-------------------------------------售后订单--------------------------------------------			  

		public Map<String, Object> findSaleService(Integer pageNo,Integer[] agentIds, Long start, Long end, Integer state,Integer[] gartenIds) {
			String agentId=MyUtil.changeArrayToString(agentIds);
			String gartenId=MyUtil.changeArrayToString(gartenIds);
			Map<String,Object> param=MyUtil.putMapParams("agentId",agentId,"start",start,"end",end,"state",state,"gartenId",gartenId);//传递参数
			List<SaleServiceAll> ss=bigcontrolDao.findSaleService(param);
			ss=MyUtil.setSaleServiceAll(ss);
			Map<String,Object> result=MyUtil.putMapParams("state",1,"info",MyPage.listPage16(ss, pageNo));//订单编号错误
			return result;
		}

		public Map<String, Object> replySaleService(Long saleServiceId, String reply) {
			Map<String,Object> result=MyUtil.putMapParams("state",10);//参数错误
			if(null!=saleServiceId&&null!=reply){
				SaleServiceAll ss=bigcontrolDao.findSaleServiceBySaleServiceId(saleServiceId);
				ss=MyUtil.setSaleServiceAll(ss);
				Map<String,Object> param=MyUtil.putMapParams("saleServiceId",saleServiceId,"reply",reply);//传递参数
				bigcontrolDao.replySaleService(param);
				MyUtil.putMapParams(result,"state",1);
				MyUtil.register(ss.getAgent().getPhoneNumber(), MyParamAll.YTX_DUANXIN_SHHF, new String[] {saleServiceId+"",ss.getTitle()});//datas 1订单号 2 订单标题
			}
			return result;
		}

		public Map<String, Object> deleteSaleService(Long saleServiceId) {
			Map<String,Object> result=MyUtil.putMapParams("state",10);//参数错误
			if(null!=saleServiceId){
				bigcontrolDao.deleteSaleService(saleServiceId);
				MyUtil.putMapParams(result,"state",1);
			}
			return result;
		}

		public void exportAttendance(String token,String province,String city, String countries ,Integer gartenId, String job, Integer classId,
				HttpServletResponse response) throws Exception {
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			ArrayList<CardNoDetail> list = new ArrayList<CardNoDetail>();	
			List<CardNoDetail> babyCardNoList=null;
			List<CardNoDetail> teacherCardNoList = null;
			if(null!=em){
				Map<String, Object> params = MyUtil.putMapParams("province", province, "city", city, "countries", countries, "gartenId", gartenId, "job", job,"classId",classId);
				if(null!=job){		
					if("宝宝".equals(job)){
						babyCardNoList = bigcontrolDao.getBabyCardNoList(params);
						list.addAll(babyCardNoList);
					}else if("老师".equals(job)){
						teacherCardNoList = bigcontrolDao.getTeacherCardNoList(params);
						list.addAll(teacherCardNoList);
					}
				}
				if(null==job||"".equals(job)){			
					babyCardNoList = bigcontrolDao.getBabyCardNoList(params);
					teacherCardNoList = bigcontrolDao.getTeacherCardNoList(params);
					list.addAll(teacherCardNoList);
					list.addAll(babyCardNoList);
					
				}
				String title =null;
				if(gartenId!=null){
					GartenInfo gartenInfo = smallcontrolDao.getGartenById(gartenId);
					title = gartenInfo.getGartenName();
					if(classId!=null){
						GartenClass gartenClass = bigcontrolDao.findClassById(classId);
						title=title+gartenClass.getLeadGrade()+gartenClass.getLeadClass();
					}
				}
				
				String titles[]= {"id","类型","持卡人姓名","考勤卡号1","考勤卡号2","考勤卡号3"};
				String fields[]= {"jobid","job","name","cardno1","cardno2","cardno3"};
				
				response.setContentType("application/x-execl");
				response.setHeader("Content-Disposition", "attachment;filename=" + new String(((title==null?"":title)+"考勤卡列表.xls").getBytes(), "ISO-8859-1"));
				ServletOutputStream outputStream = response.getOutputStream();
				
				ExcelUtils.Export_(titles, fields, list, outputStream);
					
			
			}
			
		}
		
		//-------------------------- ----  员工管理管理    ----------------         ----------------	


				public Map<String, Object> findEmployee(Integer pageNo, String name, Integer employeeNo, Long departmentNo,
						Long jobsNo,String phoneNumber) {
					Map<String,Object> param=MyUtil.putMapParams("name", name, "employeeNo", employeeNo, "departmentNo", departmentNo,"jobsNo",jobsNo,"phoneNumber",phoneNumber);
					List<Employee> employee= bigcontrolDao.findEmployee(param);
					Map<String,Object> result=MyUtil.putMapParams("state", 1,"info",MyPage.listPage16(employee, pageNo));
					return result;
				}

				public Map<String, Object> deleteEmployee(Integer  employeeNo) {
					Map<String,Object> result=MyUtil.putMapParams("state", 3,"info","存在幼儿园的代理商是这个员工,无法删除");
					Map<String,Object> param=MyUtil.putMapParams( "employeeNo", employeeNo);
					if(!ifDeleteEmployee(employeeNo)){
						bigcontrolDao.deleteEmployee(param);
						MyUtil.putMapParams(result,"state", 1,"info","成功");
						
					}
					
					return result;
				}
				/**
				 * 是否能删除员工
				 * @param employeeNo 员工主键
				 * @return 有签约幼儿园返回 true  没有false
				 */
				private boolean ifDeleteEmployee(Integer employeeNo) {
					Boolean b=bigcontrolDao.findIfDeleteEmployee(employeeNo);
					
					return b;
				}

				public Map<String, Object> updateEmployee(Integer employeeNo, String name, Long departmentNo, Long jobsNo,
						String permission, String province, String city, String countries, String phoneNumber, String pwd,Integer sex,Long entryTime,String token) {
					Employee em=bigcontrolDao.findEmployeeByToken(token);
					Map<String,Object> result=MyUtil.putMapParams("state",0);
					if(null!=em){
						Employee employee = bigcontrolDao.findEmployeeByPhoneNumber(phoneNumber);
						if(!em.getPhoneNumber().equals(phoneNumber)&&null!=employee){
							return MyUtil.putMapParams("state", 2);				//手机号码与原来不同,且新修改的手机号码已经存在
						}
						Map<String,Object> param=MyUtil.putMapParams( "employeeNo", employeeNo,"name",name,"departmentNo",departmentNo,"jobsNo",jobsNo,"permission",permission);
						MyUtil.putMapParams(param,"province",province,"city",city,"countries",countries,"phoneNumber",phoneNumber,"pwd",pwd);
						MyUtil.putMapParams(param,"sex",sex,"entryTime",entryTime,"lastEmployeeNo",em.getEmployeeNo());
						bigcontrolDao.updateEmployee(param);
						MyUtil.putMapParams(result,"state",1);
					}
					return result;
				}

			public Map<String, Object> addEmployee(String name, Long departmentNo, Long jobsNo, String permission,
					String province, String city, String countries, String phoneNumber, String pwd,Integer sex,Long entryTime,String token) {
				Employee em=bigcontrolDao.findEmployeeByToken(token);
				Map<String,Object> result=MyUtil.putMapParams("state",0);
				if(null!=em){
					Employee employee = bigcontrolDao.findEmployeeByPhoneNumber(phoneNumber);
					if(null!=employee){
						return MyUtil.putMapParams("state", 2);		//该手机号码已经注册
					}
					String uuid=UUID.randomUUID().toString();
					Map<String,Object> param=MyUtil.putMapParams( "name",name,"departmentNo",departmentNo,"jobsNo",jobsNo,"permission",permission,"token",uuid);
					MyUtil.putMapParams(param,"province",province,"city",city,"countries",countries,"phoneNumber",phoneNumber,"pwd",pwd);
					MyUtil.putMapParams(param,"sex",sex,"entryTime",entryTime, "lastEmployeeNo",em.getEmployeeNo());
					bigcontrolDao.addEmployee(param);
					MyUtil.putMapParams(result,"state",1);
				}
				return result;
			}

		public   Map<String,Object> getEmployee(Long departmentNo,Long jobsNo){
			Map<String, Object> params = MyUtil.putMapParams("departmentNo", departmentNo, "jobsNo", jobsNo);
			List<Employee> list = bigcontrolDao.findEmployee(params);
			
			
			return MyUtil.putMapParams("state", 1,"info",list);
		}
		
		
		public Map<String,Object> employeePerformance(Long departmentNo,Long jobsNo,Integer employeeNo,Integer pageNo){
			
		
			Map<String, Object> params = MyUtil.putMapParams("departmentNo", departmentNo, "jobsNo", jobsNo, "employeeNo", employeeNo);
			List<AgentAuditDetail> employeePerformance = bigcontrolDao.findEmployeePerformance(params);
			
			for(AgentAuditDetail a : employeePerformance){
				Employee employee = bigcontrolDao.findEmployeeById(a.getResourceId());
				a.setEmployee(employee);
			}
			return MyUtil.putMapParams("state", 1, "info", MyPage.listPage16(employeePerformance, pageNo));
		}
		
		public Map<String, Object> findCpActivity(Integer pageNo, String state, Integer employeeNo) {
			Map<String,Object> param=MyUtil.putMapParams( "state",state,"employee",employeeNo);
			List<CpActivity> cpActivity=bigcontrolDao.findCpActivity(param);
			Map<String,Object> result=MyUtil.putMapParams("state",1,"info",MyPage.listPage16(cpActivity, pageNo));
			return result;
		}
		public Map<String, Object> findDepartmentCpActivity(Integer pageNo, String state, String token) {
			
			Map<String,Object> result=MyUtil.putMapParams("state",0,"info",null);
			Employee em=bigcontrolDao.findEmployeeByToken(token);
			if(null!=em){
				Map<String,Object> param=MyUtil.putMapParams( "state",state,"departmentNo",em.getDepartmentNo());
				List<CpActivity> cpActivity=bigcontrolDao.findDepartmentCpActivity(param);
				MyUtil.putMapParams(result,"state",1,"info",MyPage.listPage16(cpActivity, pageNo));
			}
			
			return result;
		}
				
		
		public Map<String, Object> deleteCpActivity(Integer cpActivityId) {
			bigcontrolDao.deleteCpActivity(cpActivityId);
			Map<String,Object> result=MyUtil.putMapParams("state",1);
			return result;
		}

		public Map<String, Object> addCpActivity(String token, String content,  String reason,String title) {
			Employee em=bigcontrolDao.findEmployeeByToken(token);//根据账号查找到用户,手机号//根据账号查找到用户,手机号
 			Map<String,Object> result=MyUtil.putMapParams("state",0);
 			if(null!=em){
				Map<String,Object> param=MyUtil.putMapParams( "employeeNo",em.getEmployeeNo(),"content",content,"reason",reason,"title",title);
				bigcontrolDao.addCpActivity(param);
				MyUtil.putMapParams(result,"state",1);
 			}
			return result;
		}

		public Map<String, Object> updateCpActivity(Integer cpActivityId, String state) {
			Map<String,Object> param=MyUtil.putMapParams( "cpActivityId",cpActivityId,"state",state);
			bigcontrolDao.updateCpActivity(param);
			Map<String,Object> result=MyUtil.putMapParams("state",1);
			return result;
		}

		public Map<String, Object> findDepartmentReport(Integer pageNo, String token, Integer type, Long startTime,
				Long endTime) {
			Employee em=bigcontrolDao.findEmployeeByToken(token);
			Map<String,Object> result=MyUtil.putMapParams("state",0,"info",null);
			if(null!=em){
				Map<String,Object> param=MyUtil.putMapParams( "type",type,"startTime",startTime,"endTime",endTime,"departmentNo",em.getDepartmentNo());
				List<ReportAll> report=bigcontrolDao.findDepartmentReport(param);
				MyUtil.putMapParams(result,"state",1,"info",MyPage.listPage16(report, pageNo));
			}
			
			return result;
		}

		public Map<String, Object> findReport(Integer pageNo,  Integer type, Long startTime, Long endTime,Long departmentNo) {
				Map<String,Object> param=MyUtil.putMapParams( "type",type,"startTime",startTime,"endTime",endTime,"departmentNo",departmentNo);
				List<ReportAll> report=bigcontrolDao.findDepartmentReport(param);
				Map<String,Object> result=MyUtil.putMapParams("state",1,"info",MyPage.listPage16(report, pageNo));
			return result;
		}

		public Map<String, Object> deleteReport(Integer reportId) {
			bigcontrolDao.deleteReport(reportId);
			Map<String,Object> result=MyUtil.putMapParams("state",1);
		return result;
		}

		public Map<String, Object> addReport(String token, Long startTime, Long endTime, String workContent,
				String workSummary, String harmonizeContent, String plan, Integer type) {
			Employee em=bigcontrolDao.findEmployeeByToken(token);
			Map<String,Object> result=MyUtil.putMapParams("state",0);
			if(null!=em){
				Map<String,Object> param=MyUtil.putMapParams( "startTime",startTime,"endTime",endTime,"endTime",endTime,"workContent",workContent,"workSummary",workSummary,"harmonizeContent",harmonizeContent,"plan",plan);
				MyUtil.putMapParams( param,"employeeNo",em.getEmployeeNo(),"type",type);
				bigcontrolDao.addReport(param);
				MyUtil.putMapParams(result,"state",1);
			}
			return result;
		}
		
		public Map<String, Object> findMyReport(Integer pageNo, String token, Integer type, Long startTime,
				Long endTime) {
			Employee em=bigcontrolDao.findEmployeeByToken(token);
			Map<String,Object> result=MyUtil.putMapParams("state",0,"info",null);
			if(null!=em){
				Map<String,Object> param=MyUtil.putMapParams( "type",type,"startTime",startTime,"endTime",endTime,"employeeNo",em.getEmployeeNo());
				List<Report> report=bigcontrolDao.findMyReport(param);
				MyUtil.putMapParams(result,"state",1,"info",MyPage.listPage16(report, pageNo));
			}
			return result;
		}

		//-------------------------- ---- 开园申请     ----------------         ----------------			
				
		public Map<String, Object> findMyApplyGarten(String token,Integer pageNo) {
			Employee em=bigcontrolDao.findEmployeeByToken(token);
			Map<String,Object> result=MyUtil.putMapParams("state",0,"info",null);
			if(null!=em){
				List<AgentAudit> aa= bigcontrolDao.findMyApplyGarten(em.getEmployeeNo());
				MyUtil.putMapParams(result,"state",1,"info",MyPage.listPage16(aa, pageNo));
			}
			return result;
		}
		
		

		//开园申请
		public Map<String,Object> applyGarten(String token,String gartenName,String name,String phoneNumber,String contractNumber,String province,
				String city, String countries,Integer workerCount,Integer babyCount,Integer gradeCount,Integer classCount,Double money1,String equipment,String remark,Integer gartenType){
			Employee em=bigcontrolDao.findEmployeeByToken(token);
			Map<String,Object> result=MyUtil.putMapParams("state",0,"info",null);
			 if(null!=em){
				 WorkerInfo worker = principalDao.findPrincipalByAccount(phoneNumber);
				 if(null!=worker){
					 return MyUtil.putMapParams(result,"state", 4,"info","该参数已被注册");			//该幼儿园联系手机号码已经被注册
				 }
				 agentDao.addApplyGarten(0,gartenName,name, phoneNumber, contractNumber, province,
							 city, countries, workerCount,babyCount,gradeCount,classCount, money1, equipment,em.getEmployeeNo(),remark,gartenType);
				 MyUtil.putMapParams(result,"state", 1,"info","操作成功" );
			 }
			
			return result;
			
		}
		
		/*public Map<String,Object> employeePerformance(String token,String province,String city,String countries,Integer state,Integer pageNo){
			Employee em=bigcontrolDao.findEmployeeByToken(token);
			Map<String,Object> result=MyUtil.putMapParams("state",0,"info",null);
			 if(null!=em){
				 Map<String, Object> params = MyUtil.putMapParams("province", province, "city", city, "countries", countries, "state", state);
				 
				 bigcontrolDao.findEmployeePerformance(params);
			 }
		}*/
		
		public Map<String, Object> findDepartment() {
			List<Department> d=bigcontrolDao.findDepartment();
			Map<String,Object> result=MyUtil.putMapParams("state", 1, "info", d);
			return result;
		}
		//判断部门是否有人
		public Map<String, Object> deleteDepartment(Long departmentNo) {
			Map<String,Object> result=MyUtil.putMapParams("state", 3);//此数据在其他地方有用
			if(!ifExistDepartmentEmployee(departmentNo)){
				bigcontrolDao.deleteDepartment(departmentNo);
				MyUtil.putMapParams(result,"state", 1);
			}
			return result;
		}
		/**
		 * 该部门是否有员工  有员工返回true 没有返回false
		 */
		private boolean ifExistDepartmentEmployee(Long departmentNo) {
			Boolean b=bigcontrolDao.ifExistDepartmentEmployee(departmentNo)==0?false:true;
			return b;
		}

		//判断部门是否已存在
		public Map<String, Object> addDepartment(String departmentName, String mark) {
			Map<String,Object> result=MyUtil.putMapParams("state", 4);//已被注册
			Map<String,Object> param=MyUtil.putMapParams("departmentName", departmentName,"mark",mark);
			if(!ifExistDepartment(departmentName)){
				bigcontrolDao.addDepartment(param);
				MyUtil.putMapParams(result,"state", 1);
			}
			
			return result;
		}
		/**
		 * 该部门名  已存在返回true  不存在返回false
		 */
		private boolean ifExistDepartment(String departmentName) {
			Boolean b=bigcontrolDao.ifExistDepartment(departmentName);
			return b;
		}

		public Map<String, Object> findJobs() {
			List<Jobs> j=bigcontrolDao.findJobs();
			Map<String,Object> result=MyUtil.putMapParams("state", 1, "info", j);
			return result;
		}

		public Map<String, Object> deleteJobs(Long jobsNo) {
			Map<String,Object> result=MyUtil.putMapParams("state", 3);//此数据在其他地方有用
			if(!ifExistJobsEmployee(jobsNo)){
				bigcontrolDao.deleteJobs(jobsNo);
				MyUtil.putMapParams(result,"state", 1);
			}
			return result;
		}
		/**
		 * 该岗位是否有员工  有员工返回true 没有返回false
		 */
		private boolean ifExistJobsEmployee(Long jobsNo) {
			Boolean b=bigcontrolDao.ifExistJobsEmployee(jobsNo)==0?false:true;
			return b;
		}
		
		/**
		 * 该部门名  已存在返回true  不存在返回false
		 */
		private boolean ifExistJobs(String jobsName) {
			Boolean b=bigcontrolDao.ifExistJobs(jobsName);
			return b;
		}

		public Map<String, Object> addJobs(String jobsName,String mark) {
			Map<String,Object> result=MyUtil.putMapParams("state", 4);//已被注册
			Map<String,Object> param=MyUtil.putMapParams("jobsName", jobsName,"mark",mark);
			if(!ifExistJobs(jobsName)){
				bigcontrolDao.addJobs(param);
			}
			MyUtil.putMapParams(result,"state", 1);
			return result;
		}

		public Jobs findJobsByEmployeeNo(Integer employeeNo) {
			return bigcontrolDao.findJobsByEmployeeNo(employeeNo);
		}

		public Department findDepartmentByEmployeeNo(Integer employeeNo) {
			return bigcontrolDao.findDepartmentByEmployeeNo(employeeNo);
		}
		
	public synchronized Map<String, Object> addWuliaoOrder(String token, String equipmentAll, String address, String postalcode,
			String fromPhoneNumber,BigDecimal totalPrice,String remark) {
		Employee em= bigcontrolDao.findEmployeeByToken(token);
		 Map<String,Object> result=MyUtil.putMapParams("state", 0);
		 if(null!=em){
			 Map<String,Object> param=MyUtil.putMapParams("agentId",em.getEmployeeNo(),"equipmentAll",equipmentAll,"address",address,"postalcode",postalcode,"fromPhoneNumber",fromPhoneNumber,"totalPrice",totalPrice,"resource",0);
			 MyUtil.putMapParams(param, "remark", remark);
			 agentDao.addWuliaoOrder(param);
			 MyUtil.putMapParams(result,"state", 1);
		 }
		 return result;
	}

		public Map<String, Object> findWuliaoOrder(String token,Integer pageNo ,Integer state) {
		Employee em= bigcontrolDao.findEmployeeByToken(token);
		 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 if(null!=em){
			 Map<String,Object> param=MyUtil.putMapParams("agentId",em.getEmployeeNo(),"state",state,"resource",0);
			List<WuliaoOrder> wuliaoOrder=agentDao.findWuliaoOrder(param);
			 MyUtil.putMapParams(result,"state", 1,"info",MyPage.listPage16(wuliaoOrder, pageNo));
		 }
		 return result;
	}

		public synchronized Map<String, Object> deleteWuliaoOrder(String token, Integer wuliaoId) {
			Employee em= bigcontrolDao.findEmployeeByToken(token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0);
			 if(null!=em){
				agentDao.deleteWuliaoOrder(wuliaoId);
				 MyUtil.putMapParams(result,"state", 1);
			 }
			 return result;
		}
		public Map<String,Object> departmentWuliaoOrder(String token,Integer pageNo ,Integer state){
			Employee em= bigcontrolDao.findEmployeeByToken(token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0);
			 if(null!=em){
				 Map<String, Object> params = MyUtil.putMapParams("state", state,"departmentNo",em.getDepartmentNo());
				 List<Employee> employeeList = bigcontrolDao.findEmployee(params);
				 StringBuffer sb = new StringBuffer();
				 sb.append(0);
				 for(Employee e: employeeList){
					 sb.append(","+e.getEmployeeNo());
				 }
				 MyUtil.putMapParams(params, "departmentIds", sb.toString());
				 List<WuliaoOrder> list = bigcontrolDao.findDepartmentWuliaoOrder(params);
				 return MyUtil.putMapParams(result,"state",1,"info",MyPage.listPage16(list, pageNo));
			 }
			 return result;
		}
		
	public Map<String, Object> findWithdraw(Long startTime, Long endTime, Integer state) {
		 Map<String,Object> param=MyUtil.putMapParams("startTime", startTime,"endTime",endTime,"state",state);
		List<WithdrawAll> withdraw=bigcontrolDao.findWithdraw(param);
		withdraw=MyUtil.setWithdrawAll(withdraw);
		Map<String,Object> result=MyUtil.putMapParams("state", 1,"info",withdraw);
		return result;
	}

	public Map<String, Object> updateWithdraw(String token,Integer withdrawId, Integer state, String mark) {
		Employee em= bigcontrolDao.findEmployeeByToken(token);
		 Map<String,Object> result=MyUtil.putMapParams("state", 0);
		 if(null!=em){
			Map<String,Object> param=MyUtil.putMapParams("withdrawId", withdrawId,"state",state,"mark",mark,"employeeNo",em.getEmployeeNo());
			bigcontrolDao.updateWithdraw(param);
			//如果state 2拒绝 返回余额
			if(2==state){
				Withdraw w=agentDao.findWithdrawById(withdrawId);
				MyUtil.putMapParams(param,"price", w.getPrice(),"agentId",w.getAgentId());
				agentDao.updateAgentCredit(param);
			}

			MyUtil.putMapParams(result,"state", 1);
		 }
		return result;
	}

	public Map<String, Object> deleteWithdraw(Integer withdrawId) {
		bigcontrolDao.deleteWithdraw(withdrawId);
		Map<String,Object> result=MyUtil.putMapParams("state", 1);
		return result;
	}
	
	public Employee findEmployeeById(Integer employeeNo){
		Employee employee = bigcontrolDao.findEmployeeById(employeeNo);
		return employee;
	}
	public Map<String, Object> findAgentOrder(Integer pageNo,Long startTime, Long endTime, Integer agentId, Integer state) {
		Map<String,Object> param=MyUtil.putMapParams("startTime", startTime,"endTime",endTime,"agentId",agentId,"state",state);
		List<AgentOrderAll> a=bigcontrolDao.findAgentOrder(param);
		a=MyUtil.setFindAgentOrder(a);
		Map<String,Object> result=MyUtil.putMapParams("state", 1,"info",MyPage.listPage16(a, pageNo));
		return result;
	}

	
	//--------------考勤卡信息-------
	public Map<String, Object> employeeCard(Long departmentNo,Long jobsNo,Integer employeeNo,Integer pageNo){
   		
		Map<String,Object> param=MyUtil.putMapParams("departmentNo", departmentNo,"jobsNo",jobsNo,"employeeNo",employeeNo);
		List<Employee> employee = bigcontrolDao.findEmployee(param);
		ArrayList<CardSimple> list = new  ArrayList<CardSimple>();
		for(Employee e : employee){
			CardSimple c = new CardSimple();
			Integer count = bigcontrolDao.findCardCount(e.getEmployeeNo(),0,null);
			c.setAgentId(e.getEmployeeNo());
			c.setName(e.getName());
			c.setCount(count);
			list.add(c);
		}
		return MyUtil.putMapParams("state", 1, "info", MyPage.listPage16(list, pageNo));
   	}
	
	public Map<String, Object> agentCard(String province ,String city ,String countries,Integer agentId,Integer pageNo){
		Map<String,Object> param=MyUtil.putMapParams("province", province,"city",city,"countries",countries,"agentId",agentId);
		List<AgentInfo> agent = bigcontrolDao.findAgentMessge(param);
		ArrayList<CardSimple> list = new  ArrayList<CardSimple>();
		for( AgentInfo a : agent){
			CardSimple c = new CardSimple();
			Integer count = bigcontrolDao.findCardCount(a.getAgentId(),1,null);
			c.setAgentId(a.getAgentId());
			c.setName(a.getName());
			c.setCount(count);
			list.add(c);
		}
   		return MyUtil.putMapParams("state", 1, "info", MyPage.listPage16(list, pageNo));
   	}
	
	public Map<String, Object> cardList(Integer id,Integer agentType,Integer pageNo){	//0是员工 1是代理商
   		
		
		List<Card> list = bigcontrolDao.findCard(id, agentType, null);
			
		return MyUtil.putMapParams("state", 1,"count", list.size() ,"info", MyPage.listPage16(list, pageNo));
   	}
	
	public Map<String,Object> importCard(String token , String str,Integer agentId,Integer agentType,Integer returnMoney,String fileName){
		Employee em= bigcontrolDao.findEmployeeByToken(token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0);
		if(null!=em){
			if(str != null){
				//是否是excel
				//if(file.getOriginalFilename().matches("^.+\\.(?i)((xls)|(xlsx))$")){
				if(fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
					File file = LyUtils.base64ToFile(str,fileName);
					List<Card> list = ExcelUtil.importCard(file,fileName);
					file = new File("D:/", fileName);
					file.delete();
					for(Card c : list){
						c.setAgentId(agentId);
						c.setAgentType(agentType);
						c.setReturnMoney(returnMoney);
					}
					bigcontrolDao.addCard(list);
					MyUtil.putMapParams(result,"state", 1);
				}
			}
		}
		return result;
		
	}
	
	public Map<String,Object> downloadCardTemplate(HttpServletResponse response) throws IOException{
   		
		ArrayList<Object> list = new ArrayList<>();
		String [] titles= {"外卡号","内卡号"};
   		String [] fields = null;
   		
   		
   		response.setContentType("application/x-execl");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String("考勤卡列表.xls".getBytes(), "ISO-8859-1"));
   		
   		
   		ServletOutputStream outputStream = response.getOutputStream();
		ExcelUtils.Export_(titles, fields, list, outputStream);
		
   		return  null;
   	}
	
	public void exportCard(Integer agentId,Integer agentType,HttpServletResponse response) throws IOException{
		List<Card> card = bigcontrolDao.findCard(agentId,agentType,null);
   		String [] titles= {"id","内卡号","外卡号","是否可退押金"};
   		String [] fields = {"id","incard","outcard","isreturnable"};
   		
   		
   		response.setContentType("application/x-execl");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String("考勤卡列表.xls".getBytes(), "ISO-8859-1"));
   		
   		
   		ServletOutputStream outputStream = response.getOutputStream();
		ExcelUtils.Export_(titles, fields, card, outputStream);
		
   	}
	
	
	public Map<String,Object> cardReturnMoney(String token, Integer cardId ){
		Employee em= bigcontrolDao.findEmployeeByToken(token);
		Map<String,Object> result=MyUtil.putMapParams("state", 0);
		if(null!=em){
			Card card = bigcontrolDao.findCardById(cardId);
			Employee e = bigcontrolDao.findEmployeeById(card.getAgentId());
			//先生成押金退还记录,再更新卡的押金
			Map<String, Object> params = MyUtil.putMapParams("cardId", card.getId(), "outCard",card.getOutCard(), "returnMoney",card.getReturnMoney(), "name",e.getName());
			bigcontrolDao.addcardReturn(params);
			bigcontrolDao.updateCardReturnMoney(cardId);
			MyUtil.putMapParams(result, "state", 1);
		}
		return result;
   	}
	
	public Map<String,Object> cardReturnList(String name ,Integer pageNo,Long startTime, Long endTime){
		Map<String, Object> params = MyUtil.putMapParams("name", name, "startTime",startTime, "endTime", endTime);
		List<CardReturn> list = bigcontrolDao.findCardReturn(params);
		
   		return MyUtil.putMapParams("state", 1, "info", MyPage.listPage16(list, pageNo));
   	}
	
	public Map<String,Object> deleteCardReturn(Integer returnId){
   		
   		bigcontrolDao.deleteCardReturn(returnId);
   		return MyUtil.putMapParams("state",1);
   	}
	//---------------------------------幼儿园类型-----------------------	
		public Map<String, Object> addGartentype(String token, String mark, String typeName) {
			Employee em= bigcontrolDao.findEmployeeByToken(token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0);
			 if(null!=em){
					Map<String,Object> param=MyUtil.putMapParams("operator",em.getName(),"mark", mark,"typeName",typeName);
					bigcontrolDao.addGartentype(param);
					MyUtil.putMapParams(result,"state", 1);
			 }
			return result;
		}

		public Map<String, Object> deleteGartentype(Integer gartenType) {
			 Map<String,Object> result=MyUtil.putMapParams("state", 1);
			bigcontrolDao.deleteGartentype(gartenType);
			return result;
		}

		public Map<String, Object> findGartentype(Integer pageNo) {
			List<GartenType> g=bigcontrolDao.findGartentype();
			 Map<String,Object> result=MyUtil.putMapParams("state", 1,"info",g);
			return result;
		}
			
			
		public  Map<String, Object> importAttendanceNo(String str , String fileName){
			
			Map<String, Object> result = MyUtil.putMapParams("state",0);
			if(str != null){
				//是否是excel
				//if(file.getOriginalFilename().matches("^.+\\.(?i)((xls)|(xlsx))$")){
				if(fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
					File file = LyUtils.base64ToFile(str,fileName);
					List<AttendanceNo> list = ExcelUtil.importAttendanceNo(file, fileName);
					file = new File("D:/", fileName);
					file.delete();
					
					bigcontrolDao.updateAttendanceNoAll(list);
					MyUtil.putMapParams(result,"state", 1);
				}
			}
			return result;
		}	

		
		public  Map<String, Object> downloadAttendanceNoTemplate(HttpServletResponse response) throws Exception{
			
			ArrayList<Object> list = new ArrayList<>();
			String [] titles= {"id","考勤卡号1","考勤卡号2","考勤卡号3"};
	   		String [] fields = null;
	   		
	   		
	   		response.setContentType("application/x-execl");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("考勤卡列表.xls".getBytes(), "ISO-8859-1"));
	   		
	   		
	   		ServletOutputStream outputStream = response.getOutputStream();
			ExcelUtils.Export_(titles, fields, list, outputStream);
			
			
			return  null;
		}
}
