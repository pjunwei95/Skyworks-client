package io.garuda.skyworks.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.garuda.skyworks.Adapters.NotificationsRecyclerViewAdapter;
import io.garuda.skyworks.Adapters.ServicesRecyclerViewAdapter;
import io.garuda.skyworks.Data.APIService;
import io.garuda.skyworks.Data.ApiUtils;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceListFragment extends Fragment {
    @BindView(R.id.rvServices)
    RecyclerView rvServices;
    User user;
    APIService mAPIService;
    SharedPreferences sharedPref;
    String userID;
    List<Service> services;
    List<String> serviceIDs;


    public ServiceListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_list, container, false);
        ButterKnife.bind(this, view);


        //set up manager
        LinearLayoutManager llm;
        llm = new LinearLayoutManager(this.getContext());

        rvServices.setHasFixedSize(true);
        rvServices.setLayoutManager(llm);



        sharedPref = this.getActivity().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        userID = sharedPref.getString("USER", "");

        //setup API Client
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getUser(userID).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()) {
                    user = response.body();

                    serviceIDs = user.getServiceIds();
                    services = new ArrayList<>();
                    for (int i = 0; i < serviceIDs.size(); i++) {
                        mAPIService.getJob(serviceIDs.get(i)).enqueue(new Callback<Service>() {
                            @Override
                            public void onResponse(Call<Service> call, Response<Service> response) {

                                if(response.isSuccessful()) {
                                    services.add(response.body());

                                    ServicesRecyclerViewAdapter adapter = new ServicesRecyclerViewAdapter(getContext(), services, getArguments());
                                    rvServices.setAdapter(adapter);
                                }
                            }
                            @Override
                            public void onFailure(Call<Service> call, Throwable t) {
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


        return view;
    }
}
