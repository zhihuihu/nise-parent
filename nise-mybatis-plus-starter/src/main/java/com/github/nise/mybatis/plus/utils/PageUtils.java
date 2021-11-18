/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.mybatis.plus.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.nise.common.dto.PageSortReq;

/**
 * @author huzhi
 * @version $ v 0.1 2021/11/15 7:42 huzhi Exp $$
 */
public class PageUtils {

    /**
     * 返回默认分页参数
     * @param pageSortReq
     * @param <T>
     * @return
     */
    public static <T> Page<T> defaultPage(PageSortReq pageSortReq){
        Page<T> page = new Page<>();
        page.setCurrent(pageSortReq.getCurrent());
        page.setSize(pageSortReq.getSize());
        return page;
    }

}
