package com.hqyxjy.ldf.supercalendar;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ldf.calendar.Utils;
import com.ldf.calendar.component.State;
import com.ldf.calendar.interf.IDayRenderer;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.DayView;

/**
 * 自定义日历item
 *
 * @author haohao on 2017/9/22 上午 10:26
 * @version v1.0
 */
public class LianChengDayView extends DayView {

    private TextView dateTv;
    private TextView dateLunarTv;
    private ImageView marker;
    private View selectedBackground;
    private final CalendarDate today = new CalendarDate();

    /**
     * 构造器
     *
     * @param context 上下文
     */
    public LianChengDayView(Context context) {
        super(context, R.layout.liancheng_day);
        dateTv = (TextView) findViewById(R.id.lc_date);
        dateLunarTv = (TextView) findViewById(R.id.lc_date_lunar);
        marker = (ImageView) findViewById(R.id.lc_maker);
        selectedBackground = findViewById(R.id.lc_selected_background);
    }

    @Override
    public void refreshContent() {
        renderToday(day.getDate());
        renderSelect(day.getDate(), day.getState());
        renderMarker(day.getDate(), day.getState());
        super.refreshContent();
    }

    private void renderMarker(CalendarDate date, State state) {
        if (Utils.loadMarkData().containsKey(date.toString())) {
            marker.setVisibility(VISIBLE);
            //今天
            if (date.toString().equals(today.toString())) {
                if (state == State.SELECT) {
                    marker.setImageResource(R.drawable.lc_view_active_today_mark_background);
                } else {
                    marker.setImageResource(R.drawable.lc_view_unactive_today_mark_background);
                }
            } else {
                if (state == State.SELECT) {
                    marker.setImageResource(R.drawable.lc_view_active_today_mark_background);
                } else {
                    marker.setImageResource(R.drawable.lc_view_active_mark_background);
                }
            }

            if (Utils.loadMarkData().get(date.toString()).equals("0")) {
                marker.setEnabled(true);
            } else {
                marker.setEnabled(false);
            }
        } else {
            if (date.toString().equals(today.toString())) {
                if (state == State.SELECT) {
                    marker.setImageResource(R.drawable.lc_view_active_today_mark_background);
                } else {
                    marker.setImageResource(R.drawable.lc_view_unactive_today_mark_background);
                }
                marker.setVisibility(VISIBLE);
                marker.setEnabled(true);
            } else {
                marker.setVisibility(GONE);
            }
        }
    }

    private void renderSelect(CalendarDate date, State state) {
        if (state == State.SELECT) {
            selectedBackground.setVisibility(VISIBLE);
            dateTv.setTextColor(Color.WHITE);
            dateLunarTv.setTextColor(Color.WHITE);
        } else if (state == State.NEXT_MONTH || state == State.PAST_MONTH) {
            selectedBackground.setVisibility(GONE);
            dateTv.setTextColor(Color.parseColor("#d5d5d5"));
            dateLunarTv.setTextColor(Color.parseColor("#d5d5d5"));
        } else {
            selectedBackground.setVisibility(GONE);
            if (date != null) {
                if (date.equals(today)) {
                    dateTv.setTextColor(Color.parseColor("#009eff"));
                    dateLunarTv.setTextColor(Color.parseColor("#009eff"));
                } else {
                    dateTv.setTextColor(Color.parseColor("#000000"));
                    dateLunarTv.setTextColor(Color.parseColor("#999999"));
                }
            }
        }
    }

    private void renderToday(CalendarDate date) {
        if (date != null) {
            if (date.equals(today)) {
                dateTv.setText("今");
            } else {
                dateTv.setText(date.day + "");
            }
            dateLunarTv.setText(new Lunar(Lunar.getCalendar(date)).getDaytoString());
        }
    }

    @Override
    public IDayRenderer copy() {
        return new LianChengDayView(context);
    }
}
