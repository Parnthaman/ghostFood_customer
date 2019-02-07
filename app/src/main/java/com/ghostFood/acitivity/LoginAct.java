package com.ghostFood.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.fragment.Login;
import com.ghostFood.util.CommonFunctions;
import com.ghostFood.util.Constants;
import com.ghostFood.util.FontFunctions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginAct extends AppCompatActivity {


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
        CommonFunctions.getInstance().ChangeDirection(LoginAct.this);
        CommonFunctions.getInstance().ChangeToolbarLanguage(LoginAct.this, toolbar);
        ButterKnife.bind(this);


        Bundle bundle = new Bundle();
        bundle.putString("from", "loginact");
        Login login = new Login();
        login.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, login).commit();
        tlbarText.setText(Constants.Login);
        tlbarText.setVisibility(View.GONE);

        FontFunctions.getInstance().FontBalooBhaijaan(LoginAct.this, tlbarText);

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
