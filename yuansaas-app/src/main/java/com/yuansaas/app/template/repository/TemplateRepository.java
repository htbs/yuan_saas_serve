package com.yuansaas.app.template.repository;

import com.yuansaas.app.template.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * 模版表 - 数据库操作
 *
 * @author LXZ 2026/2/3 17:57
 */
public interface TemplateRepository extends JpaRepository<Template , Long> {
}
