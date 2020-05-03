package com.example.kobenhavn.dal.sync.job;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.kobenhavn.dal.local.CloneUtils;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.dal.remote.RemoteException;
import com.example.kobenhavn.dal.sync.RemoteResponseType;
import com.example.kobenhavn.dal.sync.SyncUserRxBus;
import com.example.kobenhavn.dal.sync.job.setup.JobPriority;

import timber.log.Timber;

public class UpdateUserJob extends Job {
    private final static String TAG = UpdateUserJob.class.getCanonicalName();
    private final User user;

    public UpdateUserJob(User user) {
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
        Timber.e("Executing job for %s", user.getId());

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        RemoteDataSource.getInstance().updateUser(user);

        // remote call was successful--the Comment will be updated locally to reflect that sync is no longer pending
        User updatedUser = CloneUtils.cloneUser(user, false);
        SyncUserRxBus.getInstance().post(RemoteResponseType.SUCCESS, updatedUser);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.e("Canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        SyncUserRxBus.getInstance().post(RemoteResponseType.FAILED, user);
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        if (throwable instanceof RemoteException) {
            RemoteException exception = (RemoteException) throwable;

            int statusCode = exception.getResponse().code();
            if (statusCode >= 400 && statusCode <= 500) {
                return RetryConstraint.CANCEL;
            }
        }
        // if we are here, most likely the connection was lost during job execution
        return RetryConstraint.RETRY;
    }
}
