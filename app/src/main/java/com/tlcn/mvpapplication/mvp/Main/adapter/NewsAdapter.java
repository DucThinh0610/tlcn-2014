package com.tlcn.mvpapplication.mvp.Main.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.utils.Utilities;

import java.util.List;

/**
 * Created by tskil on 9/12/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>{
    Context mContext;
    List<News> list;
    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_tintuc, parent, false);
        return new NewsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        News item = list.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.rtbLevel.setRating(item.getRating());
        holder.tvTime.setText(Utilities.getTimeAgo(mContext,item.getCreated()));
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTime;
        AppCompatRatingBar rtbLevel;

        public NewsHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            tvTime = (TextView) v.findViewById(R.id.tv_time);
            rtbLevel = (AppCompatRatingBar) v.findViewById(R.id.rtb_level);
        }
    }
    public NewsAdapter(Context context,List<News> list){
        this.mContext = context;
        this.list = list;
    }
}
