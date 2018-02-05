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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.garuda.skyworks.Adapters.CreditCardListAdapter;
import io.garuda.skyworks.Data.APIService;
import io.garuda.skyworks.Data.ApiUtils;
import io.garuda.skyworks.Models.CreditCard;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment extends AppCompatActivity implements Serializable{

    Provider provider;
    Service service;
    User user;
    CreditCardListAdapter adapter;
    ListView cardList;
    Bundle extras;
    Button add;
    APIService mAPIService;
    SharedPreferences sharedPref;
    ArrayList<CreditCard> cards;
    List<String> cardIds;
    String serviceId;
    ArrayList<LatLng> arrayPoints;


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
        arrayPoints = (ArrayList<LatLng>) extras.getSerializable("LOC");

        //bind view
        cardList = (ListView) findViewById(io.garuda.skyworks.R.id.cardList);
        add = (Button) findViewById(io.garuda.skyworks.R.id.addMethod);

        sharedPref = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        final String userID = sharedPref.getString("USER", "");

        //setup API Client
        final Activity activity = this;
        mAPIService = ApiUtils.getAPIService();
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

                                    adapter = new CreditCardListAdapter(activity, cards);
                                    cardList.setAdapter(adapter);
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
                        CreditCard card = cards.get(position);

                        service.setCreditCardID(card.getId());
                        sendJob(service);

                        user.addServiceIds(serviceId);
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


                        Intent i = new Intent(Payment.this, Confirmation.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("PROVIDER", provider);
                        mBundle.putSerializable("SERVICE", service);
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

    public void sendJob(Service service) {
        mAPIService.putJob(service).enqueue(new Callback<Service>() {
            @Override
            public void onResponse(Call<Service> call, Response<Service> response) {
                if(response.isSuccessful()) {
                    Log.i("TAG", "job submitted to API.");
                    serviceId = response.body().getId();
                }
            }
            @Override
            public void onFailure(Call<Service> call, Throwable t) {
                Log.e("TAG", t.toString());
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
