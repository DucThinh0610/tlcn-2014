package com.tlcn.mvpapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.api.network.ApiServices;
import com.tlcn.mvpapplication.api.response.TextSpeechResponse;
import com.tlcn.mvpapplication.app.AppManager;
import com.tlcn.mvpapplication.caches.image.ImageLoader;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.utils.DateUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifyDialog extends Dialog {
    private CircleImageView imvHeader;
    private TextView tvHeader, tvDistance;
    private AppCompatRatingBar ratingBar;
    private ImageView imvNew, imvSpeak;
    private Button btnLeft, btnRight;
    private ProgressBar prBar;
    private ConstraintLayout parentLayout;

    private NotifyDialogListener mCallback;
    private CountDownTimer mHandler;
    private String urlMp3;
    private Locations mLocation;
    private long timeCounter;
    private MediaPlayer mediaPlayer;

    private int type;

    public NotifyDialog(@NonNull Context context, Locations locations, NotifyDialogListener listener, long timeCounter, int type) {
        super(context, R.style.dialog_fullscreen);
        this.mLocation = locations;
        mCallback = listener;
        this.timeCounter = timeCounter;
        this.type = type;
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
        parentLayout = (ConstraintLayout) findViewById(R.id.parent_layout);
        imvSpeak = (ImageView) findViewById(R.id.imv_speak);

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

        imvSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(urlMp3))
                    return;
                playMp3();
            }
        });
    }

    private void initData() {
        tvHeader.setText(mLocation.getTitle());
        tvDistance.setText(DateUtils.getHourFromStringDate(mLocation.getLast_modify()));
        ratingBar.setRating((float) mLocation.getCurrent_level());
        ImageLoader.loadWithProgressBar(getContext(), AppManager.URL_IMAGE + mLocation.getLatest_image(), imvNew, prBar);
        btnRight.setText(R.string.detail);
        btnRight.setTextColor(getContext().getResources().getColor(R.color.blue));
        btnLeft.setText(getContext().getString(R.string.close_time, 10));
        int level = KeyUtils.checkLevel(mLocation.getCurrent_level());
        switch (level) {
            case 1:
                parentLayout.setBackground(getContext().getResources().getDrawable(R.drawable.custom_bg_corners_2dp_green));
                break;
            case 2:
                parentLayout.setBackground(getContext().getResources().getDrawable(R.drawable.custom_bg_corners_2dp_yellow));
                break;
            case 3:
                parentLayout.setBackground(getContext().getResources().getDrawable(R.drawable.custom_bg_corners_2dp_red));
                break;
        }
        String text;
        switch (type) {
            case KeyUtils.TYPE_NEW:
                text = NEW_LOCATION + mLocation.getShortTitle() + "," + CURRENT_LEVEL + getLevel(level);
                getTextToSpeech(text);
                imvHeader.setImageResource(R.drawable.ic_fiber_new_black_24dp);
                break;
            case KeyUtils.TYPE_REDUCE:
                text = OLD_LOCATION + mLocation.getShortTitle() + REDUCE + "," + CURRENT_LEVEL + getLevel(level);
                getTextToSpeech(text);
                imvHeader.setImageResource(R.drawable.ic_trending_down_black_24dp);
                break;
            case KeyUtils.TYPE_INCREASE:
                text = OLD_LOCATION + mLocation.getShortTitle() + INCREASE + "," + CURRENT_LEVEL + getLevel(level);
                getTextToSpeech(text);
                imvHeader.setImageResource(R.drawable.ic_trending_up_black_24dp);
                break;
        }
    }

    public interface NotifyDialogListener {

        void OnButtonRightClick();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mHandler.cancel();
    }

    @Override
    public void show() {
        super.show();
        mHandler = new CountDownTimer(timeCounter * 1000, 1000) {

            @Override
            public void onTick(long l) {
                btnLeft.setText(getContext().getString(R.string.close_time, l / 1000));
            }

            @Override
            public void onFinish() {
                dismiss();
            }
        };
        mHandler.start();
    }


    private void getTextToSpeech(final String text) {
        AppManager.http_fpt().from(ApiServices.class).getTextToSpeech(KeyUtils.KEY_FPT_API, "female", text).enqueue(new Callback<TextSpeechResponse>() {
            @Override
            public void onResponse(Call<TextSpeechResponse> call, Response<TextSpeechResponse> response) {
                if (response.isSuccessful()) {
                    TextSpeechResponse textSpeechResponse = response.body();
                    Log.d("resposet2p", new Gson().toJson(textSpeechResponse));
                    urlMp3 = textSpeechResponse.getAsync();
                    if (urlMp3 == null || TextUtils.isEmpty(urlMp3)) {
                        imvSpeak.setVisibility(View.GONE);
                        return;
                    } else {
                        imvSpeak.setVisibility(View.VISIBLE);
                    }
                    playMp3();
                }
            }

            @Override
            public void onFailure(Call<TextSpeechResponse> call, Throwable throwable) {
                Log.e("OnFailure", throwable.toString());
                String error = null;
                if (throwable instanceof IOException) {
                    if (throwable instanceof SocketTimeoutException) {
                        error = "Request time out";
                    } else {
                        error = "No internet connection";
                    }
                }
                if (error == null) {
                    error = "Unknown error";
                }
                Log.d("error", error);
            }
        });
    }

    private static final String NEW_LOCATION = "Có địa điểm kẹt xe mới tại ";
    private static final String OLD_LOCATION = "Địa điểm kẹt xe tại ";
    private static final String INCREASE = " có xu hướng tăng ";
    private static final String REDUCE = " có xu hướng giảm ";
    private static final String CURRENT_LEVEL = " với mức độ hiện tại là";
    private static final String LOW = " thấp";
    private static final String HIGH = " cao";
    private static final String MEDIUM = " trung bình";

    private String getLevel(int level) {
        switch (level) {
            case 1:
                return LOW;
            case 2:
                return MEDIUM;
            case 3:
                return HIGH;
        }
        return LOW;
    }

    private void playMp3() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(urlMp3);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
