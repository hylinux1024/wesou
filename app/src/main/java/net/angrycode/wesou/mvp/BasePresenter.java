package net.angrycode.wesou.mvp;

/*
 * Created by lancelot on 16/7/23.
 */
public interface BasePresenter<T extends BaseView> {

    void create();

    void destroy();
}
