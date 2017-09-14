package com.tlcn.mvpapplication.mvp.user.presenter;

import com.tlcn.mvpapplication.api.network.ApiServices;
import com.tlcn.mvpapplication.app.AppManager;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.model.UserInfoGit;
import com.tlcn.mvpapplication.mvp.user.view.IUserView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPresenter extends BasePresenter implements IUserPresenter {
    private UserInfoGit userInfoGit;

    public UserInfoGit getUserInfoGit() {
        return userInfoGit;
    }

    public void setUserInfoGit(UserInfoGit userInfoGit) {
        this.userInfoGit = userInfoGit;
    }

    //attach view de goi sang view

    public void attachView(IUserView view) {
        super.attachView(view);
    }

    public IUserView getView() {
        return (IUserView) getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //khoi tao cac object o day: list<abc>=new arraylist();
        userInfoGit=new UserInfoGit();
    }

    @Override
    public void getUserInfo(String idUser) {
        //nếu có sử dụng loading thì khai báo o day
        getView().showLoading();
        AppManager.http_api().from(ApiServices.class).getUserInfo(idUser).enqueue(new Callback<UserInfoGit>() {
            @Override
            public void onResponse(Call<UserInfoGit> call, Response<UserInfoGit> response) {
                //xử lý data ở đây, sau đó bắn những thứ cần sang view để hiển thị
                //success thi tat loading
                userInfoGit=response.body();
                getView().hideLoading();
                getView().getUserSuccess();
            }

            @Override
            public void onFailure(Call<UserInfoGit> call, Throwable t) {
                getView().hideLoading();
                getView().onFail(t.getMessage());
            }
        });
    }
}
