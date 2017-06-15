package com.devsuda.luprayertimes;

import android.database.Cursor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Abubakr on 07/06/2017.
 */

public class DateTimeAdaptor {

    private String tableName = null;
    private int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

    private void setCurrentMonthTableName() {
        switch (currentMonth) {
            case (0):
                tableName = "jan_Prayers";
                break;
            case (1):
                tableName = "feb_Prayers";
                break;
            case (2):
                tableName = "mar_Prayers";
                break;
            case (3):
                tableName = "apr_Prayers";
                break;
            case (4):
                tableName = "may_Prayers";
                break;
            case (5):
                tableName = "june_Prayers";
                break;
            case (6):
                tableName = "july_Prayers";
                break;
            case (7):
                tableName = "aug_Prayers";
                break;
            case (8):
                tableName = "sep_Prayers";
                break;
            case (9):
                tableName = "oct_Prayers";
                break;
            case (10):
                tableName = "nov_Prayers";
                break;
            case (11):
                tableName = "dec_Prayers";
                break;
        }
    }

    public String getCurrentMonthTableName() {
        setCurrentMonthTableName();
        return tableName;
    }

    public boolean isFriday() {
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 6) {
            return true;
        } else {
            return false;
        }
    }

    public DateFormat dateformat_1() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm");
        return dateFormat;
    }

    public DateFormat dateformat_2() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat;
    }

    public DateFormat gregorianDateFormat() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat;
    }


    public static long convertToMillis(Cursor cursor, int prayerID, String nextDay) {

        String prayerIgamaTimeAsString = null;
        Date prayerIgamaTimeFormated = null;
        DateTimeAdaptor dateTimeAdaptor = new DateTimeAdaptor();

        Calendar getCurrentYearCal = Calendar.getInstance();
        String currentYear = String.valueOf(getCurrentYearCal.get(getCurrentYearCal.YEAR));

        long prayerTimeInMs;

        if (prayerID == 1) { // fajr igama
            prayerIgamaTimeAsString = currentYear + cursor.getString(1) + " " + cursor.getString(5);
        } else if (prayerID == 7) { // fajr azan
            prayerIgamaTimeAsString = currentYear + cursor.getString(1) + " " + cursor.getString(4);
        } else if (prayerID == 2) { // zhor Igama
            prayerIgamaTimeAsString = currentYear + cursor.getString(1) + " " + cursor.getString(8);
        } else if (prayerID == 8) { // zhor Azan
            prayerIgamaTimeAsString = currentYear + cursor.getString(1) + " " + cursor.getString(7);
        } else if (prayerID == 3) { // asor Igama
            prayerIgamaTimeAsString = currentYear + cursor.getString(1) + " " + cursor.getString(11);
        } else if (prayerID == 9) { // asor Azan
            prayerIgamaTimeAsString = currentYear + cursor.getString(1) + " " + cursor.getString(10);
        } else if (prayerID == 4) { // Magrib Igama & Azan
            prayerIgamaTimeAsString = currentYear + cursor.getString(1) + " " + cursor.getString(12);
        } else if (prayerID == 5) { // isha igama
            prayerIgamaTimeAsString = currentYear + cursor.getString(1) + " " + cursor.getString(14);
        } else if (prayerID == 10) { // isha Azan
            prayerIgamaTimeAsString = currentYear + cursor.getString(1) + " " + cursor.getString(13);
        } else if (prayerID == 6) {
            prayerIgamaTimeAsString = nextDay + " " + cursor.getString(5);
        }

        try {
            prayerIgamaTimeFormated = dateTimeAdaptor.dateformat_1().parse(
                    prayerIgamaTimeAsString);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        prayerTimeInMs = prayerIgamaTimeFormated.getTime();

        return prayerTimeInMs;
    }
}
