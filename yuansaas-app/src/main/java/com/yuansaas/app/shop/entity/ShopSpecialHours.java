package com.yuansaas.app.shop.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

/**
 *
 * 特殊安排 配置表
 *
 * @author LXZ 2026/1/11 16:06
 */
@Data
@Entity
@Table(name = "shop_regular_hours")
public class ShopSpecialHours extends BaseEntity {

    /**
     * 店铺编码
     */
    private String shopCode;
    /**
     * 开关
     */
    private Boolean isOpen;
    /**
     * 标题 如：春节、店庆
     */
    private String title;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 时间配置 [{"start":"09:00","end":"18:00"}, ...]
     */
    private String timeSlots;

}
