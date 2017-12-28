package com.tlcn.mvpapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.utils.DateUtils;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DatePickerDialog extends Dialog implements View.OnClickListener {
    @Bind(R.id.date_from)
    DatePicker dateFrom;
    @Bind(R.id.date_to)
    DatePicker dateTo;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    @Bind(R.id.btn_ok)
    Button btnOk;


    private Date dateStart;
    private Date dateEnd;
    private OnClickListener mCallback;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_ok:
                Date endDay = DateUtils.getEndDay(dateTo.getDate());
                mCallback.onClickOk(dateFrom.getDate(), endDay);
                dismiss();
                break;
        }
    }

    public interface OnClickListener {
        void onClickOk(Date dateStart, Date dateEnd);
    }

    public DatePickerDialog(@NonNull Context context, Date dateStart, Date dateEnd, OnClickListener listener) {
        super(context, R.style.dialog_fullscreen);
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.mCallback = listener;
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
        setContentView(R.layout.dialog_date_picker);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initListener() {
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    private void initData() {

        if (dateStart != null) {
            dateFrom.setDate(dateStart);
        }
        if (dateEnd != null) {
            dateTo.setDate(dateEnd);
        }
        dateFrom.setDayMonthYearPickerViewListener(new DatePicker.IDayMonthYearPickerViewListener() {
            @Override
            public void onDateChange(Date date) {
                if (date.after(dateTo.getDate())) {
                    dateTo.setDate(date);
                }
            }
        });
        dateTo.setDayMonthYearPickerViewListener(new DatePicker.IDayMonthYearPickerViewListener() {
            @Override
            public void onDateChange(Date date) {
                if (date.before(dateFrom.getDate())) {
                    dateFrom.setDate(date);
                }
            }
        });
    }
}
