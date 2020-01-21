package com.its.camcar.helper;


import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

public class CamcarHelper {


    public static AlertDialog.Builder progressBar(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final ProgressBar progressBar = new ProgressBar(context);


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        progressBar.setLayoutParams(lp);


        builder.setView(progressBar);


        return builder;
    }
}
