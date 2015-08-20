package com.codepath.apps.simpletwitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitterclient.R;
import com.codepath.apps.simpletwitterclient.TwitterApplication;
import com.codepath.apps.simpletwitterclient.TwitterClient;
import com.codepath.apps.simpletwitterclient.fragments.UserTimelineFragment;
import com.codepath.apps.simpletwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends ActionBarActivity {
    TwitterClient client;
    User user;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent i = getIntent();
        userId = i.getLongExtra("user_id", 0);

        client = TwitterApplication.getRestClient();
        // Get user info
        if (userId > 0) {
            client.showUsers(userId, new UserProfileHttpResponseHandler());
        } else {
            client.getVerifyCredentials(new UserProfileHttpResponseHandler());
        }

        // Get the screen name from the activity that launch this
        String screenName = getIntent().getStringExtra("screen_name");
        if (null == savedInstanceState) {
            // Create the user timeline fragment
            UserTimelineFragment fragment = UserTimelineFragment.newInstance(screenName);
            // Display user timeline fragment within this activity (dynamically)
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragment);
            ft.commit();
        }
    }

    private class UserProfileHttpResponseHandler extends JsonHttpResponseHandler
    {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response)
        {
            // Response is automatically parsed into a JSONObject
            user = User.fromJson(response);
            getSupportActionBar().setTitle("@" + user.getScreenName());
            populateProfileHeader(user);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response)
        {
            super.onFailure(statusCode, headers, throwable, response);
        }
    }

    private void populateProfileHeader(User user) {
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        TextView tvProfileName = (TextView) findViewById(R.id.tvProfileName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
        tvProfileName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        String strFollowers = getApplicationContext().getString(R.string.followers);
        // tvFollowers.setText(String.valueOf(user.getFollowersCount()));
        tvFollowers.setText(String.format(strFollowers, user.getFollowersCount()));
        String strFollowing = getApplicationContext().getString(R.string.following);
        // tvFollowing.setText(String.valueOf(user.getFriendsCount()));
        tvFollowing.setText(String.format(strFollowing, user.getFriendsCount()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
