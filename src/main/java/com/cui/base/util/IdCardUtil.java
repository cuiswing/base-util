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
     * @param idCard 身份证号
     * @return true：校验通过，false：校验不通过
     */
    public static boolean validate(String idCard) {
        if (idCard.length() != 15 && idCard.length() != 18) {
            return false;
        }
        char validateCode = getValidateCode(idCard.substring(0, idCard.length() - 1));
        return validateCode == idCard.substring(idCard.length() - 1).charAt(0);
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

    /**
     * 获取身份证的生日
     *
     * @param idCard 身份证号码
     * @return 出生日期，格式：yyyyMMdd
     */
    public static String getBirthday(String idCard) {
        String birthDate = idCard.substring(6, 14);
        return birthDate;
    }

    /**
     * 获取身份证的性别
     *
     * @param idCard 身份证号码
     * @return 性别：男 or 女
     */
    public static String getSex(String idCard) {
        String genderCode = idCard.substring(idCard.length() - 2, idCard.length() - 1);
        String gender = Integer.valueOf(genderCode) % 2 == 0 ? "女" : "男";
        return gender;
    }

}
