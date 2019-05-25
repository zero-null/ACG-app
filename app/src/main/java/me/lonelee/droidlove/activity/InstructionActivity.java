package me.lonelee.droidlove.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.lonelee.droidlove.R;
import me.lonelee.droidlove.util.CommonUtil;

public class InstructionActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ImageView developerAvatar;
    private WebView wvDeveloperInstruction;
    private FloatingActionButton developerBlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        initView();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Zero");
        }

        Glide.with(this).load("https://bmob-cdn-18420.b0.upaiyun.com/2018/05/07/287aa17040494f718ec96e8cfa9418f9.jpg").into(developerAvatar);

        String data =
                "<h2>幻想次元</h2>" +
                        "<p>这是一款 ACG 社区应用，出于学习目的开发。</p>" +
                        "<h2>开发者的掩饰</h2>" +
                        "<p>因开发者水平低下，若：</p>" +
                        "<ul>" +
                        "<li>bug 频出</li>" +
                        "<li>功能残缺</li>" +
                        "<li>UI 丑陋</li>" +
                        "<li>性能堪忧</li>" +
                        "<li>......</li>" +
                        "</ul>" +
                        "<p>恳请谅解，还望反馈。</p>" +
                        "<p>此外该应用每月达一定流量后，可能将失灵至下月，别问我什么原因，因为穷(T_T)</p>" +
                        "<h2>跪求小伙伴</h2>" +
                        "<ul>" +
                        "<li>设计小姐姐</li>" +
                        "<li>Android 老哥</li>" +
                        "<li>后端爸爸</li>" +
                        "<li>彼此拥抱的咸鱼</li>" +
                        "<li>......</li>" +
                        "</ul>";
        wvDeveloperInstruction.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
        developerBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.openWebPage(InstructionActivity.this, "https://zero-null.github.io/");
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        developerAvatar = findViewById(R.id.developer_avatar);
        wvDeveloperInstruction = findViewById(R.id.wv_developer_instruction);
        developerBlog = findViewById(R.id.developer_blog);
    }
}
