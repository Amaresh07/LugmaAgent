package com.lugma.LugmaAgent.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lugma.LugmaAgent.Adapter.GroupAdapter;
import com.lugma.LugmaAgent.Adapter.RestaurantAdapter;
import com.lugma.LugmaAgent.R;
import com.lugma.LugmaAgent.Utilities.Group;
import com.lugma.LugmaAgent.Utilities.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CodeBele-PC on 14-12-2017.
 */

public class RestaurantFragment extends Fragment {
    View v;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentActivity myContext;
    public RestaurantFragment() {
    }

    public static RestaurantFragment newInstance() {
        
        Bundle args = new Bundle();
        
        RestaurantFragment fragment = new RestaurantFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        v=inflater.inflate(R.layout.fragment_restaurant,container,false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout)v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return  v;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(myContext.getSupportFragmentManager());
        adapter.addFragment(new UnclaimedFragment(), "UNCLAIMED");
        adapter.addFragment(new ClaimedFragment(), "CLAIMED");
        adapter.addFragment(new PremiumFragment(),"PREMIUM");

        viewPager.setAdapter(adapter);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
}
