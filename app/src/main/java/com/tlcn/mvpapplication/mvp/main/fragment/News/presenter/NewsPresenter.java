package com.tlcn.mvpapplication.mvp.main.fragment.News.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.mvp.main.fragment.News.view.INewsView;
import com.tlcn.mvpapplication.utils.KeyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tskil on 9/14/2017.
 */

public class NewsPresenter  extends BasePresenter implements INewsPresenter {

    private List<News> list;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    public List<News> getListNewsResult(){
        return list;
    }

    public void attachView(INewsView view) {
        super.attachView(view);
    }

    public INewsView getView(){
        return (INewsView) getIView();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child(KeyUtils.NEWS);
        list = new ArrayList<>();
    }

    @Override
    public void getListNews() {
        getView().showLoading();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> listData = dataSnapshot.getChildren();
                for(DataSnapshot data : listData){
                    News item = data.getValue(News.class);
                    list.add(item);
                }
                getView().hideLoading();
                getView().getListNewsSuccess();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().hideLoading();
                getView().onFail(databaseError.getMessage());
            }
        });
    }
}
