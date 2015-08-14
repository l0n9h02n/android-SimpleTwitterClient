package com.codepath.apps.simpletwitterclient.fragments;

import android.os.Bundle;

import com.codepath.apps.simpletwitterclient.TwitterApplication;
import com.codepath.apps.simpletwitterclient.TwitterClient;
import com.codepath.apps.simpletwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by jhkao on 8/14/15.
 */
public class HomeTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();
        populateTime();
    }

    public void populateTime() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                // Log.i("debug", json.toString());
                addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(int status, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });
    }
}
