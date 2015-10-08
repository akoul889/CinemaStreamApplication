package com.akshay.cinemastream.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akshay.cinemastream.R;
import com.akshay.cinemastream.models.LocationDetail;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.LocationViewHolder> {

    List<LocationDetail> contents;
    Context context;

    public LocationRecyclerViewAdapter(List<LocationDetail> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_location_list_item, parent, false);
        return new LocationViewHolder(view) {
        };
    }


    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        holder.locationTitle.setText(contents.get(position).getName());
        holder.stateView.setText("" + contents.get(position).getState());
        holder.countryView.setText("" + contents.get(position).getCountry());
    }


    static class LocationViewHolder extends RecyclerView.ViewHolder {

        public LocationViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


        @Bind(R.id.location_title)
        TextView locationTitle;
        @Bind(R.id.state_View)
        TextView stateView;
        @Bind(R.id.country_View)
        TextView countryView;
    }
}