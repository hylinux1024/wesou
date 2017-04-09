package net.angrycode.wesou.utils;

/**
 * Created by lancelot on 16/6/22.
 */
public class IntegerUtils {

    public static int parse(String val) {
        int retVal = 0;
        try {
            retVal = Integer.parseInt(val);
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return retVal;
    }

    public static long parseLong(String val) {
        long retVal = 0;
        try {
            retVal = Long.parseLong(val);
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return retVal;
    }

    public static int parseIntFormHex(String hex) {
        int i = 0;
        try {
            i = Integer.parseInt(hex, 16);
        } catch (NumberFormatException e) {
            LogUtils.e(e);
        }
        return i;
    }

    /**
     * 2字节16进制转成int
     *
     * @param data
     * @return
     */
    public static int byte2Int(byte[] data) {
        if (data == null || data.length < 2) {
            return 0;
        }
        int i = data[0]* 256 + data[1];
        return i;
    }

    //    public byte[] int2byte(int data) {
//
//    }
    public static byte[] checkData(byte[] data) {
        int len = data[2] * 256 + data[3];
        int sum = 0;
        for (int i = 0; i < len - 2; ++i) {
            sum += data[i] & 0xff;
        }
        byte h = (byte) (sum / 256);
        byte l = (byte) (sum % 256);

        data[len - 2] = h;
        data[len - 1] = l;
        return data;
    }

    public static boolean isDataCorrect(byte[] data) {
        int len = data[2]* 256 + data[3];
        if (len < 2) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < len - 2; ++i) {
            sum += data[i] & 0xff;
        }
        byte h = (byte) (sum / 256);
        byte l = (byte) (sum % 256);

        return (data[len - 2] == h && data[len - 1] == l);
    }
}
