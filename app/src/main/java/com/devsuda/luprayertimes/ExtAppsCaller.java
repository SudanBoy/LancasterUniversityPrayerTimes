package com.devsuda.luprayertimes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.util.Locale;

public class ExtAppsCaller {

    private static final String MASJID_ENOUR_APP_ID = "com.devsuda.lancasterprayertimes";
    private static final String GOOGLE_MAP_APP_URI = "com.google.android.apps.maps";
    private static final String FACEBOOK_APP_ID = "com.facebook.katana";

    private Context context;
    private PackageManager packageManager;
    private boolean appInstalled;
    private AlertDialog.Builder alertDialog;
    private LocationHelper locationAdaptor;
    double currentLocLat;
    double currentLocLon;
    double prayerRoomLat = 54.0116389;
    double prayerRoomLon = -2.787241;


    public ExtAppsCaller(MainActivity _mainActivity) {
        this.context = _mainActivity;
        locationAdaptor = new LocationHelper(_mainActivity);
    }

    public void gotoApp(int appId) {

        switch (appId) {
            case 1:
                if (isAppInstalled(MASJID_ENOUR_APP_ID)) {
                    Intent LaunchIntent = context.getPackageManager()
                            .getLaunchIntentForPackage("com.devsuda.lancasterprayertimes");
                    context.startActivity(LaunchIntent);
                } else {
                    actionToInstallApp(appId);
                }
                break;

            case 2:
                if (isAppInstalled(GOOGLE_MAP_APP_URI)) {
                    currentLocLat = locationAdaptor.getLatitude();
                    currentLocLon = locationAdaptor.getLongitude();

                    String uri = String.format(Locale.ENGLISH,
                            "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)",
                            prayerRoomLat, prayerRoomLon, "My current location",
                            currentLocLat, currentLocLon, "Prayer Room");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

                    intent.setPackage("com.google.android.apps.maps");

                    context.startActivity(intent);
                } else {
                    actionToInstallApp(appId);
                }
                break;

            case 3:
                if (isAppInstalled(FACEBOOK_APP_ID)) {
                    Toast.makeText(context, "Openning Facebook App", Toast.LENGTH_SHORT).show();

                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/121498887937290"));
                        context.startActivity(intent);
                    } catch (Exception e) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/121498887937290")));
                    }
                } else {
                    actionToInstallApp(appId);
                }
                break;
        }
    }



    public boolean isAppInstalled(String uri) {
        packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            appInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            appInstalled = false;
        }
        return appInstalled;
    }

    public void actionToInstallApp(int appId) {
        alertDialog = new AlertDialog.Builder(context);

        switch (appId) {
            case 1:
                alertDialog.setMessage("\nLancaster Prayer-Times App isn't installed...\nDo you want to install it now?");

                alertDialog.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                            .parse("market://details?id="
                                                    + "com.devsuda.luprayertimes")));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    context.startActivity(new Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("http://play.google.com/store/apps/details?id"
                                                    + "com.devsuda.luprayertimes")));
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
                break;

            case 2:

                String uri = "https://www.google.co.uk/maps/place/54%C2%B000'41.9%22N+2%C2%B047'11.4%22W/@54.0116389,-2.787241,18z/data=!3m1!4b1!4m5!3m4!1s0x0:0x0!8m2!3d54.0116389!4d-2.7865";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);

                alertDialog.show();

                break;

            case 3:
                Toast.makeText(context, "Openning Facebook on a browser", Toast.LENGTH_SHORT).show();
                String url = "https://www.facebook.com/LancsIsoc";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
                break;
        }

    }
}
