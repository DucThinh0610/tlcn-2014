package com.tlcn.mvpapplication.mvp.User.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.caches.image.ImageLoader;
import com.tlcn.mvpapplication.mvp.User.presenter.UserPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserView extends AppCompatActivity implements IUserView, View.OnClickListener {
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    UserPresenter mPresenter = new UserPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        ButterKnife.bind(this);
        initData();
        initListener();
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    private void initListener() {
        //các sự kiện click view được khai báo ở đây
        button.setOnClickListener(this);
    }

    private void initData() {
        // hiển thị các view được làm ở đây. như các nút hoặc các dữ liệu cứng, intent, adapter
    }

    @Override
    public void getUserSuccess() {
        if (mPresenter.getUserInfoGit() != null) {
            textView.setText(mPresenter.getUserInfoGit().getName());
            ImageLoader.load(UserView.this,
                    mPresenter.getUserInfoGit().getAvatarUrl(),
                    R.color.colorPrimary,
                    imageView);
        }
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if (view == button) {
            mPresenter.getUserInfo("ducthinh0610");
        }
    }
}
