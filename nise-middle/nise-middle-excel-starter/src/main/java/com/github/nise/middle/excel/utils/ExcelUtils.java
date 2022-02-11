/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.excel.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/11 9:28 huzhi Exp $$
 */
public class ExcelUtils {

    public static final Integer EXPORT_MAX_ROW_NUMBER_XLSX = 1048576;

    public static final Integer EXPORT_MAX_ROW_NUMBER_XLS = 65536;

    /**
     * 导出excel文件
     * @param response
     * @param data
     * @param fileName
     * @param sheetName
     * @param excelTypeEnum
     * @param cla
     * @throws Exception
     */
    public static void writeExcel(HttpServletResponse response, List data, String fileName,
                                  String sheetName, ExcelTypeEnum excelTypeEnum, Class cla) throws Exception{
        // 表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle,contentWriteCellStyle);
        EasyExcel.write(getOutputStream(fileName,response),cla).excelType(excelTypeEnum).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(data);
    }

    /**
     * 获取输出流
     * @param fileName
     * @param response
     * @return
     * @throws Exception
     */
    private static OutputStream getOutputStream(String fileName,HttpServletResponse response) throws Exception{
        fileName = URLEncoder.encode(fileName,"UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");
        return response.getOutputStream();
    }

    /**
     * 校验后缀是否为excel
     * @param fileName
     * @return
     */
    public static Boolean verifyFileFormat(String fileName){
        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        Boolean flag = false;
        for (ExcelTypeEnum et : ExcelTypeEnum.values()) {
            if(et.getValue().equals(extension)){
                flag = true;
                break;
            }
        }
        return flag;
    }

}
