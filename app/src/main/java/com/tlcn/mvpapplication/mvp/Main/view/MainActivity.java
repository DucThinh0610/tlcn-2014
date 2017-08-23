package com.tlcn.mvpapplication.mvp.Main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.mvp.Main.HomeFragment;
import com.tlcn.mvpapplication.mvp.Main.MapFragment;
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
    private List<NavigationPage> getNavigationPage(){
        NavigationPage page1 = new NavigationPage("Trang chủ", ContextCompat.getDrawable(this, R.mipmap.ic_home), HomeFragment.newInstance());
        NavigationPage page2 = new NavigationPage("Bản đồ", ContextCompat.getDrawable(this, R.mipmap.ic_maps), MapFragment.newInstance());
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
}
