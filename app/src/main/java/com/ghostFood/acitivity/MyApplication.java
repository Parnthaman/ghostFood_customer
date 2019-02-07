package com.ghostFood.acitivity;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.onesignal.OneSignal;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import co.paystack.android.PaystackSdk;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = getBaseContext();
        Constants.initViews(context);
        PaystackSdk.initialize(getApplicationContext());
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        Fresco.initialize(context);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .twitterAuthConfig(new TwitterAuthConfig(ConstantsInternal.TwitterConsumerKey, ConstantsInternal.TwitterConsumerSecret))
                .debug(true)
                .build();
        Twitter.initialize(config);

        //Initialize Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        // Initialize Realm
        Realm.init(context);
        new RealmConfiguration.Builder()
                .name(ConstantsInternal.DBName.toLowerCase() + ".realm")
                .schemaVersion(42)
                .deleteRealmIfMigrationNeeded()
                .build();;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
