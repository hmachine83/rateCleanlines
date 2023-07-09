package com.test.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgLoader;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements View.OnClickListener {

    ImageView imageView[] = new ImageView[12];
    Timer _timer;
    Boolean ignore = false;
    String review ="";

    ImageView bckground;
    ImageView licenseLogo;
    TextView textView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        Tools.currentActivity = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        imageView[0] = findViewById(R.id.icon1);
        imageView[0].setImageBitmap(Tools.loadBitmapFromAssets(this,"blue/floor.png"));

        imageView[1] = findViewById(R.id.icon2);
        imageView[1].setImageBitmap(Tools.loadBitmapFromAssets(this,"blue/odour.png"));
        imageView[2] = findViewById(R.id.icon3);
        imageView[2].setImageBitmap(Tools.loadBitmapFromAssets(this,"blue/soap.png"));
        imageView[3] = findViewById(R.id.icon4);
        imageView[3].setImageBitmap(Tools.loadBitmapFromAssets(this,"blue/urinal.png"));
        imageView[4] = findViewById(R.id.icon5);
        imageView[4].setImageBitmap(Tools.loadBitmapFromAssets(this,"blue/toilet_bowl.png"));
        imageView[5] = findViewById(R.id.icon6);
        imageView[5].setImageBitmap(Tools.loadBitmapFromAssets(this,"blue/toilet_paper.png"));
        imageView[6] = findViewById(R.id.icon7);
        imageView[6].setImageBitmap(Tools.loadBitmapFromAssets(this,"blue/wash_basin.png"));
        imageView[7] = findViewById(R.id.icon8);
        imageView[7].setImageBitmap(Tools.loadBitmapFromAssets(this,"blue/waste_bin.png"));
        imageView[8] = findViewById(R.id.icon9);
        imageView[8].setImageBitmap(Tools.loadBitmapFromAssets(this,"blue/mirror.png"));

        for(int i=0;i<9;i++){
             imageView[i].setOnClickListener(this);

        }
        _timer = new Timer();

        _timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // use runOnUiThread(Runnable action)

                        _timer.cancel();
                       MainActivity.this.finish();


            }
        }, 10*1000,1000);


        bckground = findViewById(R.id.background);
        ViewTreeObserver observer = bckground.getViewTreeObserver();

        licenseLogo = findViewById(R.id.complogo);
        textView = findViewById(R.id.unabiz);
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                bckground.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                bckground.setImageBitmap(Tools.getSmileyBackground());
                licenseLogo.setImageBitmap(Tools.getLicenseLogo());
                textView.setText(Tools.getUnabizString());
            }
        });
    }

    public void countDown( final int time, final Activity activity) {
        // Set the toast and duration
        int toastDurationInMilliSeconds = time;


        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();

                    }
                });
            }
        };


        toastCountDown.start();
    }
    void showError(){
        _timer.cancel();
        int duration = 5000;
        countDown(3000,this);
        Toast toast = Toast.makeText(this, "Thank you. A service request has been sent to the cleaner on duty.", duration);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }
    @Override
    public void onBackPressed() { }
    @Override
    public void onClick(View v) {

        MyView mv;
        if(ignore)return;

        ignore = !ignore;
        switch (v.getId()){

            case R.id.icon1:
                 mv =  findViewById(R.id.floor);
                 mv.updateBacground();
                showError();
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
                showError();
                review = "#00,0010,0000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon5:
                mv =  findViewById(R.id.toilet_bowl);
                mv.updateBacground();
                showError();
                review = "#00,0000,1000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon6:
                mv =  findViewById(R.id.toilet_paper);
                mv.updateBacground();
                showError();
                review = "#00,0000,0100,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon7:
                mv =  findViewById(R.id.wash_basin);
                mv.updateBacground();
                showError();
                review = "#00,0000,0010,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon8:
                mv =  findViewById(R.id.waste_bin);
                mv.updateBacground();
                showError();
                review = "#00,0000,0001,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon9:
                mv =  findViewById(R.id.mirror);
                mv.updateBacground();
                showError();
                review = "#00,0000,0000,1000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;
            case R.id.icon3:
                mv =  findViewById(R.id.hand_soup);
                mv.updateBacground();
                showError();
                review = "#00,0010,0000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                break;

        }
    }
}
