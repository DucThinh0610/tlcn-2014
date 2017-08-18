package com.tlcn.mvpapplication.caches.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tlcn.mvpapplication.R;

import static com.tlcn.mvpapplication.utils.LogUtils.LOGI;
import static com.tlcn.mvpapplication.utils.LogUtils.makeLogTag;

public class ImageLoader {

    public static void load(Context context, String url, final ImageView imageView) {
        load(context, url, R.color.colorPrimary, imageView);
    }

    public static void load(Context context, String url, int defaultImage, final ImageView imageView) {

        if (url == null || url.isEmpty()) {
            imageView.setImageResource(defaultImage);
            return;
        }

        Glide.with(context)
                .load(url)
                .centerCrop()
                .error(ContextCompat.getDrawable(context, defaultImage))
                .crossFade()
                .into(imageView);
    }
}
