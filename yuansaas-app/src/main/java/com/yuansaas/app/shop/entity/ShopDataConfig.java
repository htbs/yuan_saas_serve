package com.yuansaas.app.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import com.yuansaas.common.model.AddressModel;
import com.yuansaas.core.jpa.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * 商家基本信息数据配置表
 *
 * @author LXZ 2025/12/12 15:12
 */
@Data
@Entity
@Table(name = "shop_data_config")
public class ShopDataConfig extends BaseEntity {
    /**
     * '店铺code'
     */
    private String  shopCode;
    /**
     * '店铺logo'
     */
    private String  logo;
    /**
     * '店铺简介'
     */
    private String  intro;
    /**
     * 手机号
     */
    private String  phone;
    /**
     * 主题色
     */
    private String subjectColor;
    /**
     * 客服微信
     */
    private String customerServiceWechat;
    /**
     * 公众号
     */
    private String officialAccounts;
    /**
     * '标签'
     */
    @Type(value = JsonStringType.class)
    @Column(columnDefinition = "json")
    private String  label;

//    privateis_unified BOOLEAN DEFAULT false, -- 是否开启统一设置
    /**
     * 统一开始时间，如 09:00
     */
    @JsonFormat(pattern = "HH:mm:ss" )
    private LocalTime startTime;
    /**
     * 统一结束时间，如 18:00
     */
    @JsonFormat(pattern = "HH:mm:ss" )
    private LocalTime endTime ;
}
