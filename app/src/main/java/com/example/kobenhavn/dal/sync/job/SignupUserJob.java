package com.example.kobenhavn.dal.sync.job;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.dal.remote.RemoteException;
import com.example.kobenhavn.dal.sync.SignupUserRxBus;
import com.example.kobenhavn.dal.sync.RemoteResponseType;
import com.example.kobenhavn.dal.sync.job.setup.JobPriority;
import timber.log.Timber;

public class SignupUserJob extends Job {

    private static final String TAG = SignupUserJob.class.getCanonicalName();
    private final String name;
    private final String username;
    private final String password;

    public SignupUserJob(String name, String username, String password) {
        super(new Params(JobPriority.HIGH)
                .requireNetwork()
                .groupBy(TAG));

        this.name = name;
        this.username = username;
        this.password = password;
    }

    @Override
    public void onAdded() {
        Timber.e("Sign up user job is added to priority queue");
    }

    @Override
    public void onRun() throws Throwable {
        Timber.e("Executing login user job");

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        RemoteDataSource.getInstance().signupUser(name, username, password);

        // remote call was successful--the Comment will be updated locally to reflect that sync is no longer pending
        SignupUserRxBus.getInstance().post(RemoteResponseType.SUCCESS);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.e("Canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        SignupUserRxBus.getInstance().post(RemoteResponseType.FAILED);
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
