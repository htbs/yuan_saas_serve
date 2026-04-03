package com.yuansaas.app.shop.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yuansaas.app.order.enums.PayChannelEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * 签约
 *
 * @author LXZ 2025/12/15 17:16
 */
@Data
public class SignedParam {
    /**
     * 商家id
     */
    @NotNull(message = "商家id不能为空")
    private Long id;
    /**
     * 签约人
     */
    @NotBlank(message = "签约人不能为空")
    private String name;
    /**
     * 签约时间
     */
    @NotNull(message = "签约时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate signedStartAt;
    /**
     * 到期时间
     */
    @NotNull(message = "到期时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate signedEndAt;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 支付金额 （分）
     */
    private Long payAmount;
    /**
     * 支付渠道
     * @see PayChannelEnum
     */
    private PayChannelEnum payChannel;

}
