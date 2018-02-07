package io.garuda.skyworks.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.garuda.skyworks.Activities.AcceptServiceDetail;
import io.garuda.skyworks.Activities.CompleteServiceDetail;
import io.garuda.skyworks.Activities.CompleteServiceDetailRate;
import io.garuda.skyworks.Activities.Notifications;
import io.garuda.skyworks.Activities.OngoingServiceDetail;
import io.garuda.skyworks.Activities.ProviderDetail;
import io.garuda.skyworks.Data.APIService;
import io.garuda.skyworks.Data.ApiUtils;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by joshl on 4/1/2018.
 */

public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.ViewHolder>  implements Serializable{


    Context context;
    Bundle extras;
    List<Service> services;
    Provider provider;
    APIService mAPIService;

    public NotificationsRecyclerViewAdapter(Context context, List<Service> services, Bundle extras) {
        this.extras = extras;
        this.context = context;
        this.services = services;

    }

    private Context getContext(){
        return context;
    }

    @Override
    public  NotificationsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(io.garuda.skyworks.R.layout.notification_item, parent, false);
        return new  NotificationsRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NotificationsRecyclerViewAdapter.ViewHolder holder, int position) {


        Service service = services.get(position);

        if (service.getStatus().equals("Awaiting Confirmation from Customer") ||
                service.getStatus().equals("Job Done") ||
                service.getStatus().equals("Awaiting for Permit Details") ||
                service.getStatus().equals("Checking for Valid Permit Details") ||
                service.getStatus().equals("Ready to Fly") ||
                service.getStatus().equals("Payment Successful")) {
            holder.serviceName.setText(service.getType());
            holder.date_time.setText(service.getDate() + " " + service.getTime());
            holder.status.setText("Status: " + service.getStatus());

            mAPIService = ApiUtils.getAPIService(getContext());
            mAPIService.getProvider(service.getOperatorID()).enqueue(new Callback<Provider>() {
                @Override
                public void onResponse(Call<Provider> call, Response<Provider> response) {

                    if(response.isSuccessful()) {
                        provider = response.body();
                        Picasso.with(getContext())
                                .load(provider.getPosterPath())//need to change to online URL!!
                                .resize(150, 150).centerInside()
                                .into(holder.ivProviderImage);
                    }
                }
                @Override
                public void onFailure(Call<Provider> call, Throwable t) {
                    Log.e("TAG", t.toString());
                }
            });


        }


    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(io.garuda.skyworks.R.id.ivProviderImage)
        ImageView ivProviderImage;
        @BindView(R.id.serviceName)
        TextView serviceName;
        @BindView(R.id.date_time)
        TextView date_time;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.cvNotification)
        CardView cvNotification;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Service service = services.get(getAdapterPosition());

            if (service.getStatus().equals("Awaiting Confirmation from Customer")) {
                Intent intent = new Intent(getContext(), AcceptServiceDetail.class);
                intent.putExtras(extras);
                intent.putExtra("SELECTEDSERVICE", service);
                intent.putExtra("CALLER1", Notifications.class);
                getContext().startActivity(intent);
            } else if (service.getStatus().equals("Payment Successful")) {
                Intent intent = new Intent(getContext(), CompleteServiceDetail.class);
                intent.putExtras(extras);
                intent.putExtra("SELECTEDSERVICE", service);
                intent.putExtra("CALLER1", Notifications.class);
                getContext().startActivity(intent);
            } else if (service.getStatus().equals("Job Done")){
                Intent intent = new Intent(getContext(), CompleteServiceDetailRate.class);
                intent.putExtras(extras);
                intent.putExtra("SELECTEDSERVICE", service);
                intent.putExtra("CALLER1", Notifications.class);
                getContext().startActivity(intent);

            } else if (service.getStatus().equals("Awaiting for Permit Details") ||
                    service.getStatus().equals("Checking for Valid Permit Details") ||
                    service.getStatus().equals("Ready to Fly")) {
                Intent intent = new Intent(getContext(), OngoingServiceDetail.class);
                intent.putExtras(extras);
                intent.putExtra("SELECTEDSERVICE", service);
                intent.putExtra("CALLER1", Notifications.class);
                getContext().startActivity(intent);
            }
        }
    }
}
