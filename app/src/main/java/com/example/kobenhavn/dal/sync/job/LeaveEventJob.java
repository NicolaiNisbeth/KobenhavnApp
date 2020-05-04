package com.example.kobenhavn.dal.sync.job;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.dal.remote.RemoteException;
import com.example.kobenhavn.dal.sync.LeaveEventRxBus;
import com.example.kobenhavn.dal.sync.RemoteResponseType;
import com.example.kobenhavn.dal.sync.job.setup.JobPriority;

import timber.log.Timber;

public class LeaveEventJob extends Job {
    private static final String TAG = LeaveEventJob.class.getCanonicalName();
    private final String playgroundName;
    private final Event event;
    private final User user;

    public LeaveEventJob(String playgroundName, Event event, User user) {
        super(new Params(JobPriority.MID)
                .requireNetwork()
                .groupBy(TAG)
                .persist());

        this.playgroundName = playgroundName;
        this.event = event;
        this.user = user;
    }

    @Override
    public void onAdded() {
        Timber.e("User leave event job was added to priority queue");
    }

    @Override
    public void onRun() throws Throwable {
        Timber.e("Executing remove user from event job");

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        RemoteDataSource.getInstance().leaveEvent(playgroundName, event.getId(), user.getUsername());

        // remote call was successful--the Comment will be updated locally to reflect that sync is no longer pending
        LeaveEventRxBus.getInstance().post(RemoteResponseType.SUCCESS, user, event);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.e("Canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        LeaveEventRxBus.getInstance().post(RemoteResponseType.FAILED, user, event);
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
