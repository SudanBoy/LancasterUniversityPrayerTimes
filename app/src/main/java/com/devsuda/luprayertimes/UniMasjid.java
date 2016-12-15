package com.devsuda.luprayertimes;

import java.io.IOException;
import java.util.Calendar;

import com.devsuda.luprayertimes.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;

public class UniMasjid extends Activity {

	Gui_Manager_Uni time_date = new Gui_Manager_Uni(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_university_masjid);

		// ------------------

		Db_Manager_Uni dba_prayertimes_uni = new Db_Manager_Uni(this);
		try {
			dba_prayertimes_uni.create();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			dba_prayertimes_uni.open();
			dba_prayertimes_uni.getWritableDatabase();
		} catch (SQLException sqle) {
			throw sqle;
		}

		// ---------

		int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		Cursor c = dba_prayertimes_uni.getPrayersTimeOnDay(dayOfMonth);

		time_date.get_time_diff(c, dba_prayertimes_uni);

		c.close();

		dba_prayertimes_uni.close();
	}

	public void gotoFacebookApp(View view) {
		if (isConnectedToNetwork() == true) {
			if (isDataAvialble() == true) {
				gotoApp(1);
			}
		} else {
			Toast.makeText(this, "Sorry, there is no network connectivity",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void gotoLancasterMasjidApp(View view) {
		gotoApp(2);
	}

	private boolean isConnectedToNetwork() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	private boolean isDataAvialble() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
		NetworkInfo MobileNwInfo = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiNwInfo = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return ((MobileNwInfo == null ? false : MobileNwInfo.isConnected()) || (wifiNwInfo == null ? false
				: wifiNwInfo.isConnected()));
	}

	private boolean appInstalledOrNot(String uri) {
		PackageManager pm = getPackageManager();
		boolean app_installed = false;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			app_installed = false;
		}
		return app_installed;
	}

	public void gotoApp(int appChoice) {

		boolean isFbInstalled = appInstalledOrNot("com.facebook.katana");
		boolean isLancsMasjidInstalled = appInstalledOrNot("com.devsuda.lancasterprayertimes");

		if (appChoice == 1) {
			if (isFbInstalled) {
				Toast.makeText(this, "Openning Facebook App",
						Toast.LENGTH_SHORT).show();

				try {
					Intent intent = new Intent(Intent.ACTION_VIEW,
							Uri.parse("fb://page/121498887937290"));
					startActivity(intent);
				} catch (Exception e) {
					startActivity(new Intent(Intent.ACTION_VIEW,
							Uri.parse("fb://page/121498887937290")));
				}

			} else {
				Toast.makeText(this, "Openning Facebook on Browser",
						Toast.LENGTH_SHORT).show();
				String url = "https://www.facebook.com/LancsIsoc";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}

		} else if (appChoice == 2) {
			if (isLancsMasjidInstalled) {
				Intent LaunchIntent = getPackageManager()
						.getLaunchIntentForPackage(
								"com.devsuda.lancasterprayertimes");
				startActivity(LaunchIntent);
			} else {
				installApp();
			}
		}

	}

	public void installApp() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog
				.setMessage("\nMasjid E-nour App isn't installed...\nDo you want to install it now?");
		alertDialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							startActivity(new Intent(
									Intent.ACTION_VIEW,
									Uri.parse("market://details?id="
											+ "com.devsuda.lancasterprayertimes")));
						} catch (android.content.ActivityNotFoundException anfe) {
							startActivity(new Intent(
									Intent.ACTION_VIEW,
									Uri.parse("http://play.google.com/store/apps/details?id"
											+ "com.devsuda.lancasterprayertimes")));
						}
					}
				});

		alertDialog.setNegativeButton("No",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		alertDialog.show();
	}

	@Override
	public void onResume() {
		super.onResume();
		setContentView(R.layout.activity_university_masjid);

		// ======================================
		Db_Manager_Uni dba_prayertimes_uni = new Db_Manager_Uni(this);
		try {
			dba_prayertimes_uni.create();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			dba_prayertimes_uni.open();
			dba_prayertimes_uni.getWritableDatabase();
		} catch (SQLException sqle) {
			throw sqle;
		}

		int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		Cursor c = dba_prayertimes_uni.getPrayersTimeOnDay(dayOfMonth);

		time_date.get_time_diff(c, dba_prayertimes_uni);

		c.close();

		dba_prayertimes_uni.close();

		// ======================================

	}

}
