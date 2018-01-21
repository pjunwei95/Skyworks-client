package io.garuda.skyworks.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

public class CompleteServiceDetailRate extends AppCompatActivity {

    Bundle extras;
    ImageView image;
    TextView name;
    TextView license;
    TextView jobType;
    TextView date;
    TextView time;
    TextView specialRequest;
    TextView price;
    Button payNRate;
    Provider provider;
    User user;
    Service selectedService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_service_detail_rate);
        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Service Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get extras
        extras = getIntent().getExtras();
        user = (User) extras.getSerializable("USER");
        selectedService = (Service) extras.getSerializable("SELECTEDSERVICE");
        provider = selectedService.getProvider();

        //bind views
        image = (ImageView) findViewById(io.garuda.skyworks.R.id.image);
        name = (TextView) findViewById(io.garuda.skyworks.R.id.name);
        license = (TextView) findViewById(io.garuda.skyworks.R.id.license);
        jobType = (TextView) findViewById(R.id.jobType);
        date = (TextView) findViewById(io.garuda.skyworks.R.id.date);
        time = (TextView) findViewById(io.garuda.skyworks.R.id.time);
        specialRequest = (TextView) findViewById(R.id.specialRequest);
        price = (TextView) findViewById(io.garuda.skyworks.R.id.price);
        payNRate = (Button) findViewById(R.id.pay_rate);


        //update views with data
        Picasso.with(this)
                .load(provider.getPosterPath())
                .resize(500, 500).centerInside()
                .into(image);
        name.setText(provider.getName());
        license.setText(provider.getLicenseNumber());
        jobType.setText(selectedService.getType());
        date.setText(selectedService.getDate());
        time.setText(selectedService.getTime());
        specialRequest.setText(selectedService.getSpecialRequest());
        price.setText(selectedService.getQuotation());



        //button listener
        payNRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CompleteServiceDetailRate.this, PayAndRate.class);
                i.putExtra("CALLER2", Notifications.class);
                i.putExtras(extras);
                startActivity(i);

            }
        });



    }

    //setup links for menu item (back button)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i = new Intent(CompleteServiceDetailRate.this, (Class) extras.get("CALLER1"));
                i.putExtras(extras);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
