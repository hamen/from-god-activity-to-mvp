package com.ivanmorgillo.fromgodactivitytomvp.mvp.ui;

import com.ivanmorgillo.fromgodactivitytomvp.api.StackOverflowApiManager;
import com.ivanmorgillo.fromgodactivitytomvp.api.models.Question;
import com.ivanmorgillo.fromgodactivitytomvp.api.models.SearchResponse;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MvpPresenter {

    private final StackOverflowApiManager apiManager;

    private IMvpView view;

    @Inject
    public MvpPresenter(StackOverflowApiManager stackOverflowApiManager) {
        this.apiManager = stackOverflowApiManager;
    }

    public void setView(IMvpView view) {
        this.view = view;
    }

    public void loadQuestions() {
        apiManager.getSearchResponse("android")
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    view.hideList();
                    view.showProgress();
                }
            })
            .map(new Func1<SearchResponse, List<Question>>() {
                @Override
                public List<Question> call(SearchResponse searchResponse) {
                    return searchResponse.getQuestions();
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<Question>>() {
                @Override
                public void onCompleted() {
                    view.hideProgress();
                }

                @Override
                public void onError(Throwable e) {
                    view.hideProgress();
                    view.showErrorMessage();
                }

                @Override
                public void onNext(List<Question> questions) {
                    view.showList();
                    view.updateList(questions);
                }
            });
    }
}
