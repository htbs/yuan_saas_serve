package com.yuansaas.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * 属性操作类
 *
 * @author LXZ 2025/10/16 18:10
 */
public class BeanUtils {

    /**
     * 复制存在的属性
     *
     * @param source
     * @param target
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T copyExistProperties(S source, T target) {
        if (!ObjectUtils.isEmpty(source) && !ObjectUtils.isEmpty(target)) {
            copyProperties(source, target, "serialVersionUID");
            return target;
        }
        return null;
    }

    /**
     * copy属性(为空的不会copy)，忽略ignoreProperties
     *
     * @param source           原对象
     * @param target           目标对象
     * @param ignoreProperties 忽略的属性
     * @param <T>              目标对象类型
     * @return 目标对象
     * @throws BeansException 异常
     */
    public static <T> T copyProperties(Object source, T target, String... ignoreProperties) throws BeansException {
        copyProperties(source, target, false, ignoreProperties);
        return target;
    }

    /**
     * 属性copy，将source值copy到target
     *
     * @param source
     * @param target
     * @param copyNull
     * @param ignoreProperties
     * @throws BeansException
     */
    private static void copyProperties(Object source, Object target, boolean copyNull, String... ignoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> targetClass = target.getClass();
        PropertyDescriptor[] targetPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(targetClass);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            if (Objects.nonNull(ignoreList) && ignoreList.contains(targetPd.getName())) {
                continue;
            }
            Method targetPdWriteMethod = targetPd.getWriteMethod();
            if (Objects.isNull(targetPdWriteMethod)) {
                continue;
            }
            PropertyDescriptor sourcePd = org.springframework.beans.BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
            if (Objects.isNull(sourcePd)) {
                continue;
            }
            Method sourcePdReadMethod = sourcePd.getReadMethod();
            if (Objects.isNull(sourcePdReadMethod)) {
                continue;
            }
            try {
                if (!Modifier.isPublic(sourcePdReadMethod.getDeclaringClass().getModifiers())) {
                    sourcePdReadMethod.setAccessible(true);
                }
                Object sourceValue = sourcePdReadMethod.invoke(source);
                Function<Object, Object> internalPropertiesInvokeFunction = getInternalPropertiesInvokeFunction(targetPdWriteMethod, sourcePdReadMethod, sourceValue);
                invokeCopy(source, target, sourceValue, targetPd, targetPdWriteMethod, sourcePdReadMethod, copyNull, internalPropertiesInvokeFunction);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * 获取需要调用的函数
     *
     * @param targetPdWriteMethod 原始写方法
     * @param sourcePdReadMethod  原始读方法
     * @param sourceValue         原始值
     * @return 转换函数
     */
    private static Function<Object, Object> getInternalPropertiesInvokeFunction(Method targetPdWriteMethod, Method sourcePdReadMethod, Object sourceValue) {
        Class<?>[] targetParameterTypes = targetPdWriteMethod.getParameterTypes();
        if (ObjectUtils.isEmpty(targetParameterTypes)) {
            return null;
        }
        Class<?> targetFieldType = targetParameterTypes[0];
        Class<?> sourceFieldType = sourcePdReadMethod.getReturnType();
        if (ClassUtils.isAssignable(targetFieldType, sourceFieldType)) {
            // 如果目标类型和原类型为集合类型
            if (Collection.class.isAssignableFrom(targetFieldType)) {
                // 集合转换（不可以默认强转）
                final CollectionConverter collectionConverter = new CollectionConverter(targetFieldType);
                return sourceObj -> collectionConverter.convert(sourceObj, null);
            }
            // 如果类型相同，或者target是source的父类则直接复制
            return Function.identity();
        }
        // 可使用Jackson自定义转换, 如果json转换失败，再使用默认转换器转换
        if (JacksonConversion.canConvert(sourceFieldType, targetPdWriteMethod, sourceValue)) {
            return sourceObj -> JacksonConversion.convert(sourceObj, targetPdWriteMethod);
        }
        // 可以使用默认转换器
        if (CustomConversion.canConvert(sourceFieldType, targetFieldType)) {
            return sourceObj -> CustomConversion.convert(sourceObj, targetFieldType);
        }
        // 对象和对象直接Copy则将原对象转为JsonString 赋值给目标对象
        if (!ObjectUtils.isEmpty(sourceValue) && ConversionTypeDecide.noneSimpleType(sourceFieldType) && ConversionTypeDecide.noneSimpleType(targetFieldType)) {
            return sourceObj -> {
                String sourceJson = JacksonUtil.writeValueAsString(sourceObj);
                return JacksonUtil.readValue(sourceJson, targetFieldType);
            };
        }
        return null;
    }
}
