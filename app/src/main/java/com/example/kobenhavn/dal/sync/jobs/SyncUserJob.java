package com.example.kobenhavn.dal.sync.jobs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.kobenhavn.dal.local.LocaleUserUtils;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.dal.remote.RemoteException;
import com.example.kobenhavn.dal.sync.SyncResponseType;
import com.example.kobenhavn.dal.sync.SyncUserRxBus;
import com.example.kobenhavn.dal.sync.jobs.setup.JobPriority;

import timber.log.Timber;

public class SyncUserJob extends Job {
    private final static String TAG = SyncUserJob.class.getCanonicalName();
    private final User user;

    public SyncUserJob(User user) {
        super(new Params(JobPriority.MID)
        .requireNetwork()
        .groupBy(TAG)
        .persist());

        this.user = user;
    }

    @Override
    public void onAdded() {
        Timber.e("Added sync user job to priority queue");
    }

    @Override
    public void onRun() throws Throwable {
        Timber.e("Executing job for %s", user);

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        RemoteDataSource.getInstance().updateUser(user);

        // remote call was successful--the Comment will be updated locally to reflect that sync is no longer pending
        User updatedUser = LocaleUserUtils.clone(user, false);
        SyncUserRxBus.getInstance().post(SyncResponseType.SUCCESS, updatedUser);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        // sync to remote failed
        Timber.e("Canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        SyncUserRxBus.getInstance().post(SyncResponseType.FAILED, user);
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
