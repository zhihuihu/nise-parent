/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.excel.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.nise.common.dto.PageInfo;
import com.github.nise.common.utils.LambdaUtils;
import com.github.nise.middle.excel.dto.req.ImportResultPageReq;
import com.github.nise.middle.excel.handler.AbstractExcelHandler;
import com.github.nise.middle.excel.model.AbstractImportModel;
import com.github.nise.middle.excel.model.ExcelSummaryResultModel;
import com.github.nise.middle.excel.utils.ExcelUtils;
import com.github.nise.middle.excel.utils.ResourcesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/11 10:56 huzhi Exp $$
 */
@Slf4j
public class ExcelService {

    private MongoTemplate mongoTemplate;

    private List<AbstractExcelHandler> abstractExcelHandlers;

    /**
     * 有参构造方法，防止不注入这些参数
     * @param mongoTemplate
     * @param abstractExcelHandlers
     */
    public ExcelService(MongoTemplate mongoTemplate, List<AbstractExcelHandler> abstractExcelHandlers) {
        this.mongoTemplate = mongoTemplate;
        this.abstractExcelHandlers = abstractExcelHandlers;
    }

    /**
     * 导出模板
     * @param businessCode
     * @param response
     */
    public void exportTemplate(String businessCode, HttpServletResponse response){
        AbstractExcelHandler abstractExcelHandler = this.matchAbstractExcelHandler(businessCode);
        String templateUrl = abstractExcelHandler.getBusinessTemplateUrl();
        if(!ObjectUtils.isEmpty(templateUrl)){
            ResourcesUtils.export(response,templateUrl,"导入模板.xlsx");
        }
    }

    /**
     * 导入excel文件
     * @param businessCode
     * @param data
     * @param file
     * @return
     */
    public Long importExcel(String businessCode, String data, MultipartFile file){
        AbstractExcelHandler abstractExcelHandler = this.matchAbstractExcelHandler(businessCode);
        Long excelSummaryResultId = IdUtil.getWorkerId(0,0);
        try {
            InputStream inputStream = file.getInputStream();
            ExcelReader excelReader = EasyExcel.read(inputStream, abstractExcelHandler.getListenerInstance(excelSummaryResultId,data)).head(abstractExcelHandler.getImportClass()).headRowNumber(1).build();
            excelReader.readAll();
            excelReader.finish();
        } catch (IllegalArgumentException illegalArgumentException) {
            log.error("inspect item standard import error, message is {}", illegalArgumentException.getMessage());
            throw new RuntimeException("导入数据不能为空，请检查");
        } catch (Exception e) {
            log.error("inspect item standard import error, message is {}", e.getMessage());
            throw new RuntimeException("导入错误");
        }
        return excelSummaryResultId;
    }

    /**
     * 查询导入的结果
     * @param excelSummaryResultId
     * @return
     */
    public ExcelSummaryResultModel queryImportExcelResult(Long excelSummaryResultId){
        ExcelSummaryResultModel resultModel = mongoTemplate.findOne(new Query(Criteria.where("excelSummaryResultId").is(excelSummaryResultId)),
                ExcelSummaryResultModel.class);
        return resultModel;
    }

    /**
     * 导入的数据结果分页查询
     * @param businessCode
     * @param req
     * @return
     */
    public PageInfo<? extends AbstractImportModel> pageImportResult(String businessCode, ImportResultPageReq req){
        PageInfo pageInfo = new PageInfo(req);
        AbstractExcelHandler abstractExcelHandler = this.matchAbstractExcelHandler(businessCode);
        Class<?> cla = abstractExcelHandler.getImportClass();
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and(LambdaUtils.getColumnName(AbstractImportModel::getExcelSummaryResultId)).is(req.getExcelSummaryResultId());
        if (!Objects.isNull(req.getResultFlag())) {
            criteria.and(LambdaUtils.getColumnName(AbstractImportModel::getResultFlag)).is(req.getResultFlag());
        }
        query.addCriteria(criteria);
        query.skip((req.getCurrent() - 1) * req.getSize()).limit(req.getSize().intValue());
        List<?> list = mongoTemplate.find(query, cla);
        pageInfo.setRecords(list);
        pageInfo.setTotal(mongoTemplate.count(query, cla));
        return pageInfo;
    }

    /**
     * 导入的结果导出
     * @param response
     * @param businessCode
     * @param req
     */
    public void importResultExport(HttpServletResponse response,String businessCode,ImportResultPageReq req){
        try {
            AbstractExcelHandler abstractExcelHandler = this.matchAbstractExcelHandler(businessCode);
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and(LambdaUtils.getColumnName(AbstractImportModel::getExcelSummaryResultId)).is(req.getExcelSummaryResultId());
            if (!Objects.isNull(req.getResultFlag())) {
                criteria.and(LambdaUtils.getColumnName(AbstractImportModel::getResultFlag)).is(req.getResultFlag());
            }
            query.addCriteria(criteria);
            List<?> list = mongoTemplate.find(query, abstractExcelHandler.getImportClass());
            List results = new ArrayList();
            if(!CollectionUtils.isEmpty(list)){
                for (Object o : list) {
                    Object result = BeanUtils.instantiateClass(abstractExcelHandler.getExportClass());
                    BeanUtils.copyProperties(o,result);
                    results.add(result);
                }
            }
            ExcelUtils.writeExcel(response, results, "导入结果", "导入结果", ExcelTypeEnum.XLSX,
                    abstractExcelHandler.getExportClass());
        } catch (Exception e) {
            log.error("inspect item export error, param is {}", req);
        }
    }

    /**
     * 执行导入校验通过的数据后续操作
     * @param businessCode
     * @param excelSummaryResultId
     * @return
     */
    public Long executeImportData(String businessCode,Long excelSummaryResultId){
        AbstractExcelHandler abstractExcelHandler = this.matchAbstractExcelHandler(businessCode);
        return abstractExcelHandler.doImportData(excelSummaryResultId);
    }

    /**
     * 匹配处理器
     * @param businessCode
     * @return
     */
    private AbstractExcelHandler matchAbstractExcelHandler(String businessCode){
        for (AbstractExcelHandler abstractExcelHandler : abstractExcelHandlers) {
            if(abstractExcelHandler.getBusinessCode().equals(businessCode)){
                return abstractExcelHandler;
            }
        }
        throw new RuntimeException("未匹配到处理器");
    }
}
