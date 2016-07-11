package com.ivanmorgillo.fromgodactivitytomvp.mvp.ui;

import com.ivanmorgillo.fromgodactivitytomvp.api.models.Question;

import java.util.List;

public interface IMvpView {

    void showProgress();

    void hideProgress();

    void showList();

    void hideList();

    void updateList(List<Question> questions);
}
