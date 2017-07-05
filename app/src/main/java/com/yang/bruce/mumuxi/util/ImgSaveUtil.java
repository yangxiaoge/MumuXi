package com.yang.bruce.mumuxi.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by allen on 2016/6/19.
 * <p>
 * Here reference is https://github.com/gaolonglong/GankGirl/blob/master/app/src/main/java/com/app/gaolonglong/gankgirl/util/ImageUtil.java
 */
public class ImgSaveUtil {
    private static final String catalogName = "Mumuxi";
    /**
     * 保存 图片
     *
     * @param context
     * @param url
     * @param bitmap
     * @param view
     * @param tag
     * @return
     */
    public static Uri saveImage(Context context, String url, Bitmap bitmap, View view, String tag) {
        //创建文件路径
        File appDir = new File(Environment.getExternalStorageDirectory(), catalogName);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //图片名称处理
        String[] fileNameArr = url.substring(url.lastIndexOf("/") + 1).split("\\.");
        String fileName = fileNameArr[0] + ".png";

        //创建文件
        File imageFile = new File(appDir, fileName);
        try {
            FileOutputStream out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            SnackbarUtils
                    .with(view)
                    .setMessage("妹纸已经躺在你的图库里啦~")
                    .setMessageColor(Color.BLUE)
                    .setBgColor(Color.TRANSPARENT)
                    .setDuration(SnackbarUtils.LENGTH_SHORT)
                    .show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri uri = Uri.fromFile(imageFile);
        //发送广播，通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        return uri;
    }

    /**
     * 分享图片
     *
     * @param context
     * @param resource
     */
    public static void shareImage(Context context, Bitmap resource) {
        Uri uri = createUri(resource, context);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(intent, "分享到..."));
    }

    private static Uri createUri(Bitmap bitmap, Context context) {
        File file = new File(Environment.getExternalStorageDirectory(), catalogName);
        if (!file.exists())
            file.mkdirs();
        File imageFile = new File(file.getPath(), "share.jpg");
        try {
            if (!imageFile.exists()) {
                imageFile.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            String filePath = "file:" + imageFile.getAbsolutePath();
            Uri uri = null;
            // Android 7.0开始，临时授权访问的路径通过FileProvider获取
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, "com.yang.bruce.mumuxi.provider", imageFile);
            } else {
                uri = Uri.fromFile(imageFile);
            }
            return uri;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
