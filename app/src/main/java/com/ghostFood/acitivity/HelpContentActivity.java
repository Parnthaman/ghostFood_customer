package com.ghostFood.acitivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.util.FontFunctions;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HelpContentActivity extends AppCompatActivity {

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
    @BindView(R.id.txtView_title)
    TextView txtViewTitle;
    @BindView(R.id.txtView_content)
    TextView txtViewContent;
    private String title, content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_content);
        ButterKnife.bind(this);


        title = getIntent().getExtras().getString("title");
        content = getIntent().getExtras().getString("content");

        FontFunctions.getInstance().FontBalooBhaijaan(HelpContentActivity.this, tlbarText);
        FontFunctions.getInstance().FontBalooBhaijaan(HelpContentActivity.this, txtViewTitle);
        FontFunctions.getInstance().FontBalooBhaijaan(HelpContentActivity.this, txtViewContent);

        if (title != null && content!= null) {
            txtViewTitle.setText(title);
            txtViewTitle.setVisibility(View.GONE);
            tlbarText.setText(title);
            txtViewContent.setText(Html.fromHtml(content));
        } else {
        }

        tlbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
