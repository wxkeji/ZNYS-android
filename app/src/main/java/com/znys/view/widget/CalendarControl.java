package com.znys.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.znys.model.CalendarGridItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.znys.R;
import com.znys.controller.CalendarController;

/**
 * Created by mushroom on 2015/6/9.
 */
public class CalendarControl extends RelativeLayout {

    private Calendar dateDisplay;
    private ArrayList<CalendarCellView> cells = new ArrayList<CalendarCellView>();
    private String calendarTitle;
    private LinearLayout calendarGrid;

    public CalendarControl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CalendarControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarControl(Context context) {
        super(context);
    }

    public void init(final CalendarController listener) {
        if (isInEditMode()) return;
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View layout = layoutInflater.inflate(R.layout.control_calendar_view, null, false);

        if (dateDisplay == null)
            dateDisplay = Calendar.getInstance();
        calendarGrid = (LinearLayout) layout.findViewById(R.id.calendar_grid);

        Calendar calendar = (Calendar) dateDisplay.clone();
        calendar.add(Calendar.DAY_OF_MONTH, 20);
        if (dateDisplay.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)) {
            calendarTitle = new SimpleDateFormat("yyyy MMM", Locale.getDefault()).format(dateDisplay.getTime());
            calendarTitle += new SimpleDateFormat("-MMM", Locale.getDefault()).format(calendar.getTime());
        } else {
            calendarTitle = new SimpleDateFormat("yyyy MMM", Locale.getDefault()).format(dateDisplay.getTime());
        }
        calendar = (Calendar) dateDisplay.clone();
        ((TextView) layout.findViewById(R.id.calendar_day_1)).setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()));
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        ((TextView) layout.findViewById(R.id.calendar_day_2)).setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()));
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        ((TextView) layout.findViewById(R.id.calendar_day_3)).setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()));
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        ((TextView) layout.findViewById(R.id.calendar_day_4)).setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()));
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        ((TextView) layout.findViewById(R.id.calendar_day_5)).setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()));
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        ((TextView) layout.findViewById(R.id.calendar_day_6)).setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()));
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        ((TextView) layout.findViewById(R.id.calendar_day_7)).setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()));

        for (int y = 0; y < calendarGrid.getChildCount(); y++) {
            LinearLayout row = (LinearLayout) calendarGrid.getChildAt(y);
            for (int x = 0; x < row.getChildCount(); x++) {
                CalendarCellView cell = (CalendarCellView) row.getChildAt(x);
                cell.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onCellItemClick(view);
                    }
                });
                cells.add(cell);
            }
        }

        addView(layout);
        updateCells();
    }

    private void updateCells() {
        Calendar calendar;
        if (dateDisplay != null)
            calendar = (Calendar) dateDisplay.clone();
        else
            calendar = Calendar.getInstance();

        for (int i = 0; i < 21; i++) {
            Calendar date = (Calendar) calendar.clone();
            date.add(Calendar.DAY_OF_MONTH, i);
            CalendarCellView cell = cells.get(i);
            cell.setTag(new CalendarGridItem(date.get(Calendar.DAY_OF_MONTH)).setEnabled(true).setDate(date));
            cell.setVisibility(View.VISIBLE);
        }
    }

    public void setDateDisplay(Calendar dateDisplay) {
        this.dateDisplay = dateDisplay;
    }

    public String getCalendarTitle() {
        return calendarTitle;
    }

    /**
     * call after change any input data - to refresh view
     */
    public void notifyChanges() {
        updateCells();
    }
}
