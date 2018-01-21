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

public class Landing extends AppCompatActivity {


    Button login;
    TextView signUp;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        this.setTitle("Welcome to SkyWorks");

        //Bind views
        login = (Button) findViewById(R.id.btn_login);
        signUp = (TextView) findViewById(R.id.link_signup);

        //initialise data (to be replaced by backend implementation)
        initialise();

        //listeners for buttons
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Landing.this, ChooseService.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("USER", user);
                i.putExtras(mBundle);
                startActivity(i);

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Landing.this, SignUp.class);
                startActivity(i);

            }
        });


    }

    //method to initialise data
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
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
 */
