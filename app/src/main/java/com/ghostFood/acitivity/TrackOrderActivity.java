package com.ghostFood.acitivity;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.ghostFood.R;
import com.ghostFood.model.MarkerList;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.headerlistview.CustomMapView;
import com.ghostFood.util.headerlistview.DirectionsJSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/*
 * Created by Tech on 11-08-2017.
 */

public class TrackOrderActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_save)
    ImageView tlbarSave;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.tlbar_cart)
    ImageView tlbarCart;
    @BindView(R.id.tvCartCount)
    TextView tvCartCount;
    @BindView(R.id.flCart)
    FrameLayout flCart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mapView)
    CustomMapView mapView;
    @BindView(R.id.frame_lyout)
    FrameLayout frameLyout;
    @BindView(R.id.tvOrders_info)
    TextView tvOrdersInfo;
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.txt_order_details)
    TextView txtOrderDetails;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_ordernumber_Value)
    TextView tvOrdernumberValue;
    @BindView(R.id.llStatus)
    LinearLayout llStatus;
    @BindView(R.id.tvOrderDate)
    TextView tvOrderDate;
    @BindView(R.id.tvOrderDateValue)
    TextView tvOrderDateValue;
    @BindView(R.id.llOrderDate)
    LinearLayout llOrderDate;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_order_status_value)
    TextView tvOrderStatusValue;
    @BindView(R.id.llstatus)
    LinearLayout llstatus;
    @BindView(R.id.tv_driver)
    TextView tvDriver;
    @BindView(R.id.tv_drivervalue)
    TextView tvDrivervalue;
    @BindView(R.id.ll_driver)
    LinearLayout llDriver;
    @BindView(R.id.tv_reg_number)
    TextView tvRegNumber;
    @BindView(R.id.tv_reg_number_value)
    TextView tvRegNumberValue;
    @BindView(R.id.ll_reg_number)
    LinearLayout llRegNumber;
    @BindView(R.id.orderInfo)
    CardView orderInfo;
    @BindView(R.id.tvStatustracking)
    TextView tvStatustracking;
    @BindView(R.id.llDate)
    LinearLayout llDate;
    @BindView(R.id.frame1)
    FrameLayout frame1;
    @BindView(R.id.lly_scroll)
    RelativeLayout llyScroll;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.txt_support)
    TextView txtSupport;
    @BindView(R.id.img_support)
    ImageView imgSupport;
    @BindView(R.id.rly_btn_support)
    RelativeLayout rlyBtnSupport;
    @BindView(R.id.txt_call)
    TextView txtCall;
    @BindView(R.id.img_call)
    ImageView imgCall;
    @BindView(R.id.rly_btn_call)
    RelativeLayout rlyBtnCall;
    @BindView(R.id.lly_btm)
    LinearLayout llyBtm;
    //    private String orderKey;
    private Double custmoerlat;
    private Double customerlon;
    private Double vendorlat;
    private Double vendorlon;

    GoogleMap googleMap;

    private GoogleApiClient mGoogleApiClient;
    private LatLng mCurrentLatLon;
    PolylineOptions lineOptions = null;
    ArrayList<MarkerList> locationList = new ArrayList<MarkerList>();

    public static Socket socket;
    //private TrackOrderApi.TrackOrderResponse trackResponse;
    private String orderKey;
    private LocationManager mLocationManager;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 101;
    private String contact_number = "";
    ///private TrackOrderApi.Datum data;
    String from_lat, from_lon, to_lat, to_lon, orderNumber, order_status, order_date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        ButterKnife.bind(this);
        initTextListener();
        CommonFunctions.getInstance().ChangeDirection(TrackOrderActivity.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(TrackOrderActivity.this, toolbar);
        FontFunctions.getInstance().FontBalooBhaijaan(TrackOrderActivity.this, tlbarText);
       /* toolbar.setNavigationIcon(ContextCompat.getDrawable(TrackOrderActivity.this, R.drawable.ic_leftarrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                onBackPressed();
            }
        });*/

        String json = getIntent().getStringExtra("trackResponse");
//        orderKey = getIntent().getStringExtra("orderKey");

        Gson gson = new Gson();

        /*trackResponse = gson.fromJson(json, TrackOrderApi.TrackOrderResponse.class);
        data = trackResponse.getData().get(0);*/
//        orderKey = data.getOrder_key();


        orderKey = getIntent().getExtras() != null && getIntent().getExtras().getString("order_key") != null ? getIntent().getExtras().getString("order_key") : "";
        from_lat = getIntent().getExtras() != null && getIntent().getExtras().getString("from_lat") != null ? getIntent().getExtras().getString("from_lat") : "";
        from_lon = getIntent().getExtras() != null && getIntent().getExtras().getString("from_lon") != null ? getIntent().getExtras().getString("from_lon") : "";
        to_lat = getIntent().getExtras() != null && getIntent().getExtras().getString("to_lat") != null ? getIntent().getExtras().getString("to_lat") : "";
        to_lon = getIntent().getExtras() != null && getIntent().getExtras().getString("to_lon") != null ? getIntent().getExtras().getString("to_lon") : "";
        orderNumber = getIntent().getExtras() != null && getIntent().getExtras().getString("order_number") != null ? getIntent().getExtras().getString("order_number") : "";
        order_status = getIntent().getExtras() != null && getIntent().getExtras().getString("order_status") != null ? getIntent().getExtras().getString("order_status") : "";
        order_status = getIntent().getExtras() != null && getIntent().getExtras().getString("order_status") != null ? getIntent().getExtras().getString("order_status") : "";
        order_date = getIntent().getExtras() != null && getIntent().getExtras().getString("order_date") != null ? getIntent().getExtras().getString("order_date") : "";
        //orderKey = "xwgt8zn2wcep_uwb";

        mapView.setVisibility(View.VISIBLE);


        tvOrderDateValue.setText(order_date);
        tvOrderStatusValue.setText(order_status);
        tvOrdernumberValue.setText("#" + orderNumber);

        FontFunctions.getInstance().FontBabeNeueBold(TrackOrderActivity.this, tvOrderDateValue);
        FontFunctions.getInstance().FontBabeNeueBold(TrackOrderActivity.this, tvOrderStatusValue);
        FontFunctions.getInstance().FontBabeNeueBold(TrackOrderActivity.this, tvOrdernumberValue);


        {
            mapView.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(TrackOrderActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        + ActivityCompat.checkSelfPermission(TrackOrderActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission
                                .ACCESS_COARSE_LOCATION}, 1000);
                    }
                } else {
                    LoadMap(savedInstanceState);
                }
            } else {
                LoadMap(savedInstanceState);
            }

            custmoerlat = Double.parseDouble(from_lat);
            customerlon = Double.parseDouble(from_lon);
            vendorlat = Double.parseDouble(to_lat);
            vendorlon = Double.parseDouble(to_lon);


        }/* else {
            mMapView.setVisibility(View.GONE);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) frame1.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            frame1.setLayoutParams(params);
        }*/

       /* List<TrackOrderApi.Status_tracking> stepData = data.getStatus_tracking();

        List<String> step = new ArrayList<>();

        for (int count = 0; count < stepData.size(); count++) {
            step.add(stepData.get(count).getDelivery_status());
        }

        for (int count = 0; count < stepData.size(); count++) {
            TextView child = (TextView) getLayoutInflater().inflate(R.layout.step_status_textview, null);
            child.setText(stepData.get(count).getStatus_time());
            llDate.addView(child);
        }


        int sizes = 0;

        for (int count = 0; count < stepData.size(); count++) {
            if (!stepData.get(count).getStatus_time().isEmpty()) {
                sizes = sizes + 1;
            } else {
                break;
            }
        }*/

    /*    VerticalStepview.setStepsViewIndicatorComplectingPosition(sizes)
                .reverseDraw(false)
                .setStepViewTexts(step)
                .setLinePaddingProportion(0.99f)
                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#e4e3e3"))
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.textcolor_black))
                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#3F51B5"))
//                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#3F51B5"))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.ic_tracking_current_status))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.ic_tracking_accepted))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.ic_tracking_accepted))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.textcolor_black));
*/
    }


    private void initTextListener() {
        tlbarText.setText(Constants.trackOrder);
        tvOrdersInfo.setText(Constants.order_info);
        txtOrderDetails.setText(Constants.details);
        tvOrderNumber.setText(Constants.orderNumber);
        tvOrderDate.setText(Constants.orderdate);
        tvOrderStatus.setText(Constants.orderstatus);
        tvDriver.setText(Constants.driver);


        FontFunctions.getInstance().FontBabeNeueBold(TrackOrderActivity.this, tvOrdersInfo);
        FontFunctions.getInstance().FontBabeNeueBold(TrackOrderActivity.this, txtOrderDetails);
        FontFunctions.getInstance().FontBabeNeueBold(TrackOrderActivity.this, tvOrderNumber);
        FontFunctions.getInstance().FontBabeNeueBold(TrackOrderActivity.this, tvOrderDate);
        FontFunctions.getInstance().FontBabeNeueBold(TrackOrderActivity.this, tvOrderStatus);
        FontFunctions.getInstance().FontBabeNeueBold(TrackOrderActivity.this, tvDriver);
        FontFunctions.getInstance().FontBabeNeueBold(TrackOrderActivity.this, tvRegNumber);

        FontFunctions.getInstance().FontBabeNeueBold(TrackOrderActivity.this, tvRegNumberValue);
        FontFunctions.getInstance().FontBabeNeueBold(TrackOrderActivity.this, tvDrivervalue);


        tvRegNumber.setText(Constants.MobileNumber);
        //txtSupport.setText(Constants.support);
        //txtCall.setText(Constants.call);
        //tvStatustracking.setText(Constants.status_tracking);


        txtOrderDetails.setOnClickListener(this);
        rlyBtnSupport.setOnClickListener(this);
        rlyBtnCall.setOnClickListener(this);
    }

    // OnCLick Listener
    @Override
    public void onClick(View view) {

        if (view == txtOrderDetails) {
            finish();
        } else if (view == rlyBtnCall) {

            try {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + String.valueOf(contact_number)));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);

                    return;
                }

                startActivity(callIntent);

            } catch (ActivityNotFoundException e) {
                Log.e("helloAndroid", "Call failed", e);
            }

        } else if (view == rlyBtnSupport) {
            //supportDialog();
        }
    }

    private void supportDialog() {
        final Dialog dialog = new Dialog(TrackOrderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setContentView(R.layout.support_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        /*Button call = (Button) dialog.findViewById(R.id.btnCall);
        Button email = (Button) dialog.findViewById(R.id.btnEmail);

        TextView tvEmail = (TextView) dialog.findViewById(R.id.tvEmail);
        TextView tvMobile = (TextView) dialog.findViewById(R.id.tvMobile);


        tvEmail.setText(data.getSupport_email());
        tvMobile.setText(data.getSupport_number());

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + String.valueOf(data.getSupport_number())));
                    if (ActivityCompat.checkSelfPermission(TrackOrderActivity.this, Manifest.permission.CALL_PHONE) !=
                            PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(TrackOrderActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);

                        return;
                    }

                    startActivity(callIntent);

                } catch (ActivityNotFoundException e) {
                    Log.e("helloAndroid", "Call failed", e);
                }
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{data.getSupport_email()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "My subject");

                startActivity(Intent.createChooser(intent, "Email via..."));
            }
        });
        dialog.show();*/
    }

    // Map
    private void LoadMap(Bundle savedInstanceState) {
        mapView = (CustomMapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(TrackOrderActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                if (ActivityCompat.checkSelfPermission(TrackOrderActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(TrackOrderActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(false);

//                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                Criteria criteria = new Criteria();

                Location location = getLastKnownLocation();//locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                if (location != null) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                            .zoom(17)                   // Sets the zoom
                            .build();                   // Creates a CameraPosition from the builder
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }


                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(custmoerlat, customerlon))
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
                if (vendorlat != null && vendorlon != null) {
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(vendorlat, vendorlon))
                            .anchor(0.5f, 0.5f)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
                }

                addMarker();

                ConnectSocket();

            }
        });
    }


    private void addMarker() {
        if (vendorlat != null && custmoerlat != null && customerlon != null && vendorlon != null) {
            LatLng origin = new LatLng(custmoerlat, (customerlon));

            LatLng dest = new LatLng((vendorlat), (vendorlon));

            if (origin.latitude != 0.0 && origin.longitude != 0.0 && dest.latitude != 0.0 && dest.longitude != 0.0) {
                // Getting URL to the Google Directions API
                String url = getDirectionsUrl(origin, dest);

                DownloadTask downloadTask = new DownloadTask();
                // Start downloading json data from Google Directions API
                downloadTask.execute(url);
            }


            googleMap.getUiSettings().setRotateGesturesEnabled(false);
        }
    }


    private void ConnectSocket() {
        try {
            final JSONObject objInit = new JSONObject();
            objInit.put("order_key", orderKey.replace(" ", "").trim());
            objInit.put("company_id", "5b9f96ef6819215d3f78f509");

            if (socket == null) {
                socket = IO.socket("http://34.238.69.205:8021");
            } else {
                socket.emit("driver_location", objInit);
                System.out.println("Socket is already connected");
            }

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    /*if (!socket.connected())*/
                    {
                        socket.emit("driver_location", objInit);
                        locationList.clear();
                    }
                }

            }).on("authenticated", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    String taxiDetails = "asdf";
                }

            }).on("event", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                }

            }).on("driver_location", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    final String taxiDetails = args[0].toString();
                    System.out.println("Received from Socket :" + args[0].toString());

                    try {
                        TrackOrderActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject Job_CarDetails = new JSONObject(taxiDetails);
                                    String delName = Job_CarDetails.getString("driver_name");
//                                    String delEmail = Job_CarDetails.getString("driver_email");
//                                    String delMobile = Job_CarDetails.getString("driver_phone");

                                    String latitude = Job_CarDetails.getString("latitude");
                                    String longitude = Job_CarDetails.getString("longitude");

                                    tvDrivervalue.setText(delName);
//                                    tvRegNumberValue.setText(delMobile);


                                    if (locationList.size() == 0) {
                                        MarkerOptions mMarkerOptions = new MarkerOptions().position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)))
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.carmap));
                                        Marker mMarker = googleMap.addMarker(mMarkerOptions);
                                        MarkerList markerList = new MarkerList();
                                        /*markerList.setDeliveryboy_key(deliveryboy_key);
                                        markerList.setDelivery_boy_name(delivery_boy_name);
                                        markerList.setDelivery_boy_email(delivery_boy_email);
                                        markerList.setMobile_number(mobile_number);*/
                                        markerList.setLatitude(latitude);
                                        markerList.setLongitude(longitude);
                                        markerList.setMarker(mMarker);
                                        locationList.add(markerList);
                                    } else {

                                        LatLng mToPosition = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                        animateMarker(locationList.get(0).getMarker(), mToPosition, true);
                                    }

                                    showDistanceTime(Double.parseDouble(latitude), Double.parseDouble(longitude), (custmoerlat), (customerlon));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception e) {

                    }


                }
            });
            if (!socket.connected()) {
                socket.connect();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animateMarker(final Marker marker, final LatLng toPosition, final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = googleMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 5000;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = interpolator.getInterpolation((float) elapsed / duration);
                    if (toPosition != null) {
                        double lng = t * toPosition.longitude + (1 - t) * startLatLng.longitude;
                        double lat = t * toPosition.latitude + (1 - t) * startLatLng.latitude;

                        Location prevLoc = new Location("service Provider");
                        prevLoc.setLatitude(startLatLng.latitude);
                        prevLoc.setLongitude(startLatLng.longitude);

                        Location newLoc = new Location("service Provider");
                        newLoc.setLatitude(toPosition.latitude);
                        newLoc.setLongitude(toPosition.longitude);

                        float bearing = prevLoc.bearingTo(newLoc);
                        marker.setPosition(new LatLng(lat, lng));
                        marker.setRotation(bearing + 90);
                        //moveToPostion(lat, lng);
                        if (t < 1.0) {
                            // Post again 16ms later.
                            handler.postDelayed(this, 16);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private double showDistanceTime(double mDistanceLatitude, double mDistanceLongitude, double mTaxiLatitude, double mTaxiLongitude) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = mDistanceLatitude;
        double lat2 = mTaxiLatitude;
        double lon1 = mDistanceLongitude;
        double lon2 = mTaxiLongitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec + " Meter   " + meterInDec);

//        eta.setText("ETA : "+(int)round((valueResult*2),2));

        return Radius * c;
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @OnClick({R.id.tlbar_back, R.id.flCart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;
            case R.id.flCart:
                break;
        }
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";
            if (result.size() < 1) {
                //Toast.makeText(TrackOrderActivity.this, "No Points", Toast.LENGTH_SHORT).show();
                return;
            }
            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    if (j == 0) { // Get duration from the list
                        distance = (String) point.get("distance");
                        continue;
                    } else if (j == 1) { // Get duration from the list
                        duration = (String) point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);
            }

            /*vDistanceValue.setText(distance);
            vRemainingTimeValue.setText(duration);*/
            // Drawing polyline in the Google Map for the i-th route
            googleMap.addPolyline(lineOptions);
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
//                return TODO;
                break;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (socket != null) {
            socket.disconnect();
            socket = null;
        }

    }
}