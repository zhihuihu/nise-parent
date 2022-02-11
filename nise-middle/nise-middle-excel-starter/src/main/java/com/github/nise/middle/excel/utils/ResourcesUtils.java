/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.excel.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/11 14:31 huzhi Exp $$
 */
public class ResourcesUtils {

    /**
     * 导出resources路径下的文件
     * @param response
     * @param resourcesFileName
     * @param outFileName
     */
    public static void export(HttpServletResponse response, String resourcesFileName, String outFileName){
        try{
            ServletOutputStream out;
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            Resource resource = new ClassPathResource(resourcesFileName);
            InputStream is = resource.getInputStream();
            outFileName = new String((outFileName).getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;fileName=" + outFileName);
            out = response.getOutputStream();
            int b = 0;
            byte[] buffer = new byte[1024];
            while ((b = is.read(buffer)) != -1) {
                out.write(buffer, 0, b);
            }
            is.close();

            if (out != null) {
                out.flush();
                out.close();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
