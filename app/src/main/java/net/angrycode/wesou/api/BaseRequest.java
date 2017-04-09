package net.angrycode.wesou.api;

import android.util.Pair;

import java.util.Map;


/**
 * Created by lancelot on 2017/4/4.
 */

public abstract class BaseRequest<T> extends BaseAPI {

    Map<String, String> mParams;

    public BaseRequest(Map<String, String> params) {
        this.mParams = params;
    }

    public T get() {
        Pair<Integer, String> response = get(mParams);
        T t = onParse(response.first, response.second);
        return t;
    }

    public T post() {
        Pair<Integer, String> response = post(mParams);
        T t = onParse(response.first, response.second);
        return t;
    }

    public abstract T onParse(int code, String response);
}
