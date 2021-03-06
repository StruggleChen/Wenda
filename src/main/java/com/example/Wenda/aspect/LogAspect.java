package com.example.Wenda.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @auther Chen Gong
 * @created by 11/21/18 10:42 PM
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.example.Wenda.controller.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        StringBuffer sb = new StringBuffer();
        for(Object arg: joinPoint.getArgs()){
            sb.append("arg:" + arg.toString() + "|");
        }
        logger.info("before method" + sb.toString());
    }

    @After("execution(* com.example.Wenda.controller.*.*(..))")
    public void afterMethod(){
        logger.info("after method");


    }
}
