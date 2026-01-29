package com.yuansaas.core.utils;

import org.springframework.util.StringUtils;
import java.util.regex.Pattern;

/**
 * 校验工具类
 *
 * @author liuxuzhao
 */
public class ValidationUtils {

    private static final Pattern PATTERN_MOBILE = Pattern.compile("^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[0,1,4-9])|(?:5[0-3,5-9])|(?:6[2,5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[0-3,5-9]))\\d{8}$");

    private static final Pattern PATTERN_URL = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");


    /**
     * 手机号验证
     */
    public static boolean isPhone(String phone) {
        return StringUtils.hasText(phone)
                && PATTERN_MOBILE.matcher(phone).matches();
    }

    /**
     * url验证
     */
    public static boolean isURL(String url) {
        return StringUtils.hasText(url)
                && PATTERN_URL.matcher(url).matches();
    }


}
