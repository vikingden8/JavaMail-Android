/*
 * Copyright (C) 2016 Viking Den <vikingden@live.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.viking.mail;

import android.util.Log;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * class for set mail properties and send function
 *
 * author : Viking Den <vikingden@live.com>
 * time   : 2016/8/3 22:43
 */
public class Mail {

    private MailInfo mailInfo ;

    private MailListener mailListener ;

    private Mail(MailInfo mailInfo){
        this.mailInfo = mailInfo ;
        if (mailInfo != null && mailInfo.getMailListener() != null){
            mailListener = mailInfo.getMailListener() ;
        }
    }

    public void send(){
        boolean isSuccess = false ;
        Exception _e = null ;
        try{
            setDefaultCommandMap() ;
            Properties props = setProperties();
            Session session = Session.getDefaultInstance(props, new MailAuthenticator(
                    mailInfo.getUsername() , mailInfo.getPasscode()));

            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(mailInfo.getFrom());

            if (mailInfo.getTo() != null ){
                msg.setRecipients(MimeMessage.RecipientType.TO, mailInfo.getTo());
            }

            if (mailInfo.getcC() != null){
                msg.setRecipients(MimeMessage.RecipientType.CC, mailInfo.getcC());
            }

            if (mailInfo.getSubject() != null){
                msg.setSubject(mailInfo.getSubject());
            }
            Multipart mainPart = new MimeMultipart();
            if (mailInfo.getContent() != null){
                    BodyPart html = new MimeBodyPart();
                    html.setContent(mailInfo.getContent(), "text/html; charset=UTF-8");
                    mainPart.addBodyPart(html);
            }

            if (mailInfo.getAttachments() != null){
                for (String attachment : mailInfo.getAttachments()){
                    File attachFile = new File(attachment) ;
                    if (attachFile.exists()){
                        Log.i("viking" , "file exitst : " + attachFile.getName()) ;
                        BodyPart attachmentBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(attachment);
                        attachmentBodyPart.setDataHandler(new DataHandler(source));

                        // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
                        // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
                        //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
                        //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");

                        //MimeUtility.encodeWord可以避免文件名乱码
                        attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachFile.getName()));
                        mainPart.addBodyPart(attachmentBodyPart);
                    }
                }
            }

            msg.setContent(mainPart);
            msg.setSentDate(new Date());

            // send email
            Transport.send(msg);

            isSuccess = true ;
        }catch (Exception e){
            e.printStackTrace();
            _e = e ;
        }
        if (mailListener != null){
            if (isSuccess){
                mailListener.onSuccess();
            }else{
                mailListener.onFail(_e);
            }
        }
    }

    private Properties setProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.host", mailInfo.getHost());

        if(mailInfo.isNeedAuth()) {
            props.put("mail.smtp.auth", "true");
        }

        props.put("mail.smtp.port", mailInfo.getPort());
        props.put("mail.smtp.socketFactory.port", mailInfo.getPort());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        return props;
    }

    /**
     * There is something wrong with MailCap, java mail can not find a handler for the
     * multipart/mixed part, so this bit needs to be added
     */
    private void setDefaultCommandMap(){
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
    }

    public static class Builder{

        private String username ;

        private String passcode ;


        private String host ;

        private String port ;

        private String sPort ;

        private boolean needAuth ;


        private String from ;

        private String to ;

        private String cC ;


        private String subject ;

        private String content ;

        private String[] attachments ;

        private MailListener mailListener ;

        //TODO maybe html or attachment

        public Builder withUsername(String username){
            this.username = username ;
            return this ;
        }

        public Builder withPasscode(String passcode){
            this.passcode = passcode ;
            return this ;
        }

        public Builder withHost(String host){
            this.host = host ;
            return this ;
        }

        public Builder withPort(String port){
            this.port = port ;
            return this ;
        }

        public Builder withSPort(String sPort){
            this.sPort  = sPort ;
            return this ;
        }

        public Builder isNeedAuth(boolean isNeedAuth){
            this.needAuth = isNeedAuth ;
            return this ;
        }

        public Builder withFrom(String from) {
            this.from = from ;
            return this ;
        }

        public Builder withTos(String to){
            this.to = to ;
            return this ;
        }

        public Builder withCcs(String cc){
            this.cC = cc ;
            return this ;
        }

        public Builder withSubject(String subject){
            this.subject = subject ;
            return this ;
        }

        public Builder withContent(String content){
            this.content = content ;
            return this ;
        }

        public Builder withAttachments(String[] attachments){
            this.attachments = attachments ;
            return this ;
        }

        public Builder addListener(MailListener mailListener){
            this.mailListener = mailListener ;
            return this ;
        }

        public Mail build(){
            try
            {
                MailInfo mailInfo = new MailInfo(username , passcode) ;
                if (host == null || host.equals(""))
                    throw new IllegalArgumentException("Email's host should not be null or empty!") ;
                mailInfo.setHost(host);

                if (port == null || port.equals(""))
                    throw new IllegalArgumentException("Email's port should not ne null or empty!") ;
                mailInfo.setPort(port);

                if ((sPort == null || sPort.equals("")) && (port == null || port.equals("")) )
                    throw new IllegalArgumentException("Email's sport should not ne null or empty!") ;
                if ( (port != null && !port.equals("")) && (sPort != null && !sPort.equals("")) ){
                    if (!sPort.equals(port)){
                        mailInfo.setsPort(sPort);
                    }else{
                        mailInfo.setsPort(port);
                    }
                }else if ((sPort != null && !sPort.equals("")) ){
                    mailInfo.setsPort(port);
                }

                mailInfo.setNeedAuth(needAuth);

                mailInfo.setFrom(new InternetAddress(from));

                if (to == null || to.equals(""))
                    throw new IllegalArgumentException("Email's recipients should not ne null or empty!") ;
                Address[] tos = null ;
                if (to.contains(";")){
                    String[] items = to.split(";") ;
                    tos = new InternetAddress[items.length] ;
                    for (int i = 0 ; i < items.length ; i ++){
                        tos[i] = new InternetAddress(items[i]) ;
                    }
                }else{
                    tos = new InternetAddress[]{
                            new InternetAddress(to)
                    };
                }
                mailInfo.setTo(tos);

                if (cC != null && !cC.equals("")){
                    Address[] cCs = null ;
                    if (cC.contains(";")){
                        String[] items = cC.split(";") ;
                        cCs = new InternetAddress[items.length] ;
                        for (int i = 0 ; i < items.length ; i ++){
                            cCs[i] = new InternetAddress(items[i]) ;
                        }
                    }else{
                        cCs = new InternetAddress[]{
                                new InternetAddress(cC)
                        };
                    }
                    mailInfo.setcC(cCs);
                }

                if (subject != null){
                    mailInfo.setSubject(subject);
                }

                if (content != null){
                    mailInfo.setContent(content);
                }

                if (attachments != null){
                    mailInfo.setAttachments(attachments);
                }

                if (mailListener != null){
                    mailInfo.setMailListener(mailListener);
                }

                return new Mail(mailInfo) ;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null ;
        }

    }
}
