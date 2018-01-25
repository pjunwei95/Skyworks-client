package io.garuda.skyworks.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Serializable;

import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

public class FillUpForm extends AppCompatActivity implements Serializable {

    Provider provider;
    Service service;
    User user;
    EditText name;
    EditText number;
    EditText email;
    EditText date;
    EditText time;
    EditText req;
    TextView type;
    Button submit;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(io.garuda.skyworks.R.layout.activity_fill_up_form);

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
        req = (EditText) findViewById(io.garuda.skyworks.R.id.specialRequest);
        submit = (Button) findViewById(io.garuda.skyworks.R.id.submit);
        type = (TextView) findViewById(io.garuda.skyworks.R.id.jobType);

        //get extras
        extras = getIntent().getExtras();
        provider = (Provider) extras.getSerializable("PROVIDER");
        service = (Service) extras.getSerializable("SERVICE");
        user = (User) extras.get("USER");

        //setup with texts with known details
        type.setText(service.getType());
        name.setText(user.getName());
        number.setText(user.getContactNumber());
        email.setText(user.getEmail());

        //date picker
        date.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                Integer mYear = mcurrentDate.get(Calendar.YEAR);
                Integer mMonth = mcurrentDate.get(Calendar.MONTH);
                Integer mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(FillUpForm.this, new DatePickerDialog.OnDateSetListener() {
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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                Integer mHour = mcurrentDate.get(Calendar.HOUR);
                Integer mMinute = mcurrentDate.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(FillUpForm.this, AlertDialog.THEME_HOLO_DARK, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText( selectedHour + ":" + selectedMinute);
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
                String sReq = req.getText().toString().trim();

                Service mService = new Service("request", service.getType(), sName, sNumber, sEmail, sDate, sTime, sReq, provider, null, null, -1);

                if (user.getCards() == null) {
                    Intent i = new Intent(FillUpForm.this, PaymentNew.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("SERVICE", mService);
                    mBundle.putSerializable("PROVIDER", provider);
                    mBundle.putSerializable("USER", user);
                    mBundle.putSerializable("PAYMENTCALLER", FillUpForm.class);
                    i.putExtras(mBundle);
                    startActivity(i);

                } else {
                    Intent i = new Intent(FillUpForm.this, Payment.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("SERVICE", mService);
                    mBundle.putSerializable("PROVIDER", provider);
                    mBundle.putSerializable("USER", user);
                    mBundle.putSerializable("PAYMENTCALLER", FillUpForm.class);
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
                Intent i = new Intent(this, ProviderDetail.class);
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
                extras.putSerializable("CALLER", FillUpForm.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.notifications:
                i = new Intent(this, Notifications.class);
                extras.putSerializable("CALLER", FillUpForm.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myWallet:
                i = new Intent(FillUpForm.this, MyWallet.class);
                extras.putSerializable("CALLER", FillUpForm.class);
                i.putExtras(extras);
                startActivity(i);
                return true;

            case R.id.myServices:
                i = new Intent(FillUpForm.this, MyServices.class);
                extras.putSerializable("CALLER", FillUpForm.class);
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
