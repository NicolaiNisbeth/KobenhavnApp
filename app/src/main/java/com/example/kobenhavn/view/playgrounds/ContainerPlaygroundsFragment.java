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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.Subscriptions;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.view.playgrounds.add.AddPlaygroundActivity;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModelFactory;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class ContainerPlaygroundsFragment extends Fragment {
    private Toolbar toolbar;

    @BindView(R.id.events_enrolled_empty_msg)
    TextView _emptyView;

    @Inject
    UserViewModelFactory userViewModelFactory;

    @Inject
    PlaygroundsViewModelFactory playgroundsViewModelFactory;

    private UserViewModel userViewModel;

    public ContainerPlaygroundsFragment(){}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.playgrounds_container_fragment, container, false);
        ButterKnife.bind(this, root);

        // setup toolbar_item
        setHasOptionsMenu(true);
        toolbar = root.findViewById(R.id.legeplads_toolbar);
        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Legepladser");

        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(root.getContext(), getChildFragmentManager(), new ArrayList<>(), _emptyView, userViewModel);
        ViewPager viewPager = root.findViewById(R.id.playgrounds_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabLayout = root.findViewById(R.id.playground_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        userViewModel.getSubscriptionsLive(RemoteDataSource.loggedInUser.getUsername()).observe(getViewLifecycleOwner(), sectionsPagerAdapter::onSubscriptionChange);

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
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

    static class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private final TextView _emptyView;
        private final UserViewModel viewModel;
        private Context context;
        private ArrayList<Pair<String, PlaygroundsFragment>> tabList;
        private Subscriptions subscriptions;

        public SectionsPagerAdapter(Context context, FragmentManager fm, ArrayList<Pair<String, PlaygroundsFragment>> tabList, TextView _emptyView, UserViewModel viewModel) {
            super(fm);
            this.context = context;
            this.tabList = tabList;
            this._emptyView = _emptyView;
            this.viewModel = viewModel;
        }

        @Override
        public int getCount() {
            return tabList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position).first;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        /**
         * TO UNSUBSCRIBE
         * @param i
         * @return
         */
        @NotNull
        @Override
        public Fragment getItem(int i) {
            PlaygroundsFragment fragment = tabList.get(i).second;
            if (fragment != null){
                // remove playground
                fragment.setOnItemClickListener(playground -> {
                    List<Playground> updatedPlaygrounds = subscriptions.getSubscriptions();
                    updatedPlaygrounds.remove(playground);
                    viewModel.updateSubscriptionsLocally(RemoteDataSource.loggedInUser, updatedPlaygrounds);
                    Toast.makeText(context, "Legeplads blev fjernet", Toast.LENGTH_SHORT).show();
                });
            }
            return Objects.requireNonNull(tabList.get(i).second);
        }

        void onSubscriptionChange(Subscriptions subscriptions) {
            if (subscriptions == null) return;

            this.subscriptions = subscriptions;
            tabList.clear();
            for (Playground playground : subscriptions.getSubscriptions()){
                tabList.add(new Pair<>(playground.getName(), PlaygroundsFragment.newInstance(playground)));
            }
            tabList.sort((o1, o2) -> o1.first.compareTo(o2.first));

            if (tabList != null && tabList.size() > 0) _emptyView.setVisibility(View.GONE);
            else _emptyView.setVisibility(View.VISIBLE);

            notifyDataSetChanged();
        }
    }
}
