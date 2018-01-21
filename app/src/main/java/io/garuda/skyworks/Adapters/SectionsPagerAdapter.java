package io.garuda.skyworks.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.io.Serializable;

import io.garuda.skyworks.Fragments.AcceptServiceFragment;
import io.garuda.skyworks.Fragments.CompleteServiceFragment;
import io.garuda.skyworks.Fragments.NotificationsListFragment;
import io.garuda.skyworks.Fragments.OngoingServiceFragment;
import io.garuda.skyworks.Fragments.RequestServiceFragment;
import io.garuda.skyworks.Models.User;

/**
 * Created by joshl on 6/1/2018.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    User user;
    Context context;
    public SectionsPagerAdapter(FragmentManager fm, User user, Context context) {
        super(fm);
        this.user = user;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("USER", user);
        switch (position) {
            case 0:
                RequestServiceFragment requestFragment = new RequestServiceFragment();
                requestFragment.setArguments(bundle);
                return requestFragment;
            case 1:
                AcceptServiceFragment acceptFragment = new AcceptServiceFragment();
                acceptFragment.setArguments(bundle);
                return acceptFragment;
            case 2:
                OngoingServiceFragment ongoingFragment = new OngoingServiceFragment();
                ongoingFragment.setArguments(bundle);
                return ongoingFragment;
            case 3:
                CompleteServiceFragment completeFragment = new CompleteServiceFragment();
                completeFragment.setArguments(bundle);
                return completeFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Requested";
            case 1:
                return "Accepted";
            case 2:
                return "Ongoing";
            case 3:
                return "Completed";
        }
        return null;
    }
}
