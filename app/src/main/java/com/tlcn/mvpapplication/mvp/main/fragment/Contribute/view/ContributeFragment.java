package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.api.request.contribution.ContributionRequest;
import com.tlcn.mvpapplication.dialog.DialogProgress;
import com.tlcn.mvpapplication.model.Contribution;
import com.tlcn.mvpapplication.model.Result;
import com.tlcn.mvpapplication.mvp.main.fragment.Contribute.presenter.ContributePresenter;
import com.tlcn.mvpapplication.service.GPSTracker;
import com.tlcn.mvpapplication.utils.DateUtils;
import com.tlcn.mvpapplication.utils.DialogUtils;
import com.tlcn.mvpapplication.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tskil on 8/23/2017.
 */

public class ContributeFragment extends Fragment implements IContributeView, View.OnClickListener {
    public static ContributeFragment newInstance() {
        return new ContributeFragment();
    }

    //Todo: Binding
    @Bind(R.id.rdg_location)
    RadioGroup rdgLocation;
    @Bind(R.id.rdb_current)
    RadioButton rdbCurrent;
    @Bind(R.id.rdb_other)
    RadioButton rdbOther;
    @Bind(R.id.sb_level)
    SeekBar sbLevel;
    @Bind(R.id.edt_description)
    EditText edtDescription;
    @Bind(R.id.imv_take_photo)
    ImageView imvTakePhoto;
    @Bind(R.id.imv_video)
    ImageView imvVideo;
    @Bind(R.id.imv_gallery)
    ImageView imvGallery;
    @Bind(R.id.btn_send)
    Button btnSend;
    //Todo: Declaring
    ContributePresenter mPresenter = new ContributePresenter();
    private DialogProgress mProgressDialog;
    LatLng currentLocation;
    GPSTracker gpsTracker;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contribute, container, false);
        ButterKnife.bind(this, v);
        gpsTracker = new GPSTracker(getContext());
        initData(v);
        initListener(v);
        mPresenter.attachView(this);
        mPresenter.onCreate();
        return v;
    }

    private void initListener(View v) {
        //các sự kiện click view được khai báo ở đây
        btnSend.setOnClickListener(this);
        imvTakePhoto.setOnClickListener(this);
        imvGallery.setOnClickListener(this);
        imvVideo.setOnClickListener(this);
        if (rdgLocation.getCheckedRadioButtonId() == R.id.rdb_current)
            currentLocation = new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude());

        rdgLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rdb_current:
                        if(gpsTracker.canGetLocation()){
                            currentLocation = new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude());
                        }
                        else Toast.makeText(getContext(), "Vui lòng kiểm tra lại chức năng vị trí", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rdb_other:
                        break;
                }
            }
        });

    }

    private void initData(View v) {
        // hiển thị các view được làm ở đây. như các nút hoặc các dữ liệu cứng, intent, adapter

    }

    @Override
    public void showLoading() {
        showDialogLoading();
    }

    @Override
    public void hideLoading() {
        dismissDialogLoading();
    }

    protected void showDialogLoading() {
        dismissDialogLoading();
        mProgressDialog = DialogUtils.showProgressDialog(getContext());
    }

    protected void dismissDialogLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void onFail(String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send:
                ContributionRequest contribution = new ContributionRequest();
                contribution.setDevice_id(Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));
                String user_id = "";
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                }
                contribution.setUser_id(user_id);
                contribution.setLatitude(currentLocation.latitude);
                contribution.setLongitude(currentLocation.longitude);
                contribution.setLevel(sbLevel.getProgress());
                contribution.setDescription(edtDescription.getText().toString());
                contribution.setCreated(DateUtils.getCurrentDate());

                mPresenter.sendContribution(contribution);
                break;
            case R.id.imv_take_photo:
                break;
            case R.id.imv_video:
                break;
            case R.id.imv_gallery:
                break;
        }
    }
}
