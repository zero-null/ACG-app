package me.lonelee.droidlove;

import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.notification.BmobNotificationManager;
import me.lonelee.droidlove.activity.ConversationActivity;

// 自定义消息接收器处理在线消息和离线消息
public class MyMessageHandler extends BmobIMMessageHandler {

    private Context context;

    public MyMessageHandler(Context context){
        this.context = context;
    }

    @Override
    public void onMessageReceive(final MessageEvent event) {
        BmobIM.getInstance().updateUserInfo(event.getFromUserInfo());
        BmobIMConversation conversation = event.getConversation();
        conversation.setConversationTitle(event.getFromUserInfo().getName());
        conversation.setConversationIcon(event.getFromUserInfo().getAvatar());
        BmobIM.getInstance().updateConversation(conversation);

        // 显示通知
        Intent pendingIntent = new Intent(context, ConversationActivity.class);
        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        BmobNotificationManager.getInstance(context).showNotification(event,pendingIntent);
        // 分发事件

        EventBus.getDefault().post(event);


    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //离线消息，每次connect的时候会查询离线消息，如果有，此方法会被调用
    }

}