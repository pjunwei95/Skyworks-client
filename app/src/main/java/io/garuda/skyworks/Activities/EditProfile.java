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
import android.widget.EditText;

import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

public class EditProfile extends AppCompatActivity {

    User user;
    Button update;
    EditText name;
    EditText number;
    EditText email;
    Bundle extras;


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
        user = (User) extras.get("USER");

        //bind views
        name = (EditText) findViewById(io.garuda.skyworks.R.id.name);
        number = (EditText) findViewById(io.garuda.skyworks.R.id.number);
        email = (EditText) findViewById(io.garuda.skyworks.R.id.email);
        update = (Button) findViewById(io.garuda.skyworks.R.id.update);

        //update texts with known details
        name.setText(user.getName());
        number.setText(user.getContactNumber());
        email.setText(user.getEmail());

        //button listener
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(name.getText().toString().trim());
                user.setContactNumber(number.getText().toString().trim());
                user.setEmail(email.getText().toString().trim());

                Intent i = new Intent(EditProfile.this, (Class) extras.get("CALLER"));
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("USER", user);
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
