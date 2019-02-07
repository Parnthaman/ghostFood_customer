package com.ghostFood.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ghostFood.acitivity.AddAddress;
import com.ghostFood.acitivity.AddAddressMap;
import com.ghostFood.acitivity.AddressAct;
import com.ghostFood.acitivity.Cart;
import com.ghostFood.acitivity.CategoryListing;
import com.ghostFood.acitivity.ChangePassword;
import com.ghostFood.acitivity.CheckOut;
import com.ghostFood.acitivity.CouponDetails;
import com.ghostFood.acitivity.ForgotPassword;
import com.ghostFood.acitivity.Home;
import com.ghostFood.acitivity.ItemDetails;
import com.ghostFood.acitivity.ItemListing;
import com.ghostFood.acitivity.LoginAct;
import com.ghostFood.acitivity.OrderConfirmation;
import com.ghostFood.acitivity.OrderDetails;
import com.ghostFood.acitivity.OrderStatus;
import com.ghostFood.acitivity.PickupLocations;
import com.ghostFood.acitivity.PlayToEat;
import com.ghostFood.acitivity.Registraion;
import com.ghostFood.acitivity.Splash;
import com.ghostFood.acitivity.TermsAndConditionAct;
import com.ghostFood.acitivity.TourActivity;
import com.ghostFood.acitivity.TrackOrderActivity;
import com.ghostFood.payment.OnlinePayment;
import com.ghostFood.payment.WebviewOnlinePayment;


public class MyActivity {
    private static MyActivity ourInstance = new MyActivity();

    public static MyActivity getInstance() {
        return ourInstance;
    }

    private MyActivity() {
    }

    public void ForgotPassword(Context context) {
        Intent in = new Intent(context, ForgotPassword.class);
        context.startActivity(in);
    }

    public void UserAccount(Context context) {
        Intent in = new Intent(context, Registraion.class);
        context.startActivity(in);
    }


    public void Home(Context context) {
        Intent in = new Intent(context, Home.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(in);
    }


    public void ProfileChangePassword(Context context) {
        Intent in = new Intent(context, ChangePassword.class);
        context.startActivity(in);
    }

    public void PlayToEat(Context context, Bundle mBundle) {
        Intent in = new Intent(context, PlayToEat.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        context.startActivity(in);
    }

    public void TrackOrder(Context context, Bundle mBundle) {
        Intent in = new Intent(context, TrackOrderActivity.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        context.startActivity(in);
    }

    public void CategoryListing(Context context, Bundle mBundle) {
        Intent in = new Intent(context, CategoryListing.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        context.startActivity(in);
    }

    public void ItemListing(Context context, Bundle mBundle) {
        Intent in = new Intent(context, ItemListing.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        context.startActivity(in);
    }

    public void AddAddressMap(Context context, Bundle mBundle) {
        Intent in = new Intent(context, AddAddressMap.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        context.startActivity(in);
    }

    public void AddAddress(Context mContext, Bundle mBundle) {
        Intent in = new Intent(mContext, AddAddress.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        mContext.startActivity(in);
    }

    public void PickupLocations(Context mContext) {
        Intent in = new Intent(mContext, PickupLocations.class);
        mContext.startActivity(in);
    }

    public void ItemDetails(Context mContext, Bundle mBundle) {
        Intent in = new Intent(mContext, ItemDetails.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        mContext.startActivity(in);
    }

    public void OnlinePayment(Context mContext, Bundle mBundle) {
        Intent in = new Intent(mContext, OnlinePayment.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        mContext.startActivity(in);
    }

    public void WebviewOnlinePayment(Context mContext, Bundle mBundle) {
        Intent in = new Intent(mContext, WebviewOnlinePayment.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        mContext.startActivity(in);
    }

    public void OrderDetails(Context mContext, Bundle mBundle) {
        Intent in = new Intent(mContext, OrderDetails.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        mContext.startActivity(in);
    }

    public void CouponDetails(Context mContext, Bundle mBundle) {
        Intent in = new Intent(mContext, CouponDetails.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        mContext.startActivity(in);
    }

    public void LoadHome(Activity mContext) {
        Intent mainIntent = new Intent(mContext, Home.class);
        mContext.startActivity(mainIntent);
        mContext.finish();
    }

    public void Cart(Activity mContext, Bundle mBundle) {
        Intent in = new Intent(mContext, Cart.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        mContext.startActivity(in);
    }

    public void ChooseAddress(Context mContext) {
        Intent in = new Intent(mContext, AddressAct.class);
        mContext.startActivity(in);
    }

    public void LoginAct(Context mContext) {
        Intent in = new Intent(mContext, LoginAct.class);
        mContext.startActivity(in);
    }

    public void AddressAct(Context mContext) {
        Intent in = new Intent(mContext, AddressAct.class);
        mContext.startActivity(in);
    }

    public void Checkout(Context mContext, Bundle mBundle) {
        Intent in = new Intent(mContext, CheckOut.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        mContext.startActivity(in);
    }

    public void OrderConfirmation(Context mContext, Bundle mBundle) {
        Intent in = new Intent(mContext, OrderConfirmation.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        mContext.startActivity(in);
    }

    public void TermsAndCondition(Context mContext) {
        Intent in = new Intent(mContext, TermsAndConditionAct.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(in);
    }


    public void Spalsh(Context mContext) {
        Intent in = new Intent(mContext, Splash.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(in);
    }

    public void ItemListingGrid(Context mContext) {
        Bundle mBundle = new Bundle();
        Intent in = new Intent(mContext, ItemListing.class);
//        mBundle.putSerializable("category", mCategory);
//        mBundle.putSerializable("item", mItem);
//        in.putExtras(mBundle);
        mContext.startActivity(in);
    }

    public void LoadTour(Activity mContext) {
        Intent in = new Intent(mContext, TourActivity.class);
        mContext.finish();
        mContext.startActivity(in);
    }

    public void OrderStatus(Context mContext, Bundle mBundle) {
        Intent in = new Intent(mContext, OrderStatus.class);
        if (mBundle != null) {
            in.putExtras(mBundle);
        }
        mContext.startActivity(in);
    }
}
