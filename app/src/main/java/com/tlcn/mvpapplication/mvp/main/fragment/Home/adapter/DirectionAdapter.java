package com.tlcn.mvpapplication.mvp.main.fragment.Home.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.model.Locations;
import com.tlcn.mvpapplication.model.direction.Route;
import com.tlcn.mvpapplication.model.direction.Step;
import com.tlcn.mvpapplication.utils.DateUtils;
import com.tlcn.mvpapplication.utils.KeyUtils;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DirectionAdapter extends SectioningAdapter {
    private Context mContext;
    private List<Step> mList;
    private OnClickItemListener mListener;

    public interface OnClickItemListener {

        void onClickStartDetail(String id);
    }

    public DirectionAdapter(Context mContext, Route route, OnClickItemListener mListener) {
        this.mContext = mContext;
        this.mList = route.getSteps();
        this.mListener = mListener;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerUserType) {
        return new HeaderHolder(LayoutInflater.from(mContext).inflate(R.layout.item_direction_header, parent, false));
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemUserType) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_direction, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int sectionIndex, int headerUserType) {
        HeaderHolder holder = (HeaderHolder) viewHolder;
        Step item = mList.get(sectionIndex);
        holder.createView(item);
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemUserType) {
        final Locations item = mList.get(sectionIndex).getLocations().get(itemIndex);
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.createView(item);
        holder.imvStartDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickStartDetail(item.getId());
            }
        });
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return mList.get(sectionIndex).getLocations().size();
    }

    @Override
    public int getNumberOfSections() {
        return mList.size();
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public int getSectionHeaderUserType(int sectionIndex) {
        return 1;
    }

    public class HeaderHolder extends HeaderViewHolder {
        @Bind(R.id.imv_direction)
        CircleImageView imvDirection;
        @Bind(R.id.tv_direction)
        TextView tvDirection;
        @Bind(R.id.tv_distance)
        TextView tvDistance;

        HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void createView(Step step) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvDirection.setText(Html.fromHtml(step.getDescription(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                tvDirection.setText(Html.fromHtml(step.getDescription()));
            }
            tvDistance.setText(step.getDistance().getLength());
        }
    }

    public class ItemHolder extends ItemViewHolder {
        @Bind(R.id.imv_color_level)
        ImageView imvLevel;
        @Bind(R.id.view_margin)
        View viewMargin;
        @Bind(R.id.rlt_info)
        RelativeLayout rltInfo;
        @Bind(R.id.tv_description)
        TextView tvDes;
        @Bind(R.id.rtb_level)
        AppCompatRatingBar rtbLevel;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.imv_detail)
        ImageView imvStartDetail;

        ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void createView(Locations locations) {
            tvTime.setText(String.format("Gần nhất: %s", DateUtils.formatHour(locations.getLast_modify())));
            tvDes.setText(locations.getTitle());
            rtbLevel.setRating((float) locations.getCurrent_level());
            if (locations.getCurrent_level() < KeyUtils.MIN_LEVEL) {
                imvLevel.setImageResource(R.drawable.ic_circle_green);
                viewMargin.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
                rltInfo.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_bolder_green));
            } else if (locations.getCurrent_level() >= KeyUtils.MIN_LEVEL &&
                    locations.getCurrent_level() < KeyUtils.MEDIUM_LEVEL) {
                viewMargin.setBackgroundColor(ContextCompat.getColor(mContext, R.color.yellow));
                imvLevel.setImageResource(R.drawable.ic_circle_yellow);
                rltInfo.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_bolder_yellow));
            } else {
                imvLevel.setImageResource(R.drawable.ic_circle_red);
                viewMargin.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
                rltInfo.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_bolder_red));
            }
        }
    }
}
