package me.lonelee.droidlove.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;

import java.util.ArrayList;
import java.util.List;

import me.lonelee.droidlove.R;
import me.lonelee.droidlove.feature.MainActivity;
import me.lonelee.droidlove.feature.setting.SettingActivity;
import me.lonelee.droidlove.util.CommonUtil;

public class SplashActivity extends OnboarderActivity {

    private List<OnboarderPage> pages = new ArrayList<>(); // 启动页的页面集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 获取用户对“启动页是否开启”的设置
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isSplashSwitch = sharedPref.getBoolean(SettingActivity.KEY_PREF_SPLASH_SWITCH, true);

        // 根据启动页的开启状态进行相应逻辑处理
        if (isSplashSwitch) {

            CommonUtil.toFullscreen(SplashActivity.this);

            // 创建各页面
            OnboarderPage mainPage = new OnboarderPage(R.string.app_name, R.string.app_slogan, R.drawable.channel_plan_image);

            // 设置各页面背景色
            mainPage.setBackgroundColor(R.color.colorPrimaryDark);

            pages.add(mainPage);
            setOnboardPagesReady(pages);

            setDividerVisibility(View.GONE);
            setFinishButtonTitle("");
        } else {

            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();

        }

    }

    @Override
    public void onSkipButtonPressed() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onFinishButtonPressed() {

    }

}
