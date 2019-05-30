package com.hit.jobjob;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import static java.sql.Types.NULL;

public class CalculatorActivity extends AppCompatActivity {

    EditText hour_edittext , wage_edittext ;
    float sum,over_hour,exc,exc1 ;
    int hour, wage ;
    String hour_info , wage_info;
    TextView daily_wages ;
    Button calculatorButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        hour_edittext = findViewById(R.id.a_faw_hours_cal);
        wage_edittext = findViewById(R.id.hourly_wage_cal);
        daily_wages = findViewById(R.id.amount_of_wages);
        calculatorButton = findViewById(R.id.calculate);

        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour_info = hour_edittext.getText().toString();
                String wage_info = wage_edittext.getText().toString();

                if(TextUtils.isEmpty(hour_info)){
                    FancyToast.makeText(CalculatorActivity.this,getString(R.string.msg_error_hour),FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                }
                else if(TextUtils.isEmpty(wage_info)){
                    FancyToast.makeText(CalculatorActivity.this,getString(R.string.msg_error_wage),FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                }
                else {
                    hour = Integer.valueOf(hour_edittext.getText().toString());
                    wage = Integer.valueOf(wage_edittext.getText().toString());
                    if(hour <= 8) {
                        sum = hour*wage ;
                        daily_wages.setText(getString(R.string.daily_wages) + " " +String.valueOf(sum));
                    }
                    else if(hour > 8 && hour <= 12){
                        if(hour <= 10) {
                            exc = hour - 8 ;
                            over_hour = exc*(float)wage*(float)1.25;
                            sum = (8*wage)+over_hour ;
                            daily_wages.setText(getString(R.string.daily_wages) + " " +String.valueOf(sum));
                        }
                        else{
                            exc = hour - 8 ;
                            exc1 = exc - 2;
                            over_hour = ((float)2*(float)wage*(float)1.25) + (exc1*(float)wage*(float)1.50);
                            sum = (8*wage)+over_hour ;
                            daily_wages.setText(getString(R.string.daily_wages) + " " +String.valueOf(sum));
                        }
                    }
                    else if(hour > 12) {
                        exc = hour - 8 ;
                        exc1 = exc - 2;
                        over_hour = ((float)2*(float)wage*(float)1.25) + (exc1*(float)wage*(float)1.50);
                        sum = (8*wage)+over_hour ;
                        daily_wages.setText(getString(R.string.error_hour) +"\n\n"+getString(R.string.daily_wages) + " " +String.valueOf(sum));

                    }

                }
            }

        });
    }
}
