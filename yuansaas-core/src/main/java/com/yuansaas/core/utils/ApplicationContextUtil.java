package com.yuansaas.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class ApplicationContextUtil implements ApplicationContextAware {

    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;


    @Override
    public  void setApplicationContext(ApplicationContext applicationContext) throws BootstrapMethodError {
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
        checkApplicationContext();
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
        checkApplicationContext();
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
        checkApplicationContext();
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
        checkApplicationContext();
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
        checkApplicationContext();
        ObjectProvider<T> beanProvider = applicationContext.getBeanProvider(ResolvableType.forClassWithGenerics(type, generics));
        return beanProvider.getObject();
    }

    /**
     * 检查 ApplicationContext 是否已初始化
     */
    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException(
                    "ApplicationContext 未初始化，可能原因：\n" +
                            "1. ApplicationContextUtil 没有被 Spring 扫描到\n" +
                            "2. 在 Spring 初始化完成前调用了 getBean 方法\n" +
                            "3. ApplicationContextUtil 没有实现 ApplicationContextAware 接口"
            );
        }
    }
}
