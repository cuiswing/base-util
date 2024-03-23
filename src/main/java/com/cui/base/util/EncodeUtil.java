package com.cui.base.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 编码工具
 *
 * @author cuishixiang
 * @date 2023-12-06
 */
@Slf4j
public class EncodeUtil {

    /**
     * 将字符串转为Unicode编码
     */
    public static String encodeUnicode(String chinese) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < chinese.length(); i++) {
            char c = chinese.charAt(i);
            unicode.append("%u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    /**
     * 将Unicode编码字符串转为中文
     *
     * @param unicodeStr unicode字符串,可以包含任意其他非 unicode 字符（比如"\u67e5\u8be2\u6210\u529f"）
     * @return 转换后的字符串（比如"查询成功"）
     */
    public static String unicode2Chinese(String unicodeStr) {
        // Pattern pattern = Pattern.compile("(\\\\u(\\w{4}))");
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(unicodeStr);

        // 迭代，将str中的所有unicode转换为正常字符
        while (matcher.find()) {
            String unicodeFull = matcher.group(1); // 匹配出的每个字的unicode，比如\u67e5
            String unicodeNum = matcher.group(2); // 匹配出每个字的数字，比如\u67e5，会匹配出67e5

            // 将匹配出的数字按照16进制转换为10进制，转换为char类型，就是对应的正常字符了
            char singleChar = (char) Integer.parseInt(unicodeNum, 16);
            // 替换原始字符串中的unicode码
            unicodeStr = unicodeStr.replace(unicodeFull, singleChar + "");
        }
        return unicodeStr;
    }

    public static String encodeURL(String url) {
        try {
            return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            log.error("url encode error :{}", url, e);
        }
        return url;
    }
}
