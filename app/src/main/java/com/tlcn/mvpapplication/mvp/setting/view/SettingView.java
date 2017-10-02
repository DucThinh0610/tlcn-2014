package com.tlcn.mvpapplication.mvp.setting.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.tlcn.mvpapplication.R;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initData();
        initListener();
    }


    private void initListener() {
        //các sự kiện click view được khai báo ở đây
        lnlChangeLanguage.setOnClickListener(this);
        rlNotication.setOnClickListener(this);
        imvBack.setOnClickListener(this);
        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

        sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvNumDistance.setText(Utilities.getDistanceString(i*10));
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
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imv_back:
                finish();
                break;
            case R.id.rl_notification:
                break;
            case R.id.lnl_change_language:
                break;
        }
    }
}
