package isy.listsavingviewpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by justinyang on 5/9/15.
 */
public class ProfilePage2Fragment extends ScrollTabHolderFragment implements AbsListView.OnScrollListener {

    private CustomAdapter adapter;
    private ArrayList<String> stringArrayList = new ArrayList<String>();
    private int mPosition = 1;
    private ListView listView;
    private boolean isTop = true, toTop = false;
    private int bufferItemCount = 10;
    private int itemCount = 0;
    private boolean isLoading = false;

    public ProfilePage2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page1, null);
        listView = (ListView) rootView.findViewById(R.id.profile_favorite_list);
        View headerView = inflater.inflate(R.layout.profile_blank_header, listView, false);
        listView.addHeaderView(headerView);
        adapter = new CustomAdapter(getActivity().getApplicationContext(), stringArrayList);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        for (int i = 0; i < 100; i++) {
            stringArrayList.add("" + i++);
        }
        adapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
        super.setScrollTabHolder(scrollTabHolder);
    }

    public void loadMore() {
        for (int i = 0; i < 100; i++) {
            stringArrayList.add("" + i);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerTranslationY, boolean isOthersTop) {
        if (listView == null) return;
        if (isOthersTop & listView.getChildAt(1) != null) {
            listView.setSelectionFromTop(1, scrollHeight);
            isTop = true;
        }
        if (!isOthersTop & isTop & listView.getChildAt(1) != null) {
            listView.setSelectionFromTop(1, (int) getResources().getDimension(R.dimen.tab_height));
        }
        if (toTop & listView.getChildAt(1) != null) {
            listView.setSelectionFromTop(1, (int) getResources().getDimension(R.dimen.tab_height));
            toTop = false;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (view != null && view.getChildAt(1) != null && view.getChildAt(1).getTop() < (int) getResources().getDimension(R.dimen.tab_height))
            isTop = false;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mScrollTabHolder != null)
            mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
        if (view != null && view.getChildAt(1) != null && view.getChildAt(1).getTop() < (int) getResources().getDimension(R.dimen.tab_height))
            isTop = false;
        if (totalItemCount < itemCount) {
            this.itemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.isLoading = true;
            }
        }
        if (isLoading && (totalItemCount > itemCount)) {
            isLoading = false;
            itemCount = totalItemCount;
        }
        if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + bufferItemCount)) {
            loadMore();
            isLoading = true;
        }
    }
}