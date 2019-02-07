package com.ghostFood.util;
import android.app.Activity;
import android.content.SharedPreferences;

import com.ghostFood.acitivity.MyApplication;
import com.ghostFood.callback.CommonCallback;

import java.math.BigInteger;

public class SessionManager {
    private static SessionManager ourInstance = new SessionManager();
    private static String ONTABEEPREFS = "OnTabeePrefs";
    private static String ONTABEEPREFS_SETTINGS = "OnTabeePrefsSettings";

    public static SessionManager getInstance() {
        return ourInstance;
    }

    public SessionManager() {
    }

    public void Logout() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public String getLanguageCode() {
        SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
        return prefs.getString("LanguageCode", "");
    }

    public static class AppProperties {
        private static AppProperties ourInstance = new AppProperties();
        public static AppProperties getInstance() {
            return ourInstance;
        }

        public void setAppMode(ConstantsInternal.AppMode mAppMode) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS_SETTINGS, Activity.MODE_PRIVATE);
            pref.edit().putInt("AppMode", mAppMode.getValue()).apply();
        }

        public ConstantsInternal.AppMode getAppMode() {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS_SETTINGS, Activity.MODE_PRIVATE);
            Integer status = pref.getInt("AppMode", ConstantsInternal.AppMode.Prod.getValue());
            return ConstantsInternal.AppMode.fromInteger(status);
        }

        public void setLanguageCode( String mSelectLanguageCode) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("LanguageCode", (mSelectLanguageCode == null ? "" : mSelectLanguageCode)).apply();
        }

        public String getLanguageCode() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("LanguageCode", "es");
        }

        public void setAppDirection( ConstantsInternal.AppDirection mDirection) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putInt("AppDirection", mDirection.getValue()).apply();
        }

        public ConstantsInternal.AppDirection getAppDirection() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            Integer status = prefs.getInt("AppDirection", ConstantsInternal.AppDirection.LTR.getValue());
            return ConstantsInternal.AppDirection.fromInteger(status);
        }

        public void setCurrencySymbol( String mDirection) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("CurrencySymbol", (mDirection == null ? "" : mDirection)).apply();
        }

        public String getCurrencySymbol() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("CurrencySymbol", ConstantsInternal.CurrencyCode);
        }

        public void setDomain( String mDomain) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("DomainName", (mDomain == null ? "" : mDomain)).apply();
        }

        public String getDomain() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("DomainName", "abc");
        }
    }

    public static class Cart {
        private static Cart ourInstance = new Cart();
        public static Cart getInstance() {
            return ourInstance;
        }

        public void setOrderType( Integer mOrderType) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putInt("CartOrderType", (mOrderType == null ? ConstantsInternal.OrderType.Delivery.getValue() : mOrderType)).apply();
        }

        public Integer getOrderType() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getInt("CartOrderType", ConstantsInternal.OrderType.Delivery.getValue());
        }

        public void setAddress(String mAddress) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("CartAddress", (mAddress == null ? "" : mAddress)).apply();
        }

        public String getAddress() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("CartAddress", "");
        }

        public void setAddressKey( String mAddressKey) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("CartAddressKey", (mAddressKey == null ? "" : mAddressKey)).apply();
        }

        public String getAddressKey() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("CartAddressKey", "");
        }

        public void setLatitude( String mLatitude) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("CartLatitude", (mLatitude == null ? "" : mLatitude)).apply();
        }

        public String getLatitude() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("CartLatitude", "");
        }

        public void setLongitude( String mLongitude) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("CartLongitude", (mLongitude == null ? "" : mLongitude)).apply();
        }

        public String getLongitude() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("CartLongitude", "");
        }

        public void setBranchKey( String mBranchKey) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("CartBranchKey", (mBranchKey == null ? "" : mBranchKey)).apply();
        }

        public String getBranchKey() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("CartBranchKey", "");
        }
    }

    public static class User {
        private static User ourInstance = new User();
        public static User getInstance() {
            return ourInstance;
        }

        public void setDetails(String userKey, String userFirstName, String userLastName, String username, String countryCode,
                               BigInteger mobileNumber, String emailAddress, String profileImage, String userAccessToken, final CommonCallback.Listener callback) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("UserKey", (userKey == null ? "" : userKey)).apply();
            pref.edit().putString("UserFirstName", (userFirstName == null ? "" : userFirstName)).apply();
            pref.edit().putString("UserLastName", (userLastName == null ? "" : userLastName)).apply();
            pref.edit().putString("UserUsername", (username == null ? "" : username)).apply();
            pref.edit().putString("UserCountryCode", (countryCode == null ? "" : countryCode)).apply();
            pref.edit().putString("UserMobileNumber", (mobileNumber == null ? 0 : mobileNumber).toString()).apply();
            pref.edit().putString("UserEmailAddress", (emailAddress == null ? "" : emailAddress)).apply();
            pref.edit().putString("UserProfileImage", (profileImage == null ? "" : profileImage)).apply();
            pref.edit().putString("UserAccessToken", (userAccessToken == null ? "" : userAccessToken)).apply();

            callback.onSuccess();

        }

        public String getAccessToken() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("UserAccessToken", "");
        }

        public void setFirstName(String UserFirstName) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("UserFirstName", (UserFirstName == null ? "" : UserFirstName)).apply();
        }

        public void setLastName(String UserLastName) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("UserLastName", (UserLastName == null ? "" : UserLastName)).apply();
        }

        public void setMobileNumber(String mobileNumber) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("UserMobileNumber", (mobileNumber == null ? "" : mobileNumber)).apply();
        }

        public void setEmailAddress(String emailAddress) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("UserEmailAddress", (emailAddress == null ? "" : emailAddress)).apply();
        }

        public String setKey() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("UserKey", "");
        }

        public String getKey() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("UserKey", "");
        }

        public void setDeviceID(String deviceID) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("DeviceID", (deviceID == null ? "" : deviceID)).apply();
        }

        public String getDeviceID() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("DeviceID", "");
        }

        public String getFirstName() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("UserFirstName", "");
        }

        public String getLastName() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("UserLastName", "");
        }

        public String getUsername() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("UserUsername", "");
        }

        public String getMobileCode() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("UserCountryCode", "");
        }

        public String getMobile() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("UserMobileNumber", "");
        }

        public String getEmail() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("UserEmailAddress", "");
        }



        public void setProfileImage(String profileImage) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("UserProfileImage", (profileImage == null ? "" : profileImage)).apply();
        }

        public String getProfileImage() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("UserProfileImage", "");
        }
    }

    public static class Spin2Win {
        private static Spin2Win ourInstance = new Spin2Win();
        public static Spin2Win getInstance() {
            return ourInstance;
        }

        public void setJson( String mSelectLanguageCode) {
            SharedPreferences pref = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            pref.edit().putString("Spin2WinJson", (mSelectLanguageCode == null ? "" : mSelectLanguageCode)).apply();
        }

        public String getJson() {
            SharedPreferences prefs = MyApplication.context.getSharedPreferences(ONTABEEPREFS, Activity.MODE_PRIVATE);
            return prefs.getString("Spin2WinJson", "en");
        }
    }

}