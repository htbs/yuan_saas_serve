package com.yuansaas.core.valid;

import cn.hutool.core.util.StrUtil;
import com.yuansaas.core.utils.ValidationUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 * 自定义手机号验证
 * @author LXZ 2026/1/28 19:54
 */
public class HttpValidator implements ConstraintValidator<Http, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        // 如果url为空，默认不校验，即校验通过
        if (StrUtil.isEmpty(s)) {
            return true;
        }
        // 校验url
        return ValidationUtils.isURL(s);
    }
}
