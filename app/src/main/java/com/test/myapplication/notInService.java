package com.test.myapplication;

import android.app.Activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class notInService extends Activity implements LoginAdmin.PwdEnteredDialogListener {

    TextView tv;
    private Timer timer2;
    ImageView bckground;
    ImageView ns_img;
    ImageView licenseLogo;
    TextView textView;
    @Override
    public void onBackPressed() { }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_not_in_service);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Tools.currentActivity = this;
        bckground = findViewById(R.id.background);
        ns_img = findViewById(R.id.ns_img);
        licenseLogo = findViewById(R.id.complogo);
        textView = findViewById(R.id.unabiz);
        ViewTreeObserver observer = bckground.getViewTreeObserver();

        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                bckground.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                bckground.setImageBitmap(Tools.getNoServiceBackground());
                ns_img.setImageBitmap(Tools.getNoServiceImage());
                licenseLogo.setImageBitmap(Tools.getLicenseLogo());
                textView.setText(Tools.getUnabizString());
            }
        });

        tv = findViewById(R.id.unabiz);
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(notInService.this);
                if(!sharedPreferences.getBoolean("admin",false)){
                    LoginAdmin loginAdmin = new LoginAdmin();
                    // loginAdmin.listener = this;
                    loginAdmin.show(getFragmentManager(),"ffff");
                }else{
                    Intent intent = new Intent(notInService.this,AdminPage.class);
                    notInService.this.startActivity(intent);
                }

                return false;
            }
        });
        timer2 = new Timer();

        timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // use runOnUiThread(Runnable action)

                    if(Tools.isWithin()){
                        Intent intent;
                        intent = new Intent(notInService.this,Loader.class);
                       notInService.this.startActivity(intent);
                        timer2.cancel();
                    }
            }
        }, 1000,60*1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer2.cancel();
    }

    @Override
    public void onLoginPositiveClick(DialogFragment dialog, Boolean logged) {

    }
}
