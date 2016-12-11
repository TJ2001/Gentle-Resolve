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

public class MeetupServices {

    public static final String TAG = MeetupServices.class.getSimpleName();

    public static void findSupport(String zip, String interest, String radius, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.KEY, Constants.MEETUP_API_KEY);
        urlBuilder.addQueryParameter(Constants.PARAMS, "public");
        urlBuilder.addQueryParameter(Constants.QUERY_PARAMS, zip);
        urlBuilder.addQueryParameter(Constants.QUERY_TEXT, interest);
        urlBuilder.addQueryParameter(Constants.RADIUS, radius);
        urlBuilder.addQueryParameter(Constants.RESULT_LIMIT, "20");
        String url = urlBuilder.build().toString();

        Log.v("MeetupServices", "url: " + url);

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }


    public ArrayList<Meetup> processResults (String Data) {
        ArrayList<Meetup> meetups = new ArrayList<>();

        try {
            JSONArray results = new JSONArray(Data);

            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonObject = results.getJSONObject(i);

                String groupName = jsonObject.getString("name");
                Log.v("meetup", "Meetup groupName: " + groupName);
                String link = jsonObject.getString("link");
                String description = jsonObject.getString("description");
                Log.v("meetup", "Meetup description: " + description);
                String city = jsonObject.getString("city");
                Log.v("meetup", "Meetup city: " + city);
                String organizer = jsonObject.getJSONObject("organizer")
                        .getString("name");
                Log.v("meetup", "meetup organizer: " + organizer);
                String photoLink = jsonObject.getJSONObject("organizer").getJSONObject("photo").getString("photo_link");
                Log.v("meetup", "Meetup photoLink: " + photoLink);

                Meetup newMeetup = new Meetup(groupName, description, photoLink, organizer, city, link);
                meetups.add(newMeetup);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return meetups;
    }
}
