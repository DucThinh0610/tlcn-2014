package com.tlcn.mvpapplication.mvp.details.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.Image;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.mvp.details.view.IDetailsView;
import com.tlcn.mvpapplication.utils.DateUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by tskil on 9/20/2017.
 */

public class DetailsPresenter extends BasePresenter implements IDetailsPresenter {
    News mNews;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    List<Image> images;

    @Override
    public void onCreate() {
        super.onCreate();
        mNews = new News();
        images = new ArrayList<Image>();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
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
        mReference.child(KeyUtils.NEWS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> listData = dataSnapshot.getChildren();
                for (DataSnapshot data : listData) {
                    final News item = data.getValue(News.class);
                    if (item.getId() == id) {
                        getView().hideLoading();
                        mReference.child(KeyUtils.IMAGES).child(data.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot imagesSnapshot) {
                                images.clear();
                                Iterable<DataSnapshot> imagesSnapshotChildren = imagesSnapshot.getChildren();
                                for (DataSnapshot d : imagesSnapshotChildren) {
                                    Image it = d.getValue(Image.class);
                                    images.add(it);
                                }
                                Collections.sort(images, new Comparator<Image>() {
                                    @Override
                                    public int compare(Image image, Image t1) {
                                        Date date1 = DateUtils.parseStringToDate(image.getCreated_at());
                                        Date date2 = DateUtils.parseStringToDate(t1.getCreated_at());
                                        return date2.compareTo(date1);
                                    }
                                });
                                mNews.setImages(images);
                                getView().getImagesSuccess();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                getView().hideLoading();
                                getView().onFail(databaseError.toString());
                            }
                        });
                        mNews = item;
                        getView().getNewsSuccess();
                        return;
                    }
                }
                getView().hideLoading();
                getView().onFail(App.getContext().getString(R.string.post_is_not_found));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().hideLoading();
                getView().onFail(databaseError.toString());
            }
        });
    }

    public News getNews() {
        return mNews;
    }
}
