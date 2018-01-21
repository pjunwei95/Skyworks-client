package io.garuda.skyworks.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

public class PayAndRate extends AppCompatActivity {

    Bundle extras;
    TextView name;
    Button submit;
    RatingBar ratingBar;
    Provider provider;
    Service selectedService;
    User user;

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
        getSupportActionBar().setTitle("Rate" + selectedService.getProvider().getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //bind views
        submit = (Button) findViewById(R.id.submit);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //if click on me, then display the current rating value.
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int rating = ratingBar.getNumStars();
                ArrayList<Service> services = user.getServices();
                for (int i = 0; i < services.size(); i++) {
                    if (services.get(i).getType().equals(selectedService.getType())
                            && services.get(i).getStatus().equals(selectedService.getStatus())
                            && services.get(i).getDate().equals(selectedService.getDate())
                            && services.get(i).getTime().equals(selectedService.getTime())
                            && services.get(i).getSpecialRequest().equals(selectedService.getSpecialRequest())
                            && services.get(i).getProvider().getLicenseNumber().equals(selectedService.getProvider().getLicenseNumber())
                            && services.get(i).getPaymentMethod().getCardNum().equals(selectedService.getPaymentMethod().getCardNum())
                            && services.get(i).getQuotation().equals(selectedService.getQuotation())) {
                        services.remove(i);
                    }
                }
                user.setServices(services);
                selectedService.setRating(rating);
                user.addService(selectedService);
                Intent i = new Intent(PayAndRate.this, (Class) extras.get("CALLER1"));
                i.putExtras(extras);
                i.putExtra("USER", user);
                startActivity(i);

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
