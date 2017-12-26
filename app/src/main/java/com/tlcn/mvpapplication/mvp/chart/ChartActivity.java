package com.tlcn.mvpapplication.mvp.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.dialog.DialogProgress;
import com.tlcn.mvpapplication.mvp.chart.adapter.ChartAdapter;
import com.tlcn.mvpapplication.mvp.chart.dto.PieChart;
import com.tlcn.mvpapplication.utils.DialogUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChartActivity extends AppCompatActivity implements ChartContact.ChartView, ChartAdapter.OnItemClickListener {
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.btn_date_select)
    Button btnDate;
    @Bind(R.id.rcv_chart)
    RecyclerView rcvChart;


    private DialogProgress mProgressDialog;

    ChartPresenter mPresenter = new ChartPresenter();

    ChartAdapter mChartAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);

        mPresenter.attachView(this);
        mPresenter.onCreate();
        initData();
        initListener();
    }

    private void initData() {
        String idLocation = getIntent().getStringExtra(KeyUtils.KEY_INTENT_ID_LOCATION);
        if (idLocation == null || TextUtils.isEmpty(idLocation)) {
            finish();
        }
        mPresenter.getInfoChart(idLocation);

        mChartAdapter = new ChartAdapter(mPresenter.getList(), this, this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        rcvChart.setLayoutManager(linearLayout);
        rcvChart.setAdapter(mChartAdapter);
    }

    private void initListener() {

    }

    @Override
    public void showLoading() {
        showDialogLoading();
    }

    @Override
    public void hideLoading() {
        dismissDialogLoading();
    }


    @Override
    public void onFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyChart() {
        mChartAdapter.notifyDataSetChanged();
    }

    protected void showDialogLoading() {
        dismissDialogLoading();
        mProgressDialog = DialogUtils.showProgressDialog(this);
    }

    protected void dismissDialogLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void OnClickViewByHour() {
        ((PieChart) mPresenter.getChartDtoList().get(1)).setType(0);
        mChartAdapter.notifyItemChanged(1);
    }

    @Override
    public void OnClickViewByDayOfWeek() {
        ((PieChart) mPresenter.getChartDtoList().get(1)).setType(1);
        mChartAdapter.notifyItemChanged(1);
    }
}
