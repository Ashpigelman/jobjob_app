package com.hit.jobjob;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shashank.sony.fancytoastlib.FancyToast;

public class ContactActivity extends AppCompatActivity {

    EditText first_name, last_name, phone_number, mail;
    String first_name_user, last_name_user, number_phone_user, mail_user , phone_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        first_name = findViewById(R.id.first_name_contact);
        last_name = findViewById(R.id.last_name_contact);
        phone_number = findViewById(R.id.phone_contact);
        mail = findViewById(R.id.mail_contact);

        Button add_button = findViewById(R.id.button_send_contact);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_info = phone_number.getText().toString();
                GetData();
                if(TextUtils.isEmpty(phone_info)) {
                    FancyToast.makeText(ContactActivity.this,getString(R.string.msg_error_phone),FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                }
                else {
                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                    intent.putExtra(ContactsContract.Intents.Insert.NAME, first_name_user + " " + last_name_user);
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, number_phone_user);
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, mail_user);

                    startActivity(intent);
                }
            }
        });
    }

    private void GetData() {
        first_name_user = first_name.getText().toString();
        last_name_user = last_name.getText().toString();
        number_phone_user = phone_number.getText().toString();
        mail_user = mail.getText().toString();
    }
}

