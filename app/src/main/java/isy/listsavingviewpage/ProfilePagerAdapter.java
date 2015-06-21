package isy.listsavingviewpage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;

/**
 * Created by justinyang on 5/9/15.
 */
public class ProfilePagerAdapter extends FragmentPagerAdapter {

    private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
    private final String[] TITLES = {"PAGE1", "PAGE2", "PAGE3"};
    private ScrollTabHolder mListener;
    private Bundle bundle;
    private ArrayList<ScrollTabHolderFragment> scrollTabHolderFragmentArrayList = new ArrayList<ScrollTabHolderFragment>();

    public ProfilePagerAdapter(FragmentManager fm, Bundle bundle, ArrayList<ScrollTabHolderFragment> scrollTabholder) {
        super(fm);
        mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
        this.scrollTabHolderFragmentArrayList = scrollTabholder;
        this.bundle = bundle;
    }

    public void setTabHolderScrollingContent(ScrollTabHolder listener) {
        mListener = listener;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return scrollTabHolderFragmentArrayList.size();
    }

    @Override
    public Fragment getItem(int position) {
        ScrollTabHolderFragment fragment = scrollTabHolderFragmentArrayList.get(position);
        mScrollTabHolders.put(position, fragment);

        if (mListener != null) {
            fragment.setScrollTabHolder(mListener);
        }
        return fragment;
    }

    public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
        return mScrollTabHolders;
    }

}
