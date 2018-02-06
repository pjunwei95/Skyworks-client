package io.garuda.skyworks.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import io.garuda.skyworks.Data.APIService;
import io.garuda.skyworks.Data.ApiUtils;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.Serializable;
import java.util.Stack;

public class ChooseService extends AppCompatActivity implements Serializable {

    User user;
    Bundle extras;
    ImageButton inspection_button;
    ImageButton media_button;
    ImageButton mapping_button;
    ImageButton others_button;
    APIService mAPIService;
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(io.garuda.skyworks.R.layout.activity_choose_service);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(io.garuda.skyworks.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Services");


        //binding views
        inspection_button = (ImageButton) findViewById(io.garuda.skyworks.R.id.inspection);
        media_button = (ImageButton) findViewById(io.garuda.skyworks.R.id.media);
        mapping_button = (ImageButton) findViewById(io.garuda.skyworks.R.id.mapping);
        others_button = (ImageButton) findViewById(io.garuda.skyworks.R.id.others);

        //get extras
        extras = getIntent().getExtras();

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        String userID = sharedPref.getString("USER", "");


        //setup API Client
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getUser(userID).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    user = response.body();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });



        //listeners for buttons
        inspection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to be replaced by backend
                Service service = new Service("", "", "Request", null, null, null, null, "Inspection", null, null, null, null, -1 , null);

                Intent i = new Intent(ChooseService.this, ChooseLocation.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("SERVICE", service);
                i.putExtras(mBundle);
                startActivity(i);

            }
        });

        mapping_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to be replaced by backend
                Service service = new Service("", "", "Request", null, null, null, null, "Mapping", null, null, null, null, -1 , null);
                Intent i = new Intent(ChooseService.this, ChooseLocation.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("SERVICE", service);
                i.putExtras(mBundle);
                startActivity(i);

            }
        });

        media_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to be replaced by backend
                Service service = new Service("", "", "Request", null, null, null, null, "Media Production", null, null, null, null, -1 , null);
                Intent i = new Intent(ChooseService.this, ChooseLocation.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("SERVICE", service);
                i.putExtras(mBundle);
                startActivity(i);

            }
        });

        others_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to be replaced by backend
                Intent i = new Intent(ChooseService.this, FillUpFormOthers.class);
                Bundle mBundle = new Bundle();
                i.putExtras(mBundle);
                startActivity(i);


            }
        });



    }

    //inflate pop up menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    //setup links for menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent i;
        switch (item.getItemId())
        {
            case R.id.home2:
                i = new Intent(ChooseService.this, ChooseService.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.editProfile:
                i = new Intent(ChooseService.this, EditProfile.class);
                extras.putSerializable("CALLER", ChooseService.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.notifications:
                i = new Intent(ChooseService.this, Notifications.class);
                extras.putSerializable("CALLER", ChooseService.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myWallet:
                i = new Intent(ChooseService.this, MyWallet.class);
                extras.putSerializable("CALLER", ChooseService.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myServices:
                i = new Intent(ChooseService.this, MyServices.class);
                extras.putSerializable("CALLER", ChooseService.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
