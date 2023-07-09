package com.test.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

public class Tools {
    public static Activity currentActivity;

    public static void loadFromUri(final Activity self, final ImageView image, final String path){
        self.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Uri uri = Uri.parse(path);
                SvgLoader.pluck()
                        .with(self)
                        .setPlaceHolder(R.drawable.blank, R.drawable.blank)
                        .load(uri, image);
            }
        });



    }

    public static String getDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                return "Sunday";

            case Calendar.MONDAY:
                 return "Monday";

            case Calendar.TUESDAY:
                return "Tuesday";

            case Calendar.WEDNESDAY:
                return "Wednesday";

            case Calendar.THURSDAY:
                return "Thursday";

            case Calendar.FRIDAY:
                return "Friday";

            case Calendar.SATURDAY:
                return "Saturday";

        }
        return "";
    }
    private static Boolean check(String start,String end, int h, int m){
        String starts[] = start.split(":");
        String ends[] = end.split(":");

        int hs = Integer.parseInt(starts[0]);
        int ms =  Integer.parseInt(starts[1]);


        int he = Integer.parseInt(ends[0]);
        int me = Integer.parseInt(ends[1]);
        if((h>hs) && (he>h))
            return true;

        if((h==hs))
        {
            if(ms<m){return true;}else{return false;}
        }
        if((he==h))
        {
            if(me>m){return true;}else{return false;}
        }
        return false;
    }

    public static Boolean isWithin(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String start;
        String end;
        switch (day) {
            case Calendar.SUNDAY:
                 start = getSharedString("sustart");
                 end = getSharedString("suend");
                return check(start,end,hour,minute);

            case Calendar.MONDAY:
                start = getSharedString("mostart");
                end = getSharedString("moend");
              //  Log.d("Fucking monday","+"+start+" "+end);
                return check(start,end,hour,minute);

            case Calendar.TUESDAY:
                start = getSharedString("tustart");
                end = getSharedString("tuend");
                return check(start,end,hour,minute);

            case Calendar.WEDNESDAY:
                start = getSharedString("westart");
                end = getSharedString("weend");
                return check(start,end,hour,minute);

            case Calendar.THURSDAY:
                start = getSharedString("thstart");
                end = getSharedString("thend");
                return check(start,end,hour,minute);

            case Calendar.FRIDAY:
                start = getSharedString("frstart");
                end = getSharedString("frend");
                return check(start,end,hour,minute);

            case Calendar.SATURDAY:
                start = getSharedString("sastart");
                end = getSharedString("saend");
                return check(start,end,hour,minute);

        }
        return false;
    }
    public static void  setLocked(ImageView v)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
        v.setImageAlpha(128);   // 128 = 0.5
        v.refreshDrawableState();
    }
    public static int getHour(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour;

    }
    public static void writeReview(Activity activity,String type){
        if( activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else{
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1024);
            }
        }else{


            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/transmit.txt";
            File file = new File(path);
            try {
                FileWriter fw = new FileWriter(file,true);
                fw.write(type);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Writeetetet", "" + path);
        }
    }
    public static String getCleanerId(Activity activity){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return sharedPreferences.getString("cpwd","0000");
    }
    public static Bitmap loadBitmapFromAssets(Context context, String path)
    {
        InputStream stream = null;
        try
        {
            stream = context.getAssets().open(path);

            Bitmap b = BitmapFactory.decodeStream(stream);
            stream.close();
            return b;
        }
        catch (Exception ignored) {}


        return null;
    }
   static  void testPerm(){
        if( currentActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(currentActivity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else{
                currentActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1024);
            }
        }else{
            if(isTbdThere())
               ReadSettings();

        }
    }
    public static Boolean isTbdThere(){
        String downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        String tbd = downloads+"/tbd/settings.txt";
        File fp = new File(tbd);


        return fp.exists();

    }
    public static Bitmap loadBitmapFromDisk(String path)
    {
        InputStream stream = null;
        try
        {
            stream =  new FileInputStream(path);// context.getAssets().open(path);

            Bitmap b = BitmapFactory.decodeStream(stream);
            stream.close();
            return b;
        }
        catch (Exception ignored) {}


        return null;
    }
    public static void saveBmp(Activity activity, int  id,String name){
        Bitmap icon = BitmapFactory.decodeResource(activity.getResources(),id);
        String downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        String tbd = downloads+"/tbd";
        File fp = new File(tbd);
        OutputStream fOut = null;

        if(!fp.exists()){
            fp.mkdir();
        }
        File file = new File(tbd+"/"+name);
        try {
            fOut = new FileOutputStream(file);
            icon.compress(Bitmap.CompressFormat.PNG,10,fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void parseString(String str1,String str2){
        String tr = str2.replace("\""," ").trim();
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/tbd/";

        switch(str1.trim()){
            case "Option_1_background":
                updateSharedString("sm_bcg",path+tr);
                break;
            case "Option_2_background":
                updateSharedString("yl_bcg",path+tr);
                break;
            case "No_service_background":
                updateSharedString("ns_bcg",path+tr);
                break;
            case "Client_logo":
                updateSharedString("cl_logo",path+tr);
                break;
            case "Licensee_logo":
                updateSharedString("lc_logo",path+tr);
                break;
            case "No_service_image":
                updateSharedString("ns_img",path+tr);
                break;
            case "UnaBiz text":
                updateSharedString("unabiz",tr);
                break;
        }
    }
    public static String getUnabizString(){
        String s = getSharedString("unabiz");
        if(s.length()>0)
        return s;
        else
            return "© UnaBiz Pte Ltd, 2019";
    }
    public static Bitmap getNoServiceImage(){
        String path=getSharedString("ns_img");
        if(path.length()==0){
            return BitmapFactory.decodeResource(currentActivity.getResources(),
                    R.drawable.image22);
        }
        return loadBitmapFromDisk(path);
    }
    public static Bitmap getLicenseLogo(){
        String path=getSharedString("lc_logo");
        if(path.length()==0){
            return BitmapFactory.decodeResource(currentActivity.getResources(),
                    R.drawable.image8);
        }
        return loadBitmapFromDisk(path);
    }
    public static Bitmap getClientLogo(){
        String path=getSharedString("cl_logo");
        if(path.length()==0){
            return BitmapFactory.decodeResource(currentActivity.getResources(),
                    R.drawable.image7);
        }
        return loadBitmapFromDisk(path);
    }
    public static Bitmap getSmileyBackground(){
       String path=getSharedString("sm_bcg");
        if(path.length()==0){
            return BitmapFactory.decodeResource(currentActivity.getResources(),
                    R.drawable.background);
        }
        return loadBitmapFromDisk(path);
    }
    public static Bitmap getYellowBackground(){
        String path=getSharedString("yl_bcg");
        if(path.length()==0){
            return BitmapFactory.decodeResource(currentActivity.getResources(),
                    R.drawable.yellow_bckg);
        }
        return loadBitmapFromDisk(path);
    }
    public static Bitmap getNoServiceBackground(){
        String path=getSharedString("ns_bcg");
        if(path.length()==0){
            return BitmapFactory.decodeResource(currentActivity.getResources(),
                    R.drawable.dots);
        }
        return loadBitmapFromDisk(path);
    }

    public static void ReadSettings(){
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/tbd/settings.txt";
        File file = new File(path);

        String st;

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                while ((st = br.readLine()) != null){
                   String [] strs = st.split("=");


                   parseString(strs[0],strs[1]);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    public static void updateSharedString(final String key, final String value){

        currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(currentActivity);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(key,value);

                editor.commit();
            }
        });

    }
    public static String getSharedString(final String key){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(currentActivity.getBaseContext());
        return  sharedPreferences.getString(key,"");


    }


    public static Boolean getSheduleOn(Activity activity){



        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
       return sharedPreferences.getBoolean("use_shedule",true);


    }
    public static void createSettings(){
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/tbd/settings.txt";
        String what="Option_1_background = \"image1.png\"\n" +
                "Option_2_background = \"image2.png\"\n" +
                "No_service_background = \"no-service_background.png\"\n" +
                "Client_logo = \"client_logo.png\"\n" +
                "Licensee_logo = \"licensee_logo.png\"\n" +
                "No_service_image = \"no-service_image.png\"\n" +
                "UnaBiz text = \"© UnaBiz Pte Ltd, 2019\"";
        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(file,false);
            fw.write(what);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadSchedule(Activity a,String r,String value){
        TextView tv = a.findViewById(getIdFromString(r));
        tv.setText(value);
    }
    public static int getIdFromString(String s){
        switch (s){
            case "mostart":
                return R.id.mostart;

            case "moend":
                return R.id.moend;

            case "tustart":
                return  R.id.tustart;
            case "tuend":
                return R.id.tuend;
            case "westart":
                return R.id.westart;
            case "weend":
                return R.id.weend;
            case "thstart":
                return R.id.thstart;
            case "thend":
                return R.id.thend;
            case "frstart":
                return R.id.frstart;
            case "frend":
                return R.id.frend;
            case "sastart":
                return R.id.sastart;
            case "saend":
                return R.id.saend;
            case "sustart":
                return R.id.sustart;
            case "suend":
                return R.id.suend;
        }
        return 0;
    }
}
