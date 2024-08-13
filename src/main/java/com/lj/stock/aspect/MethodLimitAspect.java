package com.lj.stock.aspect;

import com.lj.stock.annotation.Limit;
import com.lj.stock.exception.MethodLimitException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class MethodLimitAspect {
    @Resource
    private HttpServletRequest httpServletRequest;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 给方法限流
     * @param joinPoint
     * @return
     */
    @SneakyThrows
    @Before("@annotation(com.lj.stock.annotation.Limit)")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        //获取客户端请求地址
        String remoteIp = httpServletRequest.getRemoteAddr();
        //进行限流操作
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        long limitTime = methodSignature.getMethod().getAnnotation(Limit.class).limitTime();
        Boolean isSuccess = ops.setIfAbsent(remoteIp, "客户端限流中", limitTime, TimeUnit.SECONDS);
        if (!isSuccess){
            throw new MethodLimitException("访问过于频繁,请稍后再试!",1001);
        }
    }
}
