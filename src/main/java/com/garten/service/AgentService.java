package com.garten.service;



import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garten.dao.AgentDao;
import com.garten.dao.BigcontrolDao;
import com.garten.dao.ParentDao;
import com.garten.dao.PrincipalDao;
import com.garten.dao.SmallcontrolDao;
import com.garten.dao.WorkerDao;
import com.garten.model.agent.AgentAudit;
import com.garten.model.agent.AgentInfo;
import com.garten.model.garten.GartenCharge;
import com.garten.model.garten.GartenClass;
import com.garten.model.garten.GartenInfo;
import com.garten.model.other.Feedback;
import com.garten.model.other.InfoLog;
import com.garten.model.other.Order;
import com.garten.model.parent.ParentInfo;
import com.garten.model.worker.WorkerInfo;
import com.garten.util.lxcutil.MyParamAll;
import com.garten.util.md5.CryptographyUtil;
import com.garten.util.myutil.MyUtil;
import com.garten.util.page.MyPage;
import com.garten.vo.agent.AgentInfoAndGarten;
import com.garten.vo.agent.AgentVisitDetail;
import com.garten.vo.agent.GartenCountTongJi;
import com.garten.vo.agent.GartenSimple;
import com.garten.vo.bigcontrol.AddDetail;
import com.garten.vo.bigcontrol.BabyMessage;
import com.garten.vo.bigcontrol.ClassManageBig;
import com.garten.vo.bigcontrol.GartenClassAll;
import com.garten.vo.bigcontrol.ParentMessage;
import com.garten.vo.bigcontrol.WorkerMessage;
import com.garten.vo.teacher.BabyCheckLogAll;
import com.garten.vo.teacher.ClassManage;
import com.garten.vo.teacher.FlowerAll;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;



@Service
public class AgentService {
	
	@Autowired
	private AgentDao agentDao;
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
	public Map<String, Object> login(String phoneNumber, String pwd) {
		Map<String,Object> param=MyUtil.putMapParams("phoneNumber", phoneNumber,"pwd",CryptographyUtil.md5(pwd, "lxc"));
		AgentInfo agent=agentDao.findAgentByPwd(param);
		String uuid="error";
		Map<String,Object> result=MyUtil.putMapParams("state", 0, "info", uuid);
		//如果worker为空则返回error
		//如果worker不为空则返回uuid,并修改token为uuid
		if(null!=agent&&!"".equals(agent)){
			uuid=agentDao.findToken(param);
			MyUtil.putMapParams(result,"state", 1, "info", uuid,"province",agent.getProvince(),"city",agent.getCity(),"countries",agent.getCountries());
			MyUtil.putMapParams(result,"agentName",agent.getAgentName());
		}
		return result;
	}

	//验证短信  再修改密码
		public Map<String, Object> updateLogin(String phoneNumber, String pwd, String number) throws ParseException {
			 AgentInfo agentInfo=agentDao.findAgentByAccount(phoneNumber);//根据账号查找到用户,手机号
			  Map<String,Object> result=MyUtil.putMapParams("state", 0,"info","没有这个用户");
				if(null!=agentInfo){
					Map<String,Object> duanxin=workerService.duanxin(phoneNumber,5,number);//0代表 老师端短信1家长  2园长 3总控 4代理商
					String newToken= UUID.randomUUID().toString();
					MyUtil.putMapParams(result,"state", 2, "info", "验证码错误");
					if(duanxin.get("0").equals("成功")){
						agentDao.updatePwdToken(MyUtil.putMapParams("token", newToken, "phoneNumber", phoneNumber,"pwd",CryptographyUtil.md5(pwd, "lxc")));
						MyUtil.putMapParams(result,"state", 1, "info", newToken);
					}
				}
				//验证码错误返回  验证错误信息
				return result;
		}

		//获取子代理列表
		public Map<String, Object> childAgent(String token, Integer pageNo) {
			AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
			Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",agentInfo);
			List<AgentInfoAndGarten> list = new ArrayList<AgentInfoAndGarten>();
			long now = new Date().getTime();
			if(null!=agentInfo){//验证用户
				if(agentInfo.getAgentGrade()==1){	//省级代理商
					list = agentDao.findProChildAgent(agentInfo.getProvince());
				}else if(agentInfo.getAgentGrade()==2){	//市级代理商
					list = agentDao.findCityChildAgent(agentInfo.getCity());
				}
				//遍历每一个代理商
				for(AgentInfoAndGarten ag : list){
					Integer gartenAttendanceCount=0;
					Integer gartenMonitorCount=0;
					List<GartenInfo> gartenList = agentDao.findGartenById(ag.getAgentId());
					for(GartenInfo garten : gartenList){
						if(garten.getAttendanceTime()*1000>now){
							gartenAttendanceCount++;
						}
						if(garten.getMonitorTime()*1000>now){
							gartenMonitorCount++;
						}
					}
					ag.setGartenAttendanceCount(gartenAttendanceCount);
					ag.setGartenMonitorCount(gartenMonitorCount);
					ag.setGartenCount(gartenList.size());
				}
				MyUtil.putMapParams(result,"info", MyPage.listPage16(list, pageNo),"state",1);
			}
			return result;
		}
		
		//获取子代理列表(不分页）
				public Map<String, Object> childAgentNoPage(String token) {
					AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
					Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",agentInfo);
					List<AgentInfoAndGarten> list = new ArrayList<AgentInfoAndGarten>();
					if(null!=agentInfo){//验证用户
						if(agentInfo.getAgentGrade()==1){	//省级代理商
							list = agentDao.findProChildAgent(agentInfo.getProvince());
						}else if(agentInfo.getAgentGrade()==2){	//市级代理商
							list = agentDao.findCityChildAgent(agentInfo.getCity());
						}
						MyUtil.putMapParams(result,"info", list,"state",1);
					}
					return result;
				}
		//幼儿园管理列表
		public   Map<String,Object> gartenManage(String token,Integer pageNo,String name,String phoneNumber){
			AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			if(null!=agentInfo){
				List<GartenInfo> list = agentDao.findGartenByAgent(name,phoneNumber,agentInfo.getAgentId());
				MyUtil.putMapParams(result,"info", MyPage.listPage16(list, pageNo), "state", 1);
			}
			return result;
		}
		
		//冻结幼儿园
		public   Map<String,Object> frostGarten(String token,Integer gartenId){
			AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			 if(null!=agentInfo){
				 agentDao.frostGarten(gartenId);
				 MyUtil.putMapParams(result,"state", 1,"info","操作成功" );
			 }
			 return result;
		}
		
		//恢复幼儿园
		public   Map<String,Object> recoverGarten(String token,Integer gartenId){
			AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			 if(null!=agentInfo){
				 agentDao.recoverGarten(gartenId);
				 MyUtil.putMapParams(result,"state", 1,"info","操作成功" );
			 }
			 return result;
		}
		
		//修改幼儿园
		public   Map<String,Object> updateGarten(String token,Integer gartenId,String gartenName,String name,String phoneNumber,
				String contractNumber,Long contractStart ,Long contractEnd,	String organizationCode,String province ,String city,
				String countries,String address,Double charge){
			AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 if(null!=agentInfo){
				 agentDao.updateGarten(gartenId,gartenName, name, phoneNumber,contractNumber,sdf.format(contractStart*1000) ,
						 sdf.format(contractEnd*1000), organizationCode,province ,city,countries, address,charge);
				 MyUtil.putMapParams(result,"state", 1,"info","操作成功" );
			 }
			 return result;
		}
		
		//开园申请列表
		public   Map<String,Object> applyList(String token,Integer pageNo ){
			AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			 if(null!=agentInfo){
				 List<AgentAudit> list = agentDao.findAgentAudit(agentInfo.getAgentId());
				 MyUtil.putMapParams(result,"info", MyPage.listPage16(list, pageNo), "state", 1);
			 }
			 return result;
		}
		
		//取消申请
		public Map<String,Object> cancelApply(String token,String auditId){
			AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			 if(null!=agentInfo){
				 agentDao.cancelApply(auditId);
				 MyUtil.putMapParams(result,"state", 1,"info","操作成功" );
			 }
			 return result;
		}
		
		//开园申请
		public Map<String,Object> applyGarten(String token,String gartenName,String name,String phoneNumber,String contractNumber,String province,
				String city, String countries,Integer count,Double money,String equipment){
			AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			 if(null!=agentInfo){
				 WorkerInfo worker = smallcontrolDao.findWorkerByPhoneNumber(phoneNumber);
				 if(null!=worker){
					 return MyUtil.putMapParams(result,"state", 2);			//该幼儿园联系手机号码已经被注册
				 }
				 agentDao.addApplyGarten(gartenName,name, phoneNumber, contractNumber, province,
							 city, countries, count, money, equipment,agentInfo.getAgentId());
				 MyUtil.putMapParams(result,"state", 1,"info","操作成功" );
			 }
			
			return result;
			
		}
		
		//添加访园记录
		public Map<String,Object> addVisit(String token,Integer gartenId,String title , String content ){
			AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			 if(null!=agentInfo){
				 agentDao.addVisit(agentInfo.getAgentId(),gartenId,title,content);
				 MyUtil.putMapParams(result,"state", 1,"info","操作成功" );
			 }
			return result;
		}
		
		//访园跟进历史
		public Map<String,Object> agentVisitList(String token,Integer pageNo,Integer gartenId){
			AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			 if(null!=agentInfo){
				 List<AgentVisitDetail> list = agentDao.getAgentVisit(agentInfo.getAgentId(),gartenId);
				 MyUtil.putMapParams(result,"info", MyPage.listPage16(list, pageNo), "state", 1);
			 }
			 return result;
		}
		
		//子代理业绩统计
		public Map<String,Object> childAgentBusiness(String token,Integer agentId,Integer pageNo){
			AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			 List<AgentAudit> list = new ArrayList<AgentAudit>();
			 if(null!=agentInfo){		
				 if(null!=agentId){		//某个子代理商业绩
					 list = agentDao.getChildAgentBusiness(agentId);
				 }else{					//该代理商下子代理业绩
					 List<AgentInfoAndGarten> ChildAgent = new ArrayList<AgentInfoAndGarten>();
					 if(agentInfo.getAgentGrade()==1){	//省级代理商
							ChildAgent = agentDao.findProChildAgent(agentInfo.getProvince());
						}else if(agentInfo.getAgentGrade()==2){	//市级代理商
							ChildAgent = agentDao.findCityChildAgent(agentInfo.getCity());
						}
					 for(AgentInfoAndGarten a:ChildAgent){
						 List<AgentAudit> agentsList = agentDao.getChildAgentBusiness(a.getAgentId());
						 list.addAll(agentsList);
					 }
				 }
				
				 MyUtil.putMapParams(result,"info", MyPage.listPage16(list, pageNo), "state", 1);
			 }
			return result;
		}
		
		public Map<String,Object> agentGarten (String token){
			AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
			 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
			 if(null!=agentInfo){
				 List<GartenSimple> list = agentDao.getGartenNameAndId(agentInfo.getAgentId());
				 
				 MyUtil.putMapParams(result, "info", list, "state", 1);
			 }
			 
			 return result;
		}
		
	//幼儿园详情
	public Map<String,Object> getGartenDetail(String token,Integer agentId) throws ParseException{
		AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
		 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 long now = new Date().getTime();
		 if(null!=agentInfo){
			 List<GartenSimple> list = agentDao.getGartenNameAndId(agentId);
			 //遍历每个幼儿园
			 for(GartenSimple g : list){
				 List<GartenCountTongJi> countList = agentDao.FindParentByGartenId(g.getGartenId());
				//根据幼儿园代理的幼儿园获取家长信息
				 Integer attendanceCount=0;
				 Integer monitorCount=0;
				 for(GartenCountTongJi tj : countList){
					 String[] gartenId = tj.getGartenId().split(",");
					 String[] attendanceTime = tj.getAttendanceTime().split(",");
					 String[] monitorTime = tj.getMonitorTime().split(",");
					
					 for(int i=0 ;i<gartenId.length;i++){
						 if(Integer.valueOf(gartenId[i])==(g.getGartenId())){
							 if (sdf.parse(attendanceTime[i]).getTime()>now) {
								 System.out.println(sdf.parse(attendanceTime[i]).getTime()*1000);
								 attendanceCount++;
							}
							 if (sdf.parse(monitorTime[i]).getTime()>now) {
								 monitorCount++;
							}
							g.setAttendanceCount(attendanceCount);
							g.setMonitorCount(monitorCount);
						 }
					 }
				 }
			 }
			 MyUtil.putMapParams(result,"info", list, "state", 1);
		 }
		 return result;
	}
	
	public Map<String,Object> deleteVisit(String token ,Integer visitId){
		AgentInfo agentInfo= agentDao.findAgentInfoByToken( token);
		 Map<String,Object> result=MyUtil.putMapParams("state", 0,"info",null);
		 if(null!=agentInfo){
			 agentDao.deleteVisit(visitId);
			 MyUtil.putMapParams(result,"state", 1,"info","操作成功" );
		 }
		 return result;
	}
}
