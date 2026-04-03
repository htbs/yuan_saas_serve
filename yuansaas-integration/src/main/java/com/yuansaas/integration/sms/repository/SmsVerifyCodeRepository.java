package com.yuansaas.integration.sms.repository;

import com.yuansaas.integration.sms.entity.SmsVerifyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 *
 * @author LXZ 2025/12/23 16:15
 */
public interface SmsVerifyCodeRepository extends JpaRepository<SmsVerifyCode, Long> {

    /**
     * 查询手机获取的最新验证码
     * @param phone 手机号
     * @param type 验证码类型
     */
    @Query(value = "select * from sms_verify_code where phone=:phone and type =:type order by create_at desc limit 1", nativeQuery = true)
    SmsVerifyCode getSmsVerifyCodeByPhoneAndTypes(@Param("phone") String phone, @Param("type")  String type);

    /**
     * 校验验证码
     * @param phone 手机号
     * @param verifyContent 验证码
     * @param serialNo 流水号
     * @param type 验证码类型
     */
    @Query(value = "select * from sms_verify_code where phone=:phone and verify_content=:verifyContent and serial_no=:serialNo and type =:type ", nativeQuery = true)
    SmsVerifyCode getSmsVerifyCode(@Param("phone") String phone, @Param("verifyContent") String verifyContent, @Param("serialNo") String serialNo,  @Param("type")  String type);

}
