package com.tlcn.mvpapplication.mvp.details.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.mvp.details.view.IDetailsView;
import com.tlcn.mvpapplication.utils.KeyUtils;

/**
 * Created by tskil on 9/20/2017.
 */

public class DetailsPresenter extends BasePresenter implements IDetailsPresenter {
    News mNews;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    @Override
    public void onCreate() {
        super.onCreate();
        mNews = new News();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child(KeyUtils.NEWS);
    }
    public void attachView(IDetailsView view) {
        super.attachView(view);
    }

    public IDetailsView getView() {
        return (IDetailsView) super.getIView();
    }

    @Override
    public void getDetailedNews(final long id) {
        getView().showLoading();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> listData = dataSnapshot.getChildren();
                for (DataSnapshot data : listData) {
                    News item = data.getValue(News.class);
                    if(item.getId() == id){
                        getView().hideLoading();
                        getView().getNewsSuccess(item);
                        return;
                    }
                }
                getView().hideLoading();
                getView().onFail("Không tìm thấy bài viết");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().hideLoading();
                getView().onFail(databaseError.toString());
            }
        });
    }
}
