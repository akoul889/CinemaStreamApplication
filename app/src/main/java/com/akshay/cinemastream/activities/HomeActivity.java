package com.akshay.cinemastream.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.akshay.cinemastream.R;
import com.akshay.cinemastream.Utils.AppConstants;
import com.akshay.cinemastream.Utils.Utils;
import com.akshay.cinemastream.fragments.FragmentCallbacks;
import com.akshay.cinemastream.fragments.MovieListFragment;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity implements FragmentCallbacks {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;

    private Subscription mSubscription;
    private MovieListFragment movieListFragment;
    protected ProgressDialog mProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mProgressDialog = new ProgressDialog(HomeActivity.this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchView.setVoiceSearch(true);
        searchView.setSuggestions(Utils.getSugessions(getApplicationContext()));
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
        movieListFragment = MovieListFragment.newInstance("");
        navigateTo(movieListFragment, MovieListFragment.class.getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        if (movieListFragment != null) {
            movieListFragment = null;
        }
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        mSubscription = RxTextView.textChangeEvents(searchView.getmSearchSrcTextView())//
                .debounce(400, TimeUnit.MILLISECONDS)// default Scheduler is Computation
                .observeOn(AndroidSchedulers.mainThread())//
                .filter(new Func1<TextViewTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        return textViewTextChangeEvent.text().length() > 2;
                    }
                })
                .subscribe(getSearchObserver());
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private Observer<TextViewTextChangeEvent> getSearchObserver() {
        return new Observer<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {
                Timber.d("--------- onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e, "--------- Woops on error!");
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
//                    Toast.makeText(HomeActivity.this, "text = " + onTextChangeEvent.text().toString(), Toast.LENGTH_SHORT).show();
                    if (movieListFragment != null) {
                        Timber.i("Searching");
                        movieListFragment.searchText(onTextChangeEvent.text().toString());
                    }else{
                        Timber.i("not searching");
                    }
            }
        };
    }

    @Override
    public void navigateTo(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.addToBackStack(title);
        fragmentTransaction.commit();
    }

    @Override
    public void openMovie(String movieId, String movieTitle) {
        Intent intent = new Intent(HomeActivity.this, MovieActivity.class);
        intent.putExtra(AppConstants.KEY_MOVIE_ID, movieId);
        intent.putExtra(AppConstants.KEY_MOVIE_TITLE, movieTitle);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void showProgressDialog(boolean cancelable, String message) {
        if (mProgressDialog != null) {
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(cancelable);
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }else{

        }
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void updateSearchSugessions() {
        if (searchView!=null) {
            searchView.setSuggestions(Utils.getSugessions(getApplicationContext()));
        }
    }


    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
            }else{
                super.onBackPressed();
            }
        }

    }
}
