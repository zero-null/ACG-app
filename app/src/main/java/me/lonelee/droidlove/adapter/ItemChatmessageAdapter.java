package me.lonelee.droidlove.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMMessage;
import de.hdodenhof.circleimageview.CircleImageView;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.util.CommonUtil;

public class ItemChatmessageAdapter extends RecyclerView.Adapter<ItemChatmessageAdapter.ViewHolder> {

    private Context context;

    private List<BmobIMMessage> messageList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tvChatTime;
         CircleImageView ivChatAvatar;
         TextView tvChatUsername;
         TextView tvChatContent;

        public ViewHolder(View v) {
            super(v);
            view = v;
            tvChatTime = view.findViewById(R.id.tv_chat_time);
            ivChatAvatar = view.findViewById(R.id.iv_chat_avatar);
            tvChatUsername = view.findViewById(R.id.tv_chat_username);
            tvChatContent = view.findViewById(R.id.tv_chat_content);
        }
    }

    public ItemChatmessageAdapter(Context context, List<BmobIMMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ItemChatmessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_message, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final BmobIMMessage curMessage = messageList.get(position);
        holder.tvChatTime.setText(CommonUtil.toString(curMessage.getUpdateTime()));

        if (curMessage.getFromId().equals(BmobIM.getInstance().getCurrentUid())){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                holder.itemView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }
        Glide.with(context).load(curMessage.getBmobIMUserInfo().getAvatar()).into(holder.ivChatAvatar);
        holder.tvChatUsername.setText(curMessage.getBmobIMUserInfo().getName());
        holder.tvChatContent.setText(curMessage.getContent());

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public List<BmobIMMessage> getMessageList(){
        return messageList;
    }

    public void addMessage(BmobIMMessage message) {
        messageList.add(message);
        notifyDataSetChanged();
    }
}