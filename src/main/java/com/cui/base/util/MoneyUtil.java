package com.cui.base.util;

import java.math.BigDecimal;

/**
 * 金额转换 元 分
 *
 * @author CUI
 * @since 2022-12-30
 */
public class MoneyUtil {

    //把字符串数字转化为大写的数字
    //把1314转换为---零佰零什零万零千零佰零什零元


    private final static BigDecimal VALUE_100 = new BigDecimal(100);


    /**
     * 1. 分转元
     *
     * @param fenAmount 分
     * @return 元
     */
    public static String fenConvertToYuan(Long fenAmount) {
        BigDecimal yuanAmount = new BigDecimal(fenAmount).divide(VALUE_100);
        System.out.println(yuanAmount.setScale(2));
        return yuanAmount.toString();
    }

    /**
     * 1. 元转分
     *
     * @param yuanAmount 元
     * @return 分
     */
    public static Integer yuanConvertToFee(double yuanAmount) {
        BigDecimal feeAmount = new BigDecimal(yuanAmount).multiply(VALUE_100);
        System.out.println(feeAmount.setScale(0, BigDecimal.ROUND_HALF_UP));
        return feeAmount.intValue();
    }

    /**
     * 1. 元转分
     *
     * @param yuanAmount 元
     * @return 分
     */
    public static Integer yuanConvertToFee(String yuanAmount) {
        BigDecimal feeAmount = new BigDecimal(yuanAmount).multiply(VALUE_100);
        // 不需要四舍五入
        System.out.println(feeAmount.setScale(0, BigDecimal.ROUND_HALF_UP));
        return feeAmount.intValue();
    }


    public static void main(String[] args) {
        System.out.println(fenConvertToYuan(0L));
        System.out.println(fenConvertToYuan(1L));
        System.out.println(fenConvertToYuan(11L));
        System.out.println(fenConvertToYuan(111L));

        System.out.println("---------");
        System.out.println(yuanConvertToFee("0.00"));
        System.out.println(yuanConvertToFee("0.01"));
        System.out.println(yuanConvertToFee("0.10"));
        System.out.println(yuanConvertToFee("1.00"));
        System.out.println(yuanConvertToFee("12.553"));
        System.out.println(yuanConvertToFee("12.554"));
        System.out.println(yuanConvertToFee("12.555"));
        System.out.println(yuanConvertToFee("12.556"));
    }
}
