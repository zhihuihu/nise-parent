/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.common.dto;

import lombok.Data;

import java.util.List;

/**
 * 分页和排序请求对象
 * @author huzhi
 * @version $ v 0.1 2021/11/15 19:39 huzhi Exp $$
 */
@Data
public class PageSortReq {

    /** 当前页 */
    private Long current = 1L;
    /** 一页大小 */
    private Long size = 10L;
    /** 排序 */
    private List<SortParam> sorts;
    /**
     * 排序参数
     */
    @Data
    private static class SortParam{

        /** 排序参数名称 */
        private String param;
        /** 排序类型 desc或者acs */
        private String order;
    }
}
