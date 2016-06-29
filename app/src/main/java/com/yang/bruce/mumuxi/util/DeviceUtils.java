package com.yang.bruce.mumuxi.util;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;

import com.yang.bruce.mumuxi.R;

/**
 * Author: 0027008122 [yang.jianan@zte.com.cn]
 * Time: 2016-06-29 13:45
 * Version: 1.0
 * TaskId:
 * Description:
 */
public class DeviceUtils {

    /**
     * 复制到剪贴板
     *
     * @param context context
     * @param content content
     */
    public static void copy2Clipboard(Context context, String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.content.ClipboardManager clipboardManager
                    = (android.content.ClipboardManager) context.getSystemService(
                    Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(context.getString(R.string.app_name),
                    content);
            clipboardManager.setPrimaryClip(clipData);
        } else {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(content);
        }
    }
}
