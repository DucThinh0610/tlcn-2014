package com.tlcn.mvpapplication.mvp.main.fragment.Favourite.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.dialog.DialogProgress;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.mvp.chooselocation.view.ChooseLocationView;
import com.tlcn.mvpapplication.mvp.main.adapter.LocationAdapter;
import com.tlcn.mvpapplication.mvp.main.fragment.Favourite.presenter.FavouritePresenter;
import com.tlcn.mvpapplication.utils.DialogUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;
import com.tlcn.mvpapplication.utils.Utilities;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tskil on 8/24/2017.
 */

public class FavouriteFragment extends Fragment implements IFavouriteView, View.OnClickListener, LocationAdapter.OnItemClick {
    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    private final int HOUSE_RESQUEST_CODE = 101;
    private final int WORK_RESQUEST_CODE = 102;
    private final int OTHER_RESQUEST_CODE = 103;

    private final int SAVE_RESULT_CODE = 101;
    //Todo: Binding
    @Bind(R.id.rcv_favourite)
    RecyclerView rcvFavourite;
    @Bind(R.id.lnl_house)
    LinearLayout lnlHouse;
    @Bind(R.id.lnl_work)
    LinearLayout lnlWork;
    @Bind(R.id.lnl_other)
    LinearLayout lnlOther;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.tv_location_house)
    TextView tvLocationHouse;
    @Bind(R.id.tv_location_work)
    TextView tvLocationWork;
    @Bind(R.id.tv_location_other)
    TextView tvLocationOther;
    @Bind(R.id.tv_distance)
    TextView tvDistance;

    //Todo: Declaring
    private DialogProgress mProgressDialog;
    FavouritePresenter mPresenter = new FavouritePresenter();
    LocationAdapter newsAdapter;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HOUSE_RESQUEST_CODE) {
            //Todo: House
            if (resultCode == SAVE_RESULT_CODE) {
                if (data.getExtras() != null) {
                    LatLng location = new LatLng(data.getDoubleExtra("latitude", 0), data.getDoubleExtra("longitude", 0));
                    mPresenter.setHouseLocation(location);
                    tvLocationHouse.setText(getString(R.string.have_set));
                }
            } else {
                mPresenter.removeHouseLocation();
                tvLocationHouse.setText(getString(R.string.add_location_house));
            }
        } else if (requestCode == WORK_RESQUEST_CODE) {
            //Todo: Work
            if (resultCode == SAVE_RESULT_CODE) {
                if (data.getExtras() != null) {
                    LatLng location = new LatLng(data.getDoubleExtra("latitude", 0), data.getDoubleExtra("longitude", 0));
                    mPresenter.setWorkLocation(location);
                    tvLocationWork.setText(getString(R.string.have_set));
                }
            } else {
                mPresenter.removeWorkLocation();
                tvLocationWork.setText(getString(R.string.add_location_work));
            }
        } else if (requestCode == OTHER_RESQUEST_CODE) {
            //Todo: Other
            if (resultCode == SAVE_RESULT_CODE) {
                if (data.getExtras() != null) {
                    LatLng location = new LatLng(data.getDoubleExtra("latitude", 0), data.getDoubleExtra("longitude", 0));
                    mPresenter.setOtherLocation(location);
                    tvLocationOther.setText(getString(R.string.have_set));
                }
            } else {
                mPresenter.removeOtherLocation();
                tvLocationOther.setText(getString(R.string.add_other_location));
                lnlOther.setVisibility(View.GONE);
                tvAdd.setVisibility(View.VISIBLE);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this, v);
        initData(v);
        initListener(v);
        mPresenter.attachView(this);
        mPresenter.onCreate();
        mPresenter.getListNews();
        return v;
    }

    private void initListener(View v) {
        //các sự kiện click view được khai báo ở đây
        lnlHouse.setOnClickListener(this);
        lnlWork.setOnClickListener(this);
        lnlOther.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvDistance.setOnClickListener(this);
    }

    private void initData(View v) {
        // hiển thị các view được làm ở đây. như các nút hoặc các dữ liệu cứng, intent, adapter
        if (App.getLocationStorage().getHouseLocation().latitude != 0) {
            tvLocationHouse.setText(getString(R.string.have_set));
        }
        if (App.getLocationStorage().getWorkLocation().latitude != 0) {
            tvLocationWork.setText(getString(R.string.have_set));
        }
        if (App.getLocationStorage().getOtherLocation().latitude != 0) {
            lnlOther.setVisibility(View.VISIBLE);
            tvAdd.setVisibility(View.GONE);
            tvLocationOther.setText(getString(R.string.have_set));
        }

        tvDistance.setText(Utilities.getDistanceString(App.getLocationStorage().getDistanceFavourite()*KeyUtils.DEFAULT_NUMBER_MULTIPLY_DISTANCE));
    }


    @Override
    public void getListLocationSuccess(List<Locations> result) {
        if (result != null) {
            newsAdapter = new LocationAdapter(result, getContext(), this);
            rcvFavourite.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvFavourite.setAdapter(newsAdapter);
        }
    }

    @Override
    public void changeLocationSuccess() {
        mPresenter.getListNews();
    }

    @Override
    public void changeDistanceFavouriteSuccess() {
        mPresenter.getListNews();
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_house:
                Intent intent = new Intent(getContext(), ChooseLocationView.class);
                intent.putExtra(KeyUtils.INTENT_KEY_TITLE, getString(R.string.house));
                if (App.getLocationStorage().getHouseLocation().latitude == 0) {
                    startActivityForResult(intent, HOUSE_RESQUEST_CODE);
                } else showDialog(intent, HOUSE_RESQUEST_CODE);
                break;
            case R.id.lnl_work:
                Intent intent2 = new Intent(getContext(), ChooseLocationView.class);
                intent2.putExtra(KeyUtils.INTENT_KEY_TITLE, getString(R.string.work));
                if (App.getLocationStorage().getWorkLocation().latitude == 0) {
                    startActivityForResult(intent2, WORK_RESQUEST_CODE);
                } else showDialog(intent2, WORK_RESQUEST_CODE);
                break;
            case R.id.lnl_other:
                Intent intent3 = new Intent(getContext(), ChooseLocationView.class);
                intent3.putExtra(KeyUtils.INTENT_KEY_TITLE, getString(R.string.other));
                if (App.getLocationStorage().getOtherLocation().latitude == 0) {
                    startActivityForResult(intent3, OTHER_RESQUEST_CODE);
                } else showDialog(intent3, OTHER_RESQUEST_CODE);
                break;
            case R.id.tv_add:
                lnlOther.setVisibility(View.VISIBLE);
                tvAdd.setVisibility(View.GONE);
                break;
            case R.id.tv_distance:
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_distance_favourite_location);
                Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
                Button btnCompleted = (Button) dialog.findViewById(R.id.btn_completed);
                final SeekBar sbDistance = (SeekBar) dialog.findViewById(R.id.sb_distance);
                final TextView tvDistanceDialog = (TextView) dialog.findViewById(R.id.tv_distance);

                tvDistanceDialog.setText(Utilities.getDistanceString(App.getLocationStorage().getDistanceFavourite()*KeyUtils.DEFAULT_NUMBER_MULTIPLY_DISTANCE));
                sbDistance.setProgress(App.getLocationStorage().getDistanceFavourite());

                sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        tvDistanceDialog.setText(Utilities.getDistanceString(i*KeyUtils.DEFAULT_NUMBER_MULTIPLY_DISTANCE));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btnCompleted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.setFavouriteDistance(sbDistance.getProgress());
                        tvDistance.setText(Utilities.getDistanceString(sbDistance.getProgress()*KeyUtils.DEFAULT_NUMBER_MULTIPLY_DISTANCE));
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
    }

    private void showDialog(final Intent intent, final int code) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_set_location);
        Button btnYes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btnNo = (Button) dialog.findViewById(R.id.btn_no);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivityForResult(intent, code);
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
    public void OnClickShare(Locations item) {

    }

    @Override
    public void OnClickDetail(Locations item) {

    }

    @Override
    public void onClickStopped(String id) {

    }
}
