package com.yuansaas.app.template.repository;

import com.yuansaas.app.template.entity.TemplateFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * 模版功能配置关系表 - 数据库操作
 *
 * @author LXZ 2026/2/3 17:57
 */
public interface TemplateFeatureRepository extends JpaRepository<TemplateFeature, Long> {
    /**
     * 根据模版code获取功能code列表
     */
    @Query(value = "select  feature_code from template_feature where template_code = :templateCode ",nativeQuery = true)
    List<String> findFeatureCodeListByTemplateCode(@Param("templateCode") String templateCode);
    /**
     * 移除模版里面的功能
     */
    @Query(value = "delete from template_feature where template_code = :templateCode and feature_code in (:featureCodes)"  , nativeQuery = true)
    void removeByFeatureCodesAndTemplateCode(@Param("templateCode") String templateCode , @Param("featureCodes") List<String> featureCodes);
}
