package com.ghostFood.acitivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.ghostFood.R;
import com.ghostFood.api.AddAddressAPI;
import com.ghostFood.api.UpdateAddressAPI;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.SessionManager;

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
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressMap extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    @BindView(R.id.ivCenterLocation)
    ImageView ivCenterLocation;
    @BindView(R.id.mapView)
    MapView mMapView;
    GoogleMap googleMap;
    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_save)
    ImageView tlbarSave;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Activity mAddAddressMap;
    ConstantsInternal.Address addressStatus = ConstantsInternal.Address.Add;
    @BindView(R.id.tlbar_cart)
    ImageView tlbarCart;
    @BindView(R.id.tvCartCount)
    TextView tvCartCount;
    @BindView(R.id.flCart)
    FrameLayout flCart;
    @BindView(R.id.acSearchLocation)
    AutoCompleteTextView acSearchLocation;
    @BindView(R.id.searchmarker)
    ImageView searchmarker;

    private double latitude = 0;
    private double longitude = 0;
    private String contactName;

    //Marker mMarker;
    String mAddressKey = "";
    private String str;

    Address addresses_bc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_map);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(AddAddressMap.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(AddAddressMap.this, toolbar);

        tlbarText.setText(Constants.AddAddress);
        FontFunctions.getInstance().FontBalooBhaijaan(AddAddressMap.this, tlbarText);
        mAddAddressMap = this;
        acSearchLocation.setHint(Constants.enter_your_location);
        tlbarSave.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            if (bundle.getString("contact_name") != null) {
                contactName = bundle.getString("contact_name");
            }

            if (ConstantsInternal.Address.fromInteger(bundle.getInt("from")) == ConstantsInternal.Address.Update) {
                addressStatus = ConstantsInternal.Address.Update;
                tlbarText.setText(Constants.UpdateAddress);
                mAddressKey = bundle.getString("address_key");
                latitude = bundle.getDouble("latitude");
                longitude = bundle.getDouble("logitude");
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(AddAddressMap.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) + ActivityCompat
                    .checkSelfPermission(AddAddressMap.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission
                                    .ACCESS_COARSE_LOCATION},
                            1000);
                }
            } else {
                LoadMap(savedInstanceState);

                acSearchLocation.setAdapter(new GooglePlacesAutocompleteAdapter(Home.mContext, R.layout.searchplace_list));
                acSearchLocation.setOnItemClickListener(this);
            }
        } else {
            LoadMap(savedInstanceState);

            acSearchLocation.setAdapter(new GooglePlacesAutocompleteAdapter(Home.mContext, R.layout.searchplace_list));
            acSearchLocation.setOnItemClickListener(this);
        }


    }

    private void LoadMap(Bundle savedInstanceState) {


        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(AddAddressMap.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(AddAddressMap.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddAddressMap.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(false);

                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();

                boolean gps_enabled = false;
                boolean network_enabled = false;


                gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                Location location = null;
                if (gps_enabled)
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                else if (network_enabled)
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                    if (addressStatus == ConstantsInternal.Address.Add) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(latitude, longitude))
                            .zoom(17)
                            .build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }

                if (addressStatus == ConstantsInternal.Address.Update) {
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(latitude, longitude))
                            .zoom(17)
                            .build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }


            }
        });

    }

    @OnClick({R.id.tlbar_back, R.id.tlbar_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;
            case R.id.tlbar_save:
                LatLng center = googleMap.getCameraPosition().target;
                getAddressFromLatLon(center);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        str = (String) parent.getItemAtPosition(position);
        // Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        Geocoder coder = new Geocoder(Home.mContext);
        try {
            ArrayList<android.location.Address> adresses = (ArrayList<Address>) coder.getFromLocationName(str, 10);
            for (Address add : adresses) {
                Double longitude = add.getLongitude();
                Double latitude = add.getLatitude();
                System.out.println(longitude + "  " + latitude);

                CommonFunctions.getInstance().HideSoftKeyboard(AddAddressMap.this);

                /*if (mMarker != null) {
                    mMarker.remove();
                }
                */

                LatLng mLatLng = new LatLng(latitude, longitude);
                /*mMarker = googleMap.addMarker(new MarkerOptions().position(mLatLng)
                        .draggable(true));
                mMarker.setTag(9999);*/

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(mLatLng)      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                //callBranchesApi(mLatLng);

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

    private void getAddressFromLatLon(final LatLng center) {

        try {

            new AsyncTask<Object, Object, Address>() {
                @Override
                protected Address doInBackground(Object... args) {

                    Geocoder geocoder;
                    List<Address> addresses = null;
                    geocoder = new Geocoder(AddAddressMap.this, Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(center.latitude, center.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addresses != null && addresses.size() > 0)
                        return addresses.get(0);

                    return null;

                }

                @SuppressWarnings("deprecation")
                protected void onPostExecute(Address addresses) {

                    addresses_bc = addresses;
                    if (addresses != null) {
                       /* Bundle bundle = new Bundle();
                        bundle.putString("contact_name", contactName);
                        bundle.putParcelable("address", (Parcelable) addresses);
                        bundle.putInt("from", addressStatus.getValue());
                        bundle.putString("address_key", mAddressKey);
                        MyActivity.getInstance().AddAddress(AddAddressMap.this, bundle);*/

                        LoadPopup(addresses);

                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(AddAddressMap.this, Constants.PleaseSelectAValidDddress);
                    }

                }
            }.execute();


        } catch (Exception e) {

        }
    }

    private void LoadPopup(Address mAddress) {


        //Address mAddress = addresses_bc;

        latitude = mAddress.getLatitude();
        longitude = mAddress.getLongitude();

      /*  if (ConstantsInternal.Address.fromInteger(bundle.getInt("from")) == ConstantsInternal.Address.Update) {
            addressStatus = ConstantsInternal.Address.Update;
            tlbarText.setText(Constants.UpdateAddress);
            mAddressKey = bundle.getString("address_key");
        }

        if (bundle.getString("contact_name") != null) {
            edtAddressContactName.setText(bundle.getString("contact_name"));
        }*/


        final Dialog dialog = new Dialog(AddAddressMap.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_addaddress);
        dialog.setCancelable(false);

        final EditText edtAddressLine1 = (EditText) dialog.findViewById(R.id.edtAddressLine1);
        final EditText edtAddressLine2 = (EditText) dialog.findViewById(R.id.edtAddressLine2);
        final EditText edtAddressContactName = (EditText) dialog.findViewById(R.id.edtAddressContactName);
        final EditText edtZipCode = (EditText) dialog.findViewById(R.id.edtZipCode);
        final EditText edtCountry = (EditText) dialog.findViewById(R.id.edtCountry);
        final EditText edtCity = (EditText) dialog.findViewById(R.id.edtCity);
        final EditText edtArea = (EditText) dialog.findViewById(R.id.edtArea);
        final EditText edtBuildingName = (EditText) dialog.findViewById(R.id.edtBuildingName);
        final EditText edtFlatNo = (EditText) dialog.findViewById(R.id.edtFlatNo);

        TextView tvAddressLine1 = (TextView) dialog.findViewById(R.id.tvAddressLine1);
        TextView tvAddressLine2 = (TextView) dialog.findViewById(R.id.tvAddressLine2);
        TextView tvAddressContactName = (TextView) dialog.findViewById(R.id.tvAddressContactName);
        TextView tvZipCode = (TextView) dialog.findViewById(R.id.tvZipCode);
        TextView tvCountry = (TextView) dialog.findViewById(R.id.tvCountry);
        TextView tvCity = (TextView) dialog.findViewById(R.id.tvCity);
        TextView tvArea = (TextView) dialog.findViewById(R.id.tvArea);
        TextView tvBuildingName = (TextView) dialog.findViewById(R.id.tvBuildingName);
        TextView tvFlatNo = (TextView) dialog.findViewById(R.id.tvFlatNo);
        TextView btnSaveAddress = (TextView) dialog.findViewById(R.id.btnSaveAddress);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);

        LinearLayout llSave = (LinearLayout) dialog.findViewById(R.id.llSave);
        ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);


        edtAddressContactName.setText(Constants.Home);
        edtFlatNo.setText("");
        edtBuildingName.setText((mAddress.getFeatureName() == null) ? "" : mAddress.getFeatureName());
        edtAddressLine1.setText((mAddress.getSubLocality() == null) ? "" : mAddress.getSubLocality());
        edtAddressLine2.setText((mAddress.getThoroughfare() == null) ? "" : mAddress.getThoroughfare());
        edtArea.setText((mAddress.getLocality() == null) ? "" : mAddress.getLocality());
        edtCity.setText((mAddress.getAdminArea() == null) ? "" : mAddress.getAdminArea());
        edtCountry.setText((mAddress.getCountryName() == null) ? "" : mAddress.getCountryName());
        edtZipCode.setText((mAddress.getPostalCode() == null) ? "" : mAddress.getPostalCode());

        if (contactName != null) {
            edtAddressContactName.setText(contactName);
        }


        tvBuildingName.setText(Constants.BuildingName);
        tvFlatNo.setText(Constants.FlatRoomNo);
        tvAddressContactName.setText(Constants.AddressType);

        tvAddressLine1.setText(Constants.AddressLine1);
        tvAddressLine2.setText(Constants.Landmark);
        tvArea.setText(Constants.Area);
        tvCity.setText(Constants.City);
        tvCountry.setText(Constants.Country);
        tvZipCode.setText(Constants.ZipCode);

        btnSaveAddress.setText(Constants.Submit);
        tvTitle.setText(Constants.AddAddress);

        // tlbarSave.setVisibility(View.GONE);
        //tlbarText.setText(Constants.AddAddress);

        if (ConstantsInternal.Address.fromInteger(addressStatus.getValue()) == ConstantsInternal.Address.Update) {
            addressStatus = ConstantsInternal.Address.Update;
            //tlbarText.setText(Constants.UpdateAddress);
            tvTitle.setText(Constants.UpdateAddress);
            btnSaveAddress.setText(Constants.UpdateAddress);
            mAddressKey = mAddressKey;
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressStatus == ConstantsInternal.Address.Add) {
                    CustomProgressDialog.getInstance().show(AddAddressMap.this);
                    AddAddressAPI mAddAddressAPI = ApiConfiguration.getInstance2().getApiBuilder().create(AddAddressAPI.class);
                    final Call<AddAddressAPI.ResponseAddress> call = mAddAddressAPI.Create(
                            SessionManager.User.getInstance().getKey(),
                            edtAddressContactName.getText().toString().trim(),
                            edtBuildingName.getText().toString().trim(),
                            edtFlatNo.getText().toString().trim(),
                            edtAddressLine1.getText().toString().trim(),
                            edtAddressLine2.getText().toString().trim(),
                            edtArea.getText().toString().trim(),
                            edtCity.getText().toString().trim(),
                            edtCountry.getText().toString().trim(),
                            edtZipCode.getText().toString().trim(),
                            latitude,
                            longitude
                    );
                    call.enqueue(new Callback<AddAddressAPI.ResponseAddress>() {
                        @Override
                        public void onResponse(Call<AddAddressAPI.ResponseAddress> call, Response<AddAddressAPI.ResponseAddress> response) {

                            if (response.isSuccessful()) {
                                if (response.body().getStatus() == 200) {
                                    CustomProgressDialog.getInstance().dismiss();

                                    finish();
                                    if (AddAddressMap.mAddAddressMap != null)
                                        AddAddressMap.mAddAddressMap.finish();
                                    com.ghostFood.fragment.Address.needToRefresh = 1;

                                    //MyActivity.getInstance().OrderConfirmation(AddAddress.this);
                                } else {
                                    CommonFunctions.getInstance().ShowSnackBar(AddAddressMap.this, response.body().getMessage());
                                    CustomProgressDialog.getInstance().dismiss();
                                }
                            } else {
                                CommonFunctions.getInstance().ShowSnackBar(AddAddressMap.this, response.message());
                                CustomProgressDialog.getInstance().dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<AddAddressAPI.ResponseAddress> call, Throwable t) {
                            CustomProgressDialog.getInstance().dismiss();
                            CommonFunctions.getInstance().ShowSnackBar(AddAddressMap.this, Constants.NoInternetConnection);
                        }
                    });
                } else if (addressStatus == ConstantsInternal.Address.Update && !mAddressKey.isEmpty()) {

                    CustomProgressDialog.getInstance().show(AddAddressMap.this);
                    UpdateAddressAPI mAddAddressAPI = ApiConfiguration.getInstance2().getApiBuilder().create(UpdateAddressAPI.class);
                    final Call<UpdateAddressAPI.ResponseUpate> call = mAddAddressAPI.Update(
                            SessionManager.User.getInstance().getKey(),
                            mAddressKey,
                            edtAddressContactName.getText().toString().trim(),
                            edtBuildingName.getText().toString().trim(),
                            edtFlatNo.getText().toString().trim(),
                            edtAddressLine1.getText().toString().trim(),
                            edtAddressLine2.getText().toString().trim(),
                            edtArea.getText().toString().trim(),
                            edtCity.getText().toString().trim(),
                            edtCountry.getText().toString().trim(),
                            edtZipCode.getText().toString().trim(),
                            latitude,
                            longitude
                    );
                    call.enqueue(new Callback<UpdateAddressAPI.ResponseUpate>() {
                        @Override
                        public void onResponse(Call<UpdateAddressAPI.ResponseUpate> call, Response<UpdateAddressAPI.ResponseUpate> response) {

                            if (response.isSuccessful()) {
                                if (response.body().getStatus() == 200) {
                                    CustomProgressDialog.getInstance().dismiss();

                                    finish();
                                    if (AddAddressMap.mAddAddressMap != null)
                                        AddAddressMap.mAddAddressMap.finish();
                                    com.ghostFood.fragment.Address.needToRefresh = 1;

                                    //MyActivity.getInstance().OrderConfirmation(AddAddress.this);
                                } else {
                                    CommonFunctions.getInstance().ShowSnackBar(AddAddressMap.this, response.body().getMessage());
                                    CustomProgressDialog.getInstance().dismiss();
                                }
                            } else {
                                CommonFunctions.getInstance().ShowSnackBar(AddAddressMap.this, response.message());
                                CustomProgressDialog.getInstance().dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<UpdateAddressAPI.ResponseUpate> call, Throwable t) {
                            CustomProgressDialog.getInstance().dismiss();
                            CommonFunctions.getInstance().ShowSnackBar(AddAddressMap.this, Constants.NoInternetConnection);
                        }
                    });

                }
            }
        });

        dialog.show();


    }
}
