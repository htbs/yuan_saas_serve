package com.yuansaas.app.shop.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * 日常安排 配置表
 *
 * @author LXZ 2026/1/11 16:06
 */
@Data
@Entity
@Table(name = "shop_regular_hours")
public class ShopRegularHours  extends BaseEntity {

    /**
     * 店铺编码
     */
    private String shopCode;
    /**
     * 1-7: 周一到周日
     */
    private Integer dayOfWeek;
    /**
     * 开关
     */
    private String isOpen;
    /**
     * 时间配置 [{"start":"09:00","end":"18:00"}, ...]
     */
    private String timeSlots;
}
