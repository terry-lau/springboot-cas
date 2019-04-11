package com.easydatalink.tech.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.easydatalink.tech.annotation.SystemControllerLog;
import com.easydatalink.tech.constants.Constants;
import com.easydatalink.tech.entity.auth.User;
import com.easydatalink.tech.entity.base.SystemLog;
import com.easydatalink.tech.service.base.ISystemLogManager;
import com.easydatalink.tech.utils.CusAccessObjectUtil;
  
/** 
 * 切点类 
 */  
@Aspect  
@Component  
public  class SystemLogAspect {  
    //注入Service用于把日志保存数据库  
	@Autowired    
    private ISystemLogManager systemLogManager;  
    //本地异常日志记录对象  
     private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);  
  
    //Controller层切点  
    @Pointcut("@annotation(com.easydatalink.tech.annotation.SystemControllerLog)")  
     public  void controllerAspect() {  
    }  
  
    /** 
     * 前置通知 用于拦截Controller层记录用户的操作 
     * 
     * @param joinPoint 切点 
     */  
    @Before("controllerAspect()")  
     public  void doBefore(JoinPoint joinPoint) {  
  
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
        //读取session中的用户  
        User user =(User) request.getSession().getAttribute(Constants.USER);  
        //请求的IP  
        String ip =  CusAccessObjectUtil.getIpAddress(request);
         try {  
            SystemLog log = new SystemLog();   
            log.setDescription(getControllerMethodDescription(joinPoint));  
            log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));  
            log.setType(SystemLog.TYPE_CONTROLLER);  
            log.setRequestIp(ip);  
            log.setExceptionCode( null);  
            log.setExceptionDetail( null);  
            log.setParams( null);  
            log.setCreateBy(user.getLoginName());    
            log.setCreateDate(new Date()); 
            log.setPlatformNo(user.getPlatformNo());
            systemLogManager.insert(log);  
        }  catch (Exception e) {  
            //记录本地异常日志  
            logger.error("异常信息:{}", e.getMessage());  
        }  
    }  
  
    /** 
     * 获取注解中对方法的描述信息 用于Controller层注解 
     * 
     * @param joinPoint 切点 
     * @return 方法描述 
     * @throws Exception 
     */  
     @SuppressWarnings("rawtypes")
	public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {  
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
        Class targetClass = Class.forName(targetName);  
        Method[] methods = targetClass.getMethods();  
        String description = "";  
        for (Method method : methods) {  
             if (method.getName().equals(methodName)) {  
                Class[] clazzs = method.getParameterTypes();  
                if (clazzs.length == arguments.length) {  
                    description = method.getAnnotation(SystemControllerLog. class).description();  
                    break;  
                }  
            }  
        }  
       return description;  
    }  
}  
