package com.test.web1.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/** 
 * 获取SpringBean工具类
* @author jiaxd
* @version 2016年3月9日 上午11:30:40
*/
public class SpringApplicationContextHolder implements ApplicationContextAware {

	private static ApplicationContext context;
	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

   
    public static Object getSpringBean(String beanName) {
        return context==null?null:context.getBean(beanName);
    }

    public static String[] getBeanDefinitionNames() {
        return context.getBeanDefinitionNames();
    }
}
