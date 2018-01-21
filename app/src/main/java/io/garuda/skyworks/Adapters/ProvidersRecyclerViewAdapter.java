package io.garuda.skyworks.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.garuda.skyworks.Activities.ProviderDetail;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;
import com.squareup.picasso.Picasso;

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
    User user;

    public ProvidersRecyclerViewAdapter(Context context, List<Provider> providers, Service service, User user) {
        this.providers = providers;
        this.context = context;
        this.service = service;
        this.user = user;
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
                .resize(500, 500).centerInside()
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
            Intent intent = new Intent(getContext(), ProviderDetail.class);
            intent.putExtra("SERVICE", service);
            intent.putExtra("PROVIDER", provider);
            intent.putExtra("USER", user);
            getContext().startActivity(intent);



        }
    }
}
