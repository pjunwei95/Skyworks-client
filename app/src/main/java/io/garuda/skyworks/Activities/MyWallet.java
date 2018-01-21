package io.garuda.skyworks.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

public class MyWallet extends AppCompatActivity implements Serializable{

    User user;
    Bundle extras;
    ListView ccList;
    Button add;
    CreditCardListAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(io.garuda.skyworks.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Wallet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get extras
        extras = getIntent().getExtras();
        user = (User) extras.get("USER");

        //bind view
        ccList = (ListView) findViewById(io.garuda.skyworks.R.id.cclist);
        add = (Button) findViewById(io.garuda.skyworks.R.id.add);


        //create and setup adapter for credit card list
        adapter1 = new CreditCardListAdapter(this, user.getCards());
        ccList.setAdapter(adapter1);


        //listener when click credit card (delete or not)
        ccList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, final View v, final int position,
                                    long arg3) {

                AlertDialog.Builder ad = new AlertDialog.Builder(MyWallet.this);
                ad.setCancelable(false);
                ad.setTitle("Delete");
                ad.setMessage("Delete this card?");
                ad.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    //deletes this item from order
                    public void onClick(DialogInterface dialog, int which) {
                        CreditCard card = user.getCards().get(position);
                        user.removeCard(card);
                        adapter1.notifyDataSetChanged();
                        Intent i = new Intent(MyWallet.this, MyWallet.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("USER", user);
                        i.putExtras(extras);
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
                Intent i = new Intent(MyWallet.this, AddPaymentMethod.class);
                extras.putSerializable("CALLER1", MyWallet.class);
                i.putExtras(extras);
                startActivity(i);

            }
        });



    }

    //setup links for menu item (back button)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i = new Intent(MyWallet.this, (Class) extras.get("CALLER"));
                i.putExtras(extras);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
