package com.example.kobenhavn.view.playgrounds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.view.playgrounds.add.AddPlaygroundActivity;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ContainerPlaygroundsFragment extends Fragment {
    private Toolbar toolbar;
    private ArrayList<Pair<String, PlaygroundsFragment>> tabList;

    @Inject
    UserViewModelFactory userViewModelFactory;

    private UserViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        View root = inflater.inflate(R.layout.playgrounds_container_fragment, container, false);

        // setup toolbar
        setHasOptionsMenu(true);
        toolbar = root.findViewById(R.id.legeplads_toolbar);
        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Legepladser");

        viewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);

        tabList = new ArrayList<>();



        // setup table layout
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(root.getContext(), getChildFragmentManager(), tabList);
        ViewPager viewPager = root.findViewById(R.id.playgrounds_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabLayout = root.findViewById(R.id.playground_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        if (RemoteDataSource.loggedInUser.getSubscribedPlaygrounds() != null){
            viewModel.getUser(RemoteDataSource.loggedInUser.getUsername()).observe(getViewLifecycleOwner(), sectionsPagerAdapter::onChange);
        }


        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_nav_add_legepladser, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_legepladser) {
            Intent intent = new Intent(getContext(), AddPlaygroundActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {
        private Context context;
        private ArrayList<Pair<String, PlaygroundsFragment>> tabList;

        public SectionsPagerAdapter(Context context, FragmentManager fm, ArrayList<Pair<String, PlaygroundsFragment>> tabList) {
            super(fm);
            this.context = context;
            this.tabList = tabList;
        }

        @Override
        public int getCount() {
            return tabList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return (tabList.get(position).first);
        }

        @Override
        public Fragment getItem(int i) {
            return Objects.requireNonNull(tabList.get(i).second);
        }

        public void onChange(User user) {
            tabList.clear();
            for (Playground model : RemoteDataSource.loggedInUser.getSubscribedPlaygrounds()){
                tabList.add(new Pair<>(model.getName(), PlaygroundsFragment.newInstance(model)));
            }
            tabList.sort((o1, o2) -> o1.first.compareTo(o2.first));

            notifyDataSetChanged();
        }
    }
}
