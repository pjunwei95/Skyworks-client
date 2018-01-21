package io.garuda.skyworks.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

public class Confirmation extends AppCompatActivity implements Serializable{

    Button OK;
    Provider provider;
    Service service;
    User user;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(io.garuda.skyworks.R.layout.activity_confirmation);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(io.garuda.skyworks.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Confirmation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get extras
        extras = getIntent().getExtras();
        provider = (Provider) extras.getSerializable("PROVIDER");
        service = (Service) extras.getSerializable("SERVICE");
        user = (User) extras.get("USER");

        //bind views
        OK = (Button) findViewById(io.garuda.skyworks.R.id.OK);

        //listener for button
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Confirmation.this, ChooseService.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("USER", user);
                i.putExtras(mBundle);
                startActivity(i);
            }
        });



    }

    //setup links for menu items
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i = new Intent(this, Payment.class);
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
                extras.putSerializable("CALLER", Confirmation.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.notifications:
                i = new Intent(this, Notifications.class);
                extras.putSerializable("CALLER", Confirmation.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myWallet:
                i = new Intent(Confirmation.this, MyWallet.class);
                extras.putSerializable("CALLER", Confirmation.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myServices:
                i = new Intent(Confirmation.this, MyServices.class);
                extras.putSerializable("CALLER", Confirmation.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //inflate pop up menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
