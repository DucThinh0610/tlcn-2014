package com.tlcn.mvpapplication.mvp.main.view;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view.ContributeFragment;
import com.tlcn.mvpapplication.mvp.main.fragment.Favourite.view.FavouriteFragment;
import com.tlcn.mvpapplication.mvp.main.fragment.Home.view.HomeFragment;
import com.tlcn.mvpapplication.mvp.main.fragment.News.view.NewsFragment;
import com.tlcn.mvpapplication.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import me.riddhimanadib.library.BottomBarHolderActivity;
import me.riddhimanadib.library.NavigationPage;

/**
 * Created by tskil on 8/22/2017.
 */

public class MainActivity extends BottomBarHolderActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // pass them to super method
        Log.e("FIREBASE_TOKEN", FirebaseInstanceId.getInstance().getToken());
        super.setupBottomBarHolderActivity(getNavigationPage());
    }

    private List<NavigationPage> getNavigationPage() {
        NavigationPage page1 = new NavigationPage(getString(R.string.home), ContextCompat.getDrawable(this, R.mipmap.ic_home), HomeFragment.newInstance());
        NavigationPage page2 = new NavigationPage(getString(R.string.favourite), ContextCompat.getDrawable(this, R.mipmap.ic_favourite), FavouriteFragment.newInstance());
        NavigationPage page3 = new NavigationPage(getString(R.string.news), ContextCompat.getDrawable(this, R.mipmap.ic_news), NewsFragment.newInstance());
        NavigationPage page4 = new NavigationPage(getString(R.string.contribution), ContextCompat.getDrawable(this, R.mipmap.ic_contribute), ContributeFragment.newInstance());
        // add them in a list
        List<NavigationPage> navigationPages = new ArrayList<>();
        navigationPages.add(page1);
        navigationPages.add(page2);
        navigationPages.add(page3);
        navigationPages.add(page4);
        return navigationPages;
    }

    @Override
    public void onBackPressed() {
        DialogUtils.showExitDialog(MainActivity.this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    hideKeyboard();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager ipm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            ipm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClickedOnBottomNavigationMenu(int menuType) {
        super.onClickedOnBottomNavigationMenu(menuType);
    }
}
