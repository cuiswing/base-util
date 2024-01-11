package com.cui.base.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http 工具类
 *
 * @author CUI
 * @date 2024-01-12
 */
@Slf4j
public class HttpUtil {

    /**
     * 获取所有的请求头信息
     */
    public static Map<String, List<String>> getAllHeader(HttpServletRequest httpRequest) {
        Map<String, List<String>> headerMap = new HashMap<>();
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headers = httpRequest.getHeaders(headerName);
            List<String> headerValues = new ArrayList<>();
            while (headers.hasMoreElements()) {
                headerValues.add(headers.nextElement());
            }
            headerMap.put(headerName, headerValues);
        }
        return headerMap;
    }
}
