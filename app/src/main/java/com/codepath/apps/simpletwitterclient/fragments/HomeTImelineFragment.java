package com.codepath.apps.simpletwitterclient.fragments;

import android.os.Bundle;

import com.codepath.apps.simpletwitterclient.TwitterApplication;
import com.codepath.apps.simpletwitterclient.TwitterClient;

/**
 * Created by jhkao on 8/14/15.
 */
public class HomeTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();
        getHomeTimeline(client);
    }

}
