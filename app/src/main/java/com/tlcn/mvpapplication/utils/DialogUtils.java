package com.tlcn.mvpapplication.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.dialog.DialogProgress;

/**
 * Created by ducthinh on 13/09/2017.
 */

public class DialogUtils {
    public static DialogProgress showProgressDialog(Context context) {
        DialogProgress progressDialog = new DialogProgress(context);
        progressDialog.show();
        return progressDialog;
    }

    public static void showSettingDialog(final Activity activity, final int resquestCode){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_open_location);
        dialog.setCanceledOnTouchOutside(true);
        Button btnYes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btnNo = (Button) dialog.findViewById(R.id.btn_no);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
                SystemUtils.exitApplication(activity);
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivityForResult(intent, resquestCode);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SystemUtils.exitApplication(activity);
            }
        });
        dialog.show();
    }

    public static void showExitDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_wanna_exit);
        dialog.setCanceledOnTouchOutside(true);
        Button btnYes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btnNo = (Button) dialog.findViewById(R.id.btn_no);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SystemUtils.exitApplication(activity);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
