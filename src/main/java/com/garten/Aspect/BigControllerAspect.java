package com.garten.Aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BigControllerAspect {

	private Logger logger =Logger.getLogger(BigControllerAspect.class) ;
	/*@Autowired
	private BigcontrolDao bigcontrolDao;
	*/
	@Pointcut("execution(* com.garten.service.*.*(..))")
	public void pointCut(){
	         
	}
	     
	@Before("pointCut()")
	public void begin(){
		logger.info("开启切面~~~~");
		//System.err.println(req.getRemoteHost());
		/*Map<String, Object> params = MyUtil.putMapParams("phoneNumber", phoneNumber, "pwd", pwd);
		Employee employee = bigcontrolDao.findEmployeeByPwd(params);
		System.out.println(employee);*/
		System.out.println("开启切面");
	}
	
	@After("pointCut()")
	public void close(){
		System.out.println("关闭切面");
	}
	     
	
}
