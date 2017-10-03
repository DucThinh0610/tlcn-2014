package com.tlcn.mvpapplication.mvp.details.presenter;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import com.tlcn.mvpapplication.model.Post;
import com.tlcn.mvpapplication.mvp.details.view.IDetailsView;
import com.tlcn.mvpapplication.utils.DateUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DetailsPresenter extends BasePresenter implements IDetailsPresenter {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Post> mListPost;
    private Locations mLocation;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser user;

    public Locations getLocation() {
        return mLocation;
    }

    public void setLocation(Object o) {
        this.mLocation = (Locations) o;
    }

    public List<Post> getListPost() {
        return mListPost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mListPost = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        user = mFirebaseAuth.getCurrentUser();
    }

    public void attachView(IDetailsView view) {
        super.attachView(view);
    }

    public IDetailsView getView() {
        return (IDetailsView) super.getIView();
    }

    @Override
    public void getListPostFromSV() {
        getView().showLoading();
        mReference.child(KeyUtils.NEWS).child(mLocation.getId()).limitToLast(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                mListPost.clear();
                for (DataSnapshot data : dataSnapshots) {
                    Post item = data.getValue(Post.class);
                    mListPost.add(item);
                }
                Collections.sort(mListPost, new Comparator<Post>() {
                    @Override
                    public int compare(Post o1, Post o2) {
                        Date date1 = DateUtils.parseStringToDate(o1.getCreated_at());
                        Date date2 = DateUtils.parseStringToDate(o2.getCreated_at());
                        return date2.compareTo(date1);
                    }
                });
                getView().getPostSuccess();
                getView().hideLoading();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().hideLoading();
                getView().onFail(databaseError.getMessage());
            }
        });
        Log.d("asd", mReference.child(KeyUtils.NEWS).child(mLocation.getId()).toString());
    }

    @Override
    public void actionDislike(String idPost) {
        getManager().action(new ActionRequest(user.getUid(), 2, idPost), new ApiCallback<BaseResponse>() {
            @Override
            public void success(BaseResponse res) {
                getView().onActionSuccess();
            }

            @Override
            public void failure(RestError error) {
                getView().onFail(error.message);
            }
        });
    }

    @Override
    public void actionLike(String idPost) {
        getManager().action(new ActionRequest(user.getUid(), 1, idPost), new ApiCallback<BaseResponse>() {
            @Override
            public void success(BaseResponse res) {
                getView().onActionSuccess();
            }

            @Override
            public void failure(RestError error) {
                getView().onFail(error.message);
            }
        });
    }
}
