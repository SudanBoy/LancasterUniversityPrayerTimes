package com.devsuda.luprayertimes;

import android.os.CountDownTimer;
import android.widget.TableRow;
import android.widget.TextView;

public class DisplayCountdowns {

    public MainActivity mainActivity;
    private static final int igamaCountDownId = 1;
    private static final int azanCountDownId = 2;
    private static final int maghribCountDownId = 3;
    private NotiAdaptor notiAdaptor;

    public DisplayCountdowns(MainActivity _mainActivity) {
        this.mainActivity = _mainActivity;
        this.notiAdaptor = new NotiAdaptor(_mainActivity);
    }

    public void countdown(long timeToNextAzanOrIgamaِ, int countDownId) {

        final TextView hourOfIgamaCdTv = (TextView) mainActivity.findViewById(R.id.hourOfIgamaCdTv);
        final TextView minOfIgamaCdTv = (TextView) mainActivity.findViewById(R.id.minOfIgamaCdTv);
        final TextView secOfIgamaCdTv = (TextView) mainActivity.findViewById(R.id.secOfIgamaCdTv);

        final TextView minOfAzanCdTv = (TextView) mainActivity.findViewById(R.id.minOfAzanCdTv);
        final TextView minOfAzanCdSign = (TextView) mainActivity.findViewById(R.id.minOfAzanCdSign);
        final TableRow azanCdBg = (TableRow) mainActivity.findViewById(R.id.azanCdBg);

        long timeToNextAzan = 0;
        long timeToNextIgamaِ = 0;
        long timeToMagribِ = 0;

        final int azanNotiId = 1;
        final int igamaNotiId = 2;

        int countDownInterval = 1000;

        switch (countDownId) {
            case azanCountDownId:
                timeToNextAzan = timeToNextAzanOrIgamaِ;
                break;
            case igamaCountDownId:
                timeToNextIgamaِ = timeToNextAzanOrIgamaِ;
                break;
            case maghribCountDownId:
                timeToMagribِ = timeToNextAzanOrIgamaِ;
                break;
        }

        if (countDownId == azanCountDownId) {
            new CountDownTimer(timeToNextAzan, countDownInterval) {

                public void onTick(long millisUntilFinished) {
                    String hourOfAzanCd = String.format("%02d", (millisUntilFinished / (60 * 60 * 1000)) % 24);
                    if (hourOfAzanCd.equals("00")) {
                        String minOfAzanCd = String.format("%02d", (millisUntilFinished / (60 * 1000)) % 60);
                        if (minOfAzanCd.equals("00")) {
                            minOfAzanCdTv.setText("Azan is calling");
                        } else {
                            minOfAzanCdTv.setText(minOfAzanCd);
                            minOfAzanCdSign.setText(" minutes to Azan\t");
                        }

                    } else {
                        minOfAzanCdSign.setText("Azan not yet called");
                    }
                }

                public void onFinish() {
                    //notiAdaptor.showNotification(azanNotiId);
                    minOfAzanCdTv.setText("Azan has been called ");
                }
            }.start();

        } else if (countDownId == igamaCountDownId) {
            new CountDownTimer(timeToNextIgamaِ, countDownInterval) {

                public void onTick(long millisUntilFinished) {
                    String hourOfIgamaCd = String.format("%02d", (millisUntilFinished / (60 * 60 * 1000)) % 24);
                    hourOfIgamaCdTv.setText("\t" + hourOfIgamaCd);
                    String minOfIgamaCd = String.format("%02d", (millisUntilFinished / (60 * 1000)) % 60);
                    minOfIgamaCdTv.setText(minOfIgamaCd);
                    String secOfIgamaCd = String.format("%02d", (millisUntilFinished / 1000) % 60);
                    secOfIgamaCdTv.setText(secOfIgamaCd);
                }

                public void onFinish() {
                    //notiAdaptor.showNotification(igamaNotiId);
                    minOfAzanCdTv.setText("Jamaa started, hurry up!");
                }
            }.start();

        } else if (countDownId == maghribCountDownId) {
            new CountDownTimer(timeToMagribِ, countDownInterval) {

                public void onTick(long millisUntilFinished) {

                    String hmsH = String.format("%02d", (millisUntilFinished / (60 * 60 * 1000)) % 24);
                    if (hmsH.equals("00")) {

                        String minOfMagribCd = String.format("%02d", (millisUntilFinished / (60 * 1000)) % 60);
                        if (minOfMagribCd.equals("00")) {
                            minOfAzanCdTv.setText("Mind the Igama for Magrib");
                        } else {
                            minOfAzanCdTv.setText(minOfMagribCd);
                            minOfAzanCdSign.setText(" minutes to azan\t");
                        }

                    } else {
                        minOfAzanCdSign.setText("Azan not yet called");
                    }
                }

                public void onFinish() {
                    //notiAdaptor.showNotification(igamaNotiId);
                    minOfAzanCdTv.setText("Azan has been called ");
                }
            }.start();
        }
    }
}
