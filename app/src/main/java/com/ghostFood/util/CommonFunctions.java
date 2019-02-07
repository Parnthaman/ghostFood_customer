package com.ghostFood.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ghostFood.acitivity.MyApplication;
import com.ghostFood.util.slidingtab.SlidingTabLayout;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.sdsmdg.tastytoast.TastyToast;
import com.sloop.fonts.FontsManager;
import com.squareup.picasso.Picasso;
import com.ghostFood.R;
import com.ghostFood.api.ProfileImageAddApi;
import com.ghostFood.api.ProfileImageRemoveApi;
import com.ghostFood.api.UserRegistrationExistsApi;
import com.ghostFood.callback.CommonCallback;
import com.ghostFood.callback.ProfileCallback;
import com.ghostFood.db.Items;
import com.ghostFood.db.SpecialItems;
import com.ghostFood.events.EBProfileImage;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Math.floor;

public class CommonFunctions {


    public static String fontPath = "fonts/appfont.otf";


    private static CommonFunctions ourInstance = new CommonFunctions();
    static DecimalFormat formatter = new DecimalFormat("#,##0.00");

    public static CommonFunctions getInstance() {
        return ourInstance;
    }

    private CommonFunctions() {
    }

    public String LTRRTLValue(String value1, String value2) {
        if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.LTR) {
            return value1 + value2;
        } else if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL) {
            return value2 + value1;
        } else {
            return "";
        }
    }

    public void ChangeFontActivity(Activity mActivity) {
        FontsManager.initFormAssets(MyApplication.context, fontPath);
        FontsManager.changeFonts(mActivity);
    }

    public boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public void ChangeFontFragment(View view) {
        FontsManager.initFormAssets(MyApplication.context, fontPath);
        FontsManager.changeFonts(view);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    public void changeTabsFont(TabLayout tabLayout) {
        Typeface face = Typeface.createFromAsset(MyApplication.context.getAssets(), CommonFunctions.fontPath);

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(face);
                }
            }
        }
    }

    public void changeTabsFont(Context mContext, SlidingTabLayout tabLayout) {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            Typeface mTypeface = Typeface.createFromAsset(mContext.getAssets(), CommonFunctions.fontPath);
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(mTypeface, Typeface.NORMAL);
                }
            }
        }
    }

    public void changeTabsFont(Context mContext, TabLayout tabLayout) {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            Typeface mTypeface = Typeface.createFromAsset(mContext.getAssets(), CommonFunctions.fontPath);
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(mTypeface, Typeface.NORMAL);
                }
            }
        }
    }


    public void ChangeDirection(Activity mActivity) {
        Constants.initViews(mActivity);
        if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mActivity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mActivity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }
    }

    public String getIntORFloat(Double itemPrice) {

        if (floor(itemPrice) == itemPrice) {
            Integer value = itemPrice.intValue();
            return String.valueOf(value);
        } else {
            return String.format("%." + ConstantsInternal.DeciamlDigitsAfterDot + "f", itemPrice);
        }
    }

    public void ChangeBackgroundToImage(Context mContext, DrawerLayout drawer, boolean b) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (b) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                drawer.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.img_background_new));
            } else {
//                drawer.setBackground(mContext.getResources().getDrawable(R.drawable.img_background_new));
            }
        } else {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                drawer.setBackgroundDrawable(mContext.getResources().getColor(R.color.bg_color));
            } else {
//                drawer.setBackground(mContext.getResources().getColor(R.color.bg_color));
            }
        }
    }


    public static class DeciamlDigitsAfterDotValue {
        public static String get(String value) {
            value = value == null ? "" : value;
            value = value.trim() == "" ? ConstantsInternal.CurrencyZero : value.trim();
            return String.format("%." + ConstantsInternal.DeciamlDigitsAfterDot + "f", Double.parseDouble(value));
        }

        public static String get(Double value) {
            return String.format("%." + ConstantsInternal.DeciamlDigitsAfterDot + "f", value);
        }

        public static String getWithCurrencyCode(String value) {
//            value = value == null ? "" : value;
//            value = value.trim().equalsIgnoreCase("") ? ConstantsInternal.CurrencyZero : value.trim();
            if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.LTR) {
                return ConstantsInternal.CurrencyCode + " " + formatter.format(Double.valueOf(value));/*String.format("%." + ConstantsInternal.DeciamlDigitsAfterDot + "f", Double.parseDouble(value)*/
            } else {
                return /*String.format("%." + ConstantsInternal.DeciamlDigitsAfterDot + "f", Double.parseDouble(value))*/ formatter.format(Double.valueOf(value)) + " " + ConstantsInternal.CurrencyCode;
            }
        }

        public static String getWithCurrencyCode(Double value) {
            if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.LTR) {
                String yourFormattedString = formatter.format(value);
//                System.out.println(NumberFormat.getNumberInstance(Locale.US).format(value));
                return ConstantsInternal.CurrencyCode + " " + yourFormattedString/*+ String.format("%." + ConstantsInternal.DeciamlDigitsAfterDot + "f", value)*/;
            } else {
                return /*tring.format("%." + ConstantsInternal.DeciamlDigitsAfterDot + "f", value)*/ formatter.format(value) + " " + ConstantsInternal.CurrencyCode;
            }
        }

        public static String getWithCurrencyCodeOriginal(String price) {
            String value = CommonFunctions.getInstance().getIntORFloat(Double.parseDouble(price));
            value = value == null ? "" : value;
            value = value.trim().equalsIgnoreCase("") ? ConstantsInternal.CurrencyZero : value.trim();
            if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.LTR) {
                return ConstantsInternal.CurrencyCode + " " + formatter.format(Double.valueOf(price));
            } else {
                return formatter.format(Double.valueOf(price)) + " " + ConstantsInternal.CurrencyCode;
            }
        }

        public static String getWithCurrencyCodeOriginal(Double value) {
            String price = CommonFunctions.getInstance().getIntORFloat(value);
            if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.LTR) {
                return ConstantsInternal.CurrencyCode + " " + formatter.format(value);
            } else {
                return formatter.format(value) + " " + ConstantsInternal.CurrencyCode;
            }
        }

    }

    public void ChangeToolbarLanguage(Activity mActivity, Toolbar mToolbar) {

        final int childCount = getAllViews(mToolbar).size();
        for (int i = 0; i < childCount; i++) {
            View view = getAllViews(mToolbar).get(i);

            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                if (imageView.getId() == R.id.tlbar_back) {
                    if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL) {
                        imageView.setImageResource(R.drawable.ic_rightarrow);
                    } else {
                        imageView.setImageResource(R.drawable.ic_leftarrow);
                    }
                }
            } else if (view instanceof TextView) {
                /*TextView textView = (TextView) view;
                if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL) {
                    textView.setGravity(Gravity.CENTER | Gravity.RIGHT);
                } else {
                    textView.setGravity(Gravity.CENTER | Gravity.LEFT);
                }*/
            } else if (view instanceof EditText) {
                /*EditText editText = (EditText) view;
                if (SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.RTL) {
                    editText.setGravity(Gravity.CENTER | Gravity.RIGHT);
                } else {
                    editText.setGravity(Gravity.CENTER | Gravity.LEFT);
                }*/
            }

        }

    }

    private List<View> getAllViews(View v) {
        if (!(v instanceof ViewGroup) || ((ViewGroup) v).getChildCount() == 0) // It's a leaf
        {
            List<View> r = new ArrayList<View>();
            r.add(v);
            return r;
        } else {
            List<View> list = new ArrayList<View>();
            list.add(v); // If it's an internal node add itself
            int children = ((ViewGroup) v).getChildCount();
            for (int i = 0; i < children; ++i) {
                list.addAll(getAllViews(((ViewGroup) v).getChildAt(i)));
            }
            return list;
        }
    }

    public void ShowToolTip(Context context, View ivReceiveininfo, String disclaimer, String s) {

        final SimpleTooltip tooltip = new SimpleTooltip.Builder(context)
                .anchorView(ivReceiveininfo)
                .text(s)
                .gravity(Gravity.BOTTOM)
                .dismissOnOutsideTouch(false)
                .dismissOnInsideTouch(false)
                .modal(true)
                .arrowColor(Color.BLACK)
                .arrowHeight(20)
                .arrowWidth(20)
                .maxWidth(R.dimen._130sdp)
                .contentView(R.layout.custom_tooltip, R.id.tv_text)
                .focusable(true)
                .build();

        TextView ed = tooltip.findViewById(R.id.tvTitle);
        ed.setText(disclaimer);
        tooltip.show();

    }

    public void FinishActivityWithDelay(final Activity activity) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                activity.finish();
            }
        }, 1500);
    }

    public void ShowCartCount(TextView tvCartCount) {
        Realm realm = Realm.getDefaultInstance();
        int mItemsCount = realm.where(Items.class).findAll().size();
        int mSpecialItemsCount = realm.where(SpecialItems.class).findAll().size();
        if (mItemsCount + mSpecialItemsCount > 0) {
            tvCartCount.setVisibility(View.VISIBLE);
            tvCartCount.setText(String.valueOf(mItemsCount + mSpecialItemsCount));
        } else {
            tvCartCount.setVisibility(View.GONE);
        }
    }


    public void SetProfilePicture(SimpleDraweeView sdView) {
        if (!SessionManager.User.getInstance().getProfileImage().trim().isEmpty()) {

            try {

                Uri uri = Uri.parse(SessionManager.User.getInstance().getProfileImage().trim());

                int width = 200, height = 200;
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                        .disableDiskCache()
                        .setRequestPriority(Priority.HIGH)
                        .setResizeOptions(new ResizeOptions(width, height))
                        .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(sdView.getController())
                        .setImageRequest(request)
                        .build();
                sdView.setController(controller);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            sdView.setController(null);
        }
    }

    public void LoadImagePicasso(Context mContext, ImageView sdView, String urls) {
        {
            String BASE_URL = ApiConfiguration.BASE_URL_STAGING;
            if (SessionManager.AppProperties.getInstance().getAppMode() == ConstantsInternal.AppMode.Dev) {
                BASE_URL = ApiConfiguration.BASE_URL_DEV;
            } else if (SessionManager.AppProperties.getInstance().getAppMode() == ConstantsInternal.AppMode.Prod) {
                BASE_URL = ApiConfiguration.BASE_URL_PROD;
            }
            /*if (!urls.startsWith(BASE_URL))
                urls = BASE_URL + urls;*/
            if (!urls.isEmpty()) {
                Picasso
                        .with(mContext)
                        .load(urls)
                        .resize(300, 300) // resizes the image to these dimensions (in pixel)
                        .centerInside()
                        .into(sdView);
            }
        }

    }

    public void LoadImageByFresco(SimpleDraweeView sdView, String img_url) {


        try {
            Uri uri = Uri.parse(img_url);
            int width = 200, height = 200;
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .disableDiskCache()
                    .setRequestPriority(Priority.HIGH)
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(sdView.getController())
                    .setImageRequest(request)
                    .build();
            sdView.setController(controller);
            sdView.getHierarchy().setProgressBarImage(new CircleProgressDrawable());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void LoadImageByFrescoOld(SimpleDraweeView sdView, String img_url) {

//        if (ConstantsInternal.isEnableDummyImages) {
//            img_url = ConstantsInternal.mDummyImage;
//        }

        try {
            Uri uri = Uri.parse(img_url);
            int width = 200, height = 200;
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .disableDiskCache()
                    .setRequestPriority(Priority.HIGH)
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(sdView.getController())
                    .setImageRequest(request)
                    .build();
            sdView.setController(controller);
            sdView.getHierarchy().setProgressBarImage(new CircleProgressDrawable());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void LoadImageByFrescoNew(ImageView sdView, String url) {


        try {

            if (!ConstantsInternal.isEnableDummyImages) {
                String BASE_URL = ApiConfiguration.BASE_URL_STAGING;
                if (SessionManager.AppProperties.getInstance().getAppMode() == ConstantsInternal.AppMode.Dev) {
                    BASE_URL = ApiConfiguration.BASE_URL_DEV;
                } else if (SessionManager.AppProperties.getInstance().getAppMode() == ConstantsInternal.AppMode.Prod) {
                    BASE_URL = ApiConfiguration.BASE_URL_PROD;
                }
                if (!url.startsWith(BASE_URL))
                    url = BASE_URL + url;
            }

            Uri uri = Uri.parse(url);


            Resources r = MyApplication.context.getResources();
            int py = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, r.getDisplayMetrics());

            Glide.with(MyApplication.context)
                    .load(url)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .fitCenter()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                            return false;
                        }
                    })
                    .into(sdView);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void showFresco1(final Context context, final SimpleDraweeView imageView, final String imageUrl) {
        try {

            Uri uri = Uri.parse(imageUrl);
            imageView.setImageURI(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ShowSnackBar(Context context, String message) {
//        TastyToast.makeText(context, message, TastyToast.LENGTH_SHORT, TastyToast.ERROR);
        View parent = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);

        Snackbar snackbar;
        snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(MyApplication.context.getResources().getColor(R.color.gray_edtext));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(MyApplication.context.getResources().getColor(R.color.white));
        snackbar.show();
    }

    public void EmptyField(Context context, String message) {
        View parent = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar;
        String result = MessageFormat.format(Constants.CannotbeEmpty, message);
        snackbar = Snackbar.make(parent, result, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_edtext));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.show();
    }

    public void ValidField(Context context, String message) {
        View parent = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar;
        String result = MessageFormat.format(Constants.EnterAValidField, message);
        snackbar = Snackbar.make(parent, result, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_edtext));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.show();
    }

    public void PasswordMatch(Context context, String message1, String message2) {
        View parent = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar;
        String result = MessageFormat.format(Constants.Didnotmatch, message1, message2);
        snackbar = Snackbar.make(parent, result, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_edtext));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.show();
    }

    public void UploadProfile(final Context context, String filePath, final ProfileCallback.ImageListener imageListener) {
        CustomProgressDialog.getInstance().show(context);
        File file = new File(filePath);
        RequestBody fbody = RequestBody.create(MediaType.parse("image/jpg"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("profile_image", file.getName(), fbody);

        RequestBody customerKey = RequestBody.create(okhttp3.MultipartBody.FORM, SessionManager.User.getInstance().getKey());

        ProfileImageAddApi mProfileImageAddApi = ApiConfiguration.getInstance().getApiBuilder().create(ProfileImageAddApi.class);
        final Call<ProfileImageAddApi.ResponseProfileImageAdd> call = mProfileImageAddApi.Register(customerKey, body);
        call.enqueue(new Callback<ProfileImageAddApi.ResponseProfileImageAdd>() {
            @Override
            public void onResponse(Call<ProfileImageAddApi.ResponseProfileImageAdd> call, Response<ProfileImageAddApi.ResponseProfileImageAdd> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

                        SessionManager.User.getInstance().setProfileImage(response.body().getData().getProfileImage());

                        CommonFunctions.getInstance().ShowSnackBar(context, response.body().getMessage());
                        imageListener.onSuccess(response.body().getData().getProfileImage());

                        EventBus.getDefault().post(new EBProfileImage());
                        CustomProgressDialog.getInstance().dismiss();
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(context, response.message());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CustomProgressDialog.getInstance().dismiss();
                    CommonFunctions.getInstance().ShowSnackBar(context, response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileImageAddApi.ResponseProfileImageAdd> call, Throwable t) {
                CommonFunctions.getInstance().ShowSnackBar(context, t.toString());
                CustomProgressDialog.getInstance().dismiss();
            }
        });
    }


    public void RemoveProfileImage(final Context context, final ProfileCallback.ImageListener callback) {
        CustomProgressDialog.getInstance().show(context);
        ProfileImageRemoveApi mProfileImageRemoveApi = ApiConfiguration.getInstance().getApiBuilder().create(ProfileImageRemoveApi.class);
        final Call<ProfileImageRemoveApi.ResponseProfileImageRemove> call = mProfileImageRemoveApi.Remove(SessionManager.User.getInstance().getKey());
        call.enqueue(new Callback<ProfileImageRemoveApi.ResponseProfileImageRemove>() {
            @Override
            public void onResponse(Call<ProfileImageRemoveApi.ResponseProfileImageRemove> call, Response<ProfileImageRemoveApi.ResponseProfileImageRemove> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();
                        SessionManager.User.getInstance().setProfileImage("");
                        CommonFunctions.getInstance().ShowSnackBar(context, response.body().getMessage());
                        EventBus.getDefault().post(new EBProfileImage());
                        callback.onSuccess("");
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(context, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CustomProgressDialog.getInstance().dismiss();
                    CommonFunctions.getInstance().ShowSnackBar(context, response.message());
                }

            }

            @Override
            public void onFailure(Call<ProfileImageRemoveApi.ResponseProfileImageRemove> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(context, t.toString());
            }
        });
    }

    public void HideSoftKeyboard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void isUserExists(final Context context, ConstantsInternal.RegistrationType registrationType, String key, final CommonCallback.Listener callback) {

        CustomProgressDialog.getInstance().show(context);
        UserRegistrationExistsApi mUserRegistrationExistsApi = ApiConfiguration.getInstance().getApiBuilder().create(UserRegistrationExistsApi.class);
        final Call<UserRegistrationExistsApi.ResponseUserRegistrationExists> call = mUserRegistrationExistsApi.Exists(registrationType.getValue(), key, SessionManager.User.getInstance().getDeviceID(), "1");
        call.enqueue(new Callback<UserRegistrationExistsApi.ResponseUserRegistrationExists>() {
            @Override
            public void onResponse(Call<UserRegistrationExistsApi.ResponseUserRegistrationExists> call, Response<UserRegistrationExistsApi.ResponseUserRegistrationExists> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();

                        SessionManager.User.getInstance().setDetails(
                                response.body().getData().getUserKey(),
                                response.body().getData().getFirstName(),
                                response.body().getData().getLastName(),
                                response.body().getData().getUsername(),
                                response.body().getData().getCountryCode(),
                                response.body().getData().getMobileNumber(),
                                response.body().getData().getEmailAddress(),
                                response.body().getData().getProfileImage(),
                                response.body().getData().getAccessToken(),
                                new CommonCallback.Listener() {
                                    @Override
                                    public void onSuccess() {
                                        callback.onSuccess();
                                    }

                                    @Override
                                    public void onFailure() {
                                        callback.onFailure();
                                    }
                                }
                        );
                    } else {
                        callback.onFailure();
                        CommonFunctions.getInstance().ShowSnackBar(context, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    callback.onFailure();
                    CommonFunctions.getInstance().ShowSnackBar(context, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<UserRegistrationExistsApi.ResponseUserRegistrationExists> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                callback.onFailure();
                CommonFunctions.getInstance().ShowSnackBar(context, t.toString());
            }
        });

    }
}