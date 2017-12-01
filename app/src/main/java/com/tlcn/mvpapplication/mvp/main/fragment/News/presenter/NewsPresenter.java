package com.tlcn.mvpapplication.mvp.main.fragment.News.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.action.ActionRequest;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.mvp.main.fragment.News.view.INewsView;
import com.tlcn.mvpapplication.utils.DateUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NewsPresenter extends BasePresenter implements INewsPresenter {

    private List<Locations> list;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    public List<Locations> getListNewsResult() {
        return list;
    }

    public void attachView(INewsView view) {
        super.attachView(view);
    }

    public INewsView getView() {
        return (INewsView) getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child(KeyUtils.LOCATIONS);
        list = new ArrayList<>();
    }

    @Override
    public void getListNews() {
        getView().showLoading();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                Iterable<DataSnapshot> listData = dataSnapshot.getChildren();
                for (DataSnapshot data : listData) {
                    Locations item = data.getValue(Locations.class);
                    if (item.getStatus()) {
                        list.add(item);
                    }
                }
                Collections.sort(list, new Comparator<Locations>() {
                    @Override
                    public int compare(Locations news, Locations t1) {
                        Date date1 = DateUtils.parseStringToDate(news.getLast_modify());
                        Date date2 = DateUtils.parseStringToDate(t1.getLast_modify());
                        return date2.compareTo(date1);
                    }
                });
                getView().hideLoading();
                getView().getListNewsSuccess(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().hideLoading();
                getView().onFail(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onChangeStopped(String id) {
        getView().showLoading();
        ActionRequest request = new ActionRequest(id, DateUtils.getCurrentDate());
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            request.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }
        getManager().actionStop(request, new ApiCallback<BaseResponse>() {
            @Override
            public void success(BaseResponse res) {
                getView().hideLoading();
                getView().notifyChangeStopped();
            }

            @Override
            public void failure(RestError error) {
                getView().hideLoading();
                getView().onFail(error.message);
            }
        });
    }
}
