package com.tlcn.mvpapplication.mvp.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlcn.mvpapplication.R;

/**
 * Created by tskil on 8/24/2017.
 */

public class FavouriteFragment extends Fragment {
    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }
}
