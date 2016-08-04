package com.viking.mail.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.viking.mail.Mail;
import com.viking.mail.MailListener;

public class MainActivity extends AppCompatActivity {

    private static final String USERNAME = "xxxxxx" ;
    private static final String PASSCODE = "xxxxxxx" ;

    private Button simple_text_mail ;
    private Button simple_text_mail_multi_receiver ;

    private Button simple_text_mail_cc ;

    private Button mail_with_attachment ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simple_text_mail = (Button) findViewById(R.id.simple_text_mail) ;
        simple_text_mail.setOnClickListener(new SimpleTextMailClickListener());

        simple_text_mail_multi_receiver = (Button) findViewById(R.id.simple_text_mail_multi_receiver) ;
        simple_text_mail_multi_receiver.setOnClickListener(new SimpleTextMailMultiReceiverClickListener());

        simple_text_mail_cc = (Button) findViewById(R.id.simple_text_mail_cC) ;
        simple_text_mail_cc.setOnClickListener(new SimpleTextMailWithCcClickListener());

        mail_with_attachment = (Button) findViewById(R.id.mail_with_attachment) ;
        mail_with_attachment.setOnClickListener(new MailWithAttachmentClickListener());

    }

    private class SimpleTextMailClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendSimpleTextMail() ;
                }
            }).start() ;
        }
    }

    private class SimpleTextMailMultiReceiverClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendSimpleTextMailMultiReceiver() ;
                }
            }).start() ;
        }

    }

    private class SimpleTextMailWithCcClickListener implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendSimpleTextMailCc() ;
                }
            }).start() ;
        }
    }

    private class MailWithAttachmentClickListener implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendMailWithAttachments() ;
                }
            }).start() ;
        }
    }

    private void sendMailWithAttachments(){
        String[] attachments = new String[]{
            "/mnt/sdcard/source.txt",
            "/mnt/sdcard/pimsecure_debug.txt"
        };
        Mail.Builder mailBuilder = new Mail.Builder()
                .withUsername(USERNAME)
                .withPasscode(PASSCODE)
                .withHost("smtp.163.com")
                .withPort("465")
                .isNeedAuth(true)
                .withFrom("dengwangjian880714@163.com")
                .withTos("1047176309@qq.com;dengwj@gionee.com")
                .withCcs("1047176309@qq.com;dengwj@gionee.com;vikingden@live.com")
                .withSubject("Send mail without intent from android")
                .withContent(getHtmlContent())
                .withAttachments(attachments)
                .addListener(new MailListener() {
                    @Override
                    public void onSuccess() {
                        Log.i("viking" , "yep , success") ;
                    }

                    @Override
                    public void onFail(Exception e) {
                        Log.i("viking" , "oh no , fail !!!" + e.getMessage()) ;
                    }
                });
        Mail mail = mailBuilder.build() ;
        mail.send();
    }

    private void sendSimpleTextMailCc() {
        Mail.Builder mailBuilder = new Mail.Builder()
                .withUsername(USERNAME)
                .withPasscode(PASSCODE)
                .withHost("smtp.163.com")
                .withPort("465")
                .isNeedAuth(true)
                .withFrom("dengwangjian880714@163.com")
                .withTos("1047176309@qq.com;dengwj@gionee.com")
                .withCcs("1047176309@qq.com;dengwj@gionee.com;vikingden@live.com")
                .withSubject("Send mail without intent from android")
                .withContent(getHtmlContent())
                .addListener(new MailListener() {
                    @Override
                    public void onSuccess() {
                        Log.i("viking" , "yep , success") ;
                    }

                    @Override
                    public void onFail(Exception e) {
                        Log.i("viking" , "oh no , fail !!!") ;
                    }
                });
        Mail mail = mailBuilder.build() ;
        mail.send();
    }

    private void sendSimpleTextMailMultiReceiver() {
        Mail.Builder mailBuilder = new Mail.Builder()
                .withUsername(USERNAME)
                .withPasscode(PASSCODE)
                .withHost("smtp.163.com")
                .withPort("465")
                .isNeedAuth(true)
                .withFrom("dengwangjian880714@163.com")
                .withTos("1047176309@qq.com;dengwj@gionee.com")
                .withSubject("Send mail without intent from android")
                .withContent("Send mail without intent from android , don't let me fail......-_-")
                .addListener(new MailListener() {
                    @Override
                    public void onSuccess() {
                        Log.i("viking" , "yep , success") ;
                    }

                    @Override
                    public void onFail(Exception e) {
                        Log.i("viking" , "oh no , fail !!!") ;
                    }
                });
        Mail mail = mailBuilder.build() ;
        mail.send();
    }

    public void sendSimpleTextMail(){
        Mail.Builder mailBuilder = new Mail.Builder()
                .withUsername(USERNAME)
                .withPasscode(PASSCODE)
                .withHost("smtp.163.com")
                .withPort("465")
                .isNeedAuth(true)
                .withFrom("dengwangjian880714@163.com")
                .withTos("1047176309@qq.com")
                .withSubject("Send mail without intent from android")
                .withContent("Send mail without intent from android , don't let me fail......-_-")
                .addListener(new MailListener() {
                    @Override
                    public void onSuccess() {
                        Log.i("viking" , "yep , success") ;
                    }

                    @Override
                    public void onFail(Exception e) {
                        Log.i("viking" , "oh no , fail !!!") ;
                    }
                });
        Mail mail = mailBuilder.build() ;
        mail.send();
    }

    public String getHtmlContent(){
        return "\n" +
                "<HTML>\n" +
                "<HEAD>\n" +
                "<TITLE>AutoTestReport</TITLE>\n" +
                "<META content=\"text/html; charset=utf-8\" http-equiv=Content-Type>\n" +
                "<STYLE type=text/css>\n" +
                "BODY {\n" +
                "FONT: 12px verdana,arial,helvetica\n" +
                "}\n" +
                "TABLE TR TD { FONT-SIZE: 12px }\n" +
                "TABLE TR TH { FONT-SIZE: 12px }\n" +
                "TABLE.details TR TH { BACKGROUND: #a6caf0; FONT-WEIGHT: bold; TEXT-ALIGN: left }\n" +
                "TABLE.details TR TD { BACKGROUND: #eeeee0 }\n" +
                "TABLE.about TR TH {BACKGROUND: #a6caf0; FONT-WEIGHT: bold; TEXT-ALIGN: left}\n" +
                "TABLE.about TR TD {BACKGROUND: #eeeee0}\n" +
                "P {MARGIN-BOTTOM: 1em; MARGIN-TOP: 0.5em; LINE-HEIGHT: 1.5em}\n" +
                "H1 {FONT: bold 165% verdana,arial,helvetica; MARGIN: 0px 0px 5px}\n" +
                "H2 {MARGIN-BOTTOM: 0.5em; FONT: bold 125% verdana,arial,helvetica; MARGIN-TOP: 1em}\n" +
                "H3 {MARGIN-BOTTOM: 0.5em; FONT: bold 115% verdana,arial,helvetica}\n" +
                "H4 {MARGIN-BOTTOM: 0.5em; FONT: bold 100% verdana,arial,helvetica}\n" +
                "H5 {MARGIN-BOTTOM: 0.5em; FONT: bold 100% verdana,arial,helvetica}\n" +
                "H6 {MARGIN-BOTTOM: 0.5em; FONT: bold 100% verdana,arial,helvetica}\n" +
                ".Error {FONT-WEIGHT: bold; COLOR: purple}\n" +
                ".Error {FONT-WEIGHT: bold; COLOR: purple}\n" +
                "TD.P10 {width:10%;}\n" +
                "TD.P15 {width:15%;}\n" +
                "TD.P5 {width:5%;}\n" +
                "TD.P20 {width:20%;}\n" +
                "TD.P25 {width:25%;}\n" +
                "TD.P30 {width:30%;}\n" +
                "TD.P35 {width:35%;}\n" +
                "TD.P40 {width:40%;}\n" +
                "TD.P45 {width:45%;}\n" +
                "</STYLE>\n" +
                "</HEAD>\n" +
                "<BODY>\n" +
                "<H1 align=center>GBL7358B01_B_T3522 测试报告</H1><BR>\n" +
                "<TABLE width=\"95%\"><TBODY><TR>\n" +
                "<TD align=right>测试平台部自动化组</TD></TR></TBODY></TABLE>\n" +
                "<HR align=left SIZE=1 width=\"95%\">\n" +
                "\n" +
                "<h2>测试结论:</h2><table width='95%'><tr><td><H3><FONT color=red>&nbsp;&nbsp;&nbsp;&nbsp;不通过</FONT></H3></td></tr></table>\n" +
                "<!--PHONE_ERROR_CONTENT此版本自动化测试时\"流量宝\"持续报错，版本不具备可测性，测试停止，请测试同事提交bugPHONE_ERROR_CONTENT-->\n" +
                "<h2>测试详情:</h2><table width='95%'><tr><td><H3><FONT color='red'>&nbsp;&nbsp;&nbsp;&nbsp;此版本自动化测试时\"流量宝\"持续报错，版本不具备可测性，测试停止，请测试同事提交bug</FONT></H3></td></tr></table>\n" +
                "</BODY>\n" +
                "</HTML>" ;
    }
}
