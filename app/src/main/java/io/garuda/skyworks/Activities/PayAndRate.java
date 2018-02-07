package io.garuda.skyworks.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import io.garuda.skyworks.Data.APIService;
import io.garuda.skyworks.Data.ApiUtils;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayAndRate extends AppCompatActivity {

    Bundle extras;
    TextView name;
    Button submit;
    RatingBar ratingBar;
    Provider provider;
    Service selectedService;
    User user;
    APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_and_rate);

        //get extras
        extras = getIntent().getExtras();
        selectedService = (Service) extras.getSerializable("SELECTEDSERVICE");
        user = (User) extras.getSerializable("USER");

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setup API Client
        mAPIService = ApiUtils.getAPIService(this);
        mAPIService.getProvider(selectedService.getOperatorID()).enqueue(new Callback<Provider>() {
            @Override
            public void onResponse(Call<Provider> call, Response<Provider> response) {

                if(response.isSuccessful()) {
                    provider = response.body();
                    getSupportActionBar().setTitle("Rate" + provider.getName());
                }
            }
            @Override
            public void onFailure(Call<Provider> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });

        //bind views
        submit = (Button) findViewById(R.id.submit);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //if click on me, then display the current rating value.
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int rating = ratingBar.getNumStars();
                selectedService.setRating(rating);
                selectedService.setStatus("Payment Successful");
                mAPIService.postJob(selectedService.getId(), selectedService).enqueue(new Callback<Service>() {
                    @Override
                    public void onResponse(Call<Service> call, Response<Service> response) {
                        if(response.isSuccessful()) {
                            Intent i = new Intent(PayAndRate.this, (Class) extras.get("CALLER1"));
                            i.putExtras(extras);
                            startActivity(i);
                        }
                        Log.e("TAG", "error");
                    }
                    @Override
                    public void onFailure(Call<Service> call, Throwable t) {
                        Log.e("TAG", t.toString());
                    }
                });

            }
        });
    }

    //setup links for menu item (back button)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i = new Intent(PayAndRate.this, (Class) extras.get("CALLER2"));
                i.putExtras(extras);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
