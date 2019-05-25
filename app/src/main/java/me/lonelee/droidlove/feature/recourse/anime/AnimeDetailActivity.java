package me.lonelee.droidlove.feature.recourse.anime;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.bean.Anime;
import me.lonelee.droidlove.util.CommonUtil;

public class AnimeDetailActivity extends AppCompatActivity {

    private ImageView cover;
    private TextView description;
    private Button view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_detail);

        Toolbar toolbar = findViewById(R.id.anime_detail_toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();

        cover = findViewById(R.id.anime_detail_cover);
        Glide.with(AnimeDetailActivity.this).load(intent.getStringExtra("coverUrl")).into(cover);
        setTitle(intent.getStringExtra("name"));

        // 设置标签
        List<String> tagList = Arrays.asList(intent.getStringExtra("label").split(" "));
        TagContainerLayout mTagContainerLayout = findViewById(R.id.anime_detail_tag);
        mTagContainerLayout.setTags(tagList);

        description = findViewById(R.id.anime_detail_description);
        description.setText(intent.getStringExtra("description"));
        view = findViewById(R.id.anime_view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AnimeDetailActivity.this);
                builder.setTitle("选择播放源")
                        .setItems(R.array.anime_view_source, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               String[] url = intent.getStringExtra("url").split(",");
                                CommonUtil.openWebPage(AnimeDetailActivity.this, url[which]);
                            }
                        });
                builder.create().show();
            }
        });
    }
}
