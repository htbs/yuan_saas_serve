package com.yuansaas.app.order.enums;
import com.yuansaas.common.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 支付渠道
 *
 * @author LXZ 2026/1/19 17:03
 */
@Getter
@AllArgsConstructor
public enum PayChannelEnum  implements IBaseEnum<PayChannelEnum> {

    UNION_PAY ("银行卡支付"),
    ALIPAY("支付宝"),
    WECHAT_PAY("微信支付"),
    CASH("现金支付"),
    CORPORATE_BANK_TRANSFER("对公转账"),
   ;

    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
