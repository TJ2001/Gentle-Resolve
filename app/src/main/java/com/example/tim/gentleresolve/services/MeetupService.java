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
                String city = resultsJSON.getString("city");
                Log.v("meetup", "Meetup city: " + city);
                String link = resultsJSON.getString("link");
                String description = resultsJSON.getString("description");
                Log.v("meetup", "Meetup description: " + description);
                String photoLink = resultsJSON.getJSONObject("group_photo").getString("photo_link");
                Log.v("meetup", "Meetup photoLink: " + photoLink);
                String organizer = resultsJSON.getJSONObject("organizer")
                        .getString("name");
                Log.v("meetup", "meetup organizer: " + organizer);
                String groupName = resultsJSON.getString("name");
                Log.v("meetup", "Meetup groupName: " + groupName);

                Meetup newMeetup = new Meetup(groupName, description, photoLink, organizer, city, link);
                meetups.add(newMeetup);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return meetups;
    }
}
