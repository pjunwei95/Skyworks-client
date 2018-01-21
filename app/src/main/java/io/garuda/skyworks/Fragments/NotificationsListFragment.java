package io.garuda.skyworks.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.garuda.skyworks.Activities.Notifications;
import io.garuda.skyworks.Adapters.NotificationsRecyclerViewAdapter;
import io.garuda.skyworks.Adapters.ProvidersRecyclerViewAdapter;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

/**
 * Created by joshl on 4/1/2018.
 */

public class NotificationsListFragment extends Fragment {

    @BindView(R.id.rvNotifications)
    RecyclerView rvNotifications;
    User user;


    public NotificationsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications_list, container, false);
        ButterKnife.bind(this, view);


        //set up manager
        LinearLayoutManager llm;
        llm = new LinearLayoutManager(this.getContext());

        rvNotifications.setHasFixedSize(true);
        rvNotifications.setLayoutManager(llm);



        //pass in data
        user = (User) getArguments().getSerializable("USER");

        NotificationsRecyclerViewAdapter adapter = new NotificationsRecyclerViewAdapter(getContext(), user, getArguments());

        rvNotifications.setAdapter(adapter);

        return view;
    }

}
