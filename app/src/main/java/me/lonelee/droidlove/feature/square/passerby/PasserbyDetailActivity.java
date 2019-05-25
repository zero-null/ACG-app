package me.lonelee.droidlove.feature.square.passerby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.lonelee.droidlove.R;

public class PasserbyDetailActivity extends AppCompatActivity {

    private ImageView avatar;
    private TextView name;
    private TextView sex;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passerby_detail);

        Intent intent = getIntent();
        avatar = findViewById(R.id.passerby_detail_avatar);
        Glide.with(PasserbyDetailActivity.this).load(intent.getStringExtra("avatarUrl")).into(avatar);
        name = findViewById(R.id.passerby_detail_name);
        name.setText(intent.getStringExtra("name"));
        sex = findViewById(R.id.passerby_detail_sex);
        sex.setText(intent.getStringExtra("sex"));
        email = findViewById(R.id.passerby_detail_email);
        email.setText(intent.getStringExtra("email"));

    }
}
