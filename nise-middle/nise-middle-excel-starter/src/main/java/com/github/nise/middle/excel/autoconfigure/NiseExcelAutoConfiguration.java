/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.excel.autoconfigure;

import com.github.nise.middle.excel.controller.ExcelController;
import com.github.nise.middle.excel.handler.AbstractExcelHandler;
import com.github.nise.middle.excel.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/11 9:08 huzhi Exp $$
 */
@Configuration
@ConditionalOnProperty(value = "nise.excel.enabled", havingValue = "true")
@Import(value = {ExcelController.class})
public class NiseExcelAutoConfiguration {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired(required = false)
    List<AbstractExcelHandler> abstractExcelHandlers;

    /**
     * 构建Excel服务对象
     * @return
     */
    @Bean
    public ExcelService excelService(){
        return new ExcelService(mongoTemplate, abstractExcelHandlers);
    }

}
