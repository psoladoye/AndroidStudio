package com.example.paul.samsungs6;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Paul on 2/4/2016.
 */
public class LocationAdapter extends BaseAdapter {

    private Location[] locations;
    private Context context;
    private LayoutInflater inflater;

    public LocationAdapter(Context context, Location[] locations) {
        this.locations = locations;
        this.context = context;
    }

    @Override
    public int getCount() {
        return locations.length;
    }

    @Override
    public Object getItem(int position) {
        return locations[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.location_list_item, parent,false);

        Location location = (Location) getItem(position);

        ImageView thumbnail = (ImageView) rowView.findViewById(R.id.thumbnail);
        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView description = (TextView) rowView.findViewById(R.id.description);

        Picasso.with(context).load(location.getImageUrl()).resize(120,120).into(thumbnail);
        title.setText(location.getTitle());
        description.setText(location.getDescription());
        return rowView;
    }
}
