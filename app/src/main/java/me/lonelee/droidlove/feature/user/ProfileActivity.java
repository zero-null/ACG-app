package me.lonelee.droidlove.feature.user;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.bean.User;
import me.lonelee.droidlove.util.CommonUtil;
import me.lonelee.droidlove.util.DataUtil;
import me.lonelee.droidlove.util.ToastUtil;
import me.lonelee.droidlove.util.UserUtil;

public class ProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_GET = 1; // 获取图片请求码

    private Toolbar mToolbar;

    private CircleImageView profileAvatar;
    private TextView profileName;
    private TextView profileSex;
    private TextView profileEmail;
    private TextView profileEmailStatu;

    private RelativeLayout modifyAvatar;
    private RelativeLayout modifyName;
    private RelativeLayout modifySex;

    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findView();

        if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(ProfileActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        } else {
        }

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("我的资料");
        }


        // 获取用户资料

        try {
            Glide.with(ProfileActivity.this).load(((JSONObject)BmobUser.getObjectByKey("avatar")).getString("url")).into(profileAvatar);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        profileName.setText(BmobUser.getObjectByKey("username").toString());
        profileSex.setText(BmobUser.getObjectByKey("sex").toString());
        profileEmail.setText(BmobUser.getObjectByKey("email").toString());
        profileEmailStatu.setText((Boolean)BmobUser.getObjectByKey("emailVerified")?"已验证":"未验证");


        // 修改头像
        modifyAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.selectImage(REQUEST_IMAGE_GET, ProfileActivity.this);
            }
        });

        // 修改昵称
        modifyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = getLayoutInflater().inflate(R.layout.dialog_profile_modifyname,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("修改昵称")
                        .setView(view)
                        .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String modifiedName = ((TextInputEditText)view.findViewById(R.id.modified_name)).getText().toString();
                                BmobUser newUser = new BmobUser();
                                newUser.setUsername(modifiedName);
                                BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
                                newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            profileName.setText(BmobUser.getObjectByKey("username").toString());
                                            ToastUtil.show(ProfileActivity.this, "修改昵称成功");
                                        }else{
                                            switch (e.getErrorCode()){
                                                case 202:
                                                    ToastUtil.show(ProfileActivity.this, "该昵称已被注册");
                                                    break;
                                                default:
                                                    ToastUtil.show(ProfileActivity.this, "修改昵称失败");
                                                    break;
                                            }

                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create().show();


            }
        });


        // 修改性别
        modifySex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("修改性别")
                        .setItems(R.array.sex_array, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String sex = getResources().getStringArray(R.array.sex_array)[which];
                                User newUser = new User();
                                newUser.setSex(sex);
                                final User bmobUser = BmobUser.getCurrentUser(User.class);
                                newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            profileSex.setText(BmobUser.getObjectByKey("sex").toString());
                                            ToastUtil.show(ProfileActivity.this,"性别修改成功");
                                        }else{
                                            ToastUtil.show(ProfileActivity.this,"性别修改失败");
                                        }
                                    }
                                });
                            }
                        }).create().show();
            }
        });

        // 注销功能
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserUtil.logout(ProfileActivity.this);
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {

            final Uri fullPhotoUri = data.getData(); // 获取内容 uri

            final String path = DataUtil.getPath(this, fullPhotoUri); // 根据 uri 获取 path


            File actualImageFile = new File(path); // 根据 path 构造文件

            File compressedImageFile = null;
            try {
                compressedImageFile = new Compressor(this).compressToFile(actualImageFile); //图片上传前先进行压缩
            } catch (IOException e) {
                e.printStackTrace();
            }
            final BmobFile bmobFile = new BmobFile(compressedImageFile);

            // 图片上传 -> 在后端更改用户头像数据 -> 重新获取用户头像数据 -> 视图更新
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        User newUser = new User();
                        newUser.setAvatar(bmobFile);
                        User bmobUser = User.getCurrentUser(User.class);
                        newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    try {
                                        Glide.with(ProfileActivity.this).load(((JSONObject)BmobUser.getObjectByKey("avatar")).getString("url")).into(profileAvatar);
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                    ToastUtil.show(ProfileActivity.this, "头像修改成功");
                                } else {
                                    ToastUtil.show(ProfileActivity.this, "头像修改失败");
                                }
                            }
                        });
                    } else {
                        ToastUtil.show(ProfileActivity.this, "图片上传失败");
                    }
                }

                @Override
                public void onProgress(Integer value) {

                }
            });

        }
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

    private void findView(){
        mToolbar = findViewById(R.id.toolbar);

        profileAvatar =  findViewById(R.id.profile_avatar);
        profileName =  findViewById(R.id.profile_name);
        profileSex = findViewById(R.id.profile_sex);
        profileEmail = findViewById(R.id.profile_email);
        profileEmailStatu = findViewById(R.id.profile_email_statu);

        modifyAvatar = findViewById(R.id.modify_avatar);
        modifyName = findViewById(R.id.modify_name);
        modifySex = findViewById(R.id.modify_sex);

        btn_logout = findViewById(R.id.logout);
    }
}
