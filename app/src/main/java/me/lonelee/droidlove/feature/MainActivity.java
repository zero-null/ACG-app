package me.lonelee.droidlove.feature;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.update.BmobUpdateAgent;
import de.hdodenhof.circleimageview.CircleImageView;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.activity.ConversationActivity;
import me.lonelee.droidlove.feature.experience.EditorActivity;
import me.lonelee.droidlove.activity.InstructionActivity;
import me.lonelee.droidlove.feature.setting.SettingActivity;
import me.lonelee.droidlove.feature.square.SquareFragment;
import me.lonelee.droidlove.feature.user.LoginActivity;
import me.lonelee.droidlove.feature.user.ProfileActivity;
import me.lonelee.droidlove.feature.recourse.ResourceFragment;
import me.lonelee.droidlove.feature.setting.PlanFragment;
import me.lonelee.droidlove.feature.experience.ExperienceFragment;

import me.lonelee.droidlove.util.CommonUtil;
import me.lonelee.droidlove.util.UserUtil;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;
    private Toolbar mToolbar;

    private ImageButton btnSetting;
    private ImageView ivConversation;

    private CircleImageView ivMainAvatar;
    private TextView tvMainUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 更新检查
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        BmobUpdateAgent.update(this);

        setContentView(R.layout.activity_main);

        initView();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        1);

            }
        }

        /*
        * 导航项的点击逻辑
        */
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });

        ivConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserUtil.isLogin()){
                    startActivity(new Intent(MainActivity.this, ConversationActivity.class));
                } else {
                    UserUtil.needLogin(MainActivity.this);
                }

            }
        });

        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_plan:
                        if (UserUtil.isLogin()) {
                            toChannel(new PlanFragment(), "plan");
                        } else {
                            UserUtil.needLogin(MainActivity.this);
                        }
                        return true;

                    case R.id.nav_learn:
                        toChannel(new ResourceFragment(), "learn");
                        return true;

                    case R.id.nav_consult:
                        if (UserUtil.isLogin()) {
                            toChannel(new SquareFragment(), "consult");
                        } else {
                            UserUtil.needLogin(MainActivity.this);
                        }
                        break;

                    case R.id.nav_write:
                        if (UserUtil.isLogin()) {
                            toChannel(new ExperienceFragment(), "write");
                        } else {
                            UserUtil.needLogin(MainActivity.this);
                        }
                        return true;

                    case R.id.nav_feedback:
                        CommonUtil.composeEmail(MainActivity.this,"《卓恋》反馈");
                        return true;

                    case R.id.nav_instruction:
                        startActivity(new Intent(MainActivity.this, InstructionActivity.class));
                        return true;
                }
                return true;
            }
        });

        // 默认进入学习频道
        toChannel(new ResourceFragment(), "learn");
        mNavView.setCheckedItem(R.id.nav_learn);

        // toolbar 设置
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 用户登录与否的后续逻辑
        if (UserUtil.isLogin()) {
            String avatarUrl = null;
            try {
                avatarUrl = ((JSONObject) BmobUser.getObjectByKey("avatar")).getString("url");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Glide.with(this).load(avatarUrl).into(ivMainAvatar);
            ivMainAvatar.setBorderWidth(2);
            ivMainAvatar.setBorderColor(getResources().getColor(R.color.white));
            String usernameData = (String) BmobUser.getObjectByKey("username");
            tvMainUsername.setText(usernameData);
            ivMainAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                }
            });
            UserUtil.connectIm(MainActivity.this);
        } else {
            ivMainAvatar.setBorderWidth(0);
            ivMainAvatar.setImageResource(R.drawable.ic_unlogin);
            tvMainUsername.setText("点击头像登录");
            ivMainAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
        }
    }

    private void initView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavView =  findViewById(R.id.nav_view);
        btnSetting = mNavView.getHeaderView(0).findViewById(R.id.btn_setting);
        ivConversation = mNavView.getHeaderView(0).findViewById(R.id.iv_conversation);
        mToolbar =  findViewById(R.id.toolbar);
        ivMainAvatar = mNavView.getHeaderView(0).findViewById(R.id.iv_main_avatar);
        tvMainUsername =  mNavView.getHeaderView(0).findViewById(R.id.tv_main_username);
    }

    private void toChannel(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout, fragment, tag)
                .commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    //默认加载学习频道的菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.learn, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                }
                return;
            }

        }
    }

    // 各频道菜单的点击逻辑
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            // 学习频道
            case R.id.search:
                CommonUtil.searchWeb(MainActivity.this,"ACG");
                return true;
            case R.id.recommend:
                CommonUtil.composeEmail(MainActivity.this,"[幻想次元] 推荐资源");
                return true;

            // 创作频道
            case R.id.new_article:
                startActivity(new Intent(MainActivity.this, EditorActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
