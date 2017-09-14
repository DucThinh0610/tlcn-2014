package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlcn.mvpapplication.R;

/**
 * Created by tskil on 8/23/2017.
 */

public class ContributeFragment extends Fragment {
    public static ContributeFragment newInstance() {
        return new ContributeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
}
