package com.akshay.cinemastream.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akshay.cinemastream.R;
import com.akshay.cinemastream.Utils.AppConstants;
import com.akshay.cinemastream.Utils.CinemaApiClient;
import com.akshay.cinemastream.Utils.CinemaApiService;
import com.akshay.cinemastream.adapters.MovieRecyclerViewAdapter;
import com.akshay.cinemastream.models.MovieDetail;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import timber.log.Timber;

public class MovieListFragment extends Fragment {
    public static final String ARG_MOVIE_SUBSTRING = "movie_substring";
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.error_title)
    TextView errorTitle;
    @Bind(R.id.error_details)
    TextView errorDetails;
    @Bind(R.id.error_layout)
    NestedScrollView errorLayout;
    private RecyclerView.Adapter mAdapter;
    private FragmentCallbacks mListener;
    private String titleSubstring;

    Observable<List<MovieDetail>> observableCall;
    Call<List<MovieDetail>> call;

    public static MovieListFragment newInstance(String titleSubstring) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE_SUBSTRING, titleSubstring);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            titleSubstring = getArguments().getString(titleSubstring);
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
        errorLayout.setVisibility(View.VISIBLE);
        errorTitle.setText(getString(R.string.search_helper));
        errorDetails.setText(getString(R.string.search_helper_details));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        searchText(titleSubstring);
//        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mContentItems));
//        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (FragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void searchText(final String titleSubstring) {
        if (call != null) {

            call.cancel();
            call = null;
        }
        if (!TextUtils.isEmpty(titleSubstring)) {
            this.titleSubstring = titleSubstring;
            mListener.showProgressDialog(false, "Searching...");
            Map<String, String> params = new HashMap<String, String>();
            params.put(CinemaApiService.VALUE_KEY, titleSubstring);
            params.put(CinemaApiService.TOKEN_KEY, AppConstants.TOKEN);

            call = CinemaApiClient.getApiService().searchMovieByTitle2(params);
            call.enqueue(new Callback<List<MovieDetail>>() {
                @Override
                public void onResponse(Response<List<MovieDetail>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Timber.i("Success got movie List " + response.body().size());
                        mListener.dismissProgressDialog();
                        if (response.body()!=null && !response.body().isEmpty()) {
                            errorLayout.setVisibility(View.GONE);
                            mAdapter = new RecyclerViewMaterialAdapter(new MovieRecyclerViewAdapter(
                                    response.body(), mListener));
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

                @Override
                public void onFailure(Throwable t) {

                    mListener.dismissProgressDialog();
                    errorLayout.setVisibility(View.VISIBLE);
                    errorTitle.setText(getString(R.string.no_data));
                    errorDetails.setText(getString(R.string.no_data_text));
                }
            });

//            observableCall = CinemaApiClient.getApiService().searchMovieByTitle(params);
//            observableCall.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                            new Action1<List<MovieDetail>>() {
//                                @Override
//                                public void call(List<MovieDetail> movieList) {
//                                    Timber.i("Success got movie List");
//                                    mListener.dismissProgressDialog();
//                                    mAdapter = new RecyclerViewMaterialAdapter(new MovieRecyclerViewAdapter(
//                                            movieList));
//                                    mRecyclerView.setAdapter(mAdapter);
//                                }
//                            });
        }
    }

}
