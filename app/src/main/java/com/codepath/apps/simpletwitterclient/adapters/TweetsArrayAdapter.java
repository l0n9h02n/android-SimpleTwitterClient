package com.codepath.apps.simpletwitterclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitterclient.R;
import com.codepath.apps.simpletwitterclient.helpers.ParseRelativeDate;
import com.codepath.apps.simpletwitterclient.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jhkao on 8/10/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Get the tweet
        Tweet tweet = getItem(position);

        // 2. Find or inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweets, parent, false);
        }

        // 3. Find the subviews to fill with data in the template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvUserScreenname = (TextView) convertView.findViewById(R.id.tvUserScreenName);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);

        // 4. Populate data into subviews
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        tvUsername.setText(tweet.getUser().getScreenName());
        tvUserScreenname.setText("@" + tweet.getUser().getName());
        tvTime.setText(ParseRelativeDate.getRelativeTimeAgo(tweet.getCreatedAt()));
        tvBody.setText(tweet.getBody());

        // 5. Return the view to be inserted into the list
        return convertView;
    }
}
