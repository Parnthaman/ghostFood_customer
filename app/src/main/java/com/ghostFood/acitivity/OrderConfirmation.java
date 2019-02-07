package com.ghostFood.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.db.Items;
import com.ghostFood.db.SpecialItems;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;
import com.ghostFood.util.SessionManager;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class OrderConfirmation extends AppCompatActivity {

    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvSuccessMessage)
    TextView tvSuccessMessage;
    @BindView(R.id.ivStatusImage)
    ImageView ivStatusImage;
    @BindView(R.id.tvReferenceNumber)
    TextView tvReferenceNumber;
    @BindView(R.id.tvConfirmationNote)
    TextView tvConfirmationNote;
    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tvConfirmationMailTxt)
    TextView tvConfirmationMailTxt;
    @BindView(R.id.tvConfirmationMail)
    TextView tvConfirmationMail;
    @BindView(R.id.tvHome)
    TextView tvHome;
    @BindView(R.id.rly_home)
    LinearLayout rlyHome;

    private String orderNumber, loyaltyPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(OrderConfirmation.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(OrderConfirmation.this, toolbar);


        orderNumber = getIntent().getExtras() != null && getIntent().getExtras().getString("order_number") != null ? getIntent().getExtras().getString("order_number") : "";
        loyaltyPoints = getIntent().getExtras() != null && getIntent().getExtras().getString("loyalty_points") != null ? getIntent().getExtras().getString("loyalty_points") : "";

        initView();
    }

    private void initView() {

        tlbarText.setText(Constants.Confirmation);
        tlbarBack.setVisibility(View.GONE);

        tvSuccessMessage.setText(Constants.YourOrderPlacedSuccessfully);
        tvReferenceNumber.setText(CommonFunctions.getInstance().fromHtml(MessageFormat.format(Constants.OrderRefNoWithLoyaltyPoints, orderNumber, loyaltyPoints)));
        tvHome.setText(Constants.Home);
        tvConfirmationNote.setText(Constants.ConfirmationNote);
        tvConfirmationMailTxt.setText(Constants.ConfirmationMailSentTo);
        tvConfirmationMail.setText(SessionManager.User.getInstance().getEmail());
        FontFunctions.getInstance().FontBalooBhaijaan(OrderConfirmation.this, tlbarText);
        FontFunctions.getInstance().FontBerlin(OrderConfirmation.this, tvSuccessMessage);
        FontFunctions.getInstance().FontBerlin(OrderConfirmation.this, tvReferenceNumber);
        FontFunctions.getInstance().FontBabeNeueBold(OrderConfirmation.this, tvHome);
        FontFunctions.getInstance().FontLatoFont(OrderConfirmation.this, tvConfirmationNote);
    }

    @OnClick({R.id.rly_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rly_home:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
        if (CategoryListing.mItemListingActivity != null) {
            CategoryListing.mItemListingActivity.finish();
        }
        if (CheckOut.mCheckoutActivity != null) {
            CheckOut.mCheckoutActivity.finish();
        }
        if (Cart.mCartActivity != null) {
            Cart.mCartActivity.finish();
        }
        if (ItemDetails.mItemDetailsActivity != null) {
            ItemDetails.mItemDetailsActivity.finish();
        }
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Items.class);
                realm.delete(SpecialItems.class);
            }
        });
        MyActivity.getInstance().Home(OrderConfirmation.this);
    }
}
