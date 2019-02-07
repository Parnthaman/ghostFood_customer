package com.ghostFood.acitivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.onesignal.OneSignal;
import com.ghostFood.R;
import com.ghostFood.util.Constants;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;

import org.jsoup.Jsoup;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //SessionManager.AppProperties.getInstance().setAppDirection(ConstantsInternal.AppDirection.RTL);
       // SessionManager.AppProperties.getInstance().setAppDirection(ConstantsInternal.AppDirection.LTR);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.tech.farmcity.customer",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                SessionManager.User.getInstance().setDeviceID(userId);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Splash.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) + ContextCompat
                    .checkSelfPermission(Splash.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale
                        (Splash.this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale
                                (Splash.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Snackbar.make(findViewById(android.R.id.content),
                            Constants.PleaseGrantPermissions,
                            Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(Splash.this,
                                            new String[]{Manifest.permission
                                                    .ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                            999);
                                }
                            }).show();
                } else {
                    ActivityCompat.requestPermissions(Splash.this,
                            new String[]{Manifest.permission
                                    .ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            999);
                }
            } else {
                //Call whatever you want
                LoadSplash();
            }
        } else {
            LoadSplash();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 999: {
                if ((grantResults.length > 0) && (grantResults[0] +
                        grantResults[1]) == PackageManager.PERMISSION_GRANTED) {
                    LoadSplash();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), Constants.EnablePermissionsFromSettings,
                            Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    intent.setData(Uri.parse("package:" + getPackageName()));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(intent);
                                }
                            }).show();
                }
                return;
            }
        }
    }



    void LoadSplash() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                new GetVersionCode().execute();


            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    private class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + Splash.this.getPackageName() + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            String currentVersion = null;
            try {
                currentVersion = Splash.this.getPackageManager().getPackageInfo(Splash.this.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (onlineVersion != null && currentVersion != null && !onlineVersion.isEmpty() && !currentVersion.isEmpty()) {
                if (!currentVersion.equals(onlineVersion)) {
                   ShowUpdateDialog();
                } else {
                    LoadNext();
                }
            } else {
                LoadNext();
            }
        }
    }

    void LoadNext()
    {
       // MyActivity.getInstance().LoadTour(Splash.this);
        MyActivity.getInstance().LoadHome(Splash.this);
    }

    private void ShowUpdateDialog() {
        final Dialog dialog = new Dialog(Splash.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_updateapp);
        dialog.setCancelable(false);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);

        Button btnUpdate= (Button) dialog.findViewById(R.id.btnUpdate);
        Button btnLater= (Button) dialog.findViewById(R.id.btnLater);

        FontFunctions.getInstance().FontSketchBold(Splash.this,tvTitle);
        FontFunctions.getInstance().FontBerlin(Splash.this,tvMessage);
        FontFunctions.getInstance().FontSketchBold(Splash.this,btnUpdate);
        FontFunctions.getInstance().FontSketchBold(Splash.this,btnLater);

        tvTitle.setText(Constants.NewVersion);
        tvMessage.setText(Constants.NewVersionMessage);
        btnUpdate.setText(Constants.Update);
        btnLater.setText(Constants.Later);

        btnLater.setAllCaps(false);
        btnUpdate.setAllCaps(false);

        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadNext();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="+ Splash.this.getPackageName()));
                startActivity(intent);
            }
        });


        dialog.show();
    }


}
