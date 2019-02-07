package com.ghostFood.acitivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesignal.OneSignal;
import com.ghostFood.R;
import com.ghostFood.adapter.ViewPagerAdapters;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class TourActivity extends AppCompatActivity {


    ViewPagerAdapters mViewPagerAdapter;
    ArrayList<Integer> mImageList;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;
    final long PERIOD_MS = 6000;
    @BindView(R.id.vpTour)
    ViewPager vpTour;
    @BindView(R.id.inTourIndicator)
    CircleIndicator inTourIndicator;
    @BindView(R.id.tvWelcome)
    TextView tvWelcome;
    @BindView(R.id.tvWelcomeText)
    TextView tvWelcomeText;
    @BindView(R.id.tvHTTP)
    TextView tvHTTP;
    @BindView(R.id.edDomainName)
    EditText edDomainName;
    @BindView(R.id.tvOnTabee)
    TextView tvOnTabee;
    @BindView(R.id.btnProceed)
    Button btnProceed;
    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        ButterKnife.bind(this);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        FontFunctions.getInstance().FontPristina(TourActivity.this, tvWelcome);
        FontFunctions.getInstance().FontPristina(TourActivity.this, tvWelcomeText);
        FontFunctions.getInstance().FontPristina(TourActivity.this, tvHTTP);
        FontFunctions.getInstance().FontPristina(TourActivity.this, tvOnTabee);
        FontFunctions.getInstance().FontPristina(TourActivity.this, edDomainName);
        FontFunctions.getInstance().FontPristina(TourActivity.this, btnProceed);

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                if (userId != null)
                    SessionManager.User.getInstance().setDeviceID(userId);
            }
        });

        LoadPager();
    }

    private void LoadPager() {

        mImageList = new ArrayList<>();
        mImageList.add(R.drawable.ic_success);
        mImageList.add(R.drawable.ic_success);
        mImageList.add(R.drawable.ic_success);
        mImageList.add(R.drawable.ic_success);
        mImageList.add(R.drawable.ic_success);

        mViewPagerAdapter = new ViewPagerAdapters(TourActivity.this, mImageList);
        vpTour.setAdapter(mViewPagerAdapter);
        inTourIndicator.setViewPager(vpTour);
        //LoadAutoPager();
    }

    private void LoadAutoPager() {
        if (mImageList.size() > 1) {
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (vpTour != null) {
                        if (currentPage == mImageList.size()) {
                            currentPage = 0;
                        }
                        vpTour.setCurrentItem(currentPage++, true);
                    }
                }
            };
            timer = new Timer(); // This will create a new Thread
            timer.schedule(new TimerTask() { // task to be scheduled

                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);
        }
    }

    @OnClick({R.id.tlbar_back, R.id.btnProceed})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;
            case R.id.btnProceed:
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getByName(edDomainName.getText().toString().trim()+".Canadianpizza.com");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                if (inetAddress != null) {
                    SessionManager.AppProperties.getInstance().setDomain(edDomainName.getText().toString().trim());
                    MyActivity.getInstance().LoadHome(TourActivity.this);
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(TourActivity.this, "Domain doesn't exist.");
                }

                break;
        }
    }
}
