package com.yuansaas.core.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 签名工具类
 *
 * @author HTB 2026/1/23 11:23
 */
public class SignUtil {

    /**
     * HmacSHA25 方式签名
     * @param data 签名数据
     * @param secret 签名secret
     * @return 签名结果
     */
    public static String singHmacSHA256(String data , String secret){
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec key =
                    new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(key);
            byte[] raw = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(raw);
        } catch (Exception e) {
            throw new RuntimeException("签名失败", e);
        }

    }
}
