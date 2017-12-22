package com.tlcn.mvpapplication.mvp.main.fragment.Favourite.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.model.Locations;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteHolder> {
    private List<Locations> mList;
    private Context mContext;
    private OnClickListener mCallback;

    public FavouriteAdapter(List<Locations> mList, Context mContext, OnClickListener mCallback) {
        if (mList == null)
            mList = new ArrayList<>();
        this.mList = mList;
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    @Override
    public FavouriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavouriteHolder(LayoutInflater.from(mContext).inflate(R.layout.item_favourite, parent, false));
    }

    @Override
    public void onBindViewHolder(FavouriteHolder holder, int position) {
        Locations item = mList.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class FavouriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.rtb_level)
        AppCompatRatingBar rtb;
        @Bind(R.id.btn_details)
        Button btnDetail;
        @Bind(R.id.rl_chart)
        RelativeLayout rltChart;
        @Bind(R.id.rl_share)
        RelativeLayout rltShare;

        public FavouriteHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btnDetail.setOnClickListener(this);
            rltChart.setOnClickListener(this);
            rltShare.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_share:
                    mCallback.onClickShare(mList.get(getAdapterPosition()).getId());
                    break;
                case R.id.rl_chart:
                    mCallback.onClickChart(mList.get(getAdapterPosition()).getId());
                    break;
                case R.id.btn_details:
                    mCallback.onClickDetail(mList.get(getAdapterPosition()).getId());
                    break;
            }
        }

        void bindView(Locations locations) {
            tvTitle.setText(locations.getTitle());
            rtb.setRating((float) locations.getCurrent_level());
        }
    }

    public interface OnClickListener {

        void onClickShare(String id);

        void onClickDetail(String id);

        void onClickChart(String id);
    }
}
