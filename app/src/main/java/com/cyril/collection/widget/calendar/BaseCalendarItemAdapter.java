package com.cyril.collection.widget.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.cyril.collection.R;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by cyril on 2017/8/7.
 */

public class BaseCalendarItemAdapter<T extends BaseCalendarItemModel> extends BaseAdapter {
    protected Context context;
    protected TreeMap<String, T> dayModelList = new TreeMap<>();
    protected List<String> indexToTimeMap = new ArrayList<>();

    public BaseCalendarItemAdapter(Context context) {
        this.context = context;
    }

    public TreeMap<String, T> getDayModelList() {
        return dayModelList;
    }

    public void setDayModelList(TreeMap<String, T> dayModelList) {
        this.dayModelList = dayModelList;
        indexToTimeMap.clear();
        for (String time : this.dayModelList.keySet()) {
            indexToTimeMap.add(time);
        }
    }

    public List<String> getIndexToTimeMap() {
        return indexToTimeMap;
    }

    @Override
    public int getCount() {
        return dayModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(String date, T model, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.griditem_calendar_item, null);
        }
        TextView dayNum = (TextView) view.findViewById(R.id.tv_day_num);
        dayNum.setText(model.getDayNumber());
        view.setBackgroundResource(R.drawable.bg_shape_calendar_item_normal);
        if (model.isToday()) {
            dayNum.setTextColor(context.getResources().getColor(R.color.red_ff725f));
            dayNum.setText(context.getResources().getString(R.string.today));
        }

        if (model.isHoliday()) {
            dayNum.setTextColor(context.getResources().getColor(R.color.red_ff725f));
        }


        if (model.getStatus() == BaseCalendarItemModel.Status.DISABLE) {
            dayNum.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
        }

        if (!model.isCurrentMonth()) {
            dayNum.setTextColor(context.getResources().getColor(R.color.gray_bbbbbb));
            view.setClickable(true);
        }

        return view;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String date = indexToTimeMap.get(i);
        View view1 = getView(date, dayModelList.get(date), view, viewGroup);
        GridView.LayoutParams layoutParams = new AbsListView.LayoutParams(CalendarView.mItemWidth, CalendarView.mItemHeight);
        view1.setLayoutParams(layoutParams);
        return view1;
    }
}
