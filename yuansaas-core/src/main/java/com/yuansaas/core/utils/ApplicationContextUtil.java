package com.yuansaas.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * spring context 工具类
 *
 * @author LXZ 2026/1/18 19:55
 */
@Slf4j
@Component
public class ApplicationContextUtil {

    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;


    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过名称获取Bean
     *
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    /**
     * 注册Bean实例
     *
     * @param beanName
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> boolean registerBean(String beanName, T bean) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        context.getBeanFactory().registerSingleton(beanName, bean);
        log.debug("【SpringContextUtil】注册实例“{}”到spring容器：{}", beanName, bean);
        return true;
    }

    /**
     * 根据泛型获取 bean
     *
     * @param type
     * @param generics
     * @param <T>
     * @return
     */
    public static <T> T getBeanByGenericType(Class<T> type, Class<?>... generics) {
        ObjectProvider<T> beanProvider = applicationContext.getBeanProvider(ResolvableType.forClassWithGenerics(type, generics));
        return beanProvider.getObject();
    }
}
