package com.hyerp.common;

import lombok.Data;
import java.util.List;

/**
 * 分页结果类
 */
@Data
public class PageResult<T> {
    private List<T> list;
    private Long total;

    public PageResult() {
    }

    public PageResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }
}

