package io.garuda.skyworks.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import io.garuda.skyworks.Adapters.ProvidersRecyclerViewAdapter;
import io.garuda.skyworks.Data.APIService;
import io.garuda.skyworks.Data.ApiUtils;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by joshl on 5/6/2017.
 *
 * This is the fragment containing the recycler view for Providers activity
 */

public class ProvidersListFragment extends Fragment {


    @BindView(io.garuda.skyworks.R.id.rvProvider)
    RecyclerView rvProvider;
    List<Provider> providers;
    Service service;
    User user;
    ArrayList<LatLng> arrayPoints;
    APIService mAPIService;


    public ProvidersListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(io.garuda.skyworks.R.layout.fragment_providers_list, container, false);
        ButterKnife.bind(this, view);


        /*
        //initialise providers data (to be replaced)
        initialise();
        */


        //set up manager
        LinearLayoutManager llm;
        llm = new LinearLayoutManager(this.getContext());

        rvProvider.setHasFixedSize(true);
        rvProvider.setLayoutManager(llm);

        //setup API Client
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getOperator().enqueue(new Callback<List<Provider>>() {
            @Override
            public void onResponse(Call<List<Provider>> call, Response<List<Provider>> response) {
                if(response.isSuccessful()) {
                    providers = response.body();
                    Log.i("TAG", "operators received from API.");

                    //pass in data
                    service = (Service) getArguments().getSerializable("SERVICE");
                    user = (User) getArguments().getSerializable("USER");

                    arrayPoints = (ArrayList<LatLng>) getArguments().getSerializable("LOC");
                    ProvidersRecyclerViewAdapter adapter = new ProvidersRecyclerViewAdapter(getContext(), providers, service, user, arrayPoints);

                    rvProvider.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Provider>> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });


        return view;
    }


}