package com.sjw.test.common.aspect;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.sjw.test.common.vo.Response;
import com.sjw.test.entity.log.Log;
import com.sjw.test.entity.user.vo.UserInfoDetails;
import com.sjw.test.service.log.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/21 16:39
 */
@Slf4j
@Component
@Aspect
public class LogAspect {


    @Autowired
    private LogService logService;

    /**
     * 记录系统审计日志
     * @param proceedingJoinPoint
     * @return
     */
    @Around("@annotation(logAnnotation)")
    public Object saveLog(ProceedingJoinPoint proceedingJoinPoint,LogAnnotation logAnnotation){

        if (log.isDebugEnabled()){
            log.debug("Enter: {}.{}() with argument[s] = {}",
                    proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                    proceedingJoinPoint.getSignature().getName(),
                    Arrays.toString(proceedingJoinPoint.getArgs()));
        }
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Object result = null;
        Throwable throwable = null;
        try {
            //Object[] dtos = proceedingJoinPoint.getArgs();
            //AppDto appDto = (AppDto) dtos[0];
            result = proceedingJoinPoint.proceed();
            if (log.isDebugEnabled()){
                log.debug("Exit: {}.{}() with result = {}",
                        proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                        proceedingJoinPoint.getSignature().getName(), result);
            }
            return result;
        }catch (Throwable e){
            log.error("Error: {} in {}.{}(),cause by:{}.",
                    Arrays.toString(proceedingJoinPoint.getArgs()),
                    proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                    proceedingJoinPoint.getSignature().getName(),
                    e.getMessage());
            throwable = e;
            result = Response.failed();
            return result;
        } finally {
            try {
                doSaveLog(result,request,throwable,logAnnotation);
            } catch (Exception ex) {
                log.error(ex.getMessage(),ex);
            }
        }
    }

    private void doSaveLog(Object result,HttpServletRequest request,Throwable throwable,LogAnnotation logAnnotation ){
           Date date=new Date();
        if(result instanceof Response){
            Response response= (Response) result;

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth.getPrincipal().equals("anonymousUser")){
                log.error("未登录");
                return;
            }
            UserInfoDetails userInfoDetails=(UserInfoDetails)auth.getPrincipal();
            log.info("userInfoDetails；{}",userInfoDetails);
            String username = userInfoDetails.getUsername();
            String apiUri = request.getRequestURI();
            String apiName=logAnnotation.module();
            // 动作
            String action = logAnnotation.action();
            if(StringUtils.isEmpty(action)) {
                action = apiName;
            }
            String content = logAnnotation.content();
            if(StringUtils.isEmpty(content)) {
                content = generateLogContent(username,apiName,action,date);
            }
            Log logInfo=new Log();
            logInfo.setCtime(date);
            logInfo.setUsername(username);
            logInfo.setApiName(apiName);
            logInfo.setApiUri(apiUri);
            logInfo.setContent(content);
            logInfo.setCode(response.getCode());
            logInfo.setCodeName(response.getMessage());
            logService.saveLog(logInfo);
            log.info("loInfo:{}",logInfo.toString());

        }
    }
    /**
     * 生成日志信息
     *
     * @param username
     * @param apiName
     * @param opDate
     * @return
     */
    private String generateLogContent(String username,String apiName,String action, Date opDate) {
        String format = "用户[%s]在[%tF %tR]操作了[%s]";
        return String.format(format,username,opDate,opDate,action);
    }
}
