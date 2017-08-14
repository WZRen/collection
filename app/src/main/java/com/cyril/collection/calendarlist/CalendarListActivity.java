package com.cyril.collection.calendarlist;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Toast;

import com.cyril.collection.R;
import com.cyril.collection.modules.CustomCalendarItemModel;
import com.cyril.collection.modules.News;
import com.cyril.collection.modules.StoriesBean;
import com.cyril.collection.post.ApiService;
import com.cyril.collection.widget.calendar.CalendarHelper;
import com.cyril.collection.widget.calendar.CalendarListView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import rx.Notification;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cyril on 2017/8/11.
 */

public class CalendarListActivity extends RxAppCompatActivity {
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat("yyyy年MM月");

    private CalendarListView calendarListView;
    private DayNewsListAdapter dayNewsListAdapter;
    private CalendarItemAdapter calendarItemAdapter;

    private TreeMap<String, List<StoriesBean>> listTreeMap = new TreeMap<>();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_list);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        calendarListView = (CalendarListView) findViewById(R.id.calendar_listview);
        dayNewsListAdapter = new DayNewsListAdapter(this);
        calendarItemAdapter = new CalendarItemAdapter(this);
        calendarListView.setCalendarListViewAdapter(calendarItemAdapter, dayNewsListAdapter);

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -7);

        loadNewsList(DAY_FORMAT.format(calendar.getTime()));
        actionBar.setTitle(YEAR_MONTH_FORMAT.format(calendar.getTime()));
        calendarListView.setOnListPullListener(new CalendarListView.onListPullListener() {
            @Override
            public void onRefresh() {
                String date = listTreeMap.firstKey();
                Calendar calendar = CalendarHelper.getCalendarByYearMonthDay(date);
                calendar.add(Calendar.MONTH, -1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                loadNewsList(DAY_FORMAT.format(calendar.getTime()));
            }

            @Override
            public void onLoadMore() {
                String date = listTreeMap.lastKey();
                Calendar calendar = CalendarHelper.getCalendarByYearMonthDay(date);
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                loadNewsList(DAY_FORMAT.format(calendar.getTime()));
            }
        });

        calendarListView.setOnMonthChangedListener(new CalendarListView.OnMonthChangedListener() {
            @Override
            public void onMonthChanged(String yearMonth) {
                Calendar calendar = CalendarHelper.getCalendarByYearMonth(yearMonth);
                actionBar.setTitle(YEAR_MONTH_FORMAT.format(calendar.getTime()));
                loadCalendarData(yearMonth);
                Toast.makeText(CalendarListActivity.this, YEAR_MONTH_FORMAT.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
            }
        });

        calendarListView.setOnCalendarViewItemClickListener(new CalendarListView.OnCalendarViewItemClickListener() {
            @Override
            public void onDateSelected(View view, String selectedDate, int listSection, SelectedDateRegion selectedDateRegion) {

            }
        });
    }

    private void loadNewsList(final String date) {
        Calendar calendar = getCalendarByYearMonthDay(date);
        String key = CalendarHelper.YEAR_MONTH_FORMAT.format(calendar.getTime());
        final Random random = new Random();
        final List<String> set = new ArrayList<>();
        while (set.size() < 5) {
            int i = random.nextInt(29);
            if (i > 0) {
                if (!set.contains(key + "-" + i)) {
                    if (i < 10) {
                        set.add(key + "-0" + i);
                    } else {
                        set.add(key + "-" + i);
                    }
                }
            }
        }

        Observable<Notification<News>> newsListOb = RetrofitProvider.getInstance()
                .create(ApiService.class)
                .getNewsList(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<News>bindToLifecycle())
                .materialize().share();

        newsListOb
                .filter(new Func1<Notification<News>, Boolean>() {
                    @Override
                    public Boolean call(Notification<News> newsNotification) {
                        return newsNotification.isOnNext();
                    }
                })
                .map(new Func1<Notification<News>, News>() {
                    @Override
                    public News call(Notification<News> newsNotification) {
                        return newsNotification.getValue();
                    }
                })
                .filter(new Func1<News, Boolean>() {
                    @Override
                    public Boolean call(News news) {
                        return !news.getStories().isEmpty();
                    }
                })
                .flatMap(new Func1<News, Observable<StoriesBean>>() {
                    @Override
                    public Observable<StoriesBean> call(News news) {
                        return Observable.from(news.getStories());
                    }
                })
                .doOnNext(new Action1<StoriesBean>() {
                    @Override
                    public void call(StoriesBean bean) {
                        int index = random.nextInt(5);
                        String day = set.get(index);
                        if (listTreeMap.get(day) != null) {
                            List<StoriesBean> list = listTreeMap.get(day);
                            list.add(bean);
                        } else {
                            List<StoriesBean> list = new ArrayList<StoriesBean>();
                            list.add(bean);
                            listTreeMap.put(day, list);
                        }
                    }
                })
                .toList()
                .subscribe(new Action1<List<StoriesBean>>() {
                    @Override
                    public void call(List<StoriesBean> storiesBeen) {
                        dayNewsListAdapter.setDateDataMap(listTreeMap);
                        dayNewsListAdapter.notifyDataSetChanged();
                        calendarItemAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void loadCalendarData(final String date) {
        new Thread() {
            @Override
            public void run() {
//                try {
//                    sleep(1000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Random random = new Random();
                        if (date.equals(calendarListView.getCurrentSelectedDate().substring(0, 7))) {
                            for (String d : listTreeMap.keySet()) {
                                if (date.equals(d.substring(0, 7))) {
                                    CustomCalendarItemModel customCalendarItemModel = calendarItemAdapter.getDayModelList().get(d);
                                    if (customCalendarItemModel != null) {
                                        customCalendarItemModel.setNewsCount(listTreeMap.get(d).size());
//                                        Log.e("calendar", date + "；" + random.nextBoolean());
                                        customCalendarItemModel.setFav(random.nextBoolean());
                                    }

                                }
                            }
                            calendarItemAdapter.notifyDataSetChanged();
                        }
                    }
                });
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }

        }.start();

    }


    private Calendar getCalendarByYearMonthDay(String yearMonthDay) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTimeInMillis(DAY_FORMAT.parse(yearMonthDay).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar;
    }
}
