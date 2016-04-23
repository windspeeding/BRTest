package com.test.brtest.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.brtest.Constants;
import com.test.brtest.R;
import com.test.brtest.model.Store;

public class DetailActivity extends BaseActivity {

    private static final String GOOGLE_MAP_BASE_URL = "http://www.google.com/maps/place/";
    private String storeName;
    private Store store;
    private TextView storePhone, storeAddressDetail;
    private CardView phoneCard,addressCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this)
                .load(store.getStoreLogoURL())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .centerInside()
                .resize(250,250).centerInside()
                .tag(this)
                .into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        if (intent!=null){
            store = (Store) intent.getSerializableExtra(Constants.TAG_STORE);
            storeName = store.getName();
        } else {
            finish();
            return;
        }

        // Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //CollapsingToolbarLayout
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(storeName);
        //Image
        loadBackdrop();
        //TextView
        storePhone=(TextView) findViewById(R.id.store_phone);
        storeAddressDetail=(TextView) findViewById(R.id.store_address_detail);
        storePhone.setText(getFormatNumber(store.getPhone()));
        storeAddressDetail.setText(getFormatAddress(store.getAddress(),store.getCity(),store.getState(),store.getZipcode()));

        //CardView
        phoneCard=(CardView)findViewById(R.id.phone_card);
        addressCard=(CardView)findViewById(R.id.address_card);
        phoneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:"+store.getPhone())));
            }
        });
        addressCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = GOOGLE_MAP_BASE_URL+store.getLatitude()+Constants.COMMA+store.getLongitude();
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));
            }
        });
    }
}
