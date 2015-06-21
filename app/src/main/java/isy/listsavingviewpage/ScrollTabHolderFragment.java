package isy.listsavingviewpage;

import android.support.v4.app.Fragment;
import android.widget.AbsListView;

/**
 * Created by justinyang on 5/4/15.
 */
public class ScrollTabHolderFragment extends Fragment implements ScrollTabHolder {

    protected ScrollTabHolder mScrollTabHolder;

    public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
        mScrollTabHolder = scrollTabHolder;
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerTranslationY, boolean isOthersTop) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        // nothing
    }

}
