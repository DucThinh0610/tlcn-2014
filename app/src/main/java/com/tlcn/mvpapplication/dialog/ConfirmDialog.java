package com.tlcn.mvpapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tlcn.mvpapplication.R;

/**
 * Created by ducthinh on 21/09/2017.
 */

public class ConfirmDialog extends Dialog {
    private String mTitle, mMessage;
    private IClickConfirmListener mCallback;

    public ConfirmDialog(@NonNull Context context, String title, String message, IClickConfirmListener callback) {
        super(context, R.style.dialog_fullscreen);
        this.mTitle = title;
        this.mMessage = message;
        this.mCallback = callback;
    }

    public ConfirmDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected ConfirmDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                getWindow().setStatusBarColor(
                        ContextCompat.getColor(
                                getContext(),
                                R.color.color_transparent)
                );
            }
        }
        setContentView(R.layout.dialog_confirm);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvMessage = (TextView) findViewById(R.id.tv_message);
        TextView btnExit = (TextView) findViewById(R.id.btn_exit);
        TextView btnOk = (TextView) findViewById(R.id.btn_ok);
        tvMessage.setText(mMessage);
        tvTitle.setText(mTitle);
        tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mCallback.onClickOk();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mCallback.onClickCancel();
            }
        });

    }

    public interface IClickConfirmListener {

        void onClickOk();

        void onClickCancel();
    }
}
