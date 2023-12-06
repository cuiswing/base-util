package com.cui.base.mail;

import lombok.Getter;

/**
 * 邮件通知类型
 *
 * @author CUI
 * @since 2021-03-21
 */
public enum EmailNoticeEnum {
    REGISTER("注册"),
    SUBMIT("提交"),
    SUCCESS("成功"),
    WAIT_PAY("待支付"),
    FAIL("失败"),
    CANCEL("退票"),
    ERROR("错误！！！"),
    ALARM("警报！！！"),
    BACKUP("备用"),
    CUSTOM("自定义"),
    DAILY_REPORT("日报"),
    WEEK_REPORT("周报"),
    MONTH_REPORT("月报"),
    YEAR_REPORT("年报"),
    OUTDOOR_DAILY_REPORT("户外日报"),
    OUTDOOR_WEEK_REPORT("户外周报"),
    OUTDOOR_MONTH_REPORT("户外月报"),
    OUTDOOR_YEAR_REPORT("户外年报"),
    OUTDOOR_PERSON_STATISTICS("户外人员统计"),
    HOSPITAL_SUCCESS("成功"),
    HOSPITAL_FAIL("失败"),
    ;

    @Getter
    private String type;

    EmailNoticeEnum(String type) {
        this.type = type;
    }
}
