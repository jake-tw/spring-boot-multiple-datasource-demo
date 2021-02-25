package com.jake.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jake.demo.model.DataSourceType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataSourceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String group = request.getHeader("group");
        log.info("request header: {}", group);
        if (StringUtils.hasLength(group)) {
            DataSourceContextHolder.setType(DataSourceType.MAIN);
        } else {
            DataSourceContextHolder.setType(DataSourceType.SUB);
        }

        log.info("interceptor datasource type: {}", DataSourceContextHolder.getType());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        log.info("clear datasource type.");
        DataSourceContextHolder.clearType();
    }
}
