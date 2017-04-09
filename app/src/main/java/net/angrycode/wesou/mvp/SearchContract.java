package net.angrycode.wesou.mvp;

import net.angrycode.wesou.bean.TorrentProject;

import java.util.List;

import rx.Observable;

/**
 * Created by lancelot on 2017/4/4.
 */

public interface SearchContract {

    interface View extends BaseView {
        void onGetTorrentProjectListFinish(List<TorrentProject> projectList);
    }

    interface Presenter extends BasePresenter<View> {
        void search(String keywords, int page);
    }
}
