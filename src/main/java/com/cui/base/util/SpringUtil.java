package com.cui.base.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring 工具类
 *
 * @author CUI
 * @since 2020-11-07
 */
@Component
@Slf4j
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        log.info("applicationContext={}", applicationContext.getId());
    }

    //通过 class type 获取 Bean
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过 bean name 和 class 获取 Bean对象
     */
    public static <T> T getBeanByNameAndType(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }
}
