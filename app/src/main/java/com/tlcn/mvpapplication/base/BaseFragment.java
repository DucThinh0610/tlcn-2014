package com.tlcn.mvpapplication.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.dialog.ConfirmDialog;
import com.tlcn.mvpapplication.dialog.DialogProgress;
import com.tlcn.mvpapplication.utils.DialogUtils;

import butterknife.ButterKnife;

abstract public class BaseFragment extends Fragment implements OnMapReadyCallback, IView {
    private GoogleMap mMap;
    private Context mContext;

    private SupportMapFragment supportMapFragment;

    private ConfirmDialog mConfirmDialog;
    private DialogProgress mProgressDialog;

    protected Context getActivityContext() {
        return getContext() == null ? mContext : getContext();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(setLayoutId(), container, false);

    }

    private void setUpMap() {
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
    }

    protected abstract int getIdMap();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpMap();
        ButterKnife.bind(this, view);
        onInitData();
        onInitListener();
    }

    protected abstract void onInitData();

    protected abstract void onInitListener();
    abstract protected int setLayoutId();

    protected void showConfirmDialog(String title, String message, final ConfirmDialog.IClickConfirmListener callback) {
        if (mConfirmDialog == null || !mConfirmDialog.isShowing()) {
            mConfirmDialog = new ConfirmDialog(getContext(), title,
                    message,
                    new ConfirmDialog.IClickConfirmListener() {
                        @Override
                        public void onClickOk() {
                            callback.onClickOk();
                        }

                        @Override
                        public void onClickCancel() {
                            callback.onClickCancel();
                        }
                    });
            mConfirmDialog.show();
        }
    }

    private void showOKDialog() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        onMapReadyCustom(mMap);
    }

    protected void onMapReadyCustom(GoogleMap mMap) {
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
}
