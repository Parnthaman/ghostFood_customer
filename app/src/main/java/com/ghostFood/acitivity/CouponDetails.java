package com.ghostFood.acitivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ghostFood.R;
import com.ghostFood.api.CouponsAPI;
import com.ghostFood.callback.CommonCallback;
import com.ghostFood.model.Coupons;
import com.ghostFood.util.CartDB;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.ConstantsInternal;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CouponDetails extends AppCompatActivity {

    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvStartDate)
    TextView tvStartDate;
    @BindView(R.id.tvEndDate)
    TextView tvEndDate;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    private String CouponCode = "";
    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnPrice)
    Button btnPrice;
    @BindView(R.id.rlPrice)
    RelativeLayout rlPrice;
    @BindView(R.id.ivCouponImage)
    SimpleDraweeView ivCouponImage;
    @BindView(R.id.tvCouponTitle)
    TextView tvCouponTitle;
    @BindView(R.id.tvCouponContent)
    TextView tvCouponContent;
    @BindView(R.id.btnShowCode)
    Button btnShowCode;
    @BindView(R.id.tvAvailableBranches)
    TextView tvAvailableBranches;
    @BindView(R.id.tvBranchList)
    TextView tvBranchList;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.ivClose)
    ImageView ivClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_details);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(CouponDetails.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(CouponDetails.this, toolbar);

        initView();


    }

    private void initView() {
        if (getIntent().getExtras() != null && getIntent().getExtras().getSerializable("details") != null) {
            final Coupons coupons = ((Coupons) getIntent().getExtras().getSerializable("details"));

            this.CouponCode = coupons.getCouponCode();
//            tlbarText.setText(Constants.Coupon);
            btnPrice.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(coupons.getCouponPrice()));
            String price = CommonFunctions.getInstance().getIntORFloat(coupons.getCouponPrice());

            if (ConstantsInternal.CouponType.fromInteger(coupons.getCouponType()) == ConstantsInternal.CouponType.OnlinePercentOfferCoupon) {
                tvPrice.setText(price + "%");
            } else {
                tvPrice.setText(CommonFunctions.DeciamlDigitsAfterDotValue.getWithCurrencyCodeOriginal(price));
            }

//            tvCouponTitle.setText(coupons.getCouponName());
            tvCouponTitle.setText(Constants.CouponDescription);
            tlbarText.setText(coupons.getCouponName());
            tvCouponContent.setText(coupons.getCouponDescription());
            btnShowCode.setText(Constants.ShowCode);
            tvAvailableBranches.setText(Constants.AvailableBranches);
            tvDate.setText(Constants.Validity);


            String strDate = "";
            try {
                SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = sdfSource.parse(coupons.getCouponStartDate());
                SimpleDateFormat sdfDestination = new SimpleDateFormat("dd MMM yyyy hh:mm");
                strDate = sdfDestination.format(date);
                System.out.println("Converted date is : " + strDate);
            } catch (ParseException pe) {
                System.out.println("Parse Exception : " + pe);
            }

            String endDate = "";
            try {
                SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = sdfSource.parse(coupons.getCouponEndDate());
                SimpleDateFormat sdfDestination = new SimpleDateFormat("dd MMM yyyy hh:mm");
                endDate = sdfDestination.format(date);
                System.out.println("Converted date is : " + endDate);
            } catch (ParseException pe) {
                System.out.println("Parse Exception : " + pe);
            }

            tvStartDate.setText(Constants.From + " " + ":" + " " + strDate);
            tvEndDate.setText(Constants.to + " " + ":" + " " + endDate);

            if (ConstantsInternal.CouponType.fromInteger(coupons.getCouponType()) == ConstantsInternal.CouponType.OnlineItem) {
                btnShowCode.setVisibility(View.VISIBLE);
                btnShowCode.setText(Constants.AddToCart);
                btnShowCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                        //Coupon Item is hardcoded below
                        //
                        CartDB.getInstance().AddCouponItems(coupons.getCouponKey(), ConstantsInternal.OfferType.CouponItem.getValue(), coupons.getCouponName(), coupons.getCouponPrice(), coupons.getCouponImage(), coupons.getUserScope(),
                                new CommonCallback.Listener() {
                                    @Override
                                    public void onSuccess() {
                                        CommonFunctions.getInstance().ShowSnackBar(CouponDetails.this, Constants.AddedToCart);
                                        Bundle mBundle = new Bundle();
                                        MyActivity.getInstance().Cart(CouponDetails.this, mBundle);
                                    }

                                    @Override
                                    public void onFailure() {
                                        CommonFunctions.getInstance().ShowSnackBar(CouponDetails.this, Constants.ItemAlreadyInCart);
                                    }
                                }
                        );
                    }
                });
            } else if (ConstantsInternal.CouponType.fromInteger(coupons.getCouponType()) == ConstantsInternal.CouponType.OnlineFlatOfferCoupon) {
                btnShowCode.setVisibility(View.VISIBLE);
                btnShowCode.setText(Constants.CopyToClipboard);
                btnShowCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(MyApplication.context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText(Constants.Copied, CouponCode);
                        clipboard.setPrimaryClip(clip);
                        CommonFunctions.getInstance().ShowSnackBar(CouponDetails.this, Constants.CopiedToClipboard);
                    }
                });
            } else if (ConstantsInternal.CouponType.fromInteger(coupons.getCouponType()) == ConstantsInternal.CouponType.OnlinePercentOfferCoupon) {

                btnPrice.setText(coupons.getCouponPrice() + "%");

                btnShowCode.setVisibility(View.VISIBLE);
                btnShowCode.setText(Constants.CopyToClipboard);
                btnShowCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(MyApplication.context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText(Constants.Copied, CouponCode);
                        clipboard.setPrimaryClip(clip);
                        CommonFunctions.getInstance().ShowSnackBar(CouponDetails.this, Constants.CopiedToClipboard);
                    }
                });
            } else if (ConstantsInternal.CouponType.fromInteger(coupons.getCouponType()) == ConstantsInternal.CouponType.PhysicalWithCode) {
                btnShowCode.setVisibility(View.VISIBLE);
                btnShowCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnShowCode.setText(CouponCode);
                    }
                });
            }

            tvAvailableBranches.setVisibility(View.GONE);
            tvBranchList.setVisibility(View.GONE);
            if (coupons.getBranches() != null && coupons.getBranches().size() > 0) {
                tvAvailableBranches.setVisibility(View.VISIBLE);
                tvBranchList.setVisibility(View.VISIBLE);
                String mBranchList = "";
                for (CouponsAPI.Branch branch : coupons.getBranches()) {
                    mBranchList = mBranchList + "<font color =\"red\">\u2022</font>" + branch.getBranchName() + "<br>";
                }

                tvBranchList.setText(CommonFunctions.getInstance().fromHtml(mBranchList));
            }


            FontFunctions.getInstance().FontBabeNeueBold(CouponDetails.this, btnPrice);
            FontFunctions.getInstance().FontBabeNeueBold(CouponDetails.this, tvCouponTitle);
            FontFunctions.getInstance().FontBabeNeueBold(CouponDetails.this, tvAvailableBranches);
            FontFunctions.getInstance().FontBabeNeueBold(CouponDetails.this, btnShowCode);
            FontFunctions.getInstance().FontBalooBhaijaan(CouponDetails.this, tlbarText);
            FontFunctions.getInstance().FontLatoFont(CouponDetails.this, tvCouponContent);
            FontFunctions.getInstance().FontLatoFont(CouponDetails.this, tvBranchList);


            CommonFunctions.getInstance().LoadImageByFresco(ivCouponImage, coupons.getCouponImage());
        } else {
            finish();
        }

    }

    @OnClick({R.id.tlbar_back, R.id.ivClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;
            case R.id.ivClose:
                finish();
                break;
        }
    }


}
