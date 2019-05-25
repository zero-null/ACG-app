package me.lonelee.droidlove.feature.setting;

import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.os.Bundle;

import me.lonelee.droidlove.feature.setting.SettingFragment;

public class SettingActivity extends PreferenceActivity
                            implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String KEY_PREF_SPLASH_SWITCH = "pref_splash_switch";

    static final String KEY_PREF_PLANRINGTON_CHOOSE = "pref_planrington_choose";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }
}
