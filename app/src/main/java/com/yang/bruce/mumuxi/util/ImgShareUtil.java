package com.yang.bruce.mumuxi.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.yang.bruce.mumuxi.R;

/**
 * Created by allen on 2016/6/21.
 */
public class ImgShareUtil {

    public static void shareImage(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");//图片
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_meizi_title)));
    }
}
