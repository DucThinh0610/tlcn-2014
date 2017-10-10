package com.tlcn.mvpapplication.mvp.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.model.direction.Step;

import java.util.List;


public class DirectionAdapter extends RecyclerView.Adapter<DirectionAdapter.DirectionHolder> {
    private List<Step> mList;
    private Context mContext;
    private OnClickItemListener mListener;

    public DirectionAdapter(List<Step> mList, Context mContext, OnClickItemListener mListener) {
        this.mList = mList;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public DirectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DirectionHolder(LayoutInflater.from(mContext).inflate(R.layout.item_direction, parent, false));
    }

    @Override
    public void onBindViewHolder(DirectionHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class DirectionHolder extends RecyclerView.ViewHolder {
        public DirectionHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnClickItemListener {

    }

}
