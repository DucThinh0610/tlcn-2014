package com.tlcn.mvpapplication.mvp.main.fragment.Favourite.presenter;

import com.google.android.gms.maps.model.LatLng;
import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.FavouriteLocationsRequest;
import com.tlcn.mvpapplication.api.response.LocationsResponse;
import com.tlcn.mvpapplication.api.response.ShareResponse;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.caches.storage.LocationStorage;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.mvp.main.fragment.Favourite.view.IFavouriteView;

import java.util.ArrayList;

/**
 * Created by tskil on 9/12/2017.
 */

public class FavouritePresenter extends BasePresenter implements IFavouritePresenter {
    //    private FirebaseDatabase mDatabase;
//    private DatabaseReference mReference;
    private LocationStorage mLocationStorage;

    public void attachView(IFavouriteView view) {
        super.attachView(view);
    }

    public IFavouriteView getView() {
        return (IFavouriteView) getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationStorage = App.getLocationStorage();
//        mDatabase = FirebaseDatabase.getInstance();
//        mReference = mDatabase.getReference().child(KeyUtils.LOCATIONS);
    }

    @Override
    public void getListNews() {
        getView().showLoading();
        double distanceToLoad = mLocationStorage.getDistanceFavourite() * 20;
        if (distanceToLoad <= 0) {
            distanceToLoad = 1;
        }
        FavouriteLocationsRequest request = new FavouriteLocationsRequest();
        request.setDistance(distanceToLoad);
        if (mLocationStorage.getHouseLocation() != null && mLocationStorage.getOtherLocation() != null && mLocationStorage.getWorkLocation() != null) {
            request.setLatLng1(mLocationStorage.getHouseLocation());
            request.setLatLng2(mLocationStorage.getWorkLocation());
            request.setLatLng3(mLocationStorage.getOtherLocation());
        } else {
            if (mLocationStorage.getHouseLocation() != null && mLocationStorage.getWorkLocation() != null) {
                request.setLatLng1(mLocationStorage.getHouseLocation());
                request.setLatLng2(mLocationStorage.getWorkLocation());
            } else if (mLocationStorage.getHouseLocation() != null && mLocationStorage.getOtherLocation() != null) {
                request.setLatLng1(mLocationStorage.getHouseLocation());
                request.setLatLng2(mLocationStorage.getOtherLocation());
            } else if (mLocationStorage.getWorkLocation() != null && mLocationStorage.getOtherLocation() != null) {
                request.setLatLng1(mLocationStorage.getWorkLocation());
                request.setLatLng2(mLocationStorage.getOtherLocation());
            } else if (mLocationStorage.getHouseLocation() != null) {
                request.setLatLng1(mLocationStorage.getHouseLocation());
            } else if (mLocationStorage.getWorkLocation() != null) {
                request.setLatLng1(mLocationStorage.getWorkLocation());
            } else if (mLocationStorage.getOtherLocation() != null) {
                request.setLatLng1(mLocationStorage.getOtherLocation());
            } else {
                request = null;
            }
        }
        if (request != null) {
            getManager().getFavouriteLocations(request, new ApiCallback<LocationsResponse>() {
                @Override
                public void success(LocationsResponse res) {
                    if (!isViewAttached())
                        return;
                    getView().getListLocationSuccess(res.getData());
                    getView().hideLoading();
                }

                @Override
                public void failure(RestError error) {
                    if (!isViewAttached())
                        return;
                    getView().onFail(error.message);
                    getView().hideLoading();
                }
            });
        } else {
            getView().getListLocationSuccess(new ArrayList<Locations>());
            getView().hideLoading();
        }
    }

    @Override
    public void getShareLink(String location_id) {
        getView().showLoading();
        getManager().getShareLink(location_id, new ApiCallback<ShareResponse>() {
            @Override
            public void success(ShareResponse res) {
                if (!isViewAttached())
                    return;
                getView().hideLoading();
                getView().getShareLinkSuccess(res.getShareLink());
            }

            @Override
            public void failure(RestError error) {
                if (!isViewAttached())
                    return;
                getView().hideLoading();
                getView().onFail(error.message);
            }
        });
    }

//    @Override
//    public void onChangeStopped(String id) {
//        getView().showLoading();
//        ActionRequest request = new ActionRequest(id, DateUtils.getCurrentDate());
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            request.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        }
//        getManager().actionStop(request, new ApiCallback<BaseResponse>() {
//            @Override
//            public void success(BaseResponse res) {
//                getView().hideLoading();
//                getView().notifyChangeStopped();
//            }
//
//            @Override
//            public void failure(RestError error) {
//                getView().hideLoading();
//                getView().onFail(error.message);
//            }
//        });
//    }

    @Override
    public void setFavouriteDistance(int progress) {
        mLocationStorage.createDistanceFavourite(progress);
        getView().changeDistanceFavouriteSuccess();
    }

    @Override
    public void setHouseLocation(LatLng location) {
        mLocationStorage.createHouseLocation(location);
        getView().changeLocationSuccess();
    }

    @Override
    public void setWorkLocation(LatLng location) {
        mLocationStorage.createWorkLocation(location);
        getView().changeLocationSuccess();
    }

    @Override
    public void setOtherLocation(LatLng location) {
        mLocationStorage.createOtherLocation(location);
        getView().changeLocationSuccess();
    }

    @Override
    public void removeHouseLocation() {
        mLocationStorage.removeHouseLocation();
        getView().changeLocationSuccess();
    }

    @Override
    public void removeWorkLocation() {
        mLocationStorage.removeWorkLocation();
        getView().changeLocationSuccess();
    }

    @Override
    public void removeOtherLocation() {
        mLocationStorage.removeOtherLocation();
        getView().changeLocationSuccess();
    }
}
