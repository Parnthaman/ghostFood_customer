package com.ghostFood.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.api.ChangePasswordAPI;
import com.ghostFood.api.ProfileUpdateApi;
import com.ghostFood.callback.ProfileCallback;
import com.ghostFood.events.EBProfile;
import com.ghostFood.util.ApiConfiguration;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.CustomProgressDialog;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.ProfileImage;
import com.ghostFood.util.SessionManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    //  EventBus myEventBus = new EventBus();

    String imagePath1 = "";
    @BindView(R.id.txtFirstName)
    TextView txtFirstName;
    @BindView(R.id.edtFirstName)
    EditText edtFirstName;
    @BindView(R.id.txtLastName)
    TextView txtLastName;
    @BindView(R.id.edtLastName)
    EditText edtLastName;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.txtMobileNumber)
    TextView txtMobileNumber;
    @BindView(R.id.edtMobileNumber)
    EditText edtMobileNumber;
    @BindView(R.id.tvupdate)
    TextView tvupdate;
    @BindView(R.id.btnSave)
    LinearLayout btnSave;
    @BindView(R.id.card_view1)
    CardView cardView1;
    @BindView(R.id.tvChangePassword)
    TextView tvChangePassword;
    @BindView(R.id.txtOldPassword)
    TextView txtOldPassword;
    @BindView(R.id.edOldPassword)
    EditText edOldPassword;
    @BindView(R.id.llOldPassword)
    LinearLayout llOldPassword;
    @BindView(R.id.txtNewPassword)
    TextView txtNewPassword;
    @BindView(R.id.edNewPassword)
    EditText edNewPassword;
    @BindView(R.id.llNewPassword)
    LinearLayout llNewPassword;
    @BindView(R.id.txtConfirmPassword)
    TextView txtConfirmPassword;
    @BindView(R.id.edConfirmPassword)
    EditText edConfirmPassword;
    @BindView(R.id.llConfirmPassword)
    LinearLayout llConfirmPassword;
    @BindView(R.id.tvUpdatePassword)
    TextView tvUpdatePassword;
    @BindView(R.id.rly_UpdatePassword)
    LinearLayout rlyUpdatePassword;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ConstantsInternal.DefaultPaymentReceiveIn defaultPaymentReceiveIn = ConstantsInternal.DefaultPaymentReceiveIn.WALLET;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    myEventBus.register(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//
        unbinder = ButterKnife.bind(this, view);
        //CommonFunctions.getInstance().ChangeFontFragment(view);


       /* Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView ivAddNewAddress = (ImageView) toolbar.findViewById(R.id.tlbar_add);
        ivAddNewAddress.setVisibility(View.INVISIBLE);*/
        initViews();


        return view;
    }

    private void initViews() {
        this.txtFirstName.setText(Constants.FirstName);
        this.txtLastName.setText(Constants.LastName);
        this.txtEmail.setText(Constants.Email);
        this.txtMobileNumber.setText(Constants.MobileNumber);

        tvupdate.setText(Constants.UpdateProfile);

        this.edtFirstName.setText(SessionManager.User.getInstance().getFirstName());
        this.edtLastName.setText(SessionManager.User.getInstance().getLastName());
        this.edtEmail.setText(SessionManager.User.getInstance().getEmail());
        this.edtMobileNumber.setText(SessionManager.User.getInstance().getMobile());


        tvChangePassword.setText(Constants.ChangePassword);
        tvUpdatePassword.setText(Constants.UpdatePassword);

        txtOldPassword.setText(Constants.OldPassword);
        txtNewPassword.setText(Constants.NewPassword);
        txtConfirmPassword.setText(Constants.ConfirmPassword);


        FontFunctions.getInstance().FontSketchBold(getActivity(), tvChangePassword);
        FontFunctions.getInstance().FontLatoFont(getActivity(), edOldPassword);
        FontFunctions.getInstance().FontLatoFont(getActivity(), edNewPassword);
        FontFunctions.getInstance().FontLatoFont(getActivity(), edConfirmPassword);

        FontFunctions.getInstance().FontKGSecondSketch(getActivity(), tvUpdatePassword);


        //CommonFunctions.getInstance().SetProfilePicture(this.ivProfilepicture);
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        } else {
            /*throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
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
    public void onDestroy() {
        super.onDestroy();
        //   myEventBus.unregister(this);
    }

    private void callUpdateProfileApi() {
        CustomProgressDialog.getInstance().show(getActivity());
        ProfileUpdateApi mProfileUpdateApi = ApiConfiguration.getInstance2().getApiBuilder().create(ProfileUpdateApi.class);
        final Call<ProfileUpdateApi.ResponseProfileUpdate> call = mProfileUpdateApi.Update(
                SessionManager.User.getInstance().getKey(),
                edtFirstName.getText().toString(),
                edtLastName.getText().toString(),
                edtMobileNumber.getText().toString(),
                edtEmail.getText().toString()
        );
        call.enqueue(new Callback<ProfileUpdateApi.ResponseProfileUpdate>() {
            @Override
            public void onResponse(Call<ProfileUpdateApi.ResponseProfileUpdate> call, Response<ProfileUpdateApi.ResponseProfileUpdate> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        CustomProgressDialog.getInstance().dismiss();
                        SessionManager.User.getInstance().setFirstName(edtFirstName.getText().toString());
                        SessionManager.User.getInstance().setLastName(edtLastName.getText().toString());
                        SessionManager.User.getInstance().setEmailAddress(edtEmail.getText().toString());
                        SessionManager.User.getInstance().setMobileNumber(edtMobileNumber.getText().toString());

                        EventBus.getDefault().post(new EBProfile());
                        CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.body().getMessage());
                        CustomProgressDialog.getInstance().dismiss();
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
            public void onFailure(Call<ProfileUpdateApi.ResponseProfileUpdate> call, Throwable t) {
                CustomProgressDialog.getInstance().dismiss();
                CommonFunctions.getInstance().ShowSnackBar(getActivity(), t.toString());
            }
        });

    }

    @OnClick({R.id.btnSave, R.id.tvChangePassword, R.id.rly_UpdatePassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                callUpdateProfileApi();
                break;
            case R.id.tvChangePassword:
//                MyActivity.getInstance().ProfileChangePassword(getActivity());
                break;
            case R.id.rly_UpdatePassword:
                ChangePassword();
                break;

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        initViews();
    }

    private void ChangePassword() {
        if (edOldPassword.getText().toString().trim().isEmpty()) {
            CommonFunctions.getInstance().ShowSnackBar(getActivity(), Constants.OldPasswordShouldNotBeEmpty);
        } else if (edNewPassword.getText().toString().trim().isEmpty()) {
            CommonFunctions.getInstance().ShowSnackBar(getActivity(), Constants.NewPasswordShouldNotBeEmpty);
        } else if (edConfirmPassword.getText().toString().trim().isEmpty()) {
            CommonFunctions.getInstance().ShowSnackBar(getActivity(), Constants.ConfirmPasswordShouldNotBeEmpty);
        } else if (edNewPassword.getText().toString().trim().equals(edConfirmPassword.getText().toString().trim())) {
            CustomProgressDialog.getInstance().show(getActivity());
            ChangePasswordAPI mChangePasswordAPI = ApiConfiguration.getInstance().getApiBuilder().create(ChangePasswordAPI.class);
            final Call<ChangePasswordAPI.ResponseChangePassword> call = mChangePasswordAPI.Update(
                    SessionManager.User.getInstance().getKey(),
                    edOldPassword.getText().toString().trim(),
                    edNewPassword.getText().toString().trim(),
                    edConfirmPassword.getText().toString().trim()
            );
            call.enqueue(new Callback<ChangePasswordAPI.ResponseChangePassword>() {
                @Override
                public void onResponse(Call<ChangePasswordAPI.ResponseChangePassword> call, Response<ChangePasswordAPI.ResponseChangePassword> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            CommonFunctions.getInstance().ShowSnackBar(getActivity(), response.body().getMessage());
                            CustomProgressDialog.getInstance().dismiss();
                            CommonFunctions.getInstance().FinishActivityWithDelay(getActivity());
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
                public void onFailure(Call<ChangePasswordAPI.ResponseChangePassword> call, Throwable t) {
                    CustomProgressDialog.getInstance().dismiss();
                    CommonFunctions.getInstance().ShowSnackBar(getActivity(), Constants.NoInternetConnection);
                }
            });
        } else {
            CommonFunctions.getInstance().ShowSnackBar(getActivity(), Constants.PasswordNotMatching);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void ShowProfilePictureDialog() {

        //CommonFunctions.Permissions.getInstance().ShowPermission(getActivity(), CommonFunctions.Permissions.Type.STORAGE);

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = getActivity().getLayoutInflater();
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
                    if (!Settings.System.canWrite(getActivity())) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        }, 2910);
                    } else {
                        ProfileImage profileImage = new ProfileImage();
                        imagePath1 = profileImage.paymentcameraIntent(getActivity());
                    }
                } else {
                    ProfileImage profileImage = new ProfileImage();
                    imagePath1 = profileImage.paymentcameraIntent(getActivity());

                }
            }
        });

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.System.canWrite(getActivity())) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 4044);
                    } else {
                        ProfileImage profileImage = new ProfileImage();
                        profileImage.openGallery(getActivity());
                    }
                } else {
                    ProfileImage profileImage = new ProfileImage();
                    profileImage.openGallery(getActivity());
                }
            }
        });

        btnRemovePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonFunctions.getInstance().RemoveProfileImage(getContext(), new ProfileCallback.ImageListener() {
                    @Override
                    public void onSuccess(String profileImageUrl) {
                        //   CommonFunctions.getInstance().SetProfilePicture(ivProfilepicture);
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

   /* @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ProfileImage profileImage = new ProfileImage();
        profileImage.ProcessProfileImage(getActivity(), imagePath1, requestCode, resultCode, data, getActivity().RESULT_OK, ivProfilepicture);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EBProfileImage event) {
        CommonFunctions.getInstance().SetProfilePicture(ivProfilepicture);
    }
*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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
                    profileImage.openGallery(getActivity());
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
                        this.imagePath1 = profileImage.paymentcameraIntent(getActivity());
                    }

                } else {
                    Log.e("Permission", "Denied");
                }
                return;
            }
        }
    }

}
