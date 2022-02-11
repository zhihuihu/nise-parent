/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.excel.dto.req;

import com.github.nise.common.dto.PageSortReq;
import lombok.Data;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/11 14:25 huzhi Exp $$
 */
@Data
public class ImportResultPageReq extends PageSortReq {

    /** 汇总结果ID */
    private Long excelSummaryResultId;

    /** 结果：true代表成功 false代表失败 */
    private Boolean resultFlag;

}
