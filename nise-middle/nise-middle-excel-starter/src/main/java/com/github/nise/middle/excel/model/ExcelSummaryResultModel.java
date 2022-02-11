/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.excel.model;

import com.github.nise.middle.excel.config.ExcelBusinessConstant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/11 10:39 huzhi Exp $$
 */
@Data
@Document(collation = ExcelBusinessConstant.EXCEL_SUMMARY_RESULT_MODEL_COLLECT_NAME)
public class ExcelSummaryResultModel {

    @Id
    private String id;
    /** 结果ID */
    @Indexed
    private Long excelSummaryResultId;
    /** 总行数 */
    private Long total;
    /** 成功数量 */
    private Long success;
    /** 失败数量 */
    private Long fail;
    /** 创建时间 */
    private LocalDateTime createTime;
}
