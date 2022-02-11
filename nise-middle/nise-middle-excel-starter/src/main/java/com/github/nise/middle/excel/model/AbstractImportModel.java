/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.excel.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/11 10:28 huzhi Exp $$
 */
@Data
public abstract class AbstractImportModel implements Serializable {

    private static final long serialVersionUID = -7714504047812736628L;

    @Id
    private String id;

    /** 结果汇总表ID */
    private Long excelSummaryResultId;

    /** 是否成功 */
    private Boolean resultFlag;

    /** 结果描述 */
    private String resultMsg;

}
