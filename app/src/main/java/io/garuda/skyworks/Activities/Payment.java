package io.garuda.skyworks.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

import io.garuda.skyworks.Adapters.CreditCardListAdapter;
import io.garuda.skyworks.Models.CreditCard;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

public class Payment extends AppCompatActivity implements Serializable{

    Provider provider;
    Service service;
    User user;
    CreditCardListAdapter adapter;
    ListView cardList;
    Bundle extras;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(io.garuda.skyworks.R.layout.activity_payment);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(io.garuda.skyworks.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment Method");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get extras
        extras = getIntent().getExtras();
        provider = (Provider) extras.getSerializable("PROVIDER");
        service = (Service) extras.getSerializable("SERVICE");
        user = (User) extras.get("USER");

        //bind view
        cardList = (ListView) findViewById(io.garuda.skyworks.R.id.cardList);
        add = (Button) findViewById(io.garuda.skyworks.R.id.addMethod);

        //create and setup adapter for credit card list
        adapter = new CreditCardListAdapter(this, user.getCards());
        cardList.setAdapter(adapter);

        //listener when click credit card
        cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, final View v, final int position,
                                    long arg3) {

                AlertDialog.Builder ad = new AlertDialog.Builder(Payment.this);
                ad.setCancelable(false);
                ad.setTitle("Select Card");
                ad.setMessage("Select this card?");
                ad.setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    //deletes this item from order
                    public void onClick(DialogInterface dialog, int which) {
                        CreditCard card = user.getCards().get(position);
                        service.setPaymentMethod(card);
                        user.addService(service);
                        Intent i = new Intent(Payment.this, Confirmation.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("PROVIDER", provider);
                        mBundle.putSerializable("SERVICE", service);
                        mBundle.putSerializable("USER", user);
                        i.putExtras(mBundle);
                        startActivity(i);
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

        //listener for adding card button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Payment.this, AddPaymentMethod.class);
                extras.putSerializable("CALLER1", Payment.class);
                i.putExtras(extras);
                startActivity(i);

            }
        });

    }


    //links for menu items
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i = new Intent(this, (Class) extras.getSerializable("PAYMENTCALLER"));
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
                extras.putSerializable("CALLER", Payment.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.notifications:
                i = new Intent(this, Notifications.class);
                extras.putSerializable("CALLER", Payment.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myWallet:
                i = new Intent(Payment.this, MyWallet.class);
                extras.putSerializable("CALLER", Payment.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myServices:
                i = new Intent(Payment.this, MyServices.class);
                extras.putSerializable("CALLER", Payment.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}