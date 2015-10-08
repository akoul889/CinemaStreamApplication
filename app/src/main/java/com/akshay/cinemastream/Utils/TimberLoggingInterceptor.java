package com.akshay.cinemastream.Utils;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import okio.Buffer;
import timber.log.Timber;

public class TimberLoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Timber.i("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers());
        Timber.v("REQUEST BODY BEGIN\n%s\nREQUEST BODY END", bodyToString(request));

        Response response = chain.proceed(request);

        ResponseBody responseBody = response.body();
        String responseBodyString = response.body().string();

        // now we have extracted the response body but in the process
        // we have consumed the original reponse and can't read it again
        // so we need to build a new one to return from this method

        Response newResponse = response.newBuilder().body(ResponseBody.create(responseBody.contentType(), responseBodyString.getBytes())).build();

        long t2 = System.nanoTime();
        Timber.i("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers());
        Timber.v("RESPONSE BODY BEGIN:\n%s\nRESPONSE BODY END", responseBodyString);

        return newResponse;
    }

    private static String bodyToString(final Request request){
        String body = "";
        try {
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            body = buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }catch (final NullPointerException e) {
            return "did not work";
        }
        return body;
    }
}