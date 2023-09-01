package com.cui.base.mail;

import com.alibaba.fastjson.JSON;
import com.cui.base.util.YamlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * 邮件工具类：每个具体的配置邮件发送组得实例化对应的对象
 * 网易邮箱常见异常信息：http://mail.163.com/help/help_spam_16.htm?ip=8.131.80.198&hostid=smtp11&time=1619584074
 * 反馈：https://feedback.mail.126.com/antispam/complain.php?user=hh-csq@163.com
 *
 * @author CUI
 * @date 2019-01-21
 */
public final class MailUtil {
    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);
    private static final String EMAIL_YAML_CONFIG = "/config/email.yml";

    /**
     * 兼容保留旧的配置方式
     */
    private static MailConfig mailConfig;
    /**
     * 新的邮箱配置，key：邮件发送方的标识，邮件服务器连接信息
     */
    private static Map<EmailNoticeEnum, List<MailConfigNew>> mailConfigMap = new HashMap<>();
    //1、连接邮件服务器的参数配置
    private static final Properties props = new Properties();

    static {
        initConfig();
    }

    public MailUtil(String config) {
        init(new MailConfig(config));
    }

    private static void init(MailConfig mailConfig) {
        if (mailConfig == null) {
            mailConfig = new MailConfig();
        }
        MailUtil.mailConfig = mailConfig;

        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", mailConfig.getHost());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.port", "465");
    }

    private static void initConfig() {
        try {
            MailServerConfig mailServerConfig = YamlUtil.loadConfig(MailUtil.EMAIL_YAML_CONFIG, MailServerConfig.class);
            mailServerConfig.getMailConfigMap().forEach((type, valueList) -> {
                List<MailConfigNew> mailConfigNewList = new ArrayList<>();
                for (Object value : valueList) {
                    MailConfigNew mailConfigNew = JSON.parseObject(JSON.toJSONString(value), MailConfigNew.class);

                    Properties properties = mailConfigNew.getProperties();
                    //设置用户的认证方式
                    properties.setProperty("mail.smtp.auth", "true");
                    //设置传输协议
                    properties.setProperty("mail.transport.protocol", "smtp");
                    //设置发件人的SMTP服务器地址
                    properties.setProperty("mail.smtp.host", mailConfigNew.getMailHost());
                    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    properties.put("mail.smtp.socketFactory.port", "465");
                    properties.put("mail.smtp.port", "465");
                    mailConfigNewList.add(mailConfigNew);
                }
                mailConfigMap.putIfAbsent(type, mailConfigNewList);
            });
            logger.info("邮箱配置：{}", mailConfigMap);
        } catch (Throwable e) {
            logger.error("邮箱配置加载失败", e);
            throw e;
        }
    }

    public static void sendMail(List<String> toList, List<String> ccList, String subject, String content) {

    }

    /**
     * 根据邮件类型发送
     *
     * @param emailNoticeEnum 邮件类型
     * @param subject         邮件主题
     * @param content         邮件内容，html格式的文本
     * @param addCcList       抄送人
     */
    public static void sendByType(EmailNoticeEnum emailNoticeEnum, String subject, String content, String... addCcList) {
        List<MailConfigNew> mailConfigNewList = mailConfigMap.get(emailNoticeEnum);
        int index = new Random().nextInt(mailConfigNewList.size());
        MailConfigNew mailConfigNew = mailConfigNewList.get(index);
        logger.info("开始发送邮件：主题:{},内容:{},邮箱配置信息:{}", subject, content, mailConfigNew);
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(mailConfigNew.getProperties());
        //设置调试信息在控制台打印出来
        // session.setDebug(true);
        try {
            //3、创建邮件的实例对象
            Message msg = createMimeMessage2(mailConfigNew, session, subject, content, addCcList);

            Transport transport = session.getTransport();
            transport.connect(mailConfigNew.getMailUsername(), mailConfigNew.getMailPassword());
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (Throwable e) {
            logger.error("邮件发送失败，配置信息：{},邮件内容:{}", mailConfigNew, content, e);
        }
    }

    /**
     * 根据固定的配置信息发送邮件
     *
     * @param subject   邮件主题
     * @param content   邮件内容，html格式的文本
     * @param addCcList 额外的抄送人
     */
    public static void sendMailByConfig(String subject, String content, String... addCcList) {
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        //session.setDebug(true);
        try {
            //3、创建邮件的实例对象
            Message msg = createMimeMessage(session, subject, content, addCcList);

            Transport transport = session.getTransport();
            transport.connect(mailConfig.getUsername(), mailConfig.getPassword());
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (Throwable e) {
            logger.error("邮件发送失败，配置信息：{},邮件内容:{}", mailConfig, content, e);
        }
    }

    private static Message createMimeMessage2(MailConfigNew mailConfigNew, Session session, String subject, String content, String[] addCcList) throws MessagingException {
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailConfigNew.getMailFrom()));
        Address[] toAddresses = new InternetAddress[mailConfigNew.getMailToList().size()];
        for (int i = 0; i < mailConfigNew.getMailToList().size(); i++) {
            toAddresses[i] = new InternetAddress(mailConfigNew.getMailToList().get(i));
        }
        msg.setRecipients(Message.RecipientType.TO, toAddresses);

        List<Address> ccList = new ArrayList<>();
        for (String cc : mailConfigNew.getMailCcList()) {
            if (!cc.isEmpty()) {
                ccList.add(new InternetAddress(cc));
            }
        }
        for (String cc : addCcList) {
            if (!cc.isEmpty()) {
                ccList.add(new InternetAddress(cc));
            }
        }
        Address[] ccAddresses = ccList.toArray(new Address[0]);
        msg.setRecipients(Message.RecipientType.CC, ccAddresses);

        //设置邮件主题
        msg.setSubject(subject, "UTF-8");
        //设置邮件正文
        msg.setContent(content, "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }

    private static Message createMimeMessage(Session session, String subject, String content, String... addCcList) throws MessagingException {
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailConfig.getEmailForm()));
        Address[] toAddresses = new InternetAddress[mailConfig.getToList().length];
        for (int i = 0; i < mailConfig.getToList().length; i++) {
            toAddresses[i] = new InternetAddress(mailConfig.getToList()[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, toAddresses);

        List<Address> ccList = new ArrayList<>();
        for (String cc : mailConfig.getCcList()) {
            if (!cc.isEmpty()) {
                ccList.add(new InternetAddress(cc));
            }
        }
        for (String cc : addCcList) {
            if (!cc.isEmpty()) {
                ccList.add(new InternetAddress(cc));
            }
        }
        Address[] ccAddresses = ccList.toArray(new Address[0]);
        msg.setRecipients(Message.RecipientType.CC, ccAddresses);

        //设置邮件主题
        msg.setSubject(subject, "UTF-8");
        //设置邮件正文
        msg.setContent(content, "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }
}
