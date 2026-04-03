package com.yuansaas.app.feature.repository;

import com.yuansaas.app.feature.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * 功能表 - 数据库操作
 *
 * @author LXZ 2026/1/29 17:47
 */
public interface FeatureRepository extends JpaRepository<Feature , Long> {

    /**
     *  查询功能code是否存在
     */
    @Query(value = "select count(1) from feature where feature_code = :featureCode and delete_status = 'N' " , nativeQuery = true)
    Integer countByFeatureCode(@Param("featureCode") String featureCode);


    /**
     *  查询功能code是否存在
     */
    @Query(value = "select feature_code from feature where feature_code in (:featureCodes) and lock_status = 'N' and  delete_status = 'N' " , nativeQuery = true)
    List<String> getFeatureCodeListByFeatureCodes(@Param("featureCodes") List<String> featureCode);

    /**
     *  根据功能code查询功能
     */
    @Query(value = "select * from feature where feature_code in (:featureCodes) and lock_status = 'N' and  delete_status = 'N' " , nativeQuery = true)
    List<Feature> getFeatureListByFeatureCodes(@Param("featureCodes") List<String> featureCode);

    /**
     *  根据行业类型获取功能code
     */
    @Query(value = "select * from feature where industry_type =:industryType and  delete_status = 'N' " , nativeQuery = true)
    List<Feature> getFeatureCodeListByIndustryType(@Param("industryType") String industryType);
}
