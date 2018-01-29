package io.garuda.skyworks.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
