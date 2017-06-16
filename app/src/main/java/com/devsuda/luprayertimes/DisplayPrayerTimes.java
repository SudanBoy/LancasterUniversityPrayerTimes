package com.devsuda.luprayertimes;

import android.database.Cursor;
import android.graphics.Color;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Abubakr on 07/06/2017.
 */

public class DisplayPrayerTimes {

    private static final int igamaCountDownId = 1;
    private static final int azanCountDownId = 2;
    private static final int maghribCountDownId = 3;
    private MainActivity mainActivity;

    private long fajrIgama_in_ms;
    private long zhorIgama_in_ms;
    private long asorIgama_in_ms;
    private long magribIgama_in_ms;
    private long ishaIgama_in_ms;

    private long currentTimeInMillis;
    private long timeToNextIgamaInMillis;
    private long timeToNextAzanInMillis;

    private long fajrAzan_in_ms;
    private long zhorAzan_in_ms;
    private long asorAzan_in_ms;
    private long ishaAzan_in_ms;

    private Date fajrIgama_time;
    private Date zhorIgama_time;
    private Date asorIgama_time;
    private Date magribIgama_time;
    private Date ishaIgama_time;

    private Date fajrAzan_time;
    private Date zhorAzan_time;
    private Date asorAzan_time;
    private Date ishaAzan_time;

    private Date current_time;
    private String currentDate;
    private Date tomorrow_date = null;
    private Calendar calendar_1;
    private Calendar calendar_2;
    private Calendar cal1;
    private String nextDay;

    private DateTimeAdaptor dateTimeAdaptor;
    private DisplayCountdowns displayCountdowns;


    private TextView displayMsgToUsrTv;

    private TextView dateNameTV;
    private TextView gregorianTimeTV;

    private TextView shehriLblTV;
    private TextView fajrAzanTV;
    private TextView fajrIgamaTV;
    private TextView sunriseTV;
    private TableLayout fajrBgTl;

    private TextView zhorAzanTV;
    private TextView zhorIgamaTV;
    private TableLayout zhorBgTL;

    private TextView asorAzanHanafiTV;
    private TextView asorIgamaTV;
    private TableLayout asorBgTL;

    private TextView magribIgamaTV;
    private TableLayout magribBgTL;

    private TextView ishaAzanTV;
    private TextView ishaIgamaTV;
    private TableLayout ishaBgTL;

    private long nextPrayerIgamaTimeInMs = 0;
    private long nextPrayerAzanTimeInMs = 0;

    private static final String highlghtColor = "#ffffff";

    public DisplayPrayerTimes(MainActivity _mainActivity) {
        this.mainActivity = _mainActivity;
        this.dateTimeAdaptor = new DateTimeAdaptor();
        this.displayCountdowns = new DisplayCountdowns(_mainActivity);
        this.current_time = new Date();
        this.currentDate = current_time.toString();
        this.calendar_1 = Calendar.getInstance();
        this.calendar_2 = Calendar.getInstance();
        this.cal1 = Calendar.getInstance();
    }

    public void get_time_diff(Cursor cursor, DatabaseAdaptor databaseAdaptor) {

        fajrIgama_in_ms = dateTimeAdaptor.convertToMillis(cursor, 1, null);
        zhorIgama_in_ms = dateTimeAdaptor.convertToMillis(cursor, 2, null);
        asorIgama_in_ms = dateTimeAdaptor.convertToMillis(cursor, 3, null);
        magribIgama_in_ms = dateTimeAdaptor.convertToMillis(cursor, 4, null);
        ishaIgama_in_ms = dateTimeAdaptor.convertToMillis(cursor, 5, null);

        fajrAzan_in_ms = dateTimeAdaptor.convertToMillis(cursor, 7, null);
        zhorAzan_in_ms = dateTimeAdaptor.convertToMillis(cursor, 8, null);
        asorAzan_in_ms = dateTimeAdaptor.convertToMillis(cursor, 9, null);
        ishaAzan_in_ms = dateTimeAdaptor.convertToMillis(cursor, 10, null);

        fajrIgama_time = new Date(fajrIgama_in_ms);
        zhorIgama_time = new Date(zhorIgama_in_ms);
        asorIgama_time = new Date(asorIgama_in_ms);
        magribIgama_time = new Date(magribIgama_in_ms);
        ishaIgama_time = new Date(ishaIgama_in_ms);

        fajrAzan_time = new Date(fajrAzan_in_ms);
        zhorAzan_time = new Date(zhorAzan_in_ms);
        asorAzan_time = new Date(asorAzan_in_ms);
        ishaAzan_time = new Date(ishaAzan_in_ms);

        displayMsgToUsrTv = (TextView) mainActivity.findViewById(R.id.displayMsgToUsrTv);

        this.dateNameTV = (TextView) mainActivity.findViewById(R.id.dayNameTV);
        this.gregorianTimeTV = (TextView) mainActivity.findViewById(R.id.gregorianTimeTV);

        this.shehriLblTV = (TextView) mainActivity.findViewById(R.id.shehriLblTV);
        this.fajrAzanTV = (TextView) mainActivity.findViewById(R.id.fajrAzanTV);
        this.fajrIgamaTV = (TextView) mainActivity.findViewById(R.id.fajrIgamaTV);
        this.sunriseTV = (TextView) mainActivity.findViewById(R.id.sunriseTV);
        this.fajrBgTl = (TableLayout) mainActivity.findViewById(R.id.fajrBgTL);

        this.zhorAzanTV = (TextView) mainActivity.findViewById(R.id.zhorAzanTV);
        this.zhorIgamaTV = (TextView) mainActivity.findViewById(R.id.zhorIgamaTV);
        this.zhorBgTL = (TableLayout) mainActivity.findViewById(R.id.zhorBgTL);

        this.asorAzanHanafiTV = (TextView) mainActivity.findViewById(R.id.asorAzanHanafiTV);
        this.asorIgamaTV = (TextView) mainActivity.findViewById(R.id.asorIgamaTV);
        this.asorBgTL = (TableLayout) mainActivity.findViewById(R.id.asorBgTL);

        this.magribIgamaTV = (TextView) mainActivity.findViewById(R.id.magribIgamaTV);
        this.magribBgTL = (TableLayout) mainActivity.findViewById(R.id.magribBgTL);

        this.ishaAzanTV = (TextView) mainActivity.findViewById(R.id.ishaAzanTV);
        this.ishaIgamaTV = (TextView) mainActivity.findViewById(R.id.ishaIgamaTV);
        this.ishaBgTL = (TableLayout) mainActivity.findViewById(R.id.ishaBgTL);

        displayTodayPrayerTimes(cursor);
        setNextPrayerAzanTimeInMsForCountdown(cursor, databaseAdaptor);
        setNextPrayerIgamaTimeInMsForCountdown(cursor, databaseAdaptor);
        startCountdown();
    }

    private void startCountdown() {
        currentTimeInMillis = Calendar.getInstance().getTimeInMillis();
        timeToNextIgamaInMillis = nextPrayerIgamaTimeInMs - currentTimeInMillis;
        timeToNextAzanInMillis = nextPrayerAzanTimeInMs - currentTimeInMillis;

        if (current_time.before(magribIgama_time) && current_time.after(asorIgama_time)) {
            displayCountdowns.countdown(timeToNextIgamaInMillis, igamaCountDownId);
            displayCountdowns.countdown(timeToNextIgamaInMillis, maghribCountDownId);
        } else {
            displayCountdowns.countdown(timeToNextIgamaInMillis, igamaCountDownId);
            displayCountdowns.countdown(timeToNextAzanInMillis, azanCountDownId);
        }
    }

    private void setNextPrayerAzanTimeInMsForCountdown(Cursor cursor, DatabaseAdaptor databaseAdaptor) {
        if (current_time.before(fajrAzan_time)) {
            nextPrayerAzanTimeInMs = fajrAzan_in_ms;
        } else if (current_time.before(zhorAzan_time)
                && current_time.after(fajrIgama_time)) {
            nextPrayerAzanTimeInMs = zhorAzan_in_ms;
        } else if (current_time.before(asorAzan_time)
                && current_time.after(zhorIgama_time)) {
            nextPrayerAzanTimeInMs = asorAzan_in_ms;
        } else if (current_time.before(magribIgama_time)
                && current_time.after(asorIgama_time)) {
            nextPrayerAzanTimeInMs = magribIgama_in_ms;
        } else if (current_time.before(ishaAzan_time)
                && current_time.after(magribIgama_time)) {
            nextPrayerAzanTimeInMs = ishaAzan_in_ms;
        } else if (current_time.after(ishaIgama_time)) {
            // initialize the calendar with current day
            try {
                calendar_1.setTime(dateTimeAdaptor.dateformat_2().parse(currentDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // go to next day in the calendar
            calendar_1.add(Calendar.DATE, 1);

            // Returns the time of this Calendar as a DateTimeAdaptor object.
            nextDay = dateTimeAdaptor.dateformat_2().format(calendar_1.getTime());

            // parse nextDay with format_2 to get the right "date" syntax
            try {
                tomorrow_date = dateTimeAdaptor.dateformat_2().parse(nextDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // set tomorrow's date as today's date in the calendar
            calendar_2.setTime(tomorrow_date);

            // Query the database
            int dayOfMonth = calendar_2.get(Calendar.DAY_OF_MONTH);
            cursor = databaseAdaptor.getPrayersTimeOnDay(dayOfMonth);

            fajrAzan_in_ms = dateTimeAdaptor.convertToMillis(cursor, 7, null);
            nextPrayerAzanTimeInMs = fajrAzan_in_ms;
        }
    }

    private void setNextPrayerIgamaTimeInMsForCountdown(Cursor cursor, DatabaseAdaptor databaseAdaptor) {
        if (current_time.before(fajrIgama_time)) {
            nextPrayerIgamaTimeInMs = fajrIgama_in_ms;
            fajrBgTl.setBackgroundColor(Color.parseColor(highlghtColor));
        } else if (current_time.before(zhorIgama_time)
                && current_time.after(fajrIgama_time)) {
            if (dateTimeAdaptor.isFriday() == true) {
                zhorBgTL.setBackgroundColor(Color.parseColor(highlghtColor));
                nextPrayerIgamaTimeInMs = zhorIgama_in_ms;
            } else {
                nextPrayerIgamaTimeInMs = zhorIgama_in_ms;
                zhorBgTL.setBackgroundColor(Color.parseColor(highlghtColor));
            }
        } else if (current_time.before(asorIgama_time)
                && current_time.after(zhorIgama_time)) {
            nextPrayerIgamaTimeInMs = asorIgama_in_ms;
            asorBgTL.setBackgroundColor(Color.parseColor(highlghtColor));
        } else if (current_time.before(magribIgama_time)
                && current_time.after(asorIgama_time)) {
            nextPrayerIgamaTimeInMs = magribIgama_in_ms;
            magribBgTL.setBackgroundColor(Color.parseColor(highlghtColor));
        } else if (current_time.before(ishaIgama_time)
                && current_time.after(magribIgama_time)) {
            nextPrayerIgamaTimeInMs = ishaIgama_in_ms;
            ishaBgTL.setBackgroundColor(Color.parseColor(highlghtColor));
        } else if (current_time.after(ishaIgama_time)) {

            // initialize the calendar with current day
            try {
                calendar_1.setTime(dateTimeAdaptor.dateformat_2().parse(currentDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // go to next day in the calendar
            calendar_1.add(Calendar.DATE, 1);

            // Returns the time of this Calendar as a DateTimeAdaptor object.
            nextDay = dateTimeAdaptor.dateformat_2().format(calendar_1.getTime());

            // parse nextDay with format_2 to get the right "date" syntax
            try {
                tomorrow_date = dateTimeAdaptor.dateformat_2().parse(nextDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // set tomorrow's date as toda's date in the calendar
            calendar_2.setTime(tomorrow_date);

            // Query the database
            int dayOfMonth = calendar_2.get(Calendar.DAY_OF_MONTH);
            cursor = databaseAdaptor.getPrayersTimeOnDay(dayOfMonth);

            displayNextDayPrayerTimes(cursor);
        }
    }

    private void displayNextDayPrayerTimes(Cursor cursor) {
        displayMsgToUsrTv.setText("All times are NOW set for tomorrow");
        showTimesOnGui(cursor);
        nextPrayerIgamaTimeInMs = dateTimeAdaptor.convertToMillis(cursor, 1, null);
        fajrBgTl.setBackgroundColor(Color.parseColor(highlghtColor));
    }

    private void displayTodayPrayerTimes(Cursor cursor) {
        setDateNameTV(cal1.get(Calendar.DAY_OF_WEEK));
        gregorianTimeTV.setText(dateTimeAdaptor.gregorianDateFormat().format(new Date()));
        showTimesOnGui(cursor);
    }

    private void showTimesOnGui(Cursor cursor) {
        shehriLblTV.setText(cursor.getString(3));
        fajrAzanTV.setText(cursor.getString(4));
        fajrIgamaTV.setText(cursor.getString(5));
        sunriseTV.setText(cursor.getString(6));
        zhorAzanTV.setText(cursor.getString(7));
        zhorIgamaTV.setText(cursor.getString(8));
        asorAzanHanafiTV.setText(cursor.getString(10));
        asorIgamaTV.setText(cursor.getString(11));
        magribIgamaTV.setText(cursor.getString(12));
        ishaAzanTV.setText(cursor.getString(13));
        ishaIgamaTV.setText(cursor.getString(14));
    }

    private void setDateNameTV(int currentDay) {
        switch (currentDay) {
            case Calendar.SATURDAY:
                dateNameTV.setText("Saturday");
                break;
            case Calendar.SUNDAY:
                dateNameTV.setText("Sunday");
                break;
            case Calendar.MONDAY:
                dateNameTV.setText("Monday");
                break;
            case Calendar.TUESDAY:
                dateNameTV.setText("Tuesday");
                break;
            case Calendar.WEDNESDAY:
                dateNameTV.setText("Wednesday");
                break;
            case Calendar.THURSDAY:
                dateNameTV.setText("Thursday");
                break;
            case Calendar.FRIDAY:
                dateNameTV.setText("Friday");
                break;
        }
    }

}
