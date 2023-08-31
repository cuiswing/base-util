package com.cui.base.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 身份证校验工具
 *
 * @author CUI
 * @since 2020-12-06
 */
@Slf4j
public class IdCardUtil {
    private static int[] weight = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};    //十七位数字本体码权重
    private static char[] validate = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};    //mod11,对应校验码字符值


    /**
     * 身份证号码校验，长度必须为 15 位或者 18 位
     *
     * @param idcard 身份证号
     * @return true：校验通过，false：校验不通过
     */
    public static boolean validate(String idcard) {
        if (idcard.length() != 15 && idcard.length() != 18) {
            return false;
        }
        char validateCode = getValidateCode(idcard.substring(0, idcard.length() - 1));
        return validateCode == idcard.substring(idcard.length() - 1).charAt(0);
    }

    /**
     * 获取身份证检验码
     *
     * @param id17 身份证前17位数字
     * @return 身份证检验码（最后一位数字）
     */
    public static char getValidateCode(String id17) {
        int sum = 0;
        for (int i = 0; i < id17.length(); i++) {
            sum = sum + Integer.parseInt(String.valueOf(id17.charAt(i))) * weight[i];
        }
        int mode = sum % 11;
        return validate[mode];
    }

}
