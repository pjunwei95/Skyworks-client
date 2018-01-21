package io.garuda.skyworks.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.Serializable;

import io.garuda.skyworks.Fragments.NotificationsListFragment;
import io.garuda.skyworks.Fragments.ProvidersListFragment;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

public class Notifications extends AppCompatActivity implements Serializable{


    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(io.garuda.skyworks.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get extras
        extras = getIntent().getExtras();

        //load fragment (recycler view)
        showFragment(NotificationsListFragment.class, extras);
    }


    //method to load recycler view fragment
    private void showFragment(Class fragmentClass, Bundle extras) {

        Fragment fragment = null;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        fragment.setArguments(extras);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(io.garuda.skyworks.R.id.fl, fragment)
                .commit();

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
