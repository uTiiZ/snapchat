package com.utiiz.snapchat.adapter;

/**
 * Created by t.kervran on 16/06/2017.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Adapter for {@link ViewPager} that will populated from the collection of Fragments classes. Objects of that classes
 * will be instantiated on demand and used as a pages views.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Class<? extends Fragment>> mPages = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private Context mContext;

    public FragmentAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(mContext, mPages.get(position).getName());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mPages.size();
    }

    public void add(Class<? extends Fragment> page, String title) {
        mPages.add(page);
        mTitles.add(title);
    }

    public int getPagesCount() {
        return mPages.size();
    }

    public int indexOf(Class<? extends Fragment> page) {
        return mPages.indexOf(page);
    }

    public String get(Integer position) {
        return mTitles.get(position);
    }
}