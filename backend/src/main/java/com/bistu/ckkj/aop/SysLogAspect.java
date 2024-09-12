package com.bistu.ckkj.aop;

import com.bistu.ckkj.pojo.Log;
import com.bistu.ckkj.service.LogService;
import com.bistu.ckkj.utils.HttpContextUtils;
import com.bistu.ckkj.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.lang.reflect.Method;

/**
 * 系统日志：切面处理类
 */
@Aspect
@Component
@RequiredArgsConstructor
public class SysLogAspect {

    private final LogService logger;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.bistu.ckkj.aop.MyLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning(pointcut = "execution(* com.bistu.ckkj.controller..*(..)) && !execution(* com.bistu.ckkj.controller.LogController.*(..))")
    public void saveSysLog(JoinPoint joinPoint) {
//        System.out.println("切面。。。。。");
//        //保存日志
//        Log log = new Log();
//
//        //从切面织入点处通过反射机制获取织入点处的方法
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        //获取切入点所在的方法
//        Method method = signature.getMethod();
//        //获取操作
//        MyLog myLog = method.getAnnotation(MyLog.class);
//        String value = null;
//        if (myLog != null) {
//            value = myLog.value();
//            log.setMessage(value);
////            sysLog.setOperation(value);//保存获取的操作
//            System.out.println(value);
//        }
//
//        //获取请求的类名
//        String className = joinPoint.getTarget().getClass().getName();
//        //获取请求的方法名
//        String methodName = method.getName();
////        sysLog.setMethod(className + "." + methodName);
//
//        //请求的参数
//        Object[] args = joinPoint.getArgs();
//        //将参数所在的数组转换成json
////        //获取用户ip地址
//        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//        String IP = com.xuexilema.blog.utils.IpUtils.getIpAddr(request);
//
//        value+="+"+className+"+"+"."+methodName+"("+args+")";
//
//        log.setIp(IP);
//        log.setMessage(value);
//
//        System.out.println(log.toString());
        //保存日志
        Log log = new Log();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

                //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        String value;
        if (myLog != null) {
            value = myLog.value();
            log.setMessage(value);
        }
        //方法名字
        String name = method.getName();
        //通过工具类获取Request对象
        RequestAttributes reqa = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)reqa;
        if (sra==null){
            return;
        }
        HttpServletRequest request = sra.getRequest();
        //访问的url
        String url = request.getRequestURI();
        String message = "方法名:"+name+", url:"+url;
        //请求方式
        String methodName = request.getMethod();

//        //获取用户ip地址
        HttpServletRequest request1 = HttpContextUtils.getHttpServletRequest();
        String IP = IpUtils.getIpAddr(request1);


        log.setIp(IP);
        log.setType(methodName);
        log.setMessage(message);
        logger.insert(log);
//        System.out.println(printlnlog);

    }

}