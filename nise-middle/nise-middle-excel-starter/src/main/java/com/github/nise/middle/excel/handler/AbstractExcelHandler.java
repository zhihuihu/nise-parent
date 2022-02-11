/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.excel.handler;

import cn.hutool.core.util.IdUtil;
import com.github.nise.common.utils.LambdaUtils;
import com.github.nise.middle.excel.listener.AbstractImportExcelListener;
import com.github.nise.middle.excel.model.AbstractImportModel;
import com.github.nise.middle.excel.model.ExcelSummaryResultModel;
import com.github.nise.middle.excel.utils.GenericsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/11 11:22 huzhi Exp $$
 */
public abstract class AbstractExcelHandler<I extends AbstractImportModel,E> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    /**
     * 获取导入的Class类
     * @return
     */
    public Class<I> getImportClass(){
        return (Class<I>) GenericsUtils.getSuperClassGenericType(this.getClass(),0);
    }

    /**
     * 获取导出的Class类
     * @return
     */
    public Class<E> getExportClass(){
        return (Class<E>) GenericsUtils.getSuperClassGenericType(this.getClass(),1);
    }

    /**
     * 获取导入处理的实例
     * @param excelSummaryResultId
     * @param reqData
     * @returnc
     */
    public abstract AbstractImportExcelListener getListenerInstance(Long excelSummaryResultId, String reqData);

    /**
     * 获取业务编码
     * @return
     */
    public abstract String getBusinessCode();

    /**
     * 获取业务描述
     * @return
     */
    public abstract String getBusinessDesc();

    /**
     * 获取业务mongo存储的表名称
     * @return
     */
    public abstract String getBusinessCollectName();

    /**
     * 获取导入模板的路径
     * @return
     */
    public abstract String getBusinessTemplateUrl();

    /**
     * 获取导入的模板下载名称
     * @return
     */
    public abstract String getBusinessTemplateFileName();

    /**
     * 执行导入校验成功的数据后续操作
     * @param importModels
     * @return
     */
    public abstract void executeImportData(List<I> importModels);

    /***
     * 执行导入的结果后续操作
     * @param excelSummaryResultId
     * @return
     */
    public Long doImportData(Long excelSummaryResultId){
        Long newExcelSummaryResultId = IdUtil.getWorkerId(0,0);
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and(LambdaUtils.getColumnName(AbstractImportModel::getExcelSummaryResultId)).is(excelSummaryResultId)
                .and(LambdaUtils.getColumnName(AbstractImportModel::getResultFlag)).is(Boolean.TRUE);
        query.addCriteria(criteria);
        List<I> successImportDatas = mongoTemplate.find(query,this.getImportClass());
        // 清除ID和结果状态，便于保存新结果
        for (I successImportData : successImportDatas) {
            successImportData.setId(null);
            successImportData.setResultFlag(null);
            successImportData.setResultMsg(null);
            successImportData.setExcelSummaryResultId(null);
        }
        this.executeImportData(successImportDatas);
        // 保存新结果
        for (I successImportData : successImportDatas) {
            successImportData.setExcelSummaryResultId(newExcelSummaryResultId);
        }

        BulkOperations ops = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, this.getBusinessCollectName());
        ops.insert(successImportDatas);
        ops.execute();
        // 保存导入总览数据
        ExcelSummaryResultModel excelSummaryResultModel = new ExcelSummaryResultModel();
        excelSummaryResultModel.setExcelSummaryResultId(newExcelSummaryResultId);
        excelSummaryResultModel.setTotal((long)successImportDatas.size());
        excelSummaryResultModel.setSuccess(successImportDatas.stream().filter(v -> Boolean.TRUE.equals(v.getResultFlag())).count());
        excelSummaryResultModel.setFail(excelSummaryResultModel.getTotal() - excelSummaryResultModel.getSuccess());
        excelSummaryResultModel.setCreateTime(LocalDateTime.now());
        mongoTemplate.save(excelSummaryResultModel);
        return newExcelSummaryResultId;
    }

}
