package com.tlcn.mvpapplication.mvp.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.tlcn.mvpapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChartActivity extends AppCompatActivity implements ChartContact.ChartView {
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.btn_date_select)
    Button btnDate;
    @Bind(R.id.rcv_chart)
    RecyclerView rcvChart;


    ChartPresenter mPresenter = new ChartPresenter();
    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);

        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
