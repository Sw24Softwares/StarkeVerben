package org.sw24softwares.starkeverben;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProgressTabsFragment extends Fragment {
    private TabLayout mTabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_progress_tabs, container, false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = getActivity().findViewById(R.id.sliding_tabs);
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
                    return new ProgressGraphFragment();
                case 1:
                    return new ProgressFragment();
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
