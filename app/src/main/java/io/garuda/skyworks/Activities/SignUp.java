package io.garuda.skyworks.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import io.garuda.skyworks.Models.CreditCard;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

public class SignUp extends AppCompatActivity {

    Button signUp;
    TextView login;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //bind views
        signUp = (Button) findViewById(R.id.btn_signup);
        login = (TextView) findViewById(R.id.link_login);

        //initialise data (to be replaced by backend)
        initialise();

        //listeners
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, ChooseService.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("USER", user);
                i.putExtras(mBundle);
                startActivity(i);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, Landing.class);
                startActivity(i);

            }
        });



    }

    public void initialise() {
        ArrayList<String> l = new ArrayList<String>();
        l.add("https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwjA-Ya0v7bYAhUX3o8KHZu1AHYQjBwIBA&url=https%3A%2F%2Fimage.freepik.com%2Ffree-photo%2Fobservation-urban-building-business-steel_1127-2397.jpg&psig=AOvVaw0RMQOB2sF0uud8tLYKxmpR&ust=1514886796255210");
        l.add("https://static.pexels.com/photos/302769/pexels-photo-302769.jpeg");
        l.add("https://blogs.intel.com/iot/files/2015/01/SmartBuilding.jpg");
        l.add("http://www.buildingtechnologies.siemens.com/bt/global/en/PublishingImages/total-building-solutions-key-visual-large.jpg");
        Provider p1 = new Provider("Garuda", "Founded in 2014, Garuda Robotics Pte Ltd is a Robotics and Artificial Intelligence technology company. Headquartered in Singapore and focused on clients in Asia, we build products and solutions for enterprises operating in agriculture, infrastructure and security. Our clients are located in Singapore, Malaysia, Indonesia and the Philippines, and include SMEs, multinational corporations and government agencies.", "https://garuda.io/wp-content/uploads/2017/05/2016-Garuda-Logo.png", "12345", l);

        ArrayList<CreditCard> list = new ArrayList<>();
        list.add(new CreditCard("5100 0000 0000 0000", 2, "Bob", "01232013", "000"));
        list.add(new CreditCard("4000 9999 8888 7777", 1, "Bob", "01232013", "899"));
        ArrayList<Service> services = new ArrayList<>();
        services.add(new Service("Accepted", "Inspection", "Bob", "88888888", "bob@yahoo.com", "1/1/2018", "15:00", "be on time!", p1, new CreditCard("5100 0000 0000 0000", 2, "Bob", "01232013", "000"), "$1000.00", -1));
        services.add(new Service("Completed", "Inspection", "Bob", "88888888", "bob@yahoo.com", "1/2/2018", "15:00", "have fun!", p1, new CreditCard("5100 0000 0000 0000", 2, "Bob", "01232013", "000"), "$1000.00", -1));
        user = new User("Bob", "88888888", "bob@yahoo.com", list, services);
    }
}



/*
public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_address) EditText _addressText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_mobile) EditText _mobileText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}
 */
