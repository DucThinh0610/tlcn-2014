package com.tlcn.mvpapplication.mvp.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.api.ApiManager;
import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.action.ActionRequest;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.base.IView;
import com.tlcn.mvpapplication.model.News;
import com.tlcn.mvpapplication.mvp.details.view.DetailsView;
import com.tlcn.mvpapplication.utils.DateUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;
import com.tlcn.mvpapplication.utils.LogUtils;
import com.tlcn.mvpapplication.utils.Utilities;

import java.util.List;

/**
 * Created by tskil on 9/12/2017.
 */

public class NewsAdapter {
//        extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
//    private Context mContext;
//    private List<News> list;
//    private ApiManager apiManager;
//    private ActionRequest actionRequest;
//    private IView iView;
//    public NewsAdapter(Context context, List<News> list,IView iView) {
//        this.mContext = context;
//        this.list = list;
//        this.apiManager = new ApiManager();
//        this.actionRequest = new ActionRequest();
//        this.iView = iView;
//    }
//
//    @Override
//    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_tintuc, parent, false);
//        return new NewsHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(final NewsHolder holder, int position) {
//        final News item = list.get(position);
//        holder.tvTitle.setText(item.getTitle());
//        holder.rtbLevel.setRating((float) item.getRating());
//        holder.tvTime.setText(DateUtils.getTimeAgo(mContext, DateUtils.parseStringToDate(item.getCreated())));
//        holder.tvDescription.setText(item.getDescription());
//
//
//        String numrating = "(" + Utilities.getAcronymNumber(item.getNum_rating()) + " " + mContext.getString(R.string.rating) + ")";
//        holder.tvNumLike.setText(Utilities.getAcronymNumber(item.getNum_like()));
//        holder.tvNumDislike.setText(Utilities.getAcronymNumber(item.getNum_dislike()));
//        holder.tvNumRating.setText(numrating);
//        holder.lnlItemTinTuc.setLongClickable(false);
//        holder.lnlItemTinTuc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Todo:Handle Event View Item Click
//                /*if (holder.lnlDescription.getVisibility() == View.GONE) {
//                    holder.lnlDescription.setVisibility(View.VISIBLE);
//                } else if (holder.lnlDescription.getVisibility() == View.VISIBLE) {
//                    holder.lnlDescription.setVisibility(View.GONE);
//                }*/
//            }
//        });
//        holder.tvStopped.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Todo:Handle Event TextView Stopped Click
//                iView.showLoading();
//                actionRequest.setType(1);
//                actionRequest.setId(item.getId());
//                apiManager.action(actionRequest, new ApiCallback<BaseResponse>() {
//                    @Override
//                    public void success(BaseResponse res) {
//                        Toast.makeText(mContext,App.getContext().getString(R.string.thanks_for_your_contribution), Toast.LENGTH_SHORT).show();
//                        iView.hideLoading();
//                    }
//
//                    @Override
//                    public void failure(RestError error) {
//                        LogUtils.LOGE("RESPONSE", error.toString());
//                        iView.hideLoading();
//                    }
//                });
//            }
//        });
//        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Todo:Handle Event Button XemChiTiet Click
//                Intent intent = new Intent(mContext, DetailsView.class);
//                intent.putExtra(KeyUtils.INTENT_KEY_ID, item.getId());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
//            }
//        });
//        holder.rlLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Todo:Handle Event Like
//                actionRequest.setType(2);
//                actionRequest.setId(item.getId());
//                holder.tvNumLike.setText(Utilities.getAcronymNumber(item.getNum_like()+1));
//                apiManager.action(actionRequest, new ApiCallback<BaseResponse>() {
//                    @Override
//                    public void success(BaseResponse res) {
//                        LogUtils.LOGE("RESPONSE", res.toString());
//                    }
//
//                    @Override
//                    public void failure(RestError error) {
//                        LogUtils.LOGE("RESPONSE", error.toString());
//                    }
//                });
//            }
//        });
//        holder.rlDislike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Todo:Handle Event Dislike
//                actionRequest.setType(3);
//                actionRequest.setId(item.getId());
//                holder.tvNumDislike.setText(Utilities.getAcronymNumber(item.getNum_dislike()+1));
//                apiManager.action(actionRequest, new ApiCallback<BaseResponse>() {
//                    @Override
//                    public void success(BaseResponse res) {
//                        LogUtils.LOGE("RESPONSE", res.toString());
//                    }
//
//                    @Override
//                    public void failure(RestError error) {
//                        LogUtils.LOGE("RESPONSE", error.toString());
//                    }
//                });
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    class NewsHolder extends RecyclerView.ViewHolder {
//        TextView tvTitle;
//        TextView tvTime;
//        TextView tvDescription;
//        TextView tvNumLike;
//        TextView tvNumDislike;
//        TextView tvNumRating;
//        TextView tvStopped;
//        Button btnDetails;
//        LinearLayout lnlDescription;
//        LinearLayout lnlItemTinTuc;
//        RelativeLayout rlLike;
//        RelativeLayout rlDislike;
//        AppCompatRatingBar rtbLevel;
//
//        public NewsHolder(View v) {
//            super(v);
//            tvTitle = (TextView) v.findViewById(R.id.tv_title);
//            tvTime = (TextView) v.findViewById(R.id.tv_time);
//            tvDescription = (TextView) v.findViewById(R.id.tv_description);
//            tvNumLike = (TextView) v.findViewById(R.id.tv_num_like);
//            tvNumDislike = (TextView) v.findViewById(R.id.tv_num_dislike);
//            tvNumRating = (TextView) v.findViewById(R.id.tv_num_rating);
//            tvStopped = (TextView) v.findViewById(R.id.tv_stopped);
//            btnDetails = (Button) v.findViewById(R.id.btn_details);
//            lnlDescription = (LinearLayout) v.findViewById(R.id.lnl_description);
//            lnlItemTinTuc = (LinearLayout) v.findViewById(R.id.lnl_item_tintuc);
//            rtbLevel = (AppCompatRatingBar) v.findViewById(R.id.rtb_level);
//            rlLike = (RelativeLayout) v.findViewById(R.id.rl_like);
//            rlDislike = (RelativeLayout) v.findViewById(R.id.rl_dislike);
//        }
//    }

}
