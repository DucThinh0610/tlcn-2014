package com.tlcn.mvpapplication.utils;

import android.content.Context;

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
}
