package com.hit.jobjob;

import android.Manifest;
import android.app.Dialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

  final int NUM_SECOND = 60;
  final int TIMER_PERMISSION_REQUEST = 1;
  String user_name;
  int flag_if_is_pic , num_minute , num_hour;
 // String number_phone = "0545546786";
  Editable edit_text_value , edit_phone_number;
  long back_pressed;

  @Override
  public void onBackPressed() {

    new FancyAlertDialog.Builder(HomeActivity.this)

            .setBackgroundColor(Color.parseColor("#d43939"))  //Don't pass R.color.colorvalue
            .setMessage(getString(R.string.msg_out))
            .setNegativeBtnText(getString(R.string.msg_no))
            .setPositiveBtnBackground(Color.parseColor("#D1D0D0"))  //Don't pass R.color.colorvalue
            .setPositiveBtnText(getString(R.string.msg_yes))
            .setNegativeBtnBackground(Color.parseColor("#d43939"))  //Don't pass R.color.colorvalue
            .setAnimation(Animation.SLIDE)
            .isCancellable(true)
            .setIcon(R.drawable.ic_pan_tool_black_24dp, Icon.Visible)
            .OnPositiveClicked(new FancyAlertDialogListener() {
              @Override
              public void OnClick() {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
              }
            })
            .OnNegativeClicked(new FancyAlertDialogListener() {
              @Override
              public void OnClick() {
              }
            })
            .build();
  }

  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    TextView home_user_name = findViewById(R.id.home_user_name);
    ImageView user_photo = findViewById(R.id.home_user_image);

    user_name = getIntent().getStringExtra("username");
    flag_if_is_pic = getIntent().getIntExtra("flag_if_is_pic", 0);
    user_photo.setImageBitmap((Bitmap) getIntent().getParcelableExtra("photo"));

    home_user_name.setText(user_name);

    if (flag_if_is_pic == 0) {
      FancyToast.makeText(this, getString(R.string.error_no_photo), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

    }

    final Button mailButton = findViewById(R.id.button_mail);
    mailButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, MailActivity.class);
        startActivity(intent);
      }
    });

    Button sms_button = findViewById(R.id.button_sms);
    sms_button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(HomeActivity.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.sms_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(HomeActivity.this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText sms_dialog = (EditText) mView.findViewById(R.id.text_sms);
        final EditText to_sms = (EditText) mView.findViewById(R.id.to_sms);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(getText(R.string.send), new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialogBox, int id) {
                    edit_text_value = sms_dialog.getText();
                    edit_phone_number = to_sms.getText();
                    Uri uri = Uri.parse("smsto:" + edit_phone_number.toString());
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra("sms_body", edit_text_value.toString());
                    startActivity(intent);
                  }
                })

                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialogBox, int id) {
                            dialogBox.cancel();
                          }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
      }
    });


    final Button alarmButton = findViewById(R.id.button_alarm);
    alarmButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, AlarmActivity.class);
        startActivity(intent);
      }
    });


    Button calendarButton = findViewById(R.id.button_calender);
    calendarButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, CalendarActivity.class);
        startActivity(intent);
      }
    });

    final Button contactButton = findViewById(R.id.button_new_add);
    contactButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, ContactActivity.class);
        startActivity(intent);
      }
    });

    final Button callButton = findViewById(R.id.button_call);
    callButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(HomeActivity.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.call_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(HomeActivity.this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText call_number = (EditText) mView.findViewById(R.id.call_number);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(getText(R.string.call), new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialogBox, int id) {
                    edit_text_value = call_number.getText();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + edit_text_value));
                    startActivity(Intent.createChooser(intent, getString(R.string.msg_yes)));
                  }
                })

                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialogBox, int id) {
                            dialogBox.cancel();
                          }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
      }
    });

    final Button calucButton = findViewById(R.id.button_currency);
    calucButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomeActivity.this, CalculatorActivity.class);
        startActivity(intent);
      }
    });

    Button youtubeButton = findViewById(R.id.button_youtube);
    youtubeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/watch?v=g5kB8gAy4rE"));
        startActivity(intent);
      }
    });

    Button webButton = findViewById(R.id.button_web);
    webButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);

          intent.putExtra(SearchManager.QUERY, "jobJob");
          startActivity(intent);
        } catch (ActivityNotFoundException e) {

          FancyToast.makeText(HomeActivity.this, getString(R.string.error_web), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
        }

      }
    });

  }

  public void InfoClick(View view) {
    com.geniusforapp.fancydialog.FancyAlertDialog.Builder alert = new com.geniusforapp.fancydialog.FancyAlertDialog.Builder(HomeActivity.this)
            .setimageResource(R.drawable.info3_icon)
            .setTextTitle(getString(R.string.info_tittle))

            .setBody(getString(R.string.info_con))
            .setNegativeColor(R.color.redColor)
            .setPositiveButtonText(getString(R.string.continue_text))
            .setPositiveColor(R.color.redColor)
            .setOnPositiveClicked(new com.geniusforapp.fancydialog.FancyAlertDialog.OnPositiveClicked() {
              @Override
              public void OnClick(View view, Dialog dialog) {
                dialog.dismiss();

              }
            }).setButtonsGravity(com.geniusforapp.fancydialog.FancyAlertDialog.PanelGravity.CENTER)
            .setBodyGravity(com.geniusforapp.fancydialog.FancyAlertDialog.TextGravity.CENTER)
            .setTitleGravity(com.geniusforapp.fancydialog.FancyAlertDialog.TextGravity.CENTER)

            .setCancelable(false)
            .build();
    alert.show();
  }
}

