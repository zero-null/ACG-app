package me.lonelee.droidlove.feature.experience;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

import me.lonelee.droidlove.R;
import me.lonelee.droidlove.bean.Post;
import me.lonelee.droidlove.util.DataUtil;

public class ExperienceItemAdapter extends RecyclerView.Adapter<ExperienceItemAdapter.ViewHolder> {

    public static List<Post> getPosts() {
        return posts;
    }

    public static void setPosts(List<Post> posts) {
        posts = posts;
    }

    private Activity activity;
    private static List<Post> posts;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View post;
        TextView postTitle;

        public ViewHolder(View view){
            super(view);
            post = view;
            postTitle = (TextView)view.findViewById(R.id.post_title);
        }
    }

    public ExperienceItemAdapter(Activity activity, List<Post> posts){
        this.activity = activity;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_experience, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Post post = posts.get(position);

        holder.post.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(activity,holder.post);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit_post:
                                Intent intent = new Intent(activity,EditorActivity.class);
                                intent.putExtra("id",post.getObjectId());
                                intent.putExtra("title",post.getTitle());
                                intent.putExtra("content",post.getContent());
                                activity.startActivity(intent);
                                return true;
                            case R.id.delete_post:
                                DataUtil.deletePost(post.getObjectId(),activity);
                                return true;
                            default:
                                return true;
                        }
                    }
                });
                popupMenu.inflate(R.menu.item_post);
                popupMenu.show();
                return true;
            }
        });

        holder.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ExperienceDetailActivity.class);
                intent.putExtra("data",post.getContent());
                intent.putExtra("title",post.getTitle());
                activity.startActivity(intent);
            }
        });
        holder.postTitle.setText(post.getTitle());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
