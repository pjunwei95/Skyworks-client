package io.garuda.skyworks.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.garuda.skyworks.Adapters.CreditCardListAdapter;
import io.garuda.skyworks.Data.APIService;
import io.garuda.skyworks.Data.ApiUtils;
import io.garuda.skyworks.Models.CreditCard;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWallet extends AppCompatActivity implements Serializable{

    User user;
    Bundle extras;
    ListView ccList;
    Button add;
    CreditCardListAdapter adapter1;
    APIService mAPIService;
    SharedPreferences sharedPref;
    String userID;
    ArrayList<CreditCard> cards;
    List<String> cardIds;

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

        sharedPref = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        userID = sharedPref.getString("USER", "");

        //setup API Client
        final Activity activity = this;
        mAPIService = ApiUtils.getAPIService(this);
        mAPIService.getUser(userID).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()) {
                    user = response.body();
                    cardIds = user.getCardIds();
                    cards = new ArrayList<>();

                    //create and setup adapter for credit card list
                    for(int i = 0; i < cardIds.size(); i++) {
                        mAPIService.getCard(cardIds.get(i)).enqueue(new Callback<CreditCard>() {
                            @Override
                            public void onResponse(Call<CreditCard> call, Response<CreditCard> response) {
                                if(response.isSuccessful()) {
                                    CreditCard card = response.body();
                                    cards.add(card);

                                    //create and setup adapter for credit card list
                                    adapter1 = new CreditCardListAdapter(activity, cards);
                                    ccList.setAdapter(adapter1);
                                }
                            }
                            @Override
                            public void onFailure(Call<CreditCard> call, Throwable t) {
                                Log.e("TAG", t.toString());
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });

        //bind view
        ccList = (ListView) findViewById(io.garuda.skyworks.R.id.cclist);
        add = (Button) findViewById(io.garuda.skyworks.R.id.add);





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
                        String cardId = cardIds.get(position);

                        mAPIService.deleteCard(cardId).enqueue(new Callback<CreditCard>() {
                            @Override
                            public void onResponse(Call<CreditCard> call, Response<CreditCard> response) {
                                if(response.isSuccessful()) {

                                }
                            }
                            @Override
                            public void onFailure(Call<CreditCard> call, Throwable t) {
                                Log.e("TAG", t.toString());
                            }
                        });

                        user.removeCardId(cardId);
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
