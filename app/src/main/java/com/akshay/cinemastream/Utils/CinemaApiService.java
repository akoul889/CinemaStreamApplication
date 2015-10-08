package com.akshay.cinemastream.Utils;

import com.akshay.cinemastream.models.LocationDetail;
import com.akshay.cinemastream.models.MovieDetail;
import com.akshay.cinemastream.models.PersonDetails;
import com.akshay.cinemastream.models.SongDetails;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by AKSHAY on 02-10-2015.
 */
public interface CinemaApiService {
    public static final String MOVIE_ID = "movie_id";
    public static final String CONNECTION_TYPE = "connection_type";
    public static final String TOKEN_KEY = "auth_token";
    public static final String VALUE_KEY = "value";

    @GET("movie/id/{"+MOVIE_ID+"}/")
    Call<MovieDetail> loadMovie(@Path(MOVIE_ID) String movieId,@Query(TOKEN_KEY) String token);
    @GET("movie/{"+MOVIE_ID+"}/{"+CONNECTION_TYPE+"}/")
    Call<List<PersonDetails>> loadMovieConnections(@Path(MOVIE_ID) String movieId,@Path(CONNECTION_TYPE) String connectionType, @Query(TOKEN_KEY) String token);
    @GET("movie/{"+MOVIE_ID+"}/songs/")
    Call<List<SongDetails>> loadMovieSongs(@Path(MOVIE_ID) String movieId, @Query(TOKEN_KEY) String token);
    @GET("movie/{"+MOVIE_ID+"}/locations/")
    Call<List<LocationDetail>> loadMovieFilmingLoctions(@Path(MOVIE_ID) String movieId, @Query(TOKEN_KEY) String token);
    @GET("movie/title/")
    Observable<List<MovieDetail>> searchMovieByTitle(@QueryMap Map<String,String> params);
    @GET("movie/title/")
    Call<List<MovieDetail>> searchMovieByTitle2(@QueryMap Map<String,String> params);
}
