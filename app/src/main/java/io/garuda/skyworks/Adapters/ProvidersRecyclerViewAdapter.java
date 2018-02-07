package io.garuda.skyworks.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.garuda.skyworks.Activities.ProviderDetail;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.SerializableLatLng;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joshl on 29/12/2017.
 *
 * This is an adapter for cards/recycler view for Providers activity
 */

public class ProvidersRecyclerViewAdapter extends RecyclerView.Adapter<ProvidersRecyclerViewAdapter.ViewHolder>{

    List<Provider> providers;
    Context context;
    Service service;
    ArrayList<SerializableLatLng> locationPoints;
    SharedPreferences sharedPref;
    Activity activity;

    public ProvidersRecyclerViewAdapter(Context context, List<Provider> providers, Service service, ArrayList<SerializableLatLng> locationPoints, Activity activity) {
        this.providers = providers;
        this.context = context;
        this.service = service;
        this.locationPoints = locationPoints;
        this.activity = activity;
    }

    private Context getContext(){
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(io.garuda.skyworks.R.layout.provider_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Provider provider = providers.get(position);

        holder.ProviderName.setText(provider.getName());
        holder.ProviderOverview.setText(provider.getOverview());

        Picasso.with(getContext())
                .load(provider.getPosterPath())//need to change to online URL!!
                .resize(150, 150).centerInside()
                .into(holder.ivProviderImage);

    }

    @Override
    public int getItemCount() {
        return providers.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(io.garuda.skyworks.R.id.ivProviderImage)
        ImageView ivProviderImage;
        @BindView(io.garuda.skyworks.R.id.ProviderName)
        TextView ProviderName;
        @BindView(io.garuda.skyworks.R.id.ProviderOverview)
        TextView ProviderOverview;
        @BindView(io.garuda.skyworks.R.id.cvProvider)
        CardView cvProvider;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Provider provider = providers.get(getAdapterPosition());
            sharedPref = activity.getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("PROVIDER", provider.getId());
            editor.commit();
            Intent intent = new Intent(getContext(), ProviderDetail.class);
            intent.putExtra("SERVICE", service);
            intent.putExtra("PROVIDER", provider);
            intent.putExtra("LOC", locationPoints);
            getContext().startActivity(intent);



        }
    }
}
