package me.lonelee.droidlove.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import de.hdodenhof.circleimageview.CircleImageView;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.activity.ChatActivity;
import me.lonelee.droidlove.util.CommonUtil;

public class ItemConversationAdapter extends RecyclerView.Adapter<ItemConversationAdapter.ViewHolder> {

    private Context context;
    private List<BmobIMConversation> conversationList;

    public ItemConversationAdapter(Context context, List<BmobIMConversation> conversationList) {
        this.context = context;
        this.conversationList = conversationList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public CircleImageView ivConvasationAvatar;
        public TextView tvConversationUsername;
        public TextView tvConversationNewestMessage;
        public TextView tvConversationUpdatetime;
        public TextView tvConversationUnreadcount;

        public ViewHolder(View v) {
            super(v);

            view = v;
            ivConvasationAvatar = view.findViewById(R.id.iv_conversation_avatar);
            tvConversationUsername = view.findViewById(R.id.tv_convasation_username);
            tvConversationNewestMessage = view.findViewById(R.id.tv_convasation_newest_message);
            tvConversationUpdatetime = view.findViewById(R.id.tv_convasation_updatetime);
            tvConversationUnreadcount = view.findViewById(R.id.tv_convasation_unreadcount);
        }
    }


    @NonNull
    @Override
    public ItemConversationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conversation, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final BmobIMConversation curConversation = conversationList.get(position);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("conversationEntrance", curConversation);
                intent.putExtra("conversationEntranceBundle",bundle);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(curConversation.getConversationIcon()).into(holder.ivConvasationAvatar);
        holder.tvConversationUsername.setText(curConversation.getConversationTitle());
        holder.tvConversationNewestMessage.setText(curConversation.getMessages().get(curConversation.getMessages().size()-1).getContent());
        holder.tvConversationUpdatetime.setText(CommonUtil.toString(curConversation.getUpdateTime()));
        holder.tvConversationUnreadcount.setText(String.valueOf(BmobIM.getInstance().getUnReadCount(curConversation.getConversationId())));
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    public List<BmobIMConversation> getConversationList() {
        return conversationList;
    }

    public void insertConversation(BmobIMConversation conversation) {
        conversationList.add(conversation);
        notifyDataSetChanged();
    }
}