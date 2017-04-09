package net.angrycode.wesou.utils;

/**
 * Created by lancelot on 2017/4/6.
 */

public class MagnetUtils {
    public static String formatMagnet(String hash) {
        StringBuilder builder = new StringBuilder("magnet:?xt=urn:btih:");
        builder.append(hash);
        return builder.toString();
    }
}
