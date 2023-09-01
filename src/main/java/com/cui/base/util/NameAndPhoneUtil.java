package com.cui.base.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 姓名和电话校验工具
 *
 * @author CUI
 * @since 2023-03-29
 */
public class NameAndPhoneUtil {

    /**
     * 正则：人名
     */
    private static final String REGEX_NAME = "^[\\u4e00-\\u9fa5]{2,8}$";
    private static final Pattern PATTERN_REGEX_NAME = Pattern.compile(REGEX_NAME);

    private static final String MOBILE_RULE = "^1[3-9]\\d{9}$";


    /**
     * 验证中文姓名
     */
    public static boolean isChineseName(String name) {
        return isMatch(PATTERN_REGEX_NAME, name);
    }

    public static boolean isPhone(String mobile) {
        if (StringUtils.isBlank(mobile) || mobile.trim().length() != 11) {
            return false;
        }
        return mobile.trim().matches(MOBILE_RULE);
    }

    public static boolean isMatch(Pattern pattern, String str) {
        return StringUtils.isNotBlank(str) && pattern.matcher(str.trim()).matches();
    }
}
