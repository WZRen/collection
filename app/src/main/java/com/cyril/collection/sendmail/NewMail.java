package com.cyril.collection.sendmail;

/**
 * Created by cyril on 2017/5/10.
 */
public class NewMail implements Runnable {
    private static final String mSender = "wzr_cyril@163.com";//发送者的邮箱
    private static final String mSenderPass = "cyril285322371";
    private static final String HostName = "smtp.163.com";
    private static final String HostPort = "465";//服务器端口
    private static final String mReceiver = "wzr@zjmax.com";//接收者邮箱
    private String title, msg, filePath = "", fileName = "";

    public NewMail() {
    }

//    public NewMail(String title, String msg) {
//        this.title = title;
//        this.msg = msg;
//    }

    public NewMail(String title, String msg, String filePath, String fileName) {
        this.title = title;
        this.msg = msg;
        this.filePath = filePath;
        this.fileName = fileName;
    }


    @Override
    public void run() {
        MailUtils sender = new MailUtils().setUser(mSender).setPassword(mSenderPass)
                .setFrom(mSender).setTo(mReceiver).setHost(HostName).setPort(HostPort)
                .setSubject(title).setBody(msg);
        sender.init();
        if (!filePath.equals("") && !fileName.equals(""))
            try {
                sender.addAttachment(filePath, fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            sender.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
