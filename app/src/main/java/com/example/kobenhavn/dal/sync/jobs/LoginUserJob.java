package com.example.kobenhavn.dal.sync.jobs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.dal.remote.RemoteException;
import com.example.kobenhavn.dal.sync.LoginUserRxBus;
import com.example.kobenhavn.dal.sync.SyncResponseType;
import com.example.kobenhavn.dal.sync.jobs.setup.JobPriority;

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

        User user = RemoteDataSource.getInstance().loginUser(username, password);
        LoginUserRxBus.getInstance().post(SyncResponseType.SUCCESS, user);

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        LoginUserRxBus.getInstance().post(SyncResponseType.FAILED, null);
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        if (throwable instanceof RemoteException){
            RemoteException exception = (RemoteException) throwable;

            int statusCode = exception.getResponse().code();
            if (statusCode >= 400 && statusCode < 500){
                return RetryConstraint.CANCEL;
            }
        }

        return RetryConstraint.RETRY;
    }
}
