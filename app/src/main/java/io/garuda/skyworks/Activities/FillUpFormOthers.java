package io.garuda.skyworks.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import io.garuda.skyworks.Data.APIService;
import io.garuda.skyworks.Data.ApiUtils;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FillUpFormOthers extends AppCompatActivity {

    Provider provider;
    Service service;
    User user;
    EditText name;
    EditText number;
    EditText email;
    EditText date;
    EditText time;
    EditText jobDescription;
    Button submit;
    Bundle extras;
    APIService mAPIService;
    SharedPreferences sharedPref;
    Boolean isNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_up_form_others);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(io.garuda.skyworks.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Enter Job Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //bind views
        name = (EditText) findViewById(io.garuda.skyworks.R.id.name);
        number = (EditText) findViewById(io.garuda.skyworks.R.id.number);
        email = (EditText) findViewById(io.garuda.skyworks.R.id.email);
        date = (EditText) findViewById(io.garuda.skyworks.R.id.date);
        time = (EditText) findViewById(io.garuda.skyworks.R.id.time);
        jobDescription = (EditText) findViewById(R.id.jobDescription);
        submit = (Button) findViewById(io.garuda.skyworks.R.id.submit);

        //get extras
        extras = getIntent().getExtras();

        sharedPref = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String userID = sharedPref.getString("USER", "");

        //setup API Client
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getUser(userID).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()) {
                    user = response.body();

                    //setup with texts with known details
                    name.setText(user.getName());
                    number.setText(user.getContactNumber());
                    email.setText(user.getEmail());

                    isNewUser = user.getCardIds() == null;
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });



        //date picker
        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                Integer mYear = mcurrentDate.get(Calendar.YEAR);
                Integer mMonth = mcurrentDate.get(Calendar.MONTH);
                Integer mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(FillUpFormOthers.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        date.setText(selectedday + "/" + (selectedmonth + 1) + "/" + selectedyear);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }
        });

        //time picker
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                Integer mHour = mcurrentDate.get(Calendar.HOUR);
                Integer mMinute = mcurrentDate.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(FillUpFormOthers.this, AlertDialog.THEME_HOLO_DARK, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, mHour, mMinute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show(); }
        });


        //listner for submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get updated details
                String sName = name.getText().toString().trim();
                String sNumber = number.getText().toString().trim();
                String sEmail = email.getText().toString().trim();
                String sDate = date.getText().toString().trim();
                String sTime = time.getText().toString().trim();
                String sJobDes = jobDescription.getText().toString().trim();

                Service mService = new Service("", sJobDes, "request", sDate, sName, sEmail, sNumber,
                        service.getType(), "operator1", sTime, "card1", "", -1, null);

                if (isNewUser == null) {
                    Intent i = new Intent(FillUpFormOthers.this, PaymentNew.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("SERVICE", mService);
                    mBundle.putSerializable("PAYMENTCALLER", FillUpFormOthers.class);
                    i.putExtras(mBundle);
                    startActivity(i);

                } else {
                    Intent i = new Intent(FillUpFormOthers.this, Payment.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("SERVICE", mService);
                    mBundle.putSerializable("PAYMENTCALLER", FillUpFormOthers.class);
                    i.putExtras(mBundle);
                    startActivity(i);
                }
            }
        });

    }

    //setup links for menu items
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent i = new Intent(this, ChooseService.class);
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
                extras.putSerializable("CALLER", FillUpFormOthers.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.notifications:
                i = new Intent(this, Notifications.class);
                extras.putSerializable("CALLER", FillUpFormOthers.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myWallet:
                i = new Intent(this, MyWallet.class);
                extras.putSerializable("CALLER", FillUpFormOthers.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myServices:
                i = new Intent(this, MyServices.class);
                extras.putSerializable("CALLER", FillUpFormOthers.class);
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
