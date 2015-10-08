package com.akshay.cinemastream.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.akshay.cinemastream.R;
import com.akshay.cinemastream.Utils.Utils;
import com.akshay.cinemastream.fragments.FragmentCallbacks;
import com.akshay.cinemastream.models.MovieDetail;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.LocationViewHolder> {

    List<MovieDetail> contents;
    Context context;
    FragmentCallbacks mListener;

    public MovieRecyclerViewAdapter(List<MovieDetail> contents,FragmentCallbacks fragmentCallbacks) {
        mListener = fragmentCallbacks;
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
                .inflate(R.layout.layout_movie_details_header, parent, false);
        return new LocationViewHolder(view) {
        };
    }


    @Override
    public void onBindViewHolder(LocationViewHolder holder, final int position) {
        MovieDetail movieDetail = contents.get(position);
        holder.titleView.setText(Utils.getFormattedDisplayText(movieDetail.getTitle()));
        holder.genreView.setText(Utils.getFormattedDisplayText(movieDetail.getGenre()));
        holder.censorRatingView.setText(Utils.getFormattedDisplayText(movieDetail.getCensorRating()));
        holder.runtimeView.setText(Utils.getFormattedTime(movieDetail.getRuntime()));
        Glide.with(context)
                .load(movieDetail.getPosterPath())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .crossFade()
                .into(holder.posterImageView);
        holder.ratingBar.setRating((float) movieDetail.getRating());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null) {
                    mListener.openMovie(contents.get(position).getId(),contents.get(position).getTitle());
                    Utils.saveSugession(context.getApplicationContext(),contents.get(position).getTitle());
                    mListener.updateSearchSugessions();
                }
            }
        });
    }


    static class LocationViewHolder extends RecyclerView.ViewHolder {

        public LocationViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }



        @Bind(R.id.poster_image_view)
        ImageView posterImageView;
        @Bind(R.id.title_view)
        TextView titleView;
        @Bind(R.id.genre_view)
        TextView genreView;
        @Bind(R.id.runtime_view)
        TextView runtimeView;
        @Bind(R.id.rating_label)
        TextView ratingLabel;
        @Bind(R.id.ratingBar)
        RatingBar ratingBar;
        @Bind(R.id.censor_rating_label)
        TextView censorRatingLabel;
        @Bind(R.id.censor_rating_view)
        TextView censorRatingView;
        @Bind(R.id.card_view)
        CardView cardView;
    }
}