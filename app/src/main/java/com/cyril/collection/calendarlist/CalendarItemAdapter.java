package com.cyril.collection.calendarlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyril.collection.R;
import com.cyril.collection.modules.CustomCalendarItemModel;
import com.cyril.collection.widget.calendar.BaseCalendarItemAdapter;
import com.cyril.collection.widget.calendar.BaseCalendarItemModel;

/**
 * Created by cyril on 2017/8/11.
 */

public class CalendarItemAdapter extends BaseCalendarItemAdapter<CustomCalendarItemModel> {
    public CalendarItemAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(String date, CustomCalendarItemModel model, View convertView, ViewGroup parent) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.custom_calendar_item, null);
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

        TextView dayNewsCount = (TextView) view.findViewById(R.id.tv_day_new_count);
        if (model.getNewsCount() > 0) {
            dayNewsCount.setText(String.format(context.getResources().getString(R.string.calendar_item_new_count), model.getNewsCount()));
            dayNewsCount.setVisibility(View.VISIBLE);
        } else {
            dayNewsCount.setVisibility(View.GONE);
        }

        ImageView isFavImageView = (ImageView) view.findViewById(R.id.image_is_fav);
        if (model.isFav()) {
            isFavImageView.setVisibility(View.VISIBLE);
        } else {
            isFavImageView.setVisibility(View.GONE);
        }
        return view;
    }
}
