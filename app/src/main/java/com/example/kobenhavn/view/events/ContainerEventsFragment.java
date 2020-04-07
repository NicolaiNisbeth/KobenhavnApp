package com.example.kobenhavn.view.events;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.view.events.future.FutureFragment;
import com.example.kobenhavn.view.events.enrolled.EnrolledFragment;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModel;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModelFactory;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ContainerEventsFragment extends Fragment {
    @Inject
    PlaygroundsViewModelFactory playgroundViewModelFactory;

    @Inject UserViewModelFactory userViewModelFactory;

    private PlaygroundsViewModel playgroundsViewModel;
    private UserViewModel userViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        final View root = inflater.inflate(R.layout.events_container_fragment, container, false);
        playgroundsViewModel = ViewModelProviders.of(this, playgroundViewModelFactory).get(PlaygroundsViewModel.class);
        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);

        List<Event> enrolledEvents = new ArrayList<>();
        userViewModel.getUser(RemoteDataSource.loggedInUser.getUsername()).observe(getViewLifecycleOwner(), user -> enrolledEvents.addAll(user.getEvents()));


        List<Event> futureEvents = new ArrayList<>();

        if (RemoteDataSource.loggedInUser != null && RemoteDataSource.loggedInUser.getSubscribedPlaygrounds() != null){
            User user = RemoteDataSource.loggedInUser;

            for (Playground playground : user.getSubscribedPlaygrounds()){
                for (Event event : playground.getEvents()){
                    Date date = event.getDetails().getDate();
                    if (DateUtils.isToday(date.getTime()) || date.after(new Date(System.currentTimeMillis()))){
                        futureEvents.add(event);
                    }
                }
            }
        }


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(root.getContext(), getChildFragmentManager(), futureEvents, enrolledEvents);
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        return root;
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final String[] TAB_TITLES = new String[]{"Kommende", "Tilmeldte"};
        private final Context mContext;
        private final List<Event> futureEvents, enrolledEvents;

        public SectionsPagerAdapter(Context context, FragmentManager fm, List<Event> futureEvents, List<Event> enrolledEvents) {
            super(fm);
            mContext = context;
            this.futureEvents = futureEvents;
            this.enrolledEvents = enrolledEvents;
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = FutureFragment.newInstance(futureEvents);
                    break;
                case 1:
                    fragment = EnrolledFragment.newInstance(enrolledEvents);
                    break;
            }
            return Objects.requireNonNull(fragment);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLES[position];
        }

        @Override
        public int getCount() {
            return TAB_TITLES.length;
        }
    }
}
