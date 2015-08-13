package com.codepath.apps.simpletwitterclient.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitterclient.R;
import com.codepath.apps.simpletwitterclient.TwitterApplication;
import com.codepath.apps.simpletwitterclient.TwitterClient;
import com.codepath.apps.simpletwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends ActionBarActivity {

    private TwitterClient client;
    private ImageView ivMyProfileImage;
    private TextView tvMyName;
    private TextView tvMyScreenName;
    private EditText etTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        ivMyProfileImage = (ImageView) findViewById(R.id.ivMyProfileImage);
        tvMyName = (TextView) findViewById(R.id.tvMyName);
        tvMyScreenName = (TextView) findViewById(R.id.tvMyScreenName);
        etTweet = (EditText) findViewById(R.id.etTweet);

        client = TwitterApplication.getRestClient();

        getMyInfo();
    }

    private void getMyInfo() {
        client.getVerifyCredentials(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                // Log.i("debug", json.toString());
                User user = User.fromJson(json);

                ivMyProfileImage.setImageResource(android.R.color.transparent);
                Picasso.with(getApplicationContext()).load(user.getProfileImageUrl()).into(ivMyProfileImage);
                tvMyScreenName.setText("@" + user.getScreenName());
                tvMyName.setText(user.getName());
            }

            @Override
            public void onFailure(int status, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tweet) {
            onTweetClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onTweetClick() {
        String content = etTweet.getText().toString();
        client.updateStatuses(content, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                setResult(Activity.RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(int status, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i("WARN", throwable.toString());
            }
        });
    }
}
