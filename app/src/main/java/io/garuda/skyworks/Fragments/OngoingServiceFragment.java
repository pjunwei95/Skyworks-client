package io.garuda.skyworks.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.garuda.skyworks.Adapters.ServicesRecyclerViewAdapter;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

public class OngoingServiceFragment extends Fragment {
    @BindView(R.id.rvServices)
    RecyclerView rvServices;
    User user;


    public OngoingServiceFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ongoing, container, false);
        ButterKnife.bind(this, view);


        //set up manager
        LinearLayoutManager llm;
        llm = new LinearLayoutManager(getActivity());

        rvServices.setHasFixedSize(true);
        rvServices.setLayoutManager(llm);


        //pass in data
        user = (User) getArguments().getSerializable("USER");

        ServicesRecyclerViewAdapter adapter = new ServicesRecyclerViewAdapter(getContext(), user, getArguments(), "Ongoing");

        rvServices.setAdapter(adapter);

        return view;
    }
}
