/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.excel.controller;

import com.github.nise.common.dto.PageInfo;
import com.github.nise.common.utils.ResponseMessage;
import com.github.nise.middle.excel.dto.req.ImportResultPageReq;
import com.github.nise.middle.excel.model.AbstractImportModel;
import com.github.nise.middle.excel.model.ExcelSummaryResultModel;
import com.github.nise.middle.excel.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/11 15:07 huzhi Exp $$
 */
@RestController
@RequestMapping(value = "excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    /**
     * 导出模板
     * @param businessCode
     * @param response
     */
    @GetMapping(value = "template/export")
    public void templateExport(@RequestParam(value = "businessCode") String businessCode, HttpServletResponse response){
        excelService.exportTemplate(businessCode,response);
    }

    /**
     * 导入excel文件
     * @param businessCode
     * @param data
     * @param file
     * @return
     */
    @PostMapping(value = "data/import")
    public ResponseMessage<Long> dataImport(@RequestParam(value = "businessCode") String businessCode,
                                            @RequestParam(value = "data",required = false) String data,
                                            @RequestParam("file") MultipartFile file){
        return ResponseMessage.success(excelService.importExcel(businessCode, data, file));
    }

    /**
     * 查询总体导入的结果
     * @param excelSummaryResultId
     * @return
     */
    @GetMapping(value = "summary/query")
    public ResponseMessage<ExcelSummaryResultModel> summaryQuery(@RequestParam(value = "excelSummaryResultId") Long excelSummaryResultId){
        return ResponseMessage.success(excelService.queryImportExcelResult(excelSummaryResultId));
    }

    /**
     * 查询导入的每一行结果
     * @param req
     * @return
     */
    @PostMapping(value = "importResult/page")
    public ResponseMessage<PageInfo<? extends AbstractImportModel>> importResultPage(@RequestParam(value = "businessCode") String businessCode,
                                                                                     @RequestBody ImportResultPageReq req){
        return ResponseMessage.success(excelService.pageImportResult(businessCode, req));
    }

    /**
     * 导入的结果导出
     * @param response
     * @param businessCode
     * @param req
     */
    @PostMapping(value = "importResult/export")
    public void importResultExport(HttpServletResponse response,
                                   @RequestParam(value = "businessCode") String businessCode,
                                   @RequestBody ImportResultPageReq req){
        excelService.importResultExport(response, businessCode, req);
    }

    /**
     * 执行导入校验成功的数据后续操作
     * @param businessCode
     * @param excelSummaryResultId
     * @return
     */
    @GetMapping(value = "importData/execute")
    public ResponseMessage<Long> executeImportData(@RequestParam(value = "businessCode") String businessCode,
                                                   @RequestParam(value = "excelSummaryResultId") Long excelSummaryResultId){
        return ResponseMessage.success(excelService.executeImportData(businessCode, excelSummaryResultId));
    }

}
