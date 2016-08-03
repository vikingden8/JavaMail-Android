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

import javax.mail.Address;

/**
 * store mail's information
 * author : Viking Den <vikingden@live.com>
 * time   : 2016/8/3 23:02
 */
public class MailInfo {

    private String username ;

    private String passcode ;


    private String host ;

    private String port ;

    private String sPort ;

    private boolean needAuth = true;


    private Address from ;

    private Address[] to ;

    private Address[] cC ;


    private String subject ;

    private String content ;

    private MailListener mailListener ;

    public MailInfo(String username ,String passcode){
        this.username = username ;
        this.passcode = passcode ;
    }

    public String getUsername() {
        return username;
    }

    public String getPasscode() {
        return passcode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getsPort() {
        return sPort;
    }

    public void setsPort(String sPort) {
        this.sPort = sPort;
    }

    public boolean isNeedAuth() {
        return needAuth;
    }

    public void setNeedAuth(boolean needAuth) {
        this.needAuth = needAuth;
    }

    public Address getFrom() {
        return from;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public Address[] getTo() {
        return to;
    }

    public void setTo(Address[] to) {
        this.to = to;
    }

    public Address[] getcC() {
        return cC;
    }

    public void setcC(Address[] cC) {
        this.cC = cC;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMailListener(MailListener listener){
        this.mailListener = listener ;
    }

    public MailListener getMailListener(){
        return mailListener ;
    }
}
