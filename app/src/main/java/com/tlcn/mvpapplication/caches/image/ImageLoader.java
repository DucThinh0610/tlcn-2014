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
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.tlcn.mvpapplication.R;

import java.io.File;

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

    public static void loadWithProgressBar(Context context, String url, final ImageView imageView, final ProgressBar progressBar) {

        if (url == null || url.isEmpty()) {
            imageView.setImageResource(R.drawable.ic_error);
            return;
        }

        Glide.with(context)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .centerCrop()
                .error(ContextCompat.getDrawable(context, R.drawable.ic_error))
                .crossFade()
                .into(imageView);
    }

    public static void loadImageFromPath(final Context context, String path, final ImageView imageView, final int corner) {
        Glide.with(context)
                .load(new File(path))
                .asBitmap()
                .centerCrop()
                .error(ContextCompat.getDrawable(context, R.color.blue))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(),
                                resource);
                        drawable.setCornerRadius(corner);
                        imageView.setImageDrawable(drawable);
                    }
                });
    }
}
