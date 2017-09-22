package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
import com.tlcn.mvpapplication.caches.image.ImageLoader;
import com.tlcn.mvpapplication.dialog.DialogProgress;
import com.tlcn.mvpapplication.mvp.chooselocation.view.ChooseLocationView;
import com.tlcn.mvpapplication.mvp.main.fragment.Contribute.presenter.ContributePresenter;
import com.tlcn.mvpapplication.service.GPSTracker;
import com.tlcn.mvpapplication.utils.DateUtils;
import com.tlcn.mvpapplication.utils.DialogUtils;
import com.tlcn.mvpapplication.utils.FileUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by tskil on 8/23/2017.
 */

public class ContributeFragment extends Fragment implements IContributeView, View.OnClickListener {
    private static final int REQUEST_READ_LIBRARY = 0;

    public static ContributeFragment newInstance() {
        return new ContributeFragment();
    }

    private static final int REQUEST_PERMISSION_READ_LIBRARY = 1;
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
    @Bind(R.id.imv_image)
    ImageView imvImage;
    //Todo: Declaring
    ContributePresenter mPresenter = new ContributePresenter();
    private DialogProgress mProgressDialog;
    LatLng postLocation;
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
        rdbOther.setOnClickListener(this);
        if (rdgLocation.getCheckedRadioButtonId() == R.id.rdb_current) {
            if (gpsTracker.canGetLocation()) {
                postLocation = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            } else
                Toast.makeText(getContext(), getString(R.string.please_check_your_location), Toast.LENGTH_SHORT).show();
        }
        rdgLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rdb_current:
                        if (gpsTracker.canGetLocation()) {
                            postLocation = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                        } else
                            Toast.makeText(getContext(), getString(R.string.please_check_your_location), Toast.LENGTH_SHORT).show();
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
    public void onSuccess() {
        Toast.makeText(getContext(), getString(R.string.thanks_for_your_contribution), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                ContributionRequest contribution = new ContributionRequest();
                contribution.setDevice_id(Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));
                String user_id = "";
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                }
                contribution.setUser_id(user_id);
                if (postLocation.latitude != 0 || postLocation == null) {
                    contribution.setLatitude(postLocation.latitude);
                    contribution.setLongitude(postLocation.longitude);
                    contribution.setLevel(sbLevel.getProgress());
                    contribution.setDescription(edtDescription.getText().toString());
                    contribution.setCreated(DateUtils.getCurrentDate());
                    mPresenter.sendContribution(contribution);
                }
                break;
            case R.id.rdb_other:
                Intent intent = new Intent(getContext(), ChooseLocationView.class);
                intent.putExtra(KeyUtils.INTENT_KEY_TITLE, getString(R.string.contribution));
                startActivityForResult(intent, 101);
                break;
            case R.id.imv_take_photo:
                break;
            case R.id.imv_video:
                break;
            case R.id.imv_gallery:
                String s[] = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
                if (checkPermissions(s)) {
                    Intent iGallery = new Intent(Intent.ACTION_GET_CONTENT);
                    iGallery.setType("image/*");
                    startActivityForResult(Intent.createChooser(iGallery, "Select a image"), REQUEST_READ_LIBRARY);
                } else {
                    requestPermissions(s, REQUEST_PERMISSION_READ_LIBRARY);
                }
                break;
        }
    }

    protected boolean checkPermissions(String[] permissions) {
        for (String s : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), s) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (requestCode == REQUEST_PERMISSION_READ_LIBRARY) {
            Intent iGallery = new Intent(Intent.ACTION_GET_CONTENT);
            iGallery.setType("image/*");
            startActivityForResult(Intent.createChooser(iGallery, "Select a image"), REQUEST_READ_LIBRARY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == 101 || resultCode == 102) {
                if (data.getExtras() != null) {
                    postLocation = new LatLng(data.getDoubleExtra(KeyUtils.INTENT_KEY_LATITUDE, 0), data.getDoubleExtra(KeyUtils.INTENT_KEY_LONGITUDE, 0));
                }
            } else {
                rdbCurrent.setChecked(true);
                if (gpsTracker.canGetLocation()) {
                    postLocation = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                } else
                    Toast.makeText(getContext(), getString(R.string.please_check_your_location), Toast.LENGTH_SHORT).show();
            }
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_READ_LIBRARY) {
                File file = FileUtils.convertUriToFile(getContext(), data.getData());
                assert file != null;
                mPresenter.setMtlPart(FileUtils.createMultipartBodyPart(getContext(),
                        FileUtils.convertUriToFile(getContext(), Uri.parse(file.getPath()))));
                ImageLoader.loadImageFromPath(getContext(), file.getPath(), imvImage, 10);
            }
        }
    }
}
