package com.codepath.apps.simpletwitterclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitterclient.R;
import com.codepath.apps.simpletwitterclient.activities.ProfileActivity;
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
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweets, parent, false);
            holder = new ViewHolder();
            holder.profileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            holder.userName = (TextView) convertView.findViewById(R.id.tvUserName);
            holder.screenName = (TextView) convertView.findViewById(R.id.tvUserScreenName);
            holder.createTime = (TextView) convertView.findViewById(R.id.tvTime);
            holder.textBody = (TextView) convertView.findViewById(R.id.tvBody);
            convertView.setTag(holder);
        }

        // 1. Get the tweet
        final Tweet tweet = getItem(position);

        // 3. Find the subviews to fill with data in the template
        /*
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvUserScreenname = (TextView) convertView.findViewById(R.id.tvUserScreenName);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        */

        // 4. Populate data into subviews
        // holder.profileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(holder.profileImage);

        holder.userName.setText(tweet.getUser().getScreenName());
        holder.screenName.setText("@" + tweet.getUser().getName());
        holder.createTime.setText(ParseRelativeDate.getRelativeTimeAgo(tweet.getCreatedAt()));
        holder.textBody.setText(tweet.getBody());

        // 5. Setup intent
        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("user_id", tweet.getUser().getUid());
                i.putExtra("screen_name", tweet.getUser().getScreenName());
                getContext().startActivity(i);
            }
        });

        // 6. Return the view to be inserted into the list
        return convertView;
    }

    private static final class ViewHolder
    {
        private ImageView profileImage;
        private TextView userName;
        private TextView screenName;
        private TextView createTime;
        private TextView textBody;
    }
}
