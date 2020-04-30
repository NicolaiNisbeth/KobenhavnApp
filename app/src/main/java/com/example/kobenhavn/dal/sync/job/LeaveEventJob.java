package com.example.kobenhavn.dal.sync.job;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.dal.remote.RemoteException;
import com.example.kobenhavn.dal.sync.RemoveEventRxBus;
import com.example.kobenhavn.dal.sync.RemoteResponseType;
import com.example.kobenhavn.dal.sync.SyncUserRxBus;
import com.example.kobenhavn.dal.sync.job.setup.JobPriority;

import timber.log.Timber;

public class LeaveEventJob extends Job {
    private static final String TAG = LeaveEventJob.class.getCanonicalName();
    private final String playgroundName;
    private final String eventID;
    private final String username;

    public LeaveEventJob(String playgroundName, String eventID, String username) {
        super(new Params(JobPriority.MID)
                .requireNetwork()
                .groupBy(TAG)
                .persist());

        this.playgroundName = playgroundName;
        this.eventID = eventID;
        this.username = username;
    }

    @Override
    public void onAdded() {
        Timber.e("User leave event job was added to priority queue");
    }

    @Override
    public void onRun() throws Throwable {
        Timber.e("Executing remove user from event job");

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        RemoteDataSource.getInstance().leaveEvent(playgroundName, eventID, username);

        // remote call was successful--the Comment will be updated locally to reflect that sync is no longer pending
        RemoveEventRxBus.getInstance().post(RemoteResponseType.SUCCESS, true);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.e("Canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        SyncUserRxBus.getInstance().post(RemoteResponseType.FAILED, null);
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
