package com.jeremelau.update;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

/**
 * Author: Created by jereme on 2018/9/12
 * E-main: liuqx@guoguang.com.cn
 */
public class UpdateUtil {
    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 2 * 获取版本号 3 * @return 当前应用的版本号 4
     */
    public static int getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void getUpdateDetail(Context context, String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, response -> {
            UpdateDetail updateDetail = new GsonBuilder().create().fromJson(response.toString(), UpdateDetail.class);
            int newVersionCode = updateDetail.getLatestVersionCode();
            int versionCode = UpdateUtil.getVersion(context);
            if (newVersionCode > versionCode) {
                ShowDialog(context, updateDetail.getReleaseNotes().get(0), updateDetail.getUrl());
            }
        }, error -> showError(context, error.toString()));
        RequestQueue mQueue = Volley.newRequestQueue(context.getApplicationContext());
        mQueue.add(jsonObjectRequest);
    }

    private static void showError(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private static void goUpdate(Context context, String url) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));//Url 就是你要打开的网址
        intent.setAction(Intent.ACTION_VIEW);
        context.startActivity(intent); //启动浏览器
    }

    /**
     * 升级系统
     *
     * @param content
     * @param url
     */
    private static void ShowDialog(Context context, String content, final String url) {
        new android.app.AlertDialog.Builder(context)
                .setTitle("版本更新")
                .setMessage(content)
                .setPositiveButton("更新", (dialog, which) -> {
                    dialog.dismiss();
                    goUpdate(context, url);
                })
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .show();
    }
}