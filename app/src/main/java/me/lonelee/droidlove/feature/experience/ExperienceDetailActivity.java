package me.lonelee.droidlove.feature.experience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import me.lonelee.droidlove.R;

public class ExperienceDetailActivity extends AppCompatActivity {

    private TextView experienceDetailTitle;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_detail);

        findViews();

        Intent intent = getIntent();

        experienceDetailTitle.setText(intent.getStringExtra("title"));

        String data = intent.getExtras().getString("data");
        webview.getSettings().setDefaultTextEncodingName("utf-8");
        webview.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
    }

    private void findViews() {
        experienceDetailTitle = findViewById(R.id.experience_detail_title);
        webview = findViewById(R.id.webview);
    }


}
