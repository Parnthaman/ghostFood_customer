package com.ghostFood.acitivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ghostFood.adapter.BranchListAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ghostFood.R;
import com.ghostFood.api.BranchesAPI;
import com.ghostFood.model.Branches;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ghostFood.acitivity.Home.mContext;
import static com.ghostFood.acitivity.MyApplication.context;

public class PickupLocations extends AppCompatActivity implements AdapterView.OnItemClickListener {

    MapView mMapView;
    @BindView(R.id.lvBranches)
    ListView lvBranches;
    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.tvNoBranchess)
    TextView tvNoBranchess;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.acSearchLocation)
    AutoCompleteTextView acSearchLocation;

    BitmapDescriptor mCurrentLocationIcon;

    Marker mMarker;

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private GoogleMap googleMap;
    ArrayList<Branches> mBranchList;
    ArrayList<Marker> mMarkerList;
    Bundle mBundle;
    View views;
    BranchListAdapter mBranchListAdapter;
    private String str;

    double mCurrentLatitude = 0.0, mCurrentLongitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_locations);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(PickupLocations.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(PickupLocations.this, toolbar);
        FontFunctions.getInstance().FontBalooBhaijaan(PickupLocations.this, tlbarText);

        tlbarText.setText(Constants.PickUp);
        acSearchLocation.setHint(Constants.enter_your_location);

        mBundle = savedInstanceState;
        MapsInitializer.initialize(PickupLocations.this);
        mCurrentLocationIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);

        tvNoBranchess.setText(Constants.NoNearByBranches);
        tvNoBranchess.setVisibility(View.GONE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(PickupLocations.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) + ContextCompat
                    .checkSelfPermission(PickupLocations.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission
                                    .ACCESS_COARSE_LOCATION},
                            1000);
                }
            } else {
                //Call whatever you want
                LoadMap(savedInstanceState);

                acSearchLocation.setAdapter(new GooglePlacesAutocompleteAdapter(mContext, R.layout.searchplace_list));
                acSearchLocation.setOnItemClickListener(this);

            }
        } else {
            LoadMap(savedInstanceState);

            acSearchLocation.setAdapter(new GooglePlacesAutocompleteAdapter(mContext, R.layout.searchplace_list));
            acSearchLocation.setOnItemClickListener(this);
        }


    }


    private void callBranchesApi(final LatLng mLatLon) {

        mBranchList = new ArrayList<>();
        mMarkerList = new ArrayList<>();
        tvNoBranchess.setVisibility(View.GONE);
        CustomProgressDialog.getInstance().show(PickupLocations.this);
        BranchesAPI mBranchesAPI = ApiConfiguration.getInstance2().getApiBuilder().create(BranchesAPI.class);
        final Call<BranchesAPI.ResponseBranches> call = mBranchesAPI.Get(String.valueOf(mLatLon.latitude), String.valueOf(mLatLon.longitude));
        //final Call<BranchesAPI.ResponseBranches> call = mBranchesAPI.Get("24.886", "-70.268");
        call.enqueue(new Callback<BranchesAPI.ResponseBranches>() {
            @Override
            public void onResponse(Call<BranchesAPI.ResponseBranches> call, Response<BranchesAPI.ResponseBranches> response) {

                LatLng mCurrentLL = new LatLng(mLatLon.latitude, mLatLon.longitude);
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        for (BranchesAPI.Datum data : response.body().getData()) {
                            Branches mBranches = new Branches();
                            mBranches.setBranchKey(data.getBranch_key());
                            mBranches.setBranchName(data.getBranch_name());
                            mBranches.setBranchAddress(data.getBranch_address_line1() + ", " + data.getBranch_address_line2() + ", " + data.getArea() + ", " + data.getCity() + ", " + data.getCountry());
                            mBranches.setDistance(data.getDistance());
                            mBranches.setLatitude(data.getLatitude());
                            mBranches.setLongitude(data.getLongitude());
                            mBranchList.add(mBranches);
                        }


                        if (mBranchList.size() == 0) {
                            lvBranches.setVisibility(View.GONE);
                            tvNoBranchess.setVisibility(View.VISIBLE);
                        } else {
                            lvBranches.setVisibility(View.VISIBLE);
                            tvNoBranchess.setVisibility(View.GONE);
                            mBranchListAdapter = new BranchListAdapter(PickupLocations.this, mBranchList, ConstantsInternal.ListType.PICKUP, mCurrentLL);
                            lvBranches.setAdapter(mBranchListAdapter);
                        }
                        googleMap.clear();
                        if (mMarker != null) {
                            mMarker.remove();
                        }
                        mMarker = googleMap.addMarker(new MarkerOptions().position(mCurrentLL).draggable(true));
                        mMarker.setTag(9999);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLL, 17));


                        LatLng mLatLon = new LatLng(0.0, 0.0);
                        if (mBranchList != null) {
                            for (int count = 0; count < mBranchList.size(); count++) {
                                mLatLon = new LatLng(Double.parseDouble(mBranchList.get(count).getLatitude()), Double.parseDouble(mBranchList.get(count).getLongitude()));

                                Marker mMarker = googleMap.addMarker(new MarkerOptions().position(mLatLon).icon(BitmapDescriptorFactory.fromBitmap(writeTextOnDrawable(R.drawable.ic_marker, "" + (count + 1)))).title(mBranchList.get(count).getBranchName()));
                                mMarker.setTag(count);

                                mMarkerList.add(mMarker);

                            }
                        }

                        lvBranches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                LatLng mLatLonToAnimate = new LatLng(Double.parseDouble(mBranchList.get(position).getLatitude()), Double.parseDouble(mBranchList.get(position).getLongitude()));
                                CameraPosition cameraPosition = new CameraPosition.Builder().target(mLatLonToAnimate).zoom(12).build();
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                mMarkerList.get(position).showInfoWindow();

                            }
                        });

                        CustomProgressDialog.getInstance().dismiss();

                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(PickupLocations.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(PickupLocations.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<BranchesAPI.ResponseBranches> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(PickupLocations.this, Constants.NoInternetConnection);
            }
        });

    }

    private void LoadMap(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(PickupLocations.this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                googleMap = mMap;

                if (ActivityCompat.checkSelfPermission(PickupLocations.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PickupLocations.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(false);

                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();

                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                if (location != null) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));


                    mCurrentLatitude = location.getLatitude();
                    mCurrentLongitude = location.getLongitude();

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(mCurrentLatitude, mCurrentLongitude))      // Sets the center of the map to location user
                            .zoom(17)                   // Sets the zoom
                            .build();                   // Creates a CameraPosition from the builder
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    googleMap.setMyLocationEnabled(true);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);

                    LatLng mLatLng = new LatLng(mCurrentLatitude, mCurrentLongitude);
                    callBranchesApi(mLatLng);

                    googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker arg0) {
                        }

                        @SuppressWarnings("unchecked")
                        @Override
                        public void onMarkerDragEnd(final Marker arg0) {
                            googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                            callBranchesApi(arg0.getPosition());
                        }

                        @Override
                        public void onMarkerDrag(Marker arg0) {
                        }
                    });


                    mMarker = googleMap.addMarker(new MarkerOptions().position(mLatLng)
                            .draggable(true));
                    mMarker.setTag(9999);


                }

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        int position = (int) (marker.getTag());

                        if (position != 9999) {
                            lvBranches.smoothScrollToPosition(position);
                        }

                        return false;
                    }
                });


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null)
            mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null)
            mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null)
            mMapView.onLowMemory();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if ((grantResults.length > 0) && (grantResults[0] +
                        grantResults[1]) == PackageManager.PERMISSION_GRANTED) {
                    LoadMap(mBundle);

                } else {

                }
                return;
            }
        }
    }

    @OnClick(R.id.tlbar_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        str = (String) parent.getItemAtPosition(position);
        // Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        Geocoder coder = new Geocoder(mContext);
        try {
            ArrayList<android.location.Address> adresses = (ArrayList<Address>) coder.getFromLocationName(str, 10);
            for (Address add : adresses) {
                Double longitude = add.getLongitude();
                Double latitude = add.getLatitude();
                System.out.println(longitude + "  " + latitude);

                CommonFunctions.getInstance().HideSoftKeyboard(PickupLocations.this);

                if (mMarker != null) {
                    mMarker.remove();
                }

                LatLng mLatLng = new LatLng(latitude, longitude);
                mMarker = googleMap.addMarker(new MarkerOptions().position(mLatLng)
                        .draggable(true));
                mMarker.setTag(9999);

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(mLatLng)      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                callBranchesApi(mLatLng);

            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    private class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }

    }

    public ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;


        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + getResources().getString(R.string.mapKey));
            sb.append("&components=");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));


            URL url = new URL(sb.toString());

            System.out.println("URL: " + url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            return resultList;
        } catch (IOException e) {
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                //  System.out.println("lat :" + predsJsonArray.getJSONObject(i).getString("lat"));
                System.out.println("============================================================");
                // resultList.add(predsJsonArray.getJSONArray(i));
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));

            }
        } catch (JSONException e) {

        }


        return resultList;

    }

    private Bitmap writeTextOnDrawable(int drawableId, String text) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId)
                .copy(Bitmap.Config.ARGB_8888, true);

        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(convertToPixels(context, 11));

        Rect textRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), textRect);

        Canvas canvas = new Canvas(bm);

        //If the text is bigger than the canvas , reduce the font size
        if (textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
            paint.setTextSize(convertToPixels(context, 7));        //Scaling needs to be used for different dpi's

        //Calculate the positions
        int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset

        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));

        canvas.drawText(text, xPos, yPos, paint);

        return bm;
    }


    public static int convertToPixels(Context context, int nDP) {
        final float conversionScale = context.getResources().getDisplayMetrics().density;

        return (int) ((nDP * conversionScale) + 0.5f);

    }

}
