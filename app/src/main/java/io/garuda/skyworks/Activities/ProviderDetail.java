package io.garuda.skyworks.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import io.garuda.skyworks.Adapters.GalleryListAdapter;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class ProviderDetail extends AppCompatActivity implements Serializable{

    Provider provider;
    Service service;
    User user;
    TextView bio;
    TextView name;
    TextView license;
    ImageView logo;
    Bundle extras;
    Button confirm_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(io.garuda.skyworks.R.layout.activity_provider_detail);

        //get extras
        extras = getIntent().getExtras();
        provider = (Provider) extras.getSerializable("PROVIDER");
        service = (Service) extras.getSerializable("SERVICE");
        user = (User) extras.get("USER");

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(io.garuda.skyworks.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(provider.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //bind views
        logo = (ImageView) findViewById(io.garuda.skyworks.R.id.image);
        bio = (TextView) findViewById(io.garuda.skyworks.R.id.bio);
        name = (TextView) findViewById(io.garuda.skyworks.R.id.name);
        license = (TextView) findViewById(R.id.license);
        confirm_button = (Button) findViewById(io.garuda.skyworks.R.id.button);

        //load details into views
        bio.setText(provider.getOverview());
        name.setText(provider.getName());
        license.setText(provider.getLicenseNumber());
        Picasso.with(this)
                .load(provider.getPosterPath())//need to change to online URL!!
                .fit()
                .into(logo);


        //setup list adapter for gallery photos
        GalleryListAdapter listAdapter = new GalleryListAdapter(this, provider.getGallery());
        ListView listView = (ListView) findViewById(io.garuda.skyworks.R.id.listView);
        listView.setAdapter(listAdapter);


        //listener for confirm button
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProviderDetail.this, FillUpForm.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("PROVIDER", provider);
                mBundle.putSerializable("SERVICE", service);
                mBundle.putSerializable("USER", user);
                i.putExtras(mBundle);
                startActivity(i);
            }
        });


    }

    //links for menu item
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i = new Intent(this, Providers.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.home2:
                i = new Intent(this, ChooseService.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.editProfile:
                i = new Intent(this, EditProfile.class);
                extras.putSerializable("CALLER", ProviderDetail.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.notifications:
                i = new Intent(this, Notifications.class);
                extras.putSerializable("CALLER", ProviderDetail.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myWallet:
                i = new Intent(ProviderDetail.this, MyWallet.class);
                extras.putSerializable("CALLER", ProviderDetail.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myServices:
                i = new Intent(ProviderDetail.this, MyServices.class);
                extras.putSerializable("CALLER", ProviderDetail.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



}