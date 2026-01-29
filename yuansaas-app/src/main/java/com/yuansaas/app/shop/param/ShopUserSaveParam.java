package com.yuansaas.app.shop.param;

import com.yuansaas.core.valid.Http;
import com.yuansaas.core.valid.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 商铺用户保存参数
 *
 * @author LXZ 2026/1/28 19:44
 */
@Data
public class ShopUserSaveParam implements Serializable {


    /**
     * 商铺code
     */
    private String shopCode;
    /**
     *  用户账号
     */
    @NotBlank(message = "用户账号不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,30}$", message = "用户账号由 数字、字母 组成")
    @Size(min = 4, max = 30, message = "用户账号长度为 4-30 个字符")
    private String userName;
    /**
     * 用户昵称
     */
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;
    /**
     * 用户名字
     */
    @Size(max = 30, message = "用户名字长度不能超过30个字符")
    private String realName;

    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 角色id
     */
    private List<Long> roleId;
    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过 50 个字符")
    private String email;
    /**
     * 手机号
     */
    @Phone
    private String phone;
    /**
     * 性别
     */
    private String sex;
    /**
     * 头像
     */
    @Http
    private String avatar;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
