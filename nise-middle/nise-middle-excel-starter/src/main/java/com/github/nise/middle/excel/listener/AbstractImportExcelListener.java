/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.github.nise.middle.excel.model.AbstractImportModel;
import com.github.nise.middle.excel.model.ExcelSummaryResultModel;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/11 11:00 huzhi Exp $$
 */
public abstract class AbstractImportExcelListener<T extends AbstractImportModel> extends AnalysisEventListener<T> {

    /** 总体结果ID */
    private Long excelSummaryResultId;
    /**  */
    private MongoTemplate mongoTemplate;
    /** 业务编码 */
    private String businessCode;
    /** mongo存储的表名称 */
    private String businessCollectName;
    /** 下载的模板路径 */
    private String businessTemplateUrl;
    /** 业务请求数据 */
    private String reqData;
    /** 导入的数据 */
    protected List<T> datas = new ArrayList<>();

    /**
     * 构造方法，这里没有设置无参构造，必须要有参数
     * @param excelSummaryResultId
     * @param mongoTemplate
     * @param businessCode
     * @param businessCollectName
     * @param businessTemplateUrl
     * @param reqData
     */
    public AbstractImportExcelListener(Long excelSummaryResultId, MongoTemplate mongoTemplate, String businessCode, String businessCollectName, String businessTemplateUrl, String reqData) {
        this.excelSummaryResultId = excelSummaryResultId;
        this.mongoTemplate = mongoTemplate;
        this.businessCode = businessCode;
        this.businessCollectName = businessCollectName;
        this.businessTemplateUrl = businessTemplateUrl;
        this.reqData = reqData;
    }

    /**
     * 执行excel数据读取完后的操作
     * @param analysisContext
     * @return
     */
    public abstract void executeAfterAllAnalysed(AnalysisContext analysisContext);

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if(datas.size() == 0){
            return;
        }
        this.executeAfterAllAnalysed(analysisContext);
        // 保存导入记录数据
        for (T data : datas) {
            data.setExcelSummaryResultId(this.excelSummaryResultId);
        }
        BulkOperations ops = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, this.businessCollectName);
        ops.insert(datas);
        ops.execute();
        // 保存导入总览数据
        ExcelSummaryResultModel excelSummaryResultModel = new ExcelSummaryResultModel();
        excelSummaryResultModel.setExcelSummaryResultId(this.excelSummaryResultId);
        excelSummaryResultModel.setTotal((long)datas.size());
        excelSummaryResultModel.setSuccess(datas.stream().filter(v -> Boolean.TRUE.equals(v.getResultFlag())).count());
        excelSummaryResultModel.setFail(excelSummaryResultModel.getTotal() - excelSummaryResultModel.getSuccess());
        excelSummaryResultModel.setCreateTime(LocalDateTime.now());
        mongoTemplate.save(excelSummaryResultModel);
        this.datas.clear();
    }
}
