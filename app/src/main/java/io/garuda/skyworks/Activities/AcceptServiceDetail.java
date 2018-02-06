package io.garuda.skyworks.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.garuda.skyworks.Data.APIService;
import io.garuda.skyworks.Data.ApiUtils;
import io.garuda.skyworks.Models.CreditCard;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptServiceDetail extends AppCompatActivity {

    Bundle extras;
    ImageView image;
    TextView name;
    TextView license;
    TextView jobType;
    TextView date;
    TextView time;
    TextView specialRequest;
    TextView price;
    Button accept;
    Button decline;
    Provider provider;
    User user;
    Service selectedService;
    APIService mAPIService;
    SharedPreferences sharedPref;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_service_detail);


        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Service Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get extras
        extras = getIntent().getExtras();
        selectedService = (Service) extras.getSerializable("SELECTEDSERVICE");

        mAPIService = ApiUtils.getAPIService();
        mAPIService.getProvider(selectedService.getOperatorID()).enqueue(new Callback<Provider>() {
            @Override
            public void onResponse(Call<Provider> call, Response<Provider> response) {

                if(response.isSuccessful()) {
                    provider = response.body();

                    //update views with data
                    Picasso.with(getApplicationContext())
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
                }
            }
            @Override
            public void onFailure(Call<Provider> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });



        //bind views
        image = (ImageView) findViewById(io.garuda.skyworks.R.id.image);
        name = (TextView) findViewById(io.garuda.skyworks.R.id.name);
        license = (TextView) findViewById(io.garuda.skyworks.R.id.license);
        jobType = (TextView) findViewById(R.id.jobType);
        date = (TextView) findViewById(io.garuda.skyworks.R.id.date);
        time = (TextView) findViewById(io.garuda.skyworks.R.id.time);
        specialRequest = (TextView) findViewById(R.id.specialRequest);
        price = (TextView) findViewById(io.garuda.skyworks.R.id.price);
        accept = (Button) findViewById(R.id.accept);
        decline = (Button) findViewById(R.id.decline);


        /*
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
        */


        //button listener
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ad = new AlertDialog.Builder(AcceptServiceDetail.this);
                ad.setCancelable(false);
                ad.setTitle("Accept Service");
                ad.setMessage("Accept this service?");
                ad.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    //deletes this item from order
                    public void onClick(DialogInterface dialog, int which) {
                        selectedService.setStatus("Awaiting for Permit Details");
                        mAPIService.postJob(selectedService.getId(), selectedService).enqueue(new Callback<Service>() {
                            @Override
                            public void onResponse(Call<Service> call, Response<Service> response) {
                                if(response.isSuccessful()) {
                                    Intent i = new Intent(AcceptServiceDetail.this, (Class) extras.get("CALLER1"));
                                    i.putExtras(extras);
                                    startActivity(i);
                                }
                            }
                            @Override
                            public void onFailure(Call<Service> call, Throwable t) {
                                Log.e("TAG", t.toString());
                            }
                        });

                    }
                });
                ad.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();

            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(AcceptServiceDetail.this);
                ad.setCancelable(false);
                ad.setTitle("Decline Service");
                ad.setMessage("Decline this service?");
                ad.setPositiveButton("Decline", new DialogInterface.OnClickListener() {
                    //deletes this item from order
                    public void onClick(DialogInterface dialog, int which) {
                        selectedService.setStatus("Completed");
                        mAPIService.postJob(selectedService.getId(), selectedService).enqueue(new Callback<Service>() {
                            @Override
                            public void onResponse(Call<Service> call, Response<Service> response) {
                                if(response.isSuccessful()) {
                                    Intent i = new Intent(AcceptServiceDetail.this, (Class) extras.get("CALLER1"));
                                    i.putExtras(extras);
                                    startActivity(i);
                                }
                            }
                            @Override
                            public void onFailure(Call<Service> call, Throwable t) {
                                Log.e("TAG", t.toString());
                            }
                        });

                    }
                });
                ad.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();

            }
        });


    }

    //setup links for menu item (back button)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i = new Intent(AcceptServiceDetail.this, (Class) extras.get("CALLER1"));
                i.putExtras(extras);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
