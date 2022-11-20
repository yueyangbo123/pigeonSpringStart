package com.yyb.framework.pigeon.interceptors;

import com.yyb.framework.pigeon.interfaces.RequestLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author YueYB
 * @version 1.0
 * @date 2022/11/20
 * @description
 */

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    private static final  ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequestLog methodAnnotation = handlerMethod.getMethodAnnotation(RequestLog.class);
        if(methodAnnotation != null){
            long start = System.currentTimeMillis();
            THREAD_LOCAL.set(start);
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequestLog methodAnnotation = handlerMethod.getMethodAnnotation(RequestLog.class);
        if(methodAnnotation != null){
            Method method = handlerMethod.getMethod();
            String requestUri = request.getRequestURI();
            String methodName = method.getDeclaringClass().getName()+":"+method.getName();
            String desc = methodAnnotation.desc();
            long end = System.currentTimeMillis();
            long start = THREAD_LOCAL.get();
            long l = end - start;
            THREAD_LOCAL.remove();
            log.info("请求路径：{}，请求方法：{}，描述信息：{}，总计耗时：{}",requestUri,methodName,desc,l);
        }
    }
}