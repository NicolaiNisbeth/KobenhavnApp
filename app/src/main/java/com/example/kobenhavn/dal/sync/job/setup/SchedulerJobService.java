package com.example.kobenhavn.dal.sync.job.setup;

import androidx.annotation.NonNull;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;

/**
 * Taken from https://proandroiddev.com/offline-apps-its-easier-than-you-think-9ff97701a73f
 */
public class SchedulerJobService extends FrameworkJobSchedulerService {

    @NonNull
    @Override
    protected JobManager getJobManager() {
        return JobManagerFactory.getJobManager();
    }
}
