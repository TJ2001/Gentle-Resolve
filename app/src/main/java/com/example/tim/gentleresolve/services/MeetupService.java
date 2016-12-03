package com.example.tim.gentleresolve.services;

import android.util.Log;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.models.Meetup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

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
                String organizer = resultsJSON.getJSONObject("organizer")
                        .getString("name");
                String city = resultsJSON.getString("city");
                String link = resultsJSON.getString("link");

                Meetup newMeetup = new Meetup(groupName, description, photoLink, organizer, city, link);
                meetups.add(newMeetup);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("meetup", "Meetup ArrayList: " + meetups);
        return meetups;
    }
}
