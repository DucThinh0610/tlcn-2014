package com.tlcn.mvpapplication.utils;

import android.app.Activity;
import android.os.Build;

/**
 * Created by tskil on 9/24/2017.
 */

public class SystemUtils {
    public static void exitApplication(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.finishAndRemoveTask();
            System.exit(0);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.finishAffinity();
            System.exit(0);
        } else {
            activity.finish();
            System.exit(0);
        }
    }
}
