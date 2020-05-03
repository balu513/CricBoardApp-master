package com.example.balakrishnaballi.cricboardapp.mvp.presenter;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;

import com.example.balakrishnaballi.cricboardapp.constants.Consts;
import com.example.balakrishnaballi.cricboardapp.mvp.model.OldMatches;
import com.example.balakrishnaballi.cricboardapp.mvp.model.ScoreCard;
import com.example.balakrishnaballi.cricboardapp.network.CricApi;
import com.example.balakrishnaballi.cricboardapp.mvp.view.OldMatchesActivity;
import com.example.balakrishnaballi.cricboardapp.mvp.view.view;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class OldMatchesPresenter extends BasePresenter {

    private com.example.balakrishnaballi.cricboardapp.mvp.view.view.OldMatchesView view;
    private CricApi cricApi;

    public OldMatchesPresenter(Lifecycle lifecycle) {
        super(lifecycle);
    }

    public OldMatchesPresenter(Lifecycle lifecycle, view.OldMatchesView view, CricApi cricApi) {
        super(lifecycle);
        this.view = view;
        this.cricApi = cricApi;
    }

    @SuppressLint("CheckResult")
    public void requestOldMatches() {
        cricApi.getFullScoreCard("","").observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableMaybeObserver<ScoreCard>() {
                    @Override
                    public void onSuccess(ScoreCard scoreCard) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });



        DisposableSingleObserver<OldMatches> disposableSingleObserver = cricApi.getOldMatches(Consts.Network.API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<OldMatches>() {
                    @Override
                    public void onSuccess(OldMatches oldMatches) {
                        List<OldMatches.MatchSummary> list = oldMatches.getData();
                        view.sendBackOldMathces(list);

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.sendbackErrorResponse(e.getMessage());

                    }
                });
        disposable.add(disposableSingleObserver);

    }
}
