package co.in.replete.komalindustries.aspect;

import java.util.Properties;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.ResponseMessage;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;

@Aspect
@Component
public class BaseAspect {

	@Autowired
	Properties responseMessageProperties;
	
	@Pointcut(KomalIndustriesConstants.BASE_POINTCUT)
	public void basePointCut() { }
	
	@Pointcut(KomalIndustriesConstants.EXCEPTION_POINTCUT)
	public void exceptionPointCut() { }
	
	@Before("basePointCut()")
	public void beforeMethod(JoinPoint jp) {/*
		System.out.println("Entering Method : " + jp.getSignature());*/
	}
	
	@After("basePointCut()")
	public void doAfterMethodExecution(JoinPoint jp) {/*
		System.out.println("Exiting Method" + jp.getSignature());*/
	}
	
	@AfterThrowing(pointcut="basePointCut()", throwing="exception")
	public void doAfterThrowingException(Exception exception) {
	}
	
	@Around("exceptionPointCut()")
	public Object doAroundExecution(ProceedingJoinPoint pjp){
		BaseWrapper response = new BaseWrapper();
		try {
			response = (BaseWrapper) pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			response.setResponseMessage(new ResponseMessage(HttpStatus.NOT_IMPLEMENTED.toString(), (null == e.getMessage()) ? KomalIndustriesConstants.ERROR_OCCURED : e.getMessage().trim()));
			return response;
		}

		if(null != response && null == response.getResponseMessage()) {
			response.setResponseMessage(new ResponseMessage(HttpStatus.OK.toString(), KomalIndustriesConstants.SUCCESS_OK));
		}
		
		return response;
		
	}
}
