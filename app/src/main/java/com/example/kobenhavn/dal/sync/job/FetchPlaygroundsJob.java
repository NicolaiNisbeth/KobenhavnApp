package com.example.kobenhavn.dal.sync.job;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.dal.remote.RemoteException;
import com.example.kobenhavn.dal.sync.FetchPlaygroundsRxBus;
import com.example.kobenhavn.dal.sync.RemoteResponseType;
import com.example.kobenhavn.dal.sync.job.setup.JobPriority;

import java.util.List;

import timber.log.Timber;

public class FetchPlaygroundsJob extends Job {

    private static final String TAG = FetchPlaygroundsJob.class.getCanonicalName();

    public FetchPlaygroundsJob() {
        super(new Params(JobPriority.HIGH)
                .requireNetwork()
                .groupBy(TAG)
                .persist());
    }

    @Override
    public void onAdded() {
        Timber.e("Added fetch playgrounds job to priority queue");
    }

    @Override
    public void onRun() throws Throwable {
        Timber.e("Executing fetching playground job");

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        List<Playground> playgrounds = RemoteDataSource.getInstance().
                getPlaygrounds();

        // remote call was successful--the Comment will be updated locally to reflect that sync is no longer pending
        FetchPlaygroundsRxBus.getInstance().publishFetchingResponse(RemoteResponseType.SUCCESS, playgrounds);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.e("Canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        FetchPlaygroundsRxBus.getInstance().publishFetchingResponse(RemoteResponseType.FAILED, null);
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        if (throwable instanceof RemoteException){
            RemoteException exception = (RemoteException) throwable;

            int statusCode = exception.getResponse().code();
            if (statusCode >= 400 && statusCode <= 500){
                return RetryConstraint.CANCEL;
            }
        }

        return RetryConstraint.CANCEL;
    }
}
