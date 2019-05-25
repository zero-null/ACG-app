package me.lonelee.droidlove.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;

import cn.bmob.newim.event.MessageEvent;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.adapter.ItemConversationAdapter;

public class ConversationActivity extends AppCompatActivity {

    private TextView tvNoneConversation;
    private RecyclerView rvConversations;
    private ItemConversationAdapter itemConversationAdapter;
    private ActionBar actionBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        initView();

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("会话");


    }

    @Override
    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBar.setSubtitle(BmobIM.getInstance().getAllUnReadCount() + " 未读");

        List<BmobIMConversation> conversationList = BmobIM.getInstance().loadAllConversation();
        if (conversationList.isEmpty()){
            rvConversations.setVisibility(View.GONE);
            tvNoneConversation.setVisibility(View.VISIBLE);
        } else {
            tvNoneConversation.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rvConversations.setLayoutManager(linearLayoutManager);
            itemConversationAdapter = new ItemConversationAdapter(this,conversationList);
            rvConversations.setAdapter(itemConversationAdapter);
            rvConversations.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event){

    }

    void initView(){
        toolbar = findViewById(R.id.toolbar);
        tvNoneConversation =findViewById(R.id.tv_none_convasation);
        rvConversations = findViewById(R.id.rv_conversations);
    }
}
