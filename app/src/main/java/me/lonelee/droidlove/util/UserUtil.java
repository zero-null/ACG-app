package me.lonelee.droidlove.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import me.lonelee.droidlove.feature.user.LoginActivity;
import me.lonelee.droidlove.bean.User;

/**
 * Created by lone on 2017/12/10.
 */

public class UserUtil {

    /**
     * 用户是否已登录
     * @return boolean
     */
    public static boolean isLogin(){
        User user = BmobUser.getCurrentUser(User.class);
        return user != null;
    }
    public static void needLogin(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("该功能需登录")
                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    /**
     * 通过用户名登录
     * @param username 用户名
     * @param password 密码
     * @param activity toast 显示的 context
     */
    public static void loginByUsername(String username, String password, final Activity activity){
        User.loginByAccount(username, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(user != null){
                    ToastUtil.show(activity, "登录成功");
                    activity.finish();
                } else {
                    switch (e.getErrorCode()){
                        case 101:
                            ToastUtil.show(activity, "用户名或密码不正确");
                            break;
                        case 9018:
                            ToastUtil.show(activity, "未填写完整");
                            break;
                        case 9019:
                            ToastUtil.show(activity, "格式错误");
                            break;
                    }
                }
            }
        });
    }

    /**
     * 通过邮箱注册
     * @param email 邮箱地址
     * @param password  密码
     * @param activity  toast 显示的 context
     */
    public static void registerByEmail(String email, String password, final Activity activity){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(email);
        user.setSex("秀吉");
        user.setEmailVerified(false);
        user.setAvatar(new BmobFile("default",null,"https://bmob-cdn-18420.b0.upaiyun.com/2018/04/26/899e1d7a40111970808e467bb5f0b712.jpg"));
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User s, BmobException e) {
                if(e == null){
                    ToastUtil.show(activity,"注册成功");
                }else{
                    switch (e.getErrorCode()){
                        case 301:
                            ToastUtil.show(activity, "邮箱格式错误");
                            break;
                        case 304:
                            ToastUtil.show(activity, "未填写完整");
                            break;
                    }
                    Log.d("调试", e.toString());
                }
            }
        });
    }


    /**
     * 注销登录
     * @param context toast 显示的 context
     */
    public static void logout(Context context){
        User.logOut();
        disconnectIm();
        ToastUtil.show(context, "注销成功");
    }

    /**
     * 连接 IM 服务器并更新本地 IM 用户数据库
     * @param context context
     */
    public static void connectIm(final Context context){
        final User user = BmobUser.getCurrentUser(User.class);
        if (!TextUtils.isEmpty(user.getObjectId())) {
            BmobIM.connect(user.getObjectId(), new ConnectListener() {
                @Override
                public void done(String uid, BmobException e) {
                    if (e == null) {
                        BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(uid, user.getUsername() , user.getAvatar().getUrl()));
                    } else {
                        ToastUtil.show(context, e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * 注销 IM 服务器
     */
    private static void disconnectIm(){
        BmobIM.getInstance().disConnect();
    }

}
