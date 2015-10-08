package com.akshay.cinemastream.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.akshay.cinemastream.R;
import com.akshay.cinemastream.Utils.AppConstants;
import com.akshay.cinemastream.fragments.LocationListFragment;
import com.akshay.cinemastream.fragments.MovieDetailFragment;
import com.akshay.cinemastream.fragments.PersonListFragment;
import com.akshay.cinemastream.fragments.SongListFragment;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    String movieTitle;
    String movieId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieId = getIntent().getStringExtra(AppConstants.KEY_MOVIE_ID);
        movieTitle = getIntent().getStringExtra(AppConstants.KEY_MOVIE_TITLE);
        setContentView(R.layout.activity_movies);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(movieTitle);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(MovieDetailFragment.newInstance(movieId), getString(R.string.text_details));
        adapter.addFragment(PersonListFragment.newInstance(movieId, AppConstants.PERSON_ACTOR), getString(R.string.text_actors));
        adapter.addFragment(PersonListFragment.newInstance(movieId, AppConstants.PERSON_DIRECTOR), getString(R.string.directors));
        adapter.addFragment(SongListFragment.newInstance(movieId), getString(R.string.text_songs));
        adapter.addFragment(LocationListFragment.newInstance(movieId), getString(R.string.text_location));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
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

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
