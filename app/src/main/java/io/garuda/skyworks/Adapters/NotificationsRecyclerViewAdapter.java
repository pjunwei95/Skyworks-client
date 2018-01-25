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
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import io.garuda.skyworks.R;

/**
 * Created by joshl on 4/1/2018.
 */

public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.ViewHolder>  implements Serializable{


    Context context;
    Bundle extras;
    User user;
    ArrayList<Service> services;

    public NotificationsRecyclerViewAdapter(Context context, User user, Bundle extras) {
        this.extras = extras;
        this.context = context;
        this.user = user;
        this.services = user.getServices();

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
    public void onBindViewHolder(NotificationsRecyclerViewAdapter.ViewHolder holder, int position) {


        Service service = services.get(position);

        if (service.getStatus().equals("Completed") || service.getStatus().equals("Accepted") ||
                service.getStatus().equals("Ongoing")) {
            holder.serviceName.setText(service.getType());
            holder.date_time.setText(service.getDate() + " " + service.getTime());
            holder.status.setText("Status: " + service.getStatus());

            Picasso.with(getContext())
                    .load(service.getProvider().getPosterPath())//need to change to online URL!!
                    .resize(150, 150).centerInside()
                    .into(holder.ivProviderImage);
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

            if (service.getStatus().equals("Accepted")) {
                Intent intent = new Intent(getContext(), AcceptServiceDetail.class);
                intent.putExtras(extras);
                intent.putExtra("SELECTEDSERVICE", service);
                intent.putExtra("CALLER1", Notifications.class);
                getContext().startActivity(intent);
            } else if (service.getStatus().equals("Completed")) {
                if (service.getRating() != -1) {
                    Intent intent = new Intent(getContext(), CompleteServiceDetail.class);
                    intent.putExtras(extras);
                    intent.putExtra("SELECTEDSERVICE", service);
                    intent.putExtra("CALLER1", Notifications.class);
                    getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), CompleteServiceDetailRate.class);
                    intent.putExtras(extras);
                    intent.putExtra("SELECTEDSERVICE", service);
                    intent.putExtra("CALLER1", Notifications.class);
                    getContext().startActivity(intent);
                }
            } else if (service.getStatus().equals("Ongoing")) {
                Intent intent = new Intent(getContext(), OngoingServiceDetail.class);
                intent.putExtras(extras);
                intent.putExtra("SELECTEDSERVICE", service);
                intent.putExtra("CALLER1", Notifications.class);
                getContext().startActivity(intent);
            }
        }
    }
}
