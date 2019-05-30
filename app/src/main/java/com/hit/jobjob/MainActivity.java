package com.hit.jobjob;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {

    Bitmap bitmap;
    ImageView user_image;
    final int CAMERA_REQ = 1;
    int flag_if_is_pic = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CAMERA_REQ && resultCode==RESULT_OK) {
            bitmap = (Bitmap)data.getExtras().get("data");
            user_image.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText= findViewById(R.id.user_name);
        Button button =findViewById(R.id.user_button);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editText.getText().toString();

                if(TextUtils.isEmpty(userName)){
                    FancyToast.makeText(MainActivity.this,getString(R.string.error_user_name),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }

                else
                {
                    Intent intent = new Intent(MainActivity.this , HomeActivity.class);
                    intent.putExtra("username" , userName);
                    intent.putExtra("photo",bitmap);
                    intent.putExtra("flag_if_is_pic",flag_if_is_pic);

                    startActivity(intent);
                }

            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        user_image = findViewById(R.id.user_image);
        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQ);
                flag_if_is_pic= 1;
            }
        });
    }
}
