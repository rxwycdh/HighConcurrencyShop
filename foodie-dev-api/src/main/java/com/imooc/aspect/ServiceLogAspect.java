package com.imooc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @FileName ServiceLogAspect
 * @Description
 * @Author jiuhao
 * @Date 2020/5/18 15:01
 * @Modified
 * @Version 1.0
 */
@Component
@Aspect
public class ServiceLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * AOP：
     * 1.前置通知:再方法调用之前执行
     * 2.后置通知:在方法正常调用之后执行
     * 3.环绕通知
     * 4.异常通知
     * 5.最终通知:在方法调用之后执行(无论是否异常)
     */
    /**
     * 第一处 代表方法返回类型 * 代表所有类型
     * 第二处 包名 代表aop所监控的类所在的包
     * 第三处 .. 代表该包以及子包吓得所有类方法
     * 第四处 代表类名 * 代表所有类
     * 第五处 *(..) 类下面的所有方法 ..代表方法中可以是任何参数
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.imooc.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.info("====== 开始执行 {}.{} ======",
                joinPoint.getTarget().getClass(), // 目标类
                joinPoint.getSignature().getName()); // 目标方法名
        // 记录开始时间
        long begin = System.currentTimeMillis();
        // 执行方法
        Object result = joinPoint.proceed();
        // 记录结束时间
        long end = System.currentTimeMillis();
        long takeTime = end - begin;
        if(takeTime > 3000) {
            LOGGER.error("======= 执行结束，耗时:{}毫秒 ======", takeTime);
        }else if(takeTime > 2000) {
            LOGGER.warn("======= 执行结束，耗时:{}毫秒 ======", takeTime);
        }else {
            LOGGER.info("======= 执行结束，耗时:{}毫秒 ======", takeTime);
        }
        return result;
    }
}
