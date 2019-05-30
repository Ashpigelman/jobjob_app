package com.hit.jobjob;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    EditText sub_text , desc_text;
    String  subject1 , description1;
    int hour_time_start,hour_time_end, min_time_start,min_time_end, year_time, month_time, day_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Button hourStartButton = findViewById(R.id.button_calender_time_start);
        hourStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showHourPickerStart();
            }
        });

        Button hourEndButton = findViewById(R.id.button_calender_time_end);
        hourEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showHourPickerEnd();
            }
        });

        Button sendButton = findViewById(R.id.button_add_calender);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData();
                if(TextUtils.isEmpty(subject1)){
                    FancyToast.makeText(CalendarActivity.this,getString(R.string.msg_error_sub),FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();

                }
                else if(TextUtils.isEmpty(description1)){
                    FancyToast.makeText(CalendarActivity.this,getString(R.string.msg_error_des),FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                }

                else if(hour_time_end < hour_time_start)
                {
                    Calendar beginTime = Calendar.getInstance();
                    beginTime.set(year_time, month_time, day_time, hour_time_start, min_time_start);

                    Calendar endTime = Calendar.getInstance();
                    endTime.set(year_time, month_time, day_time+1, hour_time_end, min_time_end);
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                    beginTime.getTimeInMillis())
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                    endTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, subject1)
                            .putExtra(CalendarContract.Events.DESCRIPTION, description1)
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                    startActivity(intent);
                }
                else {
                    Calendar beginTime = Calendar.getInstance();
                    beginTime.set(year_time, month_time, day_time, hour_time_start, min_time_start);

                    Calendar endTime = Calendar.getInstance();
                    endTime.set(year_time, month_time, day_time, hour_time_end, min_time_end);
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                    beginTime.getTimeInMillis())
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                    endTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, subject1)
                            .putExtra(CalendarContract.Events.DESCRIPTION, description1)
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                    startActivity(intent);
                }
            }
        });

        sub_text =findViewById(R.id.subject_edittext);
        desc_text =findViewById(R.id.description_edittext);

        CalendarView calendarView =findViewById(R.id.calender);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView view, int year, int month, int dayOfMonth) {

                year_time = year;
                day_time = dayOfMonth;
                month_time = month;
            }
        });
    }

    private void GetData() {
        subject1 = sub_text.getText().toString();
        description1 = desc_text.getText().toString();
    }

    private void showHourPickerStart() {

        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);

                    min_time_start = minute;
                    hour_time_start = hourOfDay;

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(CalendarActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle(getString(R.string.choose_hour_start));
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    private void showHourPickerEnd() {

        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);

                    min_time_end = minute;
                    hour_time_end = hourOfDay;

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(CalendarActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle(getString(R.string.choose_hour_end));
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }
}