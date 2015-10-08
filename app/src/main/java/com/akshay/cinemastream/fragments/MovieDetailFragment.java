package com.akshay.cinemastream.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.akshay.cinemastream.R;
import com.akshay.cinemastream.Utils.AppConstants;
import com.akshay.cinemastream.Utils.CinemaApiClient;
import com.akshay.cinemastream.Utils.Utils;
import com.akshay.cinemastream.models.MovieDetail;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class MovieDetailFragment extends Fragment {

    private static final String ARG_MOVIE_ID = "movie_id";
    @Bind(R.id.poster_image_view)
    ImageView posterImageView;
    @Bind(R.id.title_view)
    TextView titleView;
    @Bind(R.id.genre_view)
    TextView genreView;
    @Bind(R.id.runtime_view)
    TextView runtimeView;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.censor_rating_view)
    TextView censorRatingView;
    @Bind(R.id.detail_view)
    TextView detailView;
    private String movieId = "";

    public static MovieDetailFragment newInstance(String movieId) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE_ID, movieId);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getString(ARG_MOVIE_ID);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Call<MovieDetail> call = CinemaApiClient.getApiService().loadMovie(movieId, AppConstants.TOKEN);
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Response<MovieDetail> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    MovieDetail movieDetail = response.body();
                    Log.i("MovieDetail", "= " + movieDetail.toString());
                    initLayout(movieDetail);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        ButterKnife.bind(this, layout);
        return layout;
    }

    private void initLayout(MovieDetail movieDetail) {
        titleView.setText(Utils.getFormattedDisplayText(movieDetail.getTitle()));
        genreView.setText(Utils.getFormattedDisplayText(movieDetail.getGenre()));
        censorRatingView.setText(Utils.getFormattedDisplayText(movieDetail.getCensorRating()));
        detailView.setText(Utils.getFormattedDisplayText(movieDetail.getDescription()));
        runtimeView.setText(Utils.getFormattedTime(movieDetail.getRuntime()));
        Glide.with(this)
                .load(movieDetail.getPosterPath())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .crossFade()
                .into(posterImageView);
        ratingBar.setRating((float) movieDetail.getRating());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//


}
