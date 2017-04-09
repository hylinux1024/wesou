package net.angrycode.wesou.mvp;

import android.app.Activity;

import rx.subscriptions.CompositeSubscription;

/*
 * Created by lancelot on 16/7/23.
 */
public abstract class Presenter<T extends BaseView> {
    T mView;

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();
    public Presenter(T view) {
        this.mView = view;
    }

    public Activity getActivity() {
        return mView.getActivity();
    }

    public void resume(){
    }

    public void pause() {

    }
    public void destroy(){
        compositeSubscription.unsubscribe();
        mView = null;
    }
}
