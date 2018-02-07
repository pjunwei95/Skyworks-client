package io.garuda.skyworks.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.garuda.skyworks.Data.APIService;
import io.garuda.skyworks.Data.ApiUtils;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {

    User user;
    Button update;
    EditText name;
    EditText number;
    EditText email;
    Bundle extras;
    APIService mAPIService;
    SharedPreferences sharedPref;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get extras
        extras = getIntent().getExtras();


        sharedPref = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        userID = sharedPref.getString("USER", "");

        //setup API Client
        mAPIService = ApiUtils.getAPIService(this);
        mAPIService.getUser(userID).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()) {
                    user = response.body();

                    //update texts with known details
                    name.setText(user.getName());
                    number.setText(user.getContactNumber());
                    email.setText(user.getEmail());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });

        //bind views
        name = (EditText) findViewById(io.garuda.skyworks.R.id.name);
        number = (EditText) findViewById(io.garuda.skyworks.R.id.number);
        email = (EditText) findViewById(io.garuda.skyworks.R.id.email);
        update = (Button) findViewById(io.garuda.skyworks.R.id.update);


        //button listener
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(name.getText().toString().trim());
                user.setContactNumber(number.getText().toString().trim());
                user.setEmail(email.getText().toString().trim());

                mAPIService.postUser(userID, user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()) {
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("TAG", t.toString());
                    }
                });

                Intent i = new Intent(EditProfile.this, (Class) extras.get("CALLER"));
                Bundle mBundle = new Bundle();
                i.putExtras(extras);
                i.putExtras(mBundle);
                startActivity(i);

            }
        });



    }

    //setup links for menu item (back button)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i = new Intent(EditProfile.this, (Class) extras.get("CALLER"));
                i.putExtras(extras);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
