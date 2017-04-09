package net.angrycode.wesou.mvp;

import net.angrycode.wesou.R;
import net.angrycode.wesou.api.TorrentProjectRequest;
import net.angrycode.wesou.bean.TorrentProject;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lancelot on 2017/4/4.
 */

public class SearchPresenter extends Presenter<SearchContract.View> implements SearchContract.Presenter {

    public SearchPresenter(SearchContract.View view) {
        super(view);
    }

    @Override
    public void create() {

    }


    @Override
    public void search(String keywords, int page) {
        Observable<List<TorrentProject>> observable = Observable.create(subscriber -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("keywords", keywords);
            params.put("start", String.valueOf(page));

            TorrentProjectRequest request = new TorrentProjectRequest(params);
            List<TorrentProject> projectList = request.get();
            subscriber.onNext(projectList);
            subscriber.onCompleted();
        });
        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(projectList -> {
                    if (projectList != null) {
                        mView.onGetTorrentProjectListFinish(projectList);
                    } else {
                        mView.onError(1, getActivity().getString(R.string.error_request_data));
                    }
                    mView.onFinished();
                }, throwable -> {
                    mView.onError(2, getActivity().getString(R.string.error_observable));
                    mView.onFinished();
                }, () -> {
                    mView.onFinished();
                });
    }
}
