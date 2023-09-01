package com.cui.base.mail;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 邮件配置信息
 *
 * @author CUI
 * @since 2021-04-07
 */
@Data
public class MailServerConfig {

    private Map<EmailNoticeEnum, List<MailConfigNew>> mailConfigMap;
}
