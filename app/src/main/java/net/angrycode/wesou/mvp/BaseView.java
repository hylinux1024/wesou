package net.angrycode.wesou.mvp;

import android.app.Activity;

/**
 * Created by lancelot on 16/7/23.
 */
public interface BaseView {
    Activity getActivity();

    void onStartLoading();

    void onFinished();

    void onError(int code, String message);

}
