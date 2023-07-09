package com.test.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

public class Loader extends Activity {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1024){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                if(Tools.isTbdThere())
                    Tools.ReadSettings();

            }else{
                Toast.makeText(this, "The app was not allowed to write in your storage", Toast.LENGTH_LONG).show();
            }
        }
    }
    void loadDefSettings(){
        Log.d("Setings","Loadiiiiiing");
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

        }

    }
    @Override
    public void onBackPressed() { }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_loader);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Tools.currentActivity = this;
        Boolean b = sharedPreferences.getBoolean("use_second",true);
        Intent intent;
        if(b){
             intent= new Intent(this,SmileActivity.class);
            startActivity(intent);
        }else{
             intent = new Intent(this,YellowActivity.class);
            startActivity(intent);
        }
        loadDefSettings();
       Tools.testPerm();

    }

}
