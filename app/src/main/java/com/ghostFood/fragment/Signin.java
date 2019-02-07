package com.ghostFood.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.ghostFood.R;
import com.ghostFood.api.LoginApi;
import com.ghostFood.callback.CommonCallback;
import com.ghostFood.events.EBLogin;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Signin extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.txt_Emailid)
    TextView txtEmailid;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.txt_password)
    TextView txtPassword;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.tvSignin)
    TextView tvSignin;
    @BindView(R.id.rly_login)
    LinearLayout rlyLogin;
    @BindView(R.id.tvForgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.socialnetwork)
    TextView socialnetwork;
    @BindView(R.id.tvfacebook)
    TextView tvfacebook;
    @BindView(R.id.rly_facebook)
    LinearLayout rlyFacebook;
    @BindView(R.id.tv_google_plus)
    TextView tvGooglePlus;
    @BindView(R.id.rly_googleplus)
    LinearLayout rlyGoogleplus;
    @BindView(R.id.tvor)
    TextView tvor;
    @BindView(R.id.imLoginFacebook)
    LoginButton imLoginFacebook;

    CallbackManager callbackManager;
    @BindView(R.id.btn_GoogleSignin)
    SignInButton btnGoogleSignin;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    private String mParam1;
    private String mParam2;
    private String from = "";
    private OnFragmentInteractionListener mListener;

    public Signin() {
    }

    public static Signin newInstance(String param1, String param2) {
        Signin fragment = new Signin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        unbinder = ButterKnife.bind(this, view);
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        AppEventsLogger.activateApp(getActivity());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        initview();
        return view;
    }

    private void initview() {

        txtEmailid.setText(Constants.Email + " / " + Constants.MobileNumber);
        txtPassword.setText(Constants.Password);
        tvForgetPassword.setText(Constants.ForgotYourPassword);
        tvSignin.setText(Constants.Signin);

    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @OnClick({R.id.edt_username, R.id.edt_password, R.id.rly_login, R.id.rly_facebook, R.id.tv_google_plus, R.id.tvForgetPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edt_username:
                break;
            case R.id.edt_password:
                break;
            case R.id.rly_login:
                callLoginApi();
                break;
            case R.id.rly_facebook:
//                callFacebook();
                break;
            case R.id.tv_google_plus:
//                btnGoogleSignin.performClick();
//                @SuppressLint("RestrictedApi") Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
            case R.id.tvForgetPassword:
                MyActivity.getInstance().ForgotPassword(getActivity());
                break;
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            Log.e("nkjans", account.getEmail());
//
//            // Signed in successfully, show authenticated UI.
//            updateUI(account);
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w("ncjjcjd", "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
//        }

        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            final String personName = acct.getDisplayName();
            String personPhotoUrl = "";
            final String email = acct.getEmail();
            final String id = acct.getId();
            final String id_token = acct.getIdToken();

            Uri personPhotoUri = acct.getPhotoUrl();
            if (personPhotoUri != null) {
                personPhotoUrl = acct.getPhotoUrl().toString();
               /* Glide.with(getApplicationContext()).load(personPhotoUrl)
                        .thumbnail(0.5f)
                        .into(imgProfilePic);*/
            }

            Log.e("GooglePlus_Name-->", personName);
            Log.e("GooglePlus_email-->", email);
            Log.e("-->", personPhotoUrl + "");
            Log.e("GooglePlus_id-->", id + "");
            Log.e("GooglePlus_id_token-->", id_token + "");

            final String finalPersonPhotoUrl = personPhotoUrl;


//            final String finalPersonPhotoUrl = personPhotoUrl;
//            CommonApiCalls.getInstance().socialLoginExist(LoginActivity.this, "4", id, new CommonCallback.Listener() {
//                @Override
//                public void onSuccess(Object object) {
//                    SocialLoginExistApiResponse body = (SocialLoginExistApiResponse) object;
//
//                    if (body.getData().getUserExist().equals("1")) {
//
//                    } else {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("name", personName);
//                        bundle.putString("emailID", email);
//                        bundle.putString("googleID", id);
//                        bundle.putString("profileImage", finalPersonPhotoUrl);
//                        bundle.putString("token", id_token);
//                        bundle.putString("registrationType", "4");
//                        CommonFunctions.getInstance().newIntent(LoginActivity.this, RegistrationActivity.class, bundle, true);
//                    }
//
//                }
//
//                @Override
//                public void onFailure(String reason) {
//
//                }
//            });


//            ApiLibrary.fbUserExists(customProgressDialog, id, personName, id_token, "4");

        } else {
            //If login fails
            Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
        }


    }

    private void callFacebook() {
        imLoginFacebook.performClick();
        imLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.e("name", response.toString());


                                try {
                                    String email = "";
                                    String birthday = "";
                                    String id = "";
                                    String firstName = "";
                                    String lastName = "";


                                    try {
                                        email = object.getString("email") == null ? "" : object.getString("email");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        firstName = object.getString("name") == null ? "" : object.getString("name");
                                        lastName = object.getString("last_name") == null ? "" : object.getString("last_name");
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                    try {
                                        birthday = object.getString("birthday") == null ? "" : object.getString("birthday");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        id = object.getString("id") == null ? "" : object.getString("id");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    AccessToken token = AccessToken.getCurrentAccessToken();
                                    String mToken = null;
                                    if (token != null) {
                                        mToken = token.getToken();
                                    }
                                    String imageUrl = "https://graph.facebook.com/" + id + "/picture?type=large";
//                                    signInToTheApp(email, firstName, lastName, birthday, id, mToken, imageUrl);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    if (CustomProgressDialog.getInstance().isShowing()) {
                                        CustomProgressDialog.getInstance().dismiss();
                                    }
                                }


                                // Application code

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,birthday,gender");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    private void callLoginApi() {


        if (edtUsername.getText().toString().isEmpty()) {
            CommonFunctions.getInstance().EmptyField(getActivity(), Constants.UsernameHint);
        } else if (edtPassword.getText().toString().isEmpty()) {
            CommonFunctions.getInstance().EmptyField(getActivity(), Constants.Password);
        } else {
            CustomProgressDialog.getInstance().show(getActivity());
            LoginApi mLoginApi = ApiConfiguration.getInstance().getApiBuilder().create(LoginApi.class);
            final Call<LoginApi.ResponseLogin> call = mLoginApi.Login(edtUsername.getText().toString(), edtPassword.getText().toString(), "deivceid", "1");
            call.enqueue(new Callback<LoginApi.ResponseLogin>() {
                @Override
                public void onResponse(Call<LoginApi.ResponseLogin> call, final Response<LoginApi.ResponseLogin> response) {

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
                                            EventBus.getDefault().post(new EBLogin());

                                            if (!from.trim().equalsIgnoreCase("loginact")) {
                                            /*FragmentManager manager = getChildFragmentManager();
                                            FragmentTransaction transaction = manager.beginTransaction();
                                            transaction.add(R.id.container, new Myaccount());
                                            transaction.commit();*/
//                                                CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.body().getMessage());
                                                MyActivity.getInstance().LoadHome(getActivity());
                                                // getActivity().finish();
                                                //MyActivity.getInstance().Home(getActivity());
                                                // getChildFragmentManager().beginTransaction().replace(R.id.container, new Myaccount()).commit();
                                            } else {
                                                getActivity().finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure() {
                                            CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.body().getMessage());
                                        }
                                    }
                            );
                        } else {
                            CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.body().getMessage());
                            CustomProgressDialog.getInstance().dismiss();
                        }
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.message());
                        CustomProgressDialog.getInstance().dismiss();
                    }

                }

                @Override
                public void onFailure(Call<LoginApi.ResponseLogin> call, Throwable t) {
                    CustomProgressDialog.getInstance().dismiss();
                    CommonFunctions.getInstance().ShowSnackBar(getActivity(), t.toString());
                }
            });

        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
