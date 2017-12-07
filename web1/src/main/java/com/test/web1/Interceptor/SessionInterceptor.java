package com.test.web1.Interceptor;

import com.test.mysql.entity.SessionInfo;
import com.test.web1.constant.Constants;
import com.test.web1.service.SessionInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/11/8.
 */
public class SessionInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
    @Autowired
    SessionInfoService sessionInfoService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("sessionInterceptor executed");
        String sessionKey = httpServletRequest.getHeader(Constants.WX_HEADER_SKEY);
        if("".equals(sessionKey)||sessionKey==null){
            return true;
        }else{
            //sessionInfoService = (SessionInfoService) SpringApplicationContextHolder.getSpringBean("sessionInfoService");
            SessionInfo sessionInfo= sessionInfoService.getSessionInfoBySessionkey(sessionKey);
            if(null!=sessionInfo){
                //httpServletRequest.setAttribute("skey",sessionInfo);
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
