package com.example.kobenhavn.dal.sync.jobs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.dal.remote.RemoteException;
import com.example.kobenhavn.dal.sync.FetchPlaygroundsRxBus;
import com.example.kobenhavn.dal.sync.SyncResponseType;
import com.example.kobenhavn.dal.sync.jobs.setup.JobPriority;

import java.util.List;

import timber.log.Timber;

public class FetchPlaygroundsJob extends Job {

    private static final String TAG = SyncPlaygroundJob.class.getCanonicalName();

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

        List<Playground> playgrounds = RemoteDataSource.getInstance().getPlaygrounds();
        FetchPlaygroundsRxBus.getInstance().publishFetchingResponse(SyncResponseType.SUCCESS, playgrounds);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        FetchPlaygroundsRxBus.getInstance().publishFetchingResponse(SyncResponseType.FAILED, null);
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

        return RetryConstraint.CANCEL;
    }
}
