package com.tlcn.mvpapplication.mvp.setting.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.mvp.setting.presenter.SettingPresenter;
import com.tlcn.mvpapplication.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by apple on 01/10/2017.
 */

public class SettingView extends AppCompatActivity implements ISettingView, View.OnClickListener {
    @Bind(R.id.lnl_change_language)
    LinearLayout lnlChangeLanguage;
    @Bind(R.id.rl_notification)
    RelativeLayout rlNotication;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.tv_num_distance)
    TextView tvNumDistance;
    @Bind(R.id.sb_distance)
    SeekBar sbDistance;
    @Bind(R.id.imv_back)
    ImageView imvBack;

    //TODO: Declaring
    SettingPresenter mPresenter = new SettingPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        mPresenter.onCreate();
        initData();
        initListener();
    }


    private void initListener() {
        //các sự kiện click view được khai báo ở đây
        lnlChangeLanguage.setOnClickListener(this);
        rlNotication.setOnClickListener(this);
        imvBack.setOnClickListener(this);
        sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvNumDistance.setText(Utilities.getDistanceString(i * 10));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initData() {
        // hiển thị các view được làm ở đây. như các nút hoặc các dữ liệu cứng, intent, adapter
        stateChange(App.getNotificationStorage().isNotificationOn());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_back:
                finish();
                break;
            case R.id.rl_notification:
                mPresenter.setNotificationState(!App.getNotificationStorage().isNotificationOn());
                break;
            case R.id.lnl_change_language:
                break;
        }
    }

    private void stateChange(boolean state) {
        if (state) {
            tvState.setText(getString(R.string.on));
            tvState.setTextColor(getResources().getColor(R.color.green));
        } else {
            tvState.setText(getString(R.string.off));
            tvState.setTextColor(getResources().getColor(R.color.red));
        }
    }

    @Override
    public void onStateChangeSuccess(boolean state) {
        String s;
        if (state)
            s = getString(R.string.on);
        else
            s = getString(R.string.off);
        Toast.makeText(this, s + " " + getString(R.string.get_notification_from_app) + " " + getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        stateChange(state);
    }
}
