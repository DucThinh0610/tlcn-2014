package com.tlcn.mvpapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.caches.image.ImageLoader;
import com.tlcn.mvpapplication.model.Locations;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotifyDialog extends Dialog {
    private CircleImageView imvHeader;
    private TextView tvHeader, tvDistance;
    private AppCompatRatingBar ratingBar;
    private ImageView imvNew;
    private Button btnLeft, btnRight;
    private ProgressBar prBar;

    private NotifyDialogListener mCallback;
    private CountDownTimer mHandler;

    private Locations mLocation;

    private long timeCounter;

    public NotifyDialog(@NonNull Context context, Locations locations, NotifyDialogListener listener, long timeCounter) {
        super(context, R.style.dialog_fullscreen);
        this.mLocation = locations;
        mCallback = listener;
        this.timeCounter = timeCounter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.color_transparent));
            }
        }
        setContentView(R.layout.dialog_notify);

        imvHeader = (CircleImageView) findViewById(R.id.imv_notify);
        tvHeader = (TextView) findViewById(R.id.tv_name_location);
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        ratingBar = (AppCompatRatingBar) findViewById(R.id.rating_bar);
        imvNew = (ImageView) findViewById(R.id.imv_new);
        btnLeft = (Button) findViewById(R.id.btn_left);
        btnRight = (Button) findViewById(R.id.btn_right);
        prBar = (ProgressBar) findViewById(R.id.pr_bar);
        initData();
        initListener();
    }

    private void initListener() {
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.OnButtonRightClick();
                dismiss();
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initData() {
        tvHeader.setText(mLocation.getTitle());
        tvDistance.setText("Khoảng cách: ");
        ratingBar.setRating((float) mLocation.getCurrent_level());
        ImageLoader.loadImageFirebaseStorage(imvNew, prBar, mLocation.getLatest_image_url());
        btnRight.setText("Chi Tiết");
        btnRight.setTextColor(getContext().getResources().getColor(R.color.blue));
        btnLeft.setText("Đóng (10)");
    }

    public interface NotifyDialogListener {

        void OnButtonRightClick();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.cancel();
    }

    @Override
    public void show() {
        super.show();
        mHandler = new CountDownTimer(timeCounter * 1000, 1000) {

            @Override
            public void onTick(long l) {
                btnLeft.setText("Đóng (" + l / 1000 + ")");
            }

            @Override
            public void onFinish() {
                dismiss();
            }
        };
        mHandler.start();
    }
}
