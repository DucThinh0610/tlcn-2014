package com.tlcn.mvpapplication.dialog;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatePicker extends FrameLayout {
    private int day;
    private int month;
    private int year;
    private int currentYear;
    private DayAdapter dayAdapter;
    private ListView lvDay, lvMonth, lvYear;
    private List<Integer> listDay = new ArrayList<>();
    private List<Integer> listMonth = new ArrayList<>();
    private List<Integer> listYear = new ArrayList<>();

    public DatePicker(Context context) {
        super(context);
        init();
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DatePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        day = DateUtils.getCurrentDayInMonth();
        month = DateUtils.getCurrentMonth();
        year = DateUtils.getCurrentYear();
        currentYear = DateUtils.getCurrentYear();
        inflate(getContext(), R.layout.date_picker, this);
    }

    public void setDate(Date date) {
        this.day = DateUtils.getDayOfDate(date);
        this.month = DateUtils.getMonthOfDate(date);
        this.year = DateUtils.getYearOfDate(date);
        // valid day month year
        validateDayMonthYear();
        // selection day month year
        lvDay.setSelection(getPositionByDay(day));
        lvMonth.setSelection(getPositionByMonth(month));
        lvYear.setSelection(getPositionByYear(year));
        // change listener
        if (listener != null) {
            listener.onDateChange(DateUtils.createDateFromDMY(day, month, year));
        }
    }

    public Date getDate() {
        return DateUtils.createDateFromDMY(day, month, year);
    }

    private int getPositionByDay(int day) {
        return day - 1 + 2/* 2 null object */ - 2/* 2 index first to make center */;
    }

    private int getPositionByMonth(int month) {
        return month + 2/* 2 null object */ - 2/* 2 index first to make center */;
    }

    private int getPositionByYear(int year) {
        if (year < currentYear - 100 || year > currentYear + 100) {
            return 2;
        }
        int yearOfZeroIndex = currentYear - 100 + 2/* 2 null object */ - 2/* 2 index first to make center */;
        return year - yearOfZeroIndex;
    }

    private int getDayByPosition(int position) {
        if (position == 0
                || position == 1
                || position == listDay.size() - 2
                || position == listDay.size() - 1) {
            return 0;
        } else {
            return listDay.get(position);
        }
    }

    private int getMonthByPosition(int position) {
        if (position == 0
                || position == 1
                || position == listMonth.size() - 2
                || position == listMonth.size() - 1) {
            return 0;
        } else {
            return position - 2;
        }
    }

    private int getYearByPosition(int position) {
        if (position == 0
                || position == 1
                || position == listYear.size() - 2
                || position == listYear.size() - 1) {
            return 0;
        } else {
            int yearOfZeroIndex = currentYear - 100 + 2/* 2 null object */ - 2/* 2 index first to make center */;
            return yearOfZeroIndex + position - 2;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // find list view
        lvDay = (ListView) findViewById(R.id.lv_day);
        lvMonth = (ListView) findViewById(R.id.lv_month);
        lvYear = (ListView) findViewById(R.id.lv_year);

        // build list day
        listDay.clear();
        listDay.add(null);
        listDay.add(null);
        for (int i = 1; i <= 31; i++) {
            listDay.add(i);
        }
        listDay.add(null);
        listDay.add(null);

        // build list month
        listMonth.clear();
        listMonth.add(null);
        listMonth.add(null);
        for (int i = 0; i < 12; i++) {
            listMonth.add(i);
        }
        listMonth.add(null);
        listMonth.add(null);

        // build list year
        listYear.clear();
        int minYear = currentYear - 100;
        int maxYear = currentYear + 100;
        listYear.add(null);
        listYear.add(null);
        for (int i = minYear; i <= maxYear; i++) {
            listYear.add(i);
        }
        listYear.add(null);
        listYear.add(null);

        // set adapter
        dayAdapter = new DayAdapter();
        lvDay.setAdapter(dayAdapter);
        lvMonth.setAdapter(new MonthAdapter());
        lvYear.setAdapter(new YearAdapter());

        // valid day month year
        validateDayMonthYear();

        // init current day month year
        lvDay.setSelection(getPositionByDay(day));
        lvMonth.setSelection(getPositionByMonth(month));
        lvYear.setSelection(getPositionByYear(year));

        lvDay.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    int currentIndex = view.getFirstVisiblePosition();
                    int zeroIndexTop = view.getChildAt(0).getTop();
                    int oneIndexTop = view.getChildAt(1).getTop();
                    int total = Math.abs(zeroIndexTop) + Math.abs(oneIndexTop);
                    if (zeroIndexTop < 0) {
                        if (Math.abs(zeroIndexTop) > total / 2) {
                            currentIndex = currentIndex + 1;
                        }
                    }
                    view.setSelection(currentIndex);

                    day = getDayByPosition(currentIndex + 2);
                    validateDayMonthYear();
                    if (listener != null) {
                        listener.onDateChange(DateUtils.createDateFromDMY(day, month, year));
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        lvMonth.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    int currentIndex = view.getFirstVisiblePosition();
                    int zeroIndexTop = view.getChildAt(0).getTop();
                    int oneIndexTop = view.getChildAt(1).getTop();
                    int total = Math.abs(zeroIndexTop) + Math.abs(oneIndexTop);
                    if (zeroIndexTop < 0) {
                        if (Math.abs(zeroIndexTop) > total / 2) {
                            currentIndex = currentIndex + 1;
                        }
                    }
                    view.setSelection(currentIndex);

                    month = getMonthByPosition(currentIndex + 2);
                    validateDayMonthYear();
                    if (listener != null) {
                        listener.onDateChange(DateUtils.createDateFromDMY(day, month, year));
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        lvYear.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    int currentIndex = view.getFirstVisiblePosition();
                    int zeroIndexTop = view.getChildAt(0).getTop();
                    int oneIndexTop = view.getChildAt(1).getTop();
                    int total = Math.abs(zeroIndexTop) + Math.abs(oneIndexTop);
                    if (zeroIndexTop < 0) {
                        if (Math.abs(zeroIndexTop) > total / 2) {
                            currentIndex = currentIndex + 1;
                        }
                    }
                    view.setSelection(currentIndex);

                    year = getYearByPosition(currentIndex + 2);
                    validateDayMonthYear();
                    if (listener != null) {
                        listener.onDateChange(DateUtils.createDateFromDMY(day, month, year));
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        lvDay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1 && position < listDay.size() - 2) {
                    lvDay.smoothScrollToPositionFromTop(position - 2, 0);

                    day = getDayByPosition(position);
                    validateDayMonthYear();
                    if (listener != null) {
                        listener.onDateChange(DateUtils.createDateFromDMY(day, month, year));
                    }
                }
            }
        });
        lvMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1 && position < listMonth.size() - 2) {
                    lvMonth.smoothScrollToPositionFromTop(position - 2, 0);

                    month = getMonthByPosition(position);
                    validateDayMonthYear();
                    if (listener != null) {
                        listener.onDateChange(DateUtils.createDateFromDMY(day, month, year));
                    }
                }
            }
        });
        lvYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1 && position < listYear.size() - 2) {
                    lvYear.smoothScrollToPositionFromTop(position - 2, 0);

                    year = getYearByPosition(position);
                    validateDayMonthYear();
                    if (listener != null) {
                        listener.onDateChange(DateUtils.createDateFromDMY(day, month, year));
                    }
                }
            }
        });
    }

    private class DayAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listDay != null ? listDay.size() : 0;
        }

        @Override
        public Integer getItem(int position) {
            return listDay.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder holder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_date_pick, parent, false);
                holder = new ViewHolder();
                holder.tvDay = (TextView) view.findViewById(R.id.tv_content);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            if (listDay.get(position) != null) {
                holder.tvDay.setText(String.valueOf(listDay.get(position)));
                holder.tvDay.setTextColor(getResources().getColor(DateUtils.isValidDate(listDay.get(position), month, year) ? R.color.black : R.color.tran_grey));
            } else {
                holder.tvDay.setText("");
            }

            return view;
        }

        private class ViewHolder {
            TextView tvDay;
        }

    }

    private class MonthAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listMonth != null ? listMonth.size() : 0;
        }

        @Override
        public Integer getItem(int position) {
            return listMonth.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder holder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_date_pick, parent, false);
                holder = new ViewHolder();
                holder.tvMonth = (TextView) view.findViewById(R.id.tv_content);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            if (listMonth.get(position) != null) {
                holder.tvMonth.setText(DateUtils.getMonth(listMonth.get(position)));
            } else {
                holder.tvMonth.setText("");
            }

            return view;
        }

        private class ViewHolder {
            TextView tvMonth;
        }

    }

    private class YearAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listYear != null ? listYear.size() : 0;
        }

        @Override
        public Integer getItem(int position) {
            return listYear.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder holder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_date_pick, parent, false);
                holder = new ViewHolder();
                holder.tvYear = (TextView) view.findViewById(R.id.tv_content);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            if (listYear.get(position) != null) {
                holder.tvYear.setText(String.valueOf(listYear.get(position)));
            } else {
                holder.tvYear.setText("");
            }

            return view;
        }

        private class ViewHolder {
            TextView tvYear;
        }

    }

    private void validateDayMonthYear() {
        while (!DateUtils.isValidDate(day, month, year)) {
            day--;
        }
        dayAdapter.notifyDataSetChanged();
        lvDay.smoothScrollToPositionFromTop(getPositionByDay(day), 0);
    }

    private IDayMonthYearPickerViewListener listener;

    public void setDayMonthYearPickerViewListener(IDayMonthYearPickerViewListener listener) {
        this.listener = listener;
    }

    public interface IDayMonthYearPickerViewListener {
        void onDateChange(Date date);
    }
}
