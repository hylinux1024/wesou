package net.angrycode.wesou.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by lancelot on 2016/11/14.
 */

public class GlideUtils {
    private GlideUtils() {

    }

    public static void loadImage(Context context, int placeholderRes, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(placeholderRes).crossFade().into(imageView);
    }
}
