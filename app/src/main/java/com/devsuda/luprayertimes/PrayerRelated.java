package com.devsuda.luprayertimes;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import android.database.Cursor;

public class PrayerRelated {

	public static long prayerTimeInMs(Cursor c, int prayerID, String nextDay) {

		String prayerIgamaTimeAsString = null;
		Date prayerIgamaTimeFormated = null;

		Calendar getCurrentYearCal = Calendar.getInstance();
		int currentYear = getCurrentYearCal.get(getCurrentYearCal.YEAR);
		String theYear = String.valueOf(currentYear);

		long prayerTimeInMs;

		if (prayerID == 1) { // fajr igama
			prayerIgamaTimeAsString = theYear + c.getString(1) + " " + c.getString(5);
		} else if (prayerID == 7) { // fajr azan
			prayerIgamaTimeAsString = theYear + c.getString(1) + " " + c.getString(4);
		} else if (prayerID == 2) { // zhor Igama
			prayerIgamaTimeAsString = theYear + c.getString(1) + " " + c.getString(8);
		} else if (prayerID == 8) { // zhor Azan
			prayerIgamaTimeAsString = theYear + c.getString(1) + " " + c.getString(7);
		} else if (prayerID == 3) { // asor Igama
			prayerIgamaTimeAsString = theYear + c.getString(1) + " " + c.getString(11);
		} else if (prayerID == 9) { // asor Azan
			prayerIgamaTimeAsString = theYear + c.getString(1) + " " + c.getString(10);
		} else if (prayerID == 4) { // Magrib Igama & Azan
			prayerIgamaTimeAsString = theYear + c.getString(1) + " " + c.getString(12);
		} else if (prayerID == 5) { // isha igama
			prayerIgamaTimeAsString = theYear + c.getString(1) + " " + c.getString(14);
		} else if (prayerID == 10) { // isha Azan
			prayerIgamaTimeAsString = theYear + c.getString(1) + " " + c.getString(13);
		} else if (prayerID == 6) {
			prayerIgamaTimeAsString = nextDay + " " + c.getString(5);
		}

		try {
			prayerIgamaTimeFormated = Check_Date.dateformat_1().parse(
					prayerIgamaTimeAsString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		prayerTimeInMs = prayerIgamaTimeFormated.getTime();

		return prayerTimeInMs;
	}

}
