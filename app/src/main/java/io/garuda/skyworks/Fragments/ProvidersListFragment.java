package io.garuda.skyworks.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.garuda.skyworks.Adapters.ProvidersRecyclerViewAdapter;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joshl on 5/6/2017.
 *
 * This is the fragment containing the recycler view for Providers activity
 */

public class ProvidersListFragment extends Fragment {


    @BindView(io.garuda.skyworks.R.id.rvProvider)
    RecyclerView rvProvider;
    private List<Provider> providers;
    Service service;
    User user;



    public ProvidersListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(io.garuda.skyworks.R.layout.fragment_providers_list, container, false);
        ButterKnife.bind(this, view);


        //set up manager
        LinearLayoutManager llm;
        llm = new LinearLayoutManager(this.getContext());

        rvProvider.setHasFixedSize(true);
        rvProvider.setLayoutManager(llm);

        //initialise providers data (to be replaced)
        initialise();

        //pass in data
        service = (Service) getArguments().getSerializable("SERVICE");
        user = (User) getArguments().getSerializable("USER");
        ProvidersRecyclerViewAdapter adapter = new ProvidersRecyclerViewAdapter(getContext(), providers, service, user);

        rvProvider.setAdapter(adapter);

        return view;
    }

    //initialise providers data (to be replaced)
    public void initialise () {

        ArrayList<String> l = new ArrayList<String>();
        l.add("https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwjA-Ya0v7bYAhUX3o8KHZu1AHYQjBwIBA&url=https%3A%2F%2Fimage.freepik.com%2Ffree-photo%2Fobservation-urban-building-business-steel_1127-2397.jpg&psig=AOvVaw0RMQOB2sF0uud8tLYKxmpR&ust=1514886796255210");
        l.add("https://static.pexels.com/photos/302769/pexels-photo-302769.jpeg");
        l.add("https://blogs.intel.com/iot/files/2015/01/SmartBuilding.jpg");
        l.add("http://www.buildingtechnologies.siemens.com/bt/global/en/PublishingImages/total-building-solutions-key-visual-large.jpg");
        Provider p1 = new Provider("Garuda", "Founded in 2014, Garuda Robotics Pte Ltd is a Robotics and Artificial Intelligence technology company. Headquartered in Singapore and focused on clients in Asia, we build products and solutions for enterprises operating in agriculture, infrastructure and security. Our clients are located in Singapore, Malaysia, Indonesia and the Philippines, and include SMEs, multinational corporations and government agencies.", "https://garuda.io/wp-content/uploads/2017/05/2016-Garuda-Logo.png", "12345", l);
        Provider p2 = new Provider("Big Drones", "We are a big big drone company!", "https://burnieworks.com.au/img/big/big-inset.jpg", "33333", l);
        Provider p3 = new Provider("Small Drones", "We are a small small drone company!", "http://www.small-project.eu/SMALL.png", "11111", l);

        providers = new ArrayList<Provider>();
        providers.add(p1);
        providers.add(p2);
        providers.add(p3);

    }


}