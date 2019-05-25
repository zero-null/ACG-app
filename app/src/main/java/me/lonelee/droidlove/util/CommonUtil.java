package me.lonelee.droidlove.util;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.View;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import me.lonelee.droidlove.R;

public class CommonUtil {

    /**
     * 转换时间戳为字符串
     * @param time 时间戳
     * @return 格式化时间字符串
     */
    public static String toString(Long time){
       return DateFormat.getDateTimeInstance().format(new Date(time));
    }

    /**
     * 生成一种随机颜色
     * @param context context
     * @return 颜色整型值
     */
    public static int generateColor(Context context) {
        int[] colors = context.getResources().getIntArray(R.array.random_background_color);
        return colors[new Random().nextInt(colors.length)];
    }

    /**
     * 启用全屏
     * @param activity 需启用全屏的活动
     */
    public static void toFullscreen(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    /**
     * 获取本机应用的图片
     * @param REQUEST_IMAGE_GET 请求码
     * @param activity activity
     */
    public static void selectImage(int REQUEST_IMAGE_GET, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    /**
     * 显示开发中对话框
     * @param context context
     */
    public static void showDevelopingDialog(Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("抱歉(>_<)")
                .setMessage("该功能开发中")
                .setPositiveButton("一顶原谅帽", null)
                .create().show();
    }

    /**
     * 打开相应 url 的网页
     * @param context context
     * @param url  网页的 url
     */
    public static void openWebPage(Context context, String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * 调用应用搜索关键词
     * @param context context
     * @param query 关键词
     */
    public static void searchWeb(Context context, String query){
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * 调用邮件应用发送邮件
     * @param context context
     * @param subject 邮件标题
     */
    public static void composeEmail(Context context, String subject) {
        String[] address = {"zero-lee@outlook.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,address);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
