package com.yang.bruce.mumuxi.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by allen on 2016/6/19.
 * <p>
 * Here reference is https://github.com/gaolonglong/GankGirl/blob/master/app/src/main/java/com/app/gaolonglong/gankgirl/util/ImageUtil.java
 */
public class ImgSaveUtil {

    /**
     * 保存 图片
     * @param context
     * @param url
     * @param bitmap
     * @param imageView
     * @param tag
     * @return
     */
    public static Uri saveImage(Context context, String url, Bitmap bitmap, View imageView, String tag) {
        //创建文件路径
        File appDir = new File(Environment.getExternalStorageDirectory(), "Mumuxi相册");
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
            Snackbar.make(imageView, "妹纸已经躺在你的图库里啦.. ( ＞ω＜)", Snackbar.LENGTH_SHORT).show();
            out.flush();
            out.close();
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
     * @param context
     * @param resource
     */
    public static void shareImage(Context context, Bitmap resource){
        Uri uri = createUri(resource);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intent, "分享到..."));
    }

    private static Uri createUri(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(), "Mumuxi相册");
        if (!file.exists())
            file.mkdirs();
        File image = new File(file.getPath(), "share.jpg");
        try {
            if (!image.exists()) {
                image.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            String filePath = "file:" + image.getAbsolutePath();
            return Uri.parse(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
