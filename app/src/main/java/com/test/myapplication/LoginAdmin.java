package com.test.myapplication;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class LoginAdmin extends DialogFragment implements View.OnClickListener {


    int atmpt = 0;
    int pwdValid = 0;
    private Timer _timer;
    void checkLogin(int val){
        atmpt+=1;
        switch (atmpt){
            case 1:
                if(val==3){pwdValid+=1;}break;
            case 2:
                if(val==5){pwdValid+=1;}break;
            case 3:
                if(val==8){pwdValid+=1;}break;
            case 4:
                if(val==1){pwdValid+=1;}break;
            case 5:
                if(val==4){pwdValid+=1;}break;
            case 6:
                if(val==9){pwdValid+=1;}break;
        }
        updateStars();
    }
    void updateStars(){
        String s = "";
        for(int i=0;i<atmpt;i++){
            s+="*";
        }
        pwdField.setText(s);
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
                if(pwdValid>=6){
                    listener.onLoginPositiveClick(LoginAdmin.this,true);
                }else{
                    showError();
                }
                break;
        }
    }

    public interface PwdEnteredDialogListener {
        public void onLoginPositiveClick(DialogFragment dialog,Boolean logged);
    }
    PwdEnteredDialogListener listener;
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (LoginAdmin.PwdEnteredDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement PwdEnteredDialogListener");
        }
    }

    private Button button1, button2, button3, button4,
            button5, button6, button7, button8,
            button9, button0, buttonDelete, buttonEnter;
    public void countDown( final int time, final Activity activity) {
        // Set the toast and duration
        int toastDurationInMilliSeconds = 5000;


        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
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


        toastCountDown.start();
    }

    TextView pwdField;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.loginadmin, null);

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
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(pwdValid>=6){
                            listener.onLoginPositiveClick(LoginAdmin.this,true);
                        }else{
                            showError();
                        }
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
    void showError(){
        int duration = Toast.LENGTH_SHORT;
        countDown(duration,getActivity());
        Toast toast = Toast.makeText(getContext(), "             Wrong password           ", duration);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }

}