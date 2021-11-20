/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.mybatis.plus.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.nise.common.constant.NiseConstant;
import com.github.nise.common.dto.PageSortReq;
import org.springframework.util.ObjectUtils;

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

    /**
     * 完善排序字段
     * 该方法未写容错方法，可能排序字段并不在表中
     * @param queryWrapper
     * @param pageSortReq
     */
    public static void fillOrderBy(QueryWrapper queryWrapper,PageSortReq pageSortReq,boolean changeUnderline){
        if(null != pageSortReq && CollectionUtil.isNotEmpty(pageSortReq.getSorts())){
            for (PageSortReq.SortParam sortParam : pageSortReq.getSorts()) {
                if(!ObjectUtils.isEmpty(sortParam.getParam())){
                    String column = sortParam.getParam();
                    if(changeUnderline){
                        column = humpToLine(column);
                    }
                    boolean isAsc = true;
                    if(!ObjectUtils.isEmpty(sortParam.getOrder())){
                        isAsc = (sortParam.getOrder().toUpperCase()).equals(NiseConstant.ASC);
                    }
                    queryWrapper.orderBy(true, isAsc,sortParam.getOrder());
                }
            }
        }
    }

    /**
     * 驼峰转下划线
     * @param str
     * @return
     */
    private static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

}
