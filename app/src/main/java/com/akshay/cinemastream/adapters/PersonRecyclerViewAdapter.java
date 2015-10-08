package com.akshay.cinemastream.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akshay.cinemastream.R;
import com.akshay.cinemastream.models.PersonDetails;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class PersonRecyclerViewAdapter extends RecyclerView.Adapter<PersonRecyclerViewAdapter.ViewHolder> {

    List<PersonDetails> contents;
Context context;

    public PersonRecyclerViewAdapter(List<PersonDetails> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public PersonRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_actor_card, parent, false);
        return new ViewHolder(view) {
        };
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(contents.get(position).getProfilePath())
                .centerCrop()
                .placeholder(R.drawable.placeholder_person)
                .error(R.drawable.placeholder_person)
                .crossFade()
                .into(holder.posterImageView);
        holder.nameView.setText(contents.get(position).getName());
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'list_item_actor_card.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.poster_image_view)
        ImageView posterImageView;
        @Bind(R.id.name_view)
        TextView nameView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}