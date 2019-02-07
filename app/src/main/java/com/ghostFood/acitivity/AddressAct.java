package com.ghostFood.acitivity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.fragment.Address;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.FontFunctions;
import com.ghostFood.util.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressAct extends AppCompatActivity {


    @BindView(R.id.tlbar_back)
    ImageView tlbarBack;
    @BindView(R.id.tlbar_save)
    ImageView tlbarSave;
    @BindView(R.id.tlbar_text)
    TextView tlbarText;
    @BindView(R.id.tlbar_cart)
    ImageView tlbarCart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        CommonFunctions.getInstance().ChangeDirection(AddressAct.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(AddressAct.this, toolbar);

        Bundle bundle = new Bundle();
        bundle.putString("from", "addressact");
        Address address = new Address();
        address.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, address).commit();
        tlbarText.setText(Constants.DeliveryAddess);
        FontFunctions.getInstance().FontBalooBhaijaan(AddressAct.this, tlbarText);
        FontFunctions.getInstance().FontBalooBhaijaan(AddressAct.this, tlbarText);

        tlbarSave.setVisibility(View.VISIBLE);
        tlbarSave.setImageResource(R.drawable.ic_addaddress);
        tlbarSave.setColorFilter(ContextCompat.getColor(MyApplication.context,R.color.tabbackground));

        tlbarSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                MyActivity.getInstance().AddAddressMap(AddressAct.this, bundle);
            }
        });

    }

    @OnClick({R.id.tlbar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tlbar_back:
                finish();
                break;
        }
    }
}
