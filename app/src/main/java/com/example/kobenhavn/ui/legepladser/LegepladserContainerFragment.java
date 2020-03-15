package com.example.kobenhavn.ui.legepladser;

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
import com.example.kobenhavn.ui.legepladser.tilfoj.TilfojLegepladsActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

public class LegepladserContainerFragment extends Fragment {

    private Toolbar toolbar;
    ArrayList<Pair<String, LegepladsFragment>> tabList;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.legepladser_container_fragment, container, false);
        setHasOptionsMenu(true);

        // setup toolbar
        toolbar = root.findViewById(R.id.legeplads_toolbar);
        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Legepladser");



        // fetch data
        tabList = new ArrayList<>();
        for (LegepladsModel model : fetchSubscribedLegepladser()){
            tabList.add(new Pair<>(model.getTitel(), LegepladsFragment.newInstance(model)));
        }

        // setup table layout
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(root.getContext(), getChildFragmentManager(), tabList);
        ViewPager viewPager = root.findViewById(R.id.legepladser_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabLayout = root.findViewById(R.id.legepladser_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        final int TAB_CONTENT_START = 96;

        tabLayout.setOnScrollChangeListener(new View.OnScrollChangeListener() {

            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                final int maxAllowWidthOfTabBar = tabLayout.getMeasuredWidth() + TAB_CONTENT_START;
                if (scrollX > maxAllowWidthOfTabBar)
                    tabLayout.setScrollX(maxAllowWidthOfTabBar);
            }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_legepladser) {
            Intent intent = new Intent(getContext(), TilfojLegepladsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Context context;
        private ArrayList<Pair<String, LegepladsFragment>> tabList;

        public SectionsPagerAdapter(Context context, FragmentManager fm, ArrayList<Pair<String, LegepladsFragment>> tabList) {
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
    public ArrayList<LegepladsModel> fetchSubscribedLegepladser(){
        ArrayList<LegepladsModel> data = new ArrayList<>();
        LegepladsModel lindevangsparken = new LegepladsModel("Lindevangsparken", "Agervænget 34", "beskrivelse", null, "img_url");
        LegepladsModel soerbyparken = new LegepladsModel("Sørbyparken", "Blåhavsgade 5", "beskrivelse", null, "img_url");
        LegepladsModel frederiksbergparken = new LegepladsModel("Frederiksbergparken", "Søpapair 13", "beskrivelse", null, "img_url");
        LegepladsModel athenparken = new LegepladsModel("Athenparken", "Berliner Strasse 55", "beskrivelse", null, "img_url");
        data.add(lindevangsparken);
        data.add(soerbyparken);
        data.add(frederiksbergparken);
        data.add(athenparken);
        return data;
    }
}
