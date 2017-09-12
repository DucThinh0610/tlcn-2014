package com.tlcn.mvpapplication.mvp.Main.view;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.mvp.Main.FavouriteFragment;
import com.tlcn.mvpapplication.mvp.Main.fragment.HomeFragment;
import com.tlcn.mvpapplication.mvp.Main.NewsFragment;
import com.tlcn.mvpapplication.mvp.Main.SettingFragment;

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
        super.setupBottomBarHolderActivity(getNavigationPage());
    }

    private List<NavigationPage> getNavigationPage() {
        NavigationPage page1 = new NavigationPage("Trang chủ", ContextCompat.getDrawable(this, R.mipmap.ic_home), HomeFragment.newInstance());
        NavigationPage page2 = new NavigationPage("Quan tâm", ContextCompat.getDrawable(this, R.mipmap.ic_favourite), FavouriteFragment.newInstance());
        NavigationPage page3 = new NavigationPage("Tin tức", ContextCompat.getDrawable(this, R.mipmap.ic_news), NewsFragment.newInstance());
        NavigationPage page4 = new NavigationPage("Cài đặt", ContextCompat.getDrawable(this, R.mipmap.ic_setting), SettingFragment.newInstance());
        // add them in a list
        List<NavigationPage> navigationPages = new ArrayList<>();
        navigationPages.add(page1);
        navigationPages.add(page2);
        navigationPages.add(page3);
        navigationPages.add(page4);
        return navigationPages;
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
}
