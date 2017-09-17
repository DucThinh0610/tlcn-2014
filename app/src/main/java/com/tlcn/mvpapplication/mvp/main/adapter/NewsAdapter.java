package com.tlcn.mvpapplication.mvp.main.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.utils.Utilities;

import java.util.List;

/**
 * Created by tskil on 9/12/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private Context mContext;
    private List<News> list;

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_tintuc, parent, false);
        return new NewsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NewsHolder holder, int position) {
        final News item = list.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.rtbLevel.setRating(item.getRating());
        holder.tvTime.setText(Utilities.getTimeAgo(mContext, item.getCreated()));
        holder.tvDescription.setText(item.getDescription());
        holder.tvNumLike.setText(item.getNumLike() + "");
        holder.tvNumDislike.setText(item.getNumDislike() + "");
        holder.lnlItemTinTuc.setLongClickable(false);
        holder.lnlItemTinTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo:Handle Event View Item Click
                if (holder.lnlDescription.getVisibility() == View.GONE) {
                    holder.lnlDescription.setVisibility(View.VISIBLE);
                } else if (holder.lnlDescription.getVisibility() == View.VISIBLE) {
                    holder.lnlDescription.setVisibility(View.GONE);
                }
            }
        });
        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo:Handle Event Button XemChiTiet Click

            }
        });
        holder.lnlLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo:Handle Event Like

            }
        });
        holder.lnlDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo:Handle Event Dislike

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTime;
        TextView tvDescription;
        TextView tvNumLike;
        TextView tvNumDislike;
        Button btnDetails;
        LinearLayout lnlDescription;
        LinearLayout lnlItemTinTuc;
        LinearLayout lnlLike;
        LinearLayout lnlDislike;
        AppCompatRatingBar rtbLevel;

        public NewsHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            tvTime = (TextView) v.findViewById(R.id.tv_time);
            tvDescription = (TextView) v.findViewById(R.id.tv_description);
            tvNumLike = (TextView) v.findViewById(R.id.tv_num_like);
            tvNumDislike = (TextView) v.findViewById(R.id.tv_num_dislike);
            btnDetails = (Button) v.findViewById(R.id.btn_details);
            lnlDescription = (LinearLayout) v.findViewById(R.id.lnl_description);
            lnlItemTinTuc = (LinearLayout) v.findViewById(R.id.lnl_item_tintuc);
            rtbLevel = (AppCompatRatingBar) v.findViewById(R.id.rtb_level);
            lnlLike = (LinearLayout) v.findViewById(R.id.lnl_like);
            lnlDislike = (LinearLayout) v.findViewById(R.id.lnl_dislike);
        }
    }

    public NewsAdapter(Context context, List<News> list) {
        this.mContext = context;
        this.list = list;
    }
}
