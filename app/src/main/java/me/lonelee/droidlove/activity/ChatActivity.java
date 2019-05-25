package me.lonelee.droidlove.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.exception.BmobException;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.adapter.ItemChatmessageAdapter;

public class ChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvChatMessage;
    private EditText etChatMessage;
    private Button btnChatSendMessage;

    private ItemChatmessageAdapter itemChatmessageAdapter;
    private BmobIMConversation mConversationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initView();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("聊天");
        }

        Intent intent = getIntent();
        BmobIMConversation conversationEntrance = (BmobIMConversation) intent.getBundleExtra("conversationEntranceBundle").getSerializable("conversationEntrance");

        // 获取聊天记录
        if (conversationEntrance != null) {
            mConversationManager = BmobIMConversation.obtain(BmobIMClient.getInstance(), conversationEntrance);
            mConversationManager.queryMessages(null, 20, new MessagesQueryListener() {
                @Override
                public void done(List<BmobIMMessage> list, BmobException e) {
                    if (e == null) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
                        rvChatMessage.setLayoutManager(linearLayoutManager);
                        itemChatmessageAdapter = new ItemChatmessageAdapter(ChatActivity.this,list);
                        rvChatMessage.setAdapter(itemChatmessageAdapter);
                    }
                }
            });
        }



        /*
         * 发送消息
         */
        btnChatSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etChatMessage.getText().toString();
                BmobIMTextMessage msg = new BmobIMTextMessage();
                msg.setContent(message);
                mConversationManager.sendMessage(msg, listener);
                etChatMessage.setText("");

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mConversationManager.updateLocalCache();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        rvChatMessage = findViewById(R.id.rv_chat_message);
        etChatMessage = findViewById(R.id.et_chat_message);
        btnChatSendMessage = findViewById(R.id.btn_chat_send_message);
    }

    /**
     * 消息发送监听器
     */
    public MessageSendListener listener = new MessageSendListener() {

        @Override
        public void onProgress(int value) {
            super.onProgress(value);
        }

        @Override
        public void onStart(BmobIMMessage msg) {
            super.onStart(msg);
        }

        @Override
        public void done(BmobIMMessage msg, BmobException e) {
            itemChatmessageAdapter.addMessage(msg);
            rvChatMessage.scrollToPosition(itemChatmessageAdapter.getMessageList().size() - 1);
        }
    };

    // 接收到在线消息后的逻辑处理
    @Subscribe
    public void onEventMainThread(MessageEvent event){
        itemChatmessageAdapter.addMessage(event.getMessage());
        rvChatMessage.scrollToPosition(itemChatmessageAdapter.getMessageList().size() - 1);
    }

}
