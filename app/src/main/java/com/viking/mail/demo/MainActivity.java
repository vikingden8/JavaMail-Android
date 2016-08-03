package com.viking.mail.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.viking.mail.Mail;
import com.viking.mail.MailListener;

public class MainActivity extends AppCompatActivity {

    private Button simple_text_mail ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simple_text_mail = (Button) findViewById(R.id.simple_text_mail) ;
        simple_text_mail.setOnClickListener(new SimpleTextMailClickListener());
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

    public void sendSimpleTextMail(){
        Mail.Builder mail = new Mail.Builder()
                .withUsername("xxxx@163.com")
                .withPasscode("xxxx")
                .withHost("smtp.163.com")
                .withPort("465")
                .isNeedAuth(true)
                .withFrom("xxxxx@163.com")
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
                })
                ;
        Mail rMail = mail.build() ;
        rMail.send();
    }
}
