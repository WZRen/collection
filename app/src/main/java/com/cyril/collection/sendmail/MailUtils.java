package com.cyril.collection.sendmail;

import android.util.Log;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by cyril on 2017/5/10.
 */
public class MailUtils extends Authenticator {
    private String host;
    private String port;
    private String user;
    private String password;
    private String from;
    private String to;
    private String subject;
    private String body;

    private Multipart multipart;
    private Properties props;

    public MailUtils() {
    }

    public MailUtils(String user, String password, String from, String to, String host, String port, String subject, String body) {
        this.user = user;
        this.password = password;
        this.from = from;
        this.to = to;
        this.host = host;
        this.port = port;
        this.subject = subject;
        this.body = body;
    }

    public MailUtils setHost(String host) {
        this.host = host;
        return this;
    }

    public MailUtils setPort(String port) {
        this.port = port;
        return this;
    }

    public MailUtils setUser(String user) {
        this.user = user;
        return this;
    }

    public MailUtils setPassword(String password) {
        this.password = password;
        return this;
    }

    public MailUtils setFrom(String from) {
        this.from = from;
        return this;
    }

    public MailUtils setTo(String to) {
        this.to = to;
        return this;
    }

    public MailUtils setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public MailUtils setBody(String body) {
        this.body = body;
        return this;
    }

    public void init() {
        multipart = new MimeMultipart();
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);

        props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
    }

    public boolean send() throws MessagingException {
        if (!user.equals("") && !password.equals("") && !to.equals("") && !from.equals("")) {
            Session session = Session.getDefaultInstance(props, this);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            InternetAddress addressTo = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, addressTo);
            message.setSubject(subject);
            message.setSentDate(new Date());

            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(body);
            multipart.addBodyPart(bodyPart);

            message.setContent(multipart);

            Transport.send(message);
            return true;
        } else {
            return false;
        }
    }

    /*
    添加附件
     */
    public void addAttachment(String filePath, String fileName) throws Exception {
        Log.i("sendMail", filePath);
        BodyPart bodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filePath);
        bodyPart.setDataHandler(new DataHandler(source));
        bodyPart.setFileName(fileName);
        multipart.addBodyPart(bodyPart);
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }
}
