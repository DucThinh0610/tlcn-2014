package com.tlcn.mvpapplication.mvp.savedlistnews.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.model.Locations;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.LocationViewHolder> {
    private List<Locations> mList;
    private Context mContext;

    public SavedAdapter(List<Locations> mList, Context mContext, OnItemClick mListener) {
        this.mList = mList;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocationViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_saved, parent, false));
    }

    @Override
    public void onBindViewHolder(final LocationViewHolder holder, int position) {
        final Locations item = mList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.rtbLevel.setRating((float) item.getLevel());
        if(item.getStatus()){
            holder.tvContributeNow.setText(mContext.getString(R.string.stopped));
            holder.tvContributeNow.setTextColor(mContext.getResources().getColor(R.color.color_yes));
            holder.tvContributeNow.setBackgroundResource(R.drawable.custom_background_textview_stopped);

            holder.tvCurrentState.setText(mContext.getString(R.string.traffic_jam));
        }
        else {
            holder.tvContributeNow.setText(mContext.getString(R.string.traffic_jam));
            holder.tvContributeNow.setTextColor(mContext.getResources().getColor(R.color.color_no));
            holder.tvContributeNow.setBackgroundResource(R.drawable.custom_background_textview_traffic_jam_now);

            holder.tvCurrentState.setText(mContext.getString(R.string.stopped));
        }
        holder.tvContributeNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onContributingListener(holder.itemView,holder.getAdapterPosition(),item);
            }
        });

        holder.imvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCloseClickListener(holder.itemView,holder.getAdapterPosition(),item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.rtb_level)
        AppCompatRatingBar rtbLevel;
        @Bind(R.id.tv_current_state)
        TextView tvCurrentState;
        @Bind(R.id.tv_contribute_now)
        TextView tvContributeNow;
        @Bind(R.id.imv_close)
        ImageView imvClose;
        LocationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClick mListener;

    public interface OnItemClick {
        void onContributingListener(View view,int position, Object item);

        void onCloseClickListener(View view, int position, Object item);
    }
}
