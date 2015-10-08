package com.akshay.cinemastream.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akshay.cinemastream.R;
import com.akshay.cinemastream.Utils.AppConstants;
import com.akshay.cinemastream.Utils.CinemaApiClient;
import com.akshay.cinemastream.adapters.SongsRecyclerViewAdapter;
import com.akshay.cinemastream.models.SongDetails;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SongListFragment extends Fragment {
    public static final String ARG_MOVIE_ID = "movie_id";
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.error_title)
    TextView errorTitle;
    @Bind(R.id.error_details)
    TextView errorDetails;
    @Bind(R.id.error_layout)
    NestedScrollView errorLayout;
    private RecyclerView.Adapter mAdapter;

    private String movieId;

    public static SongListFragment newInstance(String movieId) {
        SongListFragment fragment = new SongListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    public SongListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getString(ARG_MOVIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        Call<List<SongDetails>> call = CinemaApiClient.getApiService().loadMovieSongs(movieId, AppConstants.TOKEN);
        call.enqueue(new Callback<List<SongDetails>>() {
            @Override
            public void onResponse(Response<List<SongDetails>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body()!=null) {
                        if (response.body()!=null && !response.body().isEmpty()) {
                            errorLayout.setVisibility(View.GONE);
                            mAdapter = new RecyclerViewMaterialAdapter(new SongsRecyclerViewAdapter(response.body()));
                            mRecyclerView.setAdapter(mAdapter);
                        }else{
                            errorLayout.setVisibility(View.VISIBLE);
                            errorTitle.setText(getString(R.string.no_data));
                            errorDetails.setText(getString(R.string.no_data_text));
                        }
                    }else{

                        errorLayout.setVisibility(View.VISIBLE);
                        errorTitle.setText(getString(R.string.no_data));
                        errorDetails.setText(getString(R.string.no_data_text));
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

                errorLayout.setVisibility(View.VISIBLE);
                errorTitle.setText(getString(R.string.no_data));
                errorDetails.setText(getString(R.string.no_data_text));
            }
        });
//        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mContentItems));
//        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
