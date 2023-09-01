package com.cui.base.mail;

import lombok.Data;

import java.util.List;
import java.util.Properties;

/**
 * 邮件配置信息
 *
 * @author CUI
 * @date 2021-04-07
 */
@Data
public class MailConfigNew {
    private String mailHost;
    private String mailUsername;
    private String mailPassword;
    private String mailFrom;
    private List<String> mailToList;
    private List<String> mailCcList;
    private Properties properties = new Properties();
}
