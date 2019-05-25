package me.lonelee.droidlove.feature.square.passerby;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.activity.ChatActivity;
import me.lonelee.droidlove.bean.User;

public class PasserbyItemAdapter extends RecyclerView.Adapter<PasserbyItemAdapter.ViewHolder> {

    private Activity activity;

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> users) {
        PasserbyItemAdapter.users = users;
    }

    private static List<User> users;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View user;
        ImageView avatar;
        TextView name;
        Button chat;

        public ViewHolder(View view){
            super(view);
            user = view;
            avatar = view.findViewById(R.id.square_avatar);
            name = view.findViewById(R.id.square_name);
            chat = view.findViewById(R.id.square_chat);
        }
    }

    public PasserbyItemAdapter(Activity activity, List<User> users){
        this.activity = activity;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_passerby, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = users.get(position);
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar().getUrl()), null);
                Intent intent = new Intent(activity,ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("conversationEntrance", conversationEntrance);
                intent.putExtra("conversationEntranceBundle",bundle);
                activity.startActivity(intent);
            }
        });
        Glide.with(activity).load(user.getAvatar().getFileUrl()).into(holder.avatar);
        holder.name.setText(user.getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,PasserbyDetailActivity.class);
                intent.putExtra("avatarUrl", user.getAvatar().getFileUrl());
                intent.putExtra("name", user.getUsername());
                intent.putExtra("sex", user.getSex());
                intent.putExtra("email", user.getEmail());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
