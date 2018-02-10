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
import android.widget.Toast;


import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.R;

public class IPSetup extends AppCompatActivity {

    SharedPreferences sharedPref;
    EditText IP;
    Button submit;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipsetup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("URL Setup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extras = getIntent().getExtras();
        IP = (EditText) findViewById(R.id.ipAdd);
        submit = (Button) findViewById(R.id.submit);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get updated details
                String ip = IP.getText().toString().trim();

                sharedPref = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("IP", ip);
                editor.commit();
                Toast.makeText(IPSetup.this, "URL updated!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(IPSetup.this, ChooseService.class);
                startActivity(i);

            }
        });

    }
    //setup links for menu item (back button)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i = new Intent(this, (Class) extras.get("CALLER"));
                i.putExtras(extras);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
