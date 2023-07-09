package com.test.myapplication;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PictureDrawable;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
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


public class SmileActivity extends Activity implements LoginAdmin.PwdEnteredDialogListener, View.OnClickListener {

    ImageView imageView[] = new ImageView[12];
    private ImageView mImageView=null;
    TextView tv;
    MyView mv[] = new MyView[5];
     ImageView logo;
     Boolean ignore = false;
    String review ="";
    Timer _timer;

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
    String excelent = "file:///android_asset/green2.svg";
    String verygood = "file:///android_asset/green1.svg";
    String good = "file:///android_asset/yellow1.svg";
    String meak = "file:///android_asset/meak1.svg";
    String bad = "file:///android_asset/red1.svg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Tools.currentActivity = SmileActivity.this;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_smile);




        mImageView = findViewById(R.id.sendrequest);

        bckground = findViewById(R.id.background);

        logo = findViewById(R.id.complogo);
        logo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LoginCleaner lc = new LoginCleaner();
                lc.show(getFragmentManager(),"gggggg");
                return false;
            }
        });
        imageView[0] = findViewById(R.id.icon1);


        imageView[1] = findViewById(R.id.icon2);


        imageView[2] = findViewById(R.id.icon3);


        imageView[3] = findViewById(R.id.icon4);


        imageView[4] = findViewById(R.id.icon5);

        loadSvgs();

        for (int i = 0;i<5;i++){
            imageView[i].setOnClickListener(this);
        }
        ViewTreeObserver observer = mImageView.getViewTreeObserver();
        clientLogo = findViewById(R.id.clientLogo);
        licenseLogo = findViewById(R.id.complogo);
        textView = findViewById(R.id.unabiz);
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                mImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                makeButton();
                toast = new Toast(SmileActivity.this);
                bckground.setImageBitmap(Tools.getSmileyBackground());
                clientLogo.setImageBitmap(Tools.getClientLogo());
                licenseLogo.setImageBitmap(Tools.getLicenseLogo());
                textView.setText(Tools.getUnabizString());
            }
        });

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SmileActivity.this);
                if(!sharedPreferences.getBoolean("admin",false)){
                    LoginAdmin loginAdmin = new LoginAdmin();
                    // loginAdmin.listener = this;
                    loginAdmin.show(getFragmentManager(),"ffff");
                }else{
                    Intent intent = new Intent(SmileActivity.this,AdminPage.class);
                    SmileActivity.this.startActivity(intent);
                }

                return false;
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmileActivity.this,MainActivity.class);
                SmileActivity.this.startActivity(intent);
            }
        });

        mv[0]=findViewById(R.id.excellent);
        mv[1]=findViewById(R.id.very_good);
        mv[2]=findViewById(R.id.good);
        mv[3]=findViewById(R.id.poor);
        mv[4]=findViewById(R.id.very_poor);

        for(int i=0;i<5;i++){
            mv[i].removeFrame();
        }

        _timer = new Timer();

        _timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // use runOnUiThread(Runnable action)

                if(Tools.getSheduleOn(SmileActivity.this)){

                    if(!Tools.isWithin()){
                        _timer.cancel();
                        Intent intent;
                        intent = new Intent(SmileActivity.this,notInService.class);
                        SmileActivity.this.startActivity(intent);

                    }
                }




            }
        }, 1000,60*1000);

    }
    @Override
    public void onBackPressed() { }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        _timer.cancel();
    }

    public void onLoginPositiveClick(DialogFragment dialog, Boolean logged){

        Log.d("Back aback back","return from adminlogin");
        dialog.dismiss();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("admin",true);
        editor.commit();
        Intent intent = new Intent(SmileActivity.this,AdminPage.class);
        SmileActivity.this.startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    void makeButton(){
        Bitmap bitmap = Bitmap.createBitmap(
                mImageView.getWidth(), // Width
                mImageView.getHeight(), // Height
                Bitmap.Config.ARGB_8888 // Config
        );

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(bitmap);

        // Draw a solid color on the canvas as background
        canvas.drawColor(Color.TRANSPARENT);

        // Initialize a new Paint instance to draw the rounded rectangle
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xff2e75b6);
        paint.setAntiAlias(true);

        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(4.0f);
        paint1.setColor(Color.BLACK);
        paint1.setAntiAlias(true);

        Paint paintText = new Paint();
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(32);
        paintText.setColor(Color.WHITE);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setAntiAlias(true);
        // Set an offset value in pixels to draw rounded rectangle on canvas
        int offset =  5;


        RectF rectF = new RectF(
                offset, // left
                offset, // top
                canvas.getWidth() - offset, // right
                canvas.getHeight() - offset // bottom
        );



        // Define the corners radius of rounded rectangle
        int cornersRadius = 15;

        // Finally, draw the rounded corners rectangle object on the canvas
        canvas.drawRoundRect(
                rectF, // rect
                cornersRadius, // rx
                cornersRadius, // ry
                paint // Paint
        );
        canvas.drawRoundRect(
                rectF, // rect
                cornersRadius, // rx
                cornersRadius, // ry
                paint1 // Paint
        );
        String text = "Service Request";

        canvas.drawText(text, canvas.getWidth()/2, canvas.getHeight()/2+5, paintText);
        mImageView.setImageBitmap(bitmap);
    }

    void cleanBck(){

        for(int i =0;i<mv.length;i++){
            mv[i].removeBacground();
            mv[i].removeFrame();
        }
        ignore = false;
    }

    private Toast mToastToShow;
    public void showToast( ) {
        // Set the toast and duration
        int toastDurationInMilliSeconds = 5000;
        mToastToShow = Toast.makeText(this, "Thank you for your valuable feedback", Toast.LENGTH_LONG);

        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.show();
            }
            public void onFinish() {
                mToastToShow.cancel();

                loadSvgs();
                ignore = false;
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();
    }
    Toast toast;
    void showError(){

        showToast();
    }
    void loadSvgs(){

        Tools.loadFromUri(this,imageView[0],excelent);

        Tools.loadFromUri(this,imageView[1],verygood);

        Tools.loadFromUri(this,imageView[2],good);

        Tools.loadFromUri(this,imageView[3],meak);

        Tools.loadFromUri(this,imageView[4],bad);
    }
    private static Bitmap pictureDrawable2Bitmap(PictureDrawable pd) {

        Bitmap bitmap = Bitmap.createBitmap(pd.getIntrinsicWidth(), pd.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawPicture(pd.getPicture());
        return bitmap;
    }

    void  setLocked2(ImageView v)
    {
        Bitmap bitmap = pictureDrawable2Bitmap((PictureDrawable) v.getDrawable());
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0f); //Remove Colour
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorFilter);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap,0,0,paint);
        v.setImageBitmap(bitmap);
    }
    void setGray(int p){
        for(int i=0;i<5;i++){
            if(p==i)
                continue;
           setLocked2(imageView[i]);

        }
    }

    @Override
    public void onClick(View v) {
        MyView mv;
        if(ignore)return;

        ignore = !ignore;

        switch (v.getId()) {

            case R.id.icon1:
                mv = this.mv[0];
               // mv.updateBacground();
                showError();
                review = "#01,0000,0000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                setGray(0);
                break;
            case R.id.icon2:
                mv = this.mv[1];
                //mv.updateBacground();
                showError();
                review = "#02,0000,0000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                setGray(1);
                break;
            case R.id.icon3:
                mv = this.mv[2];
                //mv.updateBacground();
                showError();
                review = "#03,0000,0000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                setGray(2);
                break;
            case R.id.icon4:
                mv = this.mv[3];
                //mv.updateBacground();
                showError();
                review = "#04,0000,0000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                setGray(3);
                break;
            case R.id.icon5:
                mv = this.mv[4];
                //mv.updateBacground();
                showError();
                review = "#05,0000,0000,0000,0000,"+Tools.getCleanerId(this)+"%";
                Tools.writeReview(this,review);
                setGray(4);
                break;
        }
    }
}
