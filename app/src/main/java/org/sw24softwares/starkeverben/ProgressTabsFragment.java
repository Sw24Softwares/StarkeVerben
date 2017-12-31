package org.sw24softwares.starkeverben;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

public class ProgressTabsFragment extends Fragment {

        private SectionsPagerAdapter mSectionsPagerAdapter;
        private TabLayout mTabLayout;
        private ViewPager mViewPager;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.activity_progress_tabs, container, false);

                // Create the adapter that will return a fragment for each of the three
                // primary sections of the activity.
                mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

                // Set up the ViewPager with the sections adapter.
                mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
                mViewPager.setAdapter(mSectionsPagerAdapter);

                mTabLayout = (TabLayout) getActivity().findViewById(R.id.sliding_tabs);
                mTabLayout.setupWithViewPager(mViewPager);

                return view;
        }

        @Override
        public void onResume() {
                mTabLayout.setVisibility(View.VISIBLE);
                super.onResume();
        }

        @Override
        public void onStop() {
                mTabLayout.setVisibility(View.GONE);
                super.onStop();
        }

        public class SectionsPagerAdapter extends FragmentPagerAdapter {

                public SectionsPagerAdapter(FragmentManager fm) {
                        super(fm);
                }

                @Override
                public Fragment getItem(int position) {
                        switch (position) {
                                case 0:
                                        ProgressGraphFragment tab1 = new ProgressGraphFragment();
                                        return tab1;
                                case 1:
                                        ProgressFragment tab2 = new ProgressFragment();
                                        return tab2;
                                default:
                                        return null;
                        }
                }

                @Override
                public int getCount() {
                        // Show 2 total pages.
                        return 2;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                        switch (position) {
                                case 0:
                                        return getText(R.string.chart);
                                case 1:
                                        return getText(R.string.lesson);
                        }
                        return null;
                }
        }
}
