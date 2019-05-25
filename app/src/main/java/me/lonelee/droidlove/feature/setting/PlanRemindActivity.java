package me.lonelee.droidlove.feature.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import me.lonelee.droidlove.R;
import me.lonelee.droidlove.feature.setting.SettingActivity;

public class PlanRemindActivity extends AppCompatActivity {

    private TextView planRemindNameText;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_remind);

        findViews();

        Intent intent = getIntent();
        String planRemindName = intent.getExtras().getString("plan_remind_name");
        planRemindNameText.setText("是 [" + planRemindName + "] 的时间了呢");

        // 获取设置的提醒铃声的 URI
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String planRingtonUri = sharedPref.getString(SettingActivity.KEY_PREF_PLANRINGTON_CHOOSE,null);

        mediaPlayer = new MediaPlayer();

        if (!(planRingtonUri.equals("") || planRingtonUri == null)){
            // 播放铃声
            try {
                mediaPlayer.setDataSource(planRingtonUri);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

    }

    public void reminded(View v){
        mediaPlayer.stop();
        finish();
    }

    private void findViews(){
        planRemindNameText = (TextView)findViewById(R.id.plan_remind_name);
    }

}
