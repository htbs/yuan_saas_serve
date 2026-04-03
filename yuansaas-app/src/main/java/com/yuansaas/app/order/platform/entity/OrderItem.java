package com.yuansaas.app.order.platform.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import com.yuansaas.app.order.platform.model.MerchantOrderExtModel;
import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

/**
 *
 * 平台订单明细
 *
 * @author LXZ 2026/1/18 14:30
 */
@Data
@Entity
@Table(name = "m_order_item")
public class OrderItem extends BaseEntity {

    /**
     * 订单编号
     */
    private  Long orderNo;
    /**
     * 订单类型
     */
    private  String type;
    /**
     * 商品名称
     */
    private  String merchandiseName;
    /**
     * 商品金额
     */
    private  Integer merchandiseAmount;
    /**
     * 扩展字段
     */
    @Type(value = JsonStringType.class)
    @Column(columnDefinition = "json")
    private MerchantOrderExtModel merchantOrderExt;
}
