package com.tlcn.mvpapplication.mvp.details.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlcn.mvpapplication.BuildConfig;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.caches.image.ImageLoader;
import com.tlcn.mvpapplication.model.Image;
import com.tlcn.mvpapplication.utils.DateUtils;

import java.util.List;

/**
 * Created by tskil on 9/22/2017.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageHolder> {
    private Context mContext;
    private List<Image> list;

    public ImagesAdapter(Context mContext, List<Image> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_image, parent, false);
        return new ImageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        ImageLoader.load(mContext, BuildConfig.SERVER_URL_API+list.get(position).getUrl(),holder.imvContent);
        holder.tvCreatedAt.setText(DateUtils.formatDateToString(list.get(position).getCreated_at()));
        holder.tvCreatedBy.setText(list.get(position).getUser_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        ImageView imvContent;
        TextView tvCreatedBy;
        TextView tvCreatedAt;
        public ImageHolder(View v) {
            super(v);
            imvContent = (ImageView) v.findViewById(R.id.imv_content);
            tvCreatedBy = (TextView) v.findViewById(R.id.tv_created_by);
            tvCreatedAt = (TextView) v.findViewById(R.id.tv_created_at);
        }
    }
}


