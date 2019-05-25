package me.lonelee.droidlove.feature.experience;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.lonelee.droidlove.feature.experience.ExperienceDetailActivity;
import me.lonelee.droidlove.bean.Post;

public class SearchedPostAdapater extends BaseAdapter {

    private Activity activity;
    private List<Post> posts = new ArrayList<>();

    public SearchedPostAdapater(Activity activity, List<Post> posts){
        this.activity = activity;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView title = new TextView(activity);
        title.setText(posts.get(position).getTitle().toString());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ExperienceDetailActivity.class);
                intent.putExtra("data",posts.get(position).getContent());
                intent.putExtra("title",posts.get(position).getTitle());
                activity.startActivity(intent);
            }
        });
        return title;
    }
}
