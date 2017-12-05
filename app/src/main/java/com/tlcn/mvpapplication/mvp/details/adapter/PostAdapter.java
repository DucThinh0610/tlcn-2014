package com.tlcn.mvpapplication.mvp.details.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.caches.image.ImageLoader;
import com.tlcn.mvpapplication.model.Post;
import com.tlcn.mvpapplication.utils.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> mList;
    private Context mContext;
    private StorageReference storageRef;

    public PostAdapter(List<Post> mList, Context mContext, OnItemClick mListener) {
        this.mList = mList;
        this.mContext = mContext;
        this.mListener = mListener;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {
        final Post item = mList.get(position);
        holder.tvTime.setText(DateUtils.formatFullDate(item.getCreated_at()));

        holder.tvCountDislike.setText(String.valueOf(item.getCount_dislike()));
        holder.tvCountLike.setText(String.valueOf(item.getCount_like()));
        holder.rtbLevel.setRating((float) item.getLevel());
        if (TextUtils.isEmpty(item.getUser_id())) {
            holder.tvUserName.setText(R.string.anonymous);
        } else {
            holder.tvUserName.setText(item.getUser_name());
        }
        if (!TextUtils.isEmpty(item.getDescription())) {
            holder.tvDes.setText(item.getDescription());
            holder.tvDes.setVisibility(View.VISIBLE);
        } else
            holder.tvDes.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(item.getUrl_image())) {
            StorageReference imageRef = storageRef.child("images/" + item.getUrl_image());
            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    ImageLoader.loadWithProgressBar(App.getContext(), uri.toString(), holder.imvImage, holder.prBar);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    holder.prBar.setVisibility(View.GONE);
                    holder.imvImage.setImageResource(R.drawable.ic_error);
                }
            });

        } else {
            holder.imvImage.setVisibility(View.GONE);
            holder.prBar.setVisibility(View.GONE);
        }
        holder.rlDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickDislike(item.getId());
            }
        });
        holder.rlLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickLike(item.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_user)
        TextView tvUserName;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.imv_image)
        ImageView imvImage;
        @Bind(R.id.tv_description)
        TextView tvDes;
        @Bind(R.id.rtb_level)
        RatingBar rtbLevel;
        @Bind(R.id.rl_dislike)
        RelativeLayout rlDislike;
        @Bind(R.id.tv_num_dislike)
        TextView tvCountDislike;
        @Bind(R.id.rl_like)
        RelativeLayout rlLike;
        @Bind(R.id.tv_num_like)
        TextView tvCountLike;
        @Bind(R.id.pr_bar)
        ProgressBar prBar;

        public PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClick mListener;

    public interface OnItemClick {

        void onClickDislike(String id);

        void onClickLike(String id);
    }
}