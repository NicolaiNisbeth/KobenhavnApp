package com.example.kobenhavn.dal.sync.jobs;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.offline.dal.local.LocalePlaygroundUtils;
import com.example.offline.dal.local.model.Playground;
import com.example.offline.dal.remote.RemoteDataSource;
import com.example.offline.dal.remote.RemoteException;
import com.example.offline.dal.sync.SyncPlaygroundRxBus;
import com.example.offline.dal.sync.SyncResponseType;
import com.example.offline.dal.sync.jobs.setup.JobPriority;

import timber.log.Timber;

public class SyncPlaygroundJob extends Job {

    private static final String TAG = SyncPlaygroundJob.class.getCanonicalName();
    private final Playground playground;

    public SyncPlaygroundJob(Playground playground) {
        super(new Params(JobPriority.MID)
                .requireNetwork()
                .groupBy(TAG)
                .persist());

        this.playground = playground;
    }

    @Override
    public void onAdded() {
        Timber.e("Added playground job to priority queue");
    }

    @Override
    public void onRun() throws Throwable {
        Timber.e("Executing job for %s", playground);

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        RemoteDataSource.getInstance().getPlayground(playground.getId());

        // remote call was successful--the Comment will be updated locally to reflect that sync is no longer pending
        Playground updatedPlayground = LocalePlaygroundUtils.clone(playground, false);
        SyncPlaygroundRxBus.getInstance().post(SyncResponseType.SUCCESS , updatedPlayground);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.e("Canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        // sync to remote failed
        SyncPlaygroundRxBus.getInstance().post(SyncResponseType.FAILED, playground);
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
