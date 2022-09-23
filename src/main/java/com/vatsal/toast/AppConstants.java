package com.vatsal.toast;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;


public class AppConstants extends Service {
    String TAG = "AppConstants";
    AlertDialog alertDialog;

    Boolean isInBackground;

    public static final String NoInternet = "You are not connected to internet";

    private static AppConstants instance = new AppConstants();
    static Context context;
    ConnectivityManager connectivityManager;
    boolean connected = false;

    public static AppConstants getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;

        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
        }
        return connected;
    }

    public void displayToast(String msg) {
        try {
            lbl_toast.setText(msg);
            toast.show();
        } catch (Exception e) {

        }
    }

    public void displayToastError(String msg) {
        try {
            lbl_toast_error.setText(msg);
            toast_error.show();
        } catch (Exception e) {

        }
    }

    Toast toast, toast_error;
    View toast_layout, toast_layout_error;
    TextView lbl_toast, lbl_toast_error;

    public void setToast() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toast_layout = inflater.inflate(R.layout.toast_layout,
                null);
        lbl_toast = toast_layout.findViewById(R.id.lbl_toast);
        toast = new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toast_layout);
        setErrorToast();
    }

    public void setErrorToast() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toast_layout_error = inflater.inflate(R.layout.toast_layout_error,
                null);
        lbl_toast_error = toast_layout_error.findViewById(R.id.lbl_toast);
        toast_error = new Toast(context.getApplicationContext());
        toast_error.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        toast_error.setDuration(Toast.LENGTH_SHORT);
        toast_error.setView(toast_layout_error);
    }

    public static boolean isValidEmail(CharSequence target) {
        //return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(target).matches();
    }

    public void showDialog(Activity activity, String message) {

        alertDialog = new SpotsDialog.Builder().setContext(activity).setCancelable(false).setTheme(R.style.CustomDialog).build();
        alertDialog.setMessage(message);
        alertDialog.show();


    }

    public void hideDialog() {
        if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
    }

    public boolean IsInBackground(){
        ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(myProcess);
        isInBackground = myProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
        if(isInBackground) {
            return true;
        }else{
            return false;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public String totalStorage(){

        File file = Environment.getDataDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long iBlockSize = statFs.getBlockSizeLong();
        long iTotalBlocks = statFs.getBlockCountLong();

        String cnt_size;

        double size_kb = (iTotalBlocks * iBlockSize)/1024;
        double size_mb = size_kb / 1024;
        double size_gb = size_mb / 1024 ;

        if (size_gb > 0){
            cnt_size = String.format("%.02f", size_gb) + " GB";
        }else if(size_mb > 0){
            cnt_size = String.format("%.02f", size_mb) + " MB";
        }else{
            cnt_size = String.format("%.02f", size_kb) + " KB";
        }
//        Log.e(TAG,"size Total " + cnt_size);
        return cnt_size;
    }

    public String availableStorage(){

        File file = Environment.getDataDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long iBlockSize = statFs.getBlockSizeLong();
        long iAvailableBlocks = statFs.getAvailableBlocksLong();

        String cnt_size;

        double size_kb = (iAvailableBlocks * iBlockSize)/1024;
        double size_mb = size_kb / 1024;
        double size_gb = size_mb / 1024 ;

        if (size_gb > 0){
            cnt_size = String.format("%.02f", size_gb) + " GB";
        }else if(size_mb > 0){
            cnt_size = String.format("%.02f", size_mb) + " MB";
        }else{
            cnt_size = String.format("%.02f", size_kb) + " KB";
        }
//        Log.e(TAG,"size Total " + cnt_size);
        return cnt_size;

    }

    public String usedStorage(){

        File file = Environment.getDataDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long iBlockSize = statFs.getBlockSizeLong();
        long iAvailableBlocks = statFs.getAvailableBlocksLong();
        long iTotalBlocks = statFs.getBlockCountLong();

        String cnt_size;

        double size_kb = ((iTotalBlocks * iBlockSize) - (iAvailableBlocks * iBlockSize))/1024;
        double size_mb = size_kb / 1024;
        double size_gb = size_mb / 1024 ;

        if (size_gb > 0){
            cnt_size = String.format("%.02f", size_gb) + " GB";
        }else if(size_mb > 0){
            cnt_size = String.format("%.02f", size_mb) + " MB";
        }else{
            cnt_size = String.format("%.02f", size_kb) + " KB";
        }
//        Log.e(TAG,"size Total " + cnt_size);  
        return cnt_size;

    }

}
