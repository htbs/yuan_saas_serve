package com.yuansaas.user.users.entity;

import com.yuansaas.core.jpa.model.BaseEntity;
import com.yuansaas.user.common.enums.UserStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * 商铺用户信息表
 *
 * @author LXZ 2026/1/22 18:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "shop_user")
public class ShopUser extends BaseEntity {
    /**
     * 商铺code
     */
    private String shopCode;
    /**
     * 用户账号
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 岗位编号
     */
    private String postCode;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String headUrl;
    /**
     * 用户性别
     */
    private String sex;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private String loginDate;
    /**
     * 账户状态
     */
    private String status = UserStatus.active.name();
}
