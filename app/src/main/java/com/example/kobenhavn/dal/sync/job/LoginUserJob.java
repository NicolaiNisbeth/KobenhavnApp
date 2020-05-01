package com.example.kobenhavn.dal.sync.job;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.dal.remote.RemoteException;
import com.example.kobenhavn.dal.sync.LoginUserRxBus;
import com.example.kobenhavn.dal.sync.RemoteResponseType;
import com.example.kobenhavn.dal.sync.job.setup.JobPriority;

import timber.log.Timber;

public class LoginUserJob extends Job {
    private static final String TAG = LoginUserJob.class.getCanonicalName();
    private final String username;
    private final String password;

    public LoginUserJob(String username, String password) {
        super(new Params(JobPriority.HIGH)
                .requireNetwork()
                .groupBy(TAG));

        this.username = username;
        this.password = password;
    }

    @Override
    public void onAdded() {
        Timber.e("Login user job is added to priority queue");
    }

    @Override
    public void onRun() throws Throwable {
        Timber.e("Executing login user job");

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        User user = RemoteDataSource.getInstance().loginUser(username, password);

        // remote call was successful--the Comment will be updated locally to reflect that sync is no longer pending
        LoginUserRxBus.getInstance().post(RemoteResponseType.SUCCESS, user);

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.e("Canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        LoginUserRxBus.getInstance().post(RemoteResponseType.FAILED, throwable);
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
