package com.test.web1.webconfig;

import com.test.web1.Interceptor.SessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2017/11/8.
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        HandlerInterceptor interceptor = getMyInterceptor();
        InterceptorRegistration addInterceptor = registry.addInterceptor(interceptor);
        addInterceptor.addPathPatterns("/weapp/**");
//        addInterceptor.excludePathPatterns("/*/login");
//        addInterceptor.excludePathPatterns("/**.html");
//        addInterceptor.excludePathPatterns("/");
        super.addInterceptors(registry);
    }

    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new SessionInterceptor();
    }
}
