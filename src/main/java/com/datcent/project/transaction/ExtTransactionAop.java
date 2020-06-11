package com.datcent.project.transaction;

import com.datcent.project.transaction.utils.TransactionUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

@Slf4j
@Aspect
@Component
public class ExtTransactionAop {


    @Autowired
    private TransactionUtils transactionUtils;


    @Around(value = "@annotation(com.datcent.project.transaction.TaoTransaction)")
    public Object around(ProceedingJoinPoint joinPoint) {
        TransactionStatus begin = null;
        try {
            System.out.println(">>>开启事务");
            begin = transactionUtils.begin();
            Object result = joinPoint.proceed();
            System.out.println(">>>提交事务");
            transactionUtils.commit(begin);
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println(">>>回滚事务");
            if (begin != null) {
                transactionUtils.rollback(begin);
            }
            return "系统错误";
        }
    }
}
