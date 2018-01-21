package io.garuda.skyworks.Adapters;

/**
 * Created by joshl on 1/1/2018.
 *
 * This is an adapter to load images into gallery list in provider detail page
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryListAdapter extends ArrayAdapter<String>{
    private final Activity context;
    private final ArrayList<String> imageUrls;
    private LayoutInflater inflater;

    public GalleryListAdapter(Activity context, ArrayList<String> imageUrls) {
        super(context, io.garuda.skyworks.R.layout.gallery_list_item, imageUrls);
        this.context = context;
        this.imageUrls = imageUrls;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if (null == convertView) {
            convertView = inflater.inflate(io.garuda.skyworks.R.layout.gallery_list_item, null, false);
        }
        Picasso
                .with(context)
                .load(imageUrls.get(position))
                .resize(1000, 500).centerCrop()
                .into((ImageView) convertView);

        return convertView;
    }

}
