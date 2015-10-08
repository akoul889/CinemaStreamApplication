package com.akshay.cinemastream.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akshay.cinemastream.R;
import com.akshay.cinemastream.Utils.AppConstants;
import com.akshay.cinemastream.Utils.CinemaApiClient;
import com.akshay.cinemastream.adapters.PersonRecyclerViewAdapter;
import com.akshay.cinemastream.models.PersonDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import timber.log.Timber;

/**
 * Created by AKSHAY on 02-10-2015.
 */
public class PersonListFragment extends Fragment {

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private String movieId;
    private int personType;
    public static final String ARG_MOVIE_ID = "movie_id";
    public static final String ARG_PERSON_TYPE = "person_type";
    @Bind(R.id.error_title)
    TextView errorTitle;
    @Bind(R.id.error_details)
    TextView errorDetails;
    @Bind(R.id.error_layout)
    NestedScrollView errorLayout;


    List<PersonDetails> personList = new ArrayList<PersonDetails>();
    private GridLayoutManager mLayoutManager;

    public static PersonListFragment newInstance(String movieId,int personType) {
        PersonListFragment fragment = new PersonListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE_ID, movieId);
        args.putInt(ARG_PERSON_TYPE, personType);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getString(ARG_MOVIE_ID);
            personType = getArguments().getInt(ARG_PERSON_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new GridAdapter();
//        mRecyclerView.setAdapter(mAdapter);


        Call<List<PersonDetails>> call = CinemaApiClient.getApiService().loadMovieConnections(movieId,getConnectionType() , AppConstants.TOKEN);

        call.enqueue(getCallBack());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    private Callback<List<PersonDetails>> getCallBack(){
        return new Callback<List<PersonDetails>>() {
            @Override
            public void onResponse(Response<List<PersonDetails>> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    Timber.i("success");
                    personList = response.body();
                    if (personList!=null && !personList.isEmpty()) {
                        errorLayout.setVisibility(View.GONE);
                        initAdapter();
                    }else{
                        errorLayout.setVisibility(View.VISIBLE);
                        errorTitle.setText(getString(R.string.no_data));
                        errorDetails.setText(getString(R.string.no_data_text));
                    }
                }else{
                    Timber.i("failure");
                    errorLayout.setVisibility(View.VISIBLE);
                    errorTitle.setText(getString(R.string.no_data));
                    errorDetails.setText(getString(R.string.no_data_text));
                }
            }

            @Override
            public void onFailure(Throwable t) {

                errorLayout.setVisibility(View.VISIBLE);
                errorTitle.setText(getString(R.string.no_data));
                errorDetails.setText(getString(R.string.no_data_text));
            }
        };
    }

    private void initAdapter(){
        if (personList != null && !personList.isEmpty()) {
            Collections.sort(personList);
            mAdapter = new PersonRecyclerViewAdapter(personList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
    private String getConnectionType(){
        String connectionType;
        switch (personType){
            case AppConstants.PERSON_ACTOR:
                connectionType = "actors";
            break;
            case AppConstants.PERSON_DIRECTOR:
                connectionType = "directors";
            break;
            default:
                connectionType = "actors";
            break;
        }
        return connectionType;
    }
}
