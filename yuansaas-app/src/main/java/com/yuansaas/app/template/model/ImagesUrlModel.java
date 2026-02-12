package com.yuansaas.app.template.model;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * 预览图model
 *
 * @author LXZ 2026/2/5 18:44
 */
@Data
public class ImagesUrlModel implements Serializable {

    /**
     * 类型 （首页 ， 我的 ， 商城 ...）前端自己命名code
     */
    private String type;
    /**
     * 图片类型
     */
    private String imageType;
    /**
     * 图片地址
     */
    private String imageRefer;
    /**
     * 描述
     */
    private String remark;
}
