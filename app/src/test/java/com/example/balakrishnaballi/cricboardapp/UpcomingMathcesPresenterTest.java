package com.example.balakrishnaballi.cricboardapp;

import com.example.balakrishnaballi.cricboardapp.constants.Consts;
import com.example.balakrishnaballi.cricboardapp.mvp.model.Match;
import com.example.balakrishnaballi.cricboardapp.mvp.model.Matches;
import com.example.balakrishnaballi.cricboardapp.network.CricApi;
import com.example.balakrishnaballi.cricboardapp.mvp.presenter.UpcomingMatchesPresenter;
import com.example.balakrishnaballi.cricboardapp.mvp.view.view;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UpcomingMathcesPresenterTest {
    @BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }
    /*
    To test Presenter API,we have to comment Android Components so before test we have to comment
    "observeOn(AndroidSchedulers.mainThread())" inside requestUpcomingCricketMatches().
     */
    @Test
    public void testFecthUpcomingMatches() {
        CricApi api = mock(CricApi.class);
        view.UpcomingMatchesView mockView = mock(view.UpcomingMatchesView.class);


        Matches matches = new Matches();
        List<Match> matchList = getMathcesMock();
        matches.setMatches(matchList);


        UpcomingMatchesPresenter presenter = new UpcomingMatchesPresenter(null, mockView, api);
        when(api.getUpcomongMatches(Consts.Network.API_KEY)).thenReturn(Single.just(matches));
        presenter.requestUpcomingCricketMatches();

        verify(mockView).sendBackUpcomingMathces(matchList);
    }

    @Test(expected = Exception.class)
    public void testErrorResponse() {
        CricApi api = mock(CricApi.class);
        view.UpcomingMatchesView mockView = mock(view.UpcomingMatchesView.class);

        when(api.getUpcomongMatches(Consts.Network.API_KEY)).thenThrow(new Exception());

        UpcomingMatchesPresenter presenter = new UpcomingMatchesPresenter(null, mockView, api);
        presenter.requestUpcomingCricketMatches();

        verify(mockView).sendErrorReport("error");
    }

    public List<Match> getMathcesMock() {
        List<Match> matches = new ArrayList<Match>();
        for (int i = 0; i < 20; i++) {
            Match match = new Match();
            match.setDate("date:" + i);
            match.setTeam1("team1 " + i);
            match.setTeam2("team2" + i);
            matches.add(match);
        }
        return matches;
    }
}
