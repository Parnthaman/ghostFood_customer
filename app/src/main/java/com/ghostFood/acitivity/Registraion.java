package com.ghostFood.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ghostFood.R;
import com.ghostFood.api.RegistraionUserApi;
import com.ghostFood.api.UserRegistrationExistsApi;
import com.ghostFood.callback.CommonCallback;
import com.ghostFood.events.EBLogin;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Registraion extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvSignup)
    TextView tvSignup;
    @BindView(R.id.edt_firstname)
    EditText edtFirstname;
    @BindView(R.id.edt_lastname)
    EditText edtLastname;
    @BindView(R.id.edt_emailId)
    EditText edtEmailId;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_confirmpassword)
    EditText edtConfirmpassword;
    @BindView(R.id.edt_mobilenumber)
    EditText edtMobilenumber;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.tv_Or)
    TextView tvOr;
    @BindView(R.id.tvAlreadyAHungryBunny)
    TextView tvAlreadyAHungryBunny;
    @BindView(R.id.tvLogin)
    TextView tvLogin;

    CallbackManager callbackManager;
    @BindView(R.id.ivFacebook)
    ImageView ivFacebook;
    @BindView(R.id.ivTwitter)
    ImageView ivTwitter;
    @BindView(R.id.ivGooglePlus)
    ImageView ivGooglePlus;
    @BindView(R.id.llFirstName)
    LinearLayout llFirstName;
    @BindView(R.id.llLastName)
    LinearLayout llLastName;
    @BindView(R.id.llEmailId)
    LinearLayout llEmailId;
    @BindView(R.id.llPassword)
    LinearLayout llPassword;
    @BindView(R.id.llConfirmPassword)
    LinearLayout llConfirmPassword;
    @BindView(R.id.llMobileNumber)
    LinearLayout llMobileNumber;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.llUsername)
    LinearLayout llUsername;

    GoogleSignInOptions gso;
    @BindView(R.id.tvTandC)
    TextView tvTandC;
    private String from = "";
    ConstantsInternal.RegistrationType registrationType = ConstantsInternal.RegistrationType.Normal;
    GoogleApiClient mGoogleApiClient;
    Integer RC_SIGN_IN = 525;

    TwitterAuthClient mTwitterAuthClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraion_user);

        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(Registraion.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(Registraion.this, toolbar);
        initString();

        if (getIntent().getExtras() != null && getIntent().getExtras().getString("from") != null) {
            from = getIntent().getExtras().getString("from");
        }
        this.registrationType = ConstantsInternal.RegistrationType.Normal;
        this.edtFirstname.setTag("");
        this.edtLastname.setTag("");
        LoadFacebook();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //LoadTwitter();
        mTwitterAuthClient = new TwitterAuthClient();
    }

   /* private void LoadTwitter() {
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls

                Toast.makeText(Registraion.this, "Andorid "+result.data.getUserName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(Registraion.this, "Andorid "+exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/


    private void LoadFacebook() {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    private ProfileTracker mProfileTracker;

                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        if (Profile.getCurrentProfile() == null) {
                            mProfileTracker = new ProfileTracker() {
                                @Override
                                protected void onCurrentProfileChanged(Profile profile, final Profile profile2) {

                                    if (profile2 != null) {
                                        isUserExists(profile2.getId(), new CommonCallback.Listener() {
                                            @Override
                                            public void onSuccess() {
                                                if (!from.trim().equalsIgnoreCase("loginact")) {
                                                    MyActivity.getInstance().Home(Registraion.this);
                                                } else {
                                                    EventBus.getDefault().post(new EBLogin());
                                                    finish();
                                                }
                                            }

                                            @Override
                                            public void onFailure() {

                                                llPassword.setVisibility(View.GONE);
                                                llConfirmPassword.setVisibility(View.GONE);
                                                llUsername.setVisibility(View.GONE);

                                                edtUsername.setText(profile2.getId());
                                                edtFirstname.setText(profile2.getFirstName());
                                                edtLastname.setText(profile2.getLastName());

                                                edtFirstname.setTag(profile2.getId());
                                                edtLastname.setTag(profile2.getLinkUri().toString());
                                            }
                                        });


                                    }
                                    mProfileTracker.stopTracking();
                                }
                            };
                            // no need to call startTracking() on mProfileTracker
                            // because it is called by its constructor, internally.
                        } else {
                            final Profile profile = Profile.getCurrentProfile();

                            if (profile != null) {
                                isUserExists(profile.getId(), new CommonCallback.Listener() {
                                    @Override
                                    public void onSuccess() {
                                        if (!from.trim().equalsIgnoreCase("loginact")) {
                                            MyActivity.getInstance().Home(Registraion.this);
                                        } else {
                                            EventBus.getDefault().post(new EBLogin());
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure() {
                                        llPassword.setVisibility(View.GONE);
                                        llConfirmPassword.setVisibility(View.GONE);
                                        llUsername.setVisibility(View.GONE);

                                        edtUsername.setText(profile.getId());
                                        edtFirstname.setText(profile.getFirstName());
                                        edtLastname.setText(profile.getLastName());

                                        edtFirstname.setTag(profile.getId());
                                        edtLastname.setTag(profile.getLinkUri().toString());
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancel() {
                        // login cancelled
                        Toast.makeText(Registraion.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // login error
                        Toast.makeText(Registraion.this, "Error  " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != RC_SIGN_IN) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        //loginButton.onActivityResult(requestCode, resultCode, data);
        mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            final GoogleSignInAccount acct = result.getSignInAccount();
            isUserExists(acct.getId(), new CommonCallback.Listener() {

                @Override
                public void onSuccess() {
                    if (!from.trim().equalsIgnoreCase("loginact")) {
                        MyActivity.getInstance().Home(Registraion.this);
                    } else {
                        EventBus.getDefault().post(new EBLogin());
                        finish();
                    }
                }

                @Override
                public void onFailure() {
                    llPassword.setVisibility(View.GONE);
                    llConfirmPassword.setVisibility(View.GONE);
                    llUsername.setVisibility(View.GONE);

                    edtUsername.setText(acct.getId());
                    edtFirstname.setText(acct.getGivenName());
                    edtLastname.setText(acct.getFamilyName());
                    edtEmailId.setText(acct.getEmail());

                    edtFirstname.setTag(acct.getId());
                    edtLastname.setTag(acct.getId());

                    //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
                    //updateUI(true);
                }
            });


        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    private void initString() {

        tvTandC.setText(Constants.ByRegisteringYouAreAgreeingToOurTermsAndConditions);

        tlbarText.setVisibility(View.GONE);
        tlbarText.setText(Constants.Registration);

        tvSignup.setText(Constants.Signup);
        edtUsername.setHint(Constants.Username);
        edtFirstname.setHint(Constants.FirstName);
        edtLastname.setHint(Constants.LastName);
        edtEmailId.setHint(Constants.Email);
        edtPassword.setHint(Constants.Password);
        edtConfirmpassword.setHint(Constants.ConfirmPassword);
        edtMobilenumber.setHint(Constants.MobileNumber);
        btnSignup.setText(Constants.Signup);

        tvAlreadyAHungryBunny.setText(Constants.AlreadyaHungryBunny);
        tvLogin.setText(Constants.Login);

        tvOr.setText("(" + Constants.Or + ")");

        FontFunctions.getInstance().FontSketchBold(Registraion.this, tvSignup);
        FontFunctions.getInstance().FontKGSecondSketch(Registraion.this, btnSignup);
        FontFunctions.getInstance().FontLatoFont(Registraion.this, edtUsername);
        FontFunctions.getInstance().FontLatoFont(Registraion.this, edtFirstname);
        FontFunctions.getInstance().FontLatoFont(Registraion.this, edtLastname);
        FontFunctions.getInstance().FontLatoFont(Registraion.this, edtEmailId);
        FontFunctions.getInstance().FontLatoFont(Registraion.this, edtPassword);
        FontFunctions.getInstance().FontLatoFont(Registraion.this, edtConfirmpassword);
        FontFunctions.getInstance().FontLatoFont(Registraion.this, edtMobilenumber);

        FontFunctions.getInstance().FontCMMidNight(Registraion.this, tvOr);
        FontFunctions.getInstance().FontCMMidNight(Registraion.this, tvAlreadyAHungryBunny);
        FontFunctions.getInstance().FontCMMidNight(Registraion.this, tvTandC);
        FontFunctions.getInstance().FontCMMidNight(Registraion.this, tvLogin);
    }

    @OnClick({R.id.tvTandC, R.id.btn_signup, R.id.tlbar_back, R.id.tvAlreadyAHungryBunny, R.id.tvLogin, R.id.ivFacebook, R.id.ivTwitter, R.id.ivGooglePlus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvTandC:
                MyActivity.getInstance().TermsAndCondition(Registraion.this);
                break;
            case R.id.btn_signup:
                CallRegisterApi();
                break;
            case R.id.tlbar_back:
                finish();
                break;
            case R.id.tvAlreadyAHungryBunny:
                finish();
                break;
            case R.id.tvLogin:
                finish();
                break;
            case R.id.ivFacebook:
                this.registrationType = ConstantsInternal.RegistrationType.Facebook;
                LoginManager.getInstance().logOut();
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
                break;
            case R.id.ivTwitter:
                this.registrationType = ConstantsInternal.RegistrationType.Twitter;

                callTwitter();

                /*TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                TwitterAuthClient authClient = new TwitterAuthClient();
                authClient.requestEmail(session, new com.twitter.sdk.android.core.Callback<String>() {
                    @Override
                    public void success(Result<String> result) {
                        // Do something with the result, which provides the email address
                        Toast.makeText(Registraion.this, "Andorid "+result.data, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Do something on failure
                        Toast.makeText(Registraion.this, "Andorid "+exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/


                break;
            case R.id.ivGooglePlus:
                this.registrationType = ConstantsInternal.RegistrationType.GooglePlus;


                signIn();
                break;

        }
    }

    private void callTwitter() {
        mTwitterAuthClient.authorize(this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

            @Override
            public void success(Result<TwitterSession> twitterSessionResult) {

                final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                isUserExists(String.valueOf(session.getUserId()), new CommonCallback.Listener() {

                    @Override
                    public void onSuccess() {
                        if (!from.trim().equalsIgnoreCase("loginact")) {
                            MyActivity.getInstance().Home(Registraion.this);
                        } else {
                            EventBus.getDefault().post(new EBLogin());
                            finish();
                        }
                    }

                    @Override
                    public void onFailure() {
                        TwitterAuthClient authClient = new TwitterAuthClient();

                        authClient.requestEmail(session, new com.twitter.sdk.android.core.Callback<String>()

                        {
                            @Override
                            public void success(Result<String> result) {

                                llPassword.setVisibility(View.GONE);
                                llConfirmPassword.setVisibility(View.GONE);
                                llUsername.setVisibility(View.GONE);

                                edtUsername.setText(session.getUserName());
                                edtFirstname.setText("");
                                edtLastname.setText("");
                                edtEmailId.setText(result.data);

                                edtFirstname.setTag(String.valueOf(session.getId()));
                                edtLastname.setTag(String.valueOf(session.getId()));
                            }

                            @Override
                            public void failure(TwitterException exception) {
                                // Do something on failure
                                Toast.makeText(Registraion.this, "Andorid " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                });
            }

            @Override
            public void failure(TwitterException e) {
                e.printStackTrace();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {

    }

    private void isUserExists(String key, final CommonCallback.Listener callback) {

        CustomProgressDialog.getInstance().show(Registraion.this);
        UserRegistrationExistsApi mUserRegistrationExistsApi = ApiConfiguration.getInstance().getApiBuilder().create(UserRegistrationExistsApi.class);
        final Call<UserRegistrationExistsApi.ResponseUserRegistrationExists> call = mUserRegistrationExistsApi.Exists(this.registrationType.getValue(), key, SessionManager.User .getInstance().getDeviceID(), "1");
        call.enqueue(new Callback<UserRegistrationExistsApi.ResponseUserRegistrationExists>() {
            @Override
            public void onResponse(Call<UserRegistrationExistsApi.ResponseUserRegistrationExists> call, Response<UserRegistrationExistsApi.ResponseUserRegistrationExists> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();
                        callback.onSuccess();
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

                                        if (!from.trim().equalsIgnoreCase("loginact")) {
                                            MyActivity.getInstance().Home(Registraion.this);
                                        } else {
                                            EventBus.getDefault().post(new EBLogin());
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                }
                        );
                    } else {
                        callback.onFailure();
                        //CommonFunctions.getInstance().ShowSnackBar(Registraion.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    callback.onFailure();
                    //CommonFunctions.getInstance().ShowSnackBar(Registraion.this, response.message());
                    CustomProgressDialog.getInstance().dismiss();
                }

            }

            @Override
            public void onFailure(Call<UserRegistrationExistsApi.ResponseUserRegistrationExists> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                callback.onFailure();
                CommonFunctions.getInstance().ShowSnackBar(Registraion.this, Constants.NoInternetConnection);
            }
        });

    }

    private void CallRegisterApi() {

        if (edtUsername.getText().toString().isEmpty()) {
            CommonFunctions.getInstance().EmptyField(Registraion.this, Constants.Username);
            return;
        }
        if (edtFirstname.getText().toString().isEmpty()) {
            CommonFunctions.getInstance().EmptyField(Registraion.this, Constants.FirstName);
            return;
        }
        if (edtLastname.getText().toString().isEmpty()) {
            CommonFunctions.getInstance().EmptyField(Registraion.this, Constants.LastName);
            return;
        }
        /*if (edtEmailId.getText().toString().isEmpty()) {
            CommonFunctions.getInstance().EmptyField(Registraion.this, Constants.Email);
            return;
        }
        if (!CommonFunctions.getInstance().isValidEmail(edtEmailId.getText().toString())) {
            CommonFunctions.getInstance().ValidField(Registraion.this, Constants.Email);
            return;
        }*/
        if (edtMobilenumber.getText().toString().isEmpty()) {
            CommonFunctions.getInstance().EmptyField(Registraion.this, Constants.MobileNumber);
            return;
        }

        if (edtMobilenumber.getText().toString().length() < 7 && edtMobilenumber.getText().toString().length() > 16) {
            CommonFunctions.getInstance().ValidField(Registraion.this, Constants.MobileNumber);
            return;
        }

        if (ConstantsInternal.RegistrationType.Normal.getValue() == registrationType.getValue()) {
            if (edtPassword.getText().toString().isEmpty()) {
                CommonFunctions.getInstance().EmptyField(Registraion.this, Constants.Password);
                return;
            }

            if (edtPassword.getText().toString().length() < 6) {
                CommonFunctions.getInstance().ValidField(Registraion.this, Constants.Password);
                return;
            }

            if (edtConfirmpassword.getText().toString().isEmpty()) {
                CommonFunctions.getInstance().EmptyField(Registraion.this, Constants.ConfirmPassword);
                return;
            }
            if (edtConfirmpassword.getText().toString().length() < 6) {
                CommonFunctions.getInstance().ValidField(Registraion.this, Constants.ConfirmPassword);
                return;
            }

            if (!edtPassword.getText().toString().equals(edtConfirmpassword.getText().toString())) {
                CommonFunctions.getInstance().PasswordMatch(Registraion.this, Constants.Password, Constants.ConfirmPassword);
                return;
            }
        }


        CustomProgressDialog.getInstance().show(Registraion.this);
        RegistraionUserApi mRegistraionUserApi = ApiConfiguration.getInstance2().getApiBuilder().create(RegistraionUserApi.class);
        final Call<RegistraionUserApi.ResponseRegistraionUser> call = mRegistraionUserApi.Register(
                registrationType.getValue(),
                this.edtUsername.getText().toString(),
                this.edtFirstname.getText().toString(),
                this.edtLastname.getText().toString(),
                this.edtEmailId.getText().toString(),
                this.edtPassword.getText().toString(),
                this.edtConfirmpassword.getText().toString(),
                this.edtMobilenumber.getText().toString(),
                this.edtFirstname.getTag().toString(),
                this.edtLastname.getTag().toString(),
                "",
                ConstantsInternal.DeviceTypeId,
                SessionManager.User.getInstance().getDeviceID(),
                "1",
                ConstantsInternal.DeviceTypeId
        );

        call.enqueue(new Callback<RegistraionUserApi.ResponseRegistraionUser>() {
            @Override
            public void onResponse(Call<RegistraionUserApi.ResponseRegistraionUser> call, Response<RegistraionUserApi.ResponseRegistraionUser> response) {
                CustomProgressDialog.getInstance().dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(Registraion.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();
                        CommonFunctions.getInstance().ShowSnackBar(Registraion.this, response.body().getMessage());
                        finish();
                    } else {
                        CommonFunctions.getInstance().ShowSnackBar(Registraion.this, response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
                    }
                } else {
                    CommonFunctions.getInstance().ShowSnackBar(Registraion.this, response.message());
                }
            }

            @Override
            public void onFailure(Call<RegistraionUserApi.ResponseRegistraionUser> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(Registraion.this, Constants.NoInternetConnection);
            }
        });

    }

}
