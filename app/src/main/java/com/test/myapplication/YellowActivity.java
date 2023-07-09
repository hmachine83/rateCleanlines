package com.test.myapplication;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class YellowActivity extends Activity implements LoginAdmin.PwdEnteredDialogListener, View.OnClickListener {
    ImageView imageView[] = new ImageView[15];
    TextView tv;
    ImageView logo;
    MyView mv[] = new MyView[11];
    Timer _timer = null;
    Timer timer2 = null;
    Boolean ignore = false;
    String review ="";
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1024){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Tools.writeReview(this,review);
            }else{
                Toast.makeText(this, "The app was not allowed to write in your storage", Toast.LENGTH_LONG).show();
            }
        }
    }
    ImageView bckground;
    ImageView clientLogo;
    ImageView licenseLogo;
    TextView textView;
    @Override
    public void onBackPressed() { }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.currentActivity = this;

            this.requestWindowFeature(Window.FEATURE_NO_TITLE);

            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_yellow);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            imageView[0] = findViewById(R.id.icon1);
            imageView[0].setImageBitmap(Tools.loadBitmapFromAssets(this,"yellow/floor.png"));
            imageView[1] = findViewById(R.id.icon2);
            imageView[1].setImageBitmap(Tools.loadBitmapFromAssets(this,"yellow/odour.png"));
            imageView[2] = findViewById(R.id.icon3);
            imageView[2].setImageBitmap(Tools.loadBitmapFromAssets(this,"yellow/soap.png"));
            imageView[3] = findViewById(R.id.icon4);
            imageView[3].setImageBitmap(Tools.loadBitmapFromAssets(this,"yellow/urinal.png"));
            imageView[4] = findViewById(R.id.icon5);
            imageView[4].setImageBitmap(Tools.loadBitmapFromAssets(this,"yellow/toilet_bowl.png"));
            imageView[5] = findViewById(R.id.icon6);
            imageView[5].setImageBitmap(Tools.loadBitmapFromAssets(this,"yellow/toilet_paper.png"));
            imageView[6] = findViewById(R.id.icon7);
            imageView[6].setImageBitmap(Tools.loadBitmapFromAssets(this,"yellow/wash_basin.png"));
            imageView[7] = findViewById(R.id.icon8);
            imageView[7].setImageBitmap(Tools.loadBitmapFromAssets(this,"yellow/waste_bin.png"));
            imageView[8] = findViewById(R.id.icon9);
            imageView[8].setImageBitmap(Tools.loadBitmapFromAssets(this,"yellow/mirror.png"));
        logo = findViewById(R.id.complogo);


        logo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LoginCleaner lc = new LoginCleaner();
                lc.show(getFragmentManager(),"gggggg");
                return false;
            }
        });

        tv = findViewById(R.id.unabiz);
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(YellowActivity.this);
                if(!sharedPreferences.getBoolean("admin",false)){
                    LoginAdmin loginAdmin = new LoginAdmin();
                    // loginAdmin.listener = this;
                    loginAdmin.show(getFragmentManager(),"ffff");
                }else{
                    Intent intent = new Intent(YellowActivity.this,AdminPage.class);
                    YellowActivity.this.startActivity(intent);
                }

                return false;
            }
        });
        imageView[9] = findViewById(R.id.icon10);
        imageView[10] = findViewById(R.id.icon12);
        for(int i=0;i<11;i++){
            imageView[i].setOnClickListener(this);
        }

        mv[0]=findViewById(R.id.floor);
        mv[1]=findViewById(R.id.odour);
        mv[2]=findViewById(R.id.urinal);
        mv[3]=findViewById(R.id.toilet_bowl);
        mv[4]=findViewById(R.id.toilet_paper);
        mv[5]=findViewById(R.id.wash_basin);
        mv[6]=findViewById(R.id.waste_bin);
        mv[7]=findViewById(R.id.mirror);
        mv[8]=findViewById(R.id.hand_soup);
        mv[9]=findViewById(R.id.good_work);
        mv[10]=findViewById(R.id.can_be_better);

        bckground = findViewById(R.id.background);
        clientLogo = findViewById(R.id.clientLogo);
        licenseLogo = findViewById(R.id.complogo);
        textView = findViewById(R.id.unabiz);
        ViewTreeObserver observer = bckground.getViewTreeObserver();

        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                bckground.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                bckground.setImageBitmap(Tools.getYellowBackground());
               // clientLogo.setImageBitmap(Tools.getClientLogo());
                licenseLogo.setImageBitmap(Tools.getLicenseLogo());
                textView.setText(Tools.getUnabizString());
            }
        });
        timer2 = new Timer();

       timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // use runOnUiThread(Runnable action)

                if(Tools.getSheduleOn(YellowActivity.this)){

                    if(!Tools.isWithin()){
                        Intent intent;
                        intent = new Intent(YellowActivity.this,notInService.class);
                        YellowActivity.this.startActivity(intent);
                        timer2.cancel();
                    }
                }




            }
        }, 1000,60*1000);
    }

    void cleanBck(){
      for(int i =0;i<mv.length;i++){
          mv[i].removeBacground();
      }
      ignore = false;
    }

    @Override
    public void onLoginPositiveClick(DialogFragment dialog, Boolean logged) {
        dialog.dismiss();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("admin",true);
        editor.commit();
        Intent intent = new Intent(YellowActivity.this,AdminPage.class);
        YellowActivity.this.startActivity(intent);
    }

    private Toast mToastToShow;
    public void showToast( final String s) {
        // Set the toast and duration
        int toastDurationInMilliSeconds = 5000;
        mToastToShow = Toast.makeText(this, s, Toast.LENGTH_LONG);

        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.show();
            }
            public void onFinish() {
                mToastToShow.cancel();
                cleanBck();
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();
    }
    void showError(){
       // _timer.cancel();
        showToast("Thank you for your valuable feedback");
    }
    void showError(String s){
        showToast(s);
    }
    @Override
    public void onClick(View v) {
        String s = "Thank you. A service request has been sent to the cleaner on duty.";
        MyView mv;

        if(ignore)return;

        ignore = !ignore;
        switch (v.getId()){

            case R.id.icon1:
                mv =  findViewById(R.id.floor);
                mv.updateBacground();
                showError(s);
                review = "#01,0000,0000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon2:
                mv =  findViewById(R.id.odour);
                mv.updateBacground();
                showError();
                review = "#00,0100,0000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon4:
                mv =  findViewById(R.id.urinal);
                mv.updateBacground();
                showError(s);
                review = "#00,0010,0000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon5:
                mv =  findViewById(R.id.toilet_bowl);
                mv.updateBacground();
                showError(s);
                review = "#00,0000,1000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon6:
                mv =  findViewById(R.id.toilet_paper);
                mv.updateBacground();
                showError(s);
                review = "#00,0000,0100,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon7:
                mv =  findViewById(R.id.wash_basin);
                mv.updateBacground();
                showError(s);
                review = "#00,0000,0010,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon8:
                mv =  findViewById(R.id.waste_bin);
                mv.updateBacground();
                showError(s);
                review = "#00,0000,0001,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon9:
                mv =  findViewById(R.id.mirror);
                mv.updateBacground();
                showError(s);
                review = "#00,0000,0000,1000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon3:
                mv =  findViewById(R.id.hand_soup);
                mv.updateBacground();
                showError(s);
                review = "#00,0010,0000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon10:
                mv =  findViewById(R.id.good_work);
                mv.updateBacground();
                showError();
                review = "#01,0000,0000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon12:
                mv =  findViewById(R.id.can_be_better);
                mv.updateBacground();
                showError();
                review = "#04,0000,0000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
        }
    }
}
