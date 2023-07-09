package com.test.myapplication;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AdminPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_admin_page);
        Tools.currentActivity = this;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!sharedPreferences.contains("mostart")){

            editor.putBoolean("use_shedule",true);
            editor.putBoolean("use_second",true);

            editor.putString("mostart","06:00");
            editor.putString("moend","23:00");

            editor.putString("tustart","06:00");
            editor.putString("tuend","23:00");

            editor.putString("westart","06:00");
            editor.putString("weend","23:00");

            editor.putString("thstart","06:00");
            editor.putString("thend","23:00");

            editor.putString("frstart","06:00");
            editor.putString("frend","23:00");

            editor.putString("sastart","06:00");
            editor.putString("saend","23:00");

            editor.putString("sustart","06:00");
            editor.putString("suend","23:00");

            editor.commit();

        }else{

            Boolean b = false;
            b = sharedPreferences.getBoolean("use_shedule",true);
            ((CheckBox)findViewById(R.id.chkUseScheduler)).setChecked(b);

            b = sharedPreferences.getBoolean("use_second",true);
            ((CheckBox)findViewById(R.id.chkPage)).setChecked(b);

            String s = "";
            s = sharedPreferences.getString("mostart","06:00");
            Tools.loadSchedule(this,"mostart",s);

            s = sharedPreferences.getString("moend","23:00");
            Tools.loadSchedule(this,"moend",s);

            s = sharedPreferences.getString("tustart","06:00");
            Tools.loadSchedule(this,"tustart",s);

            s = sharedPreferences.getString("tuend","23:00");
            Tools.loadSchedule(this,"tuend",s);

            s = sharedPreferences.getString("westart","06:00");
            Tools.loadSchedule(this,"westart",s);

            s = sharedPreferences.getString("weend","23:00");
            Tools.loadSchedule(this,"weend",s);

            s = sharedPreferences.getString("thstart","06:00");
            Tools.loadSchedule(this,"thstart",s);

            s = sharedPreferences.getString("thend","23:00");
            Tools.loadSchedule(this,"thend",s);

            s = sharedPreferences.getString("frstart","06:00");
            Tools.loadSchedule(this,"frstart",s);

            s = sharedPreferences.getString("frend","23:00");
            Tools.loadSchedule(this,"frend",s);

            s = sharedPreferences.getString("sastart","06:00");
            Tools.loadSchedule(this,"sastart",s);

            s = sharedPreferences.getString("saend","23:00");
            Tools.loadSchedule(this,"saend",s);

            s = sharedPreferences.getString("sustart","06:00");
            Tools.loadSchedule(this,"sustart",s);

            s = sharedPreferences.getString("suend","23:00");
            Tools.loadSchedule(this,"suend",s);
        }
    }
    void updateSharedString(String key,String value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);

        editor.commit();

    }
    void updateSharedBoolean(String key,Boolean value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);

        editor.commit();
    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();


        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.chkPage:
               // Log.d("Cehchechhecheche","chkPage clickeeed "+checked);
                    updateSharedBoolean("use_second",checked);

                break;
            case R.id.chkUseScheduler:
                //Log.d("Cehchechhecheche","chkUseScheduler: clickeeed "+checked);
                updateSharedBoolean("use_shedule",checked);
                break;

        }
    }
    public void onButtonClicked(View v){
        Intent intent;
        intent = new Intent(AdminPage.this,Loader.class);
        this.startActivity(intent);

    }
    @Override
    public void onBackPressed() { }
    void showTimeDialog(final TextView textView, final String key){
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String min = "";
                        String hour = "";
                        String value = "";
                        if(minute<10){
                            min = "0"+minute;
                        }else{
                            min = ""+minute;
                        }
                        if(hourOfDay<10){
                            hour = "0"+hourOfDay;
                        }else{
                            hour = ""+hourOfDay;
                        }
                        value = hour + ":" + min;
                        updateSharedString(key,value);
                        textView.setText(value);

                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    public void onTimeUpdate(View v){
        switch (v.getId()){
            case R.id.mostart:
                showTimeDialog((TextView) v,"mostart");
                break;
            case R.id.moend:
                showTimeDialog((TextView) v,"moend");
                Log.d("Printntntn","MOnday endd");
                break;
            case R.id.tustart:
                showTimeDialog((TextView) v,"tustart");
                break;
            case R.id.tuend:
                showTimeDialog((TextView) v,"tuend");
                break;
            case R.id.westart:
                showTimeDialog((TextView) v,"westart");
                break;
            case R.id.weend:
                showTimeDialog((TextView) v,"weend");
                break;
            case R.id.thstart:
                showTimeDialog((TextView) v,"thstart");
                break;
            case R.id.thend:
                showTimeDialog((TextView) v,"thend");
                break;
            case R.id.frstart:
                showTimeDialog((TextView) v,"frstart");
                break;
            case R.id.frend:
                showTimeDialog((TextView) v,"frend");
                break;
            case R.id.sastart:
                showTimeDialog((TextView) v,"sastart");
                break;
            case R.id.saend:
                showTimeDialog((TextView) v,"saend");
                break;
            case R.id.sustart:
                showTimeDialog((TextView) v,"sustart");
                break;
            case R.id.suend:
                showTimeDialog((TextView) v,"suend");
                break;
        }
    }


}
