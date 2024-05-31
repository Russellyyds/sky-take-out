package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {

    }

    /**
     * 前置通知，在通知中进行公共字段的赋值
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws Exception {
        log.info("开进行公共字段自动填充");
        //获取到当前被拦截方法的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        System.out.println(signature);
        System.out.println(signature.getMethod());
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        System.out.println(annotation);
        OperationType operationType = annotation.value();

        //获取到当前被拦截方法的参数--实体对象
        Object[] args = joinPoint.getArgs();
        //防止一波空指针
        if (args.length==0 ||args==null){
            return;
        }
        Object entity=args[0];
        //准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        //根据当前不同的操作类型，为对应的属性通过反射赋值

        if (operationType== OperationType.INSERT){
            System.out.println("INSERT");
            Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
            //通过反射为对象属性赋值
            setCreateUser.invoke(entity,currentId);
            setCreateTime.invoke(entity,now);
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentId);
        }else if (operationType== OperationType.UPDATE){
            System.out.println("UPDATE");
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
            //通过反射为对象属性赋值
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentId);
        }
    }
}
