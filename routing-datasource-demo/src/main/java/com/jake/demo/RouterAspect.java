package com.jake.demo;

import java.lang.annotation.Annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.jake.demo.annotation.DataSourceRouter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class RouterAspect {

    @Pointcut("@annotation(com.jake.demo.datasource.DataSourceRouter)")
    private void router() {
    }

    @Before("router()")
    public void datasourceRouter(JoinPoint joinPoint) throws Throwable {
        @SuppressWarnings("unchecked")
        Annotation annotation = joinPoint.getSignature().getDeclaringType().getAnnotation(DataSourceRouter.class);
        if (DataSourceRouter.class.isInstance(annotation)) {
            DataSourceContextHolder.setType(((DataSourceRouter) annotation).value());
        }

        log.info("after aspect datasource type: {}", DataSourceContextHolder.getType());
    }
}
