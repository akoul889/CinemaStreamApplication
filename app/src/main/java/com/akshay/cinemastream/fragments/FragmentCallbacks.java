package com.akshay.cinemastream.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by akshaykoul on 6/10/15.
 */
public interface FragmentCallbacks {
    public void navigateTo(Fragment fragment,String title);
    public void openMovie(String movieId,String movieTitle);
    public void showProgressDialog(boolean cancelable, String message);
    public void dismissProgressDialog();
    public void updateSearchSugessions();
}
