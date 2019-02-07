package com.ghostFood.acitivity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fujiyuu75.sequent.Animation;
import com.fujiyuu75.sequent.Sequent;
import com.ghostFood.R;
import com.ghostFood.adapter.NavigationMenuAdapter;
import com.ghostFood.callback.ProfileCallback;
import com.ghostFood.events.EBLogin;
import com.ghostFood.events.EBProfile;
import com.ghostFood.events.EBProfileImage;
import com.ghostFood.fragment.Address;
import com.ghostFood.fragment.Coupon;
import com.ghostFood.fragment.Help;
import com.ghostFood.fragment.HomePage;
import com.ghostFood.fragment.Language;
import com.ghostFood.fragment.Login;
import com.ghostFood.fragment.LoyaltyPoints;
import com.ghostFood.fragment.Orders;
import com.ghostFood.fragment.Profile;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.ProfileImage;
import com.ghostFood.util.SessionManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EventBus myEventBus = EventBus.getDefault();

    ArrayList<String> listDataHeader;
    ArrayList<Integer> listDataHeaderIcons;
    HashMap<String, List<String>> listDataChild;
    ExpandableListView expListView;
    NavigationMenuAdapter listAdapter;
    public static Context mContext;
    DrawerLayout drawer;
    ImageView iv_add;
    ImageView iv_profilepicture;

    public static final int SELECT_PICTURE = 1;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.tlbar_logo)
    ImageView tlbarLogo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.left_drawer)
    ExpandableListView leftDrawer;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.flAdd)
    FrameLayout flAdd;
    @BindView(R.id.ivQR)
    ImageView ivQR;
    private Dialog dialog;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    private static final int CAMERA_REQUEST = 1888;
    String imagePath1 = "";
    boolean doubleBackToExitPressedOnce = false;

    TextView tvUsername, tv_email;
    TextView ivEditProfile;
    ImageView ivChoosePic;
    LinearLayout layout, layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(Home.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(Home.this, toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = getApplicationContext();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        toggle.setDrawerIndicatorEnabled(false);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_nav_icon, Home.this.getTheme());
        final DrawerLayout drawers = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listAdapter.notifyDataSetChanged();
                Sequent.origin(layout1).duration(300).anim(Home.this, Animation.BOUNCE_IN).start();
                //Sequent.origin(layout).duration(500).anim(Home.this, Animation.BOUNCE_IN).start();
                if (drawers.isDrawerVisible(GravityCompat.START)) {
                    drawers.closeDrawer(GravityCompat.START);
                } else {
                    drawers.openDrawer(GravityCompat.START);
                }
                CommonFunctions.getInstance().HideSoftKeyboard(Home.this);
            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        enableExpandableList();

        initString();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomePage()).commit();
        CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, true);
        tlbarText.setVisibility(View.VISIBLE);
        tlbarLogo.setVisibility(View.GONE);

    }

    private void initString() {



        /*if(SessionManager.AppProperties.getInstance().getAppDirection() == ConstantsInternal.AppDirection.LTR)
        {
            tlbarText.setGravity(Gravity.LEFT);
        }
        else
        {
            tlbarText.setGravity(Gravity.RIGHT);
        }*/

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            CommonFunctions.getInstance().ShowSnackBar(Home.this, Constants.PleaseclickBACKagaintoexit);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void enableExpandableList() {


        listDataHeader = new ArrayList<String>();
        listDataHeaderIcons = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();
        expListView = (ExpandableListView) findViewById(R.id.left_drawer);

        expListView.setGroupIndicator(null);
        expListView.setChildIndicator(null);
        expListView.setChildDivider(getResources().getDrawable(android.R.color.transparent));
        expListView.setDivider(getResources().getDrawable(android.R.color.transparent));
        expListView.setDividerHeight(0);

        LayoutInflater myinflater = getLayoutInflater();
        ViewGroup myHeader = (ViewGroup) myinflater.inflate(R.layout.nav_header_home, expListView, false);
        expListView.addHeaderView(myHeader, null, false);

        CommonFunctions.getInstance().ChangeFontFragment(myHeader);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        iv_profilepicture = (ImageView) myHeader.findViewById(R.id.iv_profilepicture);

        if (!SessionManager.User.getInstance().getKey().trim().isEmpty()) {
            //CommonFunctions.getInstance().SetProfilePicture(iv_profilepicture);
        }


        ivChoosePic = (ImageView) myHeader.findViewById(R.id.iv_choosepicture);
        layout = (LinearLayout) myHeader.findViewById(R.id.layout);
        layout1 = (LinearLayout) myHeader.findViewById(R.id.layout1);

        Sequent.origin(layout).anim(Home.this, R.anim.overshoot).start();

        ivChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ShowProfilePictureDialog();

                drawer.closeDrawer(GravityCompat.START);
            }
        });
        iv_profilepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ShowProfilePictureDialog();

                drawer.closeDrawer(GravityCompat.START);
            }
        });

        ivEditProfile = (TextView) myHeader.findViewById(R.id.tvEditProfile);
        ivEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Profile()).commit();
                tlbarText.setVisibility(View.VISIBLE);
                tlbarLogo.setVisibility(View.GONE);
                tlbarText.setText(Constants.EditProfile);
                CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, true);

                drawer.closeDrawer(GravityCompat.START);
            }
        });
        tvUsername = (TextView) myHeader.findViewById(R.id.tv_username);
        tv_email = (TextView) myHeader.findViewById(R.id.tv_email);
        tvUsername.setText(SessionManager.User.getInstance().getFirstName() + " " + SessionManager.User.getInstance().getLastName());
        tv_email.setText(SessionManager.User.getInstance().getEmail());

        prepareListData(listDataHeader, listDataHeaderIcons, listDataChild);
        listAdapter = new NavigationMenuAdapter(this, listDataHeader, listDataHeaderIcons, listDataChild);
        expListView.setAdapter(listAdapter);

        tlbarText.setText(Constants.Home);

        /*Animation rotation = AnimationUtils.loadAnimation(Home.this, R.anim.rotate);
        rotation.setFillAfter(true);
        tlbarLogo.startAnimation(rotation);*/


        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                int flag = 0;

                if (groupPosition == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomePage()).commit();
                    tlbarText.setVisibility(View.VISIBLE);
                    tlbarText.setText(Constants.Home);
                    tlbarLogo.setVisibility(View.GONE);
                    ivQR.setVisibility(View.VISIBLE);
                    CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, true);
                    flag = 1;
                } else if (groupPosition == 1) {
                    if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Login()).commit();
                        tlbarText.setText(Constants.Login);
                        CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, true);
                        tlbarText.setVisibility(View.VISIBLE);
                        tlbarLogo.setVisibility(View.GONE);
                        ivQR.setVisibility(View.INVISIBLE);
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Orders()).commit();

                        CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, false);
                        tlbarText.setVisibility(View.VISIBLE);
                        tlbarText.setText(Constants.Orders);
                        tlbarLogo.setVisibility(View.GONE);
                        ivQR.setVisibility(View.GONE);
                    }
                    flag = 1;
                } else if (groupPosition == 2) {
                    if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Login()).commit();
                        tlbarText.setText(Constants.Login);
                        CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, true);
                        tlbarText.setVisibility(View.VISIBLE);
                        tlbarLogo.setVisibility(View.GONE);
                        ivQR.setVisibility(View.INVISIBLE);
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Coupon()).commit();
                        CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, false);
                        tlbarText.setVisibility(View.VISIBLE);
                        tlbarLogo.setVisibility(View.GONE);
                        tlbarText.setText(Constants.Coupon);
                        ivQR.setVisibility(View.GONE);
                    }
                    flag = 1;

                } else if (groupPosition == 3) {
                    if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Login()).commit();
                        tlbarText.setText(Constants.Login);
                        CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, true);
                        tlbarText.setVisibility(View.VISIBLE);
                        tlbarLogo.setVisibility(View.GONE);
                        ivQR.setVisibility(View.INVISIBLE);
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Language()).commit();
                        tlbarText.setVisibility(View.VISIBLE);
                        tlbarLogo.setVisibility(View.GONE);
                        CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, true);
                        tlbarText.setText(Constants.Language);
                        ivQR.setVisibility(View.GONE);

                    }
                    flag = 1;
                } else if (groupPosition == 4) {
                    Bundle bundle = new Bundle();
                    MyActivity.getInstance().Cart(Home.this, bundle);
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new LocationBranch()).commit();
//                    tlbarText.setVisibility(View.VISIBLE);
//                    tlbarLogo.setVisibility(View.GONE);
//                    tlbarText.setText(Constants.Location);
//                    CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, false);
                    flag = 1;
                } else if (groupPosition == 5) {
                    if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Login()).commit();
                        tlbarText.setText(Constants.Login);
                        tlbarText.setVisibility(View.VISIBLE);
                        CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, true);
                        tlbarLogo.setVisibility(View.GONE);
                        ivQR.setVisibility(View.INVISIBLE);
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new LoyaltyPoints()).commit();
                        tlbarText.setVisibility(View.VISIBLE);
                        CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, false);
                        tlbarLogo.setVisibility(View.GONE);
                        tlbarText.setText(Constants.LoyaltyPoints);
                        ivQR.setVisibility(View.GONE);
                    }
                    flag = 1;
                } else if (groupPosition == 6) {
                    if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Login()).commit();
                        tlbarText.setText(Constants.Login);
                        CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, true);
                        tlbarText.setVisibility(View.VISIBLE);
                        tlbarLogo.setVisibility(View.GONE);
                        ivQR.setVisibility(View.INVISIBLE);
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Address()).commit();
                        tlbarText.setVisibility(View.VISIBLE);
                        tlbarLogo.setVisibility(View.GONE);
                        CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, false);
                        tlbarText.setText(Constants.Address);
                        ivQR.setVisibility(View.GONE);
                    }
                    flag = 1;
                } /*else if (groupPosition == 7) {
                 *//*if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
                        MyActivity.getInstance().LoginAct(Home.this);
                    }
                    else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Settings()).commit();
                        tlbarText.setText(Constants.Settings);
                        tlbarText.setVisibility(View.VISIBLE);
                        CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, false);
                        tlbarLogo.setVisibility(View.GONE);
                        flag = 1;
                    }*//*
                } */ //else if (groupPosition == 6) {
//                   /* getSupportFragmentManager().beginTransaction().replace(R.id.container, new Help()).commit();
//                    tlbarText.setVisibility(View.VISIBLE);
//                    tlbarLogo.setVisibility(View.GONE);
//                    tlbarText.setText(Constants.Help);
//                    flag = 1;*/
//
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new com.ghostFood.fragment.Settings()).commit();
//                    tlbarText.setVisibility(View.VISIBLE);
//                    tlbarLogo.setVisibility(View.GONE);
//                    tlbarText.setText(Constants.gallery);
//                    flag = 1;
//
//                }
                else if (groupPosition == 7) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Help()).commit();
                    tlbarText.setVisibility(View.VISIBLE);
                    tlbarLogo.setVisibility(View.GONE);
                    tlbarText.setText(Constants.Help);
                    flag = 1;
                    ivQR.setVisibility(View.INVISIBLE);
                } else if (groupPosition == 8) {
                    drawer.closeDrawer(GravityCompat.START);
                    if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Login()).commit();
                        tlbarText.setVisibility(View.VISIBLE);
                    } else {
                        flag = 1;
//                        finish();
//                        MyActivity.getInstance().Home(Home.this);
                        final Dialog dialog = new Dialog(Home.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_alerts);
                        TextView tvAlertTxt = dialog.findViewById(R.id.tvAlertTxt);
                        TextView tvAlertDescription = dialog.findViewById(R.id.tvAlertDescription);
                        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
                        TextView tvYes = dialog.findViewById(R.id.tvYes);
                        tvCancel.setText(Constants.Cancel);
                        tvYes.setText(Constants.Yes + "," + " " + Constants.Logout);
                        tvAlertDescription.setText(Constants.allDataTobeCleared);
                        tvAlertTxt.setText(Constants.areYouSure);
                        tvYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                String language = SessionManager.AppProperties.getInstance().getLanguageCode();
                                ConstantsInternal.AppDirection direction = SessionManager.AppProperties.getInstance().getAppDirection();
                                SessionManager.getInstance().Logout();
                                SessionManager.AppProperties.getInstance().setLanguageCode(language);
                                SessionManager.AppProperties.getInstance().setAppDirection(direction);
                                tlbarText.setVisibility(View.VISIBLE);
                                CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, true);
                                tvUsername.setText(Constants.Guest);
                                ivEditProfile.setVisibility(View.INVISIBLE);
                                ivChoosePic.setVisibility(View.INVISIBLE);
                                iv_profilepicture.setVisibility(View.VISIBLE);
                                tv_email.setVisibility(View.INVISIBLE);
                                listDataHeader.set(8, Constants.Login);
                                listAdapter.notifyDataSetChanged();
                                tlbarText.setText(Constants.Login);
                                tlbarText.setVisibility(View.VISIBLE);
                                tlbarLogo.setVisibility(View.GONE);
                                MyActivity.getInstance().Home(Home.this);

                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        window.setAttributes(wlp);
                        dialog.show();

                    }
                }

                //}/*else if (groupPosition == 10) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AppSettings()).commit();
//                tlbarText.setVisibility(View.VISIBLE);
//                tlbarLogo.setVisibility(View.GONE);
//                tlbarText.setText(Constants.AppSettings);
//                flag = 1;
                //}*/


                if (flag == 1) {
                    drawer.closeDrawer(GravityCompat.START);
                }

                return false;
            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                if (groupPosition == 1 && childPosition == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Profile()).commit();
                    CommonFunctions.getInstance().ChangeBackgroundToImage(Home.this, drawer, false);
                    tlbarText.setText(Constants.Profile);
                }
                drawer.closeDrawer(GravityCompat.START);

                return false;
            }
        });
    }


    private void ShowProfilePictureDialog() {

        //CommonFunctions.Permissions.getInstance().ShowPermission(HomePage.this, CommonFunctions.Permissions.Type.STORAGE);

        final Dialog dialog = new Dialog(Home.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = Home.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_profilepicture, null);
        CommonFunctions.getInstance().ChangeFontFragment(dialogView);
        dialog.setContentView(dialogView);
        Button btnTakePhoto = (Button) dialog.findViewById(R.id.btn_TakePhoto);
        Button btnAddPhoto = (Button) dialog.findViewById(R.id.btn_AddPhoto);
        Button btnRemovePhoto = (Button) dialog.findViewById(R.id.btn_RemovePhoto);

        btnTakePhoto.setText(Constants.TakePhoto);
        btnAddPhoto.setText(Constants.AddPhoto);
        btnRemovePhoto.setText(Constants.RemovePhoto);

        if (SessionManager.User.getInstance().getProfileImage().trim().isEmpty()) {
            btnRemovePhoto.setEnabled(false);
        }

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.System.canWrite(Home.this)) {
                        ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        }, 2910);
                    } else {
                        ProfileImage profileImage = new ProfileImage();
                        imagePath1 = profileImage.paymentcameraIntent(Home.this);
                    }
                } else {
                    ProfileImage profileImage = new ProfileImage();
                    imagePath1 = profileImage.paymentcameraIntent(Home.this);

                }


            }
        });

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.System.canWrite(Home.this)) {
                        ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 4044);
                    } else {
                        ProfileImage profileImage = new ProfileImage();
                        profileImage.openGallery(Home.this);
                    }
                } else {
                    ProfileImage profileImage = new ProfileImage();
                    profileImage.openGallery(Home.this);
                }

                // CommonFunctions.getInstance().UploadProfile(HomePage.this, "/sdcard/DCIM/Camera/20170125_193947.jpg");
            }
        });

        btnRemovePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonFunctions.getInstance().RemoveProfileImage(Home.this, new ProfileCallback.ImageListener() {
                    @Override
                    public void onSuccess(String profileImageUrl) {
                        // CommonFunctions.getInstance().SetProfilePicture(iv_profilepicture);
                    }

                    @Override
                    public void onFailure(String profileImageUrl) {

                    }
                });
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    private void prepareListData(List<String> listDataHeader, List<Integer> listDataHeaderIcons, Map<String,
            List<String>> listDataChild) {

        listDataHeader.add(Constants.Home);
        listDataHeader.add(Constants.Orders);
        listDataHeader.add(Constants.Coupon);
        listDataHeader.add(Constants.Language);
        listDataHeader.add(Constants.Cart);
        listDataHeader.add(Constants.LoyaltyPoints);
        listDataHeader.add(Constants.Address);
//        listDataHeader.add(Constants.Settings);
        listDataHeader.add(Constants.Help);

        if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
            listDataHeader.add(Constants.Login);
            tvUsername.setText(Constants.Guest);
            ivEditProfile.setVisibility(View.INVISIBLE);
            ivChoosePic.setVisibility(View.INVISIBLE);
            iv_profilepicture.setVisibility(View.VISIBLE);
            tv_email.setVisibility(View.INVISIBLE);
        } else {
            listDataHeader.add(Constants.Logout);
            ivEditProfile.setVisibility(View.VISIBLE);
            ivChoosePic.setVisibility(View.VISIBLE);
            iv_profilepicture.setVisibility(View.VISIBLE);
        }
        // listDataHeader.add(Constants.AppSettings);

        listDataHeaderIcons.add(R.drawable.ic_home_white);
        listDataHeaderIcons.add(R.drawable.ic_order_white);
        listDataHeaderIcons.add(R.drawable.ic_coupon_white);
        listDataHeaderIcons.add(R.drawable.ic_language_white);
        listDataHeaderIcons.add(R.drawable.ic_cart);
        listDataHeaderIcons.add(R.drawable.ic_loyality_white);
        listDataHeaderIcons.add(R.drawable.ic_address_white);
//        listDataHeaderIcons.add(R.drawable.ic_settings_white);
        listDataHeaderIcons.add(R.drawable.ic_help_white);
        listDataHeaderIcons.add(R.drawable.ic_login_white);
        //  listDataHeaderIcons.add(R.drawable.ic_settings_white);

        /*if (SessionManager.User.getInstance().getKey().trim().isEmpty()) {
            MyActivity.getInstance().LoginAct(Home.this);
        }
        else {
            List<String> HelpSub = new ArrayList<String>();
            HelpSub.add(Constants.TermsAndCondition);
            HelpSub.add(Constants.PrivacyPolicy);
            listDataChild.put(listDataHeader.get(8), HelpSub);
        }*/
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ProfileImage profileImage = new ProfileImage();

        try {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                fragment.onActivityResult(requestCode, resultCode, data);
                Log.d("Activity", "ON RESULT CALLED");
            }
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        super.onActivityResult(requestCode, resultCode, data);

        //profileImage.ProcessProfileImage(Home.this, imagePath1, requestCode, resultCode, data, RESULT_OK, iv_profilepicture);
    }

    public String getPath1(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = Home.this.managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2909: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission", "Granted");
                    //dispatchTakeVideoIntent();
                } else {
                    Log.e("Permission", "Denied");
                }
                return;
            }

            case 4044: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission", "Granted");
                    ProfileImage profileImage = new ProfileImage();
                    profileImage.openGallery(Home.this);
                } else {
                    Log.e("Permission", "Denied");
                }
                return;
            }
            case 2910: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission", "Granted");
                    {
                        ProfileImage profileImage = new ProfileImage();
                        this.imagePath1 = profileImage.paymentcameraIntent(Home.this);
                    }

                } else {
                    Log.e("Permission", "Denied");
                }
                return;
            }
        }
    }

    public Bitmap getBitMap(String imgFile) {
        File imgFiles = new File(imgFile);
        if (imgFiles.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFiles.getAbsolutePath());
            return myBitmap;
        }
        return null;
    }

    private void paymentcameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        {
            Uri fileUri = getOutputMediaFileUri();
            imagePath1 = fileUri.getPath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    private Uri getOutputMediaFileUri() {
        Uri photoURI = null;
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion <= 23) {
            photoURI = Uri.fromFile(getOutputMediaFile());
        } else {
            photoURI = FileProvider.getUriForFile(Home.this, Home.this.getPackageName() + ".provider", getOutputMediaFile());
        }


        return photoURI;
    }

    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Farmcity");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Farmcity", "failed to create directory");
                return null;
            }
        }

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "img_" + "1" + ".jpg");

        if (mediaFile.exists())
            mediaFile.delete();

        return mediaFile.getAbsoluteFile();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath1(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    @Override
    public void onStart() {
        super.onStart();
        myEventBus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        myEventBus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        tvUsername.setText(SessionManager.User.getInstance().getFirstName() + " " + SessionManager.User.getInstance().getLastName());
        tv_email.setText(SessionManager.User.getInstance().getEmail());
        if (!SessionManager.User.getInstance().getKey().isEmpty()) {
            listDataHeader.set(8, Constants.Logout);
            ivEditProfile.setVisibility(View.VISIBLE);
            ivChoosePic.setVisibility(View.VISIBLE);
            iv_profilepicture.setVisibility(View.VISIBLE);
        } else {
            tvUsername.setText(Constants.Guest);
            iv_profilepicture.setVisibility(View.VISIBLE);
            tv_email.setVisibility(View.INVISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EBProfileImage event) {
        //CommonFunctions.getInstance().SetProfilePicture(iv_profilepicture);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EBLogin event) {
        CommonFunctions.getInstance().ShowSnackBar(Home.this, "EBLogin");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EBProfile event) {
        tvUsername.setText(SessionManager.User.getInstance().getFirstName() + SessionManager.User.getInstance().getLastName());
        if (!SessionManager.User.getInstance().getKey().isEmpty()) {
            listDataHeader.set(8, Constants.Logout);
            ivEditProfile.setVisibility(View.VISIBLE);
            ivChoosePic.setVisibility(View.VISIBLE);
            iv_profilepicture.setVisibility(View.VISIBLE);
        }
    }
}