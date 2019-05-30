package com.hit.jobjob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shashank.sony.fancytoastlib.FancyToast;

public class MailActivity extends AppCompatActivity {

    EditText to_mail , subject_mail , content_in_mail;
    String to , subject , massage ,to_info , subject_info ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        to_mail = findViewById(R.id.mail_to);
        subject_mail = findViewById(R.id.mail_subject);
        content_in_mail =findViewById(R.id.content_mail);

        Button sendButton =findViewById(R.id.button_send_mail);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to_info = to_mail.getText().toString();

                GetData();
                if(TextUtils.isEmpty(to_info)){
                    FancyToast.makeText(MailActivity.this,getString(R.string.msg_error_to),FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                }
                else {
                    Intent intent = new Intent( Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL ,new String[]{to});
                    intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                    intent.putExtra(Intent.EXTRA_TEXT,massage);

                    intent.setType("massage/rfc822");
                    startActivity(Intent.createChooser(intent,getString(R.string.send_mail_with)));
                }

            }
        });
    }

    private void GetData() {
        to = to_mail.getText().toString();
        subject = subject_mail.getText().toString();
        massage = content_in_mail.getText().toString();
    }
}
