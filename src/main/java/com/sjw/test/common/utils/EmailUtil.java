package com.sjw.test.common.utils;

import com.sjw.test.common.model.EmailAccout;
import com.sjw.test.common.model.MessageInfo;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.Security;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/28 14:00
 */
@Slf4j
public class EmailUtil {

    //邮件发送者
    private static final String EMAIL_SENDER="18852923055@163.com";
    //开启smtp协议 使用授权码
    private static final String EMAIL_PASSWORD="GPJWQCCTZPLMULFO";
    //邮件title
    private static final String EMAIL_TITLE="测试标题";


    /*
     *
     * @author Sunny
     * @description 发送验证码(邮箱）
     * @date 2019/6/27 11:42
     * @param  * @param email
     * @return java.lang.String
     * @version 1.0
     */
    public static String sendEmailByCode(String email,String codeValue){
        //验证码
        String emailSender =EMAIL_SENDER;
        String title =EMAIL_TITLE;
        String emailContent="您的验证为:"+codeValue+", 如非本人操作，请忽略本邮件";
        log.info("Email from " + emailSender + "; to " + email + "; title = " + title + "; code = " + codeValue);

        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setFrom(emailSender);
        messageInfo.setMsg(emailContent);
        messageInfo.setSendDate(new Date());
        messageInfo.setSubject(title);
        messageInfo.setTo(Arrays.asList(email));

        EmailAccout emailAccout = new EmailAccout();
        emailAccout.setPassword(EMAIL_PASSWORD);
        emailAccout.setPlace("smtp.163.com");
        emailAccout.setUsername(EMAIL_SENDER);

        try {
            sslSend(messageInfo, emailAccout);
            log.info("发送成功");
        }catch (Exception e) {
            log.info("发送失败：" + e.toString());
        }
        return codeValue;
    }
    /**
     * 采用SSL协议发送邮件，可向多人单人发送邮件
     * @param msg1
     * @param emailAccount
     * @return
     * @throws AddressException
     * @throws MessagingException
     * @throws IOException
     */
    private static boolean sslSend(MessageInfo msg1, EmailAccout emailAccount)
            throws AddressException, MessagingException, IOException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", emailAccount.getPlace());
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");

        final String username = emailAccount.getUsername();
        final String password = emailAccount.getPassword();
        Session session = Session.getDefaultInstance(props, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }});
        Message msg = new MimeMessage(session);

        // 设置发件人和收件人
        msg.setFrom(new InternetAddress(emailAccount.getUsername()));
        List<String> tos = msg1.getTo();
        Address to[] = new InternetAddress[tos.size()];
        for(int i=0;i<tos.size();i++){
            to[i] = new InternetAddress(tos.get(i));
        }
        // 多个收件人地址 GPJWQCCTZPLMULFO
        msg.setRecipients(Message.RecipientType.TO, to);
        msg.setSubject(msg1.getSubject()); // 标题
        msg.setText(msg1.getMsg());// 内容
        msg.setSentDate(new Date());
        Transport.send(msg);
        log.info("发送结果：" + msg.getContent());
        return true;
    }

    public static void main(String[] args) {
        sendEmailByCode("2287329355@qq.com","1234");
    }
}
