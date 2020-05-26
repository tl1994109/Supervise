package com.datcent.project.zt;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class SystemLogTimerInterceptor {
    private static  final Logger logger= LoggerFactory.getLogger(SystemLogTimerInterceptor.class);


    @Pointcut("@annotation(com.datcent.project.zt.SystemLogTimerAnnotation)")
    public  void handlingTimePointcut(){

    }

    @Around("handlingTimePointcut()")
    public  Object handlingTimeAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature=joinPoint.getSignature();
        MethodSignature methodSignature=(MethodSignature)signature;
        Method method=methodSignature.getMethod();
        SystemLogTimerAnnotation annotation=method.getAnnotation(SystemLogTimerAnnotation.class);
        String methodStr=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();

        logger.info("---------------------------------------------");
        logger.info("请求方法:[{}],请求方法说明:[{}]",methodStr,annotation.value());
        long startTime=System.currentTimeMillis();
        Object proceed=joinPoint.proceed();
        logger.info("执行时长:"+(System.currentTimeMillis()-startTime));
        logger.info("---------------------------------------------");
        return  proceed;
    }



}
