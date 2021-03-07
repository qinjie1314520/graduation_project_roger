package com.notification.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class SendEmailBy163 {
    public static String ownEmailAccount = null;
    public static String ownEmailPassword = null;
    public static Properties properties = null;
    public static String myEmailSMTPHost = null;
    public static Session session = null;

    static {
        myEmailSMTPHost = "smtp.163.com";
        ownEmailAccount = "qj18780122553@163.com";
        ownEmailPassword = "INMNIVSSTIJDICUU";


        properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", myEmailSMTPHost);
        properties.setProperty("mail.smtp.auth", "true");
        session = Session.getInstance(properties);
    }

    /**
     * 发送邮件的种类， TO 发给谁
     */
    public static Integer MessageRecipientTypeTO = 1;
    /**
     * 发送邮件的种类，CC 抄送给谁
     */
    public static Integer MessageRecipientTypeCC = 2;
    /**
     * 发送邮件的种类，BCC 密送给谁
     */
    public static Integer MessageRecipientTypeBCC = 3;

    /**
     * 发送邮件
     *
     * @param receiver 接受者邮箱
     * @param name     自己的昵称
     * @param subject  主题
     * @param content  内容
     * @param num      邮件类型1-TO，2-CC，3-BCC，一般就1就行
     * @return
     */
    public static boolean sendMessage(String receiver, String name, String subject, String content, int num) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(ownEmailAccount, name, "utf-8"));
        switch (num) {
            case 1:
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver, subject, "utf-8"));
                break;
            case 2:
                message.setRecipient(Message.RecipientType.CC, new InternetAddress(receiver, subject, "utf-8"));
                break;
            case 3:
                message.setRecipient(Message.RecipientType.BCC, new InternetAddress(receiver, subject, "utf-8"));
                break;
            default:
                return false;
        }

        message.setSentDate(new Date());
        message.setSubject(subject);
        message.setContent(content, "text/html;charset=utf-8");
        message.saveChanges();

        Transport transport = null;

        transport = session.getTransport();
        transport.connect(ownEmailAccount, ownEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());


        if (transport != null) {
            transport.close();
        }
        return true;
    }

    public static void main(String args[]) throws MessagingException, UnsupportedEncodingException {
        SendEmailBy163.sendMessage("qj18780122553@163.com", "1768923041@qq.com", "验证码", "您本次的验证码316161", 1);
    }
}
