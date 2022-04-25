package com.vector.springbootshiro.Utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author WangJiaHui
 * @description:
 * @ClassName ApplicationContextUtils
 * @date 2022/4/24 19:10
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }
}
