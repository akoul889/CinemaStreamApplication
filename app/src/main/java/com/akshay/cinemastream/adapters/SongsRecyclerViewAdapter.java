package com.akshay.cinemastream.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akshay.cinemastream.R;
import com.akshay.cinemastream.models.SongDetails;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class SongsRecyclerViewAdapter extends RecyclerView.Adapter<SongsRecyclerViewAdapter.SongsViewHolder> {

    List<SongDetails> contents;
    Context context;

    public SongsRecyclerViewAdapter(List<SongDetails> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public SongsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_song_list_item, parent, false);
        return new SongsViewHolder(view) {
        };
    }


    @Override
    public void onBindViewHolder(SongsViewHolder holder, int position) {
        holder.songTitle.setText(contents.get(position).getTitle());
        holder.durationView.setText(""+contents.get(position).getDuration()+" minutes");
    }


    static class SongsViewHolder extends RecyclerView.ViewHolder {

        public SongsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @Bind(R.id.song_title)
        TextView songTitle;
        @Bind(R.id.duration_View)
        TextView durationView;
    }
}