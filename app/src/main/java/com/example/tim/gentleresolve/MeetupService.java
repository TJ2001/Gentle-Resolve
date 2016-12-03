package com.example.tim.gentleresolve;

import android.graphics.Movie;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;

/**
 * Created by Guest on 12/2/16.
 */
public class MeetupService {

    public static final String TAG = MeetupService.class.getSimpleName();

    public static void findSupport(String passion, String zip, String radius, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.KEY, Constants.MEETUP_API_KEY);
        urlBuilder.addQueryParameter(Constants.PARAMS, passion);
        urlBuilder.addQueryParameter(Constants.QUERY_PARAMS, zip);
        urlBuilder.addQueryParameter(Constants.RADIUS, radius);
        urlBuilder.addQueryParameter(Constants.RESULT_LIMIT, "20");
        String url = urlBuilder.build().toString();

        Log.v("MeetupService", "url: " + url);

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Meetup> processResults (String Data) {
        ArrayList<Meetup> meetups = new ArrayList<>();

        try {
            JSONObject meetupJSON = new JSONObject(Data);

            JSONArray results = meetupJSON.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject resultsJSON = results.getJSONObject(i);

                String groupName = resultsJSON.getString("name");
                String description = resultsJSON.getString("description");
                String photoLink = resultsJSON.getString("photo_link");

                String city = resultsJSON.getString("city");
                String state = resultsJSON.getString("state");
                String zip = resultsJSON.getString("zip")

                String Overview = resultsJSON.getString("overview");
                String releaseDate = resultsJSON.getString("release_date");
                String voteAverage = resultsJSON.getString("vote_average");
                String meetupId = resultsJSON.getString("id");
                ArrayList<String> genreId = new ArrayList<>();
                JSONArray genreList = resultsJSON.getJSONArray("genre_ids");

                for (int j = 0; j < genreList.length(); j++) {
                    genreId.add(genreList.getString(j));
                }
                Meetup newMeetup = new Meetup(Poster_path, Overview, releaseDate, genreId, Title, voteAverage, meetupId);
                meetups.add(newMeetup);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return meetups;
    }
}
