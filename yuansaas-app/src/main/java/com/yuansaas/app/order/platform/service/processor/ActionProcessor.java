package com.yuansaas.app.order.platform.service.processor;

import com.yuansaas.app.order.platform.enums.OrderAction;
import com.yuansaas.app.order.platform.params.SubmitOrderParam;
import com.yuansaas.core.utils.ApplicationContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 *
 * 订单处理器
 *
 * @author LXZ 2026/1/18 17:06
 */
public abstract class ActionProcessor {

    private static final String BEAN_NAME_SUFFIX = "OrderProcessor";


    /**
     * 提交订单
     */
    public static Boolean submit(SubmitOrderParam submitOrderParam) {
        return getProcessor(OrderAction.submit).process(submitOrderParam);

    }
    public Boolean process(SubmitOrderParam rpOrderParam) {return true;}


    private static ActionProcessor getProcessor(OrderAction action) {
        String beanName = action.name().concat(BEAN_NAME_SUFFIX);
        ActionProcessor processor = null;
        try {
            processor = ApplicationContextUtil.getBean(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            throw new RuntimeException("未找到对应的流程处理器:" + beanName);
        }
        return processor;
    }


}
