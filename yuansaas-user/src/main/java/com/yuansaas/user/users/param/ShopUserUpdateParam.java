package com.yuansaas.user.users.param;

import com.yuansaas.core.valid.Http;
import com.yuansaas.core.valid.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 商铺用户修改参数
 *
 * @author LXZ 2026/1/28 19:44
 */
@Data
public class ShopUserUpdateParam implements Serializable {

    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空")
    private Long id;
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

}
