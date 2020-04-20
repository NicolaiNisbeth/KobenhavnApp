package com.example.kobenhavn.dal.sync.jobs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.kobenhavn.dal.local.LocaleUserUtils;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.dal.remote.RemoteException;
import com.example.kobenhavn.dal.sync.SyncResponseType;
import com.example.kobenhavn.dal.sync.SyncUserRxBus;
import com.example.kobenhavn.dal.sync.UpdateSubscriptionsRxBus;
import com.example.kobenhavn.dal.sync.jobs.setup.JobPriority;

import java.util.List;

import timber.log.Timber;

public class UpdateUserWithSubscriptionsJob extends Job {
    private final static String TAG = UpdateUserWithSubscriptionsJob.class.getCanonicalName();
    private final String username;
    private final List<Playground> playgrounds;

    public UpdateUserWithSubscriptionsJob(String username, List<Playground> playgrounds) {
        super(new Params(JobPriority.MID)
        .requireNetwork()
        .groupBy(TAG)
        .persist());

        this.username = username;
        this.playgrounds = playgrounds;
    }

    @Override
    public void onAdded() {
        Timber.e("Added update user with subscriptions to priority queue");
    }

    @Override
    public void onRun() throws Throwable {
        Timber.e("Executing subscription job for %s", username);

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        RemoteDataSource.getInstance().updateUserWithSubscription(username, playgrounds);

        // remote call was successful--the Comment will be updated locally to reflect that sync is no longer pending
        UpdateSubscriptionsRxBus.getInstance().post(SyncResponseType.SUCCESS, username);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        // sync to remote failed
        Timber.e("Canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        UpdateSubscriptionsRxBus.getInstance().post(SyncResponseType.FAILED, username);
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        if (throwable instanceof RemoteException) {
            RemoteException exception = (RemoteException) throwable;

            int statusCode = exception.getResponse().code();
            if (statusCode >= 400 && statusCode < 500) {
                return RetryConstraint.CANCEL;
            }
        }
        // if we are here, most likely the connection was lost during job execution
        return RetryConstraint.RETRY;
    }
}
