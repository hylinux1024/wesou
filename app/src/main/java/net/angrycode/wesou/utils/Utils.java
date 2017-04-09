package net.angrycode.wesou.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

import net.angrycode.wesou.R;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Created by lancelot on 2016/12/20.
 */

public class Utils {
    public static void copyText(Context context, String text) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        }
        ToastUtils.show(context, R.string.text_copied);
    }

    public static String getReadableLength(long length) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留两位小数
        nf.setMaximumFractionDigits(2);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);

        float kb = length / 1024;
        if (kb < 1024) {
            return nf.format(kb) + "KB";
        }
        float mb = kb / 1024;
        if (mb < 1024) {
            return nf.format(mb) + "MB";
        }
        float gb = mb / 1024;
        if (gb < 1024) {
            return nf.format(gb) + "GB";
        }
        float tb = gb / 1024;
        return nf.format(tb) + "T";

    }
}
