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

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * create a authenticator for session when it is created
 *
 * author : Viking Den <vikingden@live.com>
 * time   : 2016/8/3 22:25
 */
public class MailAuthenticator extends Authenticator{

    /**
     * email's username
     */
    private final String username ;

    /**
     * email's passcode
     */
    private final String passcode ;

    public MailAuthenticator(String username , String passcode){
        this.username = username ;
        this.passcode = passcode ;
    }

    /**
     * Called when password authentication is needed.  Subclasses should
     * override the default implementation, which returns null. <p>
     *
     * Note that if this method uses a dialog to prompt the user for this
     * information, the dialog needs to block until the user supplies the
     * information.  This method can not simply return after showing the
     * dialog.
     * @return The PasswordAuthentication collected from the
     *		user, or null if none is provided.
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication(){

        return new PasswordAuthentication(username , passcode);

    }

}
