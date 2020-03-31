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
import androidx.viewpager.widget.ViewPager;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.PlaygroundModel;
import com.example.kobenhavn.view.playgrounds.add.AddPlaygroundActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

public class ContainerPlaygroundsFragment extends Fragment {
    private Toolbar toolbar;
    private ArrayList<Pair<String, PlaygroundsFragment>> tabList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.playgrounds_container_fragment, container, false);

        // setup toolbar
        setHasOptionsMenu(true);
        toolbar = root.findViewById(R.id.legeplads_toolbar);
        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Legepladser");

        // fetch data
        tabList = new ArrayList<>();
        for (PlaygroundModel model : fetchSubscribedLegepladser()){
            tabList.add(new Pair<>(model.getTitle(), PlaygroundsFragment.newInstance(model)));
        }

        // setup table layout
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(root.getContext(), getChildFragmentManager(), tabList);
        ViewPager viewPager = root.findViewById(R.id.playgrounds_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabLayout = root.findViewById(R.id.playground_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        final int TAB_CONTENT_START = 96;
        tabLayout.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            final int maxAllowWidthOfTabBar = tabLayout.getMeasuredWidth() + TAB_CONTENT_START;
            if (scrollX > maxAllowWidthOfTabBar)
                tabLayout.setScrollX(maxAllowWidthOfTabBar);
        });

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
            return ((Fragment) Objects.requireNonNull(tabList.get(i).second));
        }


    }
    public ArrayList<PlaygroundModel> fetchSubscribedLegepladser(){
        ArrayList<PlaygroundModel> data = new ArrayList<>();
        PlaygroundModel lindevangsparken = new PlaygroundModel("Lindevangsparken", "Agervænget 34", "beskrivelse", null, "img_url");
        PlaygroundModel soerbyparken = new PlaygroundModel("Sørbyparken", "Blåhavsgade 5", "beskrivelse", null, "img_url");
        PlaygroundModel frederiksbergparken = new PlaygroundModel("Frederiksbergparken", "Søpapair 13", "beskrivelse", null, "img_url");
        PlaygroundModel athenparken = new PlaygroundModel("Athenparken", "Berliner Strasse 55", "beskrivelse", null, "img_url");
        data.add(lindevangsparken);
        data.add(soerbyparken);
        data.add(frederiksbergparken);
        data.add(athenparken);
        return data;
    }
}
