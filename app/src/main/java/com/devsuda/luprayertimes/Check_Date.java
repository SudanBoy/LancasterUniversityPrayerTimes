package com.devsuda.luprayertimes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Check_Date {

	public static boolean ifItIsFriday() {
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 6) {
			return true;
		} else {
			return false;
		}
	}

	public static DateFormat dateformat_1() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm");
		return dateFormat;
	}

	public static DateFormat dateformat_2() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat;
	}

	public static DateFormat dateformat_3() {
		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		return dateFormat;
	}

}
