package com.test.myapplication;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class LoginCleaner extends DialogFragment implements View.OnClickListener {
    int atmpt = 0;
    int pwdValid = 0;
    int arr[] = new int[4];
    void checkLogin(int val){
        atmpt+=1;
        switch (atmpt){
            case 1:
                arr[0]=val;break;
            case 2:
                arr[1]=val;break;
            case 3:
                arr[2]=val;break;
            case 4:
                arr[3]=val;break;

        }
        if(atmpt>4){
            return;
        }
        updateStars();
    }

    void updateStars(){
        String s = "";
        for(int i=0;i<atmpt;i++){
            s+=""+arr[i];
        }
        pwdField.setText(s);
    }
    String getPwd(){
        String s = "";
        for(int i=0;i<4;i++){
            s+=""+arr[i];
        }
        return s;
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i){
            case R.id.button_0:
                checkLogin(0);

                break;
            case R.id.button_1:
                checkLogin(1);
                break;
            case R.id.button_2:
                checkLogin(2);
                break;
            case R.id.button_3:
                checkLogin(3);
                break;
            case R.id.button_4:
                checkLogin(4);
                break;
            case R.id.button_5:
                checkLogin(5);
                break;
            case R.id.button_6:
                checkLogin(6);
                break;
            case R.id.button_7:
                checkLogin(7);
                break;
            case R.id.button_8:
                checkLogin(8);
                break;
            case R.id.button_9:
                checkLogin(9);
                break;
            case R.id.button_delete:
                if(atmpt>0){atmpt-=1;}
                updateStars();
                break;
            case R.id.button_enter:
                if(atmpt>=4){
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("cpwd",getPwd());
                    editor.commit();
                    dismiss();
                }else{
                    showError();
                }
                break;
        }
    }
    private Toast mToastToShow;
    public void showToast(int duration,final Activity activity ) {
        // Set the toast and duration
        int toastDurationInMilliSeconds = duration;
        mToastToShow = Toast.makeText(this.getActivity(), "Cleaner id is at least 4 digit long", Toast.LENGTH_LONG);

        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.show();
            }
            public void onFinish() {
                mToastToShow.cancel();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        atmpt = 0;
                        pwdValid = 0;
                        pwdField.setText("");
                    }
                });
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();
    }


    void showError(){
        int duration = Toast.LENGTH_SHORT;
        showToast(duration,getActivity());

    }

    private Timer _timer;
    private Button button1, button2, button3, button4,
            button5, button6, button7, button8,
            button9, button0, buttonDelete, buttonEnter;
    TextView pwdField;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.logincleaner, null);

        pwdField = view.findViewById(R.id.pwdField);
        updateStars();
        button1 = (Button) view.findViewById(R.id.button_1);
        button1.setOnClickListener(this);
        button2 = (Button) view.findViewById(R.id.button_2);
        button2.setOnClickListener(this);
        button3 = (Button) view.findViewById(R.id.button_3);
        button3.setOnClickListener(this);
        button4 = (Button) view.findViewById(R.id.button_4);
        button4.setOnClickListener(this);
        button5 = (Button) view.findViewById(R.id.button_5);
        button5.setOnClickListener(this);
        button6 = (Button) view.findViewById(R.id.button_6);
        button6.setOnClickListener(this);
        button7 = (Button) view.findViewById(R.id.button_7);
        button7.setOnClickListener(this);
        button8 = (Button) view.findViewById(R.id.button_8);
        button8.setOnClickListener(this);
        button9 = (Button) view.findViewById(R.id.button_9);
        button9.setOnClickListener(this);
        button0 = (Button) view.findViewById(R.id.button_0);
        button0.setOnClickListener(this);
        buttonDelete = (Button) view.findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(this);
        buttonEnter = (Button) view.findViewById(R.id.button_enter);
        buttonEnter.setOnClickListener(this);
        builder.setView(view)
                // Add action buttons
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        _timer = new Timer();

        _timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // use runOnUiThread(Runnable action)

                        _timer.cancel();
                        dismiss();

            }
        }, 30*1000,1000);
        return builder.create();

    }
}
