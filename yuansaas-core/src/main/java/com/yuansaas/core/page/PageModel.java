package com.yuansaas.core.page;

import jakarta.validation.constraints.Min;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.el.util.Validation;
import org.hibernate.query.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.ValidationUtils;

import java.util.List;

/**
 *
 * 分页model
 *
 * @author LXZ 2025/10/16 17:12
 */
@NoArgsConstructor
@Setter
public class PageModel {
    /**
     * 查询的页码
     */
    @Min(value = 1, message = "pageNo必须为大于1的整数")
    private Integer pageNo;
    /**
     * 每页显示条数
     */
    @Min(value = 1, message = "pageSize必须为大于1的整数")
    private Integer pageSize;


    /**
     * 分页对象
     */
    private Page rPage;

    public PageModel(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        this.setPageNo(ObjectUtils.isEmpty(this.pageNo) ? 1 : this.pageNo);
        return this.pageNo;
    }

    public Integer getPageSize() {
        this.setPageSize(ObjectUtils.isEmpty(this.pageSize) ? 10 : this.pageSize);
        return this.pageSize;
    }

    public PagedModel.PageMetadata getPage() {
        if (ObjectUtils.isEmpty(rPage)) {
            rPage = Page.page(this.getPageNo(), this.getPageSize());
        }
        return rPage;
    }

    public <T> Page<T> getRPage(List<T> content, long totalElements) {
        Page rPage = getPage();
        rPage.setContent(content);
        rPage.setTotalElements(totalElements);
        return rPage;
    }

    /**
     * 获取查询的offset
     *
     * @return
     */
    public Integer obtainOffset() {
        return (this.getPageNo() <= 0 ? 0 : this.getPageNo() - 1) * getPageSize();
    }
}
